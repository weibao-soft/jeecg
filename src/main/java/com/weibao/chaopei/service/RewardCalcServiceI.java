package com.weibao.chaopei.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.weibao.chaopei.entity.CompanyAccountEntity;
import com.weibao.chaopei.entity.CompanyUnrewardedDetailEntity;
import com.weibao.chaopei.entity.PersonalAccountEntity;
import com.weibao.chaopei.entity.PersonalUnrewardedDetailEntity;
import com.weibao.chaopei.entity.PolicyEntity;

public interface RewardCalcServiceI extends CommonService {
	/**
	 * 一张保单分润后，需要在一个事务里，修改所有上级公司账户的待分润余额、个人账户的待分润余额、插入公司账户待分润明细列表、插入个人账户待分润明细列表、修改保单状态为已分润
	 * @param cAcctList	公司账户列表
	 * @param personalAcct 个人账户
	 * @param cRwdEtyList 公司账户待分润明细列表
	 * @param pRwdEtyList 个人账户待分润明细列表
	 * @param policyEntity 分润的保单
	 */
	public void batchSavePolicyReward(List<CompanyAccountEntity> cAcctList, 
			PersonalAccountEntity personalAcct,
			List<CompanyUnrewardedDetailEntity> cRwdEtyList,
			List<PersonalUnrewardedDetailEntity> pRwdEtyList, 
			PolicyEntity policyEntity);
	
	public <D, I, U> void saveUnrewardToReward(D delete, I insert, U update);
}
