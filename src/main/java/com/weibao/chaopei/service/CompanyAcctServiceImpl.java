package com.weibao.chaopei.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weibao.chaopei.entity.CompanyRewardedDetailEntity;
import com.weibao.chaopei.entity.CompanyUnrewardedDetailEntity;
import com.weibao.chaopei.page.RewardDetailPage;
import com.weibao.chaopei.util.PolicyUtil;

@Service("companyAcctService")
@Transactional
public class CompanyAcctServiceImpl extends CommonServiceImpl implements CompanyAcctServiceI {
	private static final Logger logger = Logger.getLogger(CompanyAcctServiceImpl.class);
	
	/**
	 * 从结果Map中取值放入保单PageBean中
	 * @param obj
	 * @param policyMainPage
	 */
	private void setRewardDetailPage(Map<String, Object> obj, RewardDetailPage rewardDetailPage) {
		rewardDetailPage.setId((String)obj.get("id"));
		//复制车辆信息
		rewardDetailPage.setPlateNo((String)obj.get("plate_no"));
		//复制保单详情
		String prodName = (String)obj.get("prod_name");
		String prodPlan = (String)obj.get("prod_plan");
		rewardDetailPage.setProdName(prodName + "|" + prodPlan);
		rewardDetailPage.setPolicyNo((String)obj.get("policy_no"));
		rewardDetailPage.setHolderCompName((String)obj.get("holder_comp_name"));
		rewardDetailPage.setUserNo((String)obj.get("user_no"));
		rewardDetailPage.setUserName((String)obj.get("username"));
		rewardDetailPage.setDepartName((String)obj.get("departname"));
		rewardDetailPage.setLastUpdateTime((Date)obj.get("last_update_time"));
		//支付时间、分润时间
		rewardDetailPage.setPayTime((Date)obj.get("pay_time"));
		rewardDetailPage.setDivideTime((Date)obj.get("divide_time"));
		rewardDetailPage.setRewardTime((Date)obj.get("reward_time"));
		//复制保单状态、分润状态
		rewardDetailPage.setStatus((String)obj.get("status"));
		rewardDetailPage.setRewardStatus((String)obj.get("reward_status"));
		//分润金额
		rewardDetailPage.setAmount((BigDecimal)obj.get("amount"));
	}

