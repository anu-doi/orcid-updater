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

import org.glassfish.jersey.server.mvc.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import au.edu.anu.orcid.auth.orcid.OAuthException;
import au.edu.anu.orcid.auth.orcid.OrcidException;
import au.edu.anu.orcid.process.retrieve.IdObtainer;
import au.edu.anu.orcid.process.retrieve.NoRecordException;
import au.edu.anu.orcid.security.permission.PermissionService;

/**
 * <p>RecordIdResource</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Classes to display a page based on the persons database identifier</p>
 * <p>Resource that displays information about a person and performs actions in regards to Orcid based on
 * their database identifier</p>
 *
 * @author Genevieve Turner
 *
 */
@Component
@Scope("request")
@Path("/id")
@Template
public class RecordIdResource extends AbstractRecordResource<Long> {
	static final Logger LOGGER = LoggerFactory.getLogger(RecordIdResource.class);
	
	@Inject
	IdObtainer idObtainer;
	
	@Inject
	PermissionService permissionService;
	
	/**
	 * <p>Get the page that displays the users information</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/id/{id}</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
	 * 
	 * @param id The identifier
	 * @return The page
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{id}")
	public Response getPage(@PathParam("id") Long id) throws NoRecordException {
		LOGGER.info("Attempting to find record for: {}", id);
		return super.getPage(id, idObtainer);
	}
	
	/**
	 * <p>Create the record in ORCID</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/id/{id}/export/orcid/create?action={action}</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
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
			, @Context UriInfo uriInfo) throws OAuthException, NoRecordException {
		return super.beginRecordUpdateRequest(id, idObtainer, action, uriInfo);
	}
	
	/**
	 * <p>Get the orcid from the authorization code</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/id/{id}/export/orcid/create/process</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
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
	public Response updateOrcid(@PathParam("id") Long id, @QueryParam("code") String authorizationCode
			, @Context UriInfo uriInfo) throws OAuthException, NoRecordException {
		LOGGER.debug("In process add works");
		return super.updateOrcid(id, idObtainer, authorizationCode, uriInfo);
	}
	
	/**
	 * Get the the URI to return to when attempting to find a users ORCID
	 * 
	 * @param id The persons university id
	 * @param uriInfo The URI information
	 * @return The generated URI
	 */
	protected URI getImportOrcidIdURI(Long id, UriInfo uriInfo) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("id");
		builder.path(id.toString());
		builder.path("export").path("orcid").path("create/process");
		return builder.build();
	}
	
	/**
	 * <p>Begin the process of sending works to ORCID by redirecting to get an authorization code</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/id/{id}/export/orcid/add-works</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
	 * 
	 * @param id The id of the person to add works to
	 * @param uriInfo The URI information
	 * @return The response
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{id}/export/orcid/add-works")
	public Response addWorks(@PathParam("id") Long id, @Context UriInfo uriInfo) throws NoRecordException {
		LOGGER.debug("Beginning the process for adding works...");
		return super.addWorks(id, idObtainer, uriInfo);
	}
	
	/**
	 * <p>Use the authorization code and add works to the persons ORCID profile</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/id/{id}/export/orcid/add-works/process?code={code}</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
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
	@Path("/{id}/export/orcid/add-works/process")
	public Response addWorks(@PathParam("id") Long id, @QueryParam("code") String authorizationCode, @Context UriInfo uriInfo)
			 throws OAuthException, OrcidException, NoRecordException {
		return super.addWorks(id, idObtainer, authorizationCode, uriInfo);
	}
	
	/**
	 * <p>Begin the process of sending works to ORCID by redirecting to get an authorization code</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/id/{id}/orcid/update-works</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
	 * 
	 * @param id The id of the person to add works to
	 * @param uriInfo The URI information
	 * @return The response
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{id}/export/orcid/update-works")
	public Response updateWorks(@PathParam("id") Long id, @Context UriInfo uriInfo) throws NoRecordException {
		LOGGER.debug("Beginning the process to update works...");
		return super.updateWorks(id, idObtainer, uriInfo);
	}
	
	/**
	 * <p>Use the authorization code and update works to the persons ORCID profile</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/id/{id}/export/orcid/update-works/process?code={code}</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
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
	@Path("/{id}/export/orcid/update-works/process")
	public Response updateWorks(@PathParam("id") Long id, @QueryParam("code") String authorizationCode, @Context UriInfo uriInfo)
			 throws OAuthException, OrcidException, NoRecordException {
		LOGGER.debug("Attempting to add works...");
		return super.updateWorks(id, idObtainer, authorizationCode, uriInfo);
	}
	
	/**
	 * Get the URI for the page that displays the information about the person
	 * 
	 * @param id The id of the person
	 * @param uriInfo  The URI information
	 * @return The URI
	 */
	protected URI getPageURI(Long id, UriInfo uriInfo) {
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
	protected URI getUpdateRedirectURI(Long id, UriInfo uriInfo, String extraPath) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("id");
		builder.path(id.toString());
		builder.path("export").path("orcid").path(extraPath);
		
		return builder.build();
	}
	
	/**
	 * <p>Import a users record</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/uid/{uid}/import</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
	 * 
	 * @param id The id of the person
	 * @param uriInfo The URI information
	 * @return The response
	 */
	@GET
	@Path("/{id}/import")
	public Response updatePerson(@PathParam("id") Long id, @Context UriInfo uriInfo) {
		LOGGER.debug("Importing record for id: {}", id);
		return super.updatePerson(id, idObtainer, uriInfo);
	}
	
}
