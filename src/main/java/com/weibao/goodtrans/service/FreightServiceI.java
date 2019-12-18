package com.weibao.goodtrans.service;

import org.jeecgframework.core.common.service.CommonService;

import com.weibao.goodtrans.entity.FreightPolicyEntity;
import com.weibao.goodtrans.page.FreightPolicyPage;

public interface FreightServiceI extends CommonService {
	/**
	 * 新增保存货运险保单、投保人、被保人、法人、货物等信息
	 */
	public FreightPolicyEntity addMain(FreightPolicyPage freightPolicyPage);
	/**
	 * 修改保存货运险保单、投保人、被保人、法人、货物等信息
	 */
	public FreightPolicyEntity updateMain(FreightPolicyPage freightPolicyPage);
}
