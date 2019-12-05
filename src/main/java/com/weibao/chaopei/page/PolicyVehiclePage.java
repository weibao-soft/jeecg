package com.weibao.chaopei.page;

import java.io.Serializable;
import java.util.Date;

public class PolicyVehiclePage implements Serializable {
	private static final long serialVersionUID = -724566306691255205L;
	//保单id
	private java.lang.String id;
	//车牌号
	private java.lang.String plateNo;
	//车架号
	private java.lang.String frameNo;
	//发动机号
	private java.lang.String engineNo;
	//保险开始日期
	private java.util.Date startDate;
	//保险结束日期
	private java.util.Date endDate;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(java.lang.String plateNo) {
		this.plateNo = plateNo;
	}
	public java.lang.String getFrameNo() {
		return frameNo;
	}
	public void setFrameNo(java.lang.String frameNo) {
		this.frameNo = frameNo;
	}
	public java.lang.String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(java.lang.String engineNo) {
		this.engineNo = engineNo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
