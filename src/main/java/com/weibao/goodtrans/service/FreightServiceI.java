package com.weibao.goodtrans.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.weibao.goodtrans.entity.FreightPolicyEntity;
import com.weibao.goodtrans.page.FreightPolicyPage;

public interface FreightServiceI extends CommonService {
	/**
	 *  查询货运险保单列表
	 * @return
	 */
	public DataGrid getPolicyList(FreightPolicyPage policy, DataGrid dataGrid);
	/**
	 * 新增保存货运险保单、投保人、被保人、法人、货物等信息
	 */
	public FreightPolicyEntity addMain(FreightPolicyPage freightPolicyPage);
	/**
	 * 修改保存货运险保单、投保人、被保人、法人、货物等信息
	 */
	public FreightPolicyEntity updateMain(FreightPolicyPage freightPolicyPage);
	/**
	 * 删除货运险保单
	 * @param freightId
	 */
	public void delMain(String freightId);
	/**
	 * 根据保单id修改保单状态
	 * @param freightId
	 * @param status
	 * @return
	 */
	public int updatePolicyStatus(String freightId, String status);
}
