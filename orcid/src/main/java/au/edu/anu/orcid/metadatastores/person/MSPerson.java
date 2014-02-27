package au.edu.anu.orcid.metadatastores.person;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.orcid.ns.orcid.Biography;
import org.orcid.ns.orcid.ContactDetails;
import org.orcid.ns.orcid.CreditName;
import org.orcid.ns.orcid.Email;
import org.orcid.ns.orcid.ExternalIdentifiers;
import org.orcid.ns.orcid.Keywords;
import org.orcid.ns.orcid.PersonalDetails;
import org.orcid.ns.orcid.ResearcherUrls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.metadatastores.MSFORSubject;

public class MSPerson {
	Logger LOGGER = LoggerFactory.getLogger(MSPerson.class);
	
	private String uid;
	private String extId;
	private String givenName;
	private String surname;
	private String displayName;
	private String ariesId;
	private String email;
	private List<String> phoneNumbers = new ArrayList<String>();
	private List<String> faxNumbers = new ArrayList<String>();
	private String jobTitle;
	private String preferredName;
	private String staffType;
	private String organisationalUnit;
	private String nlaId;
	private String country;
	private String institution;	
	private String description;
	private String orcid;
	private List<MSFORSubject> subjects = new ArrayList<MSFORSubject>();

	@XmlElement(name="uid")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@XmlElement(name="external-identifier")
	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	@XmlElement(name="given-name")
	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	@XmlElement(name="surname")
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@XmlElement(name="display-name")
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@XmlElement(name="aries-id")
	public String getAriesId() {
		return ariesId;
	}

	public void setAriesId(String ariesId) {
		this.ariesId = ariesId;
	}

	@XmlElement(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement(name="phone")
	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	@XmlElement(name="fax")
	public List<String> getFaxNumbers() {
		return faxNumbers;
	}

	public void setFaxNumbers(List<String> faxNumbers) {
		this.faxNumbers = faxNumbers;
	}

	@XmlElement(name="job-title")
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@XmlElement(name="preferred-name")
	public String getPreferredName() {
		return preferredName;
	}

	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	@XmlElement(name="staff-type")
	public String getStaffType() {
		return staffType;
	}

	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	@XmlElement(name="organisational-unit")
	public String getOrganisationalUnit() {
		return organisationalUnit;
	}

	public void setOrganisationalUnit(String organisationalUnit) {
		this.organisationalUnit = organisationalUnit;
	}

	@XmlElement(name="nla-id")
	public String getNlaId() {
		return nlaId;
	}

	public void setNlaId(String nlaId) {
		this.nlaId = nlaId;
	}

	@XmlElement(name="country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@XmlElement(name="institution")
	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	@XmlElement(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name="orcid")
	public String getOrcid() {
		return orcid;
	}

	public void setOrcid(String orcid) {
		this.orcid = orcid;
	}

	@XmlElement(name="for-subject")
	public List<MSFORSubject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<MSFORSubject> subjects) {
		this.subjects = subjects;
	}
	
	
}
