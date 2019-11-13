package com.weibao.chaopei.page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RewardDetailPage implements Serializable {
	private static final long serialVersionUID = -2634009980324608243L;

	//保单id(失效)
	private java.lang.String id;
	//产品id
	private java.lang.String prodId;
	//产品名称
	private java.lang.String prodName;
	//保障方案id
	private java.lang.String planId;
	//产品方案计划
	private java.lang.String prodPlan;
	//保单号
	private java.lang.String policyNo;
	//车牌号
	private java.lang.String plateNo;
	//投保人
	private java.lang.String holderCompName;
	//用户id
	private java.lang.String userId;
	//用户账号/出单员
	private java.lang.String userNo;
	//用户姓名
	private java.lang.String userName;
	//部门id
	private java.lang.String departId;
	//部门名称/出单机构
	private java.lang.String departName;
	//最后修改时间
	private java.util.Date lastUpdateTime;
	//支付时间
	private java.util.Date payTime;
	//保单状态
	private java.lang.String status;
	//分润状态：0-已分润，可提现；1-已提现，待到账；2-已到账
	private java.lang.String rewardStatus;
	//公司账户ID
	private String companyAccountId;
	//个人账户ID
	private String personalAccountId;
	//分润金额
	private BigDecimal amount;
	//分润时间
	private Date divideTime;
	//款项收到账时间
	private Date receiveTime;
	//分润到账时间
	private Date rewardTime;
	//明细生成时间
	private Date generateTime;
	
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
	public java.lang.String getProdName() {
		return prodName;
	}
	public void setProdName(java.lang.String prodName) {
		this.prodName = prodName;
	}
	public java.lang.String getPlanId() {
		return planId;
	}
	public void setPlanId(java.lang.String planId) {
		this.planId = planId;
	}
	public java.lang.String getProdPlan() {
		return prodPlan;
	}
	public void setProdPlan(java.lang.String prodPlan) {
		this.prodPlan = prodPlan;
	}
	public java.lang.String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(java.lang.String policyNo) {
		this.policyNo = policyNo;
	}
	public java.lang.String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(java.lang.String plateNo) {
		this.plateNo = plateNo;
	}
	public java.lang.String getHolderCompName() {
		return holderCompName;
	}
	public void setHolderCompName(java.lang.String holderCompName) {
		this.holderCompName = holderCompName;
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
	public java.lang.String getUserNo() {
		return userNo;
	}
	public void setUserNo(java.lang.String userNo) {
		this.userNo = userNo;
	}
	public java.lang.String getUserName() {
		return userName;
	}
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	public java.lang.String getDepartId() {
		return departId;
	}
	public void setDepartId(java.lang.String departId) {
		this.departId = departId;
	}
	public java.lang.String getDepartName() {
		return departName;
	}
	public void setDepartName(java.lang.String departName) {
		this.departName = departName;
	}
	public java.lang.String getRewardStatus() {
		return rewardStatus;
	}
	public void setRewardStatus(java.lang.String rewardStatus) {
		this.rewardStatus = rewardStatus;
	}
	public java.util.Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(java.util.Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public java.util.Date getPayTime() {
		return payTime;
	}
	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}
	public String getCompanyAccountId() {
		return companyAccountId;
	}
	public void setCompanyAccountId(String companyAccountId) {
		this.companyAccountId = companyAccountId;
	}
	public String getPersonalAccountId() {
		return personalAccountId;
	}
	public void setPersonalAccountId(String personalAccountId) {
		this.personalAccountId = personalAccountId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getDivideTime() {
		return divideTime;
	}
	public void setDivideTime(Date divideTime) {
		this.divideTime = divideTime;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public Date getRewardTime() {
		return rewardTime;
	}
	public void setRewardTime(Date rewardTime) {
		this.rewardTime = rewardTime;
	}
	public Date getGenerateTime() {
		return generateTime;
	}
	public void setGenerateTime(Date generateTime) {
		this.generateTime = generateTime;
	}
}
