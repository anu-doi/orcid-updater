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

/**
 * <p>ObtainInformation</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Interface for the logic around the retrieval and saving of information about objects.</p>
 *
 * @author Genevieve Turner
 *
 * @param <T> The object type to find information about
 * @param <U> The ORCID object type to return
 */
public interface ObtainInformation<T, U> {
	/**
	 * Get the object
	 * 
	 * @param id The database id of the person for whom the object is being retrieved
	 * @return The associated object
	 */
	public T get(Long id);
	
	/**
	 * Get the object
	 * 
	 * @param uid The institutional unique identifier
	 * @return The object
	 */
	public T get(String uid);
	
	/**
	 * Get the object from an external source and save the relevant information to the orcid-updater database. 
	 * (e.g. The external source for ANU is metadata stores).
	 * 
	 * @param uid The institutional unique identifier
	 * @return The fetched object
	 */
	public T fetch(String uid);
	
	/**
	 * Save the object
	 * 
	 * @param t The object to save
	 */
	public void save(T t);
	
	/**
	 * Get the object in an ORCID object format
	 * 
	 * @param id The database id of the person for whom the orcid object is being retrieved
	 * @return The orcid object
	 */
	public U getOrcidObject(Long id);
	
	/**
	 * Get the object in an ORCID object format
	 * 
	 * @param uid The institutional unique identifier
	 * @return The orcid object
	 */
	public U getOrcidObject(String uid);
}
