package com.weibao.chaopei.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.ResultType;

import com.weibao.chaopei.page.CommissionConfRef;
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
	
	@Arguments({"departId"})
	@ResultType(CommissionConfRef.class)
	List<CommissionConfRef> getCommissionConfByRoot(String departId);
	
	@Arguments({"departId"})
	@ResultType(CommissionConfRef.class)
	List<CommissionConfRef> getCommissionConfBySubOrg(String departId);
	
	@Arguments({"userId"})
	@ResultType(CommissionConfRef.class)
	List<CommissionConfRef> getCommissionConfByUser(String userId);
}
