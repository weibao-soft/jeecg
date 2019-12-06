package com.weibao.chaopei.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 * 公司及个人账户提现记录关联明细的关系表
 */
@Entity
@Table(name = "wb_withdraw_order_detail", schema = "")
@Data
public class WithdrawOrderDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;

	/**提现记录id*/
	@Column(name ="order_id",nullable=true,length=32)
	private java.lang.String orderId;

	/**佣金分润明细id*/
	@Column(name ="reward_detail_id",nullable=true,length=32)
	private java.lang.String rewardDetailId;

}
