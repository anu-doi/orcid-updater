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
 * <p>UidObtainer</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Obtain information with the university id</p>
 *
 * @author Genevieve Turner
 *
 */
public class UidObtainer implements Obtainer<String> {
	static final Logger LOGGER = LoggerFactory.getLogger(UidObtainer.class);

	@Inject
	ObtainPersonInformation personOI;
	
	@Inject
	ObtainPublicationInformation pubOI;
	
	@Override
	public OrcidMessage getFullOrcidProfile(String uid) {
		OrcidMessage message = new OrcidMessage();
		
		message.setMessageVersion("1.1");
		message.setOrcidProfile(getProfile(uid));
		
		return message;
	}

	@Override
	public OrcidMessage getOrcidWorks(String uid) throws NoRecordException {
		OrcidMessage message = new OrcidMessage();
		message.setMessageVersion("1.1");
		OrcidProfile profile = new OrcidProfile();
		OrcidWorks works = getWorks(uid);
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
	 * @param uid The university identifier
	 * @return The profile
	 */
	private OrcidProfile getProfile(String uid) {
		OrcidProfile profile = new OrcidProfile();
		
		OrcidBio bio = getBio(uid);
		if (bio == null) {
			return null;
		}
		profile.setOrcidBio(bio);
		OrcidWorks works = getWorks(uid);
		if (works != null) {
			OrcidActivities activities = new OrcidActivities();
			activities.setOrcidWorks(works);
			profile.setOrcidActivities(activities);
		}
		else {
			LOGGER.info("No works found!");
		}
		return profile;
	}
	
	/**
	 * Get the persons bio information
	 * 
	 * @param uid The university identifier
	 * @return The bio information
	 */
	private OrcidBio getBio(String uid) {
		return personOI.getOrcidObject(uid);
	}
	
	/**
	 * Get the persons works
	 * 
	 * @param uid The university identifier
	 * @return The persons works
	 */
	private OrcidWorks getWorks(String uid) {
		List<OrcidWork> pubWorks = pubOI.getOrcidObject(uid);
		if (pubWorks.size() > 0) {
			OrcidWorks orcidWorks = new OrcidWorks();
			orcidWorks.getOrcidWork().addAll(pubWorks);
			return orcidWorks;
		}
		
		return null;
	}

	@Override
	public Person getPerson(String uid) throws NoRecordException {
		Person person = personOI.get(uid);
		if (person == null) {
			throw new NoRecordException("There is no user with the given uid: " + uid);
		}
		return person;
	}

	@Override
	public Person fetchPerson(String uid) {
		
		Person person = personOI.fetch(uid);
		pubOI.fetch(uid);
		
		return person;
	}

	@Override
	public void updatePerson(Person person) {
		personOI.save(person);
	}

}
