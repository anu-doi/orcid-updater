package au.edu.anu.orcid.display;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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
import au.edu.anu.orcid.process.retrieve.NoRecordException;
import au.edu.anu.orcid.process.retrieve.Obtainer;
import au.edu.anu.orcid.security.permission.PermissionService;

/**
 * <p>AbstractResource</p>
 *
 * <p>The Australian National University</p>
 *
 * <p></p>
 *
 * @author Genevieve Turner
 *
 * @param <T>
 */
public abstract class AbstractRecordResource<T> {
	static final Logger LOGGER = LoggerFactory.getLogger(AbstractRecordResource.class);
	
	@Inject
	PermissionService permissionService;
	
	/**
	 * Get the page that displays the users information
	 * 
	 * @param id The id to use
	 * @param obtainer The obtainer class to perform actions with
	 * @return The page
	 * @throws NoRecordException
	 */
	public Response getPage(T id, Obtainer<T> obtainer) throws NoRecordException {
		Person person = obtainer.getPerson(id);
		permissionService.checkPerson(person);
		OrcidMessage message = obtainer.getFullOrcidProfile(id);
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
	 * @param id The id to use
	 * @param obtainer The obtainer class to perform actions with
	 * @param action The action to perform
	 * @param uriInfo The uri infomration
	 * @return The response
	 * @throws OAuthException
	 * @throws NoRecordException
	 */
	public Response beginRecordUpdateRequest(T id, Obtainer<T> obtainer, String action, UriInfo uriInfo) 
			throws OAuthException, NoRecordException {
		Person person = obtainer.getPerson(id);
		permissionService.checkPerson(person);
		if ("find".equals(action)) {
			URI createURI = getImportOrcidIdURI(id, uriInfo);
			OAuthAuthenticator auth = new OAuthAuthenticator();
			URI codeRequestURI = auth.getAuthorizationCodeRequestUri(OAuthConstants.PROFILE_READ, createURI.toString());

			return Response.seeOther(codeRequestURI).build();
		}
		else if ("create".equals(action)) {
			OAuthAuthenticator auth = new OAuthAuthenticator();

			LOGGER.info("User needs to be created in orcid: {}", person.getUid());
			OrcidMessage message = obtainer.getFullOrcidProfile(id);
			String orcid = auth.createOrcid(message);
			person.setOrcid(orcid);
			obtainer.updatePerson(person);
			
			return Response.seeOther(getPageURI(id, uriInfo)).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	/**
	 * Get the ORCID from the authorization code
	 * 
	 * @param id The id to use
	 * @param obtainer The obtainer class to perform actions with
	 * @param authorizationCode The authorization code
	 * @param uriInfo The URI information
	 * @return The response
	 * @throws OAuthException
	 * @throws NoRecordException
	 */
	public Response updateOrcid(T id, Obtainer<T> obtainer, String authorizationCode
			, @Context UriInfo uriInfo) throws OAuthException, NoRecordException {
		LOGGER.debug("In process add works");
		Person person = obtainer.getPerson(id);
		permissionService.checkPerson(person);
		OAuthAuthenticator auth = new OAuthAuthenticator();
		AccessToken token = auth.getAccessTokenFromAuthorizationCode(authorizationCode);
		if (token.getOrcid() != null) {
			LOGGER.info("Orcid {} for User {}", token.getOrcid(), id);
			person.setOrcid(token.getOrcid());
			obtainer.updatePerson(person);
		}
		return Response.seeOther(getPageURI(id, uriInfo)).build();
	}

	/**
	 * Begin the process of sending works to ORCID by redirecting to get an authorization code
	 * 
	 * @param id The id to use
	 * @param obtainer The obtainer class to perform actions with
	 * @param uriInfo The URI information
	 * @return The response
	 * @throws NoRecordException
	 */
	public Response addWorks(T id, Obtainer<T> obtainer, UriInfo uriInfo) throws NoRecordException {
		LOGGER.debug("Beginning the process for adding works...");
		Person person = obtainer.getPerson(id);
		permissionService.checkPerson(person);
		URI getRedirectURI = getUpdateRedirectURI(id, uriInfo, "add-works/process");
		OAuthAuthenticator auth = new OAuthAuthenticator();
		URI codeRequestURI = auth.getAuthorizationCodeRequestUri(OAuthConstants.WORKS_CREATE, getRedirectURI.toString());
		
		return Response.seeOther(codeRequestURI).build();
	}
	
	/**
	 * Use the authorization code and add works to the persons ORCID profile
	 * 
	 * @param id The id to use
	 * @param obtainer The obtainer class to perform actions with
	 * @param authorizationCode The authorization code
	 * @param uriInfo THe URI information
	 * @return The response
	 * @throws OAuthException
	 * @throws OrcidException
	 * @throws NoRecordException
	 */
	public Response addWorks(T id, Obtainer<T> obtainer, String authorizationCode, UriInfo uriInfo)
			 throws OAuthException, OrcidException, NoRecordException {
		LOGGER.debug("Attempting to add works...");
		Person person = obtainer.getPerson(id);
		permissionService.checkPerson(person);
		OrcidMessage message = obtainer.getOrcidWorks(id);
		OAuthAuthenticator auth = new OAuthAuthenticator();
		AccessToken token = auth.getAccessTokenFromAuthorizationCode(authorizationCode);
		auth.addWorks(token, message);
		return Response.seeOther(getPageURI(id, uriInfo)).build();
	}
	
	/**
	 * Begin the process of sending works to ORCID by redirecting to get an authorization code
	 * 
	 * @param id The id to use
	 * @param obtainer The obtainer class to perform actions with
	 * @param uriInfo The URI information
	 * @return The response
	 * @throws NoRecordException
	 */
	public Response updateWorks(T id, Obtainer<T> obtainer, @Context UriInfo uriInfo) throws NoRecordException {
		LOGGER.debug("Beginning the process to update works...");
		Person person = obtainer.getPerson(id);
		permissionService.checkPerson(person);
		URI getRedirectURI = getUpdateRedirectURI(id, uriInfo, "update-works/process");
		OAuthAuthenticator auth = new OAuthAuthenticator();
		URI codeRequestURI = auth.getAuthorizationCodeRequestUri(OAuthConstants.WORKS_UPDATE, getRedirectURI.toString());
		
		return Response.seeOther(codeRequestURI).build();
	}
	
	/**
	 * Use the authorization code and update works to the persons ORCID profile
	 * 
	 * @param id The id to use
	 * @param obtainer The obtainer class to perform actions with
	 * @param authorizationCode The authorization code
	 * @param uriInfo THe URI information
	 * @return The response
	 * @throws OAuthException
	 * @throws OrcidException
	 * @throws NoRecordException
	 */
	public Response updateWorks(T id, Obtainer<T> obtainer, String authorizationCode, @Context UriInfo uriInfo) 
			throws OAuthException, OrcidException, NoRecordException {
		LOGGER.debug("Attempting to add works...");
		Person person = obtainer.getPerson(id);
		permissionService.checkPerson(person);
		OAuthAuthenticator auth = new OAuthAuthenticator();
		AccessToken token = auth.getAccessTokenFromAuthorizationCode(authorizationCode);
		OrcidMessage message = obtainer.getOrcidWorks(id);
		auth.updateWorks(token, message);
		return Response.seeOther(getPageURI(id, uriInfo)).build();
	}
	
	/**
	 * Import a users record
	 * 
	 * @param id The id to use
	 * @param obtainer The obtainer class to perform actions with
	 * @param uriInfo The URI information
	 * @return The response
	 */
	public Response updatePerson(T id, Obtainer<T> obtainer, UriInfo uriInfo) {
		obtainer.fetchPerson(id);
		return Response.seeOther(getPageURI(id, uriInfo)).build();
	}
	
	abstract protected URI getImportOrcidIdURI(T id, UriInfo uriInfo);
	
	abstract protected URI getUpdateRedirectURI(T id, UriInfo uriInfo, String extraPath);
	
	abstract protected URI getPageURI(T id, UriInfo uriInfo);
}
