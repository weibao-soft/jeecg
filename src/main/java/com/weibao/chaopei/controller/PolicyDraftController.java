package com.weibao.chaopei.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weibao.chaopei.entity.DraftEntity;
import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.page.PolicyMainPage;
import com.weibao.chaopei.service.DraftServiceI;
import com.weibao.chaopei.service.GuorenApiServiceI;
import com.weibao.chaopei.service.PolicyServiceI;

@Controller
@RequestMapping("/policyDraftController")
public class PolicyDraftController extends BaseController {
	private static final Logger logger = Logger.getLogger(PolicyDraftController.class);
	
	private static final String ISO8859 = "ISO8859-1";
	
	private static final String UTF8 = "UTF-8";
	
	Gson gson = new GsonBuilder().create();	
	
	@Autowired
	private PolicyServiceI policyService;
	
	@Autowired
	private DraftServiceI draftService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private GuorenApiServiceI guorenApiService;

	/**
	 * 我的保单信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/policy/draftMainListBase");
	}
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(DraftEntity draftEntity, HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid) {
		
		CriteriaQuery cq = new CriteriaQuery(DraftEntity.class, dataGrid);
		try{
			//自定义追加查询条件
			//查询当前用户下的草稿单
			TSUser currentUser = ResourceUtil.getSessionUser();
			String status = "1";
			draftEntity.setStatus(status);			
			draftEntity.setUserId(currentUser.getId());						
			//查询条件组装器
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, draftEntity);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.draftService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 添加保单主信息
	 * 
	 * @param policyMainPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	public ModelAndView doAdd(PolicyMainPage policyMainPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			//String userId = request.getParameter("userId");
			String userId = ResourceUtil.getSessionUser().getId();
			policyMainPage.setUserId(userId);
			draftService.addMain(policyMainPage);
			systemService.addLog(message+":", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单主信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return new ModelAndView("com/weibao/chaopei/policy/draftMainListBase");
	}
	
	/**
	 * 修改保单主信息
	 * 
	 * @param policyMainPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	public ModelAndView doUpdate(PolicyMainPage policyMainPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "修改成功";
		try{
			String userId = ResourceUtil.getSessionUser().getId();
			policyMainPage.setUserId(userId);
			draftService.updateMain(policyMainPage);
			systemService.addLog(message+":", Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单主信息修改失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		request.setAttribute("policyMainPage", policyMainPage);
		return new ModelAndView("com/weibao/chaopei/policy/draftMainListBase");
	}
	
	/**
	 * 删除草稿状态的保单
	 * 
	 * @param policyMainPage
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(String draftId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "删除成功";
        PolicyMainPage policyMainPage = null;
		try{
			policyMainPage = policyService.getPolicyMainPage(draftId);
			draftService.delMain(policyMainPage);
			systemService.addLog(message+":", Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单主信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 主保单在未保存草稿之前，直接提交核保
	 * @param policyMainPage
	 * @return
	 */
	@RequestMapping(params = "insuranceAdd")
	@ResponseBody
	public AjaxJson insuranceAdd(PolicyMainPage policyMainPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "核保成功";
		try{
			String userId = ResourceUtil.getSessionUser().getId();
			policyMainPage.setUserId(userId);
			//1.先将草稿单、保单、投保人等信息写进数据库，保单状态为草稿
			List<PolicyEntity> list = draftService.addMain(policyMainPage);
			//2.调用核保接口
			List<Map<String, String>> insRsList = guorenApiService.insuredService(list);
			//net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(insRsList);
			j.setObj(insRsList);
			//3.根据核保接口返回的数据，修改保单状态为已投保，修改主草稿单状态为已投保
			String draftId = policyMainPage.getDraftId();
			if(insRsList != null && insRsList.size() == list.size()) {
				policyService.updatePolicyStatus(list, draftId);
			} else {
				message = "保单核保失败，请重新发起核保申请！";
				j.setSuccess(false);
				j.setObj(policyMainPage);
				j.setMsg(message);
				return j;
			}
			//TODO：如果提交核保的是3台车，但是返回的只有2台车，这种情况如何处理？？？
			systemService.addLog(message+":", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单核保失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		request.setAttribute("policyMainPage", policyMainPage);
		return j;
	}
	
	/**
	 * 草稿修改页面提交核保
	 * 
	 * @param policyMainPage
	 * @return
	 */
	@RequestMapping(params = "insuranceUpdate")
	@ResponseBody
	public AjaxJson insuranceUpdate(PolicyMainPage policyMainPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "核保成功";
		try{
			String userId = ResourceUtil.getSessionUser().getId();
			policyMainPage.setUserId(userId);
			
			//1.先修改草稿单、保单、投保人等信息，保单状态为草稿
			List<PolicyEntity> list = draftService.updateMain(policyMainPage);
			//2.调用核保接口
			List<Map<String, String>> insRsList = guorenApiService.insuredService(list);
			//net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(insRsList);
			j.setObj(insRsList);
			//3.根据核保接口返回的数据，修改保单状态为已投保，修改主草稿单状态为已投保
			String draftId = policyMainPage.getDraftId();
			if(insRsList != null && insRsList.size() == list.size()) {
				policyService.updatePolicyStatus(list, draftId);
			} else {
				j.setSuccess(false);
				message = "保单核保失败，请重新发起核保申请！";
			}
			//TODO：如果提交核保的是3台车，但是返回的只有2台车，这种情况如何处理？？？
			systemService.addLog(message+":", Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单核保失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		request.setAttribute("policyMainPage", policyMainPage);
		return j;
	}
	
	/**
	 * 核保支付
	 * 
	 * @param insRsList
	 * @return
	 */
	@RequestMapping(params = "insurancePay")
	@ResponseBody
	public AjaxJson insurancePay(String params, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "支付成功";
		List<PolicyEntity> list = new ArrayList<PolicyEntity>();
		Map<String, String> insRs = null;
		try{
			//1.页面传入的数据
			List<Map<String, String>> paramList = gson.fromJson(params, List.class);
			for(int i = 0; i < paramList.size(); i++) {
				Map<String, String> param = paramList.get(i);
				PolicyEntity policy = new PolicyEntity();
	    		String id = (String)param.get("id");
	    		String proposalNo = (String)param.get("proposalNo");
	    		String orderNo = (String)param.get("orderNo");
	    		String policyMobile = (String)param.get("policyMobile");
	    		policy.setId(id);
	    		policy.setProposalNo(proposalNo);
	    		policy.setOrderNo(orderNo);
	    		policy.setPolicyMobile(policyMobile);
	    		list.add(policy);
			}
			//2.调用支付接口
			if(list.isEmpty()) {
				j.setSuccess(false);
				message = "参数错误，请重新发起支付申请！";
				return j;
			}
			
			insRs = guorenApiService.payService(list);
			//3.根据支付接口返回的数据，修改保单支付状态
			if(insRs != null && !insRs.isEmpty()) {
				//net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(insRs);
				j.setObj(insRs);
			} else {
				j.setSuccess(false);
				message = "保单支付失败，请重新发起申请！";
			}
			systemService.addLog(message+":", Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单支付失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
