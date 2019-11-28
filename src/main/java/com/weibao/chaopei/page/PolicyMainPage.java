package com.weibao.chaopei.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.poi.excel.annotation.Excel;


/**
 * 国任公众物流责任险保单Form Bean
 * @author dms
 *
 */
public class PolicyMainPage implements Serializable {
	private static final long serialVersionUID = -724566306691255205L;
	
	//车辆信息
	private List<PolicyVehiclePage> vehicles = new ArrayList<PolicyVehiclePage>();
	//保单id(失效)
	private java.lang.String id;
	//产品id
	private java.lang.String prodId;
	//产品代码
	private java.lang.String prodCode;
	//保单号
	@Excel(name="保单号",width=32)
	private java.lang.String policyNo;
	//电子保单链接url
	private java.lang.String policyUrl;	
	//产品名称
	private java.lang.String prodName;
	//保险公司名称
	private java.lang.String insurCompName;
	//保障方案id
	private java.lang.String planId;
	//产品方案计划
	private java.lang.String prodPlan;
	//产品方案code
	private java.lang.String planCode;
	//车牌号
	@Excel(name="车牌号",width=32)
	private java.lang.String plateNo;
	//车架号
	@Excel(name="车架号",width=32)
	private java.lang.String frameNo;
	//发动机号
	@Excel(name="发动机号",width=32)
	private java.lang.String engineNo;
	//保险开始日期
	private java.util.Date startDate;
	//保险结束日期
	private java.util.Date endDate;
	//保费
	@Excel(name="保费",width=32)
	private java.math.BigDecimal premium;
	//保单状态
	@Excel(name="保单状态",width=32)
	private java.lang.String status;
	//投保联系人
	private java.lang.String contactName;
	//保单接收手机
	private java.lang.String policyMobile;
	//发票类型
	private java.lang.String invoiceType;
	//发票号码
	private java.lang.String invoiceNumb;
	//投保人性质
	private java.lang.String holderNature;
	//投保单位组织机构代码
	private java.lang.String holderOrgCode;
	//投保单位名称
	private java.lang.String holderCompName;
	//投保单位性质
	private java.lang.String holderCompNature;
	//行业类别
	private java.lang.String industryType;
	//纳税人识别号
	private java.lang.String taxpayerNo;
	//普票接收人手机
	private java.lang.String receiverMobile;
	//专票公司名称
	private java.lang.String compName;
	//专票公司地址
	private java.lang.String compAddress;
	//专票公司电话
	private java.lang.String compPhone;
	//开户行
	private java.lang.String depositBank;
	//银行账号
	private java.lang.String bankAccount;
	//专票收件人
	private java.lang.String recipients;
	//专票收件人电话
	private java.lang.String recipientsTel;
	//专票收件地址
	private java.lang.String reciAddress;
	//专票接收人、电话、地址拼接
	private String taxiAddr;
	//被保单位组织机构代码
	private java.lang.String insuredOrgCode;
	//被保单位名称
	private java.lang.String insuredCompName;
	//投保车辆(台)
	private java.lang.Integer truckNums;
	//草稿id
	private java.lang.String draftId;
	//用户id
	private java.lang.String userId;
	//用户账号
	private java.lang.String userNo;
	//用户姓名
	private java.lang.String userName;
	//部门id
	private java.lang.String departId;
	//部门名称
	private java.lang.String departName;
	//支付状态
	@Excel(name="支付状态",width=32)
	private java.lang.String payStatus;
	//分润状态
	private java.lang.String rewardStatus;
	//创建时间
	private java.util.Date createTime;
	//最后修改时间
	private java.util.Date lastUpdateTime;
	//支付时间
	private java.util.Date payTime;
	//查询条件支付时间范围
	private String payTimeFilter_begin;
	private String payTimeFilter_end;
	//查询条件创建时间范围
	private String createTimeFilter_begin;
	private String createTimeFilter_end;
	//是否草稿
	private boolean isDraft = true;
	
	public List<PolicyVehiclePage> getVehicles() {
		return vehicles;
	}
	public void setVehicles(List<PolicyVehiclePage> vehicles) {
		this.vehicles = vehicles;
	}
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
	public java.lang.String getProdCode() {
		return prodCode;
	}
	public void setProdCode(java.lang.String prodCode) {
		this.prodCode = prodCode;
	}
	public java.lang.String getProdName() {
		return prodName;
	}
	public void setProdName(java.lang.String prodName) {
		this.prodName = prodName;
	}
	public java.lang.String getInsurCompName() {
		return insurCompName;
	}
	public void setInsurCompName(java.lang.String insurCompName) {
		this.insurCompName = insurCompName;
	}
	public java.lang.String getProdPlan() {
		return prodPlan;
	}
	public void setProdPlan(java.lang.String prodPlan) {
		this.prodPlan = prodPlan;
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
	public java.lang.Integer getTruckNums() {
		return truckNums;
	}
	public void setTruckNums(java.lang.Integer truckNums) {
		this.truckNums = truckNums;
	}
	public java.lang.String getDraftId() {
		return draftId;
	}
	public void setDraftId(java.lang.String draftId) {
		this.draftId = draftId;
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
	public java.math.BigDecimal getPremium() {
		return premium;
	}
	public void setPremium(java.math.BigDecimal premium) {
		this.premium = premium;
	}
	public java.lang.String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(java.lang.String payStatus) {
		this.payStatus = payStatus;
	}
	public java.lang.String getRewardStatus() {
		return rewardStatus;
	}
	public void setRewardStatus(java.lang.String rewardStatus) {
		this.rewardStatus = rewardStatus;
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
	public java.lang.String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(java.lang.String policyNo) {
		this.policyNo = policyNo;
	}
	public java.lang.String getPolicyUrl() {
		return policyUrl;
	}
	public void setPolicyUrl(java.lang.String policyUrl) {
		this.policyUrl = policyUrl;
	}
	public boolean isDraft() {
		return isDraft;
	}
	public void setDraft(boolean isDraft) {
		this.isDraft = isDraft;
	}
	public java.lang.String getInvoiceNumb() {
		return invoiceNumb;
	}
	public void setInvoiceNumb(java.lang.String invoiceNumb) {
		this.invoiceNumb = invoiceNumb;
	}
	public String getTaxiAddr() {
		return taxiAddr;
	}
	public void setTaxiAddr(String taxiAddr) {
		this.taxiAddr = taxiAddr;
	}
	public java.lang.String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(java.lang.String planCode) {
		this.planCode = planCode;
	}
	public java.util.Date getPayTime() {
		return payTime;
	}
	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}
	public String getPayTimeFilter_begin() {
		return payTimeFilter_begin;
	}
	public void setPayTimeFilter_begin(String payTimeFilter_begin) {
		this.payTimeFilter_begin = payTimeFilter_begin;
	}
	public String getPayTimeFilter_end() {
		return payTimeFilter_end;
	}
	public void setPayTimeFilter_end(String payTimeFilter_end) {
		this.payTimeFilter_end = payTimeFilter_end;
	}
	public String getCreateTimeFilter_begin() {
		return createTimeFilter_begin;
	}
	public void setCreateTimeFilter_begin(String createTimeFilter_begin) {
		this.createTimeFilter_begin = createTimeFilter_begin;
	}
	public String getCreateTimeFilter_end() {
		return createTimeFilter_end;
	}
	public void setCreateTimeFilter_end(String createTimeFilter_end) {
		this.createTimeFilter_end = createTimeFilter_end;
	}		
}
