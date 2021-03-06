//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.02.05 at 03:55:58 PM EST 
//


package org.orcid.ns.orcid;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}orcid-works" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}orcid-grants" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}orcid-patents" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "orcidWorks",
    "orcidGrants",
    "orcidPatents"
})
@XmlRootElement(name = "orcid-activities")
public class OrcidActivities {

    @XmlElement(name = "orcid-works")
    protected OrcidWorks orcidWorks;
    @XmlElement(name = "orcid-grants")
    protected OrcidGrants orcidGrants;
    @XmlElement(name = "orcid-patents")
    protected OrcidPatents orcidPatents;

    /**
     * Gets the value of the orcidWorks property.
     * 
     * @return
     *     possible object is
     *     {@link OrcidWorks }
     *     
     */
    public OrcidWorks getOrcidWorks() {
        return orcidWorks;
    }

    /**
     * Sets the value of the orcidWorks property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrcidWorks }
     *     
     */
    public void setOrcidWorks(OrcidWorks value) {
        this.orcidWorks = value;
    }

    /**
     * Gets the value of the orcidGrants property.
     * 
     * @return
     *     possible object is
     *     {@link OrcidGrants }
     *     
     */
    public OrcidGrants getOrcidGrants() {
        return orcidGrants;
    }

    /**
     * Sets the value of the orcidGrants property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrcidGrants }
     *     
     */
    public void setOrcidGrants(OrcidGrants value) {
        this.orcidGrants = value;
    }

    /**
     * Gets the value of the orcidPatents property.
     * 
     * @return
     *     possible object is
     *     {@link OrcidPatents }
     *     
     */
    public OrcidPatents getOrcidPatents() {
        return orcidPatents;
    }

    /**
     * Sets the value of the orcidPatents property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrcidPatents }
     *     
     */
    public void setOrcidPatents(OrcidPatents value) {
        this.orcidPatents = value;
    }

}
