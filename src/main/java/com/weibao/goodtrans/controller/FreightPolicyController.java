package com.weibao.goodtrans.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com.weibao.chaopei.entity.DraftEntity;
import com.weibao.chaopei.service.GuorenApiServiceI;
import com.weibao.goodtrans.entity.FreightPolicyEntity;
import com.weibao.goodtrans.page.FreightPolicyPage;
import com.weibao.goodtrans.service.FreightServiceI;

@Controller
@RequestMapping("/freightPolicyController")
public class FreightPolicyController extends BaseController {

	private static final Logger logger = Logger.getLogger(FreightPolicyController.class);
	
	@Autowired
	private FreightServiceI freightService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private GuorenApiServiceI guorenApiService;

	/**
	 * 我的保单信息列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weibao/goodtrans/freightPolicyListBase");
	}

	/**
	 * 保单信息新增 页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(String paramId, HttpServletRequest request) {
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
		return new ModelAndView("com/weibao/goodtrans/freightPolicyAdd");
	}
	
	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(FreightPolicyEntity freightEntity, HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid) {
		
		CriteriaQuery cq = new CriteriaQuery(DraftEntity.class, dataGrid);
		try{
			//自定义追加查询条件
			//查询当前用户下的草稿单
			TSUser currentUser = ResourceUtil.getSessionUser();
			String status = "1";
			freightEntity.setStatus(status);			
			freightEntity.setUserId(currentUser.getId());						
			//查询条件组装器
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, freightEntity);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.freightService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 添加货运险保单
	 * @param freightPolicyPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	public ModelAndView doAdd(FreightPolicyPage freightPolicyPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			//String userId = request.getParameter("userId");
			String userId = ResourceUtil.getSessionUser().getId();
			freightPolicyPage.setUserId(userId);
			freightService.addMain(freightPolicyPage);
			systemService.addLog(message+":", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "货运保单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return new ModelAndView("com/weibao/chaopei/policy/draftMainListBase");
	}
	
	/**
	 * 货运险保单核保支付
	 * @param freightPolicyPage
	 * @return
	 */
	@RequestMapping(params = "insurancePay")
	@ResponseBody
	public AjaxJson insurancePay(FreightPolicyPage freightPolicyPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "支付成功";
		Map<String, String> insRs = new HashMap<String, String>();
		try{
			String userId = ResourceUtil.getSessionUser().getId();
			freightPolicyPage.setUserId(userId);
			//1.先将草稿单、保单、投保人等信息写进数据库，保单状态为草稿
			FreightPolicyEntity policy = freightService.addMain(freightPolicyPage);
			String freightId = freightPolicyPage.getId();
			//2.调用支付接口
			//调用支付接口，insRs = guorenApiService.payService(policy);
			j.setObj(insRs);
			//3.根据支付接口返回的数据，修改保单支付状态
			if(insRs != null && !insRs.isEmpty()) {
				String url = insRs.get("data");
				String resultCode = insRs.get("resultCode");
				request.setAttribute("payUrl", url);
				logger.info("payurl ================ " + url);
				//net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(insRs);
				if("0".equals(resultCode)) {
					//修改保单状态，freightService.updatePolicyStatus(policy, freightId);
					j.setObj(insRs);
				} else {
					logger.info("insurancePay result info ==== " + insRs);
					message = insRs.get("resultMsg");
					j.setSuccess(false);
				}
			} else {
				j.setSuccess(false);
				message = "支付链接获取失败，请重新发起申请！";
				j.setMsg(message);
				j.setObj(freightPolicyPage);
				return j;
			}
			systemService.addLog(message+":", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单支付失败";
			//throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		request.setAttribute("freightPolicyPage", freightPolicyPage);
		return j;
	}

}
