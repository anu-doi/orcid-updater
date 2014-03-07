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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.orcid.ns.orcid.Biography;
import org.orcid.ns.orcid.ContactDetails;
import org.orcid.ns.orcid.CreditName;
import org.orcid.ns.orcid.Email;
import org.orcid.ns.orcid.ExternalIdentifiers;
import org.orcid.ns.orcid.Keywords;
import org.orcid.ns.orcid.OrcidBio;
import org.orcid.ns.orcid.PersonalDetails;
import org.orcid.ns.orcid.ResearcherUrls;

/**
 * <p>Person</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Entity class for the 'person' table</p>
 *
 * @author Genevieve Turner
 *
 */
@Entity
@Table(name="person")
public class Person {
	private Long id;
	private String extId;
	private String uid;
	private String familyName;
	private String givenName;
	private String creditName;
	private String description;
	private String orcid;
	private List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();
	private Set<Publication> orcidPublications = new HashSet<Publication>();
	
	/**
	 * Constructor
	 */
	public Person() {
		
	}

	/**
	 * Get the id
	 * 
	 * @return The id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	 * <p>The intended use of the external identifier is to be able to provide the ability to match to people in
	 * other classes/tables.  As an example {@link Author}</p>
	 * 
	 * @return The external identifier
	 */
	@Column(name="external_id")
	public String getExtId() {
		return extId;
	}

	/**
	 * <p>Set the external identifier</p>
	 * <p>The intended use of the external identifier is to be able to provide the ability to match to people in
	 * other classes/tables.  As an example {@link Author}</p>
	 * 
	 * @param extId The external identifier
	 */
	public void setExtId(String extId) {
		this.extId = extId;
	}

	/**
	 * <p>Get the uid.</p>
	 * <p>The uid is essentially a unique identifier internal to the university so that it is known on the university
	 * side who to find/update</p>
	 * 
	 * @return The uid
	 */
	@Column(name="uid")
	public String getUid() {
		return uid;
	}

	/**
	 * <p>Set the uid</p>
	 * <p>The uid is essentially a unique identifier internal to the university so that it is known on the university
	 * side who to find/update</p>
	 * 
	 * @param uid The uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * Get the family name
	 * 
	 * @return The family name
	 */
	@Column(name="family_name")
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * Set the family name
	 * 
	 * @param familyName The family name
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	/**
	 * Get the given name
	 * 
	 * @return The given name
	 */
	@Column(name="given_name")
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
	 * Get the credit name
	 * 
	 * @return The credit name
	 */
	@Column(name="credit_name")
	public String getCreditName() {
		return creditName;
	}

	/**
	 * Set the credit name
	 * 
	 * @param creditName The credit name
	 */
	public void setCreditName(String creditName) {
		this.creditName = creditName;
	}

	/**
	 * Get the description
	 * 
	 * @return The description
	 */
	@Column(name="description")
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description
	 * 
	 * @param description The description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the orcid
	 * 
	 * @return The orcid
	 */
	@Column(name="orcid")
	public String getOrcid() {
		return orcid;
	}

	/**
	 * Set the orcid
	 * 
	 * @param orcid The orcid
	 */
	public void setOrcid(String orcid) {
		this.orcid = orcid;
	}

	/**
	 * Get the associated email address
	 * 
	 * @return The email addresses
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "person")
	@Cascade(CascadeType.ALL)
	@NotFound(action=NotFoundAction.IGNORE)
	public List<EmailAddress> getEmailAddresses() {
		return emailAddresses;
	}

	/**
	 * Set the email addresses
	 * 
	 * @param emailAddresses The email addresses
	 */
	public void setEmailAddresses(List<EmailAddress> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}
	
	/**
	 * Get the associated publications
	 * 
	 * @return The publications
	 */
	//@ManyToMany(fetch = FetchType.LAZY, ma)
	@ManyToMany(targetEntity=Publication.class)
	@JoinTable(
			name="publication_orcid",
			joinColumns=@JoinColumn(name="person_id",referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="publication_id", referencedColumnName="id")
			)
	public Set<Publication> getOrcidPublications() {
		return orcidPublications;
	}

	/**
	 * Set the associated publications
	 * 
	 * @param orcidPublications The publications
	 */
	public void setOrcidPublications(Set<Publication> orcidPublications) {
		this.orcidPublications = orcidPublications;
	}
	
