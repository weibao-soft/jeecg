package com.weibao.goodtrans.dao;

import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.pojo.MiniDaoPage;

@MiniDao
public interface FreightPolicyDao {
	@Arguments({"userId"})
	@Sql("select * from wb_freight_insurance_policy p where p.user_id=:userid")
	@ResultType(Map.class)
	public MiniDaoPage<Map<String,Object>> getPolicyEntitys(String userId);

	/**
	 *  根据id查询保单支付信息
	 * @return
	 */
	@Arguments({"policyid"})
	@Sql("select p.id,p.all_premium,p.goods_name,p.order_no,p.proposal_no from wb_freight_insurance_policy p where p.pay_status='0' and p.id=:policyid")
	@ResultType(Map.class)
	public Map<String, Object> getPolicyPayPage(String policyid);
	
	/**
	 * 根据保单id修改保单状态
	 * @param policyid
	 * @return
	 */
	@Sql("update wb_freight_insurance_policy set `status`=:status where id =:policyid")
	public int updatePolicyStatus(@Param("policyid") String policyid, @Param("status") String status);

}
