package data;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.dao.PersonDAO;
import au.edu.anu.orcid.db.dao.impl.PersonDAOImpl;
import au.edu.anu.orcid.db.model.Person;

public class PersonDAOTest {
	Logger LOGGER = LoggerFactory.getLogger(PersonDAOTest.class);
	
	@Test
	public void test() {
		PersonDAO personDAO = new PersonDAOImpl();
		Person person = personDAO.getPersonByUniId("t1111111");
		if (person == null) {
			fail("Person is null");
		}
		LOGGER.info("{}", person.getGivenName());
	}
}
