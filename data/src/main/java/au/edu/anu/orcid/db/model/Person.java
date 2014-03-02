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
	
	public Person() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="external_id")
	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	@Column(name="uid")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(name="family_name")
	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	@Column(name="given_name")
	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	@Column(name="credit_name")
	public String getCreditName() {
		return creditName;
	}

	public void setCreditName(String creditName) {
		this.creditName = creditName;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="orcid")
	public String getOrcid() {
		return orcid;
	}

	public void setOrcid(String orcid) {
		this.orcid = orcid;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "person")
	@Cascade(CascadeType.ALL)
	@NotFound(action=NotFoundAction.IGNORE)
	public List<EmailAddress> getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(List<EmailAddress> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}
	
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

	public void setOrcidPublications(Set<Publication> orcidPublications) {
		this.orcidPublications = orcidPublications;
	}
	
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

	@Transient
	private Biography getBiography() {
		Biography biography = new Biography();
		biography.setValue(description);
		return biography;
	}

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

	@Transient
	private ExternalIdentifiers getIdentitifers() {
		return null;
	}

	@Transient
	private Keywords getKeywords() {
		return null;
	}

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
