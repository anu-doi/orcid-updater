package au.edu.anu.orcid.auth.orcid;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientProperties;
import org.orcid.ns.orcid.OrcidMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.util.PropertyLoader;

public class OAuthAuthenticator {
	static final Logger LOGGER = LoggerFactory.getLogger(OAuthAuthenticator.class);
	private static Properties orcidProperties_ = PropertyLoader.loadProperties("orcid.properties");
	private static final String orcidMatchUrl = orcidProperties_.getProperty("orcid.api.uri") + "/([^/]*)/orcid-profile";
	private static final String clientId_ = orcidProperties_.getProperty("orcid.auth.clientId");
	private static final String clientSecret_ = orcidProperties_.getProperty("orcid.auth.clientSecret");

	Pattern orcidUrlPattern = Pattern.compile(orcidMatchUrl);
	
	AccessToken createToken_;
	
	public OAuthAuthenticator() {
		LOGGER.info("Match URL: {}", orcidMatchUrl);
		LOGGER.info("Pattern URL: {}", orcidUrlPattern.toString());
		
	}
	
	private static Client client_;
	private Client getClient() {
		if (client_ == null) {
			ClientBuilder clientBuilder = ClientBuilder.newBuilder();
			if ("true".equals(orcidProperties_.getProperty("debug"))) {
				clientBuilder.hostnameVerifier(new Verifier());
			}
			
			client_ = clientBuilder.build();
		}
		return client_;
	}
	
	public String createOrcid(OrcidMessage message) throws OAuthException {
		if (message != null) {
			String authorizationStr = getCreateCredentials();
			LOGGER.info(authorizationStr);
			Client client = getClient();
			WebTarget target = client.target(orcidProperties_.getProperty("orcid.version.api.uri")).path("orcid-profile");
			LOGGER.info("Sending Create Record request to orcid");
			Response response = target.request().accept(MediaType.APPLICATION_XML).header("Authorization", authorizationStr).post(Entity.entity(message, "application/vdn.orcid+xml"));
			URI location = response.getLocation();
			if (location != null) {
				LOGGER.info("Location: {}", location.toString());
				// Search for a url equivalent to: https://api.sandbox-1.orcid.org/0000-0001-8025-637X/orcid-profile
				Matcher m = orcidUrlPattern.matcher(location.toString());
				boolean found = m.find();
				if (found) {
					String orcid = m.group(1);
					LOGGER.info("Orcid: {}", orcid);
					return orcid;
				}
			}
			else {
				String textResponse = response.readEntity(String.class);
				LOGGER.info("location is null. Response Status: {}", response.getStatus());
				LOGGER.info("Response: {}", textResponse);
				throw new OAuthException("Exception creating orcid");
			}
			String textResponse = response.readEntity(String.class);
			LOGGER.info("Response content: {}", textResponse);
		}
		else {
			throw new OAuthException("No message to send");
		}
		return null;
	}
	
	private String getCreateCredentials() throws OAuthException {
		LOGGER.info("In getCreateCredentials");
		if (createToken_ == null) {
			Client client = getClient();
			WebTarget target = client.target(orcidProperties_.getProperty("orcid.api.uri")).path("oauth").path("token");
			LOGGER.info("Target URL: {}", target.getUri().toString());
			Form form = new Form();
			form.param("grant_type", "client_credentials");
			form.param("client_id", clientId_);
			form.param("client_secret", clientSecret_);
			form.param("scope",  "/orcid-profile/create");
			
			Response response = target.request(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML).post(Entity.form(form));
			LOGGER.info("Status: {}", response.getStatus());
			if (response.getStatus() == 200) {
				createToken_ = response.readEntity(AccessToken.class);
			}
			else {
				LOGGER.info("Content Type: {}", response.getHeaders().get("Content-Type"));
				String value = response.readEntity(String.class);
				LOGGER.error("Response: {}", value);
				throw new OAuthException("Exception trying to authenticate with server");
			}
		}
		LOGGER.info("Create Authorization String: {}", createToken_.getTokenType() + " " + createToken_.getAccessToken());
		return createToken_.getTokenType() + " " + createToken_.getAccessToken();
	}
	
	public void addWorks(String orcid, OrcidMessage message, String authorizationCode) throws OAuthException, OrcidException {
		AccessToken token = getAccessTokenFromAuthorizationCode(authorizationCode);
		LOGGER.info("{} - {} - {}", token.getTokenType(), token.getAccessToken(), token.getRefreshToken());
		addWorks(token, message);
	}
	
