package com.weibao.chaopei.service;

import org.jeecgframework.core.common.service.CommonService;

import com.weibao.chaopei.page.PolicyMainPage;

public interface DraftServiceI extends CommonService {
	/**
	 * 新增保存保单、投保人、被保人、收件人等信息
	 */
	public PolicyMainPage addMain(PolicyMainPage policyMainPage);
	/**
	 * 修改保存保单、投保人、被保人、收件人等信息
	 */
	public PolicyMainPage updateMain(PolicyMainPage policyMainPage);

}
