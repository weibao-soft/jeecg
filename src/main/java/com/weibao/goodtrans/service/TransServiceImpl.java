package com.weibao.goodtrans.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
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

import com.weibao.chaopei.util.PolicyUtil;
import com.weibao.goodtrans.dao.FreightPolicyDao;
import com.weibao.goodtrans.entity.FreightPolicyEntity;
import com.weibao.goodtrans.entity.TransPolicyEntity;
import com.weibao.goodtrans.page.TransPolicyPage;

@Service("transService")
@Transactional
public class TransServiceImpl extends CommonServiceImpl implements TransServiceI {
	private static final Logger logger = Logger.getLogger(TransServiceImpl.class);

	@Autowired
	private FreightPolicyDao freightPolicyDao;
	
	/**
	 * 新增保存货运险保单、投保人、被保人等信息
	 */
	@Override
	public TransPolicyEntity addMain(TransPolicyPage transPolicyPage) {
		TransPolicyEntity transEntity = new TransPolicyEntity();
		
		try {
			Date currDate = Calendar.getInstance().getTime();
			BeanUtils.copyProperties(transEntity, transPolicyPage);
			if(transEntity.getVehiclePlateNo() == null) {
				return null;
			}
			transPolicyPage.setPayStatus("0");
			transEntity.setPayStatus("0");
			
			//创建时间
			transEntity.setCreateTime(currDate);
			transEntity.setLastUpdateTime(currDate);
			//保存保单主要信息
			this.save(transEntity);
			transPolicyPage.setCreateTime(currDate);
			transPolicyPage.setId(transEntity.getId());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return transEntity;
	}
	
	/**
	 * 修改保存货运险保单、投保人、被保人等信息
	 */
	@Override
	public TransPolicyEntity updateMain(TransPolicyPage transPolicyPage) {
		TransPolicyEntity transEntity = new TransPolicyEntity();
		
		try {
			Date currDate = Calendar.getInstance().getTime();
			BeanUtils.copyProperties(transEntity, transPolicyPage);
			if(transEntity.getVehiclePlateNo() == null) {
				return null;
			}
			if("".equals(transEntity.getId())) {
				transEntity.setId(null);
			}

			//修改时间
			transEntity.setLastUpdateTime(currDate);
			//保存保单主要信息
			this.saveOrUpdate(transEntity);
			transPolicyPage.setLastUpdateTime(currDate);
			transPolicyPage.setId(transEntity.getId());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return transEntity;
	}

	/**
	 * 删除货运险保单
	 * @param freightId
	 */
	@Override
	public void delMain(String freightId) {
		TransPolicyEntity transEntity = null;
		
		try {
			transEntity = new TransPolicyEntity();
			//删除保单
			if(StringUtils.isNotBlank(freightId)) {
				transEntity.setId(freightId);
				this.delete(transEntity);
			}
		} catch(HibernateException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
	}
	
	private String getCertTypeName(String certType) {
		if("1".equals(certType)) {
			return "组织机构代码证";
		} else if("2".equals(certType)) {
			return "税务登记证";
		} else if("3".equals(certType)) {
			return "营业执照";
		} else if("4".equals(certType)) {
			return "身份证";
		} else if("5".equals(certType)) {
			return "其他";
		} else {
			return "";
		}
	}
	
	private String getSex(String code) {
		if("M".equals(code)) {
			return "男";
		} else {
			return "女";
		}
	}
	
	/**
	 * 从结果Map中取值放入保单PageBean中
	 * @param obj
	 * @param transPolicyPage
	 */
	private void setTransPolicyPage(Map<String, Object> obj, TransPolicyPage transPolicyPage) {
		//复制保单详情
		transPolicyPage.setId((String)obj.get("id"));
		transPolicyPage.setProductCode((String)obj.get("product_code"));
		transPolicyPage.setPolicyNo((String)obj.get("policy_no"));
		transPolicyPage.setPolicyUrl((String)obj.get("policy_url"));
		transPolicyPage.setVehiclePlateNo((String)obj.get("vehicle_plate_no"));
		transPolicyPage.setTrailerPlateNo((String)obj.get("trailer_plate_no"));
		transPolicyPage.setStatus((String)obj.get("status"));
		transPolicyPage.setPayStatus((String)obj.get("pay_status"));
		//保费
		BigDecimal premium = (BigDecimal)obj.get("all_premium");
		if(premium == null) {
			premium = new BigDecimal(0);
		}
		transPolicyPage.setAllPremium(premium);
		transPolicyPage.setCreateTime((Date)obj.get("create_time"));
		transPolicyPage.setPayTime((Date)obj.get("pay_time"));
		
		transPolicyPage.setCargoStartDate((Date)obj.get("cargo_start_date"));
		transPolicyPage.setCargoEndDate((Date)obj.get("cargo_end_date"));
		transPolicyPage.setCargoStartSite((String)obj.get("cargo_start_site"));
		transPolicyPage.setCargoTargetSite((String)obj.get("cargo_target_site"));
		//复制投保人
		String certType = getCertTypeName((String)obj.get("holder_cert_type"));
		String strSex = getSex((String)obj.get("holder_sex"));
		transPolicyPage.setHolderName((String)obj.get("holder_name"));
		transPolicyPage.setHolderCertNo((String)obj.get("holder_cert_no"));
		transPolicyPage.setHolderCertType((String)obj.get("holder_cert_type"));
		transPolicyPage.setHolderSex((String)obj.get("holder_sex"));
		transPolicyPage.setHolderCertName(certType);
		transPolicyPage.setHolderSexs(strSex);
		transPolicyPage.setHolderBirthday((String)obj.get("holder_birthday"));
		transPolicyPage.setHolderEmail((String)obj.get("holder_email"));
		transPolicyPage.setHolderCtatName((String)obj.get("holder_ctat_name"));
		transPolicyPage.setHolderCtatMobile((String)obj.get("holder_ctat_mobile"));
		//复制被保人
		certType = getCertTypeName((String)obj.get("insured_cert_type"));
		strSex = getSex((String)obj.get("insured_sex"));
		transPolicyPage.setInsuredName((String)obj.get("insured_name"));
		transPolicyPage.setInsuredCertNo((String)obj.get("insured_cert_no"));
		transPolicyPage.setInsuredCertType((String)obj.get("insured_cert_type"));
		transPolicyPage.setInsuredSex((String)obj.get("insured_sex"));
		transPolicyPage.setInsuredCertName(certType);
		transPolicyPage.setInsuredSexs(strSex);
		transPolicyPage.setInsuredCtatName((String)obj.get("insured_ctat_name"));
		transPolicyPage.setInsuredCtatMobile((String)obj.get("insured_ctat_mobile"));
		transPolicyPage.setPhRelToIns((String)obj.get("ph_rel_to_ins"));

		transPolicyPage.setCargoItem((String)obj.get("cargo_item"));
		transPolicyPage.setCargoPkgAndCount((String)obj.get("cargo_pkg_and_count"));
		transPolicyPage.setDurationType((String)obj.get("duration_type"));
		transPolicyPage.setDuration((Float)obj.get("duration"));
		transPolicyPage.setInsuredAmount((BigDecimal)obj.get("insured_amount"));
		transPolicyPage.setPremiumRate((BigDecimal)obj.get("premium_rate"));

		transPolicyPage.setInvoiceNumb((String)obj.get("invoice_numb"));
		//部门id、用户id
		transPolicyPage.setUserId((String)obj.get("user_id"));
		transPolicyPage.setDepartId((String)obj.get("depart_id"));
		transPolicyPage.setUserNo((String)obj.get("user_no"));
		transPolicyPage.setDepartName((String)obj.get("departname"));
		transPolicyPage.setUserName((String)obj.get("username"));
		transPolicyPage.setLastUpdateTime((Date)obj.get("last_update_time"));
		
		transPolicyPage.setRecipients((String)obj.get("recipients"));
		transPolicyPage.setRecipientsTel((String)obj.get("recipients_tel"));
		transPolicyPage.setReciAddress((String)obj.get("reci_address"));
		
	}
	
	/**
	 *  查询货运险保单列表
	 * @return
	 */
	public DataGrid getPolicyList(TransPolicyPage policy, DataGrid dataGrid) {
		Long total = 0L;
		List<Map<String, Object>> objs = null;
		List<TransPolicyPage> policyList = new ArrayList<TransPolicyPage>();
		StringBuffer stbSql = new StringBuffer();
		StringBuffer stbHeadSql1 = new StringBuffer();
		StringBuffer stbHeadSql2 = new StringBuffer();
		
		stbHeadSql1.append("select a.id, a.policy_no, a.policy_url, a.vehicle_plate_no, a.trailer_plate_no, a.waybill_no, a.cargo_trans_way, a.cargo_start_date, a.cargo_end_date, ");
		stbHeadSql1.append("a.holder_name, a.holder_cert_no, a.insured_name, a.insured_cert_no, a.holder_cert_type, a.insured_cert_type, a.holder_sex, a.holder_birthday, a.holder_email, ");
		stbHeadSql1.append("a.holder_ctat_name, a.holder_ctat_mobile, a.insured_sex, a.insured_ctat_name, a.insured_ctat_mobile, a.ph_rel_to_ins, a.cargo_item, a.cargo_pkg_and_count, ");
		stbHeadSql1.append("CONCAT(a.recipients, ' ', a.recipients_tel, ' ', a.reci_address) as taxi_addr, a.create_time, a.pay_time, a.last_update_time, ");
		stbHeadSql1.append("a.premium_rate, a.insured_amount, a.one_premium, a.all_premium, a.`status`, a.pay_status, a.invoice_numb, d.id depart_id, d.departname, bu.username, bu.realname ");
		stbHeadSql2.append("select count(1) ");
		stbSql.append(" from wb_trans_insurance_policy a,t_s_base_user bu,t_s_user_org uo,t_s_depart d ");
		stbSql.append(" where bu.ID=a.user_id and bu.id=uo.user_id and d.ID=uo.org_id and (a.`status`='2' or a.`status`='3') ");
		
		try {
			List<Object> objList = new ArrayList<Object>();
			int page = dataGrid.getPage();
			int rows = dataGrid.getRows();
			String sort = dataGrid.getSort();
			String order = dataGrid.getOrder();
			TransPolicyPage freightPolicyPage = null;
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
				String column = PolicyUtil.getColumnName(FreightPolicyEntity.class, sort);
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
				freightPolicyPage = new TransPolicyPage();

				setTransPolicyPage(obj, freightPolicyPage);
				policyList.add(freightPolicyPage);
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
	private void getQueryConditions(TransPolicyPage policy, StringBuffer stbSql, List<Object> objList) {
		Object param = null;
		if(StringUtils.isNotBlank(policy.getVehiclePlateNo())) {
			stbSql.append(" and a.vehicle_plate_no like ?");
			param = new String("%" + policy.getVehiclePlateNo() + "%");
			objList.add(param);
		}
		if(StringUtils.isNotBlank(policy.getTrailerPlateNo())) {
			stbSql.append(" and a.trailer_plate_no like ?");
			param = new String("%" + policy.getTrailerPlateNo() + "%");
			objList.add(param);
		}
		if(StringUtils.isNotBlank(policy.getWayBillNo())) {
			stbSql.append(" and a.waybill_no like ?");
			param = new String("%" + policy.getWayBillNo() + "%");
			objList.add(param);
		}
		if(StringUtils.isNotBlank(policy.getStatus())) {
			stbSql.append(" and a.`status` = ?");
			param = new String(policy.getStatus());
			objList.add(param);
		}
		if(StringUtils.isNotBlank(policy.getPayStatus())) {
			stbSql.append(" and a.`pay_status` = ?");
			param = new String(policy.getPayStatus());
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
	}
}
