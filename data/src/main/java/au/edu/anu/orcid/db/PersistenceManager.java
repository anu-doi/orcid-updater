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
package au.edu.anu.orcid.db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>PersistenceManager</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Persistence Manager that deals with the creation and destruction of the Entity Manager Factory</p>
 *
 * @author Genevieve Turner
 *
 */
public class PersistenceManager {
	static final Logger LOGGER = LoggerFactory.getLogger(PersistenceManager.class);
	
	private static final PersistenceManager singleton_ = new PersistenceManager();
	
	protected EntityManagerFactory emf;
	
	/**
	 * Get an instance of the persistence manager
	 * 
	 * @return
	 */
	public static PersistenceManager getInstance() {
		return singleton_;
	}
	
	/**
	 * Constructor
	 */
	private PersistenceManager() {
		
	}
	
	/**
	 * Get the entity manager factory.  Create one if it does not already exist.
	 * 
	 * @return The entity manager factory
	 */
	public EntityManagerFactory getEntityManagerFactory() {
		if (emf == null) {
			createEntityManagerFactory();
		}
		return emf;
	}
	
	/**
	 * Close the entity manager factory
	 */
	public void closeEntityManagerFactory() {
		if (emf != null) {
			emf.close();
			emf = null;
			LOGGER.info("Persistence finished at " + new java.util.Date());
		}
	}
	
	/**
	 * Create an entity manager factory
	 */
	protected void createEntityManagerFactory() {
		this.emf = Persistence.createEntityManagerFactory("orcid");
		LOGGER.info("Persistence started at " + new java.util.Date());
	}
}
