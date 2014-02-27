//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.02.05 at 03:55:58 PM EST 
//


package org.orcid.ns.orcid;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for citation-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="citation-type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="formatted-unspecified"/>
 *     &lt;enumeration value="bibtex"/>
 *     &lt;enumeration value="ris"/>
 *     &lt;enumeration value="formatted-apa"/>
 *     &lt;enumeration value="formatted-harvard"/>
 *     &lt;enumeration value="formatted-ieee"/>
 *     &lt;enumeration value="formatted-mla"/>
 *     &lt;enumeration value="formatted-vancouver"/>
 *     &lt;enumeration value="formatted-chicago"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "citation-type")
@XmlEnum
public enum CitationType {

    @XmlEnumValue("formatted-unspecified")
    FORMATTED_UNSPECIFIED("formatted-unspecified"),
    @XmlEnumValue("bibtex")
    BIBTEX("bibtex"),
    @XmlEnumValue("ris")
    RIS("ris"),
    @XmlEnumValue("formatted-apa")
    FORMATTED_APA("formatted-apa"),
    @XmlEnumValue("formatted-harvard")
    FORMATTED_HARVARD("formatted-harvard"),
    @XmlEnumValue("formatted-ieee")
    FORMATTED_IEEE("formatted-ieee"),
    @XmlEnumValue("formatted-mla")
    FORMATTED_MLA("formatted-mla"),
    @XmlEnumValue("formatted-vancouver")
    FORMATTED_VANCOUVER("formatted-vancouver"),
    @XmlEnumValue("formatted-chicago")
    FORMATTED_CHICAGO("formatted-chicago");
    private final String value;

    CitationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CitationType fromValue(String v) {
        for (CitationType c: CitationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
