package com.weibao.chaopei.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.weibao.chaopei.page.PolicyMainPage;
import com.weibao.chaopei.service.PolicyServiceI;

@Controller
@RequestMapping("/policyMainController")
public class PolicyMainController extends BaseController {
	private static final Logger logger = Logger.getLogger(PolicyMainController.class);
	
	@Autowired
	private PolicyServiceI  policyService;
	@Autowired
	private SystemService systemService;

	/**
	 * 保单主信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/policy/policyMainList");
	}

	/**
	 * 保单主信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/policy/policyMainAdd");
	}
	
	/**
	 * 专票信息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(PolicyMainPage policyMainPage, HttpServletRequest req) {
		try {
			req.setCharacterEncoding("utf-8");
			String compName2 = policyMainPage.getCompName2();
			String compAddress = policyMainPage.getCompAddress();
			String depositBank = policyMainPage.getDepositBank();
			String recipients = policyMainPage.getRecipients();
			String reciAddress = policyMainPage.getReciAddress();
			compName2 = new String(compName2.getBytes("ISO8859-1"), "utf-8");
			compAddress = new String(compAddress.getBytes("ISO8859-1"), "utf-8");
			depositBank = new String(depositBank.getBytes("ISO8859-1"), "utf-8");
			recipients = new String(recipients.getBytes("ISO8859-1"), "utf-8");
			reciAddress = new String(reciAddress.getBytes("ISO8859-1"), "utf-8");
			policyMainPage.setCompName2(compName2);
			policyMainPage.setCompAddress(compAddress);
			policyMainPage.setDepositBank(depositBank);
			policyMainPage.setRecipients(recipients);
			policyMainPage.setReciAddress(reciAddress);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		if (StringUtil.isNotEmpty(policyMainPage.getId())) {
			//holderEntity = policyService.getEntity(HolderEntity.class, holderEntity.getId());
		}
		req.setAttribute("policyMainPage", policyMainPage);
		return new ModelAndView("com/weibao/chaopei/policy/speInvoiceAdd");
	}
	
	/**
	 * 添加保单主信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(PolicyMainPage policyMainPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			policyService.addMain(policyMainPage);
			systemService.addLog(message+":", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
			message = "保单主信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
