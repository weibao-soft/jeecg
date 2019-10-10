package com.weibao.chaopei.controller;

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
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.weibao.chaopei.entity.ProductDetailEntity;
import com.weibao.chaopei.entity.ProductEntity;
import com.weibao.chaopei.page.ProductMainPage;
import com.weibao.chaopei.service.ProductServiceI;

@Controller
@RequestMapping("/productMainController")
public class ProductMainController extends BaseController {
	
	@Autowired
	private ProductServiceI  productService;
	@Autowired
	private SystemService systemService;
	
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
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, productDetailEntity);
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.productService.getDataGridReturn(cq, true);
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
	    String hql0 = "from ProductDetailEntity where 1 = 1 AND productId = ? ";
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
			message = "订单主信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
