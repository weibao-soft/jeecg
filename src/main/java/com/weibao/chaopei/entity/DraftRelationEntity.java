package com.weibao.chaopei.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 草稿关联保单关系表
 */
@Entity
@Table(name = "wb_insurance_policy", schema = "")
public class DraftRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
	/**草稿id*/
	@Column(name ="draft_id",nullable=true,length=32)
	private java.lang.String draftId;
	
	/**保单id*/
	@Column(name ="policy_id",nullable=true,length=32)
	private java.lang.String policyId;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getDraftId() {
		return draftId;
	}

	public void setDraftId(java.lang.String draftId) {
		this.draftId = draftId;
	}

	public java.lang.String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(java.lang.String policyId) {
		this.policyId = policyId;
	}

}
