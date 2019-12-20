package com.weibao.goodtrans.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 货运险保单信息表
 */
@Data
@Entity
@Table(name = "wb_freight_insurance_policy", schema = "")
public class FreightPolicyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	/**方案id*/
	@Column(name ="plan_id",nullable=true,length=32)
	private java.lang.String planId;
	//保单号
	@Column(name ="policy_no",nullable=true,length=32)
	private java.lang.String policyNo;


	/*****车辆信息*****/
	//车牌号|航班航次|车次|船名
	@Column(name ="vehicle_plate_no",nullable=true,length=20)
	private java.lang.String vehiclePlateNo;
	//拖车车牌号
	@Column(name ="trailer_plate_no",nullable=true,length=20)
	private java.lang.String trailerPlateNo;
	//车架号
	@Column(name ="vehicle_frame_no",nullable=true,length=20)
	private java.lang.String vehicleFrameNo;

	/*****保单信息*****/
	//起运时间 
	@Column(name ="cargo_start_date",nullable=true)
	private java.util.Date cargoStartDate;
	//结束时间 
	@Column(name ="cargo_end_date",nullable=true)
	private java.util.Date cargoEndDate;
	//总保险金额
	@Column(name ="all_insured_amount",nullable=true,scale=2,length=10)
	private java.math.BigDecimal allInsuredAmount;
	//总保费，保险费合计
	@Column(name ="all_premium",nullable=true,scale=2,length=10)
	private java.math.BigDecimal allPremium;
	
	/*****投保货物信息*****/
	//货物名称
	@Column(name ="goods_name",nullable=true,length=30)
	private java.lang.String goodsName;
	//承保险别
	@Column(name ="category",nullable=true,length=30)
	private java.lang.String category;
	//件数|重量
	@Column(name ="count_or_weight",nullable=true,scale=2,length=6)
	private java.lang.Float countOrWeight;
	//保险金额
	@Column(name ="insured_amount",nullable=true,scale=2,length=10)
	private java.math.BigDecimal insuredAmount;
	//费率
	@Column(name ="premium_rate",nullable=true,scale=2,length=10)
	private java.math.BigDecimal premiumRate;

	/*****投保人信息*****/
	//投保人类型，GR=>个人，QY=>企业
	@Column(name ="holder_part_type",nullable=true,length=20)
	private java.lang.String holderPartType;
	//投保人姓名，投保单位名称|企业名称，不可空
	@Column(name ="holder_name",nullable=true,length=20)
	private java.lang.String holderName;
	//投保人证件类型：(1-组织机构代码证 2-税务登记证 3-营业执照 4-身份证 5-其他)，默认为1
	@Column(name ="holder_cert_type",nullable=true,length=20)
	private java.lang.String holderCertType;
	//投保人证件号码，投保单位组织机构代码，不可空
	@Column(name ="holder_cert_no",nullable=true,length=20)
	private java.lang.String holderCertNo;
	//投保人证件有效期
	@Column(name ="holder_validity",nullable=true,length=20)
	private java.lang.String holderValidity;
	//投保人性别|M男，F女，默认女
	@Column(name ="holder_sex",nullable=true,length=20)
	private java.lang.String holderSex;
	//投保人职业
	@Column(name ="holder_profession",nullable=true,length=20)
	private java.lang.String holderProfession;
	//投保人国籍
	@Column(name ="holder_nation",nullable=true,length=20)
	private java.lang.String holderNation;
	//投保人通讯地址
	@Column(name ="holder_address",nullable=true,length=20)
	private java.lang.String holderAddress;
	//投保人邮政编码
	@Column(name ="holder_postal",nullable=true,length=20)
	private java.lang.String holderPostal;
	//投保联系人名称
	@Column(name ="holder_ctat_name",nullable=true,length=20)
	private java.lang.String holderCtatName;
	//联系人Email地址
	@Column(name ="holder_ctat_email",nullable=true,length=20)
	private java.lang.String holderCtatEmail;
	//联系人办公电话
	@Column(name ="holder_ctat_phone",nullable=true,length=20)
	private java.lang.String holderCtatPhone;
	//联系人手机号码
	@Column(name ="holder_ctat_mobile",nullable=true,length=20)
	private java.lang.String holderCtatMobile;
	//投保人传真号码
	@Column(name ="holder_ctat_fax",nullable=true,length=20)
	private java.lang.String holderCtatFax;

	/*****被保人信息*****/
	//投保人类型，GR=>个人，QY=>企业
	@Column(name ="insured_part_type",nullable=true,length=20)
	private java.lang.String insuredPartType;
	//被保人姓名，被保单位名称，不可空
	@Column(name ="insured_name",nullable=true,length=20)
	private java.lang.String insuredName;
	//被保人证件类型：(1-组织机构代码证 2-税务登记证 3-营业执照 4-身份证 5-其他)，默认为1
	@Column(name ="insured_cert_type",nullable=true,length=20)
	private java.lang.String insuredCertType;
	//被保人证件号码，被保单位组织机构代码，不可空
	@Column(name ="insured_cert_no",nullable=true,length=20)
	private java.lang.String insuredCertNo;
	//被保人证件有效期
	@Column(name ="insured_validity",nullable=true,length=20)
	private java.lang.String insuredValidity;
	//被保人性别|M男，F女，默认女
	@Column(name ="insured_sex",nullable=true,length=20)
	private java.lang.String insuredSex;
	//被保人职业
	@Column(name ="insured_profession",nullable=true,length=20)
	private java.lang.String insuredProfession;
	//被保人国籍
	@Column(name ="insured_nation",nullable=true,length=20)
	private java.lang.String insuredNation;
	//被保人通讯地址
	@Column(name ="insured_address",nullable=true,length=20)
	private java.lang.String insuredAddress;
	//被保人邮编
	@Column(name ="insured_postal",nullable=true,length=20)
	private java.lang.String insuredPostal;
	//被保联系人名称
	@Column(name ="insured_ctat_name",nullable=true,length=20)
	private java.lang.String insuredCtatName;
	//联系人Email地址
	@Column(name ="insured_ctat_email",nullable=true,length=20)
	private java.lang.String insuredCtatEmail;
	//联系人办公电话
	@Column(name ="insured_ctat_phone",nullable=true,length=20)
	private java.lang.String insuredCtatPhone;
	//联系人手机号码
	@Column(name ="insured_ctat_mobile",nullable=true,length=20)
	private java.lang.String insuredCtatMobile;
	//被保人传真号码
	@Column(name ="insured_ctat_fax",nullable=true,length=20)
	private java.lang.String insuredCtatFax;
	//法人、负责人姓名
	@Column(name ="corporate",nullable=true,length=20)
	private java.lang.String corporate;
	//法人证件名称
	@Column(name ="corpor_cert_name",nullable=true,length=20)
	private java.lang.String corporCertName;
	//法人证件号码
	@Column(name ="corpor_cert_no",nullable=true,length=20)
	private java.lang.String corporCertNo;
	//法人证件有效期
	@Column(name ="corpor_validity",nullable=true,length=20)
	private java.lang.String corporValidity;
	
	/*****权益人信息*****/
	//权益人
	@Column(name ="beneficiary",nullable=true,length=20)
	private java.lang.String beneficiary;

	/*****纸质保单收件人信息*****/
	//收件人
	@Column(name ="recipients",nullable=true,length=20)
	private java.lang.String recipients;
	//收件人电话
	@Column(name ="recipients_tel",nullable=true,length=20)
	private java.lang.String recipientsTel;
	//收件地址
	@Column(name ="reci_address",nullable=true,length=20)
	private java.lang.String reciAddress;

	/*****出单机构保单修改时间信息*****/
	//用户id
	@Column(name ="user_id",nullable=true,length=32)
	private java.lang.String userId;
	//保单状态
	@Column(name ="status",nullable=true,length=1)
	private java.lang.String status;
	//支付状态
	@Column(name ="pay_status",nullable=true,length=1)
	private java.lang.String payStatus;
	//支付时间
	@Column(name ="pay_time",nullable=true)
	private java.util.Date payTime;
	//创建时间
	@Column(name ="create_time",nullable=true)
	private java.util.Date createTime;
	//最后修改时间
	@Column(name ="last_update_time",nullable=true)
	private java.util.Date lastUpdateTime;
}
