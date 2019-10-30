package com.weibao.chaopei.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 保单暂存表
 */
@Entity
@Table(name = "wb_insurance_draft", schema = "")
public class DraftEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
	/**保障方案id*/
	@ManyToOne(fetch = FetchType.LAZY, optional=true)
	@JoinColumn(name = "plan_id")
	private ProductDetailEntity productDetailEntity;
	//@Column(name ="plan_id",nullable=true,length=32)
	//private java.lang.String planId;
	
	/**投保单位名称*/
	@Column(name ="holder_comp_name",nullable=true,length=100)
	private java.lang.String holderCompName;
	
	/**专票收件人*/
	@Column(name ="recipients",nullable=true,length=64)
	private java.lang.String recipients;
	
	/**投保车辆(台)*/
	@Column(name ="truck_nums",nullable=true,length=5)
	private java.lang.Integer truckNums;
	
	/**状态*/
	@Column(name ="status",nullable=true,length=1)
	private java.lang.String status;
	
	/**用户id*/
	@Column(name ="user_id",nullable=true,length=32)
	private java.lang.String userId;
	
	/**暂存时间*/
	@Column(name ="save_time",nullable=true)
	private java.util.Date saveTime;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public ProductDetailEntity getProductDetailEntity() {
		return productDetailEntity;
	}

	public void setProductDetailEntity(ProductDetailEntity productDetailEntity) {
		this.productDetailEntity = productDetailEntity;
	}

	public java.lang.String getRecipients() {
		return recipients;
	}

	public void setRecipients(java.lang.String recipients) {
		this.recipients = recipients;
	}

	public java.lang.String getHolderCompName() {
		return holderCompName;
	}

	public void setHolderCompName(java.lang.String holderCompName) {
		this.holderCompName = holderCompName;
	}

	public java.lang.Integer getTruckNums() {
		return truckNums;
	}

	public void setTruckNums(java.lang.Integer truckNums) {
		this.truckNums = truckNums;
	}

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public java.util.Date getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(java.util.Date saveTime) {
		this.saveTime = saveTime;
	}

}
