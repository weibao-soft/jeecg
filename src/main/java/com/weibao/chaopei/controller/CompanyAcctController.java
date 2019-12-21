package com.weibao.chaopei.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.weibao.chaopei.entity.CompanyAccountEntity;
import com.weibao.chaopei.entity.CompanyRewardedDetailEntity;
import com.weibao.chaopei.entity.CompanyUnrewardedDetailEntity;
import com.weibao.chaopei.entity.WithdrawOrderEntity;
import com.weibao.chaopei.service.CompanyAcctServiceI;

/**
 * 公司账户管理
 * @author dms
 *
 */
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
	
	
	
	@RequestMapping(params = "goBindAccount")
	public ModelAndView goBindAccount(HttpServletRequest request) {
		//查询公司账户基本信息
		CompanyAccountEntity companyAcct = getCompanyAccount();
		request.setAttribute("companyAcct", companyAcct);
		
		return new ModelAndView("com/weibao/chaopei/companyacct/company-bind");
	}
	
	@RequestMapping(params = "doBindAccount")
	@ResponseBody
	public AjaxJson doBindAccount(CompanyAccountEntity compAcct, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "绑定成功";
		try{			
			//密码加密
			CompanyAccountEntity existsAcct = companyAcctService.getEntity(CompanyAccountEntity.class, compAcct.getId());
			existsAcct.setBankAcctName(compAcct.getRealName());
			existsAcct.setBankInfo(compAcct.getBankInfo());
			existsAcct.setBankNo(compAcct.getBankNo());
			existsAcct.setCertiNo(compAcct.getCertiNo());
			existsAcct.setRealName(compAcct.getRealName());
			
			String password = oConvertUtils.getString(request.getParameter("withdrawPasswd"));
			
			existsAcct.setWithdrawPasswd(PasswordUtil.encrypt(compAcct.getRealName(), password, PasswordUtil.getStaticSalt()));
			companyAcctService.saveOrUpdate(existsAcct);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "绑定失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(params = "withdrawOrCancel")
	@ResponseBody
	public AjaxJson withdrawOrCancel(WithdrawOrderEntity orderEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "操作成功";
		try{			
			//更新提现记录表的状态
			companyAcctService.withdrawOrCancel(orderEntity);
		}catch(Exception e){
			e.printStackTrace();
			message = "操作失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	@RequestMapping(params = "base")
	public ModelAndView base(HttpServletRequest request) {
		//查询公司账户基本信息
		CompanyAccountEntity companyAcct = getCompanyAccount();
		request.setAttribute("companyAcct", companyAcct);
		
		return new ModelAndView("com/weibao/chaopei/companyacct/companyAcctBaseInfo");
	}
	
	@RequestMapping(params = "acctReceiveBalanceDetail")
	public ModelAndView acctReceiveBalanceDetail(String companyAccountId, HttpServletRequest request) {
		CompanyAccountEntity companyAcct = getCompanyAccount();
		//如果有任何一个属性是空的，则需要弹出页面框让用户绑定信息
		if(StringUtil.isEmpty(companyAcct.getBankAcctName()) ||  StringUtil.isEmpty(companyAcct.getBankInfo())
				|| StringUtil.isEmpty(companyAcct.getBankNo()) || StringUtil.isEmpty(companyAcct.getCertiNo())
				|| StringUtil.isEmpty(companyAcct.getWithdrawPasswd())){
			request.setAttribute("isNeedBind", true);
		}else {
			request.setAttribute("isNeedBind", false);
		}
		request.setAttribute("companyAccountId", companyAccountId);

		return new ModelAndView("com/weibao/chaopei/companyacct/acctReceiveBalanceDetails");
	}
	
	@RequestMapping(params = "acctUnreceiveBalanceDetail")
	public ModelAndView acctUnreceiveBalanceDetail(String companyAccountId, HttpServletRequest request) {
		request.setAttribute("companyAccountId", companyAccountId);
		return new ModelAndView("com/weibao/chaopei/companyacct/acctUnreceiveBalanceDetails");
	}
	
	@RequestMapping(params = "withdrawOrderList")
	public ModelAndView withdrawOrderList(String accountId, HttpServletRequest request) {
		request.setAttribute("accountId", accountId);
		return new ModelAndView("com/weibao/chaopei/companyacct/withdrawOrderList");
	}
	
	@RequestMapping(params = "withdrawOrderListAll")
	public ModelAndView withdrawOrderListAll(String accountId, HttpServletRequest request) {		
		return new ModelAndView("com/weibao/chaopei/companyacct/withdrawOrderListAll");
	}
	
	@RequestMapping(params = "withdrawOrderListBase")
	public ModelAndView withdrawOrderListBase(String accountId, HttpServletRequest request) {
		request.setAttribute("accountId", accountId);
		return new ModelAndView("com/weibao/chaopei/companyacct/withdrawOrderListBase");
	}
	
	@RequestMapping(params = "withdrawOrderListBaseAll")
	public ModelAndView withdrawOrderListBaseAll(String accountId, HttpServletRequest request) {
		request.setAttribute("accountId", accountId);
		return new ModelAndView("com/weibao/chaopei/companyacct/withdrawOrderListBaseAll");
	}
	
	@RequestMapping(params = "withdrawOrderDetails")
	public ModelAndView withdrawOrderDetails(String orderId, HttpServletRequest request) {
		request.setAttribute("orderId", orderId);
		return new ModelAndView("com/weibao/chaopei/companyacct/withdrawOrderDetails");
	}
	
	
	/**
	 * 查询已分润的明细
	 * easyui AJAX请求数据查询dataGrid
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 *
	 */
	@RequestMapping(params = "acctReceiveDetailDatagrid")
	public void acctReceiveDetailDatagrid(CompanyRewardedDetailEntity comRewardDetailEntity,
			HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

		try{
			//获取当前用户的所属机构
			String departId = getCurrentDepartId();
			comRewardDetailEntity.setDepartId(departId);
			//CompanyAccountEntity companyAcct = getCompanyAccount();
			//comRewardDetailEntity.setCompanyAccountId(companyAcct.getId());
			
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
	 * 查询未分润的明细
	 * easyui AJAX请求数据查询dataGrid
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 *
	 */
	@RequestMapping(params = "acctUnreceiveDetailDatagrid")
	public void acctUnreceiveDetailDatagrid(CompanyUnrewardedDetailEntity comUnrewardDetailEntity,
			HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

		try{
			//获取当前用户的所属机构
			String departId = getCurrentDepartId();
			comUnrewardDetailEntity.setDepartId(departId);
			//CompanyAccountEntity companyAcct = getCompanyAccount();
			//comUnrewardDetailEntity.setCompanyAccountId(companyAcct.getId());
			
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
	
	/**
	 * 根据部门id获取公司账号，根据公司账户id查询提现记录列表
	 * easyui AJAX请求数据查询dataGrid
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "withdrawOrderDatagrid")
	public void withdrawOrderDatagrid(WithdrawOrderEntity withdrawOrderEntity, HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid) {
		try{
		    //自定义追加查询条件
			companyAcctService.getWithdrawOrderList(withdrawOrderEntity, dataGrid);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

		TagUtil.datagrid(response, dataGrid);
	}
	
	
	@RequestMapping(params = "withdrawOrderDatagridAll")
	public void withdrawOrderDatagridAll(WithdrawOrderEntity withdrawOrderEntity, HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid) {
		try{
		    //自定义追加查询条件
			companyAcctService.withdrawOrderDatagridAll(withdrawOrderEntity, dataGrid);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 根据部门id获取公司账号，根据公司账户id查询提现记录明细列表
	 * easyui AJAX请求数据查询dataGrid
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "withdrawDetailDatagrid")
	public void withdrawDetailDatagrid(String orderId, HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid) {
		try{
		    //自定义追加查询条件
			if(StringUtil.isNotEmpty(orderId)){		
				orderId = orderId.replace(",", "");
				companyAcctService.getWithdrawDetailList(orderId, dataGrid);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 根据部门id获取公司账户信息
	 * @return
	 */
	protected CompanyAccountEntity getCompanyAccount() {
		CompanyAccountEntity companyAcct = null;
		try {
			String departId = getCurrentDepartId();
			List<CompanyAccountEntity> acctList = systemService.findByProperty(CompanyAccountEntity.class, "departId", departId);
			if(acctList == null || acctList.isEmpty()) {
				companyAcct = new CompanyAccountEntity();
			} else {
				companyAcct = acctList.get(0);
			}
		} catch(Exception e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}
		return companyAcct;
	}
	
	/**
	 * 获取当前用户的部门id
	 * @return
	 */
	private String getCurrentDepartId() {
		String departId = "";
		try {
			//获取当前用户的所属机构
			TSUser user = clientManager.getClient().getUser();
			TSDepart currentDepart = user.getCurrentDepart();
			departId = currentDepart.getId();
		} catch(Exception e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}
		return departId;
	}
	
	/**
	 * 批量提现
	 * @param params 用逗号分隔的分润明细id
	 * @return
	 */
	@RequestMapping(params = "withdraw")
	@ResponseBody
	public AjaxJson withdraw(String params, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "申请提现成功！";
		
		//Map<String, String> insRs = null;
		if(params == null) {
			message = "参数错误，请重新发起提现申请！";
			j.setMsg(message);
			j.setSuccess(false);
			return j;
		}

		try{
            companyAcctService.withdrawCompany(params);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "支付链接获取失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
