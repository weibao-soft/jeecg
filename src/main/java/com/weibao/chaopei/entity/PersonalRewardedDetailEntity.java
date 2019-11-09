package com.weibao.chaopei.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 个人账户分润明细表（存款单）
 * @author Liut
 *
 */
@Entity
@Table(name = "wb_personal_rewarded_detail")
public class PersonalRewardedDetailEntity implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3691963132208088996L;
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
	@Column(name ="personal_account_id",nullable=true,length=32)
	private String personalAccountId;
	
	/**
	 * 用户ID
	 */
	@Column(name ="user_id",nullable=true,length=32)
	private String userId;
	
	/**
	 * 保单ID
	 */
	@Column(name ="policy_id",nullable=true,length=32)
	private String policyId;
	
	/**
	 * 分润金额
	 */
	@Column(name ="amount")
	private BigDecimal amount;
	
	/**
	 * 状态：0-已分润，可提现；1-已提现，待到账；2-已到账
	 */
	@Column(name ="status",nullable=true,length=32)
	private String status;
	
	/**
	 * 分润时间
	 */
	@Column(name ="divide_time")
	private Date divideTime;
	
	/**
	 * 申请提现时间
	 */
	@Column(name ="withdraw_time")
	private Date withdrawTime;
	
	/**
	 * 款项收到账时间
	 */
	@Column(name ="receive_time")
	private Date receiveTime;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	public String getPersonalAccountId() {
		return personalAccountId;
	}

	public void setPersonalAccountId(String personalAccountId) {
		this.personalAccountId = personalAccountId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDivideTime() {
		return divideTime;
	}

	public void setDivideTime(Date divideTime) {
		this.divideTime = divideTime;
	}

	public Date getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(Date withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
}
