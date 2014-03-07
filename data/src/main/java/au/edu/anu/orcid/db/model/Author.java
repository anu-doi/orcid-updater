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
 * <p>Author</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Entity class for the 'publication_author' table</p>
 *
 * @author Genevieve Turner
 *
 */
@Entity
@Table(name="publication_author")
public class Author {
	static final Logger LOGGER = LoggerFactory.getLogger(Author.class);
	
	private Long id;
	private String extId;
	private Publication publication;
	private String name;
	
	/**
	 * Constructor
	 */
	public Author() {
		
	}
	
	/**
	 * Get the identifier
	 * 
	 * @return The identifier
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	/**
	 * Set the identifier
	 * 
	 * @param id The identifier
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * <p>Get the external identifier.</p>
	 * <p>The intended use of the external identifier for Author is to provide the ability to match an author
	 * with a {@link Person}</p>
	 * 
	 * @return The external identifier
	 */
	@Column(name="external_id")
	public String getExtId() {
		return extId;
	}

	/**
	 * <p>Set the external identifier</p>
	 * <p>The intended use of the external identifier for Author is to provide the ability to match an author
	 * with a {@link Person}</p>
	 * 
	 * @param extId The external identifier
	 */
	public void setExtId(String extId) {
		this.extId = extId;
	}

	/**
	 * Get the associated publication
	 * 
	 * @return The  publication
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publication_id", referencedColumnName="id", nullable = false)
	public Publication getPublication() {
		return publication;
	}

	/**
	 * Set the associated publication
	 * 
	 * @param publication The publication
	 */
	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	/**
	 * Get the author name
	 * 
	 * @return The author name
	 */
	@Column(name="author_name")
	public String getName() {
		return name;
	}

	/**
	 * Set the author's name
	 * 
	 * @param name The author's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		
		if (extId != null) {
			hashCode = 17 * hashCode + extId.hashCode();
		}
		if (publication != null) {
			hashCode = 17 * hashCode + publication.hashCode();
		}
		if (name != null) {
			hashCode = 17 * hashCode + name.hashCode();
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
		if (!(obj instanceof Author)) {
			return false;
		}
		Author other = (Author) obj;
		return (
				((this.getExtId() == other.getExtId()) || (this.getExtId() != null && this.getExtId().equals(other.getExtId()))) &&
				//TODO potentially fix this up, would prefer to directly reference the publication however this causes an infinite loop and stackoverflow exception
				((this.getPublication() == other.getPublication()) 
						|| (this.getPublication() != null && this.getPublication().getExternalIdentifier() != null
						&& other.getPublication() != null && other.getPublication().getExternalIdentifier() != null
						&& this.getPublication().getExternalIdentifier().equals(other.getPublication().getExternalIdentifier()))) &&
				((this.getName() == other.getName()) || (this.getName() != null && this.getName().equals(other.getName())))
				);
	}
}
