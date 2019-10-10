package com.weibao.chaopei.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "product", schema = "")
@SuppressWarnings("serial")
public class ProductEntity implements java.io.Serializable {
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
	/**名称*/    
	@Column(name ="name",nullable=true,length=100)
	private java.lang.String name;
	
	/**标题*/
	@Column(name ="title",nullable=true,length=255)
	private java.lang.String title;
	
	/**期限*/
	@Column(name ="period",nullable=true,length=10)
	private java.lang.String period;
	
	/**保险公司名称*/    
	@Column(name ="company",nullable=true,length=20)
	private java.lang.String company;
	
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

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getPeriod() {
		return period;
	}

	public void setPeriod(java.lang.String period) {
		this.period = period;
	}

	public java.lang.String getCompany() {
		return company;
	}

	public void setCompany(java.lang.String company) {
		this.company = company;
	}	
}
