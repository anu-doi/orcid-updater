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

@Entity
@Table(name="email")
public class EmailAddress {
	Logger LOGGER = LoggerFactory.getLogger(EmailAddress.class);
	
	private Long id;
	private Person person;
	private Boolean primary;
	private String email;
	
	public EmailAddress() {
		
	}
	
	public EmailAddress(Boolean primary, String email) {
		this.primary = primary;
		this.email = email;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", referencedColumnName="id", nullable = false)
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Column(name="primary_address")
	public Boolean getPrimary() {
		return primary;
	}

	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}

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
