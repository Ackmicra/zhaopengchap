package com.uranus.platform.business.ws.process;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;


/**
 * <p>request complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="request">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="brNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reqDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reqSerial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reqTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="txCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "request", propOrder = {
    "brNo",
    "content",
    "reqDate",
    "reqSerial",
    "reqTime",
    "token",
    "txCode"
})
@Data
public class Request {

    protected String brNo;
    protected String content;
    protected String reqDate;
    protected String reqSerial;
    protected String reqTime;
    protected String token;
    protected String txCode;

    /**
     * 获取brNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrNo() {
        return brNo;
    }

    /**
     * 设置brNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrNo(String value) {
        this.brNo = value;
    }

    /**
     * 获取content属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置content属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * 获取reqDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqDate() {
        return reqDate;
    }

    /**
     * 设置reqDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqDate(String value) {
        this.reqDate = value;
    }

    /**
     * 获取reqSerial属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqSerial() {
        return reqSerial;
    }

    /**
     * 设置reqSerial属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqSerial(String value) {
        this.reqSerial = value;
    }

    /**
     * 获取reqTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqTime() {
        return reqTime;
    }

    /**
     * 设置reqTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqTime(String value) {
        this.reqTime = value;
    }

    /**
     * 获取token属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置token属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * 获取txCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxCode() {
        return txCode;
    }

    /**
     * 设置txCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxCode(String value) {
        this.txCode = value;
    }

}
