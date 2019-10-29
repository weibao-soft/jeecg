package com.weibao.chaopei.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 被投保人信息表
 */
@Entity
@Table(name = "wb_insured_info", schema = "")
public class InsuredEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
	/**被投保单位组织机构代码*/
	@Column(name ="insured_org_code",nullable=true,length=24)
	private java.lang.String insuredOrgCode;
	
	/**被投保单位名称*/
	@Column(name ="insured_comp_name",nullable=true,length=100)
	private java.lang.String insuredCompName;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getInsuredOrgCode() {
		return insuredOrgCode;
	}

	public void setInsuredOrgCode(java.lang.String insuredOrgCode) {
		this.insuredOrgCode = insuredOrgCode;
	}

	public java.lang.String getInsuredCompName() {
		return insuredCompName;
	}

	public void setInsuredCompName(java.lang.String insuredCompName) {
		this.insuredCompName = insuredCompName;
	}

}
