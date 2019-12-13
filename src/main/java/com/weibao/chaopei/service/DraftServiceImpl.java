package com.weibao.chaopei.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weibao.chaopei.dao.PolicyMainDao;
import com.weibao.chaopei.entity.DraftEntity;
import com.weibao.chaopei.entity.DraftRelationEntity;
import com.weibao.chaopei.entity.HolderEntity;
import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.entity.ProductDetailEntity;
import com.weibao.chaopei.entity.ReceiverEntity;
import com.weibao.chaopei.page.PolicyMainPage;
import com.weibao.chaopei.page.PolicyVehiclePage;
import com.weibao.chaopei.util.CommonUtil;

@Service("draftService")
@Transactional
public class DraftServiceImpl extends CommonServiceImpl implements DraftServiceI {
	private static final Logger logger = Logger.getLogger(DraftServiceImpl.class);
	
	@Autowired
	private PolicyMainDao policyMainDao;

	/**
	 * 新增保存保单、投保人、被投保人、收件人等信息
	 */
	@Override
	public List<PolicyEntity> addMain(PolicyMainPage policyMainPage) {
		HolderEntity holderEntity = new HolderEntity(); 
		ReceiverEntity receiverEntity = new ReceiverEntity();
		ProductDetailEntity detailEntity = new ProductDetailEntity();
		DraftEntity draftEntity = new DraftEntity();
		DraftRelationEntity draftRelationEntity = null;
		PolicyEntity policyEntity = null;
		List<PolicyVehiclePage> vehicles = null;
		List<PolicyEntity> policys = null;
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
			int truckNums = vehicles.size();
			BeanUtils.copyProperties(holderEntity, policyMainPage);
			BeanUtils.copyProperties(receiverEntity, policyMainPage);
			BeanUtils.copyProperties(draftEntity, policyMainPage);
			
			holderEntity.setLastUpdateTime(currDate);
			draftEntity.setSaveTime(currDate);
			holderEntity.setId(UUIDGenerator.generate());
			//String recipientsId = "0";
		
			/**保存-保单关联信息，投保人、被投保人、收件人*/
			//this.saveOrUpdate(holderEntity);
			policyMainDao.saveHolderEntity(holderEntity);
			//policyMainDao.saveInsuredEntity(insuredEntity);
			if("3".equals(invoiceType)) {
				receiverEntity.setId(UUIDGenerator.generate());
				//this.saveOrUpdate(receiverEntity);
				policyMainDao.saveReceiverEntity(receiverEntity);
				//recipientsId = policyMainDao.getReceiverIdByTel(receiverEntity.getRecipientsTel());
			}
			//String holderId = policyMainDao.getHolderIdByCode(holderEntity.getHolderOrgCode());

			//草稿关联产品方案
			detailEntity.setId(policyMainPage.getPlanId());
			draftEntity.setProductDetailEntity(detailEntity);
			//车辆台数
			draftEntity.setTruckNums(truckNums);
			//创建人
			draftEntity.setUserId(policyMainPage.getUserId());
			//保存草稿
			this.save(draftEntity);

			policys = new ArrayList<PolicyEntity>();
			for(int i = 0; i < vehicles.size(); i++) {
				PolicyVehiclePage vehicle = vehicles.get(i);
				policyEntity = new PolicyEntity();
				draftRelationEntity = new DraftRelationEntity();

				if(vehicle.getFrameNo() == null) {
					continue;
				}
				CommonUtil.copyPropertiesIgnoreNull(policyMainPage, policyEntity);
				CommonUtil.copyPropertiesIgnoreNull(vehicle, policyEntity);
				policyEntity.setPayStatus("0");
				policyEntity.setRewardStatus("0");
				policyEntity.setCarTypeCode("0");
				policyEntity.setSeatNum(0);
				
				//创建时间
				policyEntity.setCreateTime(currDate);
				policyEntity.setLastUpdateTime(currDate);
				
				//保存保单主要信息
				this.save(policyEntity);
				//保存草稿和保单的关系
				draftRelationEntity.setPolicyId(policyEntity.getId());
				draftRelationEntity.setDraftId(draftEntity.getId());
				this.save(draftRelationEntity);
				vehicle.setId(policyEntity.getId());
				policys.add(policyEntity);
			}
			
			policyMainPage.setCreateTime(currDate);
			policyMainPage.setDraftId(draftEntity.getId());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return policys;
	}

