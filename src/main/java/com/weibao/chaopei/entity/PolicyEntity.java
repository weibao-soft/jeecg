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
	@Column(name ="plan_id",nullable=false,length=32)
	private java.lang.String planId;
	
	/**保单号*/
	@Column(name ="policy_no",nullable=true,length=32)
	private java.lang.String policyNo;
	
	/**批改申请单号*/
	@Column(name ="batch_no",nullable=true,length=32)
	private java.lang.String batchNo;
	
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
	
	/**保费*/
	@Column(name ="premium",nullable=true,scale=2,length=10)
	private java.math.BigDecimal premium;
	
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
	
	/**投保人性质*/
	@Column(name ="holder_nature",nullable=true,length=10)
	private java.lang.String holderNature;
	
	/**投保单位组织机构代码*/
	@Column(name ="holder_org_code",nullable=true,length=24)
	private java.lang.String holderOrgCode;
	
	/**投保单位名称*/
	@Column(name ="holder_comp_name",nullable=true,length=100)
	private java.lang.String holderCompName;
	
	/**投保单位性质*/
	@Column(name ="holder_comp_nature",nullable=true,length=10)
	private java.lang.String holderCompNature;
	
	/**行业类别*/
	@Column(name ="industry_type",nullable=true,length=10)
	private java.lang.String industryType;
	
	/**被保单位组织机构代码*/
	@Column(name ="insured_org_code",nullable=true,length=24)
	private java.lang.String insuredOrgCode;
	
	/**被保单位名称*/
	@Column(name ="insured_comp_name",nullable=true,length=100)
	private java.lang.String insuredCompName;
	
	/**纳税人识别号*/
	@Column(name ="taxpayer_no",nullable=true,length=30)
	private java.lang.String taxpayerNo;
	
	/**普票接收人手机*/
	@Column(name ="receiver_mobile",nullable=true,length=20)
	private java.lang.String receiverMobile;
	
	/**专票公司名称*/
	@Column(name ="comp_name",nullable=true,length=100)
	private java.lang.String compName;
	
	/**专票公司地址*/
	@Column(name ="comp_address",nullable=true,length=200)
	private java.lang.String compAddress;
	
	/**专票公司电话*/
	@Column(name ="comp_phone",nullable=true,length=20)
	private java.lang.String compPhone;
	
	/**开户行*/
	@Column(name ="deposit_bank",nullable=true,length=30)
	private java.lang.String depositBank;
	
	/**银行账号*/
	@Column(name ="bank_account",nullable=true,length=30)
	private java.lang.String bankAccount;
	
	/**专票收件人*/
	@Column(name ="recipients",nullable=true,length=64)
	private java.lang.String recipients;
	
	/**专票收件人电话*/
	@Column(name ="recipients_tel",nullable=true,length=20)
	private java.lang.String recipientsTel;
	
	/**专票收件地址*/
	@Column(name ="reci_address",nullable=true,length=255)
	private java.lang.String reciAddress;
	
	/**用户id*/
	@Column(name ="user_id",nullable=true,length=32)
	private java.lang.String userId;
	
	/**支付状态*/
	@Column(name ="pay_status",nullable=true,length=1)
	private java.lang.String payStatus;
	
	/**支付时间*/
	@Column(name ="pay_time",nullable=true)
	private java.util.Date payTime;
	
	/**分润状态*/
	@Column(name ="reward_status",nullable=true,length=1)
	private java.lang.String rewardStatus;
	
	/**分润时间*/
	@Column(name ="reward_time",nullable=true)
	private java.util.Date rewardTime;
	
	/**创建时间*/
	@Column(name ="create_time",nullable=true)
	private java.util.Date createTime;
	
	/**最后修改时间*/
	@Column(name ="last_update_time",nullable=true)
	private java.util.Date lastUpdateTime;

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

	public java.lang.String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(java.lang.String policyNo) {
		this.policyNo = policyNo;
	}

	public java.lang.String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(java.lang.String batchNo) {
		this.batchNo = batchNo;
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

	public java.lang.String getHolderNature() {
		return holderNature;
	}

	public void setHolderNature(java.lang.String holderNature) {
		this.holderNature = holderNature;
	}

	public java.lang.String getHolderOrgCode() {
		return holderOrgCode;
	}

	public void setHolderOrgCode(java.lang.String holderOrgCode) {
		this.holderOrgCode = holderOrgCode;
	}

	public java.lang.String getHolderCompName() {
		return holderCompName;
	}

	public void setHolderCompName(java.lang.String holderCompName) {
		this.holderCompName = holderCompName;
	}

	public java.lang.String getHolderCompNature() {
		return holderCompNature;
	}

	public void setHolderCompNature(java.lang.String holderCompNature) {
		this.holderCompNature = holderCompNature;
	}

	public java.lang.String getInsuredOrgCode() {
		return insuredOrgCode;
	}

	public void setInsuredOrgCode(java.lang.String insuredOrgCode) {
		this.insuredOrgCode = insuredOrgCode;
	}

	public java.lang.String getInsuredCompName() {
		return insuredCompName;
	}

	public void setInsuredCompName(java.lang.String insuredCompName) {
		this.insuredCompName = insuredCompName;
	}

	public java.lang.String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(java.lang.String industryType) {
		this.industryType = industryType;
	}

	public java.lang.String getTaxpayerNo() {
		return taxpayerNo;
	}

	public void setTaxpayerNo(java.lang.String taxpayerNo) {
		this.taxpayerNo = taxpayerNo;
	}

	public java.lang.String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(java.lang.String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public java.lang.String getCompName() {
		return compName;
	}

	public void setCompName(java.lang.String compName) {
		this.compName = compName;
	}

	public java.lang.String getCompAddress() {
		return compAddress;
	}

	public void setCompAddress(java.lang.String compAddress) {
		this.compAddress = compAddress;
	}

	public java.lang.String getCompPhone() {
		return compPhone;
	}

	public void setCompPhone(java.lang.String compPhone) {
		this.compPhone = compPhone;
	}

	public java.lang.String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(java.lang.String depositBank) {
		this.depositBank = depositBank;
	}

	public java.lang.String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(java.lang.String bankAccount) {
		this.bankAccount = bankAccount;
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

	public java.lang.String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(java.lang.String payStatus) {
		this.payStatus = payStatus;
	}

	public java.util.Date getPayTime() {
		return payTime;
	}

	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}

	public java.lang.String getRewardStatus() {
		return rewardStatus;
	}

	public void setRewardStatus(java.lang.String rewardStatus) {
		this.rewardStatus = rewardStatus;
	}

	public java.util.Date getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(java.util.Date rewardTime) {
		this.rewardTime = rewardTime;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(java.util.Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public java.math.BigDecimal getPremium() {
		return premium;
	}

	public void setPremium(java.math.BigDecimal premium) {
		this.premium = premium;
	}

}
