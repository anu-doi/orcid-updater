package au.edu.anu.orcid.metadatastores.retrieve;

import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.model.Person;
import au.edu.anu.orcid.metadatastores.person.MSPerson;
import au.edu.anu.orcid.process.retrieve.ObtainPersonInformation;
import au.edu.anu.orcid.util.PropertyLoader;

public class ObtainMSPersonInformation extends ObtainPersonInformation {
	Logger LOGGER = LoggerFactory.getLogger(ObtainMSPersonInformation.class);
	private static final Properties metadataStoresProperties = PropertyLoader.loadProperties("metadatastores.properties");

	@Override
	public Person fetch(String uid) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(metadataStoresProperties.getProperty("app.uri")).path(metadataStoresProperties.getProperty("extension.person")).path(uid);
		//WebTarget target = client.target("http://localhost:8180/services/rest/person/info").path(uid);
		LOGGER.info("Target URL: {}", target.getUri().toString());
		
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		MSPerson msPerson = response.readEntity(MSPerson.class);
		Person person = msPerson.getPerson();
		save(person);
		return person;
	}
}
