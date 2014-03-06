/*******************************************************************************
 * Australian National University Orcid Updater
 * Copyright (C) 2013  The Australian National University
 * 
 * This file is part of Australian National University Orcid Updater.
 * 
 * Australian National University Orcid Updater is free software: you
 * can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package au.edu.anu.orcid.display;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Viewable;
import org.orcid.ns.orcid.OrcidMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.auth.orcid.AccessToken;
import au.edu.anu.orcid.auth.orcid.OAuthAuthenticator;
import au.edu.anu.orcid.auth.orcid.OAuthConstants;
import au.edu.anu.orcid.auth.orcid.OAuthException;
import au.edu.anu.orcid.auth.orcid.OrcidException;
import au.edu.anu.orcid.db.model.Person;
import au.edu.anu.orcid.process.retrieve.IdObtainer;
import au.edu.anu.orcid.security.permission.PermissionService;

/**
 * <p>RecordIdResource</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Classes to display a page based on the persons database identifier</p>
 *
 * @author Genevieve Turner
 *
 */
@Path("/id")
public class RecordIdResource {
	static final Logger LOGGER = LoggerFactory.getLogger(RecordIdResource.class);
	
	@Inject
	IdObtainer idObtainer;
	
	@Inject
	PermissionService permissionService;
	
	/**
	 * Get the page that displays the users information
	 * 
	 * @param id The identifier
	 * @return The page
	 */
	@GET
	@Path("/{id}")
	public Response getPage(@PathParam("id") Long id) {
		LOGGER.info("Attempting to find record for: {}", id);
		//Person person = permissionService.getPerson(id);
		Person person = idObtainer.getPerson(id);
		if (person == null) {
			throw new NotFoundException();
		}
		permissionService.checkPerson(person);
		OrcidMessage message = idObtainer.getFullOrcidProfile(id);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", message);
		model.put("orcid",  person.getOrcid());
		model.put("extid", person.getUid());
		Viewable viewable =  new Viewable("page.jsp", model);
		return Response.ok(viewable).build();
	}
	
