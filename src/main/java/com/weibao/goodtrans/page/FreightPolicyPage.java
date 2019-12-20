package com.weibao.goodtrans.page;

import java.io.Serializable;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 货运险保单Form Bean类
 * @author dms
 *
 */
public class FreightPolicyPage implements Serializable {
	private static final long serialVersionUID = 1L;

	//保单id
	private java.lang.String id;
	//保障方案id
	private java.lang.String planId;
	//产品编号
	private java.lang.String planCode;
	//产品方案计划
	private java.lang.String prodPlan;
	//保单号
	private java.lang.String policyNo;
	//电子保单链接url
	private java.lang.String policyUrl;
	//发票号码
	private java.lang.String invoiceNumb;
	//保险公司名称
	private java.lang.String insurCompName;

	/*****运输工具信息*****/
	//车牌号|航班航次|车次|船名
	@Excel(name="车牌号",width=15)
	private java.lang.String vehiclePlateNo;
	//挂车牌号
	@Excel(name="挂车牌号",width=15)
	private java.lang.String trailerPlateNo;
	//车架号
	@Excel(name="车架号",width=22)
	private java.lang.String vehicleFrameNo;
	//保险开始日期
	@Excel(name="起保日期",width=22)
	private java.util.Date cargoStartDate;
	//保险结束日期
	@Excel(name="终保日期",width=22)
	private java.util.Date cargoEndDate;
	
	/*****投保货物信息*****/
	//货物名称
	private java.lang.String goodsName;
	//承保险别
	private java.lang.String category;
	//件数|重量
	private java.lang.Float countOrWeight;
	//保险金额
	private java.math.BigDecimal insuredAmount;
	//费率
	private java.math.BigDecimal premiumRate;

	/*****保单信息*****/
	//总保险金额
	private java.math.BigDecimal allInsuredAmount;
	//总保费，保险费合计
	@Excel(name="保费",width=11)
	private java.math.BigDecimal allPremium;

	/*****投保人信息*****/
	//投保人类型，GR=>个人，QY=>企业
	private java.lang.String holderPartType;
	//投保人姓名，投保单位名称|企业名称，不可空
	@Excel(name="投保人",width=24)
	private java.lang.String holderName;
	//投保人证件类型：(1-组织机构代码证 2-税务登记证 3-营业执照 4-身份证 5-其他)，默认为1
	private java.lang.String holderCertType;
	//投保人证件号码，投保单位组织机构代码，不可空
	@Excel(name="证件号",width=24)
	private java.lang.String holderCertNo;
	//投保人证件有效期
	private java.lang.String holderValidity;
	//投保人性别|M男，F女，默认女
	private java.lang.String holderSex;
	//投保人职业
	private java.lang.String holderProfession;
	//投保人国籍
	private java.lang.String holderNation;
	//投保人通讯地址
	private java.lang.String holderAddress;
	//投保人邮编
	private java.lang.String holderPostal;
	//投保联系人名称
	private java.lang.String holderCtatName;
	//联系人Email地址
	private java.lang.String holderCtatEmail;
	//联系人办公电话
	private java.lang.String holderCtatPhone;
	//联系人手机号码
	private java.lang.String holderCtatMobile;
	//投保人传真号码
	private java.lang.String holderCtatFax;

