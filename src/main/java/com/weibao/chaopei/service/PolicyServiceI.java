package com.weibao.chaopei.service;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.weibao.chaopei.entity.HolderEntity;
import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.entity.ReceiverEntity;
import com.weibao.chaopei.page.CommonBean;
import com.weibao.chaopei.page.InvoiceExportPage;
import com.weibao.chaopei.page.PolicyMainPage;

public interface PolicyServiceI extends CommonService {
	/**
	 *  查询单个保单信息
	 */
	public PolicyMainPage getOnePolicyMainPage(String policyid);
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
	 *  查询下级机构的保单列表
	 * @return
	 */
	public DataGrid getPolicyList(PolicyMainPage policy, DataGrid dataGrid, List<String> userIdList);
	
	/**
	 * 查询国任的保单列表
	 * @param policy
	 * @param dataGrid
	 * @param userIdList
	 * @return
	 */
	public DataGrid getGuorenPolicyList(PolicyMainPage policy, DataGrid dataGrid, List<String> userIdList);
	
	/**
	 *  导出国任的发票信息列表
	 * @param policy
	 * @param dataGrid
	 * @param userIdList
	 * @return
	 */
	public List<InvoiceExportPage> getGuorenInvoiceList(PolicyMainPage policy, DataGrid dataGrid, List<String> userIdList);
	
	/**
	 * 根据部门id获取用户列表
	 * @param departIds
	 * @return
	 */
	public List<String> getDepartUserIds(List<String> departIds);
	/**
	 *  查询产品方案信息
	 * @return
	 */
	public List<CommonBean> getProductPlan(String departId, String prodId);
	/**
	 *  根据id查询保单支付信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> getPolicyPayPage(String id);
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
	 *  查询收件人信息
	 */
	public List<ReceiverEntity> getPolicyReceivers(String userId);
	/**
	 * 查询该部门所有子部门的ID
	 * @param userid
	 * @return
	 */
	public List<String> getChildDepartIds(String parentId);
	/**
	 * 查询用户所属的部门ID
	 * @param userid
	 * @return
	 */
	public List<String> getDepartIdByUser(String userid);
	/**
	 * 根据保单id、草稿id分别修改保单和草稿的状态为已投保
	 * @param policyid
	 * @param draftId
	 * @return
	 */
	public int updatePolicyStatusById(List<String> policyids, String draftId);
	/**
	 * 根据保单id、草稿id分别修改保单和草稿的状态为已投保
	 * @param policyid
	 * @param draftId
	 * @return
	 */
	public int updatePolicyStatus(List<PolicyEntity> list, String draftId);
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
