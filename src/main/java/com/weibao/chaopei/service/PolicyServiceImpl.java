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
import com.weibao.chaopei.entity.ProductDetailEntity;
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
			setVehiclePage(objs, policyMainPage);
			setPolicyMainPage(obj, policyMainPage);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return policyMainPage;
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
		policyMainPage.setVehicles(vehicles);
		
	}
	
	/**
	 * 从结果Map中取值放入保单PageBean中
	 * @param obj
	 * @param policyMainPage
	 */
	private void setPolicyMainPage(Map<String, Object> obj, PolicyMainPage policyMainPage) {
		//复制保单详情
		policyMainPage.setPlanId((String)obj.get("plan_id"));
		policyMainPage.setStartDate((Date)obj.get("start_date"));
		policyMainPage.setEndDate((Date)obj.get("end_date"));
		policyMainPage.setStatus((String)obj.get("status"));
		policyMainPage.setContactName((String)obj.get("contact_name"));
		policyMainPage.setPolicyMobile((String)obj.get("policy_mobile"));
		policyMainPage.setInvoiceType((String)obj.get("invoice_type"));
		//草稿id、用户id
		policyMainPage.setDraftId((String)obj.get("draft_id"));
		policyMainPage.setUserId((String)obj.get("user_id"));
		//复制投保人
		policyMainPage.setHolderNature((String)obj.get("holder_nature"));
		policyMainPage.setHolderOrgCode((String)obj.get("holder_org_code"));
		policyMainPage.setHolderCompName((String)obj.get("holder_comp_name"));
		policyMainPage.setHolderCompNature((String)obj.get("holder_comp_nature"));
		policyMainPage.setIndustryType((String)obj.get("industry_type"));
		policyMainPage.setTaxpayerNo((String)obj.get("taxpayer_no"));
		policyMainPage.setReceiverMobile((String)obj.get("receiver_mobile"));
		policyMainPage.setCompName((String)obj.get("comp_name"));
		policyMainPage.setCompAddress((String)obj.get("comp_address"));
		policyMainPage.setCompPhone((String)obj.get("comp_phone"));
		policyMainPage.setDepositBank((String)obj.get("deposit_bank"));
		policyMainPage.setBankAccount((String)obj.get("bank_account"));
		//复制被保人
		policyMainPage.setInsuredCompName((String)obj.get("insured_comp_name"));
		policyMainPage.setInsuredOrgCode((String)obj.get("insured_org_code"));
		policyMainPage.setTruckNums((Integer)obj.get("truck_nums"));
		//复制收件人
		policyMainPage.setRecipients((String)obj.get("recipients"));
		policyMainPage.setRecipientsTel((String)obj.get("recipients_tel"));
		policyMainPage.setReciAddress((String)obj.get("reci_address"));
	}
	
	/**
	 *  查询产品方案信息
	 * @return
	 */
	public List<Map<String, String>> getProductPlan(String prodId) {
		return policyMainDao.getProductPlan(prodId);
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
	 * 根据id删除不在列表里的保单
	 * @param draftId
	 * @param ids
	 */
	public int deletePolicys(String draftId, String[] ids) {
		return policyMainDao.deletePolicys(draftId, ids);
	}

	/**
	 * 根据id删除关系表保单id不在列表里的行
	 * @param draftId
	 * @param ids
	 */
	public int deleteRelations(String draftId, String[] ids) {
		return policyMainDao.deleteRelations(draftId, ids);
	}

	/**
	 * 新增保存保单、投保人、被投保人、收件人等信息
	 */
	@Override
	public PolicyMainPage addMain(PolicyMainPage policyMainPage) {
		HolderEntity holderEntity = new HolderEntity(); 
		ReceiverEntity receiverEntity = new ReceiverEntity();
		ProductDetailEntity detailEntity = new ProductDetailEntity();
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

			for(int i = 0; i < vehicles.size(); i++) {
				PolicyVehiclePage vehicle = vehicles.get(i);
				policyEntity = new PolicyEntity();
				draftRelationEntity = new DraftRelationEntity();

				if(vehicle.getPlateNo() == null) {
					continue;
				}
				BeanUtils.copyProperties(policyEntity, policyMainPage);
				BeanUtils.copyProperties(policyEntity, vehicle);
				//创建时间
				policyEntity.setCreateTime(currDate);
				policyEntity.setLastUpdateTime(currDate);
				//policyEntity.setStatus("1");
				
				//保存保单主要信息
				this.save(policyEntity);
				//保存草稿和保单的关系
				draftRelationEntity.setPolicyId(policyEntity.getId());
				draftRelationEntity.setDraftId(draftEntity.getId());
				this.save(draftRelationEntity);
				//vehicle.setId(policyEntity.getId());
			}
			
			//policyMainPage.setDraftId(draftEntity.getId());
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
		HolderEntity holderEntity = new HolderEntity(); 
		ReceiverEntity receiverEntity = new ReceiverEntity();
		ProductDetailEntity detailEntity = new ProductDetailEntity();
		DraftEntity draftEntity = new DraftEntity();
		DraftRelationEntity draftRelationEntity = null;
		PolicyEntity policyEntity = null;
		List<PolicyVehiclePage> vehicles = null;
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
			
			/**保存-保单关联信息，投保人、被投保人、收件人*/
			policyMainDao.saveHolderEntity(holderEntity);
			//policyMainDao.saveInsuredEntity(insuredEntity);
			if("3".equals(invoiceType)) {
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
			//draftEntity.setStatus("1");
			this.saveOrUpdate(draftEntity);

			delIds = new ArrayList<String>();
			for(int i = 0; i < vehicles.size(); i++) {
				PolicyVehiclePage vehicle = vehicles.get(i);
				policyEntity = new PolicyEntity();

				if(vehicle.getPlateNo() == null) {
					continue;
				}
				BeanUtils.copyProperties(policyEntity, policyMainPage);
				BeanUtils.copyProperties(policyEntity, vehicle);
				//修改时间
				policyEntity.setLastUpdateTime(currDate);
				policyEntity.setId(vehicle.getId());
				//policyEntity.setStatus("1");
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
				
			}
			//草稿中删除的保单，同时也从数据库删除
			if(!delIds.isEmpty()) {
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
		}
		return policyMainPage;
	}
	
	private void resetRecipients(PolicyMainPage policyMainPage) {
		policyMainPage.setCompName("");
		policyMainPage.setCompAddress("");
		policyMainPage.setCompPhone("");
		policyMainPage.setDepositBank("");
		policyMainPage.setBankAccount("");
		policyMainPage.setRecipients("");
		policyMainPage.setRecipientsTel("");
		policyMainPage.setReciAddress("");
	}
}
