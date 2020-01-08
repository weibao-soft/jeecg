package com.weibao.chaopei.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jeecg.demo.util.FreemarkerUtil;
import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.entity.ProductDetailEntity;
import com.weibao.chaopei.page.InvoiceExportPage;
import com.weibao.chaopei.page.PolicyMainPage;
import com.weibao.chaopei.service.PolicyServiceI;

import cn.hutool.core.convert.Convert;

@Controller
@RequestMapping("/guorenPolicyController")
public class GuorenPolicyController extends BaseController {
	private static final Logger logger = Logger.getLogger(GuorenPolicyController.class);

	private static final String UTF8 = "UTF-8";
	
	@Autowired
	private PolicyServiceI  policyService;
	
	
	/**
	 * 跳转到查询列表页面
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		try{
			String userId = ResourceUtil.getSessionUser().getId();
			List<String> ids = policyService.getDepartIdByUser(userId);
			String parentId = ids.get(0);			
			request.setAttribute("parentId", parentId);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			request.setAttribute("parentId", "");
		}
		return new ModelAndView("com/weibao/chaopei/guoren/guorenPolicyList");
	}
	
	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(PolicyMainPage policy, HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid) {
		List<String> idList = new ArrayList<String>();
		List<String> userIdList = null;
		
		try{
			String parentId = policy.getDepartId();
			if(StringUtils.isBlank(parentId)) {
				parentId = getDepartId(request);
			}
			if(StringUtils.isNotBlank(parentId)) {
				idList.add(parentId);
				getAllChildDepartIds(parentId, idList);
				userIdList = policyService.getDepartUserIds(idList);
			}
			//userIdList = getAllChildDeparts(parentIds, request);
			policyService.getGuorenPolicyList(policy, dataGrid, userIdList);
			//查询条件组装器
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
	 * 获取当前登录用户的部门id
	 * @param request
	 * @return
	 */
	public String getDepartId(HttpServletRequest request) {
		String parentId = "";
		
		try{
			String userId = ResourceUtil.getSessionUser().getId();
			List<String> ids = policyService.getDepartIdByUser(userId);
			parentId = ids.get(0);
			
			request.setAttribute("parentId", parentId);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			request.setAttribute("parentId", "");
		}
		return parentId;
	}
	
	
	
