package com.weibao.chaopei.service;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import com.weibao.chaopei.entity.HolderEntity;
import com.weibao.chaopei.entity.InsuredEntity;
import com.weibao.chaopei.page.PolicyMainPage;

public interface PolicyServiceI extends CommonService {
	/**
	 *  查询保单信息
	 */
	public PolicyMainPage getPolicyMainPage(String draftId);
	/**
	 *  根据id查询投保人信息
	 * @param id
	 * @return
	 */
	public HolderEntity getHolderById(String id);
	/**
	 *  查询投保人名称
	 */
	public List<Map<String, String>> getPolicyHolders();
	/**
	 *  查询被保人名称
	 */
	public List<InsuredEntity> getPolicyInsureds();
	/**
	 * 根据id删除不在列表里的保单
	 * @param draftId
	 * @param ids
	 */
	public int deletePolicys(String draftId, String[] ids);
	/**
	 * 新增保存保单、投保人、被保人、收件人等信息
	 */
	public PolicyMainPage addMain(PolicyMainPage policyMainPage);
	/**
	 * 修改保存保单、投保人、被保人、收件人等信息
	 */
	public PolicyMainPage updateMain(PolicyMainPage policyMainPage);
}