	/**
	 * Create the record in ORCID
	 * 
	 * @param id The id of the person to create the record for
	 * @param action The action to perform
	 * @param uriInfo The uri information
	 * @return The response
	 * @throws OAuthException
	 */
	@GET
	@Path("/{id}/export/orcid/create")
	public Response beginRecordUpdateRequest(@PathParam("id") Long id, @QueryParam("action") String action
			, @Context UriInfo uriInfo) throws OAuthException {
		Person person = idObtainer.getPerson(id);
		if (person == null) {
			throw new NotFoundException();
		}
		if ("find".equals(action)) {
			URI createURI = getImportOrcidIdURI(id, uriInfo);
			OAuthAuthenticator auth = new OAuthAuthenticator();
			URI codeRequestURI = auth.getAuthorizationCodeRequestUri(OAuthConstants.PROFILE_READ, createURI.toString());

			return Response.seeOther(codeRequestURI).build();
		}
		else if ("create".equals(action)) {
			OAuthAuthenticator auth = new OAuthAuthenticator();

			LOGGER.info("User needs to be created in orcid: {}", person.getUid());
			OrcidMessage message = idObtainer.getFullOrcidProfile(id);
			String orcid = auth.createOrcid(message);
			person.setOrcid(orcid);
			idObtainer.updatePerson(person);
			
			return Response.seeOther(getPageURI(id, uriInfo)).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	/**
	 * Get the orcid from the authorization code
	 * 
	 * @param id The persons id
	 * @param authorizationCode The authorization code
	 * @param uriInfo The URI information
	 * @return The response
	 * @throws OAuthException
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{id}/export/orcid/create/process")
	public Response updateOrcid(@PathParam("id") Long id, @QueryParam("code") String authorizationCode, @Context UriInfo uriInfo) throws OAuthException {
		Person person = idObtainer.getPerson(id);
		if (person == null) {
			throw new NotFoundException();
		}
		LOGGER.debug("In process add works");
		OAuthAuthenticator auth = new OAuthAuthenticator();
		AccessToken token = auth.getAccessTokenFromAuthorizationCode(authorizationCode);
		if (token.getOrcid() != null) {
			LOGGER.info("Orcid {} for User {}", token.getOrcid(), id);
			person.setOrcid(token.getOrcid());
			idObtainer.updatePerson(person);
		}
		return Response.seeOther(getPageURI(id, uriInfo)).build();
	}
	
	/**
	 * Get the the URI to return to when attempting to find a users ORCID
	 * 
	 * @param uid The persons university id
	 * @param uriInfo The URI information
	 * @return The generated URI
	 */
	private URI getImportOrcidIdURI(Long id, UriInfo uriInfo) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("uid");
		builder.path(id.toString());
		builder.path("export").path("orcid").path("create/process");
		return builder.build();
	}
	
	/**
	 * Begin the process of sending works to ORCID by redirecting to get an authorization code
	 * 
	 * @param id The id of the person to add works to
	 * @param uriInfo The URI information
	 * @return The response
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{id}/export/orcid/add-works")
	public Response updateWorks(@PathParam("id") Long id, @Context UriInfo uriInfo) {
		LOGGER.debug("In add works");
		Person person = idObtainer.getPerson(id);
		if (person == null) {
			throw new NotFoundException();
		}
		URI getRedirectURI = getUpdateRedirectURI(id, uriInfo, "update-works/process");
		LOGGER.info("Redirect URI {}", getRedirectURI.toString());
		OAuthAuthenticator auth = new OAuthAuthenticator();
		URI codeRequestURI = auth.getAuthorizationCodeRequestUri(OAuthConstants.WORKS_CREATE, getRedirectURI.toString());
		
		LOGGER.info("URI to go to: {}", codeRequestURI);
		return Response.seeOther(codeRequestURI).build();
	}
	
	/**
	 * Use the authorization code and add works to the persons ORCID profile
	 * 
	 * @param id The id of the person
	 * @param authorizationCode The authorization code
	 * @param uriInfo The URI information
	 * @return The response
	 * @throws OAuthException
	 * @throws OrcidException
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/add-works/process")
	public Response addWorks(@PathParam("id") Long id, @QueryParam("code") String authorizationCode, @Context UriInfo uriInfo)
			 throws OAuthException, OrcidException {
		LOGGER.debug("In process add works");
		Person person = idObtainer.getPerson(id);
		if (person == null) {
			throw new NotFoundException();
		}
		OAuthAuthenticator auth = new OAuthAuthenticator();
		AccessToken token = auth.getAccessTokenFromAuthorizationCode(authorizationCode);
		OrcidMessage message = idObtainer.getOrcidWorks(id);
		LOGGER.debug("Adding Works");
		auth.updateWorks(token, message);
		LOGGER.debug("Works added");
		return Response.seeOther(getPageURI(id, uriInfo)).build();
	}
	
	/**
	 * Begin the process of sending works to ORCID by redirecting to get an authorization code
	 * 
	 * @param id The id of the person to add works to
	 * @param uriInfo The URI information
	 * @return The response
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{id}/export/orcid/update-works")
	public Response addWorks(@PathParam("id") Long id, @Context UriInfo uriInfo) {
		LOGGER.debug("In update works");
		Person person = idObtainer.getPerson(id);
		if (person == null) {
			throw new NotFoundException();
		}
		URI getRedirectURI = getUpdateRedirectURI(id, uriInfo, "update-works/process");
		LOGGER.info("Redirect URI {}", getRedirectURI.toString());
		OAuthAuthenticator auth = new OAuthAuthenticator();
		URI codeRequestURI = auth.getAuthorizationCodeRequestUri(OAuthConstants.WORKS_READ + " " + OAuthConstants.WORKS_UPDATE, getRedirectURI.toString());
		
		LOGGER.info("URI to go to: {}", codeRequestURI);
		return Response.seeOther(codeRequestURI).build();
	}
	
	/**
	 * Use the authorization code and update works to the persons ORCID profile
	 * 
	 * @param id The id of the person
	 * @param authorizationCode The authorization code
	 * @param uriInfo The URI information
	 * @return The response
	 * @throws OAuthException
	 * @throws OrcidException
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/update-works/process")
	public Response updateWorks(@PathParam("id") Long id, @QueryParam("code") String authorizationCode, @Context UriInfo uriInfo)
			 throws OAuthException, OrcidException {
		LOGGER.debug("In process update works");
		Person person = idObtainer.getPerson(id);
		if (person == null) {
			throw new NotFoundException();
		}
		OAuthAuthenticator auth = new OAuthAuthenticator();
		AccessToken token = auth.getAccessTokenFromAuthorizationCode(authorizationCode);
		OrcidMessage message = idObtainer.getOrcidWorks(id);
		LOGGER.debug("Updating Works");
		auth.addWorks(token, message);
		LOGGER.debug("Works added");
		return Response.seeOther(getPageURI(id, uriInfo)).build();
	}
	
	/**
	 * Get the URI for the page that displays the information about the person
	 * 
	 * @param id The id of the person
	 * @param uriInfo  The URI information
	 * @return The URI
	 */
	private URI getPageURI(Long id, UriInfo uriInfo) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("id");
		builder.path(id.toString());
		return builder.build();
	}
	
	/**
	 * Get the URI to process the adding of works
	 * 
	 * @param id The id of the person
	 * @param uriInfo The URI information
	 * @return The URI
	 */
	private URI getUpdateRedirectURI(Long id, UriInfo uriInfo, String extraPath) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("id");
		builder.path(id.toString());
		builder.path("export").path("orcid").path(extraPath);
		
		return builder.build();
	}
	
	/**
	 * Import a users record
	 * 
	 * @param id The id of the person
	 * @param uriInfo The URI information
	 * @return The response
	 */
	@GET
	@Path("/{id}/import")
	public Response updatePerson(@PathParam("id") Long id, @Context UriInfo uriInfo) {
		idObtainer.fetchPerson(id);
		return Response.seeOther(getPageURI(id, uriInfo)).build();
	}
	
}
