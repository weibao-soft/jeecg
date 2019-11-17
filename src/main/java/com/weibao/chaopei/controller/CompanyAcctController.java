package com.weibao.chaopei.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
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
import com.weibao.chaopei.entity.CompanyUnrewardedDetailEntity;
import com.weibao.chaopei.service.CompanyAcctServiceI;

@Controller
@RequestMapping("/companyAcctController")
public class CompanyAcctController extends BaseController {
	private static final Logger logger = Logger.getLogger(CompanyAcctController.class);
	
	@Resource
	private ClientManager clientManager;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CompanyAcctServiceI companyAcctService;
	
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
	
	@RequestMapping(params = "acctReceiveBalanceDetail")
	public ModelAndView acctReceiveBalanceDetail(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/companyacct/acctReceiveBalanceDetails");
	}
	
	@RequestMapping(params = "acctUnreceiveBalanceDetail")
	public ModelAndView acctUnreceiveBalanceDetail(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/companyacct/acctUnreceiveBalanceDetails");
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
	public void acctReceiveDetailDatagrid(CompanyRewardedDetailEntity comRewardDetailEntity,
			HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

		try{
			//获取当前用户的所属机构
			TSUser user = clientManager.getClient().getUser();
			TSDepart currentDepart = user.getCurrentDepart();
			String departId = currentDepart.getId();
			comRewardDetailEntity.setDepartId(departId);

			List<CompanyAccountEntity> acctList = systemService.findByProperty(CompanyAccountEntity.class, "departId", departId);
			CompanyAccountEntity companyAcct = acctList.get(0);
			comRewardDetailEntity.setCompanyAccountId(companyAcct.getId());
			//查询条件组装器
			if(StringUtil.isNotEmpty(comRewardDetailEntity.getCompanyAccountId())){		
				companyAcctService.getReceiveDetailList(comRewardDetailEntity, dataGrid);
			}
		} catch (SecurityException e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}

		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyui AJAX请求数据测试修改。。
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "acctUnreceiveDetailDatagrid")
	public void acctUnreceiveDetailDatagrid(CompanyUnrewardedDetailEntity comUnrewardDetailEntity,
			HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

		try{
			//获取当前用户的所属机构
			TSUser user = clientManager.getClient().getUser();
			TSDepart currentDepart = user.getCurrentDepart();
			String departId = currentDepart.getId();
			comUnrewardDetailEntity.setDepartId(departId);

			List<CompanyAccountEntity> acctList = systemService.findByProperty(CompanyAccountEntity.class, "departId", departId);
			CompanyAccountEntity companyAcct = acctList.get(0);
			comUnrewardDetailEntity.setCompanyAccountId(companyAcct.getId());
			
			//组装查询条件
			if(StringUtil.isNotEmpty(comUnrewardDetailEntity.getCompanyAccountId())){		
				companyAcctService.getUnreceiveDetailList(comUnrewardDetailEntity, dataGrid);
			}
		} catch (SecurityException e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}

		TagUtil.datagrid(response, dataGrid);
	}
}