	/**
	 * 修改保存保单、投保人、被投保人、收件人等信息
	 */
	@Override
	public List<PolicyEntity> updateMain(PolicyMainPage policyMainPage) {
		HolderEntity holderEntity = new HolderEntity(); 
		ReceiverEntity receiverEntity = new ReceiverEntity();
		ProductDetailEntity detailEntity = new ProductDetailEntity();
		DraftEntity draftEntity = new DraftEntity();
		DraftRelationEntity draftRelationEntity = null;
		PolicyEntity policyEntity = null;
		List<PolicyVehiclePage> vehicles = null;
		List<PolicyEntity> policys = null;
		List<String> delIds = null;
		String draftId = null;
		String invoiceType = "1";
		
		try {
			Date currDate = Calendar.getInstance().getTime();
			draftId = policyMainPage.getDraftId();
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
			int truckNums = vehicles.size();
			BeanUtils.copyProperties(holderEntity, policyMainPage);
			BeanUtils.copyProperties(receiverEntity, policyMainPage);
			BeanUtils.copyProperties(draftEntity, policyMainPage);
			
			holderEntity.setLastUpdateTime(currDate);
			draftEntity.setSaveTime(currDate);
			holderEntity.setId(UUIDGenerator.generate());
			
			/**保存-保单关联信息，投保人、被投保人、收件人*/
			policyMainDao.saveHolderEntity(holderEntity);
			//policyMainDao.saveInsuredEntity(insuredEntity);
			if("3".equals(invoiceType)) {
				receiverEntity.setId(UUIDGenerator.generate());
				policyMainDao.saveReceiverEntity(receiverEntity);
			}
			
			//草稿关联产品方案
			detailEntity.setId(policyMainPage.getPlanId());
			draftEntity.setProductDetailEntity(detailEntity);
			//车辆台数
			draftEntity.setTruckNums(truckNums);
			//创建人
			draftEntity.setUserId(policyMainPage.getUserId());
			draftEntity.setId(draftId);
			//保存草稿
			this.saveOrUpdate(draftEntity);

			policys = new ArrayList<PolicyEntity>();
			delIds = new ArrayList<String>();
			for(int i = 0; i < vehicles.size(); i++) {
				PolicyVehiclePage vehicle = vehicles.get(i);
				policyEntity = new PolicyEntity();

				if(vehicle.getFrameNo() == null) {
					continue;
				}
				if("".equals(vehicle.getId())) {
					vehicle.setId(null);
				}
				if (vehicle.getStartDate() == null) {
					vehicle.setStartDate(policyMainPage.getStartDate());
				}
				if (vehicle.getEndDate() == null) {
					vehicle.setEndDate(policyMainPage.getEndDate());
				}
				CommonUtil.copyPropertiesIgnoreNull(policyMainPage, policyEntity);
				CommonUtil.copyPropertiesIgnoreNull(vehicle, policyEntity);
				policyEntity.setCarTypeCode("0");
				policyEntity.setSeatNum(0);
				
				//修改时间
				policyEntity.setLastUpdateTime(currDate);
				policyEntity.setId(vehicle.getId());
				//保存保单主要信息
				this.saveOrUpdate(policyEntity);
				
				if(vehicle.getId() == null) {
					draftRelationEntity = new DraftRelationEntity();
					//保存草稿和保单的关系
					draftRelationEntity.setPolicyId(policyEntity.getId());
					draftRelationEntity.setDraftId(draftEntity.getId());
					this.save(draftRelationEntity);
					vehicle.setId(policyEntity.getId());
				} else {
					delIds.add(vehicle.getId());
				}
				policys.add(policyEntity);
				
			}
			//草稿中删除的保单，同时也从数据库删除
			if(policyMainPage.isDraft() && !delIds.isEmpty()) {
				int size = delIds.size();
				String[] ids = delIds.toArray(new String[size]);
				policyMainDao.deletePolicys(draftId, ids);
				policyMainDao.deleteRelations(draftId, ids);
			}
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return policys;
	}
	
	/**
	 * 重置收件人属性
	 * @param policyMainPage
	 */
	private void resetRecipients(PolicyMainPage policyMainPage) {
		policyMainPage.setCompName("");
		policyMainPage.setCompAddress("");
		policyMainPage.setCompPhone("");
		policyMainPage.setDepositBank("");
		policyMainPage.setBankAccount("");
	}

	/**
	 * 删除保单、草稿信息
	 * @param draftId
	 */
	@Override
	public void delMain(String draftId) {
		DraftEntity draftEntity = new DraftEntity();
		
		try {
			draftEntity.setId(draftId);
			//删除草稿
			this.delete(draftEntity);

			//删除草稿和保单的关系
			policyMainDao.deleteRelationsByDraft(draftId);
			//删除保单
			policyMainDao.deletePolicysByDraft(draftId);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * 删除保单、草稿信息
	 * @param policyMainPage
	 */
	@Override
	public void delMain(PolicyMainPage policyMainPage) {
		DraftEntity draftEntity = new DraftEntity();
		PolicyEntity policyEntity = null;
		List<PolicyVehiclePage> vehicles = null;
		String draftId = null;
		
		try {
			draftId = policyMainPage.getDraftId();
			vehicles = policyMainPage.getVehicles();
			draftEntity.setId(draftId);
			//删除草稿
			this.delete(draftEntity);

			//删除草稿和保单的关系
			policyMainDao.deleteRelationsByDraft(draftId);
			for(int i = 0; i < vehicles.size(); i++) {
				PolicyVehiclePage vehicle = vehicles.get(i);
				policyEntity = new PolicyEntity();

				//删除保单
				if(StringUtils.isNotBlank(vehicle.getId())) {
					policyEntity.setId(vehicle.getId());
					this.delete(policyEntity);
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
	}
}
