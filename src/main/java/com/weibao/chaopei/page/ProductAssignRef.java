package com.weibao.chaopei.page;

import java.util.List;

public class ProductAssignRef implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -724566306691255205L;
	private String id;
	private java.lang.String productPlanId;
	private java.lang.String productId;
	private java.lang.String company;	
	private java.lang.String productPlan;
	private java.lang.String assignStatus;
	private String departid;
	private List<String> checkedProdctAssign;	
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public java.lang.String getProductPlanId() {
		return productPlanId;
	}
	public void setProductPlanId(java.lang.String productPlanId) {
		this.productPlanId = productPlanId;
	}
	public java.lang.String getCompany() {
		return company;
	}
	public void setCompany(java.lang.String company) {
		this.company = company;
	}
	public java.lang.String getProductId() {
		return productId;
	}
	public void setProductId(java.lang.String productId) {
		this.productId = productId;
	}
	public java.lang.String getProductPlan() {
		return productPlan;
	}
	public void setProductPlan(java.lang.String productPlan) {
		this.productPlan = productPlan;
	}
	public java.lang.String getAssignStatus() {
		return assignStatus;
	}
	public void setAssignStatus(java.lang.String assignStatus) {
		this.assignStatus = assignStatus;
	}
	public List<String> getCheckedProdctAssign() {
		return checkedProdctAssign;
	}
	public void setCheckedProdctAssign(List<String> checkedProdctAssign) {
		this.checkedProdctAssign = checkedProdctAssign;
	}
	public String getDepartid() {
		return departid;
	}
	public void setDepartid(String departid) {
		this.departid = departid;
	}
	
}
