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

public class UidObtainer implements Obtainer<String> {
	static final Logger LOGGER = LoggerFactory.getLogger(UidObtainer.class);
	
	//@Inject
	//ObtainInformation<Person, OrcidBio> personOI;
	//ObtainInformation<Person, OrcidBio> oi = new ObtainPersonInformation();
	
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
	public OrcidMessage getOrcidWorks(String uid) {
		OrcidMessage message = new OrcidMessage();
		message.setMessageVersion("1.1");
		OrcidProfile profile = new OrcidProfile();
		OrcidWorks works = getWorks(uid);
		if (works != null ) {
			OrcidActivities activities = new OrcidActivities();
			activities.setOrcidWorks(works);
			profile.setOrcidActivities(activities);
		}
		message.setOrcidProfile(profile);
		
		return message;
	}
	
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
	
	private OrcidBio getBio(String uid) {
		//ObtainInformation<Person, OrcidBio> personOI = new ObtainPersonInformation();
		return personOI.getOrcidObject(uid);
	}
	
	private OrcidWorks getWorks(String uid) {
		//ObtainInformation<List<Publication>, List<OrcidWork>> pubOI = new ObtainPublicationInformation();
		List<OrcidWork> pubWorks = pubOI.getOrcidObject(uid);
		if (pubWorks.size() > 0) {
			OrcidWorks orcidWorks = new OrcidWorks();
			orcidWorks.getOrcidWork().addAll(pubWorks);
			return orcidWorks;
		}
		
		return null;
	}

	@Override
	public Person getPerson(String uid) {
		//ObtainInformation<Person, OrcidBio> personOI = new ObtainPersonInformation();
		return personOI.get(uid);
	}

	@Override
	public Person fetchPerson(String uid) {
		//ObtainInformation<Person, OrcidBio> personOI = new ObtainPersonInformation();
		//ObtainInformation<List<Publication>, List<OrcidWork>> pubOI = new ObtainPublicationInformation();
		
		Person person = personOI.fetch(uid);
		pubOI.fetch(uid);
		
		return person;
	}

	@Override
	public void updatePerson(Person person) {
		//ObtainInformation<Person, OrcidBio> personOI = new ObtainPersonInformation();
		personOI.save(person);
	}

}
