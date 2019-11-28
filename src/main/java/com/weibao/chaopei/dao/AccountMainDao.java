package com.weibao.chaopei.dao;

import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;

@MiniDao
public interface AccountMainDao {
	/**
	 *  根据id查询公司账户信息
	 * @return
	 */
	@Arguments({"departId", "accountId"})
	@Sql("select ca.* from wb_company_account ca where ca.depart_id=:departId and ca.id=:accountId")
	@ResultType(Map.class)
	public Map<String, Object> getCompanyAccountPage(String departId, String accountId);
	
	/**
	 *  根据id查询个人账户信息
	 * @return
	 */
	@Arguments({"userId", "accountId"})
	@Sql("select pa.* from wb_personal_account pa where pa.user_id=:userId and pa.id=:accountId")
	@ResultType(Map.class)
	public Map<String, Object> getPersonalAccountPage(String userId, String accountId);
}
