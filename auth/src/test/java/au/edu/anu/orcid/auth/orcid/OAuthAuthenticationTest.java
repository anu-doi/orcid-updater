package au.edu.anu.orcid.auth.orcid;

import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.util.PropertyLoader;

public class OAuthAuthenticationTest {
	static final Logger LOGGER = LoggerFactory.getLogger(OAuthAuthenticationTest.class);
	
	@Ignore
	@Test
	public void createTest() throws OAuthException {
		OAuthAuthenticator authenticator = new OAuthAuthenticator();
		authenticator.createOrcid(null);
		LOGGER.info("Done");
	}
	
	//@Ignore
	/*@Test
	public void addWorksTest() throws OAuthException, OrcidException {
		OAuthAuthenticator authenticator = new OAuthAuthenticator();
		AccessToken token = authenticator.getAccessTokenFromAuthorizationCode("Mb1J38");
		if (token == null) {
			fail("Token not found");
		}
		Obtainer<String> obtainer = new UidObtainer();
		OrcidMessage message = obtainer.getOrcidWorks("t1111111");
		LOGGER.info("{} - {} - {}", token.getTokenType(), token.getAccessToken(), token.getRefreshToken());
		authenticator.addWorks(token, message);
	}
	
	@Ignore
	@Test
	public void updateWorksTest() throws OAuthException, OrcidException {
		OAuthAuthenticator authenticator = new OAuthAuthenticator();
		AccessToken token = authenticator.getAccessTokenFromAuthorizationCode("ldXbod");
		if (token == null) {
			fail("Token not found");
		}
		Obtainer<String> obtainer = new UidObtainer();
		OrcidMessage message = obtainer.getOrcidWorks("t1111111");
		
		LOGGER.info("{} - {} - {}", token.getTokenType(), token.getAccessToken(), token.getRefreshToken());
		authenticator.updateWorks(token, message);
	}*/

	@Ignore
	@Test
	public void getAuthorizationURLTest() {
		OAuthAuthenticator authenticator = new OAuthAuthenticator();
		String redirectURI = "http://23wj72s.uds.anu.edu.au/rest/uid/t1111111";
		try {
			URI getCodeURL = authenticator.getAuthorizationCodeRequestUri(OAuthConstants.WORKS_CREATE, redirectURI);
			LOGGER.info("Code: {}", getCodeURL.toString());
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(getCodeURL);
			Response response = target.request().get();
			LOGGER.info("Status: {}", response.getStatus());
			String responseValue = response.readEntity(String.class);
			LOGGER.info("Value: {}", responseValue);
		}
		catch (UnsupportedEncodingException e) {
			LOGGER.error("Error encoding to url");
			fail("Error encoding to url");
		}
	}

	@Ignore
	@Test
	public void patternTest() {
		String strToMatch = "https://api.sandbox-1.orcid.org/0000-0002-2616-8536/orcid-profile";
		//"https://api.sandbox-1.orcid.org"
		//String orcidMatchUrl = "https://api.sandbox-1.orcid.org/([^/])/orcid-profile";
		//Properties props = PropertyLoader.loadProperties("orcid.properties");
		//String orcidMatchUrl = props.getProperty("orcid.api.uri") + "/([^/]*)/orcid-profile";
		//String orcidMatchUrl = "https://api.sandbox-1.orcid.org/([^/]*)/";
		String orcidMatchUrl = "https://api.sandbox-1.orcid.org/v1.1/([^/]*)/";

		Pattern orcidUrlPattern = Pattern.compile(orcidMatchUrl);
		
		Matcher matcher = orcidUrlPattern.matcher(strToMatch);
		LOGGER.info("Number of matches: {}", matcher.groupCount());
		boolean found = matcher.find();
		if (found) {
			LOGGER.info("Group 0: {}", matcher.group(0));
			LOGGER.info("Group 1: {}", matcher.group(1));
		}
		else {
			LOGGER.info("No match found");
		}
	}
}
