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
import au.edu.anu.orcid.process.retrieve.NoRecordException;
import au.edu.anu.orcid.process.retrieve.UidObtainer;
import au.edu.anu.orcid.security.permission.PermissionService;

/**
 * <p>RecordUidResource</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Resource that displays information about a person and performs actions in regards to Orcid based on
 * their institutional unique identifier</p>
 *
 * @author Genevieve Turner
 *
 */
@Component
@Scope("request")
@Path("/uid")
@Template
public class RecordUidResource extends AbstractRecordResource<String> {
	static final Logger LOGGER = LoggerFactory.getLogger(RecordUidResource.class);
	
	@Inject
	UidObtainer uidObtainer;
	
	@Inject
	PermissionService permissionService;
	
	/**
	 * <p>Get the page that displays the users information</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/uid/{uid}</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
	 * 
	 * @param uid The institutional unique identifier
	 * @return The response
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}")
	public Response getPage(@PathParam("uid") String uid) throws NoRecordException {
		LOGGER.info("Attempting to find record for: {}", uid);
		return super.getPage(uid, uidObtainer);
	}
	
	/**
	 * <p>Create the record in ORCiD</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/uid/{uid}/export/orcid/create?action={action}</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
	 * 
	 * @param uid The institutional unique identifier
	 * @param action The action to perform.  Possiblities are 'find' and 'create'.
	 * @param uriInfo The uri information
	 * @return The response
	 * @throws OAuthException
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/create")
	public Response beginRecordUpdateRequest(@PathParam("uid") String uid, @QueryParam("action") String action
			, @Context UriInfo uriInfo) throws OAuthException, NoRecordException {
		return super.beginRecordUpdateRequest(uid, uidObtainer, action, uriInfo);
	}
	
	/**
	 * <p>Get the orcid from the authorization code</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/uid/{uid}/export/orcid/create/process</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
	 * 
	 * @param uid The institutional unique identifier
	 * @param authorizationCode The authorization code
	 * @param uriInfo The URI information
	 * @return The response
	 * @throws OAuthException
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/create/process")
	public Response updateOrcid(@PathParam("uid") String uid, @QueryParam("code") String authorizationCode
			, @Context UriInfo uriInfo) throws OAuthException, NoRecordException {
		return super.updateOrcid(uid, uidObtainer, authorizationCode, uriInfo);
	}
	
	/**
	 * Get the the URI to return to when attempting to find a users ORCID
	 * 
	 * @param uid The institutional unique identifier
	 * @param uriInfo The URI information
	 * @return The generated URI
	 */
	protected URI getImportOrcidIdURI(String uid, UriInfo uriInfo) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("uid");
		builder.path(uid);
		builder.path("export").path("orcid").path("create/process");
		return builder.build();
	}
	/**
	 * <p>Begin the process of sending works to ORCID by redirecting to get an authorization code</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/uid/{uid}/export/orcid/add-works</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
	 * 
	 * @param uid The institutional unique identifier
	 * @param uriInfo The URI information
	 * @return The response
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/add-works")
	public Response addWorks(@PathParam("uid") String uid, @Context UriInfo uriInfo) throws NoRecordException {
		return super.addWorks(uid, uidObtainer, uriInfo);
	}
	
	/**
	 * <p>Use the authorization code and add works to the persons ORCID profile</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/uid/{uid}/export/orcid/add-works/process?code={code}</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
	 * 
	 * @param uid The institutional unique identifier
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
			 throws OAuthException, OrcidException, NoRecordException {
		return super.addWorks(uid, uidObtainer, authorizationCode, uriInfo);
	}
	
	/**
	 * <p>Begin the process of sending works to ORCID by redirecting to get an authorization code</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/uid/{uid}/orcid/update-works</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
	 * 
	 * @param uid The institutional unique identifier
	 * @param uriInfo The URI information
	 * @return The response
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/update-works")
	public Response updateWorks(@PathParam("uid") String uid, @Context UriInfo uriInfo) throws NoRecordException {
		return super.updateWorks(uid, uidObtainer, uriInfo);
	}
	
	/**
	 * <p>Use the authorization code and update works to the persons ORCID profile</p>
	 * <p><b>URL Syntax</b></p>
	 * <p>/rest/uid/{uid}/export/orcid/update-works/process?code={code}</p>
	 * <p><b>Method</b></p>
	 * <p>GET</p>
	 * 
	 * @param uid The institutional unique identifier
	 * @param authorizationCode The authorization code
	 * @param uriInfo The URI information
	 * @return The response
	 * @throws OAuthException
	 * @throws OrcidException
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/update-works/process")
	public Response updateWorks(@PathParam("uid") String uid, @QueryParam("code") String authorizationCode
			, @Context UriInfo uriInfo) throws OAuthException, OrcidException, NoRecordException {
		return super.updateWorks(uid, uidObtainer, authorizationCode, uriInfo);
	}
	
	/**
	 * Get the URI for the page that displays the information about the person
	 * 
	 * @param uid The institutional unique identifier
	 * @param uriInfo The URI information
	 * @return The URI
	 */
	protected URI getPageURI(String uid, UriInfo uriInfo) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("uid");
		builder.path(uid);
		return builder.build();
	}
	
	/**
	 * Get the URI to process the adding of works
	 * 
	 * @param uid The institutional unique identifier
	 * @param uriInfo The URI information
	 * @return The URI
	 */
	protected URI getUpdateRedirectURI(String uid, UriInfo uriInfo, String extraPath) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("uid");
		builder.path(uid);
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
	 * @param uid The institutional unique identifier
	 * @param uriInfo The URI information
	 * @return The response
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/import")
	public Response updatePerson(@PathParam("uid") String uid, @Context UriInfo uriInfo) {
		LOGGER.debug("Importing record for unique identifier: {}", uid);
		return super.updatePerson(uid, uidObtainer, uriInfo);
	}
	
}
