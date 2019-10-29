package com.weibao.chaopei.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 投保人信息表
 */
@Entity
@Table(name = "wb_policy_holder", schema = "")
public class HolderEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
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
	
	/**修改时间*/
	@Column(name ="last_update_time",nullable=true)
	private java.util.Date lastUpdateTime;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
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

	public java.util.Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(java.util.Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
