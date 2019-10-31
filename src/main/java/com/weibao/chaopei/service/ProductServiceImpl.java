package com.weibao.chaopei.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
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
