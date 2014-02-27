package au.edu.anu.orcid.db.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import au.edu.anu.orcid.db.PersistenceManager;
import au.edu.anu.orcid.db.dao.PublicationDAO;
import au.edu.anu.orcid.db.model.Publication;

public class PublicationDAOImpl extends GenericDAOImpl<Publication, Long> implements
	PublicationDAO {
	
	public PublicationDAOImpl(Class<Publication> type) {
		super(type);
	}
	
	public PublicationDAOImpl() {
		super(Publication.class);
	}

	@Override
	public Publication getByExternalIdentifier(String identifier) {
		EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
		try {
			Query query = em.createQuery("SELECT p FROM Publication p WHERE p.ariesId = :ariesId");
			query.setParameter("ariesId", identifier);
			Publication publication = (Publication) query.getSingleResult();
			return publication;
		}
		catch (NoResultException e) {
			LOGGER.debug("No Publication found", e);
		}
		finally {
			em.close();
		}
		return null;
	}

	@Override
	public List<Publication> getPublicationsWithPersonExternalId(String externalIdentifier) {
		// TODO Auto-generated method stub

		EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
		try {
			Query query = em.createQuery("SELECT p FROM Publication p left join p.authors a WHERE a.extId = :identifier");
			query.setParameter("identifier", externalIdentifier);
			@SuppressWarnings("unchecked")
			List<Publication> publications = (List<Publication>) query.getResultList();
			return publications;
		}
		catch (NoResultException e) {
			LOGGER.debug("No Publications found", e);
		}
		finally {
			em.close();
		}
		return null;
	}
}
