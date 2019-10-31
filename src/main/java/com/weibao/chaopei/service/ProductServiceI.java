package com.weibao.chaopei.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.weibao.chaopei.entity.CommissionConfEntity;
import com.weibao.chaopei.entity.DepartProductRefEntity;
import com.weibao.chaopei.entity.ProductDetailEntity;
import com.weibao.chaopei.entity.ProductEntity;

public interface ProductServiceI extends CommonService {
	
	public void addMain(ProductEntity productEntity, List<ProductDetailEntity> productDetailsList);
	
	public void updateMain(ProductEntity productEntity, List<ProductDetailEntity> productDetailsList);
	
	public void udpateAssignProd(String depart_id, List<DepartProductRefEntity> entityList, List<String> removeIds);
	
	public void batchSaveOrUpdate(List<CommissionConfEntity> commConfList);
}
