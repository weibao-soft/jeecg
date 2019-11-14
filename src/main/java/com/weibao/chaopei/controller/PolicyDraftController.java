package com.weibao.chaopei.controller;

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
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	
	@Autowired
	private PolicyServiceI policyService;
	
	@Autowired
	private DraftServiceI draftService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private GuorenApiServiceI guorenApiService;

	/**
	 * 保单主信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/policy/draftMainList");
	}
	
	/**
	 * 我的保单信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "mainlist")
	public ModelAndView mainlist(HttpServletRequest request) {
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
			String status = "1";
			draftEntity.setStatus(status);
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
		return new ModelAndView("com/weibao/chaopei/policy/draftMainList");
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
			//String userId = ResourceUtil.getSessionUser().getId();
			//policyMainPage.setUserId(userId);
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
		return new ModelAndView("com/weibao/chaopei/policy/draftMainList");
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
		String message = "添加成功";
		try{
			//String userId = request.getParameter("userId");
			String userId = ResourceUtil.getSessionUser().getId();
			policyMainPage.setUserId(userId);
			//1.先将草稿单、保单、投保人等信息写进数据库，保单状态为草稿
			List<PolicyEntity> list = draftService.addMain(policyMainPage);
			//2.调用核保接口
			List<Map<String, String>> insRsList = guorenApiService.insuredService(list);
			//3.根据核保接口返回的数据，修改保单状态为已投保，修改主草稿单状态为已投保
			//TODO：如果提交核保的是3台车，但是返回的只有2台车，这种情况如何处理？？？			
			systemService.addLog(message+":", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单主信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		request.setAttribute("policyMainPage", policyMainPage);
		return j;
	}
	
	/**
	 * 修改保单主信息
	 * 
	 * @param policyMainPage
	 * @return
	 */
	@RequestMapping(params = "insuranceUpdate")
	@ResponseBody
	public AjaxJson insuranceUpdate(PolicyMainPage policyMainPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "修改成功";
		try{
			//String userId = ResourceUtil.getSessionUser().getId();
			//policyMainPage.setUserId(userId);
			draftService.updateMain(policyMainPage);
			systemService.addLog(message+":", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单主信息修改失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		request.setAttribute("policyMainPage", policyMainPage);
		return j;
	}
}
