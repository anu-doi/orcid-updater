package au.edu.anu.orcid.process.retrieve;

import java.util.List;

import javax.inject.Inject;

import org.orcid.ns.orcid.OrcidActivities;
import org.orcid.ns.orcid.OrcidBio;
import org.orcid.ns.orcid.OrcidMessage;
import org.orcid.ns.orcid.OrcidProfile;
import org.orcid.ns.orcid.OrcidWork;
import org.orcid.ns.orcid.OrcidWorks;

import au.edu.anu.orcid.db.model.Person;

public class IdObtainer implements Obtainer<Long> {
	
	@Inject
	ObtainPersonInformation personOI;
	
	@Inject
	ObtainPublicationInformation pubOI;
	
	/*@Override
	public OrcidMessage getFullOrcidProfile(Long id) {
		OrcidMessage message = new OrcidMessage();
		
		message.setMessageVersion("1.1");
		message.setOrcidProfile(getProfile(id));
		
		return message;
	}

	@Override
	public OrcidMessage getOrcidWorks(Long id) {
		OrcidMessage message = new OrcidMessage();
		message.setMessageVersion("1.1");
		OrcidProfile profile = new OrcidProfile();
		OrcidWorks works = getWorks(id);
		if (works != null ) {
			OrcidActivities activities = new OrcidActivities();
			activities.setOrcidWorks(works);
			profile.setOrcidActivities(activities);
		}
		message.setOrcidProfile(profile);
		
		return null;
	}
	
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
		return profile;
	}
	
	private OrcidBio getBio(Long id) {
		ObtainInformation<Person, OrcidBio> oi = new ObtainPersonInformation();
		return oi.getOrcidObject(id);
	}
	
	private OrcidWorks getWorks(Long id) {
		ObtainInformation<List<Publication>, List<OrcidWork>> pubOI = new ObtainPublicationInformation();
		List<OrcidWork> pubWorks = pubOI.getOrcidObject(id);
		if (pubWorks.size() > 0) {
			OrcidWorks orcidWorks = new OrcidWorks();
			orcidWorks.getOrcidWork().addAll(pubWorks);
			return orcidWorks;
		}
		
		return null;
	}

	@Override
	public Person getPerson(Long id) {
		ObtainInformation<Person, OrcidBio> oi = new ObtainPersonInformation();
		return oi.get(id);
	}

	@Override
	public Person fetchPerson(Long id) {
		ObtainInformation<Person, OrcidBio> personOI = new ObtainPersonInformation();
		ObtainInformation<List<Publication>, List<OrcidWork>> pubOI = new ObtainPublicationInformation();
		
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
		ObtainInformation<Person, OrcidBio> oi = new ObtainPersonInformation();
		oi.save(person);
	}*/
	

	
	@Override
	public OrcidMessage getFullOrcidProfile(Long id) {
		OrcidMessage message = new OrcidMessage();
		
		message.setMessageVersion("1.1");
		message.setOrcidProfile(getProfile(id));
		
		return message;
	}

	@Override
	public OrcidMessage getOrcidWorks(Long id) {
		OrcidMessage message = new OrcidMessage();
		message.setMessageVersion("1.1");
		OrcidProfile profile = new OrcidProfile();
		OrcidWorks works = getWorks(id);
		if (works != null ) {
			OrcidActivities activities = new OrcidActivities();
			activities.setOrcidWorks(works);
			profile.setOrcidActivities(activities);
		}
		message.setOrcidProfile(profile);
		
		return message;
	}
	
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
		return profile;
	}
	
	private OrcidBio getBio(Long id) {
		//ObtainInformation<Person, OrcidBio> personOI = new ObtainPersonInformation();
		return personOI.getOrcidObject(id);
	}
	
	private OrcidWorks getWorks(Long id) {
		//ObtainInformation<List<Publication>, List<OrcidWork>> pubOI = new ObtainPublicationInformation();
		List<OrcidWork> pubWorks = pubOI.getOrcidObject(id);
		if (pubWorks.size() > 0) {
			OrcidWorks orcidWorks = new OrcidWorks();
			orcidWorks.getOrcidWork().addAll(pubWorks);
			return orcidWorks;
		}
		
		return null;
	}

	@Override
	public Person getPerson(Long id) {
		//ObtainInformation<Person, OrcidBio> personOI = new ObtainPersonInformation();
		return personOI.get(id);
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
		//ObtainInformation<Person, OrcidBio> personOI = new ObtainPersonInformation();
		personOI.save(person);
	}
	
}
