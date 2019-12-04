package com.weibao.chaopei.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 公司及个人账户提现记录
 * @author dms
 *
 */
@Entity
@Table(name = "wb_withdraw_order")
public class WithdrawOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
	@Column(name ="account_id",nullable=true,length=32)
	private String accountId;

	/**支付宝账号*/
	@Column(name ="alipay_acct",nullable=true,length=100)
	private String alipayAcct;

	/**支付宝实名*/
	@Column(name ="alipay_name",nullable=true,length=255)
	private String alipayName;

	/**提现的银行信息*/
	@Column(name ="bank_info",nullable=true,length=255)
	private String bankInfo;
	
	/**
	 * 本次提现金额
	 */
	@Column(name ="amount",nullable=true,scale=2,length=10)
	private BigDecimal amount;
	
	/**
	 * 提现机构类型：0-机构；1-个人
	 */
	@Column(name ="org_type",nullable=true,length=1)
	private String orgType;
	
	/**
	 * 提现状态：0-未到账；1-已到账；
	 */
	@Column(name ="status",nullable=true,length=1)
	private String status;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAlipayAcct() {
		return alipayAcct;
	}

	public void setAlipayAcct(String alipayAcct) {
		this.alipayAcct = alipayAcct;
	}

	public String getAlipayName() {
		return alipayName;
	}

	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}

	public String getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
