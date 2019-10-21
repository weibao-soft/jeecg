package com.weibao.chaopei.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 保单暂存表
 */
@Entity
@Table(name = "wb_insurance_draft", schema = "")
public class DraftEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	
	/**投保人id*/
	@Column(name ="holder_id",nullable=true,length=32)
	private java.lang.String holderId;
	
	/**收件人id*/
	@Column(name ="recipients_id",nullable=true,length=32)
	private java.lang.String recipientsId;
	
	/**投保单位名称*/
	@Column(name ="comp_name",nullable=true,length=100)
	private java.lang.String compName;
	
	/**投保车辆(台)*/
	@Column(name ="truck_nums",nullable=true,length=5)
	private java.lang.Integer truckNums;
	
	/**状态*/
	@Column(name ="status",nullable=true,length=1)
	private java.lang.String status;
	
	/**用户id*/
	@Column(name ="user_id",nullable=true,length=32)
	private java.lang.String userId;
	
	/**暂存时间*/
	@Column(name ="save_time",nullable=true)
	private java.util.Date saveTime;
	
	/**修改时间*/
	@Column(name ="update_time",nullable=true)
	private java.util.Date updateTime;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getHolderId() {
		return holderId;
	}

	public void setHolderId(java.lang.String holderId) {
		this.holderId = holderId;
	}

	public java.lang.String getRecipientsId() {
		return recipientsId;
	}

	public void setRecipientsId(java.lang.String recipientsId) {
		this.recipientsId = recipientsId;
	}

	public java.lang.String getCompName() {
		return compName;
	}

	public void setCompName(java.lang.String compName) {
		this.compName = compName;
	}

	public java.lang.Integer getTruckNums() {
		return truckNums;
	}

	public void setTruckNums(java.lang.Integer truckNums) {
		this.truckNums = truckNums;
	}

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public java.util.Date getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(java.util.Date saveTime) {
		this.saveTime = saveTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

}
