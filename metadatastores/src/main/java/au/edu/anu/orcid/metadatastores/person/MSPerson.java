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
package au.edu.anu.orcid.metadatastores.person;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.model.EmailAddress;
import au.edu.anu.orcid.db.model.Person;
import au.edu.anu.orcid.metadatastores.misc.MSFORSubject;

/**
 * <p>MSPerson</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Object for unmarhsalling data from metadata stores about people</p>
 *
 * @author Genevieve Turner
 *
 */
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

	/**
	 * Get the university id
	 * @return
	 */
	@XmlElement(name="uid")
	public String getUid() {
		return uid;
	}

	/**
	 * Set the university id
	 * 
	 * @param uid The university id
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

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
	 * @param displayName The display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Get the aries id
	 * 
	 * @return The aries id
	 */
	@XmlElement(name="aries-id")
	public String getAriesId() {
		return ariesId;
	}

	/**
	 * Set the aries id
	 * 
	 * @param ariesId The aries id
	 */
	public void setAriesId(String ariesId) {
		this.ariesId = ariesId;
	}

	/**
	 * Get the email address
	 * 
	 * @return The email address
	 */
	@XmlElement(name="email")
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

	/**
	 * Get the phone numbers
	 * 
	 * @return The phone numbers
	 */
	@XmlElement(name="phone")
	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	/**
	 * Set the phone numbers
	 * 
	 * @param phoneNumbers The phone numbers
	 */
	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	/**
	 * Get the fax numbers
	 * 
	 * @return The fax numbers
	 */
	@XmlElement(name="fax")
	public List<String> getFaxNumbers() {
		return faxNumbers;
	}

	/**
	 * Set the fax numbers
	 * 
	 * @param faxNumbers The fax numbers
	 */
	public void setFaxNumbers(List<String> faxNumbers) {
		this.faxNumbers = faxNumbers;
	}

	/**
	 * Get the job title
	 * 
	 * @return  The job title
	 */
	@XmlElement(name="job-title")
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * Set the job title
	 * 
	 * @param jobTitle The job title
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * Get the preferred name
	 * 
	 * @return  The preferred name
	 */
	@XmlElement(name="preferred-name")
	public String getPreferredName() {
		return preferredName;
	}

	/**
	 * Set the preferred name
	 * 
	 * @param preferredName The preferred name
	 */
	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
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
	 * Get the nla id
	 * 
	 * @return The nla id
	 */
	@XmlElement(name="nla-id")
	public String getNlaId() {
		return nlaId;
	}

	/**
	 * Set the nla id
	 * 
	 * @param nlaId The nla id
	 */
	public void setNlaId(String nlaId) {
		this.nlaId = nlaId;
	}

	/**
	 * Get the country
	 * 
	 * @return The country
	 */
	@XmlElement(name="country")
	public String getCountry() {
		return country;
	}

	/**
	 * Set the country
	 * 
	 * @param country The country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Get the institution
	 * 
	 * @return The institution
	 */
	@XmlElement(name="institution")
	public String getInstitution() {
		return institution;
	}

	/**
	 * Set the institution
	 * 
	 * @param institution The institution
	 */
	public void setInstitution(String institution) {
		this.institution = institution;
	}

	/**
	 * Get the descriptor
	 * 
	 * @return The descriptor
	 */
	@XmlElement(name="description")
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
	 * Get hte orcid
	 * 
	 * @return The orcid
	 */
	@XmlElement(name="orcid")
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
	 * Get the field of resarch subjects
	 * 
	 * @return The field of resarch subjects
	 */
	@XmlElement(name="for-subject")
	public List<MSFORSubject> getSubjects() {
		return subjects;
	}

	/**
	 * Get the field of research subjects
	 * 
	 * @param subjects  The field of research subjects
	 */
	public void setSubjects(List<MSFORSubject> subjects) {
		this.subjects = subjects;
	}
	
	/**
	 * Transforms the metadata stores person object into a person object suitable for saving to the
	 * orcid-updater database.
	 * 
	 * @return The Person object
	 */
	public Person getPerson() {
		Person person = new Person();
		
		person.setExtId(this.getExtId());
		person.setUid(this.getUid());
		person.setFamilyName(this.getSurname());
		person.setGivenName(this.getGivenName());
		person.setCreditName(this.getDisplayName());
		person.setDescription(this.getDescription());
		person.setOrcid(this.getOrcid());
		List<EmailAddress> emails = person.getEmailAddresses();
		
		String uEmail = uid + "@anu.edu.au";
		EmailAddress primaryEmail = new EmailAddress();
		primaryEmail.setPrimary(Boolean.TRUE);
		primaryEmail.setEmail(uEmail);
		primaryEmail.setPerson(person);
		emails.add(primaryEmail);
		
		if (!uEmail.equals(this.getEmail())) {
			EmailAddress secondaryEmail = new EmailAddress();
			secondaryEmail.setPrimary(Boolean.FALSE);
			secondaryEmail.setEmail(this.getEmail());
			secondaryEmail.setPerson(person);
			emails.add(secondaryEmail);
		}
		
		/*EmailAddress primaryEmail = new EmailAddress();
		primaryEmail.setPrimary(Boolean.TRUE);
		primaryEmail.setEmail(this.getEmail());
		primaryEmail.setPerson(person);
		emails.add(primaryEmail);
		
		String uEmail = uid + "@anu.edu.au";
		
		if (!uEmail.equals(this.getEmail())) {
			EmailAddress secondaryEmail = new EmailAddress();
			secondaryEmail.setPrimary(Boolean.FALSE);
			secondaryEmail.setEmail(uEmail);
			secondaryEmail.setPerson(person);
			emails.add(secondaryEmail);
		}*/
		
		return person;
	}
}
