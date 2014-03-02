package au.edu.anu.orcid.metadatastores.publication;

import javax.xml.bind.annotation.XmlElement;

import au.edu.anu.orcid.db.model.Author;
import au.edu.anu.orcid.db.model.Publication;

public class MSAuthor {
	private String extId;
	private String displayName;
	private String givenName;
	private String organisationalUnit;
	private String staffType;
	private String surname;

	@XmlElement(name="external-identifier")
	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	@XmlElement(name="display-name")
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@XmlElement(name="given-name")
	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	@XmlElement(name="organisational-unit")
	public String getOrganisationalUnit() {
		return organisationalUnit;
	}

	public void setOrganisationalUnit(String organisationalUnit) {
		this.organisationalUnit = organisationalUnit;
	}

	@XmlElement(name="staff-type")
	public String getStaffType() {
		return staffType;
	}

	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	@XmlElement(name="surname")
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Author getAuthor(Publication publication) {
		Author author = new Author();
		author.setExtId(this.getExtId());
		String name = this.getSurname() + ", " + this.getGivenName();
		author.setName(name);
		author.setPublication(publication);
		
		return author;
	}
}
