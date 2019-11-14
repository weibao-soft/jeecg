package com.weibao.chaopei.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 产品方案详情表
 */
@Entity
@Table(name = "wb_product_detail", schema = "")
@SuppressWarnings("serial")
public class ProductDetailEntity implements java.io.Serializable {
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
	/**产品ID*/    
	@Column(name ="prod_id",nullable=true,length=32)
	private java.lang.String prodId;
	
	/**方案代码*/
	@Column(name ="plan_code",nullable=true,length=32)
	private java.lang.String planCode;
	
	/**方案名称*/
	@Column(name ="plan_name",nullable=true,length=32)
	private java.lang.String planName;
	
	/**产品方案计划*/
	@Column(name ="prod_plan",nullable=true,length=100)
	private java.lang.String prodPlan;
	
	/**营运性质*/
	@Column(name ="plan_type",nullable=true,length=30)
	private java.lang.String planType;
	
	/**保费*/
	@Column(name ="premium",nullable=true,scale=2,length=10)
	private java.math.BigDecimal premium;
	
	/**排序字段*/
	@Column(name ="sort_no",nullable=true,scale=2,length=10)
	private int sortNo;
	
	
	public java.lang.String getId(){
		return this.id;
	}

	public void setId(java.lang.String id){
		this.id = id;
	}

	public java.lang.String getProdId() {
		return prodId;
	}

	public void setProdId(java.lang.String prodId) {
		this.prodId = prodId;
	}

	public java.lang.String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(java.lang.String planCode) {
		this.planCode = planCode;
	}

	public java.lang.String getProdPlan() {
		return prodPlan;
	}

	public void setProdPlan(java.lang.String prodPlan) {
		this.prodPlan = prodPlan;
	}

	public java.lang.String getPlanType() {
		return planType;
	}

	public void setPlanType(java.lang.String planType) {
		this.planType = planType;
	}

	public java.math.BigDecimal getPremium() {
		return premium;
	}

	public void setPrice(java.math.BigDecimal premium) {
		this.premium = premium;
	}

	public java.lang.String getPlanName() {
		return planName;
	}

	public void setPlanName(java.lang.String planName) {
		this.planName = planName;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public void setPremium(java.math.BigDecimal premium) {
		this.premium = premium;
	}		
}