	/**
	 * 根据父机构的id获取下级所有子机构的id
	 */
	private void getAllChildDepartIds(String parentId, List<String> idList) {
		if(idList == null || parentId == null) {
			return;
		}
		
		List<String> ids = policyService.getChildDepartIds(parentId);
		for(int i = 0; i < ids.size(); i++) {
			String departId = ids.get(i);
			if(idList.contains(departId)) {
				continue;
			} else {
				idList.add(departId);
				getAllChildDepartIds(departId, idList);
			}
		}
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(PolicyMainPage policy,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		
		List<String> idList = new ArrayList<String>();
		List<String> userIdList = null;
		try{
			String parentId = policy.getDepartId();
			if(StringUtils.isBlank(parentId)) {
				parentId = getDepartId(request);
			}
			if(StringUtils.isNotBlank(parentId)) {
				idList.add(parentId);
				getAllChildDepartIds(parentId, idList);
				userIdList = policyService.getDepartUserIds(idList);
			}
			policyService.getGuorenPolicyListExport(policy, dataGrid, userIdList);
			//查询条件组装器
		} catch (SecurityException e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}
				
		modelMap.put(NormalExcelConstants.FILE_NAME,"guoren_policy_list");
		modelMap.put(NormalExcelConstants.CLASS,PolicyMainPage.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("国任保单数据列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,dataGrid.getResults());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	@RequestMapping(params = "ExportTaxiXls")
	public String ExportTaxiXls(PolicyMainPage policy,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		
		List<String> idList = new ArrayList<String>();
		List<String> userIdList = null;
		List<InvoiceExportPage> invoiceList = new ArrayList<InvoiceExportPage>();
		try{
			String parentId = policy.getDepartId();
			if(StringUtils.isBlank(parentId)) {
				parentId = getDepartId(request);
			}
			if(StringUtils.isNotBlank(parentId)) {
				idList.add(parentId);
				getAllChildDepartIds(parentId, idList);
				userIdList = policyService.getDepartUserIds(idList);
			}
			//userIdList = getAllChildDeparts(parentIds, request);
			invoiceList = policyService.getGuorenInvoiceList(policy, dataGrid, userIdList);
		} catch (SecurityException e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}
				
		modelMap.put(NormalExcelConstants.FILE_NAME,"guoren_invoice_list");
		modelMap.put(NormalExcelConstants.CLASS,InvoiceExportPage.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("发票信息", "导出人:"+ResourceUtil.getSessionUser().getRealName(),"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, invoiceList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出Word文档
	 * @param policyId
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportWordDoc")
	public void exportWordDoc(String policyId, HttpServletRequest request, HttpServletResponse response) {
		PolicyMainPage policyMainPage = new PolicyMainPage();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String docFileName = "policy-";
		
		try {
			request.setCharacterEncoding(UTF8);
			response.setCharacterEncoding(UTF8);
			if (StringUtil.isNotEmpty(policyId)) {
				PolicyEntity policyEntity = policyService.getEntity(PolicyEntity.class, policyId);
				ProductDetailEntity proDetailEntity = policyService.getEntity(ProductDetailEntity.class, policyEntity.getPlanId());
				policyMainPage.setProdPlan(proDetailEntity.getProdPlan());
				BeanUtils.copyProperties(policyMainPage, policyEntity);
				getData(dataMap, policyMainPage);
				docFileName += policyMainPage.getPolicyNo() + ".doc";
				FreemarkerUtil.createFile("onlinePolicy.ftl", docFileName, dataMap, request, response, FreemarkerUtil.WORD_FILE);
			}
			
		} catch(IOException e) {
			logger.info(e.getMessage(), e);
		} catch(Exception e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取保单属性
	 * @param dataMap
	 * @param policyMainPage
	 */
	private void getData(Map<String, Object> dataMap, PolicyMainPage policyMainPage) {
		Calendar canlendar = Calendar.getInstance();
		Date startDate = policyMainPage.getStartDate();
		Date endDate = policyMainPage.getEndDate();
		canlendar.setTime(startDate);
		canlendar.add(Calendar.DATE, -1);
		Date policyDate = canlendar.getTime();
		//String startDate = DateUtils.formatDate(startDate);
		//String endDate = DateUtils.formatDate(endDate);
		String strStartDate = DateUtils.date2Str(startDate, DateUtils.date_sdf_wz);
		String strEndDate = DateUtils.date2Str(endDate, DateUtils.date_sdf_wz);
		String strPolicyDate = DateUtils.date2Str(policyDate, DateUtils.date_sdf_wz);
		String premium = policyMainPage.getPremium().toString();
		premium = StringUtil.moneyToString(Double.parseDouble(premium), ",###.00");
		String chnPremium = Convert.digitToChinese(policyMainPage.getPremium());
		String prodPlan = policyMainPage.getProdPlan();
		String[] insuredAmounts = getInsuredAmount(prodPlan);
		dataMap.put("startDate", strStartDate);
		dataMap.put("endDate", strEndDate);
		dataMap.put("policyDate", strPolicyDate);
		dataMap.put("chnPremium", chnPremium);
		dataMap.put("onceInsAmount", insuredAmounts[0]);
		dataMap.put("totalInsAmount", insuredAmounts[1]);
		dataMap.put("premium", premium);
		dataMap.put("holderCompName", policyMainPage.getHolderCompName());
		dataMap.put("insuredCompName", policyMainPage.getInsuredCompName());
		dataMap.put("compAddress", policyMainPage.getCompAddress());
		dataMap.put("policyNo", policyMainPage.getPolicyNo());
		dataMap.put("plateNo", policyMainPage.getPlateNo());
		dataMap.put("frameNo", policyMainPage.getFrameNo());
	}
	
	/**
	 * 获取每次保额和累计保额
	 * @param prodPlan
	 * @return
	 */
	private String[] getInsuredAmount(String prodPlan) {
		String[] insuredAmounts = new String[2];
		String[] prodPlans = null;
		String insuredAmount1 = "";
		String insuredAmount2 = "";
		
		if(prodPlan.indexOf(',') != -1) {
			prodPlans = prodPlan.split(",");
		} else if(prodPlan.indexOf('，') != -1) {
			prodPlans = prodPlan.split("，");
		}
		if(prodPlans != null) {
			if(prodPlans.length > 1) {
				insuredAmount2 = cutNumFromString(prodPlans[1]);
			}
			insuredAmount1 = cutNumFromString(prodPlans[0]);
		} else {
			insuredAmount1 = cutNumFromString(prodPlan);
		}
		insuredAmounts[0] = insuredAmount1;
		insuredAmounts[1] = insuredAmount2;
		return insuredAmounts;
	}
	
	/**
	 * 获取格式化后的保额
	 * @param plan
	 * @return
	 */
	private String cutNumFromString(String plan) {
		String insuredAmount = "";
		char[] chars = plan.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] >= 48 && chars[i] <= 57) {
				insuredAmount += chars[i];
			}
		}
		
		insuredAmount += "0000.00";
		insuredAmount = StringUtil.moneyToString(Double.parseDouble(insuredAmount), ",###.00");
		return insuredAmount;
	}
}
