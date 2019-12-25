package com.weibao.goodtrans.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ishdr.pay.model.PayModel;
import com.ishdr.pay.utils.PayClient;
import com.weibao.chaopei.service.GuorenApiServiceI;
import com.weibao.common.util.IshdrPayUtil;
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
		
		CriteriaQuery cq = new CriteriaQuery(FreightPolicyEntity.class, dataGrid);
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
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "freightDatagrid")
	public void freightDatagrid(FreightPolicyPage policy, HttpServletRequest request,
						 HttpServletResponse response, DataGrid dataGrid) {

		try{
			String userId = ResourceUtil.getSessionUser().getId();
			policy.setUserId(userId);
			//组装查询条件
			freightService.getPolicyList(policy, dataGrid);
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
		return new ModelAndView("com/weibao/goodtrans/freightPolicyListBase");
	}
	
	/**
	 * 修改货运险保单
	 * 
	 * @param freightPolicyPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	public ModelAndView doUpdate(FreightPolicyPage freightPolicyPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "修改成功";
		try{
			String userId = ResourceUtil.getSessionUser().getId();
			freightPolicyPage.setUserId(userId);
			freightService.updateMain(freightPolicyPage);
			systemService.addLog(message+":", Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "货运保单修改失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		request.setAttribute("freightPolicyPage", freightPolicyPage);
		return new ModelAndView("com/weibao/goodtrans/freightPolicyListBase");
	}

	/**
	 * 删除未支付状态的保单，已支付状态的保单不能删除
	 *
	 * @param policyId
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(String freightId, String payStatus, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "删除成功";
		try {
			if(!"0".equals(payStatus)) {
				j.setSuccess(false);
				message = "不能删除已支付的保单";
				j.setMsg(message);
				return j;
			}
			freightService.delMain(freightId);
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
	
	/**
	 * 新增保单，并发起支付
	 * @param freightPolicyPage
	 * @return
	 */
	@RequestMapping(params = "insuranceAdd")
	@ResponseBody
	public AjaxJson insuranceAdd(FreightPolicyPage freightPolicyPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "新增成功";
		try{
			String userId = ResourceUtil.getSessionUser().getId();
			freightPolicyPage.setUserId(userId);
			//1.先将草稿单、保单、投保人等信息写进数据库，保单状态为草稿
			FreightPolicyEntity policy = freightService.addMain(freightPolicyPage);
			//String freightId = freightPolicyPage.getId();
			
			//2.调用核保接口
			//List<Map<String, String>> insRsList = guorenApiService.insuredService(list);
			//j.setObj(insRsList);
			//TODO：如果提交核保的是3台车，但是返回的只有2台车，这种情况如何处理？？？
			
			//3.调用支付接口
			j = policyPay(policy, request);
			if(!j.isSuccess()) {
				freightPolicyPage.setPayStatus(policy.getPayStatus());
			}
			systemService.addLog(j.getMsg()+":", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			message = "保单新增失败：连接获取失败，请联系管理员处理！";
			j.setSuccess(false);
			j.setMsg(message);
			//throw new BusinessException(e.getMessage());
		}
		j.setBack(freightPolicyPage);
		return j;
	}
	
	/**
	 * 支付失败后，修改保单，并重新发起支付
	 * @param freightPolicyPage
	 * @return
	 */
	@RequestMapping(params = "insuranceUpdate")
	@ResponseBody
	public AjaxJson insuranceUpdate(FreightPolicyPage freightPolicyPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "修改成功";
		try{
			String userId = ResourceUtil.getSessionUser().getId();
			freightPolicyPage.setUserId(userId);
			//1.先修改草稿单、保单、投保人等信息，保单状态为草稿
			FreightPolicyEntity policy = freightService.updateMain(freightPolicyPage);
			//String freightId = freightPolicyPage.getId();
			
			//2.调用核保接口
			//List<Map<String, String>> insRsList = guorenApiService.insuredService(list);
			//j.setObj(insRsList);
			//TODO：如果提交核保的是3台车，但是返回的只有2台车，这种情况如何处理？？？
			
			//3.调用支付接口
			j = policyPay(policy, request);
			if(!j.isSuccess()) {
				freightPolicyPage.setPayStatus(policy.getPayStatus());
			}
			systemService.addLog(j.getMsg()+":", Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			message = "保单修改失败：连接获取失败，请联系管理员处理！";
			j.setSuccess(false);
			j.setMsg(message);
			//throw new BusinessException(e.getMessage());
		}
		j.setBack(freightPolicyPage);
		return j;
	}
	
	/**
	 * 货运险保单支付
	 * @param freightPolicyPage
	 * @return
	 */
	@RequestMapping(params = "policyPay")
	@ResponseBody
	public AjaxJson policyPay(FreightPolicyEntity policy, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "支付链接获取成功";
		Map<String, String> insRs = new HashMap<String, String>();
		try{
			String freightId = policy.getId();
			//1. 调用支付接口
			String payUrl = freightPolicyPay(policy);
			insRs.put("payUrl", payUrl);
			//2. 根据支付接口返回的数据，修改保单支付状态
			if(payUrl != null && !"".equals(payUrl)) {
				logger.info("payurl ================ " + payUrl);
				//修改保单状态为：已核保，支付中
				freightService.updatePolicyStatus(freightId, "2");
				//policy.setPayStatus("2");
				j.setObj(insRs);
			} else {
				j.setSuccess(false);
				message = "支付链接获取失败，请重新发起申请！";
				j.setMsg(message);
				return j;
			}
			systemService.addLog(message+":", Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单支付失败";
			//throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 保单列表页面已核保的直接支付
	 * @param policyid
	 * @return
	 */
	@RequestMapping(params = "insurancePays")
	@ResponseBody
	public AjaxJson insurancePays(String policyid, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "支付链接获取成功";
		Map<String, String> insRs = new HashMap<String, String>();
		if(policyid == null) {
			message = "参数错误，请重新发起支付申请！";
			j.setMsg(message);
			j.setSuccess(false);
			return j;
		}
		try{
			//1.页面传入的数据
			Map<String, Object> param = freightService.getPolicyPayPage(policyid);
			if(param == null || param.isEmpty()) {
				message = "参数错误，请重新发起支付申请！";
				j.setMsg(message);
				j.setSuccess(false);
				return j;
			}
			FreightPolicyEntity policy = new FreightPolicyEntity();
    		BigDecimal premium = (BigDecimal)param.get("all_premium");
    		String goodsName = (String)param.get("goods_name");
    		//String id = (String)param.get("id");
    		//String proposalNo = (String)param.get("proposal_no");
    		//String orderNo = (String)param.get("order_no");
    		policy.setId(policyid);
    		policy.setAllPremium(premium);
    		policy.setGoodsName(goodsName);
	    		
			//2. 调用支付接口
			String payUrl = freightPolicyPay(policy);
			insRs.put("payUrl", payUrl);
			//3. 根据支付接口返回的数据，修改保单支付状态
			if(payUrl != null && !"".equals(payUrl)) {
				logger.info("payurl ================ " + payUrl);
				//修改保单状态为：已核保，支付中
				freightService.updatePolicyStatus(policyid, "2");
				//policy.setPayStatus("2");
				j.setObj(insRs);
			} else {
				j.setSuccess(false);
				message = "支付链接获取失败，请重新发起申请！";
				j.setMsg(message);
				return j;
			}
			systemService.addLog(message+":", Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单支付失败";
			//throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(FreightPolicyPage policy, HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
		String userName = "";

		try{
			userName = ResourceUtil.getSessionUser().getRealName();
			freightService.getPolicyList(policy, dataGrid);
			//查询条件组装器
		} catch (SecurityException e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}
		
		modelMap.put(NormalExcelConstants.FILE_NAME, "guoren_freight_list");
		modelMap.put(NormalExcelConstants.CLASS, FreightPolicyPage.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("永安货运保单数据列表", "导出人:" + userName, "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, dataGrid.getResults());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	//调用永安货运险支付接口
	public String freightPolicyPay(FreightPolicyEntity policy) 
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
        PayClient payClient = new PayClient(IshdrPayUtil.MD5_KEY, IshdrPayUtil.PAY_AES_KEY, IshdrPayUtil.REFUND_AES_KEY);
        PayModel payModel = new PayModel();
        payModel.setSubject(policy.getGoodsName());
        BigDecimal premium = policy.getAllPremium().multiply(new BigDecimal(100));
        int price = premium.intValue();
        payModel.setPrice(price);
        payModel.setPartnerCode(IshdrPayUtil.WEIBAO);
        String partnerTransCode = policy.getId();
        System.err.println(partnerTransCode);
        payModel.setPartnerTransCode(partnerTransCode);
        String payUrl = payClient.getPayUrl(payModel);
        return payUrl;
	}
}
