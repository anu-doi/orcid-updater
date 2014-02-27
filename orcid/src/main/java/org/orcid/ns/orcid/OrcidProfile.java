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
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}orcid-identifier" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}orcid-deprecated" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}orcid-preferences" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}orcid-history" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}orcid-bio" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}orcid-activities" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}orcid-internal" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://www.orcid.org/ns/orcid}orcid-type" default="user" />
 *       &lt;attribute name="client-type" type="{http://www.orcid.org/ns/orcid}client-type" />
 *       &lt;attribute name="group-type" type="{http://www.orcid.org/ns/orcid}group-type" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "orcidIdentifier",
    "orcidDeprecated",
    "orcidPreferences",
    "orcidHistory",
    "orcidBio",
    "orcidActivities",
    "orcidInternal"
})
@XmlRootElement(name = "orcid-profile")
public class OrcidProfile {

    @XmlElement(name = "orcid-identifier")
    protected OrcidId orcidIdentifier;
    @XmlElement(name = "orcid-deprecated")
    protected OrcidDeprecated orcidDeprecated;
    @XmlElement(name = "orcid-preferences")
    protected OrcidPreferences orcidPreferences;
    @XmlElement(name = "orcid-history")
    protected OrcidHistory orcidHistory;
    @XmlElement(name = "orcid-bio")
    protected OrcidBio orcidBio;
    @XmlElement(name = "orcid-activities")
    protected OrcidActivities orcidActivities;
    @XmlElement(name = "orcid-internal")
    protected OrcidInternal orcidInternal;
    @XmlAttribute(name = "type")
    protected OrcidType type;
    @XmlAttribute(name = "client-type")
    protected ClientType clientType;
    @XmlAttribute(name = "group-type")
    protected GroupType groupType;

    /**
     * Gets the value of the orcidIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link OrcidId }
     *     
     */
    public OrcidId getOrcidIdentifier() {
        return orcidIdentifier;
    }

    /**
     * Sets the value of the orcidIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrcidId }
     *     
     */
    public void setOrcidIdentifier(OrcidId value) {
        this.orcidIdentifier = value;
    }

    /**
     * Gets the value of the orcidDeprecated property.
     * 
     * @return
     *     possible object is
     *     {@link OrcidDeprecated }
     *     
     */
    public OrcidDeprecated getOrcidDeprecated() {
        return orcidDeprecated;
    }

    /**
     * Sets the value of the orcidDeprecated property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrcidDeprecated }
     *     
     */
    public void setOrcidDeprecated(OrcidDeprecated value) {
        this.orcidDeprecated = value;
    }

    /**
     * Gets the value of the orcidPreferences property.
     * 
     * @return
     *     possible object is
     *     {@link OrcidPreferences }
     *     
     */
    public OrcidPreferences getOrcidPreferences() {
        return orcidPreferences;
    }

    /**
     * Sets the value of the orcidPreferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrcidPreferences }
     *     
     */
    public void setOrcidPreferences(OrcidPreferences value) {
        this.orcidPreferences = value;
    }

    /**
     * Gets the value of the orcidHistory property.
     * 
     * @return
     *     possible object is
     *     {@link OrcidHistory }
     *     
     */
    public OrcidHistory getOrcidHistory() {
        return orcidHistory;
    }

    /**
     * Sets the value of the orcidHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrcidHistory }
     *     
     */
    public void setOrcidHistory(OrcidHistory value) {
        this.orcidHistory = value;
    }

    /**
     * Gets the value of the orcidBio property.
     * 
     * @return
     *     possible object is
     *     {@link OrcidBio }
     *     
     */
    public OrcidBio getOrcidBio() {
        return orcidBio;
    }

    /**
     * Sets the value of the orcidBio property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrcidBio }
     *     
     */
    public void setOrcidBio(OrcidBio value) {
        this.orcidBio = value;
    }

    /**
     * Gets the value of the orcidActivities property.
     * 
     * @return
     *     possible object is
     *     {@link OrcidActivities }
     *     
     */
    public OrcidActivities getOrcidActivities() {
        return orcidActivities;
    }

    /**
     * Sets the value of the orcidActivities property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrcidActivities }
     *     
     */
    public void setOrcidActivities(OrcidActivities value) {
        this.orcidActivities = value;
    }

    /**
     * Gets the value of the orcidInternal property.
     * 
     * @return
     *     possible object is
     *     {@link OrcidInternal }
     *     
     */
    public OrcidInternal getOrcidInternal() {
        return orcidInternal;
    }

    /**
     * Sets the value of the orcidInternal property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrcidInternal }
     *     
     */
    public void setOrcidInternal(OrcidInternal value) {
        this.orcidInternal = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link OrcidType }
     *     
     */
    public OrcidType getType() {
        if (type == null) {
            return OrcidType.USER;
        } else {
            return type;
        }
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrcidType }
     *     
     */
    public void setType(OrcidType value) {
        this.type = value;
    }

    /**
     * Gets the value of the clientType property.
     * 
     * @return
     *     possible object is
     *     {@link ClientType }
     *     
     */
    public ClientType getClientType() {
        return clientType;
    }

    /**
     * Sets the value of the clientType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientType }
     *     
     */
    public void setClientType(ClientType value) {
        this.clientType = value;
    }

    /**
     * Gets the value of the groupType property.
     * 
     * @return
     *     possible object is
     *     {@link GroupType }
     *     
     */
    public GroupType getGroupType() {
        return groupType;
    }

    /**
     * Sets the value of the groupType property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupType }
     *     
     */
    public void setGroupType(GroupType value) {
        this.groupType = value;
    }

}
