package com.weibao.chaopei.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 公司及个人账户提现记录
 * @author dms
 *
 */
@Entity
@Table(name = "wb_withdraw_order")
@Data
public class WithdrawOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;

	//公司账户/出单机构
	@Transient
	private String company;

	//个人账户/用户姓名
	@Transient
	private String person;

	/**联系电话*/
	@Transient
	private String telphone;
	
	/*
	 * 申请时间的查询字符串
	 */
	@Transient
	private String applyTimeFilter_begin;
	@Transient
	private String applyTimeFilter_end;


	@Column(name ="account_id",nullable=true,length=32)
	private String accountId;

	/**支付宝账号*/
	@Column(name ="alipay_acct",nullable=true,length=100)
	private String alipayAcct;

	/**支付宝实名*/
	@Column(name ="alipay_name",nullable=true,length=255)
	private String alipayName;

	/**提现的银行信息*/
	@Column(name ="bank_info",nullable=true,length=255)
	private String bankInfo;
	
	/**
	 * 本次提现金额
	 */
	@Column(name ="amount",nullable=true,scale=2,length=10)
	private BigDecimal amount;
	
	/**
	 * 提现机构类型：0-机构；1-个人
	 */
	@Column(name ="org_type",nullable=true,length=1)
	private String orgType;
	
	/**
	 * 提现状态：0-未到账；1-已到账；
	 */
	@Column(name ="status",nullable=true,length=1)
	private String status;

	/**
	 * 申请提现时间
	 */
	@Column(name ="apply_time",nullable=true,length=1)
	private Date applyTime;

	/**
	 * 提现到账时间
	 */
	@Column(name ="approval_time",nullable=true,length=1)
	private Date approvalTime;

}
