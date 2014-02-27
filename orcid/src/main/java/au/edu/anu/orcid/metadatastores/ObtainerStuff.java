package au.edu.anu.orcid.metadatastores;

import java.util.List;

import org.orcid.ns.orcid.OrcidActivities;
import org.orcid.ns.orcid.OrcidBio;
import org.orcid.ns.orcid.OrcidMessage;
import org.orcid.ns.orcid.OrcidProfile;
import org.orcid.ns.orcid.OrcidWork;
import org.orcid.ns.orcid.OrcidWorks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.model.Person;
import au.edu.anu.orcid.db.model.Publication;
import au.edu.anu.orcid.retrieve.ObtainInformation;
import au.edu.anu.orcid.retrieve.ObtainPersonInformation;
import au.edu.anu.orcid.retrieve.ObtainPublicationInformation;

public class ObtainerStuff {
	static final Logger LOGGER = LoggerFactory.getLogger(ObtainerStuff.class);

	
	public OrcidMessage generateCompleteProfile(Long id) {
		OrcidMessage message = new OrcidMessage();
		
		message.setMessageVersion("1.1");
		message.setOrcidProfile(getProfile(id));
		
		return message;
	}
	
	public OrcidMessage generateCompleteProfile(String uid) {
		OrcidMessage message = new OrcidMessage();
		
		message.setMessageVersion("1.1");
		message.setOrcidProfile(getProfile(uid));
		
		return message;
	}
	
	public OrcidProfile getProfile(Long id) {
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
	
	public OrcidProfile getProfile(String id) {
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
	
	private OrcidBio getBio(String uid) {
		ObtainInformation<Person, OrcidBio> oi = new ObtainPersonInformation();
		return oi.getOrcidObject(uid);
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
	
	private OrcidWorks getWorks(String uid) {
		ObtainInformation<List<Publication>, List<OrcidWork>> pubOI = new ObtainPublicationInformation();
		List<OrcidWork> pubWorks = pubOI.getOrcidObject(uid);
		if (pubWorks.size() > 0) {
			OrcidWorks orcidWorks = new OrcidWorks();
			orcidWorks.getOrcidWork().addAll(pubWorks);
			return orcidWorks;
		}
		
		return null;
	}
	
	public List<Publication> getPublications(String uid) {
		ObtainInformation<List<Publication>, List<OrcidWork>> oi = new ObtainPublicationInformation();
		return oi.get(uid);
	}
	
	public List<Publication> fetchPublications(String uid) {
		ObtainInformation<List<Publication>, List<OrcidWork>> oi = new ObtainPublicationInformation();
		return oi.fetch(uid);
	}
	
	public Person getPerson(String uid) {
		ObtainInformation<Person, OrcidBio> oi = new ObtainPersonInformation();
		return oi.get(uid);
	}
	
	public Person fetchPerson(String uid) {
		ObtainInformation<Person, OrcidBio> oi = new ObtainPersonInformation();
		return oi.fetch(uid);
	}
}
