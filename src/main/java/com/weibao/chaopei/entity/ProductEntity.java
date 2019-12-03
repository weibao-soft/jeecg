package com.weibao.chaopei.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 产品信息表
 */
@Entity
@Table(name = "wb_insurance_product", schema = "")
@SuppressWarnings("serial")
public class ProductEntity implements java.io.Serializable {
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
	/**商品代码*/    
	@Column(name ="good_code",nullable=true,length=32)
	private java.lang.String goodCode;
	
	/**产品代码*/    
	@Column(name ="prod_code",nullable=true,length=32)
	private java.lang.String prodCode;
	
	/**产品名称*/    
	@Column(name ="prod_name",nullable=true,length=100)
	private java.lang.String prodName;
	
	/**产品类型*/
	@Column(name ="prod_type",nullable=true,length=30)
	private java.lang.String prodType;
	
	/**期限*/
	@Column(name ="period",nullable=true,length=10)
	private java.lang.String period;
	
	/**保险公司名称*/    
	@Column(name ="insur_comp_name",nullable=true,length=100)
	private java.lang.String insurCompName;
	
	/**投保页面标题*/    
	@Column(name ="page_title",nullable=true,length=20)
	private java.lang.String pageTitle;
	
	
	/**投保页面url*/    
	@Column(name ="pageurl",nullable=true,length=255)
	private java.lang.String pageurl;
	
	
	public java.lang.String getId(){
		return this.id;
	}

	public void setId(java.lang.String id){
		this.id = id;
	}

	public java.lang.String getProdCode() {
		return prodCode;
	}

	public void setProdCode(java.lang.String prodCode) {
		this.prodCode = prodCode;
	}

	public java.lang.String getProdName() {
		return prodName;
	}

	public void setProdName(java.lang.String prodName) {
		this.prodName = prodName;
	}

	public java.lang.String getProdType() {
		return prodType;
	}

	public void setProdType(java.lang.String prodType) {
		this.prodType = prodType;
	}

	public java.lang.String getInsurCompName() {
		return insurCompName;
	}

	public void setInsurCompName(java.lang.String insurCompName) {
		this.insurCompName = insurCompName;
	}

	public java.lang.String getPeriod() {
		return period;
	}

	public void setPeriod(java.lang.String period) {
		this.period = period;
	}

	public java.lang.String getGoodCode() {
		return goodCode;
	}

	public void setGoodCode(java.lang.String goodCode) {
		this.goodCode = goodCode;
	}

	public java.lang.String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(java.lang.String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public java.lang.String getPageurl() {
		return pageurl;
	}

	public void setPageurl(java.lang.String pageurl) {
		this.pageurl = pageurl;
	}	
}
