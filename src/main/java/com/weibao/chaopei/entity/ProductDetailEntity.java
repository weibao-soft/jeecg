package com.weibao.chaopei.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "product_detail", schema = "")
@SuppressWarnings("serial")
public class ProductDetailEntity implements java.io.Serializable {
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
	/**产品计划*/    
	@Column(name ="product_plan",nullable=true,length=100)
	private java.lang.String productPlan;
	
	/**营运性质*/
	@Column(name ="type",nullable=true,length=100)
	private java.lang.String type;
	
	/**保费*/
	@Column(name ="price",nullable=true,scale=2,length=10)	
	private java.lang.String price;
	
	/**产品ID*/    
	@Column(name ="product_id",nullable=true,length=32)
	private java.lang.String productId;
	
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}

	public java.lang.String getProductPlan() {
		return productPlan;
	}

	public void setProductPlan(java.lang.String productPlan) {
		this.productPlan = productPlan;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.String getPrice() {
		return price;
	}

	public void setPrice(java.lang.String price) {
		this.price = price;
	}

	public java.lang.String getProductId() {
		return productId;
	}

	public void setProductId(java.lang.String productId) {
		this.productId = productId;
	}
}
