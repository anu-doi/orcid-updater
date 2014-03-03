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
package au.edu.anu.orcid.db.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.PersistenceManager;
import au.edu.anu.orcid.db.dao.GenericDAO;

/**
 * <p>GenericDAOImpl</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Generic Data Access Object Implementation</p>
 *
 * @author Genevieve Turner
 *
 * @param <T> The object type to implement
 * @param <PK> The object type of the primary key
 */
public class GenericDAOImpl<T, PK extends Serializable> implements GenericDAO<T, PK> {
	static final Logger LOGGER = LoggerFactory.getLogger(GenericDAOImpl.class);
	
	private Class<T> type_;

	/**
	 * Constructor
	 * 
	 * @param type The type of the object
	 */
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
