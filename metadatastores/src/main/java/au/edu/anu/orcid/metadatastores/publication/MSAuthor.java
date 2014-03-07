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
package au.edu.anu.orcid.metadatastores.publication;

import javax.xml.bind.annotation.XmlElement;

import au.edu.anu.orcid.db.model.Author;
import au.edu.anu.orcid.db.model.Publication;

/**
 * <p>MSAuthor</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Class for publication authors</p>
 *
 * @author Genevieve Turner
 *
 */
public class MSAuthor {
	private String extId;
	private String displayName;
	private String givenName;
	private String organisationalUnit;
	private String staffType;
	private String surname;

	/**
	 * Get the external identifier
	 * 
	 * @return The external identifier
	 */
	@XmlElement(name="external-identifier")
	public String getExtId() {
		return extId;
	}

	/**
	 * Set the external identifier
	 * 
	 * @param extId The external identifier
	 */
	public void setExtId(String extId) {
		this.extId = extId;
	}

	/**
	 * Get the display name
	 * 
	 * @return The display name
	 */
	@XmlElement(name="display-name")
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Set the display name
	 * 
	 * @param displayName  The display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Get the given name
	 * 
	 * @return The given name
	 */
	@XmlElement(name="given-name")
	public String getGivenName() {
		return givenName;
	}

	/**
	 * Set the given name
	 * 
	 * @param givenName The given name
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	/**
	 * Get the organisational unit
	 * 
	 * @return The organisational unit
	 */
	@XmlElement(name="organisational-unit")
	public String getOrganisationalUnit() {
		return organisationalUnit;
	}

	/**
	 * Set the organisational unit
	 * 
	 * @param organisationalUnit The organisational unit
	 */
	public void setOrganisationalUnit(String organisationalUnit) {
		this.organisationalUnit = organisationalUnit;
	}

	/**
	 * Get the staff type
	 * 
	 * @return The staff type
	 */
	@XmlElement(name="staff-type")
	public String getStaffType() {
		return staffType;
	}

	/**
	 * Set the staff type
	 * 
	 * @param staffType The staff type
	 */
	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	/**
	 * Get the surname
	 * 
	 * @return The surname
	 */
	@XmlElement(name="surname")
	public String getSurname() {
		return surname;
	}

	/**
	 * Set the surname
	 * 
	 * @param surname The surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	/**
	 * Transform the metadata stores author object into an author object suitable for saving into the orcid-updater
	 * application.
	 * 
	 * @param publication The publication to associate the Author with
	 * @return The Author
	 */
	public Author getAuthor(Publication publication) {
		Author author = new Author();
		author.setExtId(this.getExtId());
		String name = this.getSurname() + ", " + this.getGivenName();
		author.setName(name);
		author.setPublication(publication);
		
		return author;
	}
}
