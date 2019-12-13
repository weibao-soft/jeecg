package com.weibao.chaopei.entity;
// default package

import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.pojo.base.TSType;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "wb_policy_change_content")
@PrimaryKeyJoinColumn(name = "id")
public class WBPolicyChangeContent extends TSAttachment implements java.io.Serializable {
	public static final Short STATUS_VALID = 1;
	public static final Short STATUS_INVALID = 0;
	private String policyChangeId;
	private String type;
	private Short status;
	private Timestamp createDate;
	private Timestamp modifyDate;

	@Column(name = "policy_change_id")
	public String getPolicyChangeId() {
		return policyChangeId;
	}

	public void setPolicyChangeId(String policyChangeId) {
		this.policyChangeId = policyChangeId;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "status")
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "createdate")
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "modifydate")
	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}
}