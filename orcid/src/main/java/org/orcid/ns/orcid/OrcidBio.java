//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.02.05 at 03:55:58 PM EST 
//


package org.orcid.ns.orcid;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}personal-details" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}biography" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}researcher-urls" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}contact-details" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}keywords" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}external-identifiers" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}delegation" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}applications" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.orcid.org/ns/orcid}scope"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "personalDetails",
    "biography",
    "researcherUrls",
    "contactDetails",
    "keywords",
    "externalIdentifiers",
    "delegation",
    "applications"
})
@XmlRootElement(name = "orcid-bio")
public class OrcidBio {

    @XmlElement(name = "personal-details")
    protected PersonalDetails personalDetails;
    protected Biography biography;
    @XmlElement(name = "researcher-urls")
    protected ResearcherUrls researcherUrls;
    @XmlElement(name = "contact-details")
    protected ContactDetails contactDetails;
    protected Keywords keywords;
    @XmlElement(name = "external-identifiers")
    protected ExternalIdentifiers externalIdentifiers;
    protected Delegation delegation;
    protected Applications applications;
    @XmlAttribute(name = "scope")
    protected Scope scope;

    /**
     * Gets the value of the personalDetails property.
     * 
     * @return
     *     possible object is
     *     {@link PersonalDetails }
     *     
     */
    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    /**
     * Sets the value of the personalDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonalDetails }
     *     
     */
    public void setPersonalDetails(PersonalDetails value) {
        this.personalDetails = value;
    }

    /**
     * Gets the value of the biography property.
     * 
     * @return
     *     possible object is
     *     {@link Biography }
     *     
     */
    public Biography getBiography() {
        return biography;
    }

    /**
     * Sets the value of the biography property.
     * 
     * @param value
     *     allowed object is
     *     {@link Biography }
     *     
     */
    public void setBiography(Biography value) {
        this.biography = value;
    }

    /**
     * Gets the value of the researcherUrls property.
     * 
     * @return
     *     possible object is
     *     {@link ResearcherUrls }
     *     
     */
    public ResearcherUrls getResearcherUrls() {
        return researcherUrls;
    }

    /**
     * Sets the value of the researcherUrls property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResearcherUrls }
     *     
     */
    public void setResearcherUrls(ResearcherUrls value) {
        this.researcherUrls = value;
    }

    /**
     * Gets the value of the contactDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ContactDetails }
     *     
     */
    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    /**
     * Sets the value of the contactDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactDetails }
     *     
     */
    public void setContactDetails(ContactDetails value) {
        this.contactDetails = value;
    }

    /**
     * Gets the value of the keywords property.
     * 
     * @return
     *     possible object is
     *     {@link Keywords }
     *     
     */
    public Keywords getKeywords() {
        return keywords;
    }

    /**
     * Sets the value of the keywords property.
     * 
     * @param value
     *     allowed object is
     *     {@link Keywords }
     *     
     */
    public void setKeywords(Keywords value) {
        this.keywords = value;
    }

    /**
     * Gets the value of the externalIdentifiers property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalIdentifiers }
     *     
     */
    public ExternalIdentifiers getExternalIdentifiers() {
        return externalIdentifiers;
    }

    /**
     * Sets the value of the externalIdentifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalIdentifiers }
     *     
     */
    public void setExternalIdentifiers(ExternalIdentifiers value) {
        this.externalIdentifiers = value;
    }

    /**
     * Gets the value of the delegation property.
     * 
     * @return
     *     possible object is
     *     {@link Delegation }
     *     
     */
    public Delegation getDelegation() {
        return delegation;
    }

    /**
     * Sets the value of the delegation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Delegation }
     *     
     */
    public void setDelegation(Delegation value) {
        this.delegation = value;
    }

    /**
     * Gets the value of the applications property.
     * 
     * @return
     *     possible object is
     *     {@link Applications }
     *     
     */
    public Applications getApplications() {
        return applications;
    }

    /**
     * Sets the value of the applications property.
     * 
     * @param value
     *     allowed object is
     *     {@link Applications }
     *     
     */
    public void setApplications(Applications value) {
        this.applications = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link Scope }
     *     
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link Scope }
     *     
     */
    public void setScope(Scope value) {
        this.scope = value;
    }

}
