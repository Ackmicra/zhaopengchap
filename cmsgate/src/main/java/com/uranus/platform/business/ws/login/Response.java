package com.uranus.platform.business.ws.login;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for response complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;response&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;content&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;respCode&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;respDesc&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "response", propOrder = { "content", "respCode", "respDesc" })
public class Response {

	protected String content;
	protected String respCode;
	protected String respDesc;

	/**
	 * Gets the value of the content property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the value of the content property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setContent(String value) {
		this.content = value;
	}

	/**
	 * Gets the value of the respCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRespCode() {
		return respCode;
	}

	/**
	 * Sets the value of the respCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRespCode(String value) {
		this.respCode = value;
	}

	/**
	 * Gets the value of the respDesc property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRespDesc() {
		return respDesc;
	}

	/**
	 * Sets the value of the respDesc property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRespDesc(String value) {
		this.respDesc = value;
	}

}
