package com.weibao.goodtrans.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.weibao.goodtrans.entity.TransPolicyEntity;
import com.weibao.goodtrans.page.TransPolicyPage;

public interface TransServiceI extends CommonService {
	/**
	 *  查询货运险保单列表
	 * @return
	 */
	public DataGrid getPolicyList(TransPolicyPage policy, DataGrid dataGrid);
	/**
	 * 新增保存货运险保单、投保人、被保人等信息
	 */
	public TransPolicyEntity addMain(TransPolicyPage transPolicyPage);
	/**
	 * 修改保存货运险保单、投保人、被保人等信息
	 */
	public TransPolicyEntity updateMain(TransPolicyPage transPolicyPage);
	/**
	 * 删除货运险保单
	 * @param freightId
	 */
	public void delMain(String freightId);

}
