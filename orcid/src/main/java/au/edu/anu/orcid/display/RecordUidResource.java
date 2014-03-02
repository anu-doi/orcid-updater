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
import au.edu.anu.orcid.process.retrieve.UidObtainer;

@Path("/uid")
public class RecordUidResource {
	static final Logger LOGGER = LoggerFactory.getLogger(RecordUidResource.class);
	
	@Inject
	UidObtainer obtainer;
	
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
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/create")
	public Response beginRecordUpdateRequest(@PathParam("uid") String uid, @QueryParam("action") String action
			, @Context UriInfo uriInfo) throws OAuthException {
		
		Person person = obtainer.getPerson(uid);
		OAuthAuthenticator auth = new OAuthAuthenticator();
		if (person.getOrcid() != null) {
			LOGGER.info("User has an orcid: {}", person.getOrcid());
			//auth.getAuthorizationCodeRequestUri(getUpdateRedirectURI(uid, uriInfo).toString());
		}
		else {
			LOGGER.info("User needs to be created in orcid: {}", person.getUid());
			OrcidMessage message = obtainer.getFullOrcidProfile(uid);
			String orcid = auth.createOrcid(message);
			person.setOrcid(orcid);
			obtainer.updatePerson(person);
		}
		return Response.seeOther(getPageURI(uid, uriInfo)).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/export/orcid/add-works")
	public Response updateWorks(@PathParam("uid") String uid, @Context UriInfo uriInfo) throws UnsupportedEncodingException {
		LOGGER.info("In add works");
		URI getRedirectURI = getUpdateRedirectURI(uid, uriInfo);
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
	public Response updateWorks(@PathParam("uid") String uid, @QueryParam("code") String authorizationCode, @Context UriInfo uriInfo)
			 throws OAuthException, OrcidException {
		LOGGER.info("In process add works");
		OAuthAuthenticator auth = new OAuthAuthenticator();
		AccessToken token = auth.getAccessTokenFromAuthorizationCode(authorizationCode);
		//Person person = obtainer.getPerson(uid);
		OrcidMessage message = obtainer.getOrcidWorks(uid);
		//OrcidMessage message = person.
		LOGGER.info("Adding Works");
		auth.addWorks(token, message);
		//auth.addWorks(person.getOrcid(), message, authorizationCode);
		LOGGER.info("Works added");
		//auth.addWorks(orcid, message, authorizationCode);
		//return Response.ok("Made it to update works!").build();
		return Response.seeOther(getPageURI(uid, uriInfo)).build();
	}
	
	private URI getPageURI(String uid, UriInfo uriInfo) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("uid");
		builder.path(uid);
		return builder.build();
	}
	
	private URI getUpdateRedirectURI(String uid, UriInfo uriInfo) {
		//UriBuilder.
		//UriBuilder builder = UriBuilder.fromPath("test");
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("uid");
		builder.path(uid);
		builder.path("export").path("orcid").path("add-works/process");
		//UriBuilder.fromPath("test");
		//URI uri = 
		//LOGGER.info("URI: {}", builder.build().toString());
		//LOGGER.info("Absolute Path: {}, Base URI: {}", uriInfo.getAbsolutePath(), uriInfo.getBaseUri());
		
		return builder.build();
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{uid}/import")
	public Response updatePerson(@PathParam("uid") String uid, @Context UriInfo uriInfo) {
		obtainer.fetchPerson(uid);
		return Response.seeOther(getPageURI(uid, uriInfo)).build();
	//	return getPage(uid);
	}
	
}
