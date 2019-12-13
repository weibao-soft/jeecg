package com.weibao.chaopei.entity;
// default package

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSAttachment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 文档下载,新闻,法规表
 * @author  张代浩
 */
@Entity
@Table(name = "wb_policy_change")
@PrimaryKeyJoinColumn(name = "id")
public class WBPolicyChange extends IdEntity {
	public static final Short AGENT_SUBMITTED = 1;
	public static final Short INSURER_REPLYED = 2;
	private static final long serialVersionUID = 1L;
	private String batchNum;
	private PolicyEntity policyEntity;
//	private String insurancePolicyId;
	private Short status;
	private String description;
	private Timestamp createDate;
	private Timestamp modifyDate;

	@Column(name="batch_num")
	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "insurance_policy_id")
	public PolicyEntity getPolicyEntity() {
		return policyEntity;
	}

	public void setPolicyEntity(PolicyEntity policyEntity) {
		this.policyEntity = policyEntity;
	}

//	@Column(name="insurance_policy_id")
//	public String getInsurancePolicyId() {
//		return insurancePolicyId;
//	}
//
//	public void setInsurancePolicyId(String insurancePolicyId) {
//		this.insurancePolicyId = insurancePolicyId;
//	}

	@Column(name="status")
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="createdate")
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name="modifydate")
	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}
}