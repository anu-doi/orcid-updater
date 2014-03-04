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
import au.edu.anu.orcid.process.retrieve.UidObtainer;

/**
 * <p>RecordUidResource</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Classes to display a page based on the persons university identifier</p>
 *
 * @author Genevieve Turner
 *
 */
@Path("/uid")
public class RecordUidResource {
	static final Logger LOGGER = LoggerFactory.getLogger(RecordUidResource.class);
	
	@Inject
	UidObtainer obtainer;
	
	/**
	 * Get the page that displays the users information
	 * 
	 * @param uid The university id
	 * @return The response
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}")
	public Response getPage(@PathParam("uid") String uid) {
		LOGGER.info("Attempting to find record for: {}", uid);
		Person person = obtainer.getPerson(uid);
		OrcidMessage message = obtainer.getFullOrcidProfile(uid);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", message);
		model.put("orcid", person.getOrcid());
		model.put("extid", person.getUid());
		Viewable viewable =  new Viewable("page.jsp", model);
		return Response.ok(viewable).build();
	}
	
	/**
	 * Create the record in ORCID
	 * 
	 * @param uid The persons university identifier
	 * @param action The action to perform.  Possiblities are 'find' and 'create'.
	 * @param uriInfo The uri information
	 * @return The response
	 * @throws OAuthException
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/create")
	public Response beginRecordUpdateRequest(@PathParam("uid") String uid, @QueryParam("action") String action
			, @Context UriInfo uriInfo) throws OAuthException {
		
		if ("find".equals(action)) {
			URI createURI = getImportOrcidIdURI(uid, uriInfo);
			OAuthAuthenticator auth = new OAuthAuthenticator();
			URI codeRequestURI = auth.getAuthorizationCodeRequestUri(OAuthConstants.PROFILE_READ, createURI.toString());

			return Response.seeOther(codeRequestURI).build();
		}
		else if ("create".equals(action)) {
			Person person = obtainer.getPerson(uid);
			OAuthAuthenticator auth = new OAuthAuthenticator();

			LOGGER.info("User needs to be created in orcid: {}", person.getUid());
			OrcidMessage message = obtainer.getFullOrcidProfile(uid);
			String orcid = auth.createOrcid(message);
			person.setOrcid(orcid);
			obtainer.updatePerson(person);
			
			return Response.seeOther(getPageURI(uid, uriInfo)).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	/**
	 * Get the orcid from the authorization code
	 * 
	 * @param uid The persons university id
	 * @param authorizationCode The authorization code
	 * @param uriInfo The URI information
	 * @return The response
	 * @throws OAuthException
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/create/process")
	public Response updateOrcid(@PathParam("uid") String uid, @QueryParam("code") String authorizationCode, @Context UriInfo uriInfo) throws OAuthException {

		LOGGER.debug("In process add works");
		OAuthAuthenticator auth = new OAuthAuthenticator();
		AccessToken token = auth.getAccessTokenFromAuthorizationCode(authorizationCode);
		if (token.getOrcid() != null) {
			LOGGER.info("Orcid {} for User {}", token.getOrcid(), uid);
			Person person = obtainer.getPerson(uid);
			person.setOrcid(token.getOrcid());
			obtainer.updatePerson(person);
		}
		return Response.seeOther(getPageURI(uid, uriInfo)).build();
	}
	
	/**
	 * Get the the URI to return to when attempting to find a users ORCID
	 * 
	 * @param uid The persons university id
	 * @param uriInfo The URI information
	 * @return The generated URI
	 */
	private URI getImportOrcidIdURI(String uid, UriInfo uriInfo) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("uid");
		builder.path(uid);
		builder.path("export").path("orcid").path("create/process");
		return builder.build();
	}
	
	/**
	 * Begin the process of sending works to ORCID by redirecting to get an authorization code
	 * 
	 * @param uid The persons university identifier
	 * @param uriInfo The URI information
	 * @return The response
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/add-works")
	public Response addWorks(@PathParam("uid") String uid, @Context UriInfo uriInfo) {
		LOGGER.debug("In add works");
		URI getRedirectURI = getUpdateRedirectURI(uid, uriInfo, "add-works/process");
		LOGGER.info("Redirect URI {}", getRedirectURI.toString());
		OAuthAuthenticator auth = new OAuthAuthenticator();
		URI codeRequestURI = auth.getAuthorizationCodeRequestUri(OAuthConstants.WORKS_CREATE, getRedirectURI.toString());
		
		LOGGER.info("URI to go to: {}", codeRequestURI);
		return Response.seeOther(codeRequestURI).build();
	}
	
	/**
	 * Use the authorization code and add works to the persons ORCID profile
	 * 
	 * @param uid The persons university identifier
	 * @param authorizationCode The authorization code
	 * @param uriInfo The URI information
	 * @return The response
	 * @throws OAuthException
	 * @throws OrcidException
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/add-works/process")
	public Response addWorks(@PathParam("uid") String uid, @QueryParam("code") String authorizationCode, @Context UriInfo uriInfo)
			 throws OAuthException, OrcidException {
		LOGGER.debug("In process add works");
		OAuthAuthenticator auth = new OAuthAuthenticator();
		AccessToken token = auth.getAccessTokenFromAuthorizationCode(authorizationCode);
		OrcidMessage message = obtainer.getOrcidWorks(uid);
		LOGGER.debug("Adding Works");
		auth.addWorks(token, message);
		LOGGER.debug("Works added");
		return Response.seeOther(getPageURI(uid, uriInfo)).build();
	}
	
	/**
	 * Begin the process of sending works to ORCID by redirecting to get an authorization code
	 * 
	 * @param uid The persons university identifier
	 * @param uriInfo The URI information
	 * @return The response
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/update-works")
	public Response updateWorks(@PathParam("uid") String uid, @Context UriInfo uriInfo) {
		LOGGER.debug("In update works");
		URI getRedirectURI = getUpdateRedirectURI(uid, uriInfo, "update-works/process");
		LOGGER.info("Redirect URI {}", getRedirectURI.toString());
		OAuthAuthenticator auth = new OAuthAuthenticator();
		URI codeRequestURI = auth.getAuthorizationCodeRequestUri(OAuthConstants.WORKS_READ + " " + OAuthConstants.WORKS_UPDATE, getRedirectURI.toString());
		
		LOGGER.info("URI to go to: {}", codeRequestURI);
		return Response.seeOther(codeRequestURI).build();
	}
	
	/**
	 * Use the authorization code and update works to the persons ORCID profile
	 * 
	 * @param uid The persons university identifier
	 * @param authorizationCode The authorization code
	 * @param uriInfo The URI information
	 * @return The response
	 * @throws OAuthException
	 * @throws OrcidException
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/update-works/process")
	public Response updateWorks(@PathParam("uid") String uid, @QueryParam("code") String authorizationCode, @Context UriInfo uriInfo)
			 throws OAuthException, OrcidException {
		LOGGER.debug("In process update works");
		OAuthAuthenticator auth = new OAuthAuthenticator();
		AccessToken token = auth.getAccessTokenFromAuthorizationCode(authorizationCode);
		OrcidMessage message = obtainer.getOrcidWorks(uid);
		LOGGER.debug("Updating Works");
		auth.updateWorks(token, message);
		LOGGER.debug("Works added");
		return Response.seeOther(getPageURI(uid, uriInfo)).build();
	}
	
	/**
	 * Get the URI for the page that displays the information about the person
	 * 
	 * @param uid The persons university identifier
	 * @param uriInfo The URI information
	 * @return The URI
	 */
	private URI getPageURI(String uid, UriInfo uriInfo) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("uid");
		builder.path(uid);
		return builder.build();
	}
	
	/**
	 * Get the URI to process the adding of works
	 * 
	 * @param uid The persons university identifier
	 * @param uriInfo The URI information
	 * @return The URI
	 */
	private URI getUpdateRedirectURI(String uid, UriInfo uriInfo, String extraPath) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("uid");
		builder.path(uid);
		//builder.path("export").path("orcid").path("add-works/process");
		builder.path("export").path("orcid").path(extraPath);
		
		return builder.build();
	}
	
	/**
	 * Import a users record
	 * 
	 * @param uid The persons university identifier
	 * @param uriInfo The URI information
	 * @return The response
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/import")
	public Response updatePerson(@PathParam("uid") String uid, @Context UriInfo uriInfo) {
		obtainer.fetchPerson(uid);
		return Response.seeOther(getPageURI(uid, uriInfo)).build();
	}
	
}
