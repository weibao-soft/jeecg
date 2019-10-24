package com.weibao.chaopei.dao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.pojo.MiniDaoPage;

import com.weibao.chaopei.entity.ReceiverEntity;

@MiniDao
public interface PolicyMainDao {
	@Arguments({"userId"})
	@Sql("select * from wb_insurance_policy p where p.user_id=:userid")
	@ResultType(Map.class)
	public MiniDaoPage<Map<String,Object>> getPolicyEntitys(String userId);
	
	/**
	 *  查询保单信息
	 * @return
	 */
	@Arguments({"draftId"})
	@ResultType(Map.class)
	public Map<String, Object> getPolicyMainPage(String draftId);
	
	/**
	 *  查询收件人信息
	 * @return
	 */
	@Arguments({"recipientsId"})
	@Sql("select id,recipients,recipients_tel,reci_address from wb_invoice_receiver where id=:recipientsId")
	@ResultType(ReceiverEntity.class)
	public ReceiverEntity getReceivers(String recipientsId);
	
	/**
	 *  查询投保人名称
	 * @return
	 */
	@Sql("select comp_name from wb_policy_holder order by comp_name")
	@ResultType(String.class)
	public List<String> getPolicyHolders();
	
	/**
	 *  查询被投保人名称
	 * @return
	 */
	@Sql("select comp_name from wb_insured_info order by comp_name")
	@ResultType(String.class)
	public List<String> getPolicyInsureds();
	
	/**
	 * 查询用户所属的部门名称
	 * @param userid
	 * @return
	 */
	@Arguments({"userId"})
	@Sql("select departname from t_s_depart where id in (select org_id from t_s_user_org where user_id =:userid)")
	@ResultType(String.class)
	public List<String> getUserDepartNames(String userid);
}
