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
package au.edu.anu.orcid.auth.orcid;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
import javax.xml.bind.JAXBElement;

import org.glassfish.jersey.client.ClientProperties;
import org.orcid.ns.orcid.OrcidActivities;
import org.orcid.ns.orcid.OrcidMessage;
import org.orcid.ns.orcid.OrcidWork;
import org.orcid.ns.orcid.OrcidWorks;
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
	
	private static Client client_;
	
	/**
	 * Constructor
	 */
	public OAuthAuthenticator() {
		LOGGER.debug("Match URL: {}", orcidMatchUrl);
		LOGGER.debug("Pattern URL: {}", orcidUrlPattern.toString());
	}
	
	/**
	 * Get the client object.
	 * 
	 * @return The client
	 */
	private Client getClient() {
		if (client_ == null) {
			ClientBuilder clientBuilder = ClientBuilder.newBuilder();
			// Ignore the hsotname if debug is set to true.
			if ("true".equals(orcidProperties_.getProperty("debug"))) {
				clientBuilder.hostnameVerifier(new Verifier());
			}
			
			client_ = clientBuilder.build();
		}
		return client_;
	}
	
	/**
	 * Create an orcid
	 * 
	 * @param message The message that will be sent to orcid to create the record
	 * @return The orcid id
	 * @throws OAuthException
	 */
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
	
	/**
	 * Get the create credentials
	 * 
	 * @return The create credentials
	 * @throws OAuthException
	 */
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
	
	/**
	 * Add new works to the users list of publications in orcid
	 * 
	 * @param orcid The orcid id
	 * @param message The message to add the works
	 * @param authorizationCode The authorization code
	 * @throws OAuthException
	 * @throws OrcidException
	 */
	public void addWorks(String orcid, OrcidMessage message, String authorizationCode) throws OAuthException, OrcidException {
		AccessToken token = getAccessTokenFromAuthorizationCode(authorizationCode);
		LOGGER.debug("{} - {} - {}", token.getTokenType(), token.getAccessToken(), token.getRefreshToken());
		addWorks(token, message);
	}
	
	/**
	 * Add new works to the users list of publications in orcid
	 * 
	 * @param token The access token
	 * @param message The message to add the works
	 * @throws OrcidException
	 */
	public void addWorks(AccessToken token, OrcidMessage message) throws OrcidException {
		//TODO potentially streamline some of what is happening here!
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
			generateNullMessageOrToken(message, token);
		}
	}
	
	/**
	 * Generate the authorization string to be added to headers
	 * 
	 * @param token The access token from which to generate the authorization string
	 * @return The authorizaiton string
	 */
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
		if (message != null && token != null) {
			//TODO potentially streamline some of what is happening here!
			String authorizationStr =  getAuthorizationString(token);
			LOGGER.info("Authorization String: {}", authorizationStr);
			Client client = getClient();
			
			WebTarget target = client.target(orcidProperties_.getProperty("orcid.version.api.uri")).path(token.getOrcid()).path("orcid-works");
			Response response = target.request().accept(MediaType.APPLICATION_XML).header("Authorization", authorizationStr).get();
			if (response.getStatus() == 200) {
				OrcidMessage readWorksMessage = response.readEntity(OrcidMessage.class);
				processReadWorksMessage(message, readWorksMessage);
			}
			else {
				throw new OrcidException("Unable to read users works");
			}
			
			LOGGER.info("Sending Update Works request to orcid.  URL: {}", target.getUri());
			response = target.request().accept(MediaType.APPLICATION_XML).header("Authorization", authorizationStr).put(Entity.entity(message, "application/orcid+xml"));
			if (response.getStatus() == 200) {
				//Do nothing
				LOGGER.info("Works updated successfully");
			}
			else {
				LOGGER.error("Error adding works. Status Code: {}", response.getStatus());
				String responseVal = response.readEntity(String.class);
				LOGGER.info("Response Value: {}", responseVal);
				throw new OrcidException("Exception updating works");
			}
		}
		else {
			generateNullMessageOrToken(message, token);
		}
	}
	
	/**
	 * Adds works that have been read from Orcid to the message to send through to update
	 * 
	 * @param message The orcid message to send to orcid
	 * @param readWorksMessage The orcid message read from orcid
	 */
	private void processReadWorksMessage(OrcidMessage message, OrcidMessage readWorksMessage) {
		OrcidActivities activities = readWorksMessage.getOrcidProfile().getOrcidActivities();
		OrcidWorks readOrcidWorks = activities.getOrcidWorks();
		if (readOrcidWorks != null) {
			List<OrcidWork> worksToAdd = new ArrayList<OrcidWork>();
			for (OrcidWork work : readOrcidWorks.getOrcidWork()) {
				List<JAXBElement<String>> sourceVals = work.getWorkSource().getContent();
				for (JAXBElement<String> element : sourceVals) {
					if ("path".equals(element.getName().getLocalPart())) {
						if (!clientId_.equals(element.getValue())) {
							worksToAdd.add(work);
						}
					}
				}
			}
			LOGGER.debug("Number of works to add: {}", worksToAdd.size());
			message.getOrcidProfile().getOrcidActivities().getOrcidWorks().getOrcidWork().addAll(worksToAdd);
		}
	}
	
	/**
	 * Generate an exception because either the Orcid Message or the Access Token objects are null.
	 * 
	 * @param message The Orcid Message
	 * @param token The Acces Token
	 * @throws OrcidException
	 */
	private void generateNullMessageOrToken(OrcidMessage message, AccessToken token) throws OrcidException {
		LOGGER.info("Either the message or token is null");
		if (message == null && token == null) {
			throw new OrcidException("Orcid Message and Access Token are null");
		}
		if (message == null) {
			throw new OrcidException("Orcid Message is null");
		}
		if (token == null) {
			throw new OrcidException("Access Token is null");
		}
	}
	
	/**
	 * Retrieve the access token from orcid
	 * 
	 * @param authorizationCode The authorization code to use to generate the access token
	 * @return The access token
	 * @throws OAuthException
	 */
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
	
	/**
	 * Get the uri to redirect to so that an authorization code can be retrieved
	 * 
	 * @param scope The scope to generate a url for
	 * @param redirectURI The uri to redirect to after the user has authenticated via oauth
	 * @return The uri
	 * @throws UnsupportedEncodingException
	 */
	public URI getAuthorizationCodeRequestUri(String scope, String redirectURI) {
		UriBuilder builder = UriBuilder.fromPath(orcidProperties_.getProperty("orcid.auth.uri")).path("oauth").path("authorize");
		builder = builder. queryParam("client_id", clientId_);
		builder = builder.queryParam("response_type", "code");
		builder = builder.queryParam("scope", scope);
		builder = builder.queryParam("redirect_uri", redirectURI);
		return builder.build();
	}
	
}
