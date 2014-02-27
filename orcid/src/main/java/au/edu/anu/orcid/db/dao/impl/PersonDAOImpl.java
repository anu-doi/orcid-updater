package au.edu.anu.orcid.db.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import au.edu.anu.orcid.db.PersistenceManager;
import au.edu.anu.orcid.db.dao.PersonDAO;
import au.edu.anu.orcid.db.model.Person;

public class PersonDAOImpl extends GenericDAOImpl<Person, Long> implements
PersonDAO {
	
	/*public PersonDAOImpl(Class<Person> type) {
		super(type);
	}*/
	
	public PersonDAOImpl() {
		super(Person.class);
	}

	@Override
	public Person getByExternalIdentifier(String identifier) {
		EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
		try {
			Query query = em.createQuery("SELECT p FROM Person p WHERE p.extId = :extId");
			query.setParameter("extId", identifier);
			Person person = (Person) query.getSingleResult();
			return person;
		}
		catch (NoResultException e) {
			LOGGER.debug("No Person found", e);
		}
		finally {
			em.close();
		}
		return null;
	}

	@Override
	public Person getPersonByUniId(String uniId) {
		EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
		try {
			Query query = em.createQuery("SELECT p FROM Person p WHERE p.uid = :uid");
			query.setParameter("uid", uniId);
			Person person = (Person) query.getSingleResult();
			return person;
		}
		catch (NoResultException e) {
			LOGGER.debug("No Person found", e);
		}
		finally {
			em.close();
		}
		return null;
	}
	
	public Person getPersonByUniIdWithOrcidPublications(String uniId) {
		EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
		try {
			Query query = em.createQuery("SELECT p FROM Person p LEFT JOIN FETCH p.orcidPublications WHERE p.uid = :uid");
			query.setParameter("uid", uniId);
			Person person = (Person) query.getSingleResult();
			return person;
		}
		catch (NoResultException e) {
			LOGGER.debug("No Person found", e);
		}
		finally {
			em.close();
		}
		return null;
	}

}
