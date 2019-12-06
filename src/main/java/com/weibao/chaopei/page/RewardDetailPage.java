package com.weibao.chaopei.page;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
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
	//订单id/取现记录id
	private java.lang.String orderId;
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
	//保费金额
	private Float premium;
	

}
