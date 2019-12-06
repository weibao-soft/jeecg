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
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.weibao.chaopei.entity.PersonalAccountEntity;
import com.weibao.chaopei.entity.PersonalRewardedDetailEntity;
import com.weibao.chaopei.entity.PersonalUnrewardedDetailEntity;
import com.weibao.chaopei.entity.WithdrawOrderEntity;
import com.weibao.chaopei.service.PersonalAcctServiceI;

/**
 * 个人账户管理
 * @author dms
 *
 */
@Controller
@RequestMapping("/personalAcctController")
public class PersonalAcctController extends BaseController {
	private static final Logger logger = Logger.getLogger(PersonalAcctController.class);
	
	@Resource
	private ClientManager clientManager;
	@Autowired
	private SystemService systemService;
	@Autowired
	private PersonalAcctServiceI personalAcctService;
	
	@RequestMapping(params = "main")
	public ModelAndView main(HttpServletRequest request) {		
		return new ModelAndView("com/weibao/chaopei/personaacct/personalAcctMain");
	}
	
	@RequestMapping(params = "base")
	public ModelAndView base(HttpServletRequest request) {
		//查询个人账户基本信息
		PersonalAccountEntity personalAcct = getPersonalAccount();
		request.setAttribute("personalAcct", personalAcct);
		return new ModelAndView("com/weibao/chaopei/personaacct/personalAcctBaseInfo");
	}
	
	@RequestMapping(params = "acctReceiveBalanceDetail")
	public ModelAndView acctReceiveBalanceDetail(String personalAccountId, HttpServletRequest request) {
		request.setAttribute("personalAccountId", personalAccountId);
		return new ModelAndView("com/weibao/chaopei/personaacct/acctReceiveBalanceDetails");
	}
	
	@RequestMapping(params = "acctUnreceiveBalanceDetail")
	public ModelAndView acctUnreceiveBalanceDetail(String personalAccountId, HttpServletRequest request) {
		request.setAttribute("personalAccountId", personalAccountId);
		return new ModelAndView("com/weibao/chaopei/personaacct/acctUnreceiveBalanceDetails");
	}
	
	@RequestMapping(params = "withdrawOrderList")
	public ModelAndView withdrawOrderList(String accountId, HttpServletRequest request) {
		request.setAttribute("accountId", accountId);
		return new ModelAndView("com/weibao/chaopei/personaacct/withdrawOrderList");
	}
	
	@RequestMapping(params = "withdrawOrderListBase")
	public ModelAndView withdrawOrderListBase(String accountId, HttpServletRequest request) {
		request.setAttribute("accountId", accountId);
		return new ModelAndView("com/weibao/chaopei/personaacct/withdrawOrderListBase");
	}
	
	@RequestMapping(params = "withdrawOrderDetails")
	public ModelAndView withdrawOrderDetails(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/personaacct/withdrawOrderDetails");
	}
	
	@RequestMapping(params = "goBindAccount")
	public ModelAndView goBindAccount(HttpServletRequest request) {
		//查询公司账户基本信息
		PersonalAccountEntity personalAcct = getPersonalAccount();
		request.setAttribute("personalAcct", personalAcct);
		
		return new ModelAndView("com/weibao/chaopei/personaacct/personal-bind");
	}
	
	@RequestMapping(params = "doBindAccount")
	@ResponseBody
	public AjaxJson doBindAccount(PersonalAccountEntity persAcct, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "绑定成功";
		try{			
			//密码加密
			PersonalAccountEntity existsAcct = personalAcctService.getEntity(PersonalAccountEntity.class, persAcct.getId());
			existsAcct.setBankAcctName(persAcct.getRealName());
			existsAcct.setBankInfo(persAcct.getBankInfo());
			existsAcct.setBankNo(persAcct.getBankNo());
			existsAcct.setCertiNo(persAcct.getCertiNo());
			existsAcct.setRealName(persAcct.getRealName());
			
			String password = oConvertUtils.getString(request.getParameter("withdrawPasswd"));
			
			existsAcct.setWithdrawPasswd(PasswordUtil.encrypt(persAcct.getRealName(), password, PasswordUtil.getStaticSalt()));
			personalAcctService.saveOrUpdate(existsAcct);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "绑定失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
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
	public void acctReceiveDetailDatagrid(PersonalRewardedDetailEntity perRewardDetailEntity,
			HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

		try{
			//获取当前用户的所属机构
			String userId = getCurrentUserId();
			perRewardDetailEntity.setUserId(userId);

			//PersonalAccountEntity personalAcct = getPersonalAccount();
			//perRewardDetailEntity.setPersonalAccountId(personalAcct.getId());
			//查询条件组装器
			if(StringUtil.isNotEmpty(perRewardDetailEntity.getPersonalAccountId())){		
				personalAcctService.getReceiveDetailList(perRewardDetailEntity, dataGrid);
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
	public void acctUnreceiveDetailDatagrid(PersonalUnrewardedDetailEntity perUnrewardDetailEntity,
			HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

		try{
			//获取当前用户的所属机构
			String userId = getCurrentUserId();
			perUnrewardDetailEntity.setUserId(userId);

			//PersonalAccountEntity personalAcct = getPersonalAccount();
			//perUnrewardDetailEntity.setPersonalAccountId(personalAcct.getId());
			
			//组装查询条件
			if(StringUtil.isNotEmpty(perUnrewardDetailEntity.getPersonalAccountId())){		
				personalAcctService.getUnreceiveDetailList(perUnrewardDetailEntity, dataGrid);
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
			personalAcctService.getWithdrawOrderList(withdrawOrderEntity, dataGrid);
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
				personalAcctService.getWithdrawDetailList(orderId, dataGrid);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 根据用户id获取个人账户信息
	 * @return
	 */
	protected PersonalAccountEntity getPersonalAccount() {
		PersonalAccountEntity personalAcct = null;
		try {
			String userId = getCurrentUserId();
			List<PersonalAccountEntity> acctList = systemService.findByProperty(PersonalAccountEntity.class, "userId", userId);
			if(acctList == null || acctList.isEmpty()) {
				personalAcct = new PersonalAccountEntity();
			} else {
				personalAcct = acctList.get(0);
			}
		} catch(Exception e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}
		return personalAcct;
	}
	
	/**
	 * 获取当前用户的用户id
	 * @return
	 */
	private String getCurrentUserId() {
		String userId = "";
		try {
			//获取当前用户的用户id
			TSUser user = clientManager.getClient().getUser();
			userId = user.getId();
		} catch(Exception e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}
		return userId;
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
			personalAcctService.withdrawPerson(params);
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
