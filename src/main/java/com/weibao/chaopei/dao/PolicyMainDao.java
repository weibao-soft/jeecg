package com.weibao.chaopei.dao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.pojo.MiniDaoPage;

import com.weibao.chaopei.entity.HolderEntity;
import com.weibao.chaopei.entity.InsuredEntity;
import com.weibao.chaopei.entity.ReceiverEntity;
import com.weibao.chaopei.page.CommonBean;

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
	public List<Map<String, Object>> getPolicyMainPage(String draftId);

	/**
	 *  查询产品方案信息
	 * @return
	 */
	@Sql("select det.id,det.prod_plan name,det.premium code from wb_depart_product_rel rel, wb_product_detail det "
			+ "where rel.product_detail_id=det.id and rel.assign_status=1 and rel.depart_id=:departId and det.prod_id=:prodId")
	@ResultType(CommonBean.class)
	public List<CommonBean> getProductPlan(@Param("departId") String departId, @Param("prodId") String prodId);
	
	/**
	 *  查询收件人信息
	 * @return
	 */
	@Arguments({"recipientsId"})
	@Sql("select id,recipients,recipients_tel,reci_address,user_id from wb_invoice_receiver where id=:recipientsId")
	@ResultType(ReceiverEntity.class)
	public ReceiverEntity getReceivers(String recipientsId);
	
	/**
	 *  查询投保人id
	 * @return
	 */
	@Arguments({"orgCode"})
	@Sql("select id from wb_policy_holder where holder_org_code=:orgCode")
	@ResultType(String.class)
	public String getHolderIdByCode(String orgCode);
	
	/**
	 *  查询被投保人id
	 * @return
	 */
	@Arguments({"orgCode"})
	@Sql("select id from wb_insured_info where insured_org_code=:orgCode")
	@ResultType(String.class)
	public String getInsuredIdByCode(String orgCode);
	
	/**
	 *  查询收件人id
	 * @return
	 */
	@Arguments({"recipientsTel"})
	@Sql("select id from wb_invoice_receiver where recipients_tel=:recipientsTel")
	@ResultType(String.class)
	public String getReceiverIdByTel(String recipientsTel);
	
	/**
	 *  根据id查询投保人信息
	 * @return
	 */
	@Arguments({"id"})
	@Sql("select id,holder_nature,holder_org_code,holder_comp_name,holder_comp_nature,industry_type,taxpayer_no,receiver_mobile,"
			+ "comp_name,comp_address,comp_phone,deposit_bank,bank_account,last_update_time from wb_policy_holder where id=:id")
	@ResultType(HolderEntity.class)
	public HolderEntity getHolderById(String id);
	
	/**
	 *  查询投保人名称
	 * @return
	 */
	@Sql("select id,holder_comp_name name from wb_policy_holder order by holder_comp_name")
	@ResultType(CommonBean.class)
	public List<CommonBean> getPolicyHolders();
	
	/**
	 *  查询被投保人名称
	 * @return
	 */
	@Sql("select id,insured_comp_name name,insured_org_code code from wb_insured_info order by insured_comp_name")
	@ResultType(CommonBean.class)
	public List<CommonBean> getPolicyInsureds();
	
	/**
	 * 查询该部门所有子部门的ID
	 * @param userid
	 * @return
	 */
	@Arguments({"parentId"})
	@Sql("select id from t_s_depart where parentdepartid =:parentId")
	@ResultType(String.class)
	public List<String> getChildDepartIds(String parentId);
	
	/**
	 * 查询用户所属的部门ID
	 * @param userid
	 * @return
	 */
	@Arguments({"userId"})
	@Sql("select id from t_s_depart where id in (select org_id from t_s_user_org where user_id =:userId)")
	@ResultType(String.class)
	public List<String> getUserDepartIds(String userId);
	
	/**
	 * 查询用户所属的部门名称
	 * @param userid
	 * @return
	 */
	@Arguments({"userId"})
	@Sql("select departname from t_s_depart where id in (select org_id from t_s_user_org where user_id =:userId)")
	@ResultType(String.class)
	public List<String> getUserDepartNames(String userId);
	
	/**
	 * 保存投保人
	 * @param holder
	 */
	@Arguments({"holder"})
	public void saveHolderEntity(HolderEntity holder);
	
	/**
	 * 保存被投保人
	 * @param insured
	 */
	@Arguments({"insured"})
	public void saveInsuredEntity(InsuredEntity insured);
	
	/**
	 * 保存收件人
	 * @param receiver
	 */
	@Arguments({"receiver"})
	public void saveReceiverEntity(ReceiverEntity receiver);
	
	/**
	 *  根据草稿id删除草稿保单关系表
	 * @return
	 */
	@Sql("delete from wb_draft_relation where draft_id =:draftId")
	public int deleteRelation(@Param("draftId") String draftId);
	
	/**
	 * 根据id删除保单表id不在列表里的行
	 * @param draftId
	 * @param ids
	 */
	public int deletePolicys(@Param("draftId") String draftId, @Param("ids") String[] ids);
	
	/**
	 * 根据id删除关系表保单id不在列表里的行
	 * @param draftId
	 * @param ids
	 */
	public int deleteRelations(@Param("draftId") String draftId, @Param("ids") String[] ids);
}
