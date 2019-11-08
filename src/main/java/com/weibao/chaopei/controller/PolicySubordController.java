package com.weibao.chaopei.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.weibao.chaopei.page.PolicyMainPage;
import com.weibao.chaopei.service.PolicyServiceI;

@Controller
@RequestMapping("/policySubordController")
public class PolicySubordController extends BaseController {
	private static final Logger logger = Logger.getLogger(PolicySubordController.class);
	
	@Autowired
	private PolicyServiceI  policyService;
	@Autowired
	private SystemService systemService;
	
	/**
	 * 我下级的保单信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "subordin")
	public ModelAndView subordin(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/policy/policySubordinate");
	}
	
	/**
	 * 传入父机构的id数组，获取所有的下级子机构
	 * @param parentIds
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "subordinQuery")
	@ResponseBody
	public ModelAndView subordinQuery(String parentIds, HttpServletRequest request) {
		request.setAttribute("parentIds", parentIds);
		return new ModelAndView("com/weibao/chaopei/policy/policySubordinateList");
	}
	
	/**
	 * 我下级的保单信息列表 页面跳转
	 * 获取当前登录用户的部门id，并打开下级保单查询页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "subordinlist")
	public ModelAndView subordinlist(HttpServletRequest request) {
		try{
			String userId = ResourceUtil.getSessionUser().getId();
			List<String> ids = policyService.getDepartIdByUser(userId);
			String parentId = ids.get(0);
			
			request.setAttribute("parentId", parentId);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			request.setAttribute("parentId", "");
		}
		return new ModelAndView("com/weibao/chaopei/policy/policySubordinateList");
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
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(String parentIds, PolicyMainPage policy, HttpServletRequest request, 
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
			policyService.getPolicyList(policy, dataGrid, userIdList);
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
	 * 传入父机构的id数组，获取所有的下级子机构
	 * @param parentIds
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getAllChildDeparts")
	public List<String> getAllChildDeparts(String parentIds, HttpServletRequest request) {
		List<String> idList = new ArrayList<String>();
		List<String> userIdList = null;
		
		try {
			String parentId = "";
			String[] parentIdss = parentIds.split(",");
			for(int i = 0; i < parentIdss.length; i++) {
				parentId = parentIdss[i];
				if(idList.contains(parentId)) {
					continue;
				} else {
					idList.add(parentId);
				}
				
				getAllChildDepartIds(parentId, idList);
			}
			
			userIdList = policyService.getDepartUserIds(idList);
		} catch(Exception e) {
			logger.info(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return userIdList;
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
	 * 加载ztree
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(params="getTreeData",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson getTreeData(TSDepart depatr, HttpServletResponse response, HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();

		try{
			String userId = ResourceUtil.getSessionUser().getId();
			List<String> ids = policyService.getDepartIdByUser(userId);
			if(ids == null || ids.isEmpty()) {
				j.setObj(dataList);
				return j;
			}
			String parentId = ids.get(0);
			request.setAttribute("parentId", parentId);
			getChildDeparts(parentId, dataList);
			
			j.setObj(dataList);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
		}
		return j;
	}
	
	/**
	 * 获取该机构的下级机构
	 * @param parentId
	 * @param dataList
	 */
	private void getChildDeparts(String parentId, List<Map<String, Object>> dataList) {
		if(dataList == null || parentId == null) {
			return;
		}
		Map<String,Object> map = null;
		List<TSDepart> depatrList = new ArrayList<TSDepart>();
		
		try {
			StringBuffer hql = new StringBuffer(" from TSDepart t ");
			if(parentId != null) {
				hql.append("where t.TSPDepart.id='").append(parentId).append("'");
			}
			//hql.append(" and (parent.id is null or parent.id='')");
			depatrList = this.systemService.findHql(hql.toString());
			for (TSDepart tsdepart : depatrList) {
				map = new HashMap<String,Object>();
				map.put("id", tsdepart.getId());
				map.put("name", tsdepart.getDepartname());
				if (tsdepart.getTSPDepart() != null) {
					map.put("pId", tsdepart.getTSPDepart().getId());
					map.put("open",false);
				}else {
					map.put("pId", "1");
					map.put("open",false);
				}
				dataList.add(map);
				getChildDeparts(tsdepart.getId(), dataList);
				
			}
		} catch(Exception e) {
			logger.info(e.getMessage(), e);
		}
	}
}
