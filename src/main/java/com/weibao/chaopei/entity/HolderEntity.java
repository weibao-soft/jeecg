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
	@Column(name ="holder_nature",nullable=true,length=20)
	private java.lang.String holderNature;
	
	/**投保单位组织机构代码*/
	@Column(name ="org_code",nullable=true,length=40)
	private java.lang.String orgCode;
	
	/**投保单位名称*/
	@Column(name ="comp_name",nullable=true,length=100)
	private java.lang.String compName;
	
	/**投保单位性质*/
	@Column(name ="comp_nature",nullable=true,length=20)
	private java.lang.String compNature;
	
	/**行业类别*/
	@Column(name ="industry_type",nullable=true,length=20)
	private java.lang.String industryType;
	
	/**纳税人识别号*/
	@Column(name ="taxpayer_no",nullable=true,length=30)
	private java.lang.String taxpayerNo;
	
	/**普票接收人手机*/
	@Column(name ="receiver_mobile",nullable=true,length=20)
	private java.lang.String receiverMobile;
	
	/**专票公司名称*/
	@Column(name ="comp_name2",nullable=true,length=100)
	private java.lang.String compName2;
	
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
	@Column(name ="update_time",nullable=true)
	private java.util.Date updateTime;

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

	public java.lang.String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(java.lang.String orgCode) {
		this.orgCode = orgCode;
	}

	public java.lang.String getCompName() {
		return compName;
	}

	public void setCompName(java.lang.String compName) {
		this.compName = compName;
	}

	public java.lang.String getCompNature() {
		return compNature;
	}

	public void setCompNature(java.lang.String compNature) {
		this.compNature = compNature;
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

	public java.lang.String getCompName2() {
		return compName2;
	}

	public void setCompName2(java.lang.String compName2) {
		this.compName2 = compName2;
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

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

}
