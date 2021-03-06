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
import org.hibernate.HibernateException;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weibao.chaopei.dao.PolicyMainDao;
import com.weibao.chaopei.entity.HolderEntity;
import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.entity.ProductEntity;
import com.weibao.chaopei.entity.ReceiverEntity;
import com.weibao.chaopei.page.CommonBean;
import com.weibao.chaopei.page.InvoiceExportPage;
import com.weibao.chaopei.page.PolicyMainPage;
import com.weibao.chaopei.page.PolicyVehiclePage;
import com.weibao.chaopei.util.PolicyUtil;

@Service("policyService")
@Transactional
public class PolicyServiceImpl extends CommonServiceImpl implements PolicyServiceI {
	private static final Logger logger = Logger.getLogger(PolicyServiceImpl.class);
	
	@Autowired
	private PolicyMainDao policyMainDao;
	
	/**
	 *  查询单个保单信息
	 */
	public PolicyMainPage getOnePolicyMainPage(String policyid) {
		PolicyMainPage policyMainPage = new PolicyMainPage();
		Map<String, Object> objs = null;
		try {
			objs = policyMainDao.getOnePolicyMainPage(policyid);
			if(objs == null || objs.isEmpty()) {
				return policyMainPage;
			}
			setVehiclePage(objs, policyMainPage);
			setPolicyMainPage(objs, policyMainPage);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return policyMainPage;
	}
	
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
			setVehiclesPage(objs, policyMainPage);
			setPolicyMainPage(obj, policyMainPage);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return policyMainPage;
	}
	
	private void setVehiclePage(Map<String, Object> obj, PolicyMainPage policyMainPage) {
		List<PolicyVehiclePage> vehicles = new ArrayList<PolicyVehiclePage>();
		PolicyVehiclePage vehicle = new PolicyVehiclePage();
		vehicle.setId((String)obj.get("id"));
		vehicle.setPlateNo((String)obj.get("plate_no"));
		vehicle.setFrameNo((String)obj.get("frame_no"));
		vehicle.setEngineNo((String)obj.get("engine_no"));
		vehicle.setTonCount((Integer)obj.get("ton_count"));
		vehicle.setStartDate((java.sql.Timestamp)obj.get("start_date"));
		vehicle.setEndDate((java.sql.Timestamp)obj.get("end_date"));
		vehicles.add(vehicle);
		policyMainPage.setVehicles(vehicles);
		
	}
	
