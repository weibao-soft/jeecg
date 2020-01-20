package com.weibao.goodtrans.page;

import java.io.Serializable;

/**
 * 货运险保单Form Bean类
 * @author dms
 */
public class TransPolicyPage implements Serializable {
	private static final long serialVersionUID = 1L;

	//保单id
	private java.lang.String id;
	//产品编号，东瑞给出 60021蔬果险，费率万
	private java.lang.String productCode;
	//保单号
	private java.lang.String policyNo;
	//电子保单链接url
	private java.lang.String policyUrl;
	//发票号码
	private java.lang.String invoiceNumb;
	//所有保单保费相加的总保费，不可空
	private java.math.BigDecimal allPremium;

	/*****运输工具信息*****/
	//车牌号|航班航次|车次
	private java.lang.String vehiclePlateNo;
	//挂车牌号
	private java.lang.String trailerPlateNo;
	//运单号
	private java.lang.String wayBillNo;
	//运输方式，1水运，2航空，3公路，4铁路，5邮包，6联运
	private java.lang.String cargoTransWay;

	/*****保单信息*****/
	//保险金额，单位元，可为空（有的险种不需要保额）
	private java.math.BigDecimal insuredAmount;
	//单个保单保费，不可空。insuredAmount*费率
	private java.math.BigDecimal onePremium;
	//费率
	private java.math.BigDecimal premiumRate;
	//保障单位，D代表日，Y代表年，S代表一次保障，不可空
	private java.lang.String durationType;
	//保障单位数，与DurationType相结合使用，不可空|保险期限
	private java.lang.Float duration;
	//起运时间|保单起保日期 格式为：yyyy-MM-dd
	private java.util.Date cargoStartDate;
	//结束日期|保单终保日期
	private java.util.Date cargoEndDate;
	//起运地名称
	private java.lang.String cargoStartSite;
	//目的地名称
	private java.lang.String cargoTargetSite;
	//保险货运项目
	private java.lang.String cargoItem;
	//包装及数量
	private java.lang.String cargoPkgAndCount;

	/*****投保人信息*****/
	//投保人类型，GR=>个人，QY=>企业
	private java.lang.String holderPartType;
	//投保单位名称|企业名称
	private java.lang.String holderCompName;
	//投保人姓名，不可空
	private java.lang.String holderName;
	//投保单位组织机构代码|证件号码
	private java.lang.String holderOrgCode;
	//投保人证件号码，不可空
	private java.lang.String holderCertNo;
	//投保人证件类型：(1-居民身份证 2-军人证 3-护照 4-出生证 5-异常身份证 6-回乡证 7- 居民户口薄 8- 军官证)，默认为1
	private java.lang.String holderCertType;
	//投保人证件名称
	private java.lang.String holderCertName;
	//投保人性别|M男，F女，默认女
	private java.lang.String holderSex;
	//投保人性别
	private java.lang.String holderSexs;
	//投保人生日|投保人为个人时不能为空（企业为空）
	private java.lang.String holderBirthday;
	//投保人邮箱账号
	private java.lang.String holderEmail;
	//投保人手机号码(个人时必填，企业为空)
	private java.lang.String holderMobileNo;
	//投保联系人名称
	private java.lang.String holderCtatName;
	//联系人手机号码|保单接收手机 企业电话号码（企业时必填，个人为空）
	private java.lang.String holderCtatMobile;

	/*****被保人信息*****/
	//投保人类型，GR=>个人，QY=>企业
	private java.lang.String insuredPartType;
	//被保单位组织机构代码
	private java.lang.String insuredOrgCode;
	//被保单位名称
	private java.lang.String insuredCompName;
	//被保人姓名，不可空
	private java.lang.String insuredName;
	//被保人证件号码，不可空
	private java.lang.String insuredCertNo;
	//被保人证件类型：(1-居民身份证 2-军人证 3-护照 4-出生证 5-异常身份证 6-回乡证 7- 居民户口薄 8- 军官证)，默认为1
	private java.lang.String insuredCertType;
	//被保人证件名称
	private java.lang.String insuredCertName;
	//被保人性别|M男，F女，默认女
	private java.lang.String insuredSex;
	//被保人性别
	private java.lang.String insuredSexs;
	//被保联系人名称
	private java.lang.String insuredCtatName;
	//联系人手机号码
	private java.lang.String insuredCtatMobile;
	//投保人与被保人的关系，5：本人，3：父母，0：无关或者不确定，2：子女，1：配偶，6：其他，不可空
	private java.lang.String phRelToIns;

	/*****纸质保单收件人信息*****/
	//收件人
	private java.lang.String recipients;
	//收件人电话
	private java.lang.String recipientsTel;
	//收件地址
	private java.lang.String reciAddress;

