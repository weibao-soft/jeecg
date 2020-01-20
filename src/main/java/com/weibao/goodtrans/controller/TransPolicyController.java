package com.weibao.goodtrans.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.weibao.goodtrans.entity.TransPolicyEntity;
import com.weibao.goodtrans.page.TransPolicyPage;
import com.weibao.goodtrans.service.TransServiceI;

/**
 * 货运险保单投保
 */
@Controller
@RequestMapping("/transPolicyController")
public class TransPolicyController extends BaseController {
	private static final Logger logger = Logger.getLogger(TransPolicyController.class);
	
	Gson gson = new GsonBuilder().create();	

	@Autowired
	private TransServiceI transService;
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * 我的保单信息列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/freight/freightPolicyListBase");
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
		//return new ModelAndView("com/weibao/chaopei/freight/freightPolicyAdd");
		return new ModelAndView("com/weibao/goodtrans/goodTransAdd");
	}
	
	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(TransPolicyEntity transEntity, HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid) {
		
		CriteriaQuery cq = new CriteriaQuery(TransPolicyEntity.class, dataGrid);
		try{
			//自定义追加查询条件
			//查询当前用户下的草稿单
			TSUser currentUser = ResourceUtil.getSessionUser();
			String status = "1";
			transEntity.setStatus(status);			
			transEntity.setUserId(currentUser.getId());						
			//查询条件组装器
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, transEntity);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.transService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "transDatagrid")
	public void transDatagrid(TransPolicyPage policy, HttpServletRequest request,
						 HttpServletResponse response, DataGrid dataGrid) {

		try{
			String userId = ResourceUtil.getSessionUser().getId();
			policy.setUserId(userId);
			//组装查询条件
			transService.getPolicyList(policy, dataGrid);
		} catch (SecurityException e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 添加货运险保单
	 * @param freightPolicyPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	public ModelAndView doAdd(TransPolicyPage transPolicyPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			//String userId = request.getParameter("userId");
			String userId = ResourceUtil.getSessionUser().getId();
			transPolicyPage.setUserId(userId);
			transService.addMain(transPolicyPage);
			systemService.addLog(message+":", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "货运保单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return new ModelAndView("com/weibao/goodtrans/freightPolicyListBase");
	}
	
	/**
	 * 修改货运险保单
	 * @param freightPolicyPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	public ModelAndView doUpdate(TransPolicyPage transPolicyPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "修改成功";
		try{
			String userId = ResourceUtil.getSessionUser().getId();
			transPolicyPage.setUserId(userId);
			transService.updateMain(transPolicyPage);
			systemService.addLog(message+":", Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "货运保单修改失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		request.setAttribute("transPolicyPage", transPolicyPage);
		return new ModelAndView("com/weibao/goodtrans/freightPolicyListBase");
	}

	/**
	 * 删除未支付状态的保单，已支付状态的保单不能删除
	 * @param policyId
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(String transId, String payStatus, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "删除成功";
		try {
			if(!"0".equals(payStatus)) {
				j.setSuccess(false);
				message = "不能删除已支付的保单";
				j.setMsg(message);
				return j;
			}
			transService.delMain(transId);
			systemService.addLog(message+":", Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		} catch(HibernateException e) {
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单删除失败";
			throw new BusinessException("保单删除失败，原因：" + e.getMessage());
		} catch(Exception e) {
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单删除失败";
		}
		j.setMsg(message);
		return j;
	}
}
