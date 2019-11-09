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
 * 公司账户待分润明细表
 * @author Liut
 *
 */
@Entity
@Table(name = "wb_company_unreward_detail")
public class CompanyUnrewardedDetailEntity implements java.io.Serializable {
	
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
	
	@Column(name ="company_account_id",nullable=true,length=32)
	private String companyAccountId;
	
	@Column(name ="depart_id",nullable=true,length=32)
	private String departId;
	
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
	 * 支付成功时间
	 */
	@Column(name ="pay_time")
	private Date payTime;
	
	/**
	 * 将分润到账时间
	 */
	@Column(name ="reward_time")
	private Date rewardTime;
	
	/**
	 * 明细生成时间
	 */
	@Column(name ="generate_time")
	private Date generateTime;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public String getCompanyAccountId() {
		return companyAccountId;
	}

	public void setCompanyAccountId(String companyAccountId) {
		this.companyAccountId = companyAccountId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
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

	public Date getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(Date rewardTime) {
		this.rewardTime = rewardTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(Date generateTime) {
		this.generateTime = generateTime;
	}
}
