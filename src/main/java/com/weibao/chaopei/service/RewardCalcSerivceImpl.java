package com.weibao.chaopei.service;

import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;

import com.weibao.chaopei.entity.CompanyAccountEntity;
import com.weibao.chaopei.entity.CompanyUnrewardedDetailEntity;
import com.weibao.chaopei.entity.PersonalAccountEntity;
import com.weibao.chaopei.entity.PersonalUnrewardedDetailEntity;
import com.weibao.chaopei.entity.PolicyEntity;

/**
 * 每小时从支付成功的保单里找出待分润的保单，写入待分润明细表
 * @author Liut
 *
 */
@Service("rewardCalcServiceI")
public class RewardCalcSerivceImpl extends CommonServiceImpl implements RewardCalcServiceI {

	@Override
	public void batchSavePolicyReward(List<CompanyAccountEntity> cAcctList, PersonalAccountEntity personalAcct,
			List<CompanyUnrewardedDetailEntity> cRwdEtyList, List<PersonalUnrewardedDetailEntity> pRwdEtyList,
			PolicyEntity policyEntity) {
		
		//保存个人账户待分润佣金明细					
		if(pRwdEtyList.size() > 0)
			batchSaveOrUpdate(pRwdEtyList);
		//修改个人账户待分润余额
		if(personalAcct != null)
			saveOrUpdate(personalAcct);
		//保存公司账户待分润佣金明细								
		batchSaveOrUpdate(cRwdEtyList);
		
		//修改公司账户待分润余额
		batchSaveOrUpdate(cAcctList);		
		
		//修改这张保单的状态为已分润
		policyEntity.setRewardStatus("1");
		
		super.saveOrUpdate(policyEntity);		
	}

	@Override
	public <D, I, U> void saveUnrewardToReward(D delete, I insert, U update) {
		super.delete(delete);
		super.save(insert);
		super.updateEntitie(update);
		
	}
	
	
}