	/*****出单机构保单状态信息*****/
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
	public java.lang.String getProductCode() {
		return productCode;
	}
	public void setProductCode(java.lang.String productCode) {
		this.productCode = productCode;
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
	public java.lang.String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(java.lang.String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public java.lang.String getDurationType() {
		return durationType;
	}
	public void setDurationType(java.lang.String durationType) {
		this.durationType = durationType;
	}
	public java.lang.Float getDuration() {
		return duration;
	}
	public void setDuration(java.lang.Float duration) {
		this.duration = duration;
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
	public java.lang.String getInvoiceNumb() {
		return invoiceNumb;
	}
	public void setInvoiceNumb(java.lang.String invoiceNumb) {
		this.invoiceNumb = invoiceNumb;
	}
	public java.lang.String getCargoTransWay() {
		return cargoTransWay;
	}
	public void setCargoTransWay(java.lang.String cargoTransWay) {
		this.cargoTransWay = cargoTransWay;
	}
	public java.math.BigDecimal getInsuredAmount() {
		return insuredAmount;
	}
	public void setInsuredAmount(java.math.BigDecimal insuredAmount) {
		this.insuredAmount = insuredAmount;
	}
	public java.math.BigDecimal getPremiumRate() {
		return premiumRate;
	}
	public void setPremiumRate(java.math.BigDecimal premiumRate) {
		this.premiumRate = premiumRate;
	}
	public java.math.BigDecimal getAllPremium() {
		return allPremium;
	}
	public void setAllPremium(java.math.BigDecimal allPremium) {
		this.allPremium = allPremium;
	}
	public java.math.BigDecimal getOnePremium() {
		return onePremium;
	}
	public void setOnePremium(java.math.BigDecimal onePremium) {
		this.onePremium = onePremium;
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
	public java.lang.String getCargoPkgAndCount() {
		return cargoPkgAndCount;
	}
	public void setCargoPkgAndCount(java.lang.String cargoPkgAndCount) {
		this.cargoPkgAndCount = cargoPkgAndCount;
	}
	public java.lang.String getHolderMobileNo() {
		return holderMobileNo;
	}
	public void setHolderMobileNo(java.lang.String holderMobileNo) {
		this.holderMobileNo = holderMobileNo;
	}
	public java.lang.String getHolderCtatName() {
		return holderCtatName;
	}
	public void setHolderCtatName(java.lang.String holderCtatName) {
		this.holderCtatName = holderCtatName;
	}
	public java.lang.String getHolderCtatMobile() {
		return holderCtatMobile;
	}
	public void setHolderCtatMobile(java.lang.String holderCtatMobile) {
		this.holderCtatMobile = holderCtatMobile;
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
	public java.lang.String getCargoStartSite() {
		return cargoStartSite;
	}
	public void setCargoStartSite(java.lang.String cargoStartSite) {
		this.cargoStartSite = cargoStartSite;
	}
	public java.lang.String getCargoTargetSite() {
		return cargoTargetSite;
	}
	public void setCargoTargetSite(java.lang.String cargoTargetSite) {
		this.cargoTargetSite = cargoTargetSite;
	}
	public java.lang.String getCargoItem() {
		return cargoItem;
	}
	public void setCargoItem(java.lang.String cargoItem) {
		this.cargoItem = cargoItem;
	}
	public java.lang.String getHolderPartType() {
		return holderPartType;
	}
	public void setHolderPartType(java.lang.String holderPartType) {
		this.holderPartType = holderPartType;
	}
	public java.lang.String getHolderCompName() {
		return holderCompName;
	}
	public void setHolderCompName(java.lang.String holderCompName) {
		this.holderCompName = holderCompName;
	}
	public java.lang.String getHolderOrgCode() {
		return holderOrgCode;
	}
	public void setHolderOrgCode(java.lang.String holderOrgCode) {
		this.holderOrgCode = holderOrgCode;
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
	public java.lang.String getHolderBirthday() {
		return holderBirthday;
	}
	public void setHolderBirthday(java.lang.String holderBirthday) {
		this.holderBirthday = holderBirthday;
	}
	public java.lang.String getHolderEmail() {
		return holderEmail;
	}
	public void setHolderEmail(java.lang.String holderEmail) {
		this.holderEmail = holderEmail;
	}
	public java.lang.String getInsuredPartType() {
		return insuredPartType;
	}
	public void setInsuredPartType(java.lang.String insuredPartType) {
		this.insuredPartType = insuredPartType;
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
	public java.lang.String getPhRelToIns() {
		return phRelToIns;
	}
	public void setPhRelToIns(java.lang.String phRelToIns) {
		this.phRelToIns = phRelToIns;
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
	public java.lang.String getInsuredCtatName() {
		return insuredCtatName;
	}
	public void setInsuredCtatName(java.lang.String insuredCtatName) {
		this.insuredCtatName = insuredCtatName;
	}
	public java.lang.String getInsuredCtatMobile() {
		return insuredCtatMobile;
	}
	public void setInsuredCtatMobile(java.lang.String insuredCtatMobile) {
		this.insuredCtatMobile = insuredCtatMobile;
	}
	public java.lang.String getHolderCertName() {
		return holderCertName;
	}
	public void setHolderCertName(java.lang.String holderCertName) {
		this.holderCertName = holderCertName;
	}
	public java.lang.String getHolderSexs() {
		return holderSexs;
	}
	public void setHolderSexs(java.lang.String holderSexs) {
		this.holderSexs = holderSexs;
	}
	public java.lang.String getInsuredCertName() {
		return insuredCertName;
	}
	public void setInsuredCertName(java.lang.String insuredCertName) {
		this.insuredCertName = insuredCertName;
	}
	public java.lang.String getInsuredSexs() {
		return insuredSexs;
	}
	public void setInsuredSexs(java.lang.String insuredSexs) {
		this.insuredSexs = insuredSexs;
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
