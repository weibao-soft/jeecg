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
 * 货运险保单信息表-单次
 */
@Data
@Entity
@Table(name = "wb_trans_insurance_policy", schema = "")
public class TransPolicyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	//保单号
	@Column(name ="policy_no",nullable=true,length=32)
	private java.lang.String policyNo;

	/*****运输工具信息*****/
	//车牌号|航班航次|车次|船名
	@Column(name ="vehicle_plate_no",nullable=true,length=20)
	private java.lang.String vehiclePlateNo;
	//拖车车牌号
	@Column(name ="trailer_plate_no",nullable=true,length=20)
	private java.lang.String trailerPlateNo;
	//运单号
	@Column(name ="waybill_no",nullable=true,length=20)
	private java.lang.String wayBillNo;
	//运输方式，1水运，2航空，3公路，4铁路，5邮包，6联运
	@Column(name ="cargo_trans_way",nullable=true,length=20)
	private java.lang.String cargoTransWay;

	/*****保单信息*****/
	//保险金额，单位元，可为空（有的险种不需要保额）
	@Column(name ="insured_amount",nullable=true,scale=2,length=10)
	private java.math.BigDecimal insuredAmount;
	//费率
	@Column(name ="premium_rate",nullable=true,scale=2,length=10)
	private java.math.BigDecimal premiumRate;
	//单个保单保费，不可空。insuredAmount*费率
	@Column(name ="one_premium",nullable=true,scale=2,length=10)
	private java.math.BigDecimal onePremium;
	//总保费，保险费合计
	@Column(name ="all_premium",nullable=true,scale=2,length=10)
	private java.math.BigDecimal allPremium;
	//保障单位，D代表日，Y代表年，S代表一次保障，不可空
	@Column(name ="duration_type",nullable=true,length=20)
	private java.lang.String durationType;
	//保障单位数，与DurationType相结合使用，不可空|保险期限
	@Column(name ="duration",nullable=true,length=20)
	private java.lang.String duration;
	//起运时间|保单起保日期 格式为：yyyy-MM-dd
	@Column(name ="cargo_start_date",nullable=true,length=20)
	private java.util.Date cargoStartDate;
	//结束日期|保单终保日期
	@Column(name ="cargo_end_date",nullable=true,length=20)
	private java.util.Date cargoEndDate;
	//起运地名称
	@Column(name ="cargo_start_site",nullable=true,length=20)
	private java.lang.String cargoStartSite;
	//目的地名称
	@Column(name ="cargo_target_site",nullable=true,length=20)
	private java.lang.String cargoTargetSite;
	//保险货运项目
	@Column(name ="cargo_item",nullable=true,length=20)
	private java.lang.String cargoItem;
	//包装及数量
	@Column(name ="cargo_pkg_and_count",nullable=true,length=20)
	private java.lang.String cargoPkgAndCount;

	/*****投保人信息*****/
	//投保人类型，GR=>个人，QY=>企业
	@Column(name ="holder_part_type",nullable=true,length=20)
	private java.lang.String holderPartType;
	//投保单位名称|投保人姓名，不可空
	@Column(name ="holder_name",nullable=true,length=20)
	private java.lang.String holderName;
	//投保单位组织机构代码|投保人证件号码，不可空
	@Column(name ="holder_cert_no",nullable=true,length=20)
	private java.lang.String holderCertNo;
	//投保人证件类型：(1-居民身份证 2-军人证 3-护照 4-出生证 5-异常身份证 6-回乡证 7- 居民户口薄 8- 军官证)，默认为1
	@Column(name ="holder_cert_type",nullable=true,length=20)
	private java.lang.String holderCertType;
	//投保人性别|M男，F女，默认女
	@Column(name ="holder_sex",nullable=true,length=20)
	private java.lang.String holderSex;
	//投保人生日|投保人为个人时不能为空（企业为空）
	@Column(name ="holder_birthday",nullable=true,length=20)
	private java.lang.String holderBirthday;
	//投保人邮箱账号
	@Column(name ="holder_email",nullable=true,length=20)
	private java.lang.String holderEmail;
	//投保人手机号码(个人时必填，企业为空)
	@Column(name ="holder_mobile_no",nullable=true,length=20)
	private java.lang.String holderMobileNo;
	//投保联系人名称
	@Column(name ="holder_ctat_name",nullable=true,length=20)
	private java.lang.String holderCtatName;
	//联系人手机号码|保单接收手机 企业电话号码（企业时必填，个人为空）
	@Column(name ="holder_ctat_mobile",nullable=true,length=20)
	private java.lang.String holderCtatMobile;

	/*****被保人信息*****/
	//投保人类型，GR=>个人，QY=>企业
	@Column(name ="insured_part_type",nullable=true,length=20)
	private java.lang.String insuredPartType;
	//被保单位名称|被保人姓名，不可空
	@Column(name ="insured_name",nullable=true,length=20)
	private java.lang.String insuredName;
	//被保单位组织机构代码|被保人证件号码，不可空
	@Column(name ="insured_cert_no",nullable=true,length=20)
	private java.lang.String insuredCertNo;
	//被保人证件类型：(1-居民身份证 2-军人证 3-护照 4-出生证 5-异常身份证 6-回乡证 7- 居民户口薄 8- 军官证)，默认为1
	@Column(name ="insured_cert_type",nullable=true,length=20)
	private java.lang.String insuredCertType;
	//被保人性别|M男，F女，默认女
	@Column(name ="insured_sex",nullable=true,length=20)
	private java.lang.String insuredSex;
	//被保联系人名称
	@Column(name ="insured_ctat_name",nullable=true,length=20)
	private java.lang.String insuredCtatName;
	//联系人手机号码
	@Column(name ="insured_ctat_mobile",nullable=true,length=20)
	private java.lang.String insuredCtatMobile;
	//投保人与被保人的关系，5：本人，3：父母，0：无关或者不确定，2：子女，1：配偶，6：其他，不可空
	@Column(name ="ph_rel_to_ins",nullable=true,length=20)
	private java.lang.String phRelToIns;

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

	/*****保单支付后返回的保单编号等信息*****/
	//投保单号
	@Column(name ="proposal_no",nullable=true)
	private String proposalNo;
	//订单号
	@Column(name ="order_no",nullable=true)
	private String orderNo;
	//电子保单下载路径url
	@Column(name ="policy_url",nullable=true)
	private String policyUrl;

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
