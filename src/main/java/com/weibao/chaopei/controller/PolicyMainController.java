package com.weibao.chaopei.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
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
	
	private static final String ISO8859 = "ISO8859-1";
	
	private static final String UTF8 = "UTF-8";
	
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
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
		Calendar canlendar = Calendar.getInstance();
		canlendar.add(Calendar.DATE, 1);
		Date startDate = canlendar.getTime();
		
		canlendar.add(Calendar.YEAR, 1);
		canlendar.add(Calendar.DATE, -1);
		Date endDate = canlendar.getTime();
		
		canlendar.add(Calendar.YEAR, -1);
		canlendar.add(Calendar.DATE, 365);
		Date maxDate = canlendar.getTime();

        String start = sdfd.format(startDate);
        String end = sdfd.format(endDate);
        String max = sdfd.format(maxDate);
		request.setAttribute("start", start);
		request.setAttribute("end", end);
		request.setAttribute("max", max);
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
			req.setCharacterEncoding(UTF8);
			setCharacterEncoding(policyMainPage);
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
	 * 从页面上传来的中文参数，由"ISO8859-1"编码转换为"UTF-8"
	 * @param policyMainPage
	 */
	private void setCharacterEncoding(PolicyMainPage policyMainPage) {
		try {
			String compName2 = policyMainPage.getCompName2();
			String compAddress = policyMainPage.getCompAddress();
			String depositBank = policyMainPage.getDepositBank();
			String recipients = policyMainPage.getRecipients();
			String reciAddress = policyMainPage.getReciAddress();
			compName2 = (compName2 != null) ? new String(compName2.getBytes(ISO8859), UTF8) : compName2;
			compAddress = (compAddress != null) ? new String(compAddress.getBytes(ISO8859), UTF8) : compAddress;
			depositBank = (depositBank != null) ? new String(depositBank.getBytes(ISO8859), UTF8) : depositBank;
			recipients = (recipients != null) ? new String(recipients.getBytes(ISO8859), UTF8) : recipients;
			reciAddress = (reciAddress != null) ? new String(reciAddress.getBytes(ISO8859), UTF8) : reciAddress;
			policyMainPage.setCompName2(compName2);
			policyMainPage.setCompAddress(compAddress);
			policyMainPage.setDepositBank(depositBank);
			policyMainPage.setRecipients(recipients);
			policyMainPage.setReciAddress(reciAddress);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
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
			//String userId = request.getParameter("userId");
			String userId = ResourceUtil.getSessionUser().getId();
			policyMainPage.setUserId(userId);
			policyService.addMain(policyMainPage);
			systemService.addLog(message+":", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单主信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(policyMainPage);
		return j;
	}
}