	public void addWorks(AccessToken token, OrcidMessage message) throws OrcidException {
		if (message != null && token != null) {
			String authorizationStr =  getAuthorizationString(token);
			LOGGER.info("Authorization String: {}", authorizationStr);
			Client client = getClient();
			WebTarget target = client.target(orcidProperties_.getProperty("orcid.version.api.uri")).path(token.getOrcid()).path("orcid-works");
			LOGGER.info("Sending Create Record request to orcid");
			Response response = target.request().accept(MediaType.APPLICATION_XML).header("Authorization", authorizationStr).post(Entity.entity(message, "application/orcid+xml"));
			if (response.getStatus() == 201) {
				//Do nothing
				LOGGER.info("Works added successfully");
			}
			else {
				LOGGER.error("Error adding works. Status Code: {}", response.getStatus());
				String responseVal = response.readEntity(String.class);
				LOGGER.info("Response Value: {}", responseVal);
				throw new OrcidException("Exception adding works");
			}
		}
		else {
			LOGGER.info("Either the message or token is null");
			if (message == null) {
				LOGGER.info("Message is null");
			}
			if (token == null) {
				LOGGER.info("Token is null");
			}
		}
	}
	
	private String getAuthorizationString(AccessToken token) {
		return token.getTokenType() + " " + token.getAccessToken();
	}
	
	/**
	 * Please note that executing this will essentially remove all works and insert whatever is sent through via the message
	 * 
	 * @param token
	 * @param message
	 */
	public void updateWorks(AccessToken token, OrcidMessage message) throws OrcidException {
		//TODO fetch works from orcid as well!
		if (message != null && token != null) {

			String authorizationStr =  getAuthorizationString(token);
			LOGGER.info("Authorization String: {}", authorizationStr);
			Client client = getClient();
			WebTarget target = client.target(orcidProperties_.getProperty("orcid.version.api.uri")).path(token.getOrcid()).path("orcid-works");
			LOGGER.info("Sending Update Works request to orcid.  URL: {}", target.getUri());
			Response response = target.request().accept(MediaType.APPLICATION_XML).header("Authorization", authorizationStr).put(Entity.entity(message, "application/orcid+xml"));
			if (response.getStatus() == 200) {
				LOGGER.info("Status is: {}", response.getStatus());
				//Do nothing
				LOGGER.info("Works added successfully");
			}
			else {
				LOGGER.error("Error adding works. Status Code: {}", response.getStatus());
				String responseVal = response.readEntity(String.class);
				LOGGER.info("Response Value: {}", responseVal);
				throw new OrcidException("Exception updating works");
			}
		}
		else {
			LOGGER.info("Either the message or token is null");
			if (message == null) {
				LOGGER.info("Message is null");
			}
			if (token == null) {
				LOGGER.info("Token is null");
			}
		}
	}
	
	public AccessToken getAccessTokenFromAuthorizationCode(String authorizationCode) throws OAuthException {
		Client client = getClient();
		client.property(ClientProperties.CONNECT_TIMEOUT, 60000);
		WebTarget target = client.target(orcidProperties_.getProperty("orcid.api.uri")).path("oauth").path("token");
		
		Form form = new Form();
		form.param("client_id", clientId_);
		form.param("client_secret", clientSecret_);
		form.param("grant_type", "authorization_code");
		form.param("code", authorizationCode);
		
		LOGGER.info("Exchange authorization");
		Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
		LOGGER.info("Authorization Exchanged.  Processing Response");
		if (response.getStatus() == 200) {
			AccessToken accessToken = response.readEntity(AccessToken.class);
			return accessToken;
		}
		else {
			String responseVal = response.readEntity(String.class);
			LOGGER.error("Status: {}", response.getStatus());
			LOGGER.info("Response Value: {}", responseVal);
			throw new OAuthException("Token not generated");
		}
	}
	
	public URI getAuthorizationCodeRequestUri(String scope, String redirectURI) throws UnsupportedEncodingException {
		UriBuilder builder = UriBuilder.fromPath(orcidProperties_.getProperty("orcid.auth.uri")).path("oauth").path("authorize");
		builder = builder. queryParam("client_id", clientId_);
		builder = builder.queryParam("response_type", "code");
		//String createWorks = URLEncoder.encode("/orcid-works/create", "UTF-8");
		//String createWorks = URLEncoder.encode(scope, "UTF-8");
		String createWorks = scope;
	//	builder = builder.queryParam("scope", "/orcid-works/create");
		builder = builder.queryParam("scope", createWorks);
	//	redirectURI = "https://dc7-dev2.anu.edu.au/";
	//	redirectURI = "https://developers.google.com/oauthplayground";
		//redirectURI = URLEncoder.encode(redirectURI, "UTF-8");
		//builder = builder.queryParam("redirect_uri", "https://developers.google.com/oauthplayground");
	//	builder = builder.queryParam("redirect_uri", "http://dc7-dev2.anu.edu.au/orcid");
		builder = builder.queryParam("redirect_uri", redirectURI);
		//builder = builder.queryParam("redirect_uri", "https://developers.google.com/oauthplayground");
		//builder = builder.queryParam("redirect_uri", redirectURI);
		return builder.build();
	}
	
}
