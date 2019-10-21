package com.weibao.chaopei.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
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
	private static final Logger logger = Logger.getLogger(PolicyServiceImpl.class);

	/**
	 * 保存保单、投保人、被投保人、收件人等信息
	 */
	@Override
	public PolicyMainPage addMain(PolicyMainPage policyMainPage) {
		PolicyEntity policyEntity = new PolicyEntity();
		HolderEntity holderEntity = new HolderEntity(); 
		InsuredEntity insuredEntity = new InsuredEntity();
		ReceiverEntity receiverEntity = new ReceiverEntity();
		DraftEntity draftEntity = new DraftEntity();
		DraftRelationEntity draftRelationEntity = new DraftRelationEntity();
		String invoiceType = "1";
		
		try {
			Date currDate = Calendar.getInstance().getTime();
			invoiceType = policyMainPage.getInvoiceType();
			if("1".equals(invoiceType)) {
				resetRecipients(policyMainPage);
				policyMainPage.setTaxpayerNo("");
				policyMainPage.setReceiverMobile("");
			} else if("2".equals(invoiceType)) {
				resetRecipients(policyMainPage);
			} else if("3".equals(invoiceType)) {
				policyMainPage.setReceiverMobile("");
			}
			BeanUtils.copyProperties(policyEntity, policyMainPage);
			BeanUtils.copyProperties(holderEntity, policyMainPage);
			BeanUtils.copyProperties(receiverEntity, policyMainPage);
			BeanUtils.copyProperties(draftEntity, policyMainPage);
			
			insuredEntity.setOrgCode(policyMainPage.getOrgCode3());
			insuredEntity.setCompName(policyMainPage.getCompName3());
			policyEntity.setCreateTime(currDate);
			policyEntity.setUpdateTime(currDate);
			holderEntity.setUpdateTime(currDate);
			draftEntity.setSaveTime(currDate);
			draftEntity.setUpdateTime(currDate);
			receiverEntity.setId("0");
		
			/**保存-保单关联信息，投保人、被投保人、收件人*/
			this.save(holderEntity);
			this.save(insuredEntity);
			if("3".equals(invoiceType)) {
				this.save(receiverEntity);
			}
			//外键设置
			policyEntity.setHolderId(holderEntity.getId());
			policyEntity.setInsuredId(insuredEntity.getId());
			policyEntity.setRecipientsId(receiverEntity.getId());
			draftEntity.setHolderId(holderEntity.getId());
			draftEntity.setRecipientsId(receiverEntity.getId());
			draftEntity.setUserId(policyMainPage.getUserId());
			draftEntity.setTruckNums(1);
			//draftEntity.setStatus("1");
			//policyEntity.setStatus("1");
	
			//保存保单主要信息
			this.save(policyEntity);
			this.save(draftEntity);
			
			draftRelationEntity.setPolicyId(policyEntity.getId());
			draftRelationEntity.setDraftId(draftEntity.getId());
			this.save(draftRelationEntity);
			policyMainPage.setId(policyEntity.getId());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return policyMainPage;
	}
	
	private void resetRecipients(PolicyMainPage policyMainPage) {
		policyMainPage.setCompName2("");
		policyMainPage.setCompAddress("");
		policyMainPage.setCompPhone("");
		policyMainPage.setDepositBank("");
		policyMainPage.setBankAccount("");
		policyMainPage.setRecipients("");
		policyMainPage.setRecipientsTel("");
		policyMainPage.setReciAddress("");
	}
}
