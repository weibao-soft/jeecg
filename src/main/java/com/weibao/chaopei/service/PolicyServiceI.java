package com.weibao.chaopei.service;

import java.util.List;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.weibao.chaopei.entity.HolderEntity;
import com.weibao.chaopei.page.CommonBean;
import com.weibao.chaopei.page.PolicyMainPage;

public interface PolicyServiceI extends CommonService {
	/**
	 *  查询保单信息
	 */
	public PolicyMainPage getPolicyMainPage(String draftId);
	/**
	 *  查询保单列表
	 * @return
	 */
	public DataGrid getPolicyList(PolicyMainPage policy, DataGrid dataGrid);
	/**
	 *  查询产品方案信息
	 * @return
	 */
	public List<CommonBean> getProductPlan(String prodId);
	/**
	 *  根据id查询投保人信息
	 * @param id
	 * @return
	 */
	public HolderEntity getHolderById(String id);
	/**
	 *  查询投保人名称
	 */
	public List<CommonBean> getPolicyHolders();
	/**
	 *  查询被保人名称
	 */
	public List<CommonBean> getPolicyInsureds();
	/**
	 * 新增保存保单、投保人、被保人、收件人等信息
	 */
	public PolicyMainPage addMain(PolicyMainPage policyMainPage);
	/**
	 * 修改保存保单、投保人、被保人、收件人等信息
	 */
	public PolicyMainPage updateMain(PolicyMainPage policyMainPage);
	/**
	 * 根据id删除保单表id不在列表里的行
	 * @param draftId
	 * @param ids
	 */
	public int deletePolicys(String draftId, String[] ids);
	/**
	 * 根据id删除关系表保单id不在列表里的行
	 * @param draftId
	 * @param ids
	 */
	public int deleteRelations(String draftId, String[] ids);
}
