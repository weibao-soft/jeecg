package com.weibao.chaopei.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.weibao.chaopei.dao.ProductDetailDao;
import com.weibao.chaopei.entity.DepartProductRefEntity;
import com.weibao.chaopei.entity.ProductDetailEntity;
import com.weibao.chaopei.entity.ProductEntity;
import com.weibao.chaopei.page.ProductAssignRef;
import com.weibao.chaopei.page.ProductMainPage;
import com.weibao.chaopei.service.ProductServiceI;

@Controller
@RequestMapping("/productMainController")
public class ProductMainController extends BaseController {
	
	@Autowired
	private ProductServiceI  productService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ProductDetailDao productDetailDao;
	
	private static final Logger logger = Logger.getLogger(ProductMainController.class);
	
	/**
	 * 我的产品主 页面跳转 调试
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/product/productMainList");
	}
	
	/**
	 * 我的产品信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "mainlist")
	public ModelAndView mainlist(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/product/productMainListBase");
	}
	
	/**
	 * 产品的方案信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "productDetailListBase")
	public ModelAndView productDetailListBase(HttpServletRequest request) {
		return new ModelAndView("com/weibao/chaopei/product/productDetailListBase");
	}
	
	/**
	 *产品单主信息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ProductEntity productEntity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(productEntity.getId())) {
			productEntity = productService.getEntity(ProductEntity.class, productEntity.getId());
			req.setAttribute("productEntityPage", productEntity);
		}
		return new ModelAndView("com/weibao/chaopei/product/productMain-add");
	}
	
	/**
	 * 产品主信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ProductEntity productEntity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(productEntity.getId())) {
			productEntity = productService.getEntity(ProductEntity.class, productEntity.getId());
			req.setAttribute("productEntityPage", productEntity);
		}
		return new ModelAndView("com/weibao/chaopei/product/productMain-update");
	}
	
	
	/**
	 *跳转到分配产品的页面
	 * @param departid 被点选的机构
	 * @return
	 */
	@RequestMapping(params = "goAssignProd")
	public ModelAndView goAssignProd(String departid, HttpServletRequest req) {
		//	先查询分配给上级单位(如果上级是null，则查看所有)所有的产品，然后再查询该组织已经分配的产品，然后已经分配的产品勾选上		
		TSDepart depart = systemService.get(TSDepart.class, departid);
		TSDepart parent = depart.getTSPDepart();
		List<ProductAssignRef> refList = null;
		//	如果点选的机构是一级机构
		if(parent == null) {
			//	查询所有产品			
			refList = productDetailDao.getProductAssignRefByRoot(departid);			
		}else {
			//	如果点选的机构是以及以下的其他机构
			refList = productDetailDao.getProductAssignRefBySubRoot(parent.getId(), departid);
		}
		
		req.setAttribute("refList", refList);
		req.setAttribute("departid", departid);//	被点选的机构再传到产品分配页面
		return new ModelAndView("com/weibao/chaopei/product/departProduct-assign");
	}
	
	/**
	 * 	修改分配产品的action
	 * @param productEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "udpateAssignProd")
	public ModelAndView udpateAssignProd(ProductAssignRef productAssignRef,HttpServletRequest request, HttpServletResponse response) {		
		List<String> refs = productAssignRef.getCheckedProdctAssign();
		List<DepartProductRefEntity> entityList = new ArrayList<DepartProductRefEntity>();
		List<String> removeIds = new ArrayList<String>();
		for (String ref : refs) {
			String[] idProductPlan = ref.split(",");
			DepartProductRefEntity entity = new DepartProductRefEntity();
			String id = idProductPlan[0];
			if(StringUtil.isNotEmpty(id)) {
				entity.setId(id);
				removeIds.add(id);
			}
			entity.setAssignStatus("1");
			entity.setDepartId(productAssignRef.getDepartid());
			entity.setProductDetailId(idProductPlan[1]);
			entityList.add(entity);					
		}
		productService.udpateAssignProd(productAssignRef.getDepartid(), entityList, removeIds);
		//	传进来的都是被选中的产品方案，	
		return goAssignProd(productAssignRef.getDepartid(), request);
	}
	
	/**
	 * easyui AJAX请求数据测试修改。。
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(ProductEntity productEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ProductEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, productEntity);
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.productService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	@RequestMapping(params = "productDetailDatagrid")
	public void productDetailDatagrid(ProductDetailEntity productDetailEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ProductDetailEntity.class, dataGrid);
		
		if(productDetailEntity.getProdId() == null || "".equals(productDetailEntity.getProdId())){		
			//	查询条件组装器
		}else{
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, productDetailEntity);
			try{
				//	自定义追加查询条件
				cq.add();
				this.productService.getDataGridReturn(cq, true);
			}catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}			
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 加载产品方案明细列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "productDetailList")
	public ModelAndView productDetailList(ProductEntity productEntity, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = productEntity.getId();
		//===================================================================================
		//查询-JformOrderMain子表
	    String hql0 = "from ProductDetailEntity where 1 = 1 AND prodId = ? ";
	    try{
	    	List<ProductDetailEntity> productDetailEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("productDetailEntityList", productDetailEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/weibao/chaopei/product/productDetailList");
	}
	
	/**
	 * 添加产品主信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ProductEntity productEntity,ProductMainPage productMainPage, HttpServletRequest request) {
		List<ProductDetailEntity> productDetailsList =  productMainPage.getProductDetailsList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			productService.addMain(productEntity, productDetailsList);
			systemService.addLog(message+":", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "产品主信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新产品主信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ProductEntity productEntity,ProductMainPage productMainPage, HttpServletRequest request) {
		List<ProductDetailEntity> productDetailsList =  productMainPage.getProductDetailsList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			productService.updateMain(productEntity, productDetailsList);
			systemService.addLog(message+":", Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "产品主信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
