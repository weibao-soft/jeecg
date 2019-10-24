package com.weibao.chaopei.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.weibao.chaopei.page.PolicyMainPage;

public interface PolicyServiceI extends CommonService {
	/**
	 *  查询保单信息
	 */
	public PolicyMainPage getPolicyMainPage(String draftId);
	/**
	 *  查询投保人名称
	 */
	public List<String> getPolicyHolders();
	/**
	 *  查询被投保人名称
	 */
	public List<String> getPolicyInsureds();
	/**
	 * 保存保单、投保人、被投保人、收件人等信息
	 */
	public PolicyMainPage addMain(PolicyMainPage policyMainPage);
}