	private void setVehiclesPage(List<Map<String, Object>> objs, PolicyMainPage policyMainPage) {
		List<PolicyVehiclePage> vehicles = new ArrayList<PolicyVehiclePage>();
		for(int i = 0; i < objs.size(); i++) {
			Map<String, Object> obj = objs.get(i);
			PolicyVehiclePage vehicle = new PolicyVehiclePage();
			vehicle.setId((String)obj.get("id"));
			vehicle.setPlateNo((String)obj.get("plate_no"));
			vehicle.setFrameNo((String)obj.get("frame_no"));
			vehicle.setEngineNo((String)obj.get("engine_no"));
			vehicle.setTonCount((Integer)obj.get("ton_count"));
			vehicle.setStartDate((java.sql.Timestamp)obj.get("start_date"));
			vehicle.setEndDate((java.sql.Timestamp)obj.get("end_date"));
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
		policyMainPage.setId((String)obj.get("id"));
		policyMainPage.setPolicyNo((String)obj.get("policy_no"));
		policyMainPage.setPolicyUrl((String)obj.get("policy_url"));
		policyMainPage.setPlateNo((String)obj.get("plate_no"));
		policyMainPage.setStatus((String)obj.get("status"));
		policyMainPage.setPayStatus((String)obj.get("pay_status"));
		policyMainPage.setFrameNo((String)obj.get("frame_no"));
		//保费
		Float premium = (Float)obj.get("premium");
		if(premium == null) {
			premium = 0F;
		}
		policyMainPage.setPremium(new BigDecimal(premium));
		policyMainPage.setCreateTime((Date)obj.get("create_time"));
		policyMainPage.setPayTime((Date)obj.get("pay_time"));
		
		policyMainPage.setProdId((String)obj.get("prod_id"));
		policyMainPage.setPlanId((String)obj.get("plan_id"));
		policyMainPage.setStartDate((Date)obj.get("start_date"));
		policyMainPage.setEndDate((Date)obj.get("end_date"));
		policyMainPage.setHolderCompName((String)obj.get("holder_comp_name"));
		policyMainPage.setHolderOrgCode((String)obj.get("holder_org_code"));
		//复制被保人
		policyMainPage.setInsuredCompName((String)obj.get("insured_comp_name"));
		policyMainPage.setInsuredOrgCode((String)obj.get("insured_org_code"));
		policyMainPage.setInvoiceType((String)obj.get("invoice_type"));
		policyMainPage.setInvoiceNumb((String)obj.get("invoice_numb"));
		
		policyMainPage.setReceiverMobile((String)obj.get("receiver_mobile"));
		policyMainPage.setTaxpayerNo((String)obj.get("taxpayer_no"));
		policyMainPage.setCompName((String)obj.get("comp_name"));
		policyMainPage.setCompAddress((String)obj.get("comp_address"));
		policyMainPage.setCompPhone((String)obj.get("comp_phone"));
		policyMainPage.setDepositBank((String)obj.get("deposit_bank"));
		policyMainPage.setBankAccount((String)obj.get("bank_account"));
		policyMainPage.setTaxiAddr((String)obj.get("taxi_addr"));
		policyMainPage.setDepartName((String)obj.get("departname"));
		policyMainPage.setUserName((String)obj.get("username"));
		policyMainPage.setPlanCode((String)obj.get("plan_code"));
		policyMainPage.setProdPlan((String)obj.get("prod_plan"));
		
		
		policyMainPage.setDepartId((String)obj.get("depart_id"));
		//草稿id、用户id
		policyMainPage.setDraftId((String)obj.get("draft_id"));
		policyMainPage.setUserId((String)obj.get("user_id"));
		
		policyMainPage.setTruckNums((Integer)obj.get("truck_nums"));
		
		policyMainPage.setProdName((String)obj.get("prod_name"));
		policyMainPage.setProdCode((String)obj.get("prod_code"));
		policyMainPage.setInsurCompName((String)obj.get("insur_comp_name"));
		
		policyMainPage.setUserNo((String)obj.get("user_no"));		
		policyMainPage.setLastUpdateTime((Date)obj.get("last_update_time"));
		
		policyMainPage.setContactName((String)obj.get("contact_name"));
		policyMainPage.setPolicyMobile((String)obj.get("policy_mobile"));
		policyMainPage.setHolderNature((String)obj.get("holder_nature"));
		policyMainPage.setHolderCompNature((String)obj.get("holder_comp_nature"));
		policyMainPage.setIndustryType((String)obj.get("industry_type"));
		policyMainPage.setRecipients((String)obj.get("recipients"));
		policyMainPage.setRecipientsTel((String)obj.get("recipients_tel"));
		policyMainPage.setReciAddress((String)obj.get("reci_address"));
		policyMainPage.setRewardStatus((String)obj.get("reward_status"));
		policyMainPage.setEngineNo((String)obj.get("engine_no"));
		policyMainPage.setCarTypeCode((String)obj.get("car_type_code"));
		policyMainPage.setSeatNum((Integer)obj.get("seat_num"));
		policyMainPage.setIsPaperPolicy((String)obj.get("is_paper_policy"));
		policyMainPage.setIsPaperInvoice((String)obj.get("is_paper_invoice"));
		
	}
	
	/**
	 * 从结果Map中取值放入保单PageBean中
	 * @param obj
	 * @param policyMainPage
	 */
	private void setInvoiceExportPage(Map<String, Object> obj, InvoiceExportPage policyMainPage) {
		policyMainPage.setPolicyNo((String)obj.get("policy_no"));
		//保费
		Float premium = (Float)obj.get("premium");
		if(premium == null) {
			premium = 0F;
		}
		policyMainPage.setPremium(new BigDecimal(premium));
		policyMainPage.setCreateTime((Date)obj.get("create_time"));
		policyMainPage.setPayTime((Date)obj.get("pay_time"));
		
		policyMainPage.setHolderCompName((String)obj.get("holder_comp_name"));
		//复制被保人
		policyMainPage.setInvoiceType((String)obj.get("invoiceType"));
		
		policyMainPage.setReceiverMobile((String)obj.get("receiver_mobile"));
		policyMainPage.setTaxpayerNo((String)obj.get("taxpayer_no"));
		policyMainPage.setCompAddress((String)obj.get("comp_address"));
		policyMainPage.setCompPhone((String)obj.get("comp_phone"));
		policyMainPage.setDepositBank((String)obj.get("deposit_bank"));
		policyMainPage.setBankAccount((String)obj.get("bank_account"));
		policyMainPage.setTaxiAddr((String)obj.get("taxi_addr"));
		String isPaperPolicy = getYesNo((String)obj.get("is_paper_policy"));
		String isPaperInvoice = getYesNo((String)obj.get("is_paper_invoice"));
		policyMainPage.setIsPaperPolicy(isPaperPolicy);
		policyMainPage.setIsPaperInvoice(isPaperInvoice);
	}
	
	private String getYesNo(String code) {
		if("1".equals(code)) {
			return "是";
		} else {
			return "否";
		}
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
		
		stbHeadSql1.append("select a.id, a.policy_no, a.policy_url, a.plate_no, a.`status`, a.pay_status, a.frame_no, a.premium, a.create_time, a.pay_time, a.holder_comp_name, ");
		stbHeadSql1.append("a.holder_org_code, a.insured_comp_name, a.insured_org_code, a.invoice_type, a.invoice_numb, a.receiver_mobile, a.taxpayer_no, a.comp_address, ");
		stbHeadSql1.append("a.comp_phone, a.deposit_bank, a.bank_account, CONCAT(a.recipients, ' ', a.recipients_tel, ' ', a.reci_address) as taxi_addr, a.is_paper_policy, a.is_paper_invoice, ");
		stbHeadSql1.append("d.id depart_id, d.departname, bu.username, c.plan_code, CONCAT(b.prod_name, '|', c.prod_plan) as prod_plan, b.insur_comp_name ");
		stbHeadSql2.append("select count(1) ");
		stbSql.append(" from wb_insurance_policy a,wb_insurance_product b,wb_product_detail c,t_s_base_user bu,t_s_user_org uo,t_s_depart d ");
		stbSql.append(" where a.prod_id=b.id and a.plan_id=c.id and bu.ID=a.user_id and bu.id=uo.user_id and d.ID=uo.org_id and (a.`status`='2' or a.`status`='3') ");
		
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
			
			stbHeadSql2.append(stbSql);
			
			/*
			 * order by 语句不能用在count查询里
			 */
			if(StringUtils.isNotBlank(sort)) {
				String column = PolicyUtil.getColumnName(sort);
				if(StringUtils.isNotBlank(column)) {
					stbSql.append(" order by " + column + " " + order);
				}
			}
			
			stbHeadSql1.append(stbSql);
			
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
		stbHeadSql1.append("select a.id, a.policy_no, a.policy_url, a.plan_id, a.create_time, a.pay_time,  a.last_update_time, a.`status`, a.pay_status, a.holder_comp_name, a.premium, ");
		stbHeadSql1.append("a.plate_no, a.frame_no, a.user_id, bu.username user_no, bu.realname username, c.prod_plan, b.prod_name, b.prod_code, ");
		stbHeadSql1.append("b.insur_comp_name, d.id depart_id, d.departname ");
		stbHeadSql2.append("select count(1) ");
		stbSql.append(" from wb_insurance_policy a,wb_insurance_product b,wb_product_detail c,t_s_base_user bu,t_s_user_org uo,t_s_depart d ");
		stbSql.append(" where a.prod_id=b.id and a.plan_id=c.id and bu.ID=a.user_id and bu.id=uo.user_id and d.ID=uo.org_id and a.pay_status='1' ");
		
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
			
			stbHeadSql2.append(stbSql);
			/*
			 * order by 语句不能用在count查询里
			 */
			if(StringUtils.isNotBlank(sort)) {
				String column = PolicyUtil.getColumnName(sort);
				if(StringUtils.isNotBlank(column)) {
					stbSql.append(" order by " + column + " " + order);
				}
			}
			
			stbHeadSql1.append(stbSql);
			
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
	 *  查询国任的保单列表
	 * @return
	 */
	public DataGrid getGuorenPolicyList(PolicyMainPage policy, DataGrid dataGrid, List<String> userIdList) {
		Long total = 0L;
		List<Map<String, Object>> objs = null;
		List<PolicyMainPage> policyList = new ArrayList<PolicyMainPage>();
		StringBuffer stbSql = new StringBuffer();
		StringBuffer stbHeadSql1 = new StringBuffer();
		StringBuffer stbHeadSql2 = new StringBuffer();
		stbHeadSql1.append("select a.id, a.policy_no, a.policy_url, a.plate_no, a.`status`, a.pay_status, a.frame_no, a.premium, a.create_time, a.pay_time, a.holder_comp_name, ");
		stbHeadSql1.append("a.holder_org_code, a.insured_comp_name, a.insured_org_code, a.invoice_type, a.invoice_numb, a.receiver_mobile, a.taxpayer_no, a.comp_address, ");
		stbHeadSql1.append("a.comp_phone, a.deposit_bank, a.bank_account, CONCAT(a.recipients, ' ', a.recipients_tel, ' ', a.reci_address) as taxi_addr, a.is_paper_policy, a.is_paper_invoice, ");
		stbHeadSql1.append("d.id depart_id, d.departname, bu.username, c.plan_code, c.prod_plan ");
		stbHeadSql2.append("select count(1) ");
		stbSql.append(" from wb_insurance_policy a,wb_insurance_product b,wb_product_detail c,t_s_base_user bu,t_s_user_org uo,t_s_depart d ");
		stbSql.append(" where a.prod_id=b.id and a.plan_id=c.id and bu.ID=a.user_id and bu.id=uo.user_id and d.ID=uo.org_id and a.pay_status in('1', '2') ");
		
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
			
			stbHeadSql2.append(stbSql);
			
			/*
			 * order by 语句不能用在count查询里
			 */
			if(StringUtils.isNotBlank(sort)) {
				String column = PolicyUtil.getColumnName(sort);
				if(StringUtils.isNotBlank(column)) {
					stbSql.append(" order by " + column + " " + order);
				}
			}
			
			stbHeadSql1.append(stbSql);
			
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
	 *  导出国任的保单列表
	 * @return
	 */
	public DataGrid getGuorenPolicyListExport(PolicyMainPage policy, DataGrid dataGrid, List<String> userIdList) {
		
		List<Map<String, Object>> objs = null;
		List<PolicyMainPage> policyList = new ArrayList<PolicyMainPage>();
		StringBuffer stbSql = new StringBuffer();
		StringBuffer stbHeadSql1 = new StringBuffer();
		stbHeadSql1.append("select d.departname, a.policy_no,  CONCAT(b.prod_name, '|', c.prod_plan) prod_name, a.holder_comp_name, a.holder_org_code,  ")
				.append("bu.username user_no, bu.realname userName, a.plate_no, a.frame_no, a.engine_no, a.start_date, a.end_date,  a.premium, a.pay_time, type.typename invoice_type ");
		
		stbSql.append(" from wb_insurance_policy a,wb_insurance_product b,wb_product_detail c,t_s_base_user bu,t_s_user_org uo,t_s_depart d, t_s_typegroup tg, t_s_type type ")
				.append(" where a.prod_id=b.id and a.plan_id=c.id and bu.ID=a.user_id and bu.id=uo.user_id and d.ID=uo.org_id ")
				.append(" and tg.ID=type.typegroupid and type.typecode=a.invoice_type and tg.typegroupcode='taxiType' and a.pay_status in('1', '2') ");
		
		try {
			List<Object> objList = new ArrayList<Object>();
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
			
			
			stbSql.append(" order by a.pay_time desc ");
			
			stbHeadSql1.append(stbSql);
			
			
			Object[] objss = objList.toArray();				
			objs = findForJdbc(stbHeadSql1.toString(), objss);
			
			
			for(int i = 0; i < objs.size(); i++) {
				Map<String, Object> obj = objs.get(i);
				policyMainPage = new PolicyMainPage();

				setPolicyMainPage(obj, policyMainPage);
				policyList.add(policyMainPage);
			}
			dataGrid.setResults(policyList);
			
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return dataGrid;
	}
	
	
	/**
	 *  导出国任的发票列表
	 * @return
	 */
	public List<InvoiceExportPage> getGuorenInvoiceList(PolicyMainPage policy, DataGrid dataGrid, List<String> userIdList) {
		
		List<Map<String, Object>> objs = null;
		List<InvoiceExportPage> invoiceList = new ArrayList<InvoiceExportPage>();
		StringBuffer stbSql = new StringBuffer();
		StringBuffer stbHeadSql1 = new StringBuffer();		
		stbHeadSql1.append("select a.id, a.policy_no, a.policy_url, a.plate_no, a.`status`, a.pay_status, a.frame_no, a.premium, a.create_time, a.pay_time, a.holder_comp_name, ");
		stbHeadSql1.append("a.holder_org_code, a.insured_comp_name, a.insured_org_code, a.invoice_type, a.invoice_numb, a.receiver_mobile, a.taxpayer_no, a.comp_address, ");
		stbHeadSql1.append("a.comp_phone, a.deposit_bank, a.bank_account, CONCAT(a.recipients, ' ', a.recipients_tel, ' ', a.reci_address) as taxi_addr, a.is_paper_policy, a.is_paper_invoice, ");
		stbHeadSql1.append("d.id depart_id, d.departname, bu.username, c.plan_code, c.prod_plan, type_.typename invoiceType ");
		stbSql.append(" from wb_insurance_policy a, wb_insurance_product b, wb_product_detail c, t_s_base_user bu, t_s_user_org uo,t_s_depart d, t_s_typegroup tg, t_s_type type_ ");
		stbSql.append(" where a.prod_id=b.id and a.plan_id=c.id and bu.ID=a.user_id and bu.id=uo.user_id and d.ID=uo.org_id and tg.ID=type_.typegroupid ");
		stbSql.append(" and type_.typecode=a.invoice_type and tg.typegroupcode='taxiType' and a.pay_status in('1', '2') ");
		
		try {
			List<Object> objList = new ArrayList<Object>();
			InvoiceExportPage invoicePage = null;
			
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
				return invoiceList;
			}
			
			stbSql.append(" order by a.pay_time desc ");
			stbHeadSql1.append(stbSql);
			
			
			Object[] objss = objList.toArray();
			
			objs = findForJdbc(stbHeadSql1.toString(), objss);
			
			for(int i = 0; i < objs.size(); i++) {
				Map<String, Object> obj = objs.get(i);
				invoicePage = new InvoiceExportPage();

				setInvoiceExportPage(obj, invoicePage);
				invoiceList.add(invoicePage);
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return invoiceList;
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
		
		if(StringUtils.isNotBlank(policy.getPolicyNo())) {
			stbSql.append(" and a.policy_no like ?");
			param = new String("%" + policy.getPolicyNo());
			objList.add(param);
		}
		
		if(StringUtils.isNotBlank(policy.getPayTimeFilter_begin())) {
			stbSql.append(" and a.pay_time >= ?");			
			objList.add(DateUtils.str2Date(policy.getPayTimeFilter_begin(), DateUtils.date_sdf));
		}
		if(StringUtils.isNotBlank(policy.getPayTimeFilter_end())) {			
			stbSql.append(" and a.pay_time <= ?");			
			objList.add(DateUtils.str2Date(policy.getPayTimeFilter_end()+" 23:59:59", DateUtils.datetimeFormat));
		}
		if(StringUtils.isNotBlank(policy.getCreateTimeFilter_begin())) {
			stbSql.append(" and a.create_time >= ?");			
			objList.add(DateUtils.str2Date(policy.getCreateTimeFilter_begin(), DateUtils.date_sdf));
		}
		if(StringUtils.isNotBlank(policy.getCreateTimeFilter_end())) {			
			stbSql.append(" and a.create_time <= ?");			
			objList.add(DateUtils.str2Date(policy.getCreateTimeFilter_end()+" 23:59:59", DateUtils.datetimeFormat));
		}
		if(StringUtils.isNotBlank(policy.getInvoiceType())) {
			String[] invoiceTypes = policy.getInvoiceType().split(",");
				stbSql.append(" and a.invoice_type in(");				
				for(int i = 0; i < invoiceTypes.length; i++) {
					String invoiceType = invoiceTypes[i];
					if(StringUtils.isNotBlank(invoiceType)) {												
						stbSql.append(" ?,");						
						objList.add(invoiceType);
					}
				}
				stbSql.deleteCharAt(stbSql.length()-1);
				stbSql.append(" )");
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
	 *  方案保障 ('保障：' + det.prod_plan + ' 保费:' + det.premium)
	 * @return
	 */
	public List<CommonBean> getProductPlan(String departId, String prodId) {
		List<CommonBean> list = policyMainDao.getProductPlan(departId, prodId);
		for(int i = 0; i < list.size(); i++) {
			CommonBean bean = list.get(i);
			String name = bean.getName();
			String code = bean.getCode();
			int length = code.length();
			String decimal = code.substring(length - 3);
			String num = code;
			if(".00".equals(decimal)) {
				num = code.substring(0, length - 3);
			}
			String value = "保障:" + name + " 保费:" + num;
			bean.setName(value);
		}
		return list;
	}
	
	/**
	 *  根据id查询保单支付信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> getPolicyPayPage(String id) {
		return policyMainDao.getPolicyPayPage(id);
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
	 *  查询收件人信息
	 */
	public List<ReceiverEntity> getPolicyReceivers(String userId) {
		return policyMainDao.getPolicyReceivers(userId);
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
	 * 修改保单发票信息
	 * @param invoice
	 * @return
	 */
	public int updatePolicyInvoice(InvoiceExportPage invoice) {
    	//	修改保单状态为已支付；写入支付时间、写入保单号、生成电子保单、修改电子保单url
		String updSql = "update wb_insurance_policy set taxpayer_no=?, comp_name=?, comp_address=?, comp_phone=?, "
				+ "deposit_bank=?, bank_account=?, recipients=?, recipients_tel=?, reci_address=? where id=?";
		int updCnt = super.executeSql(updSql, invoice.getTaxpayerNo(), invoice.getCompName(), invoice.getCompAddress(),
				invoice.getCompPhone(), invoice.getDepositBank(), invoice.getBankAccount(), invoice.getRecipients(),
				invoice.getRecipientsTel(), invoice.getReciAddress(), invoice.getId());
		return updCnt;
	}

	/**
	 * 根据保单id、草稿id分别修改保单和草稿的状态为已投保
	 * @param policyid
	 * @param draftId
	 * @return
	 */
	public int updatePolicyStatusById(List<String> policyids, String draftId) {
		for(int i = 0; i < policyids.size(); i++) {
			String policyid = policyids.get(i);
			policyMainDao.updatePolicyStatus(policyid);
		}
		return policyMainDao.updateDraftStatus(draftId);
	}

	/**
	 * 根据保单id、草稿id分别修改保单和草稿的状态为已投保
	 * @param policyid
	 * @param draftId
	 * @return
	 */
	public int updatePolicyStatus(List<PolicyEntity> list, String draftId) {
		for(int i = 0; i < list.size(); i++) {
			PolicyEntity obj = list.get(i);
			String policyid = obj.getId();
			policyMainDao.updatePolicyStatus(policyid);
		}
		
		return policyMainDao.updateDraftStatus(draftId);
	}

	/**
	 * 删除保单、草稿信息
	 * @param policyid
	 */
	@Override
	public void delMain(String policyId) {
		PolicyEntity policyEntity = null;
		
		try {
			//删除草稿
			policyMainDao.deleteDraftByPolicy(policyId);
			//删除草稿和保单的关系
			policyMainDao.deleteRelationByPolicy(policyId);

			policyEntity = new PolicyEntity();
			//删除保单
			if(StringUtils.isNotBlank(policyId)) {
				policyEntity.setId(policyId);
				this.delete(policyEntity);
			}
		} catch(HibernateException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
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
