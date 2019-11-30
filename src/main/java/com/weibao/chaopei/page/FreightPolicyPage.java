package com.weibao.chaopei.page;

import java.io.Serializable;

//货运险保单Form Bean类
public class FreightPolicyPage implements Serializable {
	private static final long serialVersionUID = 1L;

	//保单id
	private java.lang.String id;
	//产品编号，东瑞给出 60021蔬果险，费率万
	private java.lang.String productCode;
	//所有保单保费相加的总保费，不可空
	private java.lang.String allPrem;

	/*****运输工具信息*****/
	//车牌号|航班航次|车次
	private java.lang.String vehiclePlateNo;
	//运单号
	private java.lang.String wayBillNo;
	//运输方式，1水运，2航空，3公路，4铁路，5邮包，6联运
	private java.lang.String cargoTransportWay;

	/*****保单信息*****/
	//保额，单位元，可为空（有的险种不需要保额）
	private java.lang.String SA;
	//单个保单保费，不可空。SA*费率
	private java.lang.String onePrem;
	//保障单位，D代表日，Y代表年，S代表一次保障，不可空
	private java.lang.String durationType;
	//保障单位数，与DurationType相结合使用，不可空|保险期限
	private java.lang.String duration;
	//保单起保日期，不可空，一般是T+1|货运起运时间
	private java.util.Date startTime;
	//保单终保日期|结束时间
	private java.util.Date endTime;
	//起运时间 格式为：yyyy-MM-dd
	private java.util.Date cargoStartDate;
	//起运地名称
	private java.lang.String cargoStartSite;
	//目的地名称
	private java.lang.String cargoTargetSite;
	//保险货运项目
	private java.lang.String cargoItem;
	//包装及数量
	private java.lang.String cargoPackageAndCount;

	/*****投保人信息*****/
	//投保人类型，GR=>个人，QY=>企业
	private java.lang.String holderPartType;
	//联系人名称
	private java.lang.String contactName;
	//联系人手机号|保单接收手机 企业电话号码（企业时必填，个人为空）
	private java.lang.String contactMobile;
	//投保单位名称|企业名称
	private java.lang.String holderCompName;
	//投保单位组织机构代码|证件号码
	private java.lang.String holderOrgCode;
	//投保人证件号码，不可空
	private java.lang.String holderCardNumber;
	//投保人证件类型：(1-居民身份证 2-军人证 3-护照 4-出生证 5-异常身份证 6-回乡证 7- 居民户口薄 8- 军官证)，默认为1
	private java.lang.String holderCardType;
	//投保人姓名，不可空
	private java.lang.String holderName;
	//投保人性别|M男，F女，默认女
	private java.lang.String holderSex;
	//投保人生日|投保人为个人时不能为空（企业为空）
	private java.lang.String holderBirthday;
	//投保人邮箱账号
	private java.lang.String holderEmail;
	//投保人手机号码(个人时必填，企业为空)
	private java.lang.String mobileNo;

	/*****被保人信息*****/
	//投保人类型，GR=>个人，QY=>企业
	private java.lang.String insuredPartType;
	//被保单位组织机构代码
	private java.lang.String insuredOrgCode;
	//被保单位名称
	private java.lang.String insuredCompName;
	//被保人证件号码，不可空
	private java.lang.String insuredCardNumber;
	//被保人证件类型：(1-居民身份证 2-军人证 3-护照 4-出生证 5-异常身份证 6-回乡证 7- 居民户口薄 8- 军官证)，默认为1
	private java.lang.String insuredCardType;
	//被保人姓名，不可空
	private java.lang.String insuredName;
	//被保人性别|M男，F女，默认女
	private java.lang.String insuredSex;
	//投保人与被保人的关系，5：本人，3：父母，0：无关或者不确定，2：子女，1：配偶，6：其他，不可空
	private java.lang.String phRelToIns;

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
	public java.lang.String getAllPrem() {
		return allPrem;
	}
	public void setAllPrem(java.lang.String allPrem) {
		this.allPrem = allPrem;
	}
	public java.lang.String getVehiclePlateNo() {
		return vehiclePlateNo;
	}
	public void setVehiclePlateNo(java.lang.String vehiclePlateNo) {
		this.vehiclePlateNo = vehiclePlateNo;
	}
	public java.lang.String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(java.lang.String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public java.lang.String getCargoTransportWay() {
		return cargoTransportWay;
	}
	public void setCargoTransportWay(java.lang.String cargoTransportWay) {
		this.cargoTransportWay = cargoTransportWay;
	}
	public java.lang.String getSA() {
		return SA;
	}
	public void setSA(java.lang.String sA) {
		SA = sA;
	}
	public java.lang.String getOnePrem() {
		return onePrem;
	}
	public void setOnePrem(java.lang.String onePrem) {
		this.onePrem = onePrem;
	}
	public java.lang.String getDurationType() {
		return durationType;
	}
	public void setDurationType(java.lang.String durationType) {
		this.durationType = durationType;
	}
	public java.lang.String getDuration() {
		return duration;
	}
	public void setDuration(java.lang.String duration) {
		this.duration = duration;
	}
	public java.util.Date getStartTime() {
		return startTime;
	}
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	public java.util.Date getEndTime() {
		return endTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	public java.util.Date getCargoStartDate() {
		return cargoStartDate;
	}
	public void setCargoStartDate(java.util.Date cargoStartDate) {
		this.cargoStartDate = cargoStartDate;
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
	public java.lang.String getCargoPackageAndCount() {
		return cargoPackageAndCount;
	}
	public void setCargoPackageAndCount(java.lang.String cargoPackageAndCount) {
		this.cargoPackageAndCount = cargoPackageAndCount;
	}
	public java.lang.String getHolderPartType() {
		return holderPartType;
	}
	public void setHolderPartType(java.lang.String holderPartType) {
		this.holderPartType = holderPartType;
	}
	public java.lang.String getContactName() {
		return contactName;
	}
	public void setContactName(java.lang.String contactName) {
		this.contactName = contactName;
	}
	public java.lang.String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(java.lang.String contactMobile) {
		this.contactMobile = contactMobile;
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
	public java.lang.String getHolderCardNumber() {
		return holderCardNumber;
	}
	public void setHolderCardNumber(java.lang.String holderCardNumber) {
		this.holderCardNumber = holderCardNumber;
	}
	public java.lang.String getHolderCardType() {
		return holderCardType;
	}
	public void setHolderCardType(java.lang.String holderCardType) {
		this.holderCardType = holderCardType;
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
	public java.lang.String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(java.lang.String mobileNo) {
		this.mobileNo = mobileNo;
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
	public java.lang.String getInsuredCardNumber() {
		return insuredCardNumber;
	}
	public void setInsuredCardNumber(java.lang.String insuredCardNumber) {
		this.insuredCardNumber = insuredCardNumber;
	}
	public java.lang.String getInsuredCardType() {
		return insuredCardType;
	}
	public void setInsuredCardType(java.lang.String insuredCardType) {
		this.insuredCardType = insuredCardType;
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
}
