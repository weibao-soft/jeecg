package com.weibao.chaopei.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.weibao.chaopei.entity.CompanyAccountEntity;
import com.weibao.chaopei.entity.CompanyRewardedDetailEntity;

@Controller
@RequestMapping("/companyAcctController")
public class CompanyAcctController extends BaseController {
	@Resource
	private ClientManager clientManager;
	@Autowired
	private SystemService systemService;
	
	@RequestMapping(params = "main")
	public ModelAndView main(HttpServletRequest request) {		
		return new ModelAndView("com/weibao/chaopei/companyacct/companyAcctMain");
	}
	
	@RequestMapping(params = "base")
	public ModelAndView base(HttpServletRequest request) {
		//查询公司账户基本信息
		TSUser user = clientManager.getClient().getUser();
		TSDepart currentDepart = user.getCurrentDepart();
		List<CompanyAccountEntity> acctList = systemService.findByProperty(CompanyAccountEntity.class, "departId", currentDepart.getId());
		CompanyAccountEntity companyAcct = acctList.get(0);
		request.setAttribute("companyAcct", companyAcct);
		return new ModelAndView("com/weibao/chaopei/companyacct/companyAcctBaseInfo");
	}
	
	@RequestMapping(params = "companyAcctBalanceDetail")
	public ModelAndView companyAcctBalanceDetail(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/companyacct/acctReceiveBalanceDetails");
	}
	
	
	/**
	 * easyui AJAX请求数据测试修改。。
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "acctReceiveDetailDatagrid")
	public void acctReceiveDetailDatagrid(CompanyRewardedDetailEntity comRewardDetailEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		CriteriaQuery cq = new CriteriaQuery(CompanyRewardedDetailEntity.class, dataGrid);
		
		if(StringUtil.isNotEmpty(comRewardDetailEntity.getCompanyAccountId())){		
			//查询条件组装器
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, comRewardDetailEntity);
			try{
				//	自定义追加查询条件
				cq.add();
				this.systemService.getDataGridReturn(cq, true);
			}catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}			
		}
				
		TagUtil.datagrid(response, dataGrid);
	}
	
}
