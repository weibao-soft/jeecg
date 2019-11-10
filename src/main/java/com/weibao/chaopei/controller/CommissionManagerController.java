package com.weibao.chaopei.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.weibao.chaopei.dao.ProductDetailDao;
import com.weibao.chaopei.entity.CommissionConfEntity;
import com.weibao.chaopei.page.CommissionConfRef;
import com.weibao.chaopei.service.ProductServiceI;

@Controller
@RequestMapping("/commissionManager")
public class CommissionManagerController extends BaseController {
	
	@Autowired
	private ProductServiceI  productService;
	@Autowired
	private ProductDetailDao productDetailDao;
	@Resource
	private ClientManager clientManager;
	
	private static final Logger logger = Logger.getLogger(CommissionManagerController.class);
	
	/**
	 * 跳转主页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "orgList")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/commisionmanage/myorglist");
	}
	
	
	@RequestMapping(params="getMyTreeDataAsync",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<Map<String,Object>> getMyTreeDataAsync(HttpServletResponse response,HttpServletRequest request ){
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		try{
			String id = request.getParameter("id");
			String parentId = request.getParameter("parentId");
			String sql = "";
			//	没有选择某个机构，加载根节点
			if(oConvertUtils.isEmpty(id)){
				TSUser currentUser = ResourceUtil.getSessionUser();
				String userName = ResourceUtil.getSessionUser().getUserName();
				//如果是admin
				if("admin".equals(userName)) {
					//	加载根节点					
					sql = "select org.ID id, org.parentdepartid parent, org.departname orgname, 0 orgType from t_s_depart org where org.parentdepartid is null order by org.depart_order asc";
					List<Map<String, Object>> orgList = productService.findForJdbc(sql);			
					populateTree(orgList,dataList, parentId);
					return dataList;
				}else {
					//	不是admin，查询所属的机构作为根节点
					sql = "select org.ID id, org.parentdepartid parent, org.departname orgname, 0 orgType from t_s_depart org, t_s_user_org uo where org.ID=uo.org_id and uo.user_id=?";
					id = currentUser.getId();
					List<Map<String, Object>> orgList = productService.findForJdbc(sql, id);	
					populateTree(orgList,dataList, parentId);
				}
			}else {
				//	加载子节点
				sql = "select org.ID id, org.parentdepartid parent, org.departname orgname, 0 orgType from t_s_depart org where org.parentdepartid=? order by org.depart_order asc";
				List<Map<String, Object>> orgList = productService.findForJdbc(sql, id);		
				//	查询该节点下属的所有人员
				String subsql = "select us.ID id, uo.org_id parent, us.username orgname, 1 orgType from t_s_user_org uo, "
						+ "t_s_base_user us where uo.user_id=us.ID and uo.org_id = ? "
						+ "and us.status in ('"+Globals.User_Normal+"', '"+Globals.User_ADMIN+"')";
				
				List<Map<String, Object>> orgSubList = productService.findForJdbc(subsql, id);
				orgList.addAll(orgSubList);
				populateTree(orgList,dataList, parentId);
				return dataList;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataList;
	}
	
	private void populateTree(List<Map<String, Object>> orgList,List<Map<String,Object>> dataList, String parentId){
		Map<String,Object> map = null;
		if(orgList!=null&&orgList.size()>0){
			for(Map<String, Object> org :orgList){
				map = new HashMap<String,Object>();
//				map.put("chkDisabled",false);
//				map.put("click", true);
				map.put("open", false);
				map.put("id", org.get("id"));
				map.put("name", org.get("orgname"));
				map.put("orgType", org.get("orgType"));
//				map.put("nocheck", false);
//				map.put("struct","TREE");
//				map.put("title","01title");
//				map.put("level", "1");

				//	判断是否有子节点
				if(StringUtil.isEmpty(parentId)) {
					//admin才会进这里
					String sql = "select count(*) from t_s_depart where parentdepartid = ?";
					Long count = this.productService.getCountForJdbcParam(sql, org.get("id"));
					
					if(count>0){
						map.put("isParent", true);
					}else{						
						map.put("isParent", false);
					}
				}else {
					//非admin的情况下判断下级机构下是否有人员
					String sql = "select count(1) from t_s_user_org uo, "
							+ "t_s_base_user us where uo.user_id=us.ID and uo.org_id = ? "
							+ "and us.status in ('"+Globals.User_Normal+"', '"+Globals.User_ADMIN+"')";
					Long count = this.productService.getCountForJdbcParam(sql, org.get("id"));
					if(count>0){
						map.put("isParent", true);
					}else{						
						map.put("isParent", false);
					}					
				}
				
				if("0".equals(String.valueOf(org.get("orgType")))) {
					map.put("icon","plug-in/ztree/css/img/diy/company.png");
				}else if("1".equals(String.valueOf(org.get("orgType")))) {
					map.put("icon","plug-in/ztree/css/img/diy/depart.png");
				}
				
				if(null == org.get("parent")) {				
					map.put("parentId","0");// admin是没有parent的
				}else{
					map.put("parentId", org.get("parent"));
				}

				dataList.add(map);
			}
		}
	}
	
	/**
	 * 方法描述:  我的组织机构查看成员列表
	 * 返回类型： ModelAndView
	 * @param departid 用户操作时点选的机构或者人员id
	 * @param orgType 机构：0，人员：1
	 * @param parentid 点选机构的父id，用于判断点选机构在树结构是否根节点
	 */
	@RequestMapping(params = "departCommissionList")
	public ModelAndView departCommissionList(HttpServletRequest request, String departid, String orgType) {
		//	传进来的departid可能是机构代码，也可能是用户代码
		String departname = "";
		TSDepart parent = null;
		//	先查询分配给上级单位(如果上级是null，则查看所有)所有的产品，然后再查询该组织已经分配的产品，然后已经分配的产品勾选上				
		if("0".equals(orgType)) {
			//	选中机构时，先从数据库中查找被选机构，借此再查找父机构parent
			TSDepart depart = productService.get(TSDepart.class, departid);
			parent = depart.getTSPDepart();
			departname = depart.getDepartname();
		}else if("1".equals(orgType)) {
			//	选中人员时，用户的所属机构就是parent
			CriteriaQuery cq = new CriteriaQuery(TSUserOrg.class);
			cq.eq("tsUser.id", departid);
			cq.add();
			List<TSUserOrg> uoList = productService.getListByCriteriaQuery(cq, false);
			//TODO: 用户归属机构，作限制，只能归属一家机构
			parent = uoList.get(0).getTsDepart();
			departname = uoList.get(0).getTsUser().getRealName();
		}
		
		TSUser currentUser = ResourceUtil.getSessionUser();
		String userName = currentUser.getUserName();
		if("admin".equals(userName)) {
			request.setAttribute("isAdmin", true);
		}		
		//	点选的节点id与当前用户所属机构的id一致，说明是根节点
		if(departid.equals(currentUser.getCurrentDepart().getId())) {
			request.setAttribute("isRoot", true);
		}else{
			request.setAttribute("isRoot", false);
		}
		
		List<CommissionConfRef> refList = null;
		
		//	点选的是整个组织机构的根节点(微保科技)		
		if(parent == null) {
			//	查询所有产品			
			refList = productDetailDao.getCommissionConfByRoot(departid);			
			
		}else {
			//	如果点选的是下级机构或者机构下的人员
			if("0".equals(orgType)) {
				//	下级机构
				refList = productDetailDao.getCommissionConfBySubOrg(departid);				
			}else if("1".equals(orgType)) {
				//	人员
				refList = productDetailDao.getCommissionConfByUser(departid);
			}
		}
		while(parent != null) {
			departname = parent.getDepartname()+"->"+departname;
			parent = parent.getTSPDepart();
		}
		
		request.setAttribute("departid", departid);
		request.setAttribute("departname", departname);
		request.setAttribute("refList", refList);
		request.setAttribute("orgType", orgType);
		return new ModelAndView("com/weibao/chaopei/commisionmanage/commision-config");

	}
	
	/**
	 * 	修改佣金设置的action
	 * @param productEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "udpateCommConf")
	public ModelAndView udpateCommConf(CommissionConfRef commConfRef, HttpServletRequest request, HttpServletResponse response) {				
		String userName = ResourceUtil.getSessionUser().getUserName();
		List<CommissionConfEntity> commConfList = commConfRef.getCommisConfList();		
		Date now = new Date();
		for (CommissionConfEntity commConf : commConfList) {
			if(StringUtil.isEmpty(commConf.getId())){
				commConf.setId(null);
			}			
			commConf.setDepartUser(commConfRef.getDepartid());
			commConf.setLastUpdateBy(userName);
			commConf.setLastUpdateTime(now);
			commConf.setType(commConfRef.getOrgType());
		}
		productService.batchSaveOrUpdate(commConfList);		
		
		//	传进来的都是被选中的产品方案，	
		return new ModelAndView("com/weibao/chaopei/commisionmanage/commision-config");
	}
}
