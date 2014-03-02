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

@Path("/id")
public class RecordIdResource {
	static final Logger LOGGER = LoggerFactory.getLogger(RecordIdResource.class);
	
	@Inject
	IdObtainer obtainer;
	
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
	
	@GET
	@Path("/{id}/export/orcid")
	public Response beginRecordUpdateRequest(@PathParam("id") Long id, @QueryParam("action") String action
			, @Context UriInfo uriInfo) throws OAuthException {
		Person person = obtainer.getPerson(id);
		OAuthAuthenticator auth = new OAuthAuthenticator();
		if (person.getOrcid() != null) {
			LOGGER.info("User has an orcid: {}", person.getOrcid());
		//	URI redirectURI = getUpdateRedirectURI(id, uriInfo);
		//	URI codeUrl = auth.getAuthorizationCodeRequestUri(OAuthConstants.WORKS_CREATE, redirectURI.toString());
		//	Response.seeOther(codeUrl);
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
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{id}/export/orcid/add-works")
	public Response updateWorks(@PathParam("id") Long id, @Context UriInfo uriInfo) throws UnsupportedEncodingException {
		LOGGER.info("In add works");
		URI getRedirectURI = getUpdateRedirectURI(id, uriInfo);
		LOGGER.info("Redirect URI {}", getRedirectURI.toString());
		//auth.addWorks(orcid, message, authorizationCode);
		OAuthAuthenticator auth = new OAuthAuthenticator();
		URI codeRequestURI = auth.getAuthorizationCodeRequestUri(OAuthConstants.WORKS_CREATE, getRedirectURI.toString());
		
		LOGGER.info("URI to go to: {}", codeRequestURI);
		return Response.seeOther(codeRequestURI).build();
		//return Response.ok("Went to add works!").build();
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/add-works/process")
	public Response updateWorks(@PathParam("id") Long id, @QueryParam("code") String authorizationCode, @Context UriInfo uriInfo)
			 throws OAuthException, OrcidException {
		LOGGER.info("In process add works");
		OAuthAuthenticator auth = new OAuthAuthenticator();
		AccessToken token = auth.getAccessTokenFromAuthorizationCode(authorizationCode);
		//Person person = obtainer.getPerson(uid);
		OrcidMessage message = obtainer.getOrcidWorks(id);
		//OrcidMessage message = person.
		LOGGER.info("Adding Works");
		auth.addWorks(token, message);
		//auth.addWorks(person.getOrcid(), message, authorizationCode);
		LOGGER.info("Works added");
		//auth.addWorks(orcid, message, authorizationCode);
		//return Response.ok("Made it to update works!").build();
		return Response.seeOther(getPageURI(id, uriInfo)).build();
	}
	
	private URI getPageURI(Long id, UriInfo uriInfo) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("id");
		builder.path(id.toString());
		return builder.build();
	}
	
	private URI getUpdateRedirectURI(Long id, UriInfo uriInfo) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("id");
		builder.path(id.toString());
		builder.path("export").path("orcid").path("add-works/process");
		
		return builder.build();
	}
	
	@GET
	@Path("/{id}/import")
	public Response updatePerson(@PathParam("id") Long id, @Context UriInfo uriInfo) {
		obtainer.fetchPerson(id);
		return Response.seeOther(getPageURI(id, uriInfo)).build();
	}
	
}
