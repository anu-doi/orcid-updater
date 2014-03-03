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
package au.edu.anu.orcid.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>EmailAddress</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Entity class for the 'email' table</p>
 *
 * @author Genevieve Turner
 *
 */
@Entity
@Table(name="email")
public class EmailAddress {
	Logger LOGGER = LoggerFactory.getLogger(EmailAddress.class);
	
	private Long id;
	private Person person;
	private Boolean primary;
	private String email;
	
	/**
	 * Constructor
	 */
	public EmailAddress() {
		
	}
	
	/**
	 * Constructor
	 * 
	 * @param primary Indicates whether the email address is a primary or secondary
	 * @param email The email address
	 */
	public EmailAddress(Boolean primary, String email) {
		this.primary = primary;
		this.email = email;
	}

	/**
	 * Get the id
	 * 
	 * @return The id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	/**
	 * Set the id
	 * 
	 * @param id The id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get the associated person
	 * 
	 * @return The person
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", referencedColumnName="id", nullable = false)
	public Person getPerson() {
		return person;
	}

	/**
	 * Set the associated person
	 * 
	 * @param person The person
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * Get whether it is a primary email or not
	 * 
	 * @return The indicator
	 */
	@Column(name="primary_address")
	public Boolean getPrimary() {
		return primary;
	}

	/**
	 * Set whether it is a primary email or not
	 * 
	 * @param primary The indicator
	 */
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}
	
	/**
	 * Get the email address
	 * 
	 * @return The email address
	 */
	@Column(name="email")
	public String getEmail() {
		return email;
	}

	/**
	 * Set the email address
	 * 
	 * @param email The email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		LOGGER.info("Compare hash code: {}", hashCode);
		
		if (person != null) {
			hashCode = 17 * hashCode + person.hashCode();
		}
		if (primary != null) {
			hashCode = 17 * hashCode + primary.hashCode();
		}
		if (email != null) {
			hashCode = 17 * hashCode + email.hashCode();
		}
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof EmailAddress)) {
			return false;
		}
		EmailAddress other = (EmailAddress) obj;
		
		return (
				((this.getEmail() == other.getEmail()) || (this.getEmail() != null && this.getEmail().equals(other.getEmail()))) &&
				((this.getPrimary() == other.getPrimary()) || (this.getPrimary() != null && this.getPrimary().equals(other.getPrimary()))) &&
				((this.getPerson() == other.getPerson()) ||
						(
						this.getPerson() != null && this.getPerson().getExtId() != null &&
						other.getPerson() != null && other.getPerson().getExtId() != null &&
						this.getPerson().getExtId().equals(other.getPerson().getExtId()))
						)
				);
	}
}
