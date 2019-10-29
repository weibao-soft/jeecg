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

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.weibao.chaopei.entity.DraftEntity;
import com.weibao.chaopei.entity.HolderEntity;
import com.weibao.chaopei.entity.InsuredEntity;
import com.weibao.chaopei.page.PolicyMainPage;
import com.weibao.chaopei.service.PolicyServiceI;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/policyMainController")
public class PolicyMainController extends BaseController {
	private static final Logger logger = Logger.getLogger(PolicyMainController.class);
	
	private static final String ISO8859 = "ISO8859-1";
	
	private static final String UTF8 = "UTF-8";
	
	@Autowired
	private PolicyServiceI  policyService;
	@Autowired
	private SystemService systemService;

	/**
	 * 保单主信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/policy/policyMainList");
	}
	
	/**
	 * 我的保单信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "mainlist")
	public ModelAndView mainlist(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/policy/policyMainListBase");
	}

	/**
	 * 保单主信息新增 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest request) {
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
			canlendar.setTime(startDate);
			int startYear = canlendar.get(Calendar.YEAR);
			canlendar.setTime(endDate);
			int endYear = canlendar.get(Calendar.YEAR);
			int year = endYear - startYear;
			
	        String start = sdfd.format(startDate);
	        String end = sdfd.format(endDate);
	        String max = sdfd.format(endDate);
			request.setAttribute("start", start);
			request.setAttribute("end", end);
			request.setAttribute("max", max);
			request.setAttribute("year", year);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		request.setAttribute("policyMainPage", policyMainPage);
		return new ModelAndView("com/weibao/chaopei/policy/policyMainUpdate");
	}
	
	/**
	 * 专票信息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addSpe")
	public ModelAndView addSpe(PolicyMainPage policyMainPage, HttpServletRequest req) {
		try {
			req.setCharacterEncoding(UTF8);
			setCharacterEncoding(policyMainPage);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		if (StringUtil.isNotEmpty(policyMainPage.getId())) {
			//holderEntity = policyService.getEntity(HolderEntity.class, holderEntity.getId());
		}
		req.setAttribute("policyMainPage", policyMainPage);
		return new ModelAndView("com/weibao/chaopei/policy/speInvoiceAdd");
	}
	
	/**
	 * 从页面上传来的中文参数，由"ISO8859-1"编码转换为"UTF-8"
	 * @param policyMainPage
	 */
	private void setCharacterEncoding(PolicyMainPage policyMainPage) {
		try {
			String compName = policyMainPage.getCompName();
			String compAddress = policyMainPage.getCompAddress();
			String depositBank = policyMainPage.getDepositBank();
			String recipients = policyMainPage.getRecipients();
			String reciAddress = policyMainPage.getReciAddress();
			compName = (compName != null) ? new String(compName.getBytes(ISO8859), UTF8) : compName;
			compAddress = (compAddress != null) ? new String(compAddress.getBytes(ISO8859), UTF8) : compAddress;
			depositBank = (depositBank != null) ? new String(depositBank.getBytes(ISO8859), UTF8) : depositBank;
			recipients = (recipients != null) ? new String(recipients.getBytes(ISO8859), UTF8) : recipients;
			reciAddress = (reciAddress != null) ? new String(reciAddress.getBytes(ISO8859), UTF8) : reciAddress;
			policyMainPage.setCompName(compName);
			policyMainPage.setCompAddress(compAddress);
			policyMainPage.setDepositBank(depositBank);
			policyMainPage.setRecipients(recipients);
			policyMainPage.setReciAddress(reciAddress);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
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
	public void datagrid(DraftEntity draftEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DraftEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, draftEntity);
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.policyService.getDataGridReturn(cq, true);
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
			policyService.addMain(policyMainPage);
			systemService.addLog(message+":", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单主信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return new ModelAndView("com/weibao/chaopei/policy/policyMainList");
	}
	
	/**
	 * 修改保单主信息
	 * 
	 * @param policyMainPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(PolicyMainPage policyMainPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "修改成功";
		try{
			//String userId = ResourceUtil.getSessionUser().getId();
			//policyMainPage.setUserId(userId);
			policyService.updateMain(policyMainPage);
			systemService.addLog(message+":", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单主信息修改失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(policyMainPage);
		return j;
	}
	
	/**
	 *  查询保单投保人列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "getHolders")
	@ResponseBody
	public JSONObject getHolders(HttpServletRequest request) {
		JSONObject object = new JSONObject();
		List<Map<String, String>> holders = new ArrayList<Map<String, String>>();
		String message = "查询成功";
		try{
			holders = policyService.getPolicyHolders();
			net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(holders);
			//String value = JSONArray.toJSONString(array);
			object.put("value", array);
			object.put("code", 200);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			object.put("code", 201);
			message = "保单投保人查询失败";
			throw new BusinessException(e.getMessage());
		}
		object.put("message", message);
		return object;
	}
	
	/**
	 *  查询保单被保人列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "getInsureds")
	@ResponseBody
	public JSONObject getInsureds(HttpServletRequest request) {
		JSONObject object = new JSONObject();
		List<InsuredEntity> holders = new ArrayList<InsuredEntity>();
		String message = "查询成功";
		try{
			holders = policyService.getPolicyInsureds();
			net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(holders);
			//String value = JSONArray.toJSONString(array);
			object.put("value", array);
			object.put("code", 200);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			object.put("code", 201);
			message = "保单投保人查询失败";
			throw new BusinessException(e.getMessage());
		}
		object.put("message", message);
		return object;
	}
	
	/**
	 *  查询保单投保人
	 * 
	 * @return
	 */
	@RequestMapping(params = "getHolderById")
	@ResponseBody
	public JSONObject getHolderById(String id, HttpServletRequest request) {
		JSONObject object = new JSONObject();
		HolderEntity holders = null;
		String message = "查询成功";
		try{
			holders = policyService.getHolderById(id);
			JSONObject obj = JSONObject.fromObject(holders);
			//String value = obj.toString();
			object.put("value", obj);
			object.put("code", 200);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			object.put("code", 201);
			message = "保单投保人查询失败";
			throw new BusinessException(e.getMessage());
		}
		object.put("message", message);
		return object;
	}
}
