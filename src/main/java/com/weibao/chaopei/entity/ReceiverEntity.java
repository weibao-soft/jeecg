package com.weibao.chaopei.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 发票收件人信息表
 */
@Entity
@Table(name = "wb_invoice_receiver", schema = "")
public class ReceiverEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
	/**专票收件人*/
	@Column(name ="recipients",nullable=true,length=64)
	private java.lang.String recipients;
	
	/**专票收件人电话*/
	@Column(name ="recipients_tel",nullable=true,length=20)
	private java.lang.String recipientsTel;
	
	/**专票收件地址*/
	@Column(name ="reci_address",nullable=true,length=200)
	private java.lang.String reciAddress;

	/**用户id*/
	@Column(name ="user_id",nullable=true,length=32)
	private java.lang.String userId;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getRecipients() {
		return recipients;
	}

	public void setRecipients(java.lang.String recipients) {
		this.recipients = recipients;
	}

	public java.lang.String getRecipientsTel() {
		return recipientsTel;
	}

	public void setRecipientsTel(java.lang.String recipientsTel) {
		this.recipientsTel = recipientsTel;
	}

	public java.lang.String getReciAddress() {
		return reciAddress;
	}
	
	public void setReciAddress(java.lang.String reciAddress) {
		this.reciAddress = reciAddress;
	}

	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

}
