package com.weibao.chaopei.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weibao.chaopei.dao.PolicyMainDao;
import com.weibao.chaopei.entity.DraftEntity;
import com.weibao.chaopei.entity.DraftRelationEntity;
import com.weibao.chaopei.entity.HolderEntity;
import com.weibao.chaopei.entity.InsuredEntity;
import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.entity.ReceiverEntity;
import com.weibao.chaopei.page.PolicyMainPage;
import com.weibao.chaopei.page.PolicyVehiclePage;

@Service("policyService")
@Transactional
public class PolicyServiceImpl extends CommonServiceImpl implements PolicyServiceI {
	private static final Logger logger = Logger.getLogger(PolicyServiceImpl.class);
	
	@Autowired
	private PolicyMainDao policyMainDao;
	
	/**
	 *  查询保单信息
	 */
	public PolicyMainPage getPolicyMainPage(String draftId) {
		PolicyMainPage policyMainPage = new PolicyMainPage();
		List<Map<String, Object>> objs = null;
		try {
			objs = policyMainDao.getPolicyMainPage(draftId);
			if(objs == null || objs.isEmpty()) {
				return policyMainPage;
			}
			Map<String, Object> obj = objs.get(0);
			String recipientsId = (String)obj.get("recipients_id");
			ReceiverEntity receiver = policyMainDao.getReceivers(recipientsId);
			setVehiclePage(objs, policyMainPage);
			setPolicyMainPage(obj, policyMainPage);
			setRecipients(receiver, policyMainPage);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return policyMainPage;
	}
	
	/**
	 * 复制收件人
	 * @param receiver
	 * @param policyMainPage
	 */
	private void setRecipients(ReceiverEntity receiver, PolicyMainPage policyMainPage) {
		policyMainPage.setRecipients(receiver.getRecipients());
		policyMainPage.setRecipientsTel(receiver.getRecipientsTel());
		policyMainPage.setReciAddress(receiver.getReciAddress());
	}
	
	private void setVehiclePage(List<Map<String, Object>> objs, PolicyMainPage policyMainPage) {
		List<PolicyVehiclePage> vehicles = new ArrayList<PolicyVehiclePage>();
		for(int i = 0; i < objs.size(); i++) {
			Map<String, Object> obj = objs.get(i);
			PolicyVehiclePage vehicle = new PolicyVehiclePage();
			vehicle.setId((String)obj.get("id"));
			vehicle.setPlateNo((String)obj.get("plate_no"));
			vehicle.setFrameNo((String)obj.get("frame_no"));
			vehicle.setEngineNo((String)obj.get("engine_no"));
			vehicles.add(vehicle);
		}
		
	}
	
	/**
	 * 从结果Map中取值放入保单PageBean中
	 * @param obj
	 * @param policyMainPage
	 */
	private void setPolicyMainPage(Map<String, Object> obj, PolicyMainPage policyMainPage) {
		policyMainPage.setId((String)obj.get("id"));
		policyMainPage.setPlanId((String)obj.get("plan_id"));
		policyMainPage.setPlateNo((String)obj.get("plate_no"));
		policyMainPage.setFrameNo((String)obj.get("frame_no"));
		policyMainPage.setEngineNo((String)obj.get("engine_no"));
		policyMainPage.setStartDate((Date)obj.get("start_date"));
		policyMainPage.setEndDate((Date)obj.get("end_date"));
		policyMainPage.setStatus((String)obj.get("status"));
		policyMainPage.setContactName((String)obj.get("contact_name"));
		policyMainPage.setPolicyMobile((String)obj.get("policy_mobile"));
		policyMainPage.setInvoiceType((String)obj.get("invoice_type"));
		policyMainPage.setHolderId((String)obj.get("holder_id"));
		policyMainPage.setInsuredId((String)obj.get("insured_id"));
		policyMainPage.setRecipientsId((String)obj.get("recipients_id"));
		policyMainPage.setDraftId((String)obj.get("draft_id"));
		policyMainPage.setUserId((String)obj.get("user_id"));
		policyMainPage.setHolderNature((String)obj.get("holder_nature"));
		policyMainPage.setOrgCode((String)obj.get("org_code"));
		policyMainPage.setCompName((String)obj.get("comp_name"));
		policyMainPage.setCompNature((String)obj.get("comp_nature"));
		policyMainPage.setIndustryType((String)obj.get("industry_type"));
		policyMainPage.setTaxpayerNo((String)obj.get("taxpayer_no"));
		policyMainPage.setReceiverMobile((String)obj.get("receiver_mobile"));
		policyMainPage.setCompName2((String)obj.get("comp_name2"));
		policyMainPage.setCompAddress((String)obj.get("comp_address"));
		policyMainPage.setCompPhone((String)obj.get("comp_phone"));
		policyMainPage.setDepositBank((String)obj.get("deposit_bank"));
		policyMainPage.setBankAccount((String)obj.get("bank_account"));
		policyMainPage.setCompName3((String)obj.get("comp_name3"));
		policyMainPage.setOrgCode3((String)obj.get("org_code3"));
		policyMainPage.setTruckNums((Integer)obj.get("truck_nums"));
	}
	
	/**
	 *  根据id查询投保人信息
	 * @param id
	 * @return
	 */
	public HolderEntity getHolderById(String id) {
		return policyMainDao.getHolderById(id);
	}
	
	/**
	 *  查询投保人名称
	 */
	public List<Map<String, String>> getPolicyHolders() {
		return policyMainDao.getPolicyHolders();
	}
	
	/**
	 *  查询被投保人名称
	 */
	public List<InsuredEntity> getPolicyInsureds() {
		return policyMainDao.getPolicyInsureds();
	}

	/**
	 * 新增保存保单、投保人、被投保人、收件人等信息
	 */
	@Override
	public PolicyMainPage addMain(PolicyMainPage policyMainPage) {
		HolderEntity holderEntity = new HolderEntity(); 
		InsuredEntity insuredEntity = new InsuredEntity();
		ReceiverEntity receiverEntity = new ReceiverEntity();
		DraftEntity draftEntity = new DraftEntity();
		DraftRelationEntity draftRelationEntity = null;
		PolicyEntity policyEntity = null;
		List<PolicyVehiclePage> vehicles = null;
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
			vehicles = policyMainPage.getVehicles();
			BeanUtils.copyProperties(holderEntity, policyMainPage);
			BeanUtils.copyProperties(receiverEntity, policyMainPage);
			BeanUtils.copyProperties(draftEntity, policyMainPage);
			
			insuredEntity.setOrgCode(policyMainPage.getOrgCode3());
			insuredEntity.setCompName(policyMainPage.getCompName3());
			holderEntity.setUpdateTime(currDate);
			draftEntity.setSaveTime(currDate);
			draftEntity.setUpdateTime(currDate);
			holderEntity.setId(UUIDGenerator.generate());
			insuredEntity.setId(UUIDGenerator.generate());
			String recipientsId = "0";
		
			/**保存-保单关联信息，投保人、被投保人、收件人*/
			//this.saveOrUpdate(holderEntity);
			//this.saveOrUpdate(insuredEntity);
			policyMainDao.saveHolderEntity(holderEntity);
			policyMainDao.saveInsuredEntity(insuredEntity);
			if("3".equals(invoiceType)) {
				receiverEntity.setId(UUIDGenerator.generate());
				//this.saveOrUpdate(receiverEntity);
				policyMainDao.saveReceiverEntity(receiverEntity);
				recipientsId = policyMainDao.getReceiverIdByTel(receiverEntity.getRecipientsTel());
			}
			String holderId = policyMainDao.getHolderIdByCode(holderEntity.getOrgCode());
			String insuredId = policyMainDao.getInsuredIdByCode(insuredEntity.getOrgCode());
			//外键设置
			draftEntity.setHolderId(holderId);
			draftEntity.setRecipientsId(recipientsId);
			draftEntity.setUserId(policyMainPage.getUserId());
			draftEntity.setTruckNums(1);
			//draftEntity.setStatus("1");
			//保存草稿
			this.save(draftEntity);

			for(int i = 0; i < vehicles.size(); i++) {
				PolicyVehiclePage vehicle = vehicles.get(i);
				policyEntity = new PolicyEntity();
				draftRelationEntity = new DraftRelationEntity();

				BeanUtils.copyProperties(policyEntity, policyMainPage);
				BeanUtils.copyProperties(policyEntity, vehicle);
				policyEntity.setCreateTime(currDate);
				policyEntity.setUpdateTime(currDate);
				policyEntity.setHolderId(holderId);
				policyEntity.setInsuredId(insuredId);
				policyEntity.setRecipientsId(recipientsId);
				//policyEntity.setStatus("1");
				//保存保单主要信息
				this.save(policyEntity);
				//保存草稿和保单的关系
				draftRelationEntity.setPolicyId(policyEntity.getId());
				draftRelationEntity.setDraftId(draftEntity.getId());
				this.save(draftRelationEntity);
				vehicle.setId(policyEntity.getId());
			}
			
			policyMainPage.setId(policyEntity.getId());
			policyMainPage.setHolderId(holderId);
			policyMainPage.setInsuredId(insuredId);
			policyMainPage.setRecipientsId(recipientsId);
			policyMainPage.setDraftId(draftEntity.getId());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return policyMainPage;
	}

	/**
	 * 修改保存保单、投保人、被投保人、收件人等信息
	 */
	@Override
	public PolicyMainPage updateMain(PolicyMainPage policyMainPage) {
		PolicyEntity policyEntity = new PolicyEntity();
		HolderEntity holderEntity = new HolderEntity(); 
		InsuredEntity insuredEntity = new InsuredEntity();
		ReceiverEntity receiverEntity = new ReceiverEntity();
		DraftEntity draftEntity = new DraftEntity();
		//DraftRelationEntity draftRelationEntity = new DraftRelationEntity();
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
			String holderId = policyMainPage.getHolderId();
			String insuredId = policyMainPage.getInsuredId();
			String recipientsId = policyMainPage.getRecipientsId();

			policyEntity.setId(policyMainPage.getId());
			holderEntity.setId(policyMainPage.getHolderId());
			insuredEntity.setId(policyMainPage.getInsuredId());
			receiverEntity.setId(policyMainPage.getRecipientsId());
			draftEntity.setId(policyMainPage.getDraftId());
			
			/**保存-保单关联信息，投保人、被投保人、收件人*/
			policyMainDao.saveHolderEntity(holderEntity);
			policyMainDao.saveInsuredEntity(insuredEntity);
			if("3".equals(invoiceType)) {
				policyMainDao.saveReceiverEntity(receiverEntity);
			}
			//外键设置
			policyEntity.setHolderId(holderId);
			policyEntity.setInsuredId(insuredId);
			policyEntity.setRecipientsId(recipientsId);
			draftEntity.setHolderId(holderId);
			draftEntity.setRecipientsId(recipientsId);
			draftEntity.setUserId(policyMainPage.getUserId());
			draftEntity.setTruckNums(1);
			//draftEntity.setStatus("1");
			//policyEntity.setStatus("1");
	
			//保存保单主要信息
			this.save(policyEntity);
			this.save(draftEntity);
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
