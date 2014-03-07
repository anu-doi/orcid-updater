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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.orcid.ns.orcid.Citation;
import org.orcid.ns.orcid.Contributor;
import org.orcid.ns.orcid.Country;
import org.orcid.ns.orcid.CreditName;
import org.orcid.ns.orcid.JournalTitle;
import org.orcid.ns.orcid.OrcidId;
import org.orcid.ns.orcid.OrcidWork;
import org.orcid.ns.orcid.PublicationDate;
import org.orcid.ns.orcid.Url;
import org.orcid.ns.orcid.Visibility;
import org.orcid.ns.orcid.WorkContributors;
import org.orcid.ns.orcid.WorkExternalIdentifier;
import org.orcid.ns.orcid.WorkExternalIdentifiers;
import org.orcid.ns.orcid.WorkTitle;
import org.orcid.ns.orcid.Year;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Publication</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Entity class for the 'publication' table</p>
 *
 * @author Genevieve Turner
 *
 */
@Entity
@Table(name="publication")
public class Publication {
	static final Logger LOGGER = LoggerFactory.getLogger(Publication.class);
	
	private Long id;
	private String externalIdentifier;
	private String title;
	private String publicationName;
	private String publicationYear;
	private String workType;
	private String issn;
	private String isbn;
	private List<Author> authors = new ArrayList<Author>();
	
	/**
	 * Constructor
	 */
	public Publication() {
		
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
	 * <p>Get the external identifier</p>
	 * <p>The external identifier for Publications is an identifier from the source system.  It is there for identification
	 * purposes so that the record can be updated rather than a new record added</p>
	 * 
	 * @return The external identifier
	 */
	@Column(name="external_id")
	public String getExternalIdentifier() {
		return externalIdentifier;
	}

	/**
	 * Set the external identifier
	 * <p>The external identifier for Publications is an identifier from the source system.  It is there for identification
	 * purposes so that the record can be updated rather than a new record added</p>
	 * 
	 * @param externalIdentifier The external identifier
	 */
	public void setExternalIdentifier(String externalIdentifier) {
		this.externalIdentifier = externalIdentifier;
	}
	
	/**
	 * Get the title
	 * 
	 * @return The title
	 */
	@Column(name="title")
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title
	 * 
	 * @param title The title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the publication name
	 * 
	 * @return The publication name
	 */
	@Column(name="publication_name")
	public String getPublicationName() {
		return publicationName;
	}

	/**
	 * Set the publication name
	 * 
	 * @param publicationName The publication name
	 */
	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}

	/**
	 * Get the publication year
	 * 
	 * @return The publication year
	 */
	@Column(name="publication_year")
	public String getPublicationYear() {
		return publicationYear;
	}

	/**
	 * Set the publication year
	 * 
	 * @param publicationYear The publication year
	 */
	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	/**
	 * Get the work type
	 * 
	 * @return The work type
	 */
	@Column(name="work_type")
	public String getWorkType() {
		return workType;
	}

	/**
	 * Set the work 
	 * 
	 * @param workType The work type
	 */
	public void setWorkType(String workType) {
		this.workType = workType;
	}

	/**
	 * Get the issn
	 * 
	 * @return The issn
	 */
	@Column(name="issn")
	public String getIssn() {
		return issn;
	}

	/**
	 * Set the issn
	 * 
	 * @param issn The issn
	 */
	public void setIssn(String issn) {
		this.issn = issn;
	}

	/**
	 * Get the isbn
	 * 
	 * @return The isbn
	 */
	@Column(name="isbn")
	public String getIsbn() {
		return isbn;
	}

	/**
	 * Set the isbn
	 * 
	 * @param isbn The isbn
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * Get the associated authors
	 * 
	 * @return The authors
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "publication")
	@Cascade(CascadeType.ALL)
	@NotFound(action=NotFoundAction.IGNORE)
	public List<Author> getAuthors() {
		return authors;
	}

	/**
	 * Set the associated authors
	 * 
	 * @param authors The authors
	 */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	/**
	 * Get the OrcidWork
	 * 
	 * @return The work
	 */
	@Transient
	public OrcidWork getWork() {
		OrcidWork work = new OrcidWork();

		work.setCountry(getCountry());
		work.setJournalTitle(getJournalTitle());
		work.setLanguageCode(getLanguageCode());
		work.setPublicationDate(getPublicationDate());
		work.setShortDescription(getShortDescription());
		work.setUrl(getUrl());
		work.setVisibility(getVisibility());
		work.setWorkCitation(getCitation());
		work.setWorkContributors(getContributors());
		work.setWorkExternalIdentifiers(getExternalIdentifiers());
		work.setWorkSource(getSource());
		work.setWorkTitle(getWorkTitle());
		work.setWorkType(getOrcidWorkType());
		
		return work;
	}

	/**
	 * Get the country for ORCID
	 * 
	 * @return The country
	 */
	@Transient
	private Country getCountry() {
		return null;
	}

	/**
	 * Get the jounral title for ORCID
	 * 
	 * @return The journal title
	 */
	@Transient
	private JournalTitle getJournalTitle() {
		if (publicationName != null) {
			JournalTitle journalTitle = new JournalTitle();
			journalTitle.setContent(publicationName);
			return journalTitle;
		}
		return null;
	}

	/**
	 * Get the ORCID language code
	 * 
	 * @return
	 */
	@Transient
	private String getLanguageCode() {
		return null;
	}

