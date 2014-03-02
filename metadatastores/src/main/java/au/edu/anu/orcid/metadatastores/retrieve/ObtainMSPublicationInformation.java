package au.edu.anu.orcid.metadatastores.retrieve;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.model.Publication;
import au.edu.anu.orcid.metadatastores.publication.MSPublication;
import au.edu.anu.orcid.process.retrieve.ObtainPublicationInformation;
import au.edu.anu.orcid.util.PropertyLoader;

public class ObtainMSPublicationInformation extends
		ObtainPublicationInformation {
	static final Logger LOGGER = LoggerFactory.getLogger(ObtainPublicationInformation.class);
	private static final Properties metadataStoresProperties = PropertyLoader.loadProperties("metadatastores.properties");

	@Override
	public List<Publication> fetch(String uid) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(metadataStoresProperties.getProperty("app.uri")).path(metadataStoresProperties.getProperty("extension.publication")).path(uid);
		//WebTarget target = client.target("http://localhost:8180/services/rest/person/publications").path(uid);
		LOGGER.info("Target URL: {}", target.getUri().toString());
		
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		List<MSPublication> msPublications = response.readEntity(new GenericType<List<MSPublication>>(){});
		List<Publication> publications = new ArrayList<Publication>();
		for (MSPublication msPub : msPublications) {
			publications.add(msPub.getPublication());
		}
		save(publications);
		return publications;
	}
}
