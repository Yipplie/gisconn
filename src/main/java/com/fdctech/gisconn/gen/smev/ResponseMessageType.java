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
 * <p>Java class for ResponseMessageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseMessageType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{urn://x-artefacts-smev-gov-ru/services/service-adapter/types}Message"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ResponseMetadata" type="{urn://x-artefacts-smev-gov-ru/services/service-adapter/types}ResponseMetadataType"/&gt;
 *         &lt;element name="ResponseContent" type="{urn://x-artefacts-smev-gov-ru/services/service-adapter/types}ResponseContentType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseMessageType", propOrder = {
    "responseMetadata",
    "responseContent"
})
public class ResponseMessageType
    extends Message
{

    @XmlElement(name = "ResponseMetadata", required = true)
    protected ResponseMetadataType responseMetadata;
    @XmlElement(name = "ResponseContent", required = true)
    protected ResponseContentType responseContent;

    /**
     * Gets the value of the responseMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseMetadataType }
     *     
     */
    public ResponseMetadataType getResponseMetadata() {
        return responseMetadata;
    }

    /**
     * Sets the value of the responseMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseMetadataType }
     *     
     */
    public void setResponseMetadata(ResponseMetadataType value) {
        this.responseMetadata = value;
    }

    /**
     * Gets the value of the responseContent property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseContentType }
     *     
     */
    public ResponseContentType getResponseContent() {
        return responseContent;
    }

    /**
     * Sets the value of the responseContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseContentType }
     *     
     */
    public void setResponseContent(ResponseContentType value) {
        this.responseContent = value;
    }

}