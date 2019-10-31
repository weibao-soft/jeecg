package com.weibao.chaopei.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weibao.chaopei.entity.CommissionConfEntity;
import com.weibao.chaopei.entity.DepartProductRefEntity;
import com.weibao.chaopei.entity.ProductDetailEntity;
import com.weibao.chaopei.entity.ProductEntity;

@Service("productService")
@Transactional
public class ProductServiceImpl extends CommonServiceImpl implements ProductServiceI {

	@Autowired
	private JdbcDao jdbcDao;
	
	@Override
	public void addMain(ProductEntity productEntity, List<ProductDetailEntity> productDetailsList) {
		//保存主产品信息
		this.save(productEntity);
		
		/**保存-产品方案信息*/
		for(ProductDetailEntity productDetail : productDetailsList){
			//外键设置
			productDetail.setProdId(productEntity.getId());
			this.save(productDetail);
		}				
	}

	@Override
	public void updateMain(ProductEntity productEntity, List<ProductDetailEntity> productDetailsList) {
		//保存主产品信息
		if(StringUtil.isNotEmpty(productEntity.getId())){
			try {
				ProductEntity temp = findUniqueByProperty(ProductEntity.class, "id", productEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(productEntity, temp);
				this.saveOrUpdate(temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			this.saveOrUpdate(productEntity);
		}
		//===================================================================================
		//获取参数
		Object id0 = productEntity.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-JformOrderMain子表
	    String hql0 = "from ProductDetailEntity where 1 = 1 AND prodId = ? ";
	    List<ProductDetailEntity> productDetailOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-JformOrderMain子表

	    //TODO author：XueLin  for: 客户数据全删完size == 0
		if(productDetailsList != null){// && jformOrderCustomerList.size() > 0 

			for(ProductDetailEntity oldE : productDetailOldList){
				boolean isUpdate = false;
				for(ProductDetailEntity sendE : productDetailsList){
					//需要更新的明细数据-JformOrderMain子表
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							this.saveOrUpdate(oldE);
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate = true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的明细，前台没有传递过来则是删除-JformOrderMain子表
		    		super.delete(oldE);
	    		}	    		
			}
			//3.持久化新增的数据-JformOrderMain子表
			for(ProductDetailEntity productDetail:productDetailsList){
				if(oConvertUtils.isEmpty(productDetail.getId())){
					//外键设置
					productDetail.setProdId(productEntity.getId());
					this.save(productDetail);
				}
			}
		}
	}

	@Override
	public void udpateAssignProd(String depart_id, List<DepartProductRefEntity> entityList, List<String> removeIds) {
		//	先修改所有未选中产品的状态为0
		if(removeIds !=null && removeIds.size() > 0) {
			String updateSql = "update wb_depart_product_rel set assign_status='0' "
					+ "where depart_id=:depart_id and product_detail_id not in(:removeIds)";
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("depart_id", depart_id);
			paramMap.put("removeIds", removeIds);
			jdbcDao.executeForMap(updateSql, paramMap);		
		}
		//	再保存选中的产品		
		this.batchSave(entityList);		
	}
	
	public void batchSaveOrUpdate(List<CommissionConfEntity> commConfList) {		
		for (CommissionConfEntity commissionConfEntity : commConfList) {
			super.saveOrUpdate(commissionConfEntity);			
		}		
	}

}
