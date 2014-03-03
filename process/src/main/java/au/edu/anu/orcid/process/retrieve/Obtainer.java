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
package au.edu.anu.orcid.process.retrieve;

import org.orcid.ns.orcid.OrcidMessage;

import au.edu.anu.orcid.db.model.Person;

/**
 * <p>Obtainer</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Interface that shows how to retrieve information in the appropriate formats</p>
 *
 * @author Genevieve Turner
 *
 * @param <T> The identifier type to obtain information from
 */
public interface Obtainer<T> {
	/**
	 * Get the entire orcid profile information i.e. bio and works information
	 * 
	 * @param id The id
	 * @return An orcid message containing the profile information
	 */
	public OrcidMessage getFullOrcidProfile(T id);
	
	/**
	 * Get the works for the person
	 * 
	 * @param id The id
	 * @return An orcid message containing the works information
	 */
	public OrcidMessage getOrcidWorks(T id);
	
	/**
	 * Get the person object for the given id
	 * 
	 * @param id The id
	 * @return The Person
	 */
	public Person getPerson(T id);
	
	/**
	 * Fetch the information about the person from an external source and save it
	 * 
	 * @param id The id
	 * @return The fetched Person object
	 */
	public Person fetchPerson(T id);
	
	/**
	 * Save the person information
	 * 
	 * @param person The Person to update
	 */
	public void updatePerson(Person person);
}
