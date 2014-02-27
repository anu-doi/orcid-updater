package au.edu.anu.orcid.display;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Viewable;
import org.orcid.ns.orcid.OrcidMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.auth.orcid.OAuthAuthenticator;
import au.edu.anu.orcid.auth.orcid.OAuthConstants;
import au.edu.anu.orcid.auth.orcid.OAuthException;
import au.edu.anu.orcid.db.model.Person;
import au.edu.anu.orcid.retrieve.IdObtainer;

@Path("/id")
public class RecordIdResource {
	static final Logger LOGGER = LoggerFactory.getLogger(RecordIdResource.class);
	
	@Inject
	IdObtainer obtainer;
	
	@GET
	@Path("/{id}")
	public Response getPage(@PathParam("id") Long id) {
		LOGGER.info("Attempting to find record for: {}", id);
		OrcidMessage message = obtainer.getFullOrcidProfile(id);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", message);
		Viewable viewable =  new Viewable("page.jsp", model);
		return Response.ok(viewable).build();
	}
	
	@GET
	@Path("/{id}/export/orcid")
	public Response beginRecordUpdateRequest(@PathParam("id") Long id, @QueryParam("action") String action
			, @Context UriInfo uriInfo) throws OAuthException, UnsupportedEncodingException {
		Person person = obtainer.getPerson(id);
		OAuthAuthenticator auth = new OAuthAuthenticator();
		if (person.getOrcid() != null) {
			URI redirectURI = getUpdateRedirectURI(id, uriInfo);
			URI codeUrl = auth.getAuthorizationCodeRequestUri(OAuthConstants.WORKS_CREATE, redirectURI.toString());
			Response.seeOther(codeUrl);
		}
		else {
			OrcidMessage message = obtainer.getFullOrcidProfile(id);
			String orcid = auth.createOrcid(message);
			person.setOrcid(orcid);
			obtainer.updatePerson(person);
		}
		return getPage(id);
	}
	
	private URI getPageURI(Long id, UriInfo uriInfo) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("uid");
		builder.path(id.toString());
		return builder.build();
	}
	
	private URI getUpdateRedirectURI(Long id, UriInfo uriInfo) {
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri());
		builder.path("uid");
		builder.path(id.toString());
		LOGGER.info("URI: {}", builder.build().toString());
		
		return null;
	}
	
	@GET
	@Path("/{id}/import")
	public Response updatePerson(@PathParam("id") Long id) {
		obtainer.fetchPerson(id);
		return getPage(id);
	}
	
}
