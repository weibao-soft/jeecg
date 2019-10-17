package com.weibao.chaopei.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weibao.chaopei.entity.DraftEntity;
import com.weibao.chaopei.entity.DraftRelationEntity;
import com.weibao.chaopei.entity.HolderEntity;
import com.weibao.chaopei.entity.InsuredEntity;
import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.entity.ReceiverEntity;
import com.weibao.chaopei.page.PolicyMainPage;

@Service("policyService")
@Transactional
public class PolicyServiceImpl extends CommonServiceImpl implements PolicyServiceI {

	@Override
	public PolicyMainPage addMain(PolicyMainPage policyMainPage) {
		PolicyEntity policyEntity = new PolicyEntity();
		HolderEntity holderEntity = new HolderEntity(); 
		InsuredEntity insuredEntity = new InsuredEntity();
		ReceiverEntity receiverEntity = new ReceiverEntity();
		DraftEntity draftEntity = new DraftEntity();
		DraftRelationEntity draftRelationEntity = new DraftRelationEntity();
		
		try {
			BeanUtils.copyProperties(policyEntity, policyMainPage);
			BeanUtils.copyProperties(holderEntity, policyMainPage);
			BeanUtils.copyProperties(receiverEntity, policyMainPage);
			
			insuredEntity.setOrgCode(policyMainPage.getOrgCode3());
			insuredEntity.setCompName(policyMainPage.getCompName3());
		
			/**保存-保单关联信息，投保人、被投保人、收件人*/
			this.save(holderEntity);
			this.save(insuredEntity);
			this.save(receiverEntity);
			//外键设置
			policyEntity.setHolderId(holderEntity.getId());
			policyEntity.setInsuredId(insuredEntity.getId());
			policyEntity.setRecipientsId(receiverEntity.getId());
			draftEntity.setHolderId(holderEntity.getId());
			draftEntity.setRecipientsId(receiverEntity.getId());
	
			//保存保单主要信息
			PolicyEntity saveTO = (PolicyEntity)this.save(policyEntity);
			DraftEntity saveTO2 = (DraftEntity)this.save(draftEntity);
			
			draftRelationEntity.setPolicyId(policyEntity.getId());
			draftRelationEntity.setDraftId(draftEntity.getId());
			this.save(draftRelationEntity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return policyMainPage;
	}
}
