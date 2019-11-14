package com.weibao.chaopei.util.http;

public class Element {
	private String prodCode;
	private String relTbl;
	private RelFieldName relFieldName;
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}	
	public String getRelTbl() {
		return relTbl;
	}
	public void setRelTbl(String relTbl) {
		this.relTbl = relTbl;
	}
	public RelFieldName getRelFieldName() {
		return relFieldName;
	}
	public void setRelFieldName(RelFieldName relFieldName) {
		this.relFieldName = relFieldName;
	}
}
