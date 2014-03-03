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

import java.io.UnsupportedEncodingException;
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
	IdObtainer obtainer;
	
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
		Person person = obtainer.getPerson(id);
		OrcidMessage message = obtainer.getFullOrcidProfile(id);
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
	@Path("/{id}/export/orcid")
	public Response beginRecordUpdateRequest(@PathParam("id") Long id, @QueryParam("action") String action
			, @Context UriInfo uriInfo) throws OAuthException {
		Person person = obtainer.getPerson(id);
		OAuthAuthenticator auth = new OAuthAuthenticator();
		if (person.getOrcid() != null) {
			LOGGER.info("User has an orcid: {}", person.getOrcid());
		}
		else {
			LOGGER.info("User needs to be created in orcid: {}", person.getUid());
			OrcidMessage message = obtainer.getFullOrcidProfile(id);
			String orcid = auth.createOrcid(message);
			person.setOrcid(orcid);
			obtainer.updatePerson(person);
		}
		return Response.seeOther(getPageURI(id, uriInfo)).build();
	}
	
	/**
	 * Begin the process of sending works to ORCID by redirecting to get an authorization code
	 * 
	 * @param id The id of the person to add works to
	 * @param uriInfo The URI information
	 * @return The response
	 * @throws UnsupportedEncodingException
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{id}/export/orcid/add-works")
	public Response updateWorks(@PathParam("id") Long id, @Context UriInfo uriInfo) {
		LOGGER.debug("In add works");
		URI getRedirectURI = getUpdateRedirectURI(id, uriInfo);
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
	public Response updateWorks(@PathParam("id") Long id, @QueryParam("code") String authorizationCode, @Context UriInfo uriInfo)
			 throws OAuthException, OrcidException {
		LOGGER.debug("In process add works");
		OAuthAuthenticator auth = new OAuthAuthenticator();
		AccessToken token = auth.getAccessTokenFromAuthorizationCode(authorizationCode);
		OrcidMessage message = obtainer.getOrcidWorks(id);
		LOGGER.debug("Adding Works");
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
	private URI getUpdateRedirectURI(Long id, UriInfo uriInfo) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("id");
		builder.path(id.toString());
		builder.path("export").path("orcid").path("add-works/process");
		
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
		obtainer.fetchPerson(id);
		return Response.seeOther(getPageURI(id, uriInfo)).build();
	}
	
}
