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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import au.edu.anu.orcid.db.PersistenceManager;
import au.edu.anu.orcid.db.dao.PublicationDAO;
import au.edu.anu.orcid.db.model.Publication;

/**
 * <p>PublicationDAOImpl</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Data Access Object Implementation class for Publication class</p>
 *
 * @author Genevieve Turner
 *
 */
public class PublicationDAOImpl extends GenericDAOImpl<Publication, Long> implements
	PublicationDAO {
	
	/**
	 * Constructor
	 */
	public PublicationDAOImpl() {
		super(Publication.class);
	}

	@Override
	public Publication getByExternalIdentifier(String identifier) {
		EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
		try {
			Query query = em.createQuery("SELECT p FROM Publication p WHERE p.externalIdentifier = :externalId");
			query.setParameter("externalId", identifier);
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