	/**
	 * Get the person's BIO information
	 * 
	 * @return The biography
	 */
	@Transient
	public OrcidBio getBio() {
		OrcidBio bio = new OrcidBio();
		
		bio.setBiography(getBiography());
		bio.setContactDetails(getContactDetails());
		bio.setExternalIdentifiers(getIdentitifers());
		bio.setKeywords(getKeywords());
		bio.setPersonalDetails(getPersonalDetails());
		bio.setResearcherUrls(getResearchUrls());
		
		return bio;
	}

	/**
	 * Get the biography
	 * 
	 * @return The biography
	 */
	@Transient
	private Biography getBiography() {
		Biography biography = new Biography();
		biography.setValue(description);
		return biography;
	}

	/**
	 * Get the contact details
	 * 
	 * @return The contact details
	 */
	@Transient
	private ContactDetails getContactDetails() {
		ContactDetails contactDetails = new ContactDetails();
		
		for (EmailAddress emailAddress : emailAddresses) {
			Email email = new Email();
			email.setPrimary(emailAddress.getPrimary());
			email.setValue(emailAddress.getEmail());
			contactDetails.getEmail().add(email);
		}
		
		return contactDetails;
	}

	/**
	 * Get the external identifiers
	 * 
	 * @return The identifiers
	 */
	@Transient
	private ExternalIdentifiers getIdentitifers() {
		return null;
	}

	/**
	 * Get the keywords
	 * 
	 * @return The keywords
	 */
	@Transient
	private Keywords getKeywords() {
		return null;
	}

	/**
	 * Get the personal details
	 * 
	 * @return The personal details
	 */
	@Transient
	private PersonalDetails getPersonalDetails() {
		PersonalDetails personalDetails = new PersonalDetails();
		CreditName creditNameObj = new CreditName();
		creditNameObj.setValue(creditName);
		personalDetails.setCreditName(creditNameObj);
		personalDetails.setFamilyName(familyName);
		personalDetails.setGivenNames(givenName);
		return personalDetails;
	}

	/**
	 * Get the research urls
	 * 
	 * @return The reseach urls
	 */
	@Transient
	private ResearcherUrls getResearchUrls() {
		return null;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;

		if (extId != null) {
			hashCode = 17 * hashCode + extId.hashCode();
		}
		if (uid != null) {
			hashCode = 17 * hashCode + uid.hashCode();
		}
		if (familyName != null) {
			hashCode = 17 * hashCode + familyName.hashCode();
		}
		if (givenName != null) {
			hashCode = 17 * hashCode + givenName.hashCode();
		}
		if (description != null) {
			hashCode = 17 * hashCode + description.hashCode();
		}
		if (description != null) {
			hashCode = 17 * hashCode + description.hashCode();
		}
		if (orcid != null) {
			hashCode = 17 * hashCode + orcid.hashCode();
		}
		if (emailAddresses != null) {
			hashCode = 17 * hashCode + emailAddresses.hashCode();
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
		Person other = (Person) obj;
		boolean isEqual = true;
		// For some reason comparing the lists doesn't appear to return a true when they should.  The containsAll method also has the same issue...
		isEqual = this.getEmailAddresses().size() == other.getEmailAddresses().size();
		EmailAddress email = null;
		
		for (int i = 0; isEqual && i < other.getEmailAddresses().size(); i++) {
			email = other.getEmailAddresses().get(i);
			isEqual = this.getEmailAddresses().contains(email);
		}
		return (
				isEqual &&
				((this.getExtId() == other.getExtId()) || (this.getExtId() != null && this.getExtId().equals(other.getExtId()))) &&
				((this.getUid() == other.getUid()) || (this.getUid() != null && this.getUid().equals(other.getUid()))) &&
				((this.getFamilyName() == other.getFamilyName()) || (this.getFamilyName() != null && this.getFamilyName().equals(other.getFamilyName()))) &&
				((this.getGivenName() == other.getFamilyName()) || (this.getGivenName() != null && this.getGivenName().equals(other.getGivenName()))) &&
				((this.getDescription() == other.getDescription()) || (this.getDescription() != null && this.getDescription().equals(other.getDescription()))) &&
				((this.getOrcid() == other.getOrcid()) || (this.getOrcid() != null && this.getOrcid().equals(other.getOrcid())))
				);
	}
}
