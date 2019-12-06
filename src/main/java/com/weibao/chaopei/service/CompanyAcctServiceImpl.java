
package com.weibao.chaopei.service;

import com.weibao.chaopei.entity.*;
import com.weibao.chaopei.page.RewardDetailPage;
import com.weibao.chaopei.util.PolicyUtil;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("companyAcctService")
@Transactional
public class CompanyAcctServiceImpl extends CommonServiceImpl implements CompanyAcctServiceI {
	private static final Logger logger = Logger.getLogger(CompanyAcctServiceImpl.class);


	/**
	 * 从结果Map中取值放入保单PageBean中
	 * @param obj
	 * @param rewardDetailPage
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
		rewardDetailPage.setPremium((Float)obj.get("premium"));
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
	private DataGrid getWithdrawDetailList(String orderId, DataGrid dataGrid,
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
		stbHeadSql1.append("a.policy_no, a.premium, a.plate_no, a.user_id, bu.username user_no, bu.realname username, ");
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

	public DataGrid getWithdrawOrderList(WithdrawOrderEntity orderEntity, DataGrid dataGrid){

		StringBuffer stbHeadSql1 = new StringBuffer();
		StringBuffer stbSql = new StringBuffer();
		stbHeadSql1.append("select od.id, od.account_id, od.alipay_acct, od.alipay_name, od.bank_info, od.amount, ")
				.append("od.org_type, od.`status`, od.apply_time, od.approval_time, dpt.departname ");

		stbSql.append("from wb_withdraw_order od, wb_company_account acct, t_s_depart dpt ")
				.append("where od.account_id=acct.id and acct.depart_id=dpt.ID ");

		Long total = 0L;
		List<Map<String, Object>> objs = null;
		List<WithdrawOrderEntity> withdrawOrderList = new ArrayList<WithdrawOrderEntity>();
		StringBuffer stbHeadSql2 = new StringBuffer();
		stbHeadSql2.append("select count(1) ");

		try {
			List<Object> objList = new ArrayList<Object>();
			int page = dataGrid.getPage();
			int rows = dataGrid.getRows();
			String sort = dataGrid.getSort();
			String order = dataGrid.getOrder();
			WithdrawOrderEntity withdrawOrderEntity = null;
			Object param = null;

			if(StringUtils.isNotBlank(orderEntity.getAccountId())){
				stbSql.append(" and od.account_id = ?");
				objList.add(orderEntity.getAccountId());
			}
			if(StringUtils.isNotBlank(orderEntity.getStatus())){
				stbSql.append(" and od.`status`=? ");
				objList.add(orderEntity.getStatus());
			}

			stbHeadSql2.append(stbSql);

			if(StringUtils.isNotBlank(sort)) {
				String column = "apply_time";
				stbSql.append(" order by " + column + " " + order);
			}

			stbHeadSql1.append(stbSql);
			Object[] objss = objList.toArray();

			total = getCountForJdbcParam(stbHeadSql2.toString(), objss);

			objs = findForJdbcParam(stbHeadSql1.toString(), page, rows, objss);

			for(int i = 0; i < objs.size(); i++) {
				Map<String, Object> obj = objs.get(i);
				WithdrawOrderEntity orderPage = new WithdrawOrderEntity();

				orderPage.setId((String)obj.get("id"));
				orderPage.setCompany((String)obj.get("departname"));
				orderPage.setAmount((BigDecimal)obj.get("amount"));
				orderPage.setAlipayAcct((String)obj.get("alipay_acct"));
				orderPage.setAlipayName((String)obj.get("alipay_name"));
				orderPage.setBankInfo((String)obj.get("bank_info"));
				orderPage.setApplyTime((Date)obj.get("apply_time"));
				orderPage.setApprovalTime((Date)obj.get("approval_time"));
				orderPage.setOrgType((String)obj.get("org_type"));
				orderPage.setStatus((String)obj.get("status"));
				withdrawOrderList.add(orderPage);
			}
			dataGrid.setResults(withdrawOrderList);
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
	public DataGrid getWithdrawDetailList(String orderId, DataGrid dataGrid) {
		StringBuffer stbSql = new StringBuffer();
		StringBuffer stbHeadSql1 = new StringBuffer();
		if(StringUtils.isBlank(orderId)) {
			dataGrid.setResults(new ArrayList<RewardDetailPage>());
			dataGrid.setTotal(0);
			return dataGrid;
		}
		
		stbHeadSql1.append("select d.id, a.plan_id, a.last_update_time, a.pay_time, a.holder_comp_name, d.`status` reward_status, ");
		stbHeadSql1.append("a.policy_no, a.premium, a.plate_no,  a.user_id, bu.username user_no, bu.realname username, ");
		stbHeadSql1.append("dp.id depart_id, dp.departname, d.amount, d.divide_time, b.prod_name, c.prod_plan ");

		stbSql.append(" from wb_insurance_policy a,wb_insurance_product b,wb_product_detail c,wb_company_rewarded_detail d,");
		stbSql.append(" wb_withdraw_order_detail e,t_s_base_user bu,t_s_user_org uo,t_s_depart dp ");
		stbSql.append(" where d.status='1' and a.prod_id=b.id and a.plan_id=c.id and bu.ID=a.user_id and a.id=d.policy_id ");
		stbSql.append(" and d.id=e.reward_detail_id and bu.id=uo.user_id and dp.ID=uo.org_id");
		getWithdrawDetailList(orderId, dataGrid, stbHeadSql1, stbSql);
		return dataGrid;
	}

	public boolean withdrawCompany(String params) {
		String[] rewardDetIds = params.split(",");
		String updateStatus = "update wb_company_rewarded_detail set status=1 where status=0 and id=?";
		CompanyAccountEntity companyAccount = null;
		WithdrawOrderEntity order = new WithdrawOrderEntity();
		order.setOrgType("0");
		order.setStatus("0");
		order.setApplyTime(new Date());
		order.setAmount(new BigDecimal(0));
		List<WithdrawOrderDetailEntity> orderDetList = new ArrayList<WithdrawOrderDetailEntity>();
		this.save(order);

		for (int i = 0; i < rewardDetIds.length; i++) {
			String rewardDetId = rewardDetIds[i];
			CompanyRewardedDetailEntity rewardedEntity = this.getEntity(CompanyRewardedDetailEntity.class, rewardDetId);
			//1.先以乐观锁的方式修改可分润明细的状态
			int updResult = super.executeSql(updateStatus, rewardDetId);
			if(updResult == 1){
				if(companyAccount == null) {
					//找到公司账户ID
					companyAccount = getEntity(CompanyAccountEntity.class, rewardedEntity.getCompanyAccountId());
					order.setAccountId(companyAccount.getId());
					order.setBankInfo(companyAccount.getBankAcctName()+"|"+companyAccount.getBankInfo()+"|"+companyAccount.getBankNo());
				}
				//2. 从公司账户的可提现余额中扣除该笔明细的金额
				companyAccount.setReceivedBalance(companyAccount.getReceivedBalance().subtract(rewardedEntity.getAmount()));
				//3. 新增一条提现记录，多条关系表
				order.setAmount(order.getAmount().add(rewardedEntity.getAmount()));
				WithdrawOrderDetailEntity orderDetEntity = new WithdrawOrderDetailEntity();
				orderDetEntity.setOrderId(order.getId());
				orderDetEntity.setRewardDetailId(rewardedEntity.getId());
				orderDetList.add(orderDetEntity);
			}else{
				throw new RuntimeException("请刷新页面重新发起提现申请！");
			}
		}
		super.saveOrUpdate(order);
		super.saveOrUpdate(companyAccount);
		super.batchSave(orderDetList);

		return true;
	}

	public boolean withdrawPerson(String params) {
		String[] rewardDetIds = params.split(",");
		String updateStatus = "update wb_personal_rewarded_detail set status=1 where status=0 and id=?";
		PersonalAccountEntity personalAccount = null;
		WithdrawOrderEntity order = new WithdrawOrderEntity();
		order.setOrgType("1");
		order.setStatus("0");
		order.setApplyTime(new Date());
		order.setAmount(new BigDecimal(0));
		List<WithdrawOrderDetailEntity> orderDetList = new ArrayList<WithdrawOrderDetailEntity>();
		this.save(order);

		for (int i = 0; i < rewardDetIds.length; i++) {
			String rewardDetId = rewardDetIds[i];
			PersonalRewardedDetailEntity rewardedEntity = this.getEntity(PersonalRewardedDetailEntity.class, rewardDetId);
			//1.先以乐观锁的方式修改可分润明细的状态
			int updResult = super.executeSql(updateStatus, rewardDetId);
			if(updResult == 1){
				if(personalAccount == null) {
					//找到个人账户ID
					personalAccount = getEntity(PersonalAccountEntity.class, rewardedEntity.getPersonalAccountId());
					order.setAccountId(personalAccount.getId());
					order.setBankInfo(personalAccount.getBankAcctName()+"|"+personalAccount.getBankInfo()+"|"+personalAccount.getBankNo());
				}
				//2. 从个人账户的可提现余额中扣除该笔明细的金额
				personalAccount.setReceivedBalance(personalAccount.getReceivedBalance().subtract(rewardedEntity.getAmount()));
				//3. 新增一条提现记录，多条关系表
				order.setAmount(order.getAmount().add(rewardedEntity.getAmount()));
				WithdrawOrderDetailEntity orderDetEntity = new WithdrawOrderDetailEntity();
				orderDetEntity.setOrderId(order.getId());
				orderDetEntity.setRewardDetailId(rewardedEntity.getId());
				orderDetList.add(orderDetEntity);
			}else{
				throw new RuntimeException("请刷新页面重新发起提现申请！");
			}
		}
		super.saveOrUpdate(order);
		super.saveOrUpdate(personalAccount);
		super.batchSave(orderDetList);

		return true;
	}
}
