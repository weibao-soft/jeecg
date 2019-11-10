package com.weibao.chaopei.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weibao.chaopei.entity.CompanyAccountEntity;
import com.weibao.chaopei.entity.CompanyRewardedDetailEntity;
import com.weibao.chaopei.entity.CompanyUnrewardedDetailEntity;
import com.weibao.chaopei.entity.PersonalAccountEntity;
import com.weibao.chaopei.entity.PersonalRewardedDetailEntity;
import com.weibao.chaopei.entity.PersonalUnrewardedDetailEntity;
import com.weibao.chaopei.service.RewardCalcServiceI;

/**
 * 
 * 	将待分润明细转移到已分润明细的定时任务 
 * 
 */
@Service("yetRwdCalcTask")
public class YetRewardCalcTask extends BasicTask{
	
	@Autowired
	private RewardCalcServiceI rewardCalcServiceI;
	
	boolean isRunning;
	
	@Override
	public void run() {
		long start = System.currentTimeMillis();
		//先把
		org.jeecgframework.core.util.LogUtil.info("===================转移待分润明细至已分润明细定时任务开始===================");
		try {	
			calcReward();
		} catch (Exception e) {
			e.printStackTrace();
		}
		org.jeecgframework.core.util.LogUtil.info("===================转移待分润明细至已分润明细定时任务结束===================");
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
			 * 1. 先查找个人账户待分润明细与公司账户待分润明细
			 * 2. 删除待分润明细，写入已分润明细
			 * 3. 修改个人账户余额与公司账户余额
			 */		
			
//			String sql = "select * from wb_company_unreward_detail t where SYSDATE() >= t.reward_time order by t.reward_time asc";
			Date now = new Date();
			List<CompanyUnrewardedDetailEntity> comUnrewardList = rewardCalcServiceI
					.findHql("from CompanyUnrewardedDetailEntity ent where ent.rewardTime <=? order by ent.rewardTime asc", now);
//			List<CompanyUnrewardedDetailEntity> comUnrewardList = rewardCalcServiceI.findListbySql(sql);
			for (CompanyUnrewardedDetailEntity cUnrewardedDetail : comUnrewardList) {				
				CompanyRewardedDetailEntity cRewardDetail = new CompanyRewardedDetailEntity();
				cRewardDetail.setAmount(cUnrewardedDetail.getAmount());
				cRewardDetail.setCompanyAccountId(cUnrewardedDetail.getCompanyAccountId());
				cRewardDetail.setDepartId(cUnrewardedDetail.getDepartId());
				cRewardDetail.setPolicyId(cUnrewardedDetail.getPolicyId());
				cRewardDetail.setDivideTime(cUnrewardedDetail.getGenerateTime());
				cRewardDetail.setReceiveTime(now);
				cRewardDetail.setStatus("0");
				
				CompanyAccountEntity companyAcct = rewardCalcServiceI.getEntity(CompanyAccountEntity.class, cUnrewardedDetail.getCompanyAccountId());
				companyAcct.setUnreceivedBalance(companyAcct.getUnreceivedBalance().subtract(cUnrewardedDetail.getAmount()));
				companyAcct.setReceivedBalance(companyAcct.getReceivedBalance().add(cUnrewardedDetail.getAmount()));
				
				rewardCalcServiceI.saveUnrewardToReward(cUnrewardedDetail, cRewardDetail, companyAcct);
			}
						
			now = new Date();
			List<PersonalUnrewardedDetailEntity> perUnrewardList = rewardCalcServiceI
					.findHql("from PersonalUnrewardedDetailEntity t where t.rewardTime <= ? order by t.rewardTime asc", now);			
			for (PersonalUnrewardedDetailEntity pUnrewardedDetail : perUnrewardList) {			
				PersonalRewardedDetailEntity pRewardDetail = new PersonalRewardedDetailEntity();
				pRewardDetail.setAmount(pUnrewardedDetail.getAmount());
				pRewardDetail.setUserId(pUnrewardedDetail.getUserId());
				pRewardDetail.setPersonalAccountId(pUnrewardedDetail.getPersonalAccountId());
				pRewardDetail.setPolicyId(pUnrewardedDetail.getPolicyId());
				pRewardDetail.setStatus("0");
				pRewardDetail.setDivideTime(pUnrewardedDetail.getGenerateTime());
				pRewardDetail.setReceiveTime(now);
				
				PersonalAccountEntity perAcct = rewardCalcServiceI.getEntity(PersonalAccountEntity.class, pUnrewardedDetail.getPersonalAccountId());
				perAcct.setUnreceivedBalance(perAcct.getUnreceivedBalance().subtract(pUnrewardedDetail.getAmount()));
				perAcct.setReceivedBalance(perAcct.getReceivedBalance().add(pUnrewardedDetail.getAmount()));
				
				rewardCalcServiceI.saveUnrewardToReward(pUnrewardedDetail, pRewardDetail, perAcct);
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			isRunning = false;
		}
	}
	
}
