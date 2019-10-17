package com.weibao.chaopei.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 保单信息表
 */
@Entity
@Table(name = "wb_insurance_policy", schema = "")
public class PolicyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
	/**产品id*/
	@Column(name ="prod_id",nullable=true,length=32)
	private java.lang.String prodId;
	
	/**保障方案id*/
	@Column(name ="plan_id",nullable=true,length=32)
	private java.lang.String planId;
	
	/**车牌号*/
	@Column(name ="plate_no",nullable=true,length=20)
	private java.lang.String plateNo;
	
	/**车架号*/
	@Column(name ="frame_no",nullable=true,length=30)
	private java.lang.String frameNo;
	
	/**发动机号*/
	@Column(name ="engine_no",nullable=true,length=30)
	private java.lang.String engineNo;
	
	/**保险开始日期*/
	@Column(name ="start_date",nullable=true)
	private java.util.Date startDate;
	
	/**保险结束日期*/
	@Column(name ="end_date",nullable=true)
	private java.util.Date endDate;
	
	/**保单状态*/
	@Column(name ="status",nullable=true,length=1)
	private java.lang.String status;
	
	/**投保联系人*/
	@Column(name ="contact_name",nullable=true,length=30)
	private java.lang.String contactName;
	
	/**保单接收手机*/
	@Column(name ="policy_mobile",nullable=true,length=20)
	private java.lang.String policyMobile;
	
	/**发票类型*/
	@Column(name ="invoice_type",nullable=true,length=1)
	private java.lang.String invoiceType;
	
	/**投保人id*/
	@Column(name ="holder_id",nullable=true,length=32)
	private java.lang.String holderId;
	
	/**被投保人id*/
	@Column(name ="insured_id",nullable=true,length=32)
	private java.lang.String insuredId;
	
	/**收件人id*/
	@Column(name ="recipients_id",nullable=true,length=32)
	private java.lang.String recipientsId;
	
	/**用户id*/
	@Column(name ="user_id",nullable=true,length=32)
	private java.lang.String userId;
	
	/**创建时间*/
	@Column(name ="create_time",nullable=true)
	private java.util.Date createTime;
	
	/**修改时间*/
	@Column(name ="update_time",nullable=true)
	private java.util.Date updateTime;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getProdId() {
		return prodId;
	}

	public void setProdId(java.lang.String prodId) {
		this.prodId = prodId;
	}

	public java.lang.String getPlanId() {
		return planId;
	}

	public void setPlanId(java.lang.String planId) {
		this.planId = planId;
	}

	public java.lang.String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(java.lang.String plateNo) {
		this.plateNo = plateNo;
	}

	public java.lang.String getFrameNo() {
		return frameNo;
	}

	public void setFrameNo(java.lang.String frameNo) {
		this.frameNo = frameNo;
	}

	public java.lang.String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(java.lang.String engineNo) {
		this.engineNo = engineNo;
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getContactName() {
		return contactName;
	}

	public void setContactName(java.lang.String contactName) {
		this.contactName = contactName;
	}

	public java.lang.String getPolicyMobile() {
		return policyMobile;
	}

	public void setPolicyMobile(java.lang.String policyMobile) {
		this.policyMobile = policyMobile;
	}

	public java.lang.String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(java.lang.String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public java.lang.String getHolderId() {
		return holderId;
	}

	public void setHolderId(java.lang.String holderId) {
		this.holderId = holderId;
	}

	public java.lang.String getInsuredId() {
		return insuredId;
	}

	public void setInsuredId(java.lang.String insuredId) {
		this.insuredId = insuredId;
	}

	public java.lang.String getRecipientsId() {
		return recipientsId;
	}

	public void setRecipientsId(java.lang.String recipientsId) {
		this.recipientsId = recipientsId;
	}

	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

}
