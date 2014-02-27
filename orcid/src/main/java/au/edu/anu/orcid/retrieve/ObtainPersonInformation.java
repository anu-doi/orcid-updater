package au.edu.anu.orcid.retrieve;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.orcid.ns.orcid.OrcidBio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.dao.GenericDAO;
import au.edu.anu.orcid.db.dao.PersonDAO;
import au.edu.anu.orcid.db.dao.impl.GenericDAOImpl;
import au.edu.anu.orcid.db.dao.impl.PersonDAOImpl;
import au.edu.anu.orcid.db.model.EmailAddress;
import au.edu.anu.orcid.db.model.Person;
import au.edu.anu.orcid.metadatastores.person.MSPerson;

public class ObtainPersonInformation implements ObtainInformation<Person, OrcidBio> {
	Logger LOGGER = LoggerFactory.getLogger(ObtainPersonInformation.class);
	
	//@Inject
	//PersonDAO personDAO;
	
	@Override
	public Person get(Long id) {
		PersonDAO personDAO = new PersonDAOImpl();
		Person person =personDAO.getSingleById(id);
		return person;
	}
	
	@Override
	public Person get(String uid) {
		PersonDAO personDAO = new PersonDAOImpl();
		Person person = personDAO.getPersonByUniId(uid);
		return person;
	}
	
	@Override
	public Person fetch(String uid) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8180/services/rest/person/info").path(uid);
		LOGGER.info("Target URL: {}", target.getUri().toString());
		
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		MSPerson msPerson = response.readEntity(MSPerson.class);
		Person person = new Person(msPerson);
		save(person);
		return person;
	}
	
	@Override
	public void save(Person person) {
		PersonDAO personDAO = new PersonDAOImpl();
		Person savedPerson = personDAO.getByExternalIdentifier(person.getExtId());
		if (savedPerson == null) {
			LOGGER.info("Saving person: {} {}", person.getGivenName(), person.getFamilyName());
			personDAO.create(person);
		}
		else if (!person.equals(savedPerson)) {
			LOGGER.info("Update person information");
			savedPerson.setGivenName(person.getGivenName());
			savedPerson.setFamilyName(person.getFamilyName());
			//TODO should other systems be able to update the orcid unless its null?
			LOGGER.info("Before Set Orcid");
			LOGGER.info("Orcid - Saved Person: {}, Updated Person: {}", savedPerson.getOrcid(), person.getOrcid());
			if (savedPerson.getOrcid() == null) {
				LOGGER.info("Set Orcid Value: {}", person.getOrcid());
				savedPerson.setOrcid(person.getOrcid());
			}
			savedPerson.setDescription(person.getDescription());
			
			List<EmailAddress> personEmails = person.getEmailAddresses();
			List<EmailAddress> savedPersonEmails = savedPerson.getEmailAddresses();
			
			if (!(personEmails.equals(savedPersonEmails))) {
				List<EmailAddress> emailsToRemove = new ArrayList<EmailAddress>(savedPersonEmails);
				emailsToRemove.removeAll(personEmails);
				
				List<EmailAddress> emailsToAdd = new ArrayList<EmailAddress>(personEmails);
				emailsToAdd.removeAll(savedPersonEmails);
				
				LOGGER.debug("Number of emails to remove: {}", emailsToRemove.size());
				LOGGER.debug("Number of emails to add: {}", emailsToAdd.size());
				
				for (EmailAddress email : emailsToAdd) {
					email.setPerson(savedPerson);
				}
				
				savedPersonEmails.retainAll(personEmails);
				savedPersonEmails.addAll(emailsToAdd);

				GenericDAO<EmailAddress, Long> emailDAO = new GenericDAOImpl<EmailAddress, Long>(EmailAddress.class);
				for (EmailAddress email : emailsToRemove) {
					emailDAO.delete(email.getId());
				}
			}
			personDAO.update(savedPerson);
		}
		else {
			LOGGER.info("Person already exists");
		}
	}

	@Override
	public OrcidBio getOrcidObject(Long id) {
		Person person = get(id);
		if (person != null) {
			return person.getBio();
		}
		return null;
	}

	@Override
	public OrcidBio getOrcidObject(String uid) {
		Person person = get(uid);
		if (person != null) {
			return person.getBio();
		}
		return null;
	}
}