	/**
	 * Get the ORCID publciation date
	 * 
	 * @return The publication date
	 */
	@Transient
	private PublicationDate getPublicationDate() {
		if (publicationYear != null) {
			PublicationDate publicationDate = new PublicationDate();
			Year year = new Year();
			year.setValue(publicationYear);
			publicationDate.setYear(year);
			return publicationDate;
		}
		return null;
	}

	/**
	 * Get the  short description for ORCID
	 * 
	 * @return the short description
	 */
	@Transient
	private String getShortDescription() {
		return null;
	}

	/**
	 * Get  an associated URL for ORCID
	 * 
	 * @return The url
	 */
	@Transient
	private Url getUrl() {
		return null;
	}

	/**
	 * Get the Visibility for ORCID
	 * 
	 * @return The visibility
	 */
	@Transient
	private Visibility getVisibility() {
		return null;
	}

	/**
	 * Get the citation for ORCID
	 * 
	 * @return The citation
	 */
	@Transient
	private Citation getCitation() {
		return null;
	}

	/**
	 * Get the contributors (e.g. Authors)
	 * 
	 * @return The contributors
	 */
	@Transient
	private WorkContributors getContributors() {
		WorkContributors contributors = new WorkContributors();
		for (Author author : authors) {
			Contributor contributor = new Contributor();
			CreditName creditName = new CreditName();
			String authorName = author.getName();
			creditName.setValue(authorName);
			contributor.setCreditName(creditName);
			contributors.getContributor().add(contributor);
		}
		if (contributors.getContributor().size() > 0) {
			return contributors;
		}
		return null;
	}

	/**
	 * Get the external identifiers for the publication for ORCID
	 * 
	 * @return The external identifiers
	 */
	@Transient
	private WorkExternalIdentifiers getExternalIdentifiers() {
		WorkExternalIdentifiers identifiers = new WorkExternalIdentifiers();
		
		if (isbn != null) {
			WorkExternalIdentifier identifier = new WorkExternalIdentifier();
			identifier.setWorkExternalIdentifierId(isbn);
			identifier.setWorkExternalIdentifierType("isbn");
			identifiers.getWorkExternalIdentifier().add(identifier);
		}
		if (issn != null) {
			WorkExternalIdentifier identifier = new WorkExternalIdentifier();
			identifier.setWorkExternalIdentifierId(issn);
			identifier.setWorkExternalIdentifierType("issn");
			identifiers.getWorkExternalIdentifier().add(identifier);
		}
		if (identifiers.getWorkExternalIdentifier().size() > 0) {
			return identifiers;
		}
		return null;
	}

	/**
	 * Get the source for ORCID
	 * 
	 * @return
	 */
	@Transient
	private OrcidId getSource() {
		return null;
	}

	/**
	 * Get the work title for ORCID
	 * 
	 * @return
	 */
	@Transient
	private WorkTitle getWorkTitle() {
		WorkTitle workTitle = new WorkTitle();
		workTitle.setTitle(title);
		return workTitle;
	}

	/**
	 * Get the work type for ORCID
	 * 
	 * @return The work type
	 */
	@Transient
	private String getOrcidWorkType() {
		return workType;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (externalIdentifier != null) {
			hashCode = 17 * hashCode + externalIdentifier.hashCode();
		}
		if (title != null) {
			hashCode = 17 * hashCode + title.hashCode();
		}
		if (publicationName != null) {
			hashCode = 17 * hashCode + publicationName.hashCode();
		}
		if (publicationYear != null) {
			hashCode = 17 * hashCode + publicationYear.hashCode();
		}
		if (workType != null) {
			hashCode = 17 * hashCode + workType.hashCode();
		}
		if (issn != null) {
			hashCode = 17 * hashCode + issn.hashCode();
		}
		if (isbn != null) {
			hashCode = 17 * hashCode + isbn.hashCode();
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
		if (!(obj instanceof Publication)) {
			return false;
		}
		Publication other = (Publication) obj;
		boolean isEqual = true;
		// For some reason comparing the lists doesn't appear to return a true when they should.  The containsAll method also has the same issue...
		isEqual = this.getAuthors().size() == other.getAuthors().size();
		Author author = null;
		for (int i = 0; isEqual && i < other.getAuthors().size(); i++) {
			author = other.getAuthors().get(i);
			isEqual = this.getAuthors().contains(author);
		}
		
		return (
				isEqual &&
				((this.getExternalIdentifier() == other.getExternalIdentifier()) || (this.getExternalIdentifier() != null && this.getExternalIdentifier().equals(other.getExternalIdentifier()))) &&
				((this.getTitle() == other.getTitle()) || (this.getTitle() != null && this.getTitle().equals(other.getTitle()))) &&
				((this.getPublicationName() == other.getTitle()) || (this.getTitle() != null && this.getTitle().equals(other.getTitle()))) &&
				((this.getPublicationYear() == other.getPublicationYear()) || (this.getPublicationYear() != null && this.getPublicationYear().equals(other.getPublicationYear()))) &&
				((this.getWorkType() == other.getWorkType()) || (this.getWorkType() != null && this.getWorkType().equals(other.getWorkType()))) &&
				((this.getIssn() == other.getIssn()) || (this.getIssn() != null && this.getIssn().equals(other.getIssn()))) &&
				((this.getIsbn() == other.getIsbn()) || (this.getIsbn() != null && this.getIsbn().equals(other.getIsbn())))
				);
	}
}
