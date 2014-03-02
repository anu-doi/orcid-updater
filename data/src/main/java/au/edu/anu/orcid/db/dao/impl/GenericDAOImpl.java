
package au.edu.anu.orcid.db.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.PersistenceManager;
import au.edu.anu.orcid.db.dao.GenericDAO;


public class GenericDAOImpl<T, PK extends Serializable> implements GenericDAO<T, PK> {
	static final Logger LOGGER = LoggerFactory.getLogger(GenericDAOImpl.class);
	
	private Class<T> type_;

	public GenericDAOImpl(Class<T> type) {
		// Attempting to have a default constructor that uses ParameterizedType causes errors
		// So we need to have a constructor that sets the class type
		this.type_ = type;
	}
	
	public T create(T o) {
		EntityManager entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			entityManager.persist(o);
			entityTransaction.commit();
		}
		finally {
			entityManager.close();
		}
		return o;
	}

	public T getSingleById(PK id) {
		EntityManager entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
		T object = null;
		try {
			object = (T) entityManager.find(type_, id);
		}
		finally {
			entityManager.close();
		}
		return object;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		EntityManager entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
		List<T> objects = null;
		try {
			objects = entityManager.createQuery("from " + type_.getName()).getResultList();
		}
		finally {
			entityManager.close();
		}
		return objects;
	}

	public T update(T o) {
		EntityManager entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			o = entityManager.merge(o);
			entityTransaction.commit();
		}
		finally {
			entityManager.close();
		}
		return o;
	}

	public void delete (PK id) {
		EntityManager entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			entityManager.remove(entityManager.getReference(type_, id));
			entityTransaction.commit();
		}
		finally {
			entityManager.close();
		}
	}
}
