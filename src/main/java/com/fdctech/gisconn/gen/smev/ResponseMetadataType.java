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
 * <p>Java class for ResponseMetadataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseMetadataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{urn://x-artefacts-smev-gov-ru/services/service-adapter/types}Metadata"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="replyToClientId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseMetadataType", propOrder = {
    "replyToClientId"
})
public class ResponseMetadataType
    extends Metadata
{

    @XmlElement(required = true)
    protected String replyToClientId;

    /**
     * Gets the value of the replyToClientId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReplyToClientId() {
        return replyToClientId;
    }

    /**
     * Sets the value of the replyToClientId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReplyToClientId(String value) {
        this.replyToClientId = value;
    }

}
