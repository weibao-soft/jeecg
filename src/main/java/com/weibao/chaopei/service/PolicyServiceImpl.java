package com.weibao.chaopei.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weibao.chaopei.dao.PolicyMainDao;
import com.weibao.chaopei.entity.HolderEntity;
import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.entity.ProductDetailEntity;
import com.weibao.chaopei.entity.ProductEntity;
import com.weibao.chaopei.page.CommonBean;
import com.weibao.chaopei.page.PolicyMainPage;
import com.weibao.chaopei.page.PolicyVehiclePage;

@Service("policyService")
@Transactional
public class PolicyServiceImpl extends CommonServiceImpl implements PolicyServiceI {
	private static final Logger logger = Logger.getLogger(PolicyServiceImpl.class);
	
	@Autowired
	private PolicyMainDao policyMainDao;
	
	/**
	 *  查询保单信息
	 */
	public PolicyMainPage getPolicyMainPage(String draftId) {
		PolicyMainPage policyMainPage = new PolicyMainPage();
		List<Map<String, Object>> objs = null;
		try {
			objs = policyMainDao.getPolicyMainPage(draftId);
			if(objs == null || objs.isEmpty()) {
				return policyMainPage;
			}
			Map<String, Object> obj = objs.get(0);
			setVehiclePage(objs, policyMainPage);
			setPolicyMainPage(obj, policyMainPage);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return policyMainPage;
	}
	
	private void setVehiclePage(List<Map<String, Object>> objs, PolicyMainPage policyMainPage) {
		List<PolicyVehiclePage> vehicles = new ArrayList<PolicyVehiclePage>();
		for(int i = 0; i < objs.size(); i++) {
			Map<String, Object> obj = objs.get(i);
			PolicyVehiclePage vehicle = new PolicyVehiclePage();
			vehicle.setId((String)obj.get("id"));
			vehicle.setPlateNo((String)obj.get("plate_no"));
			vehicle.setFrameNo((String)obj.get("frame_no"));
			vehicle.setEngineNo((String)obj.get("engine_no"));
			vehicles.add(vehicle);
		}
		policyMainPage.setVehicles(vehicles);
		
	}
	
	/**
	 * 从结果Map中取值放入保单PageBean中
	 * @param obj
	 * @param policyMainPage
	 */
	private void setPolicyMainPage(Map<String, Object> obj, PolicyMainPage policyMainPage) {
		//复制保单详情
		policyMainPage.setProdId((String)obj.get("prod_id"));
		policyMainPage.setPlanId((String)obj.get("plan_id"));
		policyMainPage.setStartDate((Date)obj.get("start_date"));
		policyMainPage.setEndDate((Date)obj.get("end_date"));
		policyMainPage.setCreateTime((Date)obj.get("create_time"));
		policyMainPage.setStatus((String)obj.get("status"));
		policyMainPage.setContactName((String)obj.get("contact_name"));
		policyMainPage.setPolicyMobile((String)obj.get("policy_mobile"));
		policyMainPage.setInvoiceType((String)obj.get("invoice_type"));
		//草稿id、用户id
		policyMainPage.setDraftId((String)obj.get("draft_id"));
		policyMainPage.setUserId((String)obj.get("user_id"));
		//复制投保人
		policyMainPage.setHolderNature((String)obj.get("holder_nature"));
		policyMainPage.setHolderOrgCode((String)obj.get("holder_org_code"));
		policyMainPage.setHolderCompName((String)obj.get("holder_comp_name"));
		policyMainPage.setHolderCompNature((String)obj.get("holder_comp_nature"));
		policyMainPage.setIndustryType((String)obj.get("industry_type"));
		policyMainPage.setTaxpayerNo((String)obj.get("taxpayer_no"));
		policyMainPage.setReceiverMobile((String)obj.get("receiver_mobile"));
		policyMainPage.setCompName((String)obj.get("comp_name"));
		policyMainPage.setCompAddress((String)obj.get("comp_address"));
		policyMainPage.setCompPhone((String)obj.get("comp_phone"));
		policyMainPage.setDepositBank((String)obj.get("deposit_bank"));
		policyMainPage.setBankAccount((String)obj.get("bank_account"));
		//复制被保人
		policyMainPage.setInsuredCompName((String)obj.get("insured_comp_name"));
		policyMainPage.setInsuredOrgCode((String)obj.get("insured_org_code"));
		policyMainPage.setTruckNums((Integer)obj.get("truck_nums"));
		//复制收件人
		policyMainPage.setRecipients((String)obj.get("recipients"));
		policyMainPage.setRecipientsTel((String)obj.get("recipients_tel"));
		policyMainPage.setReciAddress((String)obj.get("reci_address"));
		//复制支付状态、分润状态
		policyMainPage.setPayStatus((String)obj.get("pay_status"));
		policyMainPage.setRewardStatus((String)obj.get("reward_status"));
	}
	
	/**
	 * 从结果Map中取值放入保单PageBean中
	 * @param obj
	 * @param policyMainPage
	 */
	private void setPolicyOther(Map<String, Object> obj, PolicyMainPage policyMainPage) {
		policyMainPage.setId((String)obj.get("id"));
		//复制车辆信息
		policyMainPage.setPlateNo((String)obj.get("plate_no"));
		policyMainPage.setFrameNo((String)obj.get("frame_no"));
		policyMainPage.setEngineNo((String)obj.get("engine_no"));
		//复制保单详情
		policyMainPage.setProdName((String)obj.get("prod_name"));
		policyMainPage.setProdCode((String)obj.get("prod_code"));
		policyMainPage.setInsurCompName((String)obj.get("insur_comp_name"));
		policyMainPage.setProdPlan((String)obj.get("prod_plan"));
		policyMainPage.setPremium((BigDecimal)obj.get("permium"));
		policyMainPage.setUserNo((String)obj.get("user_no"));
		policyMainPage.setUserName((String)obj.get("username"));
		policyMainPage.setCreateTime((Date)obj.get("create_time"));
		policyMainPage.setLastUpdateTime((Date)obj.get("last_update_time"));
	}

	/**
	 *  查询保单列表
	 * @return
	 */
	public DataGrid getPolicyList(PolicyMainPage policy, DataGrid dataGrid) {
		Long total = 0L;
		List<Map<String, Object>> objs = null;
		List<PolicyMainPage> policyList = new ArrayList<PolicyMainPage>();
		StringBuffer stbSql = new StringBuffer();
		StringBuffer stbHeadSql1 = new StringBuffer();
		StringBuffer stbHeadSql2 = new StringBuffer();
		stbHeadSql1.append("select a.id, a.plan_id, a.create_time, a.last_update_time, a.`status`, a.pay_status, a.holder_comp_name, ");
		stbHeadSql1.append("a.plate_no, a.frame_no, a.user_id, bu.username user_no, bu.realname username, c.prod_plan, b.prod_name, b.prod_code, b.insur_comp_name ");
		stbHeadSql2.append("select count(1) ");
		stbSql.append(" from wb_insurance_policy a,wb_insurance_product b,wb_product_detail c,t_s_base_user bu ");
		stbSql.append(" where a.prod_id=b.id and a.plan_id=c.id and bu.ID=a.user_id");
		
		try {
			List<Object> objList = new ArrayList<Object>();
			int page = dataGrid.getPage();
			int rows = dataGrid.getRows();
			String sort = dataGrid.getSort();
			String order = dataGrid.getOrder();
			PolicyMainPage policyMainPage = null;
			Object param = null;
			
			getQueryConditions(policy, stbSql, objList);
			if(StringUtils.isNotBlank(policy.getUserId())) {
				stbSql.append(" and a.user_id = ?");
				param = new String(policy.getUserId());
				objList.add(param);
			}
			if(StringUtils.isNotBlank(sort)) {
				String column = getColumnName(sort);
				if(StringUtils.isNotBlank(column)) {
					stbSql.append(" order by " + column + " " + order);
				}
			}
			
			stbHeadSql1.append(stbSql);
			stbHeadSql2.append(stbSql);
			if(objList.isEmpty()) {
				total = getCountForJdbc(stbHeadSql2.toString());
				objs = findForJdbc(stbHeadSql1.toString(), page, rows);
			} else {
				Object[] objss = objList.toArray();
				total = getCountForJdbcParam(stbHeadSql2.toString(), objss);
				objs = findForJdbcParam(stbHeadSql1.toString(), page, rows, objss);
			}
			
			for(int i = 0; i < objs.size(); i++) {
				Map<String, Object> obj = objs.get(i);
				policyMainPage = new PolicyMainPage();

				setPolicyMainPage(obj, policyMainPage);
				setPolicyOther(obj, policyMainPage);
				policyList.add(policyMainPage);
			}
			dataGrid.setResults(policyList);
			dataGrid.setTotal(total.intValue());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return dataGrid;
	}

	/**
	 *  查询下级机构的保单列表
	 * @return
	 */
	public DataGrid getPolicyList(PolicyMainPage policy, DataGrid dataGrid, List<String> userIdList) {
		Long total = 0L;
		List<Map<String, Object>> objs = null;
		List<PolicyMainPage> policyList = new ArrayList<PolicyMainPage>();
		StringBuffer stbSql = new StringBuffer();
		StringBuffer stbHeadSql1 = new StringBuffer();
		StringBuffer stbHeadSql2 = new StringBuffer();
		stbHeadSql1.append("select a.id, a.plan_id, a.create_time, a.last_update_time, a.`status`, a.pay_status, a.holder_comp_name, ");
		stbHeadSql1.append("a.plate_no, a.frame_no, a.user_id, bu.username user_no, bu.realname username, c.prod_plan, b.prod_name, b.prod_code, ");
		stbHeadSql1.append("b.insur_comp_name, d.id depart_id, d.departname ");
		stbHeadSql2.append("select count(1) ");
		stbSql.append(" from wb_insurance_policy a,wb_insurance_product b,wb_product_detail c,t_s_base_user bu,t_s_user_org uo,t_s_depart d ");
		stbSql.append(" where a.prod_id=b.id and a.plan_id=c.id and bu.ID=a.user_id and bu.id=uo.user_id and d.ID=uo.org_id");
		
		try {
			List<Object> objList = new ArrayList<Object>();
			int page = dataGrid.getPage();
			int rows = dataGrid.getRows();
			String sort = dataGrid.getSort();
			String order = dataGrid.getOrder();
			PolicyMainPage policyMainPage = null;
			
			getQueryConditions(policy, stbSql, objList);
			if(userIdList != null && !userIdList.isEmpty()) {
				stbSql.append(" and a.user_id in(");
				for(int i = 0; i < userIdList.size(); i++) {
					String userId = userIdList.get(i);
					if(i != 0) {
						stbSql.append(",");
					}
					stbSql.append(" ?");
					Object param = (Object)userId;
					objList.add(param);
				}
				stbSql.append(" )");
			} else if(userIdList != null && userIdList.isEmpty()) {
				dataGrid.setResults(policyList);
				dataGrid.setTotal(0);
				return dataGrid;
			}
			if(StringUtils.isNotBlank(sort)) {
				String column = getColumnName(sort);
				if(StringUtils.isNotBlank(column)) {
					stbSql.append(" order by " + column + " " + order);
				}
			}
			
			stbHeadSql1.append(stbSql);
			stbHeadSql2.append(stbSql);
			if(objList.isEmpty()) {
				total = getCountForJdbc(stbHeadSql2.toString());
				objs = findForJdbc(stbHeadSql1.toString(), page, rows);
			} else {
				Object[] objss = objList.toArray();
				total = getCountForJdbcParam(stbHeadSql2.toString(), objss);
				objs = findForJdbcParam(stbHeadSql1.toString(), page, rows, objss);
			}
			
			for(int i = 0; i < objs.size(); i++) {
				Map<String, Object> obj = objs.get(i);
				policyMainPage = new PolicyMainPage();

				setPolicyMainPage(obj, policyMainPage);
				setPolicyOther(obj, policyMainPage);
				policyMainPage.setDepartId((String)obj.get("depart_id"));
				policyMainPage.setDepartName((String)obj.get("departname"));
				policyList.add(policyMainPage);
			}
			dataGrid.setResults(policyList);
			dataGrid.setTotal(total.intValue());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return dataGrid;
	}
	
	/**
	 * 根据页面传入的查询条件生成SQL条件语句
	 * @param policy
	 * @param stbSql
	 * @param objList
	 */
	private void getQueryConditions(PolicyMainPage policy, StringBuffer stbSql, List<Object> objList) {
		Object param = null;
		if(StringUtils.isNotBlank(policy.getHolderCompName())) {
			stbSql.append(" and a.holder_comp_name like ?");
			param = new String("%" + policy.getHolderCompName() + "%");
			objList.add(param);
		}
		if(StringUtils.isNotBlank(policy.getPlateNo())) {
			stbSql.append(" and a.plate_no like ?");
			param = new String("%" + policy.getPlateNo() + "%");
			objList.add(param);
		}
		if(StringUtils.isNotBlank(policy.getFrameNo())) {
			stbSql.append(" and a.frame_no like ?");
			param = new String("%" + policy.getFrameNo() + "%");
			objList.add(param);
		}
		if(StringUtils.isNotBlank(policy.getProdName())) {
			stbSql.append(" and b.prod_name like ?");
			param = new String("%" + policy.getProdName() + "%");
			objList.add(param);
		}
		if(StringUtils.isNotBlank(policy.getInsurCompName())) {
			stbSql.append(" and b.insur_comp_name like ?");
			param = new String("%" + policy.getInsurCompName() + "%");
			objList.add(param);
		}
		if(StringUtils.isNotBlank(policy.getStatus())) {
			stbSql.append(" and a.`status` = ?");
			param = new String(policy.getStatus());
			objList.add(param);
		}
	}
	
	/**
	 * 根据部门id获取用户列表
	 * @param departIds
	 * @return
	 */
	public List<String> getDepartUserIds(List<String> departIds) {
		StringBuffer stbSql = new StringBuffer();
		List<String> idList = new ArrayList<String>();
		List<Object> objList = new ArrayList<Object>();
		List<Map<String, Object>> objs = null;
		
		try {
			String departId = "";
			stbSql.append("select bu.id,bu.realname from t_s_depart d, t_s_base_user bu, t_s_user_org uo ");
			stbSql.append(" where bu.ID=uo.user_id and uo.org_id=d.id ");
			stbSql.append(" and d.id in(");
			for(int i = 0; i < departIds.size(); i++) {
				departId = departIds.get(i);
				if(i != 0) {
					stbSql.append(",");
				}
				stbSql.append(" ?");
				Object param = (Object)departId;
				objList.add(param);
			}
			stbSql.append(" )");
			if(!objList.isEmpty()) {
				Object[] objss = objList.toArray();
				objs = findForJdbc(stbSql.toString(), objss);
				
				for(int j = 0; j < objs.size(); j++) {
					Map<String, Object> obj = objs.get(j);
					String id = (String)obj.get("id");
					idList.add(id);
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		
		return idList;
	}
	
	
	/**
	 *  查询产品方案信息
	 * @return
	 */
	public List<CommonBean> getProductPlan(String departId, String prodId) {
		return policyMainDao.getProductPlan(departId, prodId);
	}
	
	/**
	 *  根据id查询投保人信息
	 * @param id
	 * @return
	 */
	public HolderEntity getHolderById(String id) {
		return policyMainDao.getHolderById(id);
	}
	
	/**
	 *  查询投保人名称
	 */
	public List<CommonBean> getPolicyHolders() {
		return policyMainDao.getPolicyHolders();
	}
	
	/**
	 *  查询被投保人名称
	 */
	public List<CommonBean> getPolicyInsureds() {
		return policyMainDao.getPolicyInsureds();
	}

	/**
	 * 查询该部门所有子部门的ID
	 * @param userid
	 * @return
	 */
	public List<String> getChildDepartIds(String parentId) {
		return policyMainDao.getChildDepartIds(parentId);
	}

	/**
	 * 查询用户所属的部门ID
	 * @param userid
	 * @return
	 */
	public List<String> getDepartIdByUser(String userId) {
		return policyMainDao.getUserDepartIds(userId);
	}
	
	/**
	 * 根据id删除不在列表里的保单
	 * @param draftId
	 * @param ids
	 */
	public int deletePolicys(String draftId, String[] ids) {
		return policyMainDao.deletePolicys(draftId, ids);
	}

	/**
	 * 根据id删除关系表保单id不在列表里的行
	 * @param draftId
	 * @param ids
	 */
	public int deleteRelations(String draftId, String[] ids) {
		return policyMainDao.deleteRelations(draftId, ids);
	}
	
	/**
	 * 根据实体Beande的属性名获取字段名称
	 * @param propName
	 * @return
	 */
	private String getColumnName(String propName) {
		String column1 = null;
		boolean hasField = false;
		try {
			Field field = PolicyEntity.class.getDeclaredField(propName);
			Column column = field.getAnnotation(Column.class);
			column1 = column.name();
			hasField = true;
		} catch (NoSuchFieldException e) {
			//logger.error(e);
		}
		try {
			if(!hasField) {
				Field field = ProductEntity.class.getDeclaredField(propName);
				Column column = field.getAnnotation(Column.class);
				column1 = column.name();
				hasField = true;
			}
		} catch (NoSuchFieldException e) {
			//logger.error(e);
		}
		try {
			if(!hasField) {
				Field field = ProductDetailEntity.class.getDeclaredField(propName);
				Column column = field.getAnnotation(Column.class);
				column1 = column.name();
				hasField = true;
			}
		} catch (NoSuchFieldException e) {
			logger.error(e);
		}
		if(!hasField && "userName".equals(propName)) {
			column1 = "realname";
		}
		return column1;
	}
	
	/**
	 * 根据DataGrid中的查询结果列生成查询sql
	 * @param policyEntity
	 * @param dataGrid
	 * @return
	 */
	protected String getQuerySql(PolicyEntity policyEntity, DataGrid dataGrid) {
		StringBuffer stbSql = new StringBuffer();
		
		try{
			//自定义追加查询条件
			Table table = PolicyEntity.class.getAnnotation(Table.class);
			String table1 = table.name();
			String alias1 = "a";
			table = ProductEntity.class.getAnnotation(Table.class);
			String table2 = table.name();
			String alias2 = "b";
			String column1 = null;

			/*Field[] fields = PolicyEntity.class.getDeclaredFields();
			for(int i = 1; i < fields.length; i++) {
				Column column = fields[i].getAnnotation(Column.class);
				System.out.println(column.name());
			}*/

			stbSql.append("select ");
			System.out.println(dataGrid.getField());
			String[] sfields = dataGrid.getField().split(",");
			for(int j = 0; j < sfields.length; j++) {
				boolean hasField = false;
				try {
					Field field = PolicyEntity.class.getDeclaredField(sfields[j]);
					Column column = field.getAnnotation(Column.class);
					column1 = column.name();
					stbSql.append(" ").append(alias1).append(".").append(column1).append(",");
					hasField = true;
				} catch (NoSuchFieldException e) {
					//logger.error(e);
				}
				try {
					if(!hasField) {
						Field field = ProductEntity.class.getDeclaredField(sfields[j]);
						Column column = field.getAnnotation(Column.class);
						column1 = column.name();
						stbSql.append(" ").append(alias2).append(".").append(column1).append(",");
						hasField = true;
					}
				} catch (NoSuchFieldException e) {
					logger.error(e);
				}
			}
			stbSql.deleteCharAt(stbSql.length() - 1);
			stbSql.append(" from ").append(table1).append(" ").append(alias1).append(",")
							.append(table2).append(" ").append(alias2);
			stbSql.append(" where ").append(alias1).append(".prod_id=").append(alias2).append(".id");
			//查询条件组装器
			
		} catch (SecurityException e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}
		return stbSql.toString();
	}
}
