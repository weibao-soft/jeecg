package com.weibao.chaopei.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.ResultType;

import com.weibao.chaopei.page.ProductAssignRef;

/**
 * 
 */
@MiniDao
public interface ProductDetailDao {
	
	@Arguments({"departId"})
	@ResultType(ProductAssignRef.class)
	List<ProductAssignRef> getProductAssignRefByRoot(String departId);
	
	@Arguments({"parentdepartId", "subdepartId"})
	@ResultType(ProductAssignRef.class)
	List<ProductAssignRef> getProductAssignRefBySubRoot(String parentdepartId, String subdepartId);
	
}