	/*****被保人信息*****/
	//投保人类型，GR=>个人，QY=>企业
	private java.lang.String insuredPartType;
	//被保人姓名，被保单位名称，不可空
	private java.lang.String insuredName;
	//被保人证件类型：(1-组织机构代码证 2-税务登记证 3-营业执照 4-身份证 5-其他)，默认为1
	private java.lang.String insuredCertType;
	//被保人证件号码，被保单位组织机构代码，不可空
	private java.lang.String insuredCertNo;
	//被保人证件有效期
	private java.lang.String insuredValidity;
	//被保人性别|M男，F女，默认女
	private java.lang.String insuredSex;
	//被保人职业
	private java.lang.String insuredProfession;
	//被保人国籍
	private java.lang.String insuredNation;
	//被保人通讯地址
	private java.lang.String insuredAddress;
	//被保人邮编
	private java.lang.String insuredPostal;
	//被保联系人名称
	private java.lang.String insuredCtatName;
	//联系人Email地址
	private java.lang.String insuredCtatEmail;
	//联系人办公电话
	private java.lang.String insuredCtatPhone;
	//联系人手机号码
	private java.lang.String insuredCtatMobile;
	//被保人传真号码
	private java.lang.String insuredCtatFax;
	//法人、负责人姓名
	private java.lang.String corporate;
	//法人证件名称
	private java.lang.String corporCertName;
	//法人证件号码
	private java.lang.String corporCertNo;
	//法人证件有效期
	private java.lang.String corporValidity;
	
	/*****权益人信息*****/
	//权益人
	private java.lang.String beneficiary;

	/*****纸质保单收件人信息*****/
	//收件人
	private java.lang.String recipients;
	//收件人电话
	private java.lang.String recipientsTel;
	//收件地址
	private java.lang.String reciAddress;

	/*****出单机构保单修改时间信息*****/
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
	//保单状态
	private java.lang.String status;
	//支付状态
	private java.lang.String payStatus;
	//支付时间
	private java.util.Date payTime;
	//创建时间
	private java.util.Date createTime;
	//最后修改时间
	private java.util.Date lastUpdateTime;
	
