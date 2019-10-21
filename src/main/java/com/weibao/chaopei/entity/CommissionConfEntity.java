package com.weibao.chaopei.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 佣金配置关系实体表
 *
 */
@Entity
@Table(name = "wb_commission_conf")
public class CommissionConfEntity implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4393233078178318082L;
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	@Column(name ="depart_user",nullable=true,length=32)
	private String departUser;
	@Column(name ="type",nullable=true,length=1)
	private String type;
	@Column(name ="product_plan_id",nullable=true,length=32)
    private String productPlanId;
	@Column(name ="period")
	private Integer period;
	@Column(name ="rate")
	private Float rate;
	@Column(name ="last_update_by",nullable=true,length=32)
    private String lastUpdateBy;
	@Column(name ="last_update_time")
	private Date lastUpdateTime;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public String getDepartUser() {
		return departUser;
	}
	public void setDepartUser(String departUser) {
		this.departUser = departUser;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProductPlanId() {
		return productPlanId;
	}
	public void setProductPlanId(String productPlanId) {
		this.productPlanId = productPlanId;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public Float getRate() {
		return rate;
	}
	public void setRate(Float rate) {
		this.rate = rate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
