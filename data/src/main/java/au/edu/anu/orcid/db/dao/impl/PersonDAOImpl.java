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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import au.edu.anu.orcid.db.PersistenceManager;
import au.edu.anu.orcid.db.dao.PersonDAO;
import au.edu.anu.orcid.db.model.Person;

/**
 * <p>PersonDAOImpl</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Data Access Object Implementation class for the Person class</p>
 *
 * @author Genevieve Turner
 *
 */
public class PersonDAOImpl extends GenericDAOImpl<Person, Long> implements
PersonDAO {
	
	/**
	 * Constructor
	 */
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

	@Override
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
