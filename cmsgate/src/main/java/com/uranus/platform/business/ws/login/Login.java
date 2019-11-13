package com.uranus.platform.business.ws.login;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for login complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;login&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;request&quot; type=&quot;{http://ws.inf.creditapp.app/}loginRequest&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "login", propOrder = { "request" })
public class Login {

	protected LoginRequest request;

	/**
	 * Gets the value of the request property.
	 * 
	 * @return possible object is {@link LoginRequest }
	 * 
	 */
	public LoginRequest getRequest() {
		return request;
	}

	/**
	 * Sets the value of the request property.
	 * 
	 * @param value
	 *            allowed object is {@link LoginRequest }
	 * 
	 */
	public void setRequest(LoginRequest value) {
		this.request = value;
	}

}
