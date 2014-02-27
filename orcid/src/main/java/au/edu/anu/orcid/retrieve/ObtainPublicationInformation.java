package au.edu.anu.orcid.retrieve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.orcid.ns.orcid.OrcidWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.dao.GenericDAO;
import au.edu.anu.orcid.db.dao.impl.GenericDAOImpl;
import au.edu.anu.orcid.db.dao.impl.PersonDAOImpl;
import au.edu.anu.orcid.db.dao.impl.PublicationDAOImpl;
import au.edu.anu.orcid.db.model.Author;
import au.edu.anu.orcid.db.model.Person;
import au.edu.anu.orcid.db.model.Publication;
import au.edu.anu.orcid.metadatastores.ObtainerStuff;
import au.edu.anu.orcid.metadatastores.publication.MSPublication;

public class ObtainPublicationInformation implements ObtainInformation<List<Publication>, List<OrcidWork>> {
	static final Logger LOGGER = LoggerFactory.getLogger(ObtainerStuff.class);
	
	@Override
	public List<Publication> get(Long id) {
		// TODO Auto-generated method stub
		PersonDAOImpl personDAO = new PersonDAOImpl();
		Person person = personDAO.getSingleById(id);
		PublicationDAOImpl publicationDAO = new PublicationDAOImpl(Publication.class);
		List<Publication> publications = publicationDAO.getPublicationsWithPersonExternalId(person.getExtId());
		return publications;
	}

	@Override
	public List<Publication> get(String uid) {
		PersonDAOImpl personDAO = new PersonDAOImpl();
		Person person = personDAO.getPersonByUniId(uid);
		PublicationDAOImpl publicationDAO = new PublicationDAOImpl(Publication.class);
		List<Publication> publications = publicationDAO.getPublicationsWithPersonExternalId(person.getExtId());
		return publications;
	}

	@Override
	public List<Publication> fetch(String uid) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8180/services/rest/person/publications").path(uid);
		LOGGER.info("Target URL: {}", target.getUri().toString());
		
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		List<MSPublication> msPublications = response.readEntity(new GenericType<List<MSPublication>>(){});
		List<Publication> publications = new ArrayList<Publication>();
		for (MSPublication msPub : msPublications) {
			publications.add(new Publication(msPub));
		}
		save(publications);
		return publications;
	}

	@Override
	public void save(List<Publication> publications) {
		if (publications == null || publications.size() == 0) {
			LOGGER.info("No publications found");
			return;
		}
		PublicationDAOImpl publicationDAO = new PublicationDAOImpl(Publication.class);
		//TODO remove publications no longer associated with the person!
		for (Publication publication : publications) {
			Publication savedPublication = publicationDAO.getByExternalIdentifier(publication.getAriesId());
			LOGGER.info("Publication TItle: {}", publication.getTitle());
			if (savedPublication == null) {
				LOGGER.info("Saving publication: {}", publication.getTitle());
				publicationDAO.create(publication);
			}
			else if (!savedPublication.equals(publication)) {
				LOGGER.info("Publication needs to be updated");
				savedPublication.setAriesId(publication.getAriesId());
				savedPublication.setTitle(publication.getTitle());
				savedPublication.setPublicationName(publication.getPublicationName());
				savedPublication.setPublicationYear(publication.getPublicationYear());
				savedPublication.setWorkType(publication.getWorkType());
				savedPublication.setIsbn(publication.getIsbn());
				savedPublication.setIssn(publication.getIssn());
				List<Author> publicationAuths = publication.getAuthors();
				List<Author> savedPubAuths = savedPublication.getAuthors();
				if (!(publicationAuths.equals(savedPubAuths))) {
					List<Author> authorsToRemove = new ArrayList<Author>(savedPubAuths);
					authorsToRemove.removeAll(publicationAuths);
					
					List<Author> authorsToAdd = new ArrayList<Author>(publicationAuths);
					authorsToAdd.removeAll(savedPubAuths);
					Map<Author, Author> modifiedAuthors = new HashMap<Author, Author>();
					for (Author auth : authorsToAdd) {
						for (Author remAuth : authorsToRemove) {
							if (auth.getExtId().equals(remAuth.getExtId())) {
								modifiedAuthors.put(remAuth, auth);
							}
						}
						auth.setPublication(savedPublication);
					}
					for (Entry<Author, Author> entry : modifiedAuthors.entrySet()) {
						authorsToAdd.remove(entry.getValue());
						authorsToRemove.remove(entry.getKey());
						int index = savedPubAuths.indexOf(entry.getKey());
						savedPubAuths.get(index).setName(entry.getValue().getName());
					}
					savedPubAuths.retainAll(publicationAuths);
					savedPubAuths.addAll(authorsToAdd);
					
					GenericDAO<Author, Long> authorDAO = new GenericDAOImpl<Author, Long>(Author.class);
					for (Author auth : authorsToRemove) {
						authorDAO.delete(auth.getId());
					}
				}
				publicationDAO.update(savedPublication);
			}
			else {
				LOGGER.info("Publication already exists");
			}
		}
	}

	@Override
	public List<OrcidWork> getOrcidObject(Long id) {
		List<Publication> publications = get(id);
		return publicationListToWorks(publications);
	}

	@Override
	public List<OrcidWork> getOrcidObject(String uid) {
		List<Publication> publications = get(uid);
		return publicationListToWorks(publications);
	}
	
	private List<OrcidWork> publicationListToWorks(List<Publication> publications) {
		List<OrcidWork> works = new ArrayList<OrcidWork>();
		
		for (Publication pub : publications) {
			works.add(pub.getWork());
		}
		
		return works;
	}

}
