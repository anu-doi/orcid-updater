package au.edu.anu.orcid.metadatastores;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.model.Author;
import au.edu.anu.orcid.db.model.EmailAddress;
import au.edu.anu.orcid.db.model.Person;
import au.edu.anu.orcid.db.model.Publication;

public class ObtainerTest {
	Logger LOGGER = LoggerFactory.getLogger(ObtainerTest.class);
	
	@Ignore
	@Test
	public void testPublications() {
		ObtainerStuff obtainer = new ObtainerStuff();
		List<Publication> publications = obtainer.getPublications("u4014066");
		//List<Publication> publications = obtainer.getPublications("u9909577");
		for (Publication pub : publications) {
			LOGGER.info("Publication:\t{} - {}", pub.getTitle(), pub.getWorkType());
			//LOGGER.info("Work:\t\t{}", pub.getWork().getWorkTitle().getTitle());
			for (Author auth : pub.getAuthors()) {
				LOGGER.info("-- Name: {}, Id: {}", auth.getName(), auth.getExtId());
			}
		}
	}

	//@Ignore
	@Test
	public void savePublications() {
		ObtainerStuff obtainer = new ObtainerStuff();
		//obtainer.savePublications("u4014066");
		obtainer.fetchPublications("u4014066");
	}

	//@Ignore
	@Test
	public void savePerson() {
		ObtainerStuff obtainer = new ObtainerStuff();
		obtainer.fetchPerson("u4014066");
	}

	@Ignore
	@Test
	public void testPerson() {
		ObtainerStuff obtainer = new ObtainerStuff();
		Person person = obtainer.getPerson("u4014066");
		//MSPerson person = obtainer.getPersonInformation("u9909577");
		LOGGER.info("{} {}, {}, {}, {}, {}", person.getGivenName(), person.getFamilyName(), person.getExtId(), person.getOrcid(), person.getUid(), person.getDescription());
		for (EmailAddress email : person.getEmailAddresses()) {
			LOGGER.info("{}, {}", email.getEmail(), email.getPrimary());
		}
	}
}
