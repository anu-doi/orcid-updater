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

import java.util.List;

import javax.inject.Inject;

import org.orcid.ns.orcid.OrcidActivities;
import org.orcid.ns.orcid.OrcidBio;
import org.orcid.ns.orcid.OrcidMessage;
import org.orcid.ns.orcid.OrcidProfile;
import org.orcid.ns.orcid.OrcidWork;
import org.orcid.ns.orcid.OrcidWorks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.model.Person;

/**
 * <p>IdObtainer</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Retrieve information with a the database id</p>
 * <p>Retrieve and save information based on the database identifier</p>
 *
 * @author Genevieve Turner
 *
 */
public class IdObtainer implements Obtainer<Long> {
	static final Logger LOGGER = LoggerFactory.getLogger(UidObtainer.class);
	
	@Inject
	ObtainPersonInformation personOI;
	
	@Inject
	ObtainPublicationInformation pubOI;
	
	@Override
	public OrcidMessage getFullOrcidProfile(Long id) {
		OrcidMessage message = new OrcidMessage();
		
		message.setMessageVersion("1.1");
		message.setOrcidProfile(getProfile(id));
		
		return message;
	}

	@Override
	public OrcidMessage getOrcidWorks(Long id) throws NoRecordException {
		OrcidMessage message = new OrcidMessage();
		message.setMessageVersion("1.1");
		OrcidProfile profile = new OrcidProfile();
		OrcidWorks works = getWorks(id);
		if (works != null ) {
			OrcidActivities activities = new OrcidActivities();
			activities.setOrcidWorks(works);
			profile.setOrcidActivities(activities);
		}
		else {
			throw new NoRecordException("There are no works to send to Orcid");
		}
		message.setOrcidProfile(profile);
		
		return message;
	}
	
	/**
	 * Get the persons  profile
	 * 
	 * @param id The identifier
	 * @return The profile
	 */
	private OrcidProfile getProfile(Long id) {
		OrcidProfile profile = new OrcidProfile();
		
		OrcidBio bio = getBio(id);
		if (bio == null) {
			return null;
		}
		profile.setOrcidBio(bio);
		OrcidWorks works = getWorks(id);
		if (works != null) {
			OrcidActivities activities = new OrcidActivities();
			activities.setOrcidWorks(works);
			profile.setOrcidActivities(activities);
		}
		else {
			LOGGER.debug("No works found for user with id {}", id);
		}
		return profile;
	}
	
	/**
	 * Get the persons bio information
	 * 
	 * @param id The persons id
	 * @return The bio
	 */
	private OrcidBio getBio(Long id) {
		return personOI.getOrcidObject(id);
	}
	
	/**
	 * Get the persons works
	 * 
	 * @param id The persons id
	 * @return The works
	 */
	private OrcidWorks getWorks(Long id) {
		List<OrcidWork> pubWorks = pubOI.getOrcidObject(id);
		if (pubWorks.size() > 0) {
			OrcidWorks orcidWorks = new OrcidWorks();
			orcidWorks.getOrcidWork().addAll(pubWorks);
			return orcidWorks;
		}
		
		return null;
	}

	@Override
	public Person getPerson(Long id) throws NoRecordException {
		Person person = personOI.get(id);
		if (person == null) {
			throw new NoRecordException("There is no user with the given id: " + id.toString());
		}
		return person;
	}

	@Override
	public Person fetchPerson(Long id) {
		Person person = personOI.get(id);
		if (person != null) {
			person = personOI.fetch(person.getUid());
			pubOI.fetch(person.getUid());
			return person;
		}
		return null;
	}

	@Override
	public void updatePerson(Person person) {
		personOI.save(person);
	}
	
}
