package com.weibao.chaopei.service;

import java.io.Serializable;
import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weibao.chaopei.entity.ProductDetailEntity;
import com.weibao.chaopei.entity.ProductEntity;

@Service("productService")
@Transactional
public class ProductServiceImpl extends CommonServiceImpl implements ProductServiceI {

	@Override
	public void addMain(ProductEntity productEntity, List<ProductDetailEntity> productDetailsList) {
		//保存主产品信息
		Serializable id = this.save(productEntity);
		
		/**保存-产品方案信息*/
		for(ProductDetailEntity productDetail : productDetailsList){
			//外键设置
			productDetail.setProductId(productEntity.getId());
			this.save(productDetail);
		}				
	}

}
