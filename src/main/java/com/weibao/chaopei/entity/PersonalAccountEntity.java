package com.weibao.chaopei.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 个人账户信息表
 * @author Liut
 *
 */
@Entity
@Table(name = "wb_personal_account")
public class PersonalAccountEntity implements java.io.Serializable {
	private static final long serialVersionUID = -5912078044557868261L;
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
	@Column(name ="user_id",nullable=true,length=32)
	private String userId;
	
	@Column(name ="bank_acct_name",nullable=true,length=32)
	private String bankAcctName;
	
	@Column(name ="bank_no",nullable=true,length=32)
	private String bankNo;
	
	@Column(name ="bank_info",nullable=true,length=32)
	private String bankInfo;
	
	@Column(name ="real_name",nullable=true,length=32)
	private String realName;
	
	@Column(name ="certi_no",nullable=true,length=32)
	private String certiNo;
	
	@Column(name ="withdraw_passwd",nullable=true,length=32)
	private String withdrawPasswd;
	
	@Column(name ="received_balance")
	private BigDecimal receivedBalance;
	
	@Column(name ="unreceived_balance")
	private BigDecimal unreceivedBalance;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBankAcctName() {
		return bankAcctName;
	}
	public void setBankAcctName(String bankAcctName) {
		this.bankAcctName = bankAcctName;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankInfo() {
		return bankInfo;
	}
	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCertiNo() {
		return certiNo;
	}
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}
	public String getWithdrawPasswd() {
		return withdrawPasswd;
	}
	public void setWithdrawPasswd(String withdrawPasswd) {
		this.withdrawPasswd = withdrawPasswd;
	}
	public BigDecimal getReceivedBalance() {
		return receivedBalance;
	}
	public void setReceivedBalance(BigDecimal receivedBalance) {
		this.receivedBalance = receivedBalance;
	}
	public BigDecimal getUnreceivedBalance() {
		return unreceivedBalance;
	}
	public void setUnreceivedBalance(BigDecimal unreceivedBalance) {
		this.unreceivedBalance = unreceivedBalance;
	}
}
