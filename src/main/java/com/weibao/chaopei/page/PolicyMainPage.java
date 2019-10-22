package com.weibao.chaopei.page;

import java.io.Serializable;


/**
 * 保单Form Bean
 * @author dms
 *
 */
public class PolicyMainPage implements Serializable {
	private static final long serialVersionUID = -724566306691255205L;
	//保单id
	private java.lang.String id;
	//保障方案id
	private java.lang.String planId;
	//车牌号
	private java.lang.String plateNo;
	//车架号
	private java.lang.String frameNo;
	//发动机号
	private java.lang.String engineNo;
	//保险开始日期
	private java.util.Date startDate;
	//保险结束日期
	private java.util.Date endDate;
	//保单状态
	private java.lang.String status;
	//投保联系人
	private java.lang.String contactName;
	//保单接收手机
	private java.lang.String policyMobile;
	//发票类型
	private java.lang.String invoiceType;
	//投保人性质
	private java.lang.String holderNature;
	//投保单位组织机构代码
	private java.lang.String orgCode;
	//投保单位名称
	private java.lang.String compName;
	//投保单位性质
	private java.lang.String compNature;
	//行业类别
	private java.lang.String industryType;
	//纳税人识别号
	private java.lang.String taxpayerNo;
	//普票接收人手机
	private java.lang.String receiverMobile;
	//专票公司名称
	private java.lang.String compName2;
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
	//被投保单位组织机构代码
	private java.lang.String orgCode3;
	//被投保单位名称
	private java.lang.String compName3;
	//投保人id
	private java.lang.String holderId;
	//被投保人id
	private java.lang.String insuredId;
	//收件人id
	private java.lang.String recipientsId;
	private java.lang.String draftId;
	//用户id
	private java.lang.String userId;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
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
	public java.lang.String getOrgCode3() {
		return orgCode3;
	}
	public void setOrgCode3(java.lang.String orgCode3) {
		this.orgCode3 = orgCode3;
	}
	public java.lang.String getCompName3() {
		return compName3;
	}
	public void setCompName3(java.lang.String compName3) {
		this.compName3 = compName3;
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
	
	
}
