package com.weibao.chaopei.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
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
	 * 保单主信息新增 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(String paramId, HttpServletRequest request) {
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
		Calendar canlendar = Calendar.getInstance();
		canlendar.add(Calendar.DATE, 1);
		Date startDate = canlendar.getTime();
		
		canlendar.add(Calendar.YEAR, 1);
		canlendar.add(Calendar.DATE, -1);
		Date endDate = canlendar.getTime();
		
		canlendar.add(Calendar.YEAR, -1);
		canlendar.add(Calendar.DATE, 365);
		Date maxDate = canlendar.getTime();

        String start = sdfd.format(startDate);
        String end = sdfd.format(endDate);
        String max = sdfd.format(maxDate);
		request.setAttribute("start", start);
		request.setAttribute("end", end);
		request.setAttribute("max", max);
		request.setAttribute("prodId", paramId);
		return new ModelAndView("com/weibao/chaopei/policy/policyMainAdd");
	}
	
	/**
	 * 保单主信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(String draftId, HttpServletRequest request) {
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        PolicyMainPage policyMainPage = null;
		try {
			request.setCharacterEncoding(UTF8);
			//setCharacterEncoding(policyMainPage);
			policyMainPage = policyService.getPolicyMainPage(draftId);
			Calendar canlendar = Calendar.getInstance();
			Date startDate = policyMainPage.getStartDate();
			Date endDate = policyMainPage.getEndDate();
			Date maxDate = endDate;
			int year = 1;
			if(startDate == null) {
				canlendar.add(Calendar.DATE, 1);
				startDate = canlendar.getTime();
				
				canlendar.add(Calendar.YEAR, 1);
				canlendar.add(Calendar.DATE, -1);
				endDate = canlendar.getTime();
				maxDate = canlendar.getTime();
				
				policyMainPage.setStartDate(startDate);
				policyMainPage.setEndDate(endDate);
			} else {
				canlendar.setTime(startDate);
				canlendar.add(Calendar.YEAR, 1);
				canlendar.add(Calendar.DATE, -1);
				maxDate = canlendar.getTime();
				
				canlendar.setTime(startDate);
				int startYear = canlendar.get(Calendar.YEAR);
				canlendar.setTime(endDate);
				int endYear = canlendar.get(Calendar.YEAR);
				year = endYear - startYear;
			}
			
	        String start = sdfd.format(startDate);
	        String end = sdfd.format(endDate);
	        String max = sdfd.format(maxDate);
	        //String payUrl = "https://devyun.guorenpcic.com/paycenter/?orderId=23a2e077d1e4fd19a61&code=&payOrderNo=js02&platform=pc";
			request.setAttribute("start", start);
			request.setAttribute("end", end);
			request.setAttribute("max", max);
			request.setAttribute("year", year);
			request.setAttribute("isDraft", true);
			//request.setAttribute("payUrl", payUrl);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		request.setAttribute("policyMainPage", policyMainPage);
		return new ModelAndView("com/weibao/chaopei/policy/policyMainUpdate");
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
		try {
	        //PolicyMainPage policyMainPage = policyService.getPolicyMainPage(draftId);
			//policyMainPage.setDraftId(draftId);
			//draftService.delMain(policyMainPage);
			draftService.delMain(draftId);
			systemService.addLog(message+":", Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		} catch(HibernateException e) {
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单草稿删除失败";
			throw new BusinessException("保单草稿删除失败，原因：" + e.getMessage());
		} catch(Exception e) {
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单草稿删除失败";
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
			//insRsList.clear();
			//3.根据核保接口返回的数据，修改保单状态为已投保，修改主草稿单状态为已投保
			String draftId = policyMainPage.getDraftId();
			if(insRsList != null && insRsList.size() == list.size()) {
				policyService.updatePolicyStatus(list, draftId);
			} else {
				logger.info("insuranceAdd result info ==== " + insRsList);
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
			//throw new BusinessException(e.getMessage());
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
				logger.info("insuranceUpdate result info ==== " + insRsList);
				j.setSuccess(false);
				message = "保单核保失败，请重新发起核保申请！";
			}
			//TODO：如果提交核保的是3台车，但是返回的只有2台车，这种情况如何处理？？？
			systemService.addLog(message+":", Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单核保失败";
			//throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		request.setAttribute("policyMainPage", policyMainPage);
		return j;
	}
	
	/**
	 * 草稿页面核保支付
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(params = "insurancePay")
	@ResponseBody
	public AjaxJson insurancePay(String params, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "支付链接获取成功";
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
				String url = insRs.get("data");
				String resultCode = insRs.get("resultCode");
				request.setAttribute("payUrl", url);
				logger.info("payurl ================ " + url);
				//net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(insRs);
				if("0".equals(resultCode)) {
					j.setObj(insRs);
				} else {
					logger.info("insurancePay result info ==== " + insRs);
					message = insRs.get("resultMsg");
					j.setSuccess(false);
				}
			} else {
				j.setSuccess(false);
				message = "支付链接获取失败，请重新发起申请！";
			}
			systemService.addLog(message+":", Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "支付链接获取失败";
			//throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 保单列表页面已核保的直接支付
	 * 
	 * @param policyid
	 * @return
	 */
	@RequestMapping(params = "insurancePays")
	@ResponseBody
	public AjaxJson insurancePays(String policyid, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "支付链接获取成功";
		List<PolicyEntity> list = new ArrayList<PolicyEntity>();
		Map<String, String> insRs = null;
		if(policyid == null) {
			message = "参数错误，请重新发起支付申请！";
			j.setMsg(message);
			j.setSuccess(false);
			return j;
		}
		
		try{
			String[] policyidArr = policyid.split(",");
			//1.页面传入的数据
			for(int i = 0; i < policyidArr.length; i++) {
				String policyid1 = policyidArr[i];
				Map<String, Object> param = policyService.getPolicyPayPage(policyid1);
				if(param == null || param.isEmpty()) {
					continue;
				}
				PolicyEntity policy = new PolicyEntity();
	    		String id = (String)param.get("id");
	    		String proposalNo = (String)param.get("proposal_no");
	    		String orderNo = (String)param.get("order_no");
	    		String policyMobile = (String)param.get("policy_mobile");
	    		policy.setId(id);
	    		policy.setProposalNo(proposalNo);
	    		policy.setOrderNo(orderNo);
	    		policy.setPolicyMobile(policyMobile);
				if(StringUtils.isBlank(proposalNo) || StringUtils.isBlank(orderNo)) {
					message = "保单未核保，请先核保再发起支付申请！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
	    		
	    		list.add(policy);
			}
			//2.调用支付接口
			if(list.isEmpty()) {
				message = "参数错误，请重新发起支付申请！";
				j.setMsg(message);
				j.setSuccess(false);
				return j;
			}
			
			insRs = guorenApiService.payService(list);
			//3.根据支付接口返回的数据，修改保单支付状态
			if(insRs != null && !insRs.isEmpty()) {
				String url = insRs.get("data");
				String resultCode = insRs.get("resultCode");
				request.setAttribute("payUrl", url);
				logger.info("payurl ================ " + url);
				//net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(insRs);
				if("0".equals(resultCode)) {
					j.setObj(insRs);
				} else {
					logger.info("insurancePay result info ==== " + insRs);
					message = insRs.get("resultMsg");
					j.setSuccess(false);
				}
			} else {
				j.setSuccess(false);
				message = "支付链接获取失败，请重新发起申请！";
			}
			systemService.addLog(message+":", Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "支付链接获取失败";
			//throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
