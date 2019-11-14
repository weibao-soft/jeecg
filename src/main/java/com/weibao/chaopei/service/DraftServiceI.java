package com.weibao.chaopei.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.page.PolicyMainPage;

public interface DraftServiceI extends CommonService {
	/**
	 * 新增保存保单、投保人、被保人、收件人等信息
	 */
	public List<PolicyEntity> addMain(PolicyMainPage policyMainPage);
	/**
	 * 修改保存保单、投保人、被保人、收件人等信息
	 */
	public List<PolicyEntity> updateMain(PolicyMainPage policyMainPage);
	/**
	 * 删除保单、草稿信息
	 */
	public void delMain(PolicyMainPage policyMainPage);
}
