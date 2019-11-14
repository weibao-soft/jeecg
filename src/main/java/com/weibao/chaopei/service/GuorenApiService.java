package com.weibao.chaopei.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.web.system.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.entity.ProductDetailEntity;
import com.weibao.chaopei.entity.ProductEntity;
import com.weibao.chaopei.util.http.ApiPackage;
import com.weibao.chaopei.util.http.Body;
import com.weibao.chaopei.util.http.Element;
import com.weibao.chaopei.util.http.GuorenApiConfig;
import com.weibao.chaopei.util.http.Head;
import com.weibao.chaopei.util.http.RelFieldName;

@Service("guorenApiService")
@Transactional
public class GuorenApiService extends CommonServiceImpl  {
	@Autowired
	GuorenApiConfig apiConfig;
	
	Gson gson = new GsonBuilder().create();	
	/**
	 * 核保接口，根据传入的保单List，组装成国任核保接口要求的数据，调用核保接口，然后解析得到投保单号、订单号，写回policyEntityList
	 * @param policyEntityList 保单list
	 * @return result list里面是一个map对象，map对象包含：保单id、投保单号、订单号
	 */
	public List<Map<String, String>> insuredService(List<PolicyEntity> policyEntityList) {
		ProductDetailEntity detail = null;
		ProductEntity prod = null;
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		if(policyEntityList != null && policyEntityList.size() > 0) {
			PolicyEntity entity = policyEntityList.get(0);
			detail = super.getEntity(ProductDetailEntity.class, entity.getPlanId());
			prod = super.getEntity(ProductEntity.class, entity.getProdId());
		}
		
		for (PolicyEntity policyEntity : policyEntityList) {
			try {
				ApiPackage package_ = new ApiPackage();
		    	Head head = new Head();
		    	head.setCLIENT_ENCODE(apiConfig.CLIENT_ENCODE);
		    	head.setSERVE_CODE(apiConfig.SERVE_CODE);    	
		    	head.setCHANNEL_CODE(apiConfig.CHANNEL_CODE);
		    	head.setINTERFACE_CODE(apiConfig.INTERFACE_CODE_HEBAO);
		    	head.setINTERFACE_USER_CODE(apiConfig.INTERFACE_USER_CODE);
		    	head.setINTERFACE_PWD(apiConfig.INTERFACE_PWD);
		    	head.setBUSINESS_UUID(policyEntity.getId());
		    	Date now = new Date();	    	
		    	head.setREQUEST_TIME(DateUtils.datetimeFormatSSSS(now));
		    	head.setIFTEST(apiConfig.IFTEST);
		    	head.setXML_LIST_SUFFIX("");
		    	
		    	package_.setHEAD(head);
		    	Body body = new Body();
		    	package_.setBODY(body);
		    	body.setOperCode(policyEntity.getPolicyMobile());//填录单人员联系方式
		    	body.setOperType(apiConfig.operType);
		    	body.setOrder_source(apiConfig.order_source);
		    	body.setPermium(policyEntity.getPremium().toString());
		    	body.setPlan_code(detail.getPlanCode());
		    	body.setPlan_name(detail.getPlanName());
		    	body.setGood_code(prod.getGoodCode());
		    	body.setGood_name(prod.getProdName());
		    	body.setAppnum(apiConfig.appnum);
		    	
		    	Element base = new Element();
		    	body.setBase(base);
		    	base.setProdCode(prod.getProdCode());
		    	base.setRelTbl(apiConfig.ins_reltab);
		    	base.setRelTbl(apiConfig.base_reltab);
		    	RelFieldName relFieldName_base = new RelFieldName();
		    	base.setRelFieldName(relFieldName_base);
		    	relFieldName_base.setPlcy_bgn_time(DateUtils.formatDate(policyEntity.getStartDate()));
		    	relFieldName_base.setPlcy_end_time(DateUtils.formatDate(policyEntity.getEndDate()));
		    	relFieldName_base.setCall_back_url(apiConfig.call_back_url);
		    	
		    	Element applicant = new Element();
		    	body.setApplicant(applicant);
		    	applicant.setProdCode(prod.getProdCode());
		    	applicant.setRelTbl(apiConfig.appl_reltab);
		    	RelFieldName relFieldName_applicant = new RelFieldName();
		    	applicant.setRelFieldName(relFieldName_applicant);
		    	relFieldName_applicant.setAppl_name(policyEntity.getHolderCompName());
		    	relFieldName_applicant.setClnt_type("2");
		    	relFieldName_applicant.setCertf_type("99");
		    	relFieldName_applicant.setCertf_no(policyEntity.getHolderOrgCode());
		    	relFieldName_applicant.setEmail("");
		    	relFieldName_applicant.setMobile(policyEntity.getPolicyMobile());
		    	relFieldName_applicant.setBank_account("");
		    	relFieldName_applicant.setBank_account_name("");
		    	relFieldName_applicant.setBank_code("");
		    	
		    	Element insured = new Element();
		    	body.setInsured(insured);
		    	insured.setProdCode(prod.getProdCode());
		    	insured.setRelTbl(apiConfig.ins_reltab);
		    	RelFieldName relFieldName_insured = new RelFieldName();
		    	insured.setRelFieldName(relFieldName_insured);    	
		    	relFieldName_insured.setCertf_type("99");
		    	relFieldName_insured.setCertf_no(policyEntity.getInsuredCompName());
		    	relFieldName_insured.setInsrnt_name(policyEntity.getInsuredOrgCode());
		    	relFieldName_insured.setMobile(policyEntity.getPolicyMobile());
		    	
		    	Element tgtCar = new Element();
		    	body.setTgtCar(tgtCar);
		    	tgtCar.setProdCode(prod.getProdCode());
		    	tgtCar.setRelTbl(apiConfig.tgt_reltab);
		    	RelFieldName relFieldName_tgtCar = new RelFieldName();
		    	tgtCar.setRelFieldName(relFieldName_tgtCar);    	
		    	relFieldName_tgtCar.setLicense_no(policyEntity.getPlateNo());
		    	relFieldName_tgtCar.setFrame_no(policyEntity.getFrameNo());
		    	relFieldName_tgtCar.setEngine_no(policyEntity.getEngineNo());
		    	relFieldName_tgtCar.setSeat_num("5");
		    	relFieldName_tgtCar.setToncount("4.5");	    	
		    	List limit = new ArrayList();
		    	body.setLimit(limit);
		    	
		    	Map package_map = new HashMap();
		    	package_map.put("PACKAGE", package_);
		    	String json = gson.toJson(package_map);	    
		    	try {
			    	String response = HttpClientUtil.httpPostRequest(apiConfig.API_URL, json);	
			    	Map mapRes = gson.fromJson(response, Map.class);
			    	if(mapRes.get("RESPONSE_BODY") != null) {
			    		Map resBody = (Map)mapRes.get("RESPONSE_BODY");
			    		Map resData = (Map)resBody.get("data");
			    		
			    		String updSql = "update wb_insurance_product set proposal_no=?, order_no=? where id=?";
			    		
			    		String proposalNo = (String)resData.get("proposalNo");
			    		String orderNo = (String)resData.get("orderNo");	    		
			    		int updCnt = super.executeSql(updSql, proposalNo, orderNo, policyEntity.getId());
			    		if(updCnt > 0) {
			    			//
				    		Map<String, String> r1 = new HashMap<String, String>();
				    		r1.put("id", policyEntity.getId());
				    		r1.put("proposalNo", proposalNo);
				    		r1.put("orderNo", orderNo);
				    		result.add(r1);
			    		}else {
			    			
			    		}
			    	}else {
			    		//接口返回的数据里没有RESPONSE_BODY
			    	}
		    	}catch(Exception e) {
		    		//调用远程接口或者解析接口出错，该保单不列入核保单
		    	}
			}catch(Exception e1) {
				//构造接口请求数据出错
			}	    	
		}
		
		return result;
	}
}
