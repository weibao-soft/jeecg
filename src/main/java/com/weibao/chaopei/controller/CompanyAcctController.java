package com.weibao.chaopei.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
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
	
	@RequestMapping(params = "withdrawOrderListBase")
	public ModelAndView withdrawOrderListBase(String accountId, HttpServletRequest request) {
		request.setAttribute("accountId", accountId);
		return new ModelAndView("com/weibao/chaopei/companyacct/withdrawOrderListBase");
	}
	
	@RequestMapping(params = "withdrawOrderDetails")
	public ModelAndView withdrawOrderDetails(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/companyacct/withdrawOrderDetails");
	}
	
	
	/**
	 * 查询已分润的明细
	 * easyui AJAX请求数据查询dataGrid
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
	 * @param user
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
		CriteriaQuery cq = new CriteriaQuery(WithdrawOrderEntity.class, dataGrid);
		try{
		    //自定义追加查询条件
			//CompanyAccountEntity companyAcct = getCompanyAccount();
			//withdrawOrderEntity.setAccountId(companyAcct.getId());
			//查询条件组装器
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, withdrawOrderEntity);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.companyAcctService.getDataGridReturn(cq, true);
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
			//CompanyAccountEntity companyAcct = getCompanyAccount();
			//组装查询条件
			if(StringUtil.isNotEmpty(orderId)){		
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
			//1.页面传入的数据
			//String[] rewardIds = params.split(",");
			
			/*
			Map<String, Object> param = policyService.getPolicyPayPage(policyid);
			PolicyEntity policy = new PolicyEntity();
    		String id = (String)param.get("id");
    		String proposalNo = (String)param.get("proposal_no");
    		String orderNo = (String)param.get("order_no");
    		String policyMobile = (String)param.get("policy_mobile");
    		policy.setId(id);
    		policy.setProposalNo(proposalNo);
    		policy.setOrderNo(orderNo);
    		policy.setPolicyMobile(policyMobile);
			if(StringUtils.isBlank(proposalNo) || StringUtils.isBlank(orderNo)) {
				message = "保单未核保，请先核保再发起支付申请！";
				j.setMsg(message);
				j.setSuccess(false);
				return j;
			}
    		
    		list.add(policy);
			//2.调用支付接口
			if(list.isEmpty()) {
				message = "参数错误，请重新发起支付申请！";
				j.setMsg(message);
				j.setSuccess(false);
				return j;
			}
			
			insRs = guorenApiService.payService(list);
			//3.根据支付接口返回的数据，修改保单支付状态
			if(insRs != null && !insRs.isEmpty()) {
				String url = insRs.get("data");
				String resultCode = insRs.get("resultCode");
				request.setAttribute("payUrl", url);
				System.err.println("payurl ================ " + url);
				//net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(insRs);
				if("0".equals(resultCode)) {
					j.setObj(insRs);
				} else {
					message = insRs.get("resultMsg");
					j.setSuccess(false);
				}
			} else {
				j.setSuccess(false);
				message = "支付链接获取失败，请重新发起申请！";
			}
			systemService.addLog(message+":", Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
			*/
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
