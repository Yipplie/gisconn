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
 * <p>Java class for CreateGroupIdentity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateGroupIdentity"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FRGUServiceCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FRGUServiceDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FRGUServiceRecipientDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateGroupIdentity", propOrder = {
    "frguServiceCode",
    "frguServiceDescription",
    "frguServiceRecipientDescription"
})
public class CreateGroupIdentity {

    @XmlElement(name = "FRGUServiceCode", required = true)
    protected String frguServiceCode;
    @XmlElement(name = "FRGUServiceDescription")
    protected String frguServiceDescription;
    @XmlElement(name = "FRGUServiceRecipientDescription")
    protected String frguServiceRecipientDescription;

    /**
     * Gets the value of the frguServiceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFRGUServiceCode() {
        return frguServiceCode;
    }

    /**
     * Sets the value of the frguServiceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFRGUServiceCode(String value) {
        this.frguServiceCode = value;
    }

    /**
     * Gets the value of the frguServiceDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFRGUServiceDescription() {
        return frguServiceDescription;
    }

    /**
     * Sets the value of the frguServiceDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFRGUServiceDescription(String value) {
        this.frguServiceDescription = value;
    }

    /**
     * Gets the value of the frguServiceRecipientDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFRGUServiceRecipientDescription() {
        return frguServiceRecipientDescription;
    }

    /**
     * Sets the value of the frguServiceRecipientDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFRGUServiceRecipientDescription(String value) {
        this.frguServiceRecipientDescription = value;
    }

}
