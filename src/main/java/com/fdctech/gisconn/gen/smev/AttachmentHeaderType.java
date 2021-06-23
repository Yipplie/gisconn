//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.05.29 at 06:44:27 AM YEKT 
//


package com.fdctech.gisconn.gen.smev;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for AttachmentHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachmentHeaderType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="filePath" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="passportId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SignaturePKCS7" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="TransferMethod" type="{urn://x-artefacts-smev-gov-ru/services/service-adapter/types}TransferMethodType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachmentHeaderType", propOrder = {
    "id",
    "filePath",
    "passportId",
    "signaturePKCS7",
    "transferMethod"
})
public class AttachmentHeaderType {

    @XmlElement(name = "Id")
    protected String id;
    @XmlElement(required = true)
    protected String filePath;
    protected String passportId;
    @XmlElement(name = "SignaturePKCS7")
    protected byte[] signaturePKCS7;
    @XmlElement(name = "TransferMethod")
    @XmlSchemaType(name = "string")
    protected TransferMethodType transferMethod;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the filePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the value of the filePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilePath(String value) {
        this.filePath = value;
    }

    /**
     * Gets the value of the passportId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassportId() {
        return passportId;
    }

    /**
     * Sets the value of the passportId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassportId(String value) {
        this.passportId = value;
    }

    /**
     * Gets the value of the signaturePKCS7 property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSignaturePKCS7() {
        return signaturePKCS7;
    }

    /**
     * Sets the value of the signaturePKCS7 property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSignaturePKCS7(byte[] value) {
        this.signaturePKCS7 = value;
    }

    /**
     * Gets the value of the transferMethod property.
     * 
     * @return
     *     possible object is
     *     {@link TransferMethodType }
     *     
     */
    public TransferMethodType getTransferMethod() {
        return transferMethod;
    }

    /**
     * Sets the value of the transferMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransferMethodType }
     *     
     */
    public void setTransferMethod(TransferMethodType value) {
        this.transferMethod = value;
    }

}
