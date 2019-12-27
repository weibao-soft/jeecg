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
	
	/**
	 * 根据保单id修改支付状态、支付时间、支付平台订单号等
	 * @param policyid
	 * @param orderNo
	 * @return
	 */
	@Sql("update wb_freight_insurance_policy set status='3', pay_status='1', pay_time=SYSDATE(), order_no=:orderNo where id=:policyid and pay_status='0'")
	public int updatePolicyPayInfo(@Param("policyid") String policyid, @Param("orderNo") String orderNo);

	/**
	 * 根据保单id修改保单编号和电子保单url
	 * @param policyid
	 * @return
	 */
	@Sql("update wb_freight_insurance_policy set policy_no=:policyNo, policy_url=:policyUrl where id =:policyid")
	public int updatePolicyNo(@Param("policyNo") String policyNo, @Param("policyUrl") String policyUrl, @Param("policyid") String policyid);
}
