package com.weibao.chaopei.controller;

import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.entity.WBPolicyChange;
import com.weibao.chaopei.entity.WBPolicyChangeConfirm;
import com.weibao.chaopei.entity.WBPolicyChangeContent;
import com.weibao.chaopei.service.PolicyChangeServiceI;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.superquery.util.SuperQueryUtil;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/policyChangeController")
public class PolicyChangeController extends BaseController {
	private static final Logger logger = Logger.getLogger(PolicyChangeController.class);

	private static final String ISO8859 = "ISO8859-1";

	private static final String UTF8 = "UTF-8";

	@Resource
	private ClientManager clientManager;

	@Autowired
	private PolicyChangeServiceI policyChangeService;

	@Autowired
	private SystemService systemService;

	/**
	 * 变更管理页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "manager")
	public ModelAndView manager(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/policy/policyChangeManager");
	}

	/**
	 * 变更信息列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/policy/policyChangeList");
	}

	/**
	 * 变更内容 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "contentList")
	public ModelAndView contentList(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/policy/policyChangeContent");
	}

	/**
	 * 变更回复 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "confirmList")
	public ModelAndView confirmList(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/policy/policyChangeConfirm");
	}

	/**
	 * 变更回复内容 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "addConfirm")
	public ModelAndView addConfirm(HttpServletRequest request) {
		String policyChangeId = oConvertUtils.getString(request.getParameter("policyChangeId"));// 变更ID
		request.setAttribute("policyChangeId", policyChangeId);
		return new ModelAndView("com/weibao/chaopei/policy/policyChangeConfirmAdd2");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(WBPolicyChange wbPolicyChange, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WBPolicyChange.class, dataGrid);
		String policyNo = request.getParameter("policyNo");

		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wbPolicyChange);
		try{
			//自定义追加查询条件
			String sql = SuperQueryUtil.getComplxSuperQuerySQL(request);
			if(oConvertUtils.isNotEmpty(sql)) {
				cq.add(Restrictions.sqlRestriction(" id in ("+sql+")"));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.policyChangeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = "policyChangeContent")
	@ResponseBody
	public AjaxJson policyChangeContent(WBPolicyChangeContent policyChangeContent, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String policyChangeId = policyChangeContent.getPolicyChangeId();
		List<WBPolicyChangeContent> list = this.systemService.findByProperty(WBPolicyChangeContent.class, "policyChangeId", policyChangeId);
		j.setMsg("OK");
		j.setObj(list);
		return j;
	}

	@RequestMapping(params = "policyChangeConfirm")
	@ResponseBody
	public AjaxJson policyChangeConfirm(WBPolicyChangeConfirm policyChangeConfirm, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String policyChangeId = policyChangeConfirm.getPolicyChangeId();
		List<WBPolicyChangeConfirm> list = this.systemService.findByProperty(WBPolicyChangeConfirm.class, "policyChangeId", policyChangeId);
		j.setMsg("OK");
		j.setObj(list);
		return j;
	}

	/**
	 * 保存文件
	 *
	 * @param confirm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "savePolicyChangeConfirm", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson savePolicyChangeConfirm(HttpServletRequest request, HttpServletResponse response, WBPolicyChangeConfirm confirm) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		String policyChangeId = oConvertUtils.getString(request.getParameter("policyChangeId"));// 变更ID
		confirm.setPolicyChangeId(policyChangeId);
		confirm.setStatus(WBPolicyChangeConfirm.STATUS_VALID);
		confirm.setCreateDate(DateUtils.gettimestamp());
		confirm.setCreatedate(DateUtils.gettimestamp());
		confirm.setModifyDate(DateUtils.gettimestamp());
		confirm.setSubclassname(MyClassLoader.getPackPath(confirm));
		UploadFile uploadFile = new UploadFile(request, confirm);
		uploadFile.setCusPath("files");
		//设置weboffice转化【不设置该字段，则不做在线预览转化】
		uploadFile.setSwfpath("swfpath");
		confirm = systemService.uploadFile(uploadFile);
		attributes.put("policyChangeId", policyChangeId);
		attributes.put("url", confirm.getRealpath());
		attributes.put("fileKey", confirm.getId());
		attributes.put("name", confirm.getAttachmenttitle());
		attributes.put("viewhref", "commonController.do?objfileList&fileKey=" + confirm.getId());
		attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + confirm.getId());
		j.setMsg("文件添加成功");
		j.setAttributes(attributes);
		return j;
	}
}
