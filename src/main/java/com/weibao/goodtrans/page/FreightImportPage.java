package com.weibao.goodtrans.page;

import java.io.Serializable;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 货运险保单Form Bean类
 * @author dms
 *
 */
public class FreightImportPage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//保单id
	@Excel(name="订单号/保单id",width=30)
	private java.lang.String id;
	//保单号
	@Excel(name="保单号",width=24)
	private java.lang.String policyNo;
	//电子保单链接url
	@Excel(name="电子保单url",width=60)
	private java.lang.String policyUrl;
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(java.lang.String policyNo) {
		this.policyNo = policyNo;
	}
	public java.lang.String getPolicyUrl() {
		return policyUrl;
	}
	public void setPolicyUrl(java.lang.String policyUrl) {
		this.policyUrl = policyUrl;
	}
}
