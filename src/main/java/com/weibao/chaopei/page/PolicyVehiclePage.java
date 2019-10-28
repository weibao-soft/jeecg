package com.weibao.chaopei.page;

import java.io.Serializable;

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

}
