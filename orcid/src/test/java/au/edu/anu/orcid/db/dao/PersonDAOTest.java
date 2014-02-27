package au.edu.anu.orcid.db.dao;

import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.dao.impl.PersonDAOImpl;
import au.edu.anu.orcid.db.model.Person;
import au.edu.anu.orcid.db.model.Publication;

public class PersonDAOTest {
	static final Logger LOGGER = LoggerFactory.getLogger(PersonDAOTest.class);
	
	@Test
	public void testPublicationsPublishedToOrcid() {
		String uniId = "t1111111";
		
		PersonDAO personDAO = new PersonDAOImpl();
		Person person = personDAO.getPersonByUniIdWithOrcidPublications(uniId);
		if (person != null) {
			Set<Publication> publications = person.getOrcidPublications();
			for (Publication pub : publications) {
				LOGGER.info("Publication: {}", pub.getTitle());
			}
		}
		else {
			LOGGER.error("Could not find person with uid: {}", uniId);
			fail("Could not find person with uid: " + uniId);
		}
		LOGGER.info("Done!");
	}
}
