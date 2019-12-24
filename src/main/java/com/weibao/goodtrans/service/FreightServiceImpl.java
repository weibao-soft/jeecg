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
import com.weibao.goodtrans.page.FreightPolicyPage;

@Service("freightService")
@Transactional
public class FreightServiceImpl extends CommonServiceImpl implements FreightServiceI {
	private static final Logger logger = Logger.getLogger(FreightServiceImpl.class);

	@Autowired
	private FreightPolicyDao freightPolicyDao;
	
	/**
	 * 新增保存货运险保单、投保人、被保人、法人、货物等信息
	 */
	@Override
	public FreightPolicyEntity addMain(FreightPolicyPage freightPolicyPage) {
		FreightPolicyEntity freightEntity = new FreightPolicyEntity();
		
		try {
			Date currDate = Calendar.getInstance().getTime();
			BeanUtils.copyProperties(freightEntity, freightPolicyPage);
			if(freightEntity.getVehicleFrameNo() == null) {
				return null;
			}
			freightPolicyPage.setPayStatus("0");
			freightEntity.setPayStatus("0");
			
			//创建时间
			freightEntity.setCreateTime(currDate);
			freightEntity.setLastUpdateTime(currDate);
			//保存保单主要信息
			this.save(freightEntity);
			freightPolicyPage.setCreateTime(currDate);
			freightPolicyPage.setId(freightEntity.getId());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return freightEntity;
	}
	
	/**
	 * 修改保存货运险保单、投保人、被保人、法人、货物等信息
	 */
	@Override
	public FreightPolicyEntity updateMain(FreightPolicyPage freightPolicyPage) {
		FreightPolicyEntity freightEntity = new FreightPolicyEntity();
		
		try {
			Date currDate = Calendar.getInstance().getTime();
			BeanUtils.copyProperties(freightEntity, freightPolicyPage);
			if(freightEntity.getVehicleFrameNo() == null) {
				return null;
			}
			if("".equals(freightEntity.getId())) {
				freightEntity.setId(null);
			}

			//修改时间
			freightEntity.setLastUpdateTime(currDate);
			//保存保单主要信息
			this.saveOrUpdate(freightEntity);
			freightPolicyPage.setLastUpdateTime(currDate);
			freightPolicyPage.setId(freightEntity.getId());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return freightEntity;
	}

	/**
	 * 删除货运险保单
	 * @param freightId
	 */
	@Override
	public void delMain(String freightId) {
		FreightPolicyEntity freightEntity = null;
		
		try {
			freightEntity = new FreightPolicyEntity();
			//删除保单
			if(StringUtils.isNotBlank(freightId)) {
				freightEntity.setId(freightId);
				this.delete(freightEntity);
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
	 *  根据id查询保单支付信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> getPolicyPayPage(String id) {
		return freightPolicyDao.getPolicyPayPage(id);
	}

	/**
	 * 根据保单id修改保单状态
	 * @param freightId
	 * @param status
	 * @return
	 */
	public int updatePolicyStatus(String freightId, String status) {
		return freightPolicyDao.updatePolicyStatus(freightId, status);
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
	 * @param freightPolicyPage
	 */
	private void setFreightPolicyPage(Map<String, Object> obj, FreightPolicyPage freightPolicyPage) {
		//复制保单详情
		freightPolicyPage.setId((String)obj.get("id"));
		freightPolicyPage.setPolicyNo((String)obj.get("policy_no"));
		freightPolicyPage.setPolicyUrl((String)obj.get("policy_url"));
		freightPolicyPage.setVehiclePlateNo((String)obj.get("vehicle_plate_no"));
		freightPolicyPage.setTrailerPlateNo((String)obj.get("trailer_plate_no"));
		freightPolicyPage.setVehicleFrameNo((String)obj.get("vehicle_frame_no"));
		freightPolicyPage.setStatus((String)obj.get("status"));
		freightPolicyPage.setPayStatus((String)obj.get("pay_status"));
		//保费
		BigDecimal premium = (BigDecimal)obj.get("all_premium");
		if(premium == null) {
			premium = new BigDecimal(0);
		}
		freightPolicyPage.setAllPremium(premium);
		freightPolicyPage.setCreateTime((Date)obj.get("create_time"));
		freightPolicyPage.setPayTime((Date)obj.get("pay_time"));
		
		freightPolicyPage.setPlanId((String)obj.get("plan_id"));
		freightPolicyPage.setCargoStartDate((Date)obj.get("cargo_start_date"));
		freightPolicyPage.setCargoEndDate((Date)obj.get("cargo_end_date"));
		//复制投保人
		String certType = getCertTypeName((String)obj.get("holder_cert_type"));
		String strSex = getSex((String)obj.get("holder_sex"));
		freightPolicyPage.setHolderName((String)obj.get("holder_name"));
		freightPolicyPage.setHolderCertNo((String)obj.get("holder_cert_no"));
		freightPolicyPage.setHolderCertType((String)obj.get("holder_cert_type"));
		freightPolicyPage.setHolderSex((String)obj.get("holder_sex"));
		freightPolicyPage.setHolderCertName(certType);
		freightPolicyPage.setHolderSexs(strSex);
		freightPolicyPage.setHolderProfession((String)obj.get("holder_profession"));
		freightPolicyPage.setHolderNation((String)obj.get("holder_nation"));
		freightPolicyPage.setHolderAddress((String)obj.get("holder_address"));
		freightPolicyPage.setHolderPostal((String)obj.get("holder_postal"));
		freightPolicyPage.setHolderCtatName((String)obj.get("holder_ctat_name"));
		freightPolicyPage.setHolderCtatEmail((String)obj.get("holder_ctat_email"));
		freightPolicyPage.setHolderCtatPhone((String)obj.get("holder_ctat_phone"));
		freightPolicyPage.setHolderCtatMobile((String)obj.get("holder_ctat_mobile"));
		freightPolicyPage.setHolderCtatFax((String)obj.get("holder_ctat_fax"));
		//复制被保人
		certType = getCertTypeName((String)obj.get("insured_cert_type"));
		strSex = getSex((String)obj.get("insured_sex"));
		freightPolicyPage.setInsuredName((String)obj.get("insured_name"));
		freightPolicyPage.setInsuredCertNo((String)obj.get("insured_cert_no"));
		freightPolicyPage.setInsuredCertType((String)obj.get("insured_cert_type"));
		freightPolicyPage.setInsuredSex((String)obj.get("insured_sex"));
		freightPolicyPage.setInsuredCertName(certType);
		freightPolicyPage.setInsuredSexs(strSex);
		freightPolicyPage.setInsuredProfession((String)obj.get("insured_profession"));
		freightPolicyPage.setInsuredNation((String)obj.get("insured_nation"));
		freightPolicyPage.setInsuredAddress((String)obj.get("insured_address"));
		freightPolicyPage.setInsuredPostal((String)obj.get("insured_postal"));
		freightPolicyPage.setInsuredCtatName((String)obj.get("insured_ctat_name"));
		freightPolicyPage.setInsuredCtatEmail((String)obj.get("insured_ctat_email"));
		freightPolicyPage.setInsuredCtatPhone((String)obj.get("insured_ctat_phone"));
		freightPolicyPage.setInsuredCtatMobile((String)obj.get("insured_ctat_mobile"));
		freightPolicyPage.setInsuredCtatFax((String)obj.get("insured_ctat_fax"));
		
		freightPolicyPage.setCorporate((String)obj.get("corporate"));
		freightPolicyPage.setCorporCertName((String)obj.get("corpor_cert_name"));
		freightPolicyPage.setCorporCertNo((String)obj.get("corpor_cert_no"));
		freightPolicyPage.setCorporValidity((String)obj.get("corpor_validity"));
		freightPolicyPage.setBeneficiary((String)obj.get("beneficiary"));

		freightPolicyPage.setGoodsName((String)obj.get("goods_name"));
		freightPolicyPage.setCategory((String)obj.get("category"));
		freightPolicyPage.setCountOrWeight((Float)obj.get("count_or_weight"));
		freightPolicyPage.setInsuredAmount((BigDecimal)obj.get("insured_amount"));
		freightPolicyPage.setPremiumRate((BigDecimal)obj.get("premium_rate"));

		freightPolicyPage.setInvoiceNumb((String)obj.get("invoice_numb"));
		freightPolicyPage.setInsurCompName((String)obj.get("insur_comp_name"));
		//部门id、用户id
		freightPolicyPage.setUserId((String)obj.get("user_id"));
		freightPolicyPage.setDepartId((String)obj.get("depart_id"));
		freightPolicyPage.setUserNo((String)obj.get("user_no"));
		freightPolicyPage.setDepartName((String)obj.get("departname"));
		freightPolicyPage.setUserName((String)obj.get("username"));
		
		freightPolicyPage.setPlanCode((String)obj.get("plan_code"));
		freightPolicyPage.setProdPlan((String)obj.get("prod_plan"));
		
		freightPolicyPage.setRecipients((String)obj.get("recipients"));
		freightPolicyPage.setRecipientsTel((String)obj.get("recipients_tel"));
		freightPolicyPage.setReciAddress((String)obj.get("reci_address"));
		freightPolicyPage.setLastUpdateTime((Date)obj.get("last_update_time"));
		
	}
	
	/**
	 *  查询货运险保单列表
	 * @return
	 */
	public DataGrid getPolicyList(FreightPolicyPage policy, DataGrid dataGrid) {
		Long total = 0L;
		List<Map<String, Object>> objs = null;
		List<FreightPolicyPage> policyList = new ArrayList<FreightPolicyPage>();
		StringBuffer stbSql = new StringBuffer();
		StringBuffer stbHeadSql1 = new StringBuffer();
		StringBuffer stbHeadSql2 = new StringBuffer();
		
		stbHeadSql1.append("select a.id, a.policy_no, a.policy_url, a.vehicle_plate_no, a.trailer_plate_no, a.vehicle_frame_no, a.cargo_start_date, a.cargo_end_date, ");
		stbHeadSql1.append("a.holder_name, a.holder_cert_no, a.insured_name, a.insured_cert_no, a.holder_sex, a.holder_profession, a.holder_nation, a.holder_address, a.holder_postal, ");
		stbHeadSql1.append("a.holder_ctat_name, a.holder_ctat_mobile, a.holder_ctat_phone, a.holder_ctat_fax, a.holder_ctat_email, a.insured_sex, a.insured_profession, a.insured_nation, ");
		stbHeadSql1.append("a.insured_address, a.insured_postal, a.insured_ctat_name, a.insured_ctat_mobile, a.insured_ctat_phone, a.insured_ctat_fax, insured_ctat_email, a.beneficiary, ");
		stbHeadSql1.append("a.corporate, a.corpor_cert_no, a.corpor_cert_name, CONCAT(a.recipients, ' ', a.recipients_tel, ' ', a.reci_address) as taxi_addr, a.create_time, a.pay_time, a.last_update_time, ");
		stbHeadSql1.append("a.goods_name, a.category, a.premium_rate, a.all_insured_amount, a.all_premium, a.`status`, a.pay_status, a.invoice_numb, a.holder_cert_type, a.insured_cert_type, ");
		stbHeadSql1.append("d.id depart_id, d.departname, bu.username, bu.realname, c.plan_code, CONCAT(b.prod_name, '|', c.prod_plan) as prod_plan, b.insur_comp_name ");
		stbHeadSql2.append("select count(1) ");
		stbSql.append(" from wb_freight_insurance_policy a,wb_insurance_product b,wb_product_detail c,t_s_base_user bu,t_s_user_org uo,t_s_depart d ");
		stbSql.append(" where c.prod_id=b.id and a.plan_id=c.id and bu.ID=a.user_id and bu.id=uo.user_id and d.ID=uo.org_id ");
		
		try {
			List<Object> objList = new ArrayList<Object>();
			int page = dataGrid.getPage();
			int rows = dataGrid.getRows();
			String sort = dataGrid.getSort();
			String order = dataGrid.getOrder();
			FreightPolicyPage freightPolicyPage = null;
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
				freightPolicyPage = new FreightPolicyPage();

				setFreightPolicyPage(obj, freightPolicyPage);
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
	private void getQueryConditions(FreightPolicyPage policy, StringBuffer stbSql, List<Object> objList) {
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
		if(StringUtils.isNotBlank(policy.getVehicleFrameNo())) {
			stbSql.append(" and a.vehicle_frame_no like ?");
			param = new String("%" + policy.getVehicleFrameNo() + "%");
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
