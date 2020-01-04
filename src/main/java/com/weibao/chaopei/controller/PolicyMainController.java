package com.weibao.chaopei.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ResourceUtil;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.weibao.chaopei.entity.HolderEntity;
import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.entity.ReceiverEntity;
import com.weibao.chaopei.entity.WBPolicyChange;
import com.weibao.chaopei.entity.WBPolicyChangeContent;
import com.weibao.chaopei.page.CommonBean;
import com.weibao.chaopei.page.InvoiceExportPage;
import com.weibao.chaopei.page.PolicyMainPage;
import com.weibao.chaopei.service.PolicyServiceI;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/policyMainController")
public class PolicyMainController extends BaseController {
	private static final Logger logger = Logger.getLogger(PolicyMainController.class);

	private static final String ISO8859 = "ISO8859-1";

	private static final String UTF8 = "UTF-8";

	@Resource
	private ClientManager clientManager;

	@Autowired
	private PolicyServiceI policyService;

	@Autowired
	private SystemService systemService;

	/**
	 * 我的保单信息列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/policy/policyMainListBase");
	}

	/**
	 * 保单主信息编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(String policyid, HttpServletRequest request) {
		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
		PolicyMainPage policyMainPage = null;
		try {
			request.setCharacterEncoding(UTF8);
			//setCharacterEncoding(policyMainPage);
			policyMainPage = policyService.getOnePolicyMainPage(policyid);
			policyMainPage.setId(policyid);
			Calendar canlendar = Calendar.getInstance();
			Date startDate = policyMainPage.getStartDate();
			Date endDate = policyMainPage.getEndDate();
			canlendar.setTime(startDate);
			canlendar.add(Calendar.YEAR, 1);
			canlendar.add(Calendar.DATE, -1);
			Date maxDate = canlendar.getTime();
			
			int year = 1;
			int days = 1;
			Calendar calSta = Calendar.getInstance();
			calSta.setTime(startDate);
			Calendar canEnd = Calendar.getInstance();
			canEnd.setTime(endDate);
			days = DateUtils.dateDiff('d', canEnd, calSta);
			year = (days / 365 == 0) ? 1 : days / 365;
			
			String start = sdfd.format(startDate);
			String end = sdfd.format(endDate);
			String max = sdfd.format(maxDate);
			request.setAttribute("start", start);
			request.setAttribute("end", end);
			request.setAttribute("max", max);
			request.setAttribute("year", year);
			request.setAttribute("isDraft", false);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		request.setAttribute("policyMainPage", policyMainPage);
		return new ModelAndView("com/weibao/chaopei/policy/policyMainUpdateOne");
	}

	/**
	 * 保单主信息编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goChild")
	public ModelAndView goChild(String payUrl, HttpServletRequest request) {
		request.setAttribute("payUrl", payUrl);
		return new ModelAndView("com/weibao/chaopei/policy/policyPayChild");
	}

	/**
	 * 专票信息新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "addSpe")
	public ModelAndView addSpe(PolicyMainPage policyMainPage, HttpServletRequest req) {
		try {
			req.setCharacterEncoding(UTF8);
			//setCharacterEncoding(policyMainPage);
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
	 * 专票信息修改页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "editSpe")
	public ModelAndView editSpe(String policyId, HttpServletRequest req) {
		PolicyMainPage policyMainPage = new PolicyMainPage();
		try {
			req.setCharacterEncoding(UTF8);
			if (StringUtil.isNotEmpty(policyId)) {
				PolicyEntity policyEntity = policyService.getEntity(PolicyEntity.class, policyId);
				BeanUtils.copyProperties(policyMainPage, policyEntity);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		req.setAttribute("policyMainPage", policyMainPage);
		return new ModelAndView("com/weibao/chaopei/policy/speInvoiceEdit");
	}

	/**
	 * 从页面上传来的中文参数，由"ISO8859-1"编码转换为"UTF-8"
	 * @param policyMainPage
	 */
	protected void setCharacterEncoding(PolicyMainPage policyMainPage) {
		try {
			String compName = policyMainPage.getCompName();
			String compAddress = policyMainPage.getCompAddress();
			String depositBank = policyMainPage.getDepositBank();
			String recipients = policyMainPage.getRecipients();
			String recipientsTel = policyMainPage.getRecipientsTel();
			String reciAddress = policyMainPage.getReciAddress();
			compName = (compName != null) ? new String(compName.getBytes(ISO8859), UTF8) : compName;
			compAddress = (compAddress != null) ? new String(compAddress.getBytes(ISO8859), UTF8) : compAddress;
			depositBank = (depositBank != null) ? new String(depositBank.getBytes(ISO8859), UTF8) : depositBank;
			recipients = (recipients != null) ? new String(recipients.getBytes(ISO8859), UTF8) : recipients;
			recipientsTel = (recipientsTel != null) ? new String(recipientsTel.getBytes(ISO8859), UTF8) : recipientsTel;
			reciAddress = (reciAddress != null) ? new String(reciAddress.getBytes(ISO8859), UTF8) : reciAddress;
			policyMainPage.setCompName(compName);
			policyMainPage.setCompAddress(compAddress);
			policyMainPage.setDepositBank(depositBank);
			policyMainPage.setRecipients(recipients);
			policyMainPage.setRecipientsTel(recipientsTel);
			policyMainPage.setReciAddress(reciAddress);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
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

		try{
			String userId = ResourceUtil.getSessionUser().getId();
			policy.setUserId(userId);
			//组装查询条件
			policyService.getPolicyList(policy, dataGrid);
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
	 * 修改发票信息
	 * @param invoiceExportPage
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateInvoice")
	@ResponseBody
	public AjaxJson updateInvoice(InvoiceExportPage invoiceExportPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "修改成功";
		try {
			int updCnt = policyService.updatePolicyInvoice(invoiceExportPage);
			if(updCnt < 1) {
				j.setSuccess(false);
				message = "发票信息修改失败";
			}
			systemService.addLog(message+":", Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch(HibernateException e) {
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "发票信息修改失败";
			throw new BusinessException("发票信息修改失败，原因：" + e.getMessage());
		} catch(Exception e) {
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "发票信息修改失败";
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 删除未支付状态的保单，已支付状态的保单不能删除
	 *
	 * @param policyId
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(String policyId, String payStatus, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "删除成功";
		try {
			if(!"0".equals(payStatus)) {
				j.setSuccess(false);
				message = "不能删除已支付的保单";
				j.setMsg(message);
				return j;
			}
			policyService.delMain(policyId);
			systemService.addLog(message+":", Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		} catch(HibernateException e) {
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单删除失败";
			throw new BusinessException("保单删除失败，原因：" + e.getMessage());
		} catch(Exception e) {
			logger.info(e.getMessage(), e);
			j.setSuccess(false);
			message = "保单删除失败";
		}
		j.setMsg(message);
		return j;
	}

	/**
	 *  查询保单投保人列表
	 *
	 * @return
	 */
	@RequestMapping(params = "getHolders")
	@ResponseBody
	public JSONObject getHolders(HttpServletRequest request) {
		JSONObject object = new JSONObject();
		List<CommonBean> holders = new ArrayList<CommonBean>();
		String message = "查询成功";
		try{
			holders = policyService.getPolicyHolders();
			net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(holders);
			//String value = JSONArray.toJSONString(array);
			object.put("value", array);
			object.put("code", 200);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			object.put("code", 201);
			message = "保单投保人查询失败";
			throw new BusinessException(e.getMessage());
		}
		object.put("message", message);
		return object;
	}

	/**
	 *  查询保单被保人列表
	 *
	 * @return
	 */
	@RequestMapping(params = "getInsureds")
	@ResponseBody
	public JSONObject getInsureds(HttpServletRequest request) {
		JSONObject object = new JSONObject();
		List<CommonBean> holders = new ArrayList<CommonBean>();
		String message = "查询成功";
		try{
			holders = policyService.getPolicyInsureds();
			net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(holders);
			//String value = JSONArray.toJSONString(array);
			object.put("value", array);
			object.put("code", 200);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			object.put("code", 201);
			message = "保单投保人查询失败";
			throw new BusinessException(e.getMessage());
		}
		object.put("message", message);
		return object;
	}

	/**
	 *  查询保单收件人列表
	 *
	 * @return
	 */
	@RequestMapping(params = "getReceivers")
	@ResponseBody
	public JSONObject getReceivers(HttpServletRequest request) {
		JSONObject object = new JSONObject();
		List<ReceiverEntity> holders = new ArrayList<ReceiverEntity>();
		String message = "查询成功";
		try{
			String userId = ResourceUtil.getSessionUser().getId();
			holders = policyService.getPolicyReceivers(userId);
			net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(holders);
			//String value = JSONArray.toJSONString(array);
			object.put("value", array);
			object.put("code", 200);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			object.put("code", 201);
			message = "保单收件人查询失败";
			throw new BusinessException(e.getMessage());
		}
		object.put("message", message);
		return object;
	}

	/**
	 *  查询保单投保人
	 *
	 * @return
	 */
	@RequestMapping(params = "getHolderById")
	@ResponseBody
	public JSONObject getHolderById(String id, HttpServletRequest request) {
		JSONObject object = new JSONObject();
		HolderEntity holders = null;
		String message = "查询成功";
		try{
			holders = policyService.getHolderById(id);
			JSONObject obj = JSONObject.fromObject(holders);
			//String value = obj.toString();
			object.put("value", obj);
			object.put("code", 200);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			object.put("code", 201);
			message = "保单投保人查询失败";
			throw new BusinessException(e.getMessage());
		}
		object.put("message", message);
		return object;
	}

	/**
	 *  查询可用的投保方案
	 *
	 * @return
	 */
	@RequestMapping(params = "getProductPlan")
	@ResponseBody
	public JSONObject getProductPlan(String paramId, HttpServletRequest request) {
		JSONObject object = new JSONObject();
		List<CommonBean> holders = null;
		String message = "查询成功";
		try{
			//获取当前用户的所属机构
			TSUser user = clientManager.getClient().getUser();
			TSDepart currentDepart = user.getCurrentDepart();
			holders = policyService.getProductPlan(currentDepart.getId(), paramId);
			net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(holders);
			object.put("value", array);
			object.put("code", 200);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			object.put("code", 201);
			message = "产品方案查询失败";
			throw new BusinessException(e.getMessage());
		}
		object.put("message", message);
		return object;
	}

	/**
	 * 文件添加跳转
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addPolicyChange")
	public ModelAndView addFiles(HttpServletRequest request) {
		String insurancePolicyId = oConvertUtils.getString(request.getParameter("insurancePolicyId"));// 保单ID
		request.setAttribute("insurancePolicyId", insurancePolicyId);
		return new ModelAndView("com/weibao/chaopei/policy/policyChangeAdd4");
	}

	/**
	 * 保存文件
	 *
	 * @param content
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "savePolicyChangeContent", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson savePolicyChangeContent(HttpServletRequest request, HttpServletResponse response, WBPolicyChangeContent content) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		String insurancePolicyId = oConvertUtils.getString(request.getParameter("insurancePolicyId"));// 保单ID
		String batchNum = oConvertUtils.getString(request.getParameter("batchNum"));// 变更ID
		String desc = oConvertUtils.getString(request.getParameter("desc"));// 变更说明
		WBPolicyChange changeQuery = new WBPolicyChange();
		changeQuery.setBatchNum(batchNum);
		changeQuery.setPolicyEntity(new PolicyEntity(insurancePolicyId));
		List<WBPolicyChange> exitChange = systemService.findByExample("com.weibao.chaopei.entity.WBPolicyChange", changeQuery);
		String policyChangeId;
		if (exitChange.isEmpty()) {
			WBPolicyChange policyChange = new WBPolicyChange();
			policyChange.setBatchNum(batchNum);
			policyChange.setPolicyEntity(new PolicyEntity(insurancePolicyId));
			policyChange.setStatus(WBPolicyChange.AGENT_SUBMITTED);
			policyChange.setDescription(desc);
			policyChange.setCreateDate(DateUtils.gettimestamp());
			policyChange.setModifyDate(DateUtils.gettimestamp());
			systemService.save(policyChange);
			policyChangeId = policyChange.getId();
		} else {
			policyChangeId = exitChange.get(0).getId();
		}
		content.setPolicyChangeId(policyChangeId);
		content.setStatus(WBPolicyChangeContent.STATUS_VALID);
		content.setCreatedate(DateUtils.gettimestamp());
		content.setCreateDate(DateUtils.gettimestamp());
		content.setModifyDate(DateUtils.gettimestamp());
		content.setSubclassname(MyClassLoader.getPackPath(content));
		UploadFile uploadFile = new UploadFile(request, content);
		uploadFile.setCusPath("files");
		//设置weboffice转化【不设置该字段，则不做在线预览转化】
		uploadFile.setSwfpath("swfpath");
		content = systemService.uploadFile(uploadFile);
		attributes.put("policyChangeId", policyChangeId);
		attributes.put("url", content.getRealpath());
		attributes.put("fileKey", content.getId());
		attributes.put("name", content.getAttachmenttitle());
		attributes.put("viewhref", "commonController.do?objfileList&fileKey=" + content.getId());
		attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + content.getId());
		j.setMsg("文件添加成功");
		j.setAttributes(attributes);
		return j;
	}

	/**
	 * 修改变更说明
	 * @author lilele
	 * @since 2019年12月13日
	 */
	@RequestMapping(params = "updatePolicyChange")
	@ResponseBody
	public AjaxJson updatePolicyChange(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String insurancePolicyId = oConvertUtils.getString(request.getParameter("insurancePolicyId"));// 保单ID
			String batchNum = oConvertUtils.getString(request.getParameter("batchNum"));// 变更ID
			String desc = oConvertUtils.getString(request.getParameter("desc"));// 变更说明
			WBPolicyChange changeQuery = new WBPolicyChange();
			changeQuery.setBatchNum(batchNum);
			changeQuery.setPolicyEntity(new PolicyEntity(insurancePolicyId));
			List<WBPolicyChange> exitChange = systemService.findByExample("com.weibao.chaopei.entity.WBPolicyChange", changeQuery);
			WBPolicyChange policyChange = exitChange.get(0);
			policyChange.setDescription(desc);
			systemService.updateEntitie(policyChange);
			j.setSuccess(true);
			j.setMsg("变更说明修改成功!");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("变更说明修改失败!");
		}

		return j;
	}

	/**
	 * 删除变更图片
	 * @author lilele
	 * @since 2019年12月13日
	 */
	@RequestMapping(params = "deletePolicyChangeContent")
	@ResponseBody
	public AjaxJson deletePolicyChangeContent(WBPolicyChangeContent content, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		content = systemService.getEntity(WBPolicyChangeContent.class, content.getId());
		message = "" + content.getAttachmenttitle() + "被删除成功";
		systemService.delete(content);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}
}
