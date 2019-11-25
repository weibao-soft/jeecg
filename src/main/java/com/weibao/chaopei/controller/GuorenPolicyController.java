package com.weibao.chaopei.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.weibao.chaopei.page.InvoiceExportPage;
import com.weibao.chaopei.page.PolicyMainPage;
import com.weibao.chaopei.service.PolicyServiceI;

@Controller
@RequestMapping("/guorenPolicyController")
public class GuorenPolicyController extends BaseController {
	private static final Logger logger = Logger.getLogger(GuorenPolicyController.class);
	
	@Autowired
	private PolicyServiceI  policyService;
	
	
	/**
	 * 
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
	 * 
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
	 * 
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
	
}
