package com.weibao.goodtrans.service;

import java.util.Map;

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
	 *  根据id查询保单支付信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> getPolicyPayPage(String id);
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
	/**
	 * 根据保单id修改支付状态、支付时间、支付平台订单号等
	 * @param policyid
	 * @param orderNo
	 * @return
	 */
	public int updatePolicyPayInfo(String freightId, String orderNo);
	/**
	 * 根据保单id修改保单编号和电子保单url
	 * @param freightId
	 * @return
	 */
	public int updatePolicyNo(String policyNo, String policyUrl, String freightId);
}
