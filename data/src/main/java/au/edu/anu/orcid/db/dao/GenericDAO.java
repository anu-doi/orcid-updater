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
package au.edu.anu.orcid.db.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * <p>GenericDAO</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Generic Data Access Object Implementation</p>
 *
 * @author Genevieve Turner
 *
 * @param <T> The type of the object
 * @param <PK> The type of the primary key
 */
public interface GenericDAO <T, PK extends Serializable>{
	/**
	 * Create an object
	 * 
	 * @param t The object to create
	 * @return The created object
	 */
	T create(T t);
	
	/**
	 * Get the object by the primary key
	 * 
	 * @param id The primary key
	 * @return The found object
	 */
	T getSingleById(PK id);

	/**
	 * Get all the objects in the database of this type
	 * @return
	 */
	List<T> getAll();

	/**
	 * Update the object
	 * 
	 * @param t The object to save to the database
	 * @return The saved object
	 */
	T update(T t);

	/**
	 * Delete the object with the given primary key
	 * 
	 * @param id The primary key of the object to delete
	 */
	void delete(PK id);
}
