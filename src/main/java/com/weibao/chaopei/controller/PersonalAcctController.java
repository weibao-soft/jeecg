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
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.weibao.chaopei.entity.PersonalAccountEntity;
import com.weibao.chaopei.entity.PersonalRewardedDetailEntity;
import com.weibao.chaopei.entity.PersonalUnrewardedDetailEntity;
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
}