	//查询条件支付时间范围
	private String payTimeFilter_begin;
	private String payTimeFilter_end;
	//查询条件创建时间范围
	private String createTimeFilter_begin;
	private String createTimeFilter_end;
	
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
	public java.lang.String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(java.lang.String planCode) {
		this.planCode = planCode;
	}
	public java.lang.String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(java.lang.String policyNo) {
		this.policyNo = policyNo;
	}
	public java.lang.String getVehiclePlateNo() {
		return vehiclePlateNo;
	}
	public void setVehiclePlateNo(java.lang.String vehiclePlateNo) {
		this.vehiclePlateNo = vehiclePlateNo;
	}
	public java.lang.String getTrailerPlateNo() {
		return trailerPlateNo;
	}
	public void setTrailerPlateNo(java.lang.String trailerPlateNo) {
		this.trailerPlateNo = trailerPlateNo;
	}
	public java.lang.String getVehicleFrameNo() {
		return vehicleFrameNo;
	}
	public void setVehicleFrameNo(java.lang.String vehicleFrameNo) {
		this.vehicleFrameNo = vehicleFrameNo;
	}
	public java.util.Date getCargoStartDate() {
		return cargoStartDate;
	}
	public void setCargoStartDate(java.util.Date cargoStartDate) {
		this.cargoStartDate = cargoStartDate;
	}
	public java.util.Date getCargoEndDate() {
		return cargoEndDate;
	}
	public void setCargoEndDate(java.util.Date cargoEndDate) {
		this.cargoEndDate = cargoEndDate;
	}
	public java.lang.String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}
	public java.lang.String getCategory() {
		return category;
	}
	public void setCategory(java.lang.String category) {
		this.category = category;
	}
	public java.lang.Float getCountOrWeight() {
		return countOrWeight;
	}
	public void setCountOrWeight(java.lang.Float countOrWeight) {
		this.countOrWeight = countOrWeight;
	}
	public java.math.BigDecimal getPremiumRate() {
		return premiumRate;
	}
	public void setPremiumRate(java.math.BigDecimal premiumRate) {
		this.premiumRate = premiumRate;
	}
	public java.math.BigDecimal getInsuredAmount() {
		return insuredAmount;
	}
	public void setInsuredAmount(java.math.BigDecimal insuredAmount) {
		this.insuredAmount = insuredAmount;
	}
	public java.math.BigDecimal getAllInsuredAmount() {
		return allInsuredAmount;
	}
	public void setAllInsuredAmount(java.math.BigDecimal allInsuredAmount) {
		this.allInsuredAmount = allInsuredAmount;
	}
	public java.math.BigDecimal getAllPremium() {
		return allPremium;
	}
	public void setAllPremium(java.math.BigDecimal allPremium) {
		this.allPremium = allPremium;
	}
	public java.lang.String getHolderPartType() {
		return holderPartType;
	}
	public void setHolderPartType(java.lang.String holderPartType) {
		this.holderPartType = holderPartType;
	}
	public java.lang.String getHolderCertNo() {
		return holderCertNo;
	}
	public void setHolderCertNo(java.lang.String holderCertNo) {
		this.holderCertNo = holderCertNo;
	}
	public java.lang.String getHolderCertType() {
		return holderCertType;
	}
	public void setHolderCertType(java.lang.String holderCertType) {
		this.holderCertType = holderCertType;
	}
	public java.lang.String getHolderValidity() {
		return holderValidity;
	}
	public void setHolderValidity(java.lang.String holderValidity) {
		this.holderValidity = holderValidity;
	}
	public java.lang.String getHolderName() {
		return holderName;
	}
	public void setHolderName(java.lang.String holderName) {
		this.holderName = holderName;
	}
	public java.lang.String getHolderSex() {
		return holderSex;
	}
	public void setHolderSex(java.lang.String holderSex) {
		this.holderSex = holderSex;
	}
	public java.lang.String getHolderProfession() {
		return holderProfession;
	}
	public void setHolderProfession(java.lang.String holderProfession) {
		this.holderProfession = holderProfession;
	}
	public java.lang.String getHolderNation() {
		return holderNation;
	}
	public void setHolderNation(java.lang.String holderNation) {
		this.holderNation = holderNation;
	}
	public java.lang.String getHolderAddress() {
		return holderAddress;
	}
	public void setHolderAddress(java.lang.String holderAddress) {
		this.holderAddress = holderAddress;
	}
	public java.lang.String getHolderPostal() {
		return holderPostal;
	}
	public void setHolderPostal(java.lang.String holderPostal) {
		this.holderPostal = holderPostal;
	}
	public java.lang.String getHolderCtatName() {
		return holderCtatName;
	}
	public void setHolderCtatName(java.lang.String holderCtatName) {
		this.holderCtatName = holderCtatName;
	}
	public java.lang.String getHolderCtatEmail() {
		return holderCtatEmail;
	}
	public void setHolderCtatEmail(java.lang.String holderCtatEmail) {
		this.holderCtatEmail = holderCtatEmail;
	}
	public java.lang.String getHolderCtatPhone() {
		return holderCtatPhone;
	}
	public void setHolderCtatPhone(java.lang.String holderCtatPhone) {
		this.holderCtatPhone = holderCtatPhone;
	}
	public java.lang.String getHolderCtatMobile() {
		return holderCtatMobile;
	}
	public void setHolderCtatMobile(java.lang.String holderCtatMobile) {
		this.holderCtatMobile = holderCtatMobile;
	}
	public java.lang.String getHolderCtatFax() {
		return holderCtatFax;
	}
	public void setHolderCtatFax(java.lang.String holderCtatFax) {
		this.holderCtatFax = holderCtatFax;
	}
	public java.lang.String getInsuredPartType() {
		return insuredPartType;
	}
	public void setInsuredPartType(java.lang.String insuredPartType) {
		this.insuredPartType = insuredPartType;
	}
	public java.lang.String getInsuredCertNo() {
		return insuredCertNo;
	}
	public void setInsuredCertNo(java.lang.String insuredCertNo) {
		this.insuredCertNo = insuredCertNo;
	}
	public java.lang.String getInsuredCertType() {
		return insuredCertType;
	}
	public void setInsuredCertType(java.lang.String insuredCertType) {
		this.insuredCertType = insuredCertType;
	}
	public java.lang.String getInsuredValidity() {
		return insuredValidity;
	}
	public void setInsuredValidity(java.lang.String insuredValidity) {
		this.insuredValidity = insuredValidity;
	}
	public java.lang.String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(java.lang.String insuredName) {
		this.insuredName = insuredName;
	}
	public java.lang.String getInsuredSex() {
		return insuredSex;
	}
	public void setInsuredSex(java.lang.String insuredSex) {
		this.insuredSex = insuredSex;
	}
	public java.lang.String getInsuredProfession() {
		return insuredProfession;
	}
	public void setInsuredProfession(java.lang.String insuredProfession) {
		this.insuredProfession = insuredProfession;
	}
	public java.lang.String getInsuredNation() {
		return insuredNation;
	}
	public void setInsuredNation(java.lang.String insuredNation) {
		this.insuredNation = insuredNation;
	}
	public java.lang.String getInsuredAddress() {
		return insuredAddress;
	}
	public void setInsuredAddress(java.lang.String insuredAddress) {
		this.insuredAddress = insuredAddress;
	}
	public java.lang.String getInsuredPostal() {
		return insuredPostal;
	}
	public void setInsuredPostal(java.lang.String insuredPostal) {
		this.insuredPostal = insuredPostal;
	}
	public java.lang.String getInsuredCtatName() {
		return insuredCtatName;
	}
	public void setInsuredCtatName(java.lang.String insuredCtatName) {
		this.insuredCtatName = insuredCtatName;
	}
	public java.lang.String getInsuredCtatEmail() {
		return insuredCtatEmail;
	}
	public void setInsuredCtatEmail(java.lang.String insuredCtatEmail) {
		this.insuredCtatEmail = insuredCtatEmail;
	}
	public java.lang.String getInsuredCtatPhone() {
		return insuredCtatPhone;
	}
	public void setInsuredCtatPhone(java.lang.String insuredCtatPhone) {
		this.insuredCtatPhone = insuredCtatPhone;
	}
	public java.lang.String getInsuredCtatMobile() {
		return insuredCtatMobile;
	}
	public void setInsuredCtatMobile(java.lang.String insuredCtatMobile) {
		this.insuredCtatMobile = insuredCtatMobile;
	}
	public java.lang.String getInsuredCtatFax() {
		return insuredCtatFax;
	}
	public void setInsuredCtatFax(java.lang.String insuredCtatFax) {
		this.insuredCtatFax = insuredCtatFax;
	}
	public java.lang.String getCorporate() {
		return corporate;
	}
	public void setCorporate(java.lang.String corporate) {
		this.corporate = corporate;
	}
	public java.lang.String getCorporCertName() {
		return corporCertName;
	}
	public void setCorporCertName(java.lang.String corporCertName) {
		this.corporCertName = corporCertName;
	}
	public java.lang.String getCorporCertNo() {
		return corporCertNo;
	}
	public void setCorporCertNo(java.lang.String corporCertNo) {
		this.corporCertNo = corporCertNo;
	}
	public java.lang.String getCorporValidity() {
		return corporValidity;
	}
	public void setCorporValidity(java.lang.String corporValidity) {
		this.corporValidity = corporValidity;
	}
	public java.lang.String getBeneficiary() {
		return beneficiary;
	}
	public void setBeneficiary(java.lang.String beneficiary) {
		this.beneficiary = beneficiary;
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
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
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
	public java.lang.String getProdPlan() {
		return prodPlan;
	}
	public void setProdPlan(java.lang.String prodPlan) {
		this.prodPlan = prodPlan;
	}
	public java.lang.String getPolicyUrl() {
		return policyUrl;
	}
	public void setPolicyUrl(java.lang.String policyUrl) {
		this.policyUrl = policyUrl;
	}
	public java.lang.String getInvoiceNumb() {
		return invoiceNumb;
	}
	public void setInvoiceNumb(java.lang.String invoiceNumb) {
		this.invoiceNumb = invoiceNumb;
	}
	public java.lang.String getInsurCompName() {
		return insurCompName;
	}
	public void setInsurCompName(java.lang.String insurCompName) {
		this.insurCompName = insurCompName;
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
