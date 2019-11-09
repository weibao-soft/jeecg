package com.weibao.chaopei.task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weibao.chaopei.entity.CompanyAccountEntity;
import com.weibao.chaopei.entity.CompanyUnrewardedDetailEntity;
import com.weibao.chaopei.entity.PersonalAccountEntity;
import com.weibao.chaopei.entity.PersonalUnrewardedDetailEntity;
import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.service.RewardCalcServiceI;

/**
 * 
 * 	佣金待分润明细计算定时任务 
 * 
 */
@Service("rwdCalcTask")
public class WaitRewardCalcTask extends BasicTask{
	
	@Autowired
	private RewardCalcServiceI rewardCalcServiceI;
	
	boolean isRunning;
	
	@Override
	public void run() {
		long start = System.currentTimeMillis();
		//先把
		org.jeecgframework.core.util.LogUtil.info("===================计算佣金分润定时任务开始===================");
		try {	
			calcReward();
		} catch (Exception e) {
			e.printStackTrace();
		}
		org.jeecgframework.core.util.LogUtil.info("===================计算佣金分润定时任务结束===================");
		long end = System.currentTimeMillis();
		long times = end - start;
		org.jeecgframework.core.util.LogUtil.info("总耗时"+times+"毫秒");
	}
	
	public void calcReward() {		
		if(isRunning) {
			return;
		}
		isRunning = true;
		try {
			/*
			 * {userId_planId:{parent:parentId,rate:rateNum,period:pediodNum}}
			 * 
			 */		
			String sql = "select conf.depart_user departUser, de.parentdepartid parentId, conf.type type, conf.product_plan_id planId, conf.period, conf.rate " + 
					"from wb_commission_conf conf, t_s_depart de " +
					"where conf.depart_user = de.ID " +
					"and conf.rate > 0 and conf.type=0 "+
					"union "+
					"select conf.depart_user depart_user, uo.org_id, conf.type type, conf.product_plan_id, conf.period, conf.rate " + 
					"from wb_commission_conf conf, t_s_user_org uo "+
					"where conf.depart_user = uo.user_id "+
					"and conf.rate > 0 and conf.type=1";
			List<Map<String, Object>> confList = rewardCalcServiceI.findForJdbc(sql);
			//	从数据库里查询出来的内容，以departUser_planId作为key，实体内容作Map的value
			Map<String, Map<String, Object>> commConfCache = new HashMap<String, Map<String, Object>>();
			for (Map<String, Object> map : confList) {
				commConfCache.put((String)map.get("departUser")+"_"+(String)map.get("planId"), map);
			}
			//	从保单表里查找所有已支付、未分润的记录
//			sql = "select * from wb_insurance_policy po where po.pay_status=1 and po.reward_status=0";
			List<PolicyEntity> policyList = rewardCalcServiceI.findHql("from PolicyEntity po where po.payStatus=1 and po.rewardStatus=0");			
//			List<Map<String, Object>> policyList = waitRewardCalcService.findForJdbc(sql);
			
			BigDecimal percent100 = new BigDecimal(100);
			
			for (PolicyEntity policy : policyList) {
				String user_id =  policy.getUserId();
				String plan_id = policy.getPlanId();
				String policy_id = policy.getId();;
				String departUser = "";
				Double permium =  policy.getPermium();
				BigDecimal permium_big = new BigDecimal (permium.toString());
				Date pay_time = policy.getPayTime();
				
				List<PersonalUnrewardedDetailEntity> pRwdEtyList = new ArrayList<PersonalUnrewardedDetailEntity>();
				List<CompanyUnrewardedDetailEntity> cRwdEtyList = new ArrayList<CompanyUnrewardedDetailEntity>();
				List<CompanyAccountEntity> cAcctList = new ArrayList<CompanyAccountEntity>();
				PersonalAccountEntity personalAcct = null;
				CompanyAccountEntity companyAcct = null;
				
				String key = user_id+"_"+plan_id;
				Map<String, Object> rowMap = commConfCache.get(key);
				//如果当前机构没有配置佣金，或者个人都没有配置佣金，不应当跳过，应当继续找上级
				if(rowMap == null) {
					//个人没有配置佣金, 就先去找所属机构的，但一定要确保上级机构分配了佣金，下级机构才能分配
					TSUser user = rewardCalcServiceI.getEntity(TSUser.class, user_id);
					TSUserOrg uo = user.getUserOrgList().get(0);
					if(uo != null) {
						TSDepart parent = uo.getTsDepart();
						do {
							rowMap = commConfCache.get(parent.getId()+"_"+plan_id);
							parent = parent.getTSPDepart();
						}while(rowMap == null);
					}else {
						continue;
					}
				}
				departUser = (String)rowMap.get("departUser");
				Integer period = (Integer)rowMap.get("period");
				BigDecimal rate = (BigDecimal)rowMap.get("rate");								
				Date now = new Date();
				Calendar rewardTime = new GregorianCalendar();
				rewardTime.setTime(pay_time);
				rewardTime.add(Calendar.DAY_OF_MONTH, period);
				
				if("0".equals(rowMap.get("type"))) {	//	机构
					//找到自己的公司账户，如果公司的账户不存在，那么这一机构不会分润，继续往上查找上级机构
					List<CompanyAccountEntity> cAcctListTemp = rewardCalcServiceI.findByProperty(CompanyAccountEntity.class, "departId", departUser);
					BigDecimal subRate = new BigDecimal(0);
					if(cAcctListTemp !=null && cAcctListTemp.size() > 0) {
						do {
							rate = (BigDecimal)rowMap.get("rate");
							companyAcct = cAcctListTemp.get(0);						
							BigDecimal c_newAmount = permium_big.multiply(rate.subtract(subRate).divide(percent100)).setScale(2, RoundingMode.HALF_UP);
							companyAcct.setUnreceivedBalance(c_newAmount.add(companyAcct.getUnreceivedBalance()));
							cAcctList.add(companyAcct);
							//	insert into CompanyRewardedDetailEntity
							CompanyUnrewardedDetailEntity cEntity = new CompanyUnrewardedDetailEntity();							
							cEntity.setAmount(c_newAmount);
							cEntity.setGenerateTime(now);
							cEntity.setPayTime(pay_time);
							cEntity.setRewardTime(rewardTime.getTime());
							cEntity.setPolicyId(policy_id);
							cEntity.setCompanyAccountId(companyAcct.getId());
							cEntity.setDepartId(companyAcct.getDepartId());							
							cRwdEtyList.add(cEntity);
							
							String parentId = (String)rowMap.get("parentId");
							rowMap = commConfCache.get(parentId+"_"+plan_id);
							subRate = rate;
							cAcctListTemp = rewardCalcServiceI.findByProperty(CompanyAccountEntity.class, "departId", parentId);							
							
						} while(rowMap != null);						
					}					
				}else {
					//	人员
					//	insert into PersonalRewardedDetailEntity
					//找到个人账户
					List<PersonalAccountEntity> pAcctList = rewardCalcServiceI.findByProperty(PersonalAccountEntity.class, "userId", departUser);
					if(pAcctList != null && pAcctList.size() >0) {
						personalAcct = pAcctList.get(0);
					}else {
						//这个保单的出单用户，必须要有个人账户信息，否则，这一单不予计算佣金
						continue;
					}
					
					PersonalUnrewardedDetailEntity pEntity = new PersonalUnrewardedDetailEntity();		
					BigDecimal newAmount = permium_big.multiply(rate.divide(percent100)).setScale(2, RoundingMode.HALF_UP); 
					pEntity.setAmount(newAmount);						
					pEntity.setGenerateTime(now);
					//pEntity.setPersonalAccountId(personalAccountId);
					pEntity.setPolicyId(policy_id);
					//pEntity.setReceiveTime(receiveTime); 						
					pEntity.setUserId(departUser);
					pEntity.setPersonalAccountId(personalAcct.getId());
					pEntity.setRewardTime(rewardTime.getTime());						
					pEntity.setPayTime(pay_time);					
					pRwdEtyList.add(pEntity);
					String parentId = (String)rowMap.get("parentId");
					//修改个人账户未分润余额
					personalAcct.setUnreceivedBalance(newAmount.add(personalAcct.getUnreceivedBalance()));
					BigDecimal subRate = rate;
					while(parentId != null) {
						//找到上级的公司账户，如果公司的账户不存在，那么这一机构不会分润，继续往上查找上级机构
						List<CompanyAccountEntity> cAcctListTemp = rewardCalcServiceI.findByProperty(CompanyAccountEntity.class, "departId", parentId);
						if(cAcctListTemp !=null && cAcctListTemp.size() > 0) {
							companyAcct = cAcctListTemp.get(0);
							rowMap = commConfCache.get(parentId+"_"+plan_id);
							rate = (BigDecimal)rowMap.get("rate");
							BigDecimal c_newAmount = permium_big.multiply(rate.subtract(subRate).divide(percent100)).setScale(2, RoundingMode.HALF_UP);
							companyAcct.setUnreceivedBalance(c_newAmount.add(companyAcct.getUnreceivedBalance()));
							cAcctList.add(companyAcct);
							//	insert into CompanyUnrewardedDetailEntity
							CompanyUnrewardedDetailEntity cEntity = new CompanyUnrewardedDetailEntity();							
							cEntity.setAmount(c_newAmount);
							cEntity.setGenerateTime(now);
							cEntity.setPayTime(pay_time);
							cEntity.setRewardTime(rewardTime.getTime());
							cEntity.setPolicyId(policy_id);					
							cEntity.setCompanyAccountId(companyAcct.getId());
							cEntity.setDepartId(parentId);
							
							cRwdEtyList.add(cEntity);
						}								
						
						subRate = rate;
						parentId = (String)rowMap.get("parentId");
					}
				}
				
				try {
					//一个保单开启一次事务		
					rewardCalcServiceI.batchSavePolicyReward(cAcctList, personalAcct, cRwdEtyList, pRwdEtyList, policy);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			isRunning = false;
		}
	}
	
}
