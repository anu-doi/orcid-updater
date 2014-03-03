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
package au.edu.anu.orcid.process.retrieve;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.orcid.ns.orcid.OrcidBio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.dao.GenericDAO;
import au.edu.anu.orcid.db.dao.PersonDAO;
import au.edu.anu.orcid.db.dao.impl.GenericDAOImpl;
import au.edu.anu.orcid.db.model.EmailAddress;
import au.edu.anu.orcid.db.model.Person;

/**
 * <p>ObtainPersonInformation</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Obtain information about a person</p>
 *
 * @author Genevieve Turner
 *
 */
public abstract class ObtainPersonInformation implements ObtainInformation<Person, OrcidBio> {
	Logger LOGGER = LoggerFactory.getLogger(ObtainPersonInformation.class);
	
	@Inject
	PersonDAO personDAO;
	
	@Override
	public Person get(Long id) {
		Person person =personDAO.getSingleById(id);
		return person;
	}
	
	@Override
	public Person get(String uid) {
		LOGGER.info("UID: {}", uid);
		Person person = personDAO.getPersonByUniId(uid);
		if (person == null) {
			LOGGER.info("Person is null for some reason?");
		}
		else {
			LOGGER.info("Person is not null");
		}
		return person;
	}
	
	@Override
	public void save(Person person) {
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
			LOGGER.debug("Before Set Orcid");
			LOGGER.info("Orcid - Saved Person: {}, Updated Person: {}", savedPerson.getOrcid(), person.getOrcid());
			if (savedPerson.getOrcid() == null) {
				LOGGER.debug("Set Orcid Value: {}", person.getOrcid());
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
