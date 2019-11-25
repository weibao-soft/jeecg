package com.weibao.chaopei.page;

import java.io.Serializable;

import org.jeecgframework.poi.excel.annotation.Excel;


/**
 * 保单Form Bean
 * @author dms
 *
 */
public class InvoiceExportPage implements Serializable {
	private static final long serialVersionUID = -724566306691255205L;
	//创建时间
	@Excel(name="创建日期",width=25)
	private java.util.Date createTime;
	//支付时间
	@Excel(name="支付日期",width=25)
	private java.util.Date payTime;
	//保单号
	@Excel(name="保单号",width=25)
	private java.lang.String policyNo;
	//保单接收手机
	@Excel(name="投保人电话",width=15)
	private java.lang.String policyMobile;
	//投保单位名称
	@Excel(name="投保人公司名称",width=32)
	private java.lang.String holderCompName;
	//保费
	@Excel(name="保费",width=10)
	private java.math.BigDecimal premium;
	//普票接收人手机
	@Excel(name="普票接收手机",width=15)
	private java.lang.String receiverMobile;
	//纳税人识别号
	@Excel(name="纳税号",width=22)
	private java.lang.String taxpayerNo;	
	//专票公司地址
	@Excel(name="地址",width=50)
	private java.lang.String compAddress;
	//专票公司电话
	@Excel(name="电话",width=18)
	private java.lang.String compPhone;
	//开户行
	@Excel(name="开户行",width=32)
	private java.lang.String depositBank;
	//银行账号
	@Excel(name="账号",width=25)
	private java.lang.String bankAccount;
	//发票类型
	@Excel(name="专票/普票",width=10)
	private java.lang.String invoiceType;
	//专票接收人、电话、地址拼接
	@Excel(name="邮寄地址",width=50)
	private String taxiAddr;
	
	@Excel(name="保单归属",width=15)
	private String topOrg;
	
	
	public java.lang.String getPolicyMobile() {
		return policyMobile;
	}
	public void setPolicyMobile(java.lang.String policyMobile) {
		this.policyMobile = policyMobile;
	}
	public java.lang.String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(java.lang.String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public java.lang.String getHolderCompName() {
		return holderCompName;
	}
	public void setHolderCompName(java.lang.String holderCompName) {
		this.holderCompName = holderCompName;
	}
	public java.lang.String getTaxpayerNo() {
		return taxpayerNo;
	}
	public void setTaxpayerNo(java.lang.String taxpayerNo) {
		this.taxpayerNo = taxpayerNo;
	}
	public java.lang.String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(java.lang.String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	public java.lang.String getCompAddress() {
		return compAddress;
	}
	public void setCompAddress(java.lang.String compAddress) {
		this.compAddress = compAddress;
	}
	public java.lang.String getCompPhone() {
		return compPhone;
	}
	public void setCompPhone(java.lang.String compPhone) {
		this.compPhone = compPhone;
	}
	public java.lang.String getDepositBank() {
		return depositBank;
	}
	public void setDepositBank(java.lang.String depositBank) {
		this.depositBank = depositBank;
	}
	public java.lang.String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(java.lang.String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public java.math.BigDecimal getPremium() {
		return premium;
	}
	public void setPremium(java.math.BigDecimal premium) {
		this.premium = premium;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.lang.String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(java.lang.String policyNo) {
		this.policyNo = policyNo;
	}
	public String getTaxiAddr() {
		return taxiAddr;
	}
	public void setTaxiAddr(String taxiAddr) {
		this.taxiAddr = taxiAddr;
	}
	public java.util.Date getPayTime() {
		return payTime;
	}
	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}
	public String getTopOrg() {
		return topOrg;
	}
	public void setTopOrg(String topOrg) {
		this.topOrg = topOrg;
	}	
}