	/**
	 *  查询已分润、未分润明细
	 * @return
	 */
	public DataGrid getRewardDetailList(RewardDetailPage rewardDetail, DataGrid dataGrid, 
			StringBuffer stbHeadSql1, StringBuffer stbSql) {
		Long total = 0L;
		List<Map<String, Object>> objs = null;
		List<RewardDetailPage> rewardList = new ArrayList<RewardDetailPage>();
		StringBuffer stbHeadSql2 = new StringBuffer();
		stbHeadSql2.append("select count(1) ");
		
		try {
			if(StringUtils.isBlank(rewardDetail.getCompanyAccountId()) 
					|| StringUtils.isBlank(rewardDetail.getDepartId())) {
				dataGrid.setResults(rewardList);
				dataGrid.setTotal(0);
				return dataGrid;
			}
			
			List<Object> objList = new ArrayList<Object>();
			int page = dataGrid.getPage();
			int rows = dataGrid.getRows();
			String sort = dataGrid.getSort();
			String order = dataGrid.getOrder();
			RewardDetailPage rewardDetailPage = null;
			Object param = null;

			stbSql.append(" and d.company_account_id = ? and d.depart_id = ?");
			stbHeadSql2.append(stbSql);
			
			if(StringUtils.isNotBlank(sort)) {
				String column = PolicyUtil.getColumnName(sort);
				column = PolicyUtil.getAmbiguousColumn(column);
				if(StringUtils.isNotBlank(column)) {
					stbSql.append(" order by " + column + " " + order);
				}
			}
			stbHeadSql1.append(stbSql);
			
			param = new String(rewardDetail.getCompanyAccountId());
			objList.add(param);
			param = new String(rewardDetail.getDepartId());
			objList.add(param);
			Object[] objss = objList.toArray();
			total = getCountForJdbcParam(stbHeadSql2.toString(), objss);
			objs = findForJdbcParam(stbHeadSql1.toString(), page, rows, objss);
			
			for(int i = 0; i < objs.size(); i++) {
				Map<String, Object> obj = objs.get(i);
				rewardDetailPage = new RewardDetailPage();

				setRewardDetailPage(obj, rewardDetailPage);
				rewardList.add(rewardDetailPage);
			}
			dataGrid.setResults(rewardList);
			dataGrid.setTotal(total.intValue());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return dataGrid;
	}

	/**
	 *  查询取现明细
	 * @return
	 */
	public DataGrid getWithdrawDetailList(String orderId, DataGrid dataGrid, 
			StringBuffer stbHeadSql1, StringBuffer stbSql) {
		Long total = 0L;
		List<Map<String, Object>> objs = null;
		List<RewardDetailPage> rewardList = new ArrayList<RewardDetailPage>();
		StringBuffer stbHeadSql2 = new StringBuffer();
		stbHeadSql2.append("select count(1) ");
		
		try {
			List<Object> objList = new ArrayList<Object>();
			int page = dataGrid.getPage();
			int rows = dataGrid.getRows();
			String sort = dataGrid.getSort();
			String order = dataGrid.getOrder();
			RewardDetailPage rewardDetailPage = null;
			Object param = null;

			stbSql.append(" and e.order_id = ?");
			stbHeadSql2.append(stbSql);
			
			if(StringUtils.isNotBlank(sort)) {
				String column = PolicyUtil.getColumnName(sort);
				column = PolicyUtil.getAmbiguousColumn(column);
				if(StringUtils.isNotBlank(column)) {
					stbSql.append(" order by " + column + " " + order);
				}
			}
			stbHeadSql1.append(stbSql);
			
			param = new String(orderId);
			objList.add(param);
			Object[] objss = objList.toArray();
			total = getCountForJdbcParam(stbHeadSql2.toString(), objss);
			objs = findForJdbcParam(stbHeadSql1.toString(), page, rows, objss);
			
			for(int i = 0; i < objs.size(); i++) {
				Map<String, Object> obj = objs.get(i);
				rewardDetailPage = new RewardDetailPage();

				setRewardDetailPage(obj, rewardDetailPage);
				rewardList.add(rewardDetailPage);
			}
			dataGrid.setResults(rewardList);
			dataGrid.setTotal(total.intValue());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return dataGrid;
	}

	/**
	 *  查询分润明细
	 * @return
	 */
	public DataGrid getReceiveDetailList(CompanyRewardedDetailEntity rewardDetail, DataGrid dataGrid) {
		StringBuffer stbSql = new StringBuffer();
		StringBuffer stbHeadSql1 = new StringBuffer();
		RewardDetailPage rewardPage = new RewardDetailPage();
		rewardPage.setCompanyAccountId(rewardDetail.getCompanyAccountId());
		rewardPage.setDepartId(rewardDetail.getDepartId());
		
		stbHeadSql1.append("select d.id, a.plan_id, a.last_update_time, a.pay_time, a.holder_comp_name, d.`status` reward_status, ");
		stbHeadSql1.append("a.policy_no, a.plate_no, a.user_id, bu.username user_no, bu.realname username, ");
		stbHeadSql1.append("dp.id depart_id, dp.departname, d.amount, d.divide_time, b.prod_name, c.prod_plan ");
		
		stbSql.append(" from wb_insurance_policy a,wb_insurance_product b,wb_product_detail c,wb_company_rewarded_detail d,");
		stbSql.append(" t_s_base_user bu,t_s_user_org uo,t_s_depart dp ");
		stbSql.append(" where d.status='0' and a.prod_id=b.id and a.plan_id=c.id and bu.ID=a.user_id and a.id=d.policy_id ");
		stbSql.append(" and bu.id=uo.user_id and dp.ID=uo.org_id");
		getRewardDetailList(rewardPage, dataGrid, stbHeadSql1, stbSql);
		return dataGrid;
	}

	/**
	 *  查询未分润明细
	 * @return
	 */
	public DataGrid getUnreceiveDetailList(CompanyUnrewardedDetailEntity unrewardDetail, DataGrid dataGrid) {
		StringBuffer stbSql = new StringBuffer();
		StringBuffer stbHeadSql1 = new StringBuffer();
		RewardDetailPage rewardPage = new RewardDetailPage();
		rewardPage.setCompanyAccountId(unrewardDetail.getCompanyAccountId());
		rewardPage.setDepartId(unrewardDetail.getDepartId());
		
		stbHeadSql1.append("select d.id, a.plan_id, a.last_update_time, a.pay_time, a.holder_comp_name, ");
		stbHeadSql1.append("a.policy_no, a.plate_no, a.user_id, bu.username user_no, bu.realname username, ");
		stbHeadSql1.append("dp.id depart_id, dp.departname, d.amount, d.reward_time, b.prod_name, c.prod_plan ");
		
		stbSql.append(" from wb_insurance_policy a,wb_insurance_product b,wb_product_detail c,wb_company_unreward_detail d,");
		stbSql.append(" t_s_base_user bu,t_s_user_org uo,t_s_depart dp ");
		stbSql.append(" where a.prod_id=b.id and a.plan_id=c.id and bu.ID=a.user_id and a.id=d.policy_id and bu.id=uo.user_id and dp.ID=uo.org_id");
		getRewardDetailList(rewardPage, dataGrid, stbHeadSql1, stbSql);
		return dataGrid;
	}

	/**
	 *  查询取现明细
	 * @return
	 */
	public DataGrid getWithdrawDetailList(String orderId, DataGrid dataGrid) {
		StringBuffer stbSql = new StringBuffer();
		StringBuffer stbHeadSql1 = new StringBuffer();
		if(StringUtils.isBlank(orderId)) {
			dataGrid.setResults(new ArrayList<RewardDetailPage>());
			dataGrid.setTotal(0);
			return dataGrid;
		}
		
		stbHeadSql1.append("select d.id, a.plan_id, a.last_update_time, a.pay_time, a.holder_comp_name, d.`status` reward_status, ");
		stbHeadSql1.append("a.policy_no, a.plate_no, a.user_id, bu.username user_no, bu.realname username, ");
		stbHeadSql1.append("dp.id depart_id, dp.departname, d.amount, d.divide_time, b.prod_name, c.prod_plan ");
		
		stbSql.append(" from wb_insurance_policy a,wb_insurance_product b,wb_product_detail c,wb_company_rewarded_detail d,");
		stbSql.append(" wb_withdraw_order_detail e,t_s_base_user bu,t_s_user_org uo,t_s_depart dp ");
		stbSql.append(" where d.status='0' and a.prod_id=b.id and a.plan_id=c.id and bu.ID=a.user_id and a.id=d.policy_id ");
		stbSql.append(" and d.id=e.reward_detail_id and bu.id=uo.user_id and dp.ID=uo.org_id");
		getWithdrawDetailList(orderId, dataGrid, stbHeadSql1, stbSql);
		return dataGrid;
	}
}
