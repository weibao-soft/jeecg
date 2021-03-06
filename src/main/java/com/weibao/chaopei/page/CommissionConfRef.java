package com.weibao.chaopei.page;

import java.util.List;

import com.weibao.chaopei.entity.CommissionConfEntity;

public class CommissionConfRef implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4996574528265246960L;
	
	private java.lang.String company;
	private java.lang.String prodName;
	private java.lang.String premium;
	private java.lang.String productPlan;
	private String departid;
	private java.lang.String productPlanId;
	private String id;
	private String orgType;
	private Integer period;
	private Integer parentPeriod;
	private Float rate;
	private Float parentRate;
	private List<CommissionConfEntity> commisConfList;
			
	public Integer getParentPeriod() {
		return parentPeriod;
	}
	public void setParentPeriod(Integer parentPeriod) {
		this.parentPeriod = parentPeriod;
	}
	public java.lang.String getCompany() {
		return company;
	}
	public void setCompany(java.lang.String company) {
		this.company = company;
	}
	public java.lang.String getProdName() {
		return prodName;
	}
	public void setProdName(java.lang.String prodName) {
		this.prodName = prodName;
	}
	public java.lang.String getPremium() {
		return premium;
	}
	public void setPremium(java.lang.String premium) {
		this.premium = premium;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
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
	public java.lang.String getProductPlan() {
		return productPlan;
	}
	public void setProductPlan(java.lang.String productPlan) {
		this.productPlan = productPlan;
	}
	public String getDepartid() {
		return departid;
	}
	public void setDepartid(String departid) {
		this.departid = departid;
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
	public Float getParentRate() {
		return parentRate;
	}
	public void setParentRate(Float parentRate) {
		this.parentRate = parentRate;
	}
	public List<CommissionConfEntity> getCommisConfList() {
		return commisConfList;
	}
	public void setCommisConfList(List<CommissionConfEntity> commisConfList) {
		this.commisConfList = commisConfList;
	}		
}
