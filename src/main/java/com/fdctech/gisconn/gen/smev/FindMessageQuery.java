//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.05.29 at 06:44:27 AM YEKT 
//


package com.fdctech.gisconn.gen.smev;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FindMessageQuery complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindMessageQuery"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="itSystem" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="specificQuery" type="{urn://x-artefacts-smev-gov-ru/services/service-adapter/types}FindTypeCriteria"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindMessageQuery", propOrder = {
    "itSystem",
    "specificQuery"
})
public class FindMessageQuery {

    @XmlElement(required = true)
    protected String itSystem;
    @XmlElement(required = true)
    protected FindTypeCriteria specificQuery;

    /**
     * Gets the value of the itSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItSystem() {
        return itSystem;
    }

    /**
     * Sets the value of the itSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItSystem(String value) {
        this.itSystem = value;
    }

    /**
     * Gets the value of the specificQuery property.
     * 
     * @return
     *     possible object is
     *     {@link FindTypeCriteria }
     *     
     */
    public FindTypeCriteria getSpecificQuery() {
        return specificQuery;
    }

    /**
     * Sets the value of the specificQuery property.
     * 
     * @param value
     *     allowed object is
     *     {@link FindTypeCriteria }
     *     
     */
    public void setSpecificQuery(FindTypeCriteria value) {
        this.specificQuery = value;
    }

}