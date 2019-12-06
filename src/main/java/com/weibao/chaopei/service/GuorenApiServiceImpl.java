package com.weibao.chaopei.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.web.system.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class GuorenApiServiceImpl extends CommonServiceImpl implements GuorenApiServiceI   {
	@Autowired
	GuorenApiConfig apiConfig;
	
	private static final Logger logger = LoggerFactory.getLogger(GuorenApiServiceImpl.class);
	
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
		    	relFieldName_insured.setCertf_no(policyEntity.getInsuredOrgCode());
		    	relFieldName_insured.setInsrnt_name(policyEntity.getInsuredCompName());
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
		    	relFieldName_tgtCar.setSeat_num(apiConfig.tgt_carSeatNum);
		    	relFieldName_tgtCar.setToncount(apiConfig.tgt_carTonCount);	    	
		    	List limit = new ArrayList();
		    	body.setLimit(limit);
		    	
		    	Map package_map = new HashMap();
		    	package_map.put("PACKAGE", package_);
		    	String json = gson.toJson(package_map);
		    	String response = "";
		    	try {
			    	response = HttpClientUtil.httpPostRequest(apiConfig.API_URL, json);	
			    	logger.info("request json::"+json);
			    	Map mapRes = gson.fromJson(response, Map.class);
			    	if(mapRes.get("RESPONSE_BODY") != null) {
			    		Map resBody = (Map)mapRes.get("RESPONSE_BODY");
			    					    		
			    		Double resultCode = (Double)resBody.get("resultCode");
		    			String sresultCode = String.valueOf(resultCode.intValue());		    					    			
		    			if("0".equals(sresultCode)) {
				    		Map resData = (Map)resBody.get("data");
				    		
				    		String updSql = "update wb_insurance_policy set proposal_no=?, order_no=? where id=?";
				    		
				    		String proposalNo = (String)resData.get("proposalNo");
				    		String orderNo = (String)resData.get("orderNo");	    		
				    		int updCnt = super.executeSql(updSql, proposalNo, orderNo, policyEntity.getId());
				    		if(updCnt > 0) {
				    			//
					    		Map<String, String> r1 = new HashMap<String, String>();
					    		r1.put("resultCode", sresultCode);
					    		r1.put("id", policyEntity.getId());
					    		r1.put("proposalNo", proposalNo);
					    		r1.put("orderNo", orderNo);
					    		r1.put("policyMobile", policyEntity.getPolicyMobile());
					    		result.add(r1);
				    		}else {
				    			logger.error("updCnt is 0000!!!");
				    		}
		    			}else {
		    				Map<String, String> r1 = new HashMap<String, String>();
		    				r1.put("resultCode", sresultCode);
		    				r1.put("resultMsg", (String)resBody.get("resultMsg"));
		    				result.add(r1);
		    			}
			    	}else {
			    		//接口返回的数据里没有RESPONSE_BODY
			    		logger.error("responseBody from insured is null, request json::"+json);
			    		logger.error("response from insured::"+response);
			    	}
		    	}catch(Exception e) {
		    		//调用远程接口或者解析接口出错，该保单不列入核保单
		    		logger.error("Error in response::"+response);
		    		e.printStackTrace();
		    	}
			}catch(Exception e1) {
				//构造接口请求数据出错
				e1.printStackTrace();
			}
		}
		
		return result;
	}
	@Override
	public Map<String, String> payService(List<PolicyEntity> policyEntityList) {
		
		Map<String, String> result = new HashMap<String, String>();
		try {
			String uuid = "";
			if(policyEntityList != null && policyEntityList.size() > 0) {
				PolicyEntity entity = policyEntityList.get(0);
				uuid = entity.getId();
					
				ApiPackage package_ = new ApiPackage();
		    	Head head = new Head();
		    	head.setCLIENT_ENCODE(apiConfig.CLIENT_ENCODE);
		    	head.setSERVE_CODE(apiConfig.SERVE_CODE);    	
		    	head.setCHANNEL_CODE(apiConfig.CHANNEL_CODE);
		    	if(policyEntityList.size() > 1) {
		    		head.setINTERFACE_CODE(apiConfig.INTERFACE_CODE_BATCH_PAY);//批量支付接口
		    	}else {
		    		head.setINTERFACE_CODE(apiConfig.INTERFACE_CODE_PAY);//单个支付接口
		    	}
		    	head.setINTERFACE_USER_CODE(apiConfig.INTERFACE_USER_CODE);
		    	head.setINTERFACE_PWD(apiConfig.INTERFACE_PWD);
		    	head.setBUSINESS_UUID(uuid);
		    	Date now = new Date();	    	
		    	head.setREQUEST_TIME(DateUtils.datetimeFormatSSSS(now));
		    	head.setIFTEST(apiConfig.IFTEST);
		    	head.setXML_LIST_SUFFIX("");
				
		    	package_.setHEAD(head);
		    	Body body = new Body();
		    	package_.setBODY(body);
		    	body.setOperCode(entity.getPolicyMobile());//填录单人员联系方式
		    	String proposalNo = "";
		    	String orderNo = "";
				for (int i=0; i<policyEntityList.size(); i++) {
					PolicyEntity policyEntity = policyEntityList.get(i);	
					proposalNo = proposalNo + policyEntity.getProposalNo() + ":";
					orderNo = orderNo + policyEntity.getOrderNo() + ":";
				}
				proposalNo = proposalNo.substring(0, proposalNo.length()-1);
				orderNo = orderNo.substring(0, orderNo.length()-1);
				body.setProposalNo(proposalNo);
				body.setOrderNo(orderNo);
				body.setPlatform(apiConfig.platform);
				
				Map package_map = new HashMap();
		    	package_map.put("PACKAGE", package_);
		    	String json = gson.toJson(package_map);	    
		    	try {
			    	String response = HttpClientUtil.httpPostRequest(apiConfig.API_URL, json);	
			    	Map mapRes = gson.fromJson(response, Map.class);
			    	if(mapRes.get("RESPONSE_BODY") != null) {
			    		Map resBody = (Map)mapRes.get("RESPONSE_BODY");
			    		Double resultCode = (Double)resBody.get("resultCode");
		    			String sresultCode = String.valueOf(resultCode.intValue());
		    			result.put("resultCode", sresultCode);
			    		String payurl = "";
			    		String payorderId = "";
			    		if(policyEntityList.size() > 1) {
			    			if("0".equals(sresultCode)) {
				    			Map data = (Map)resBody.get("data");
				    			payorderId = (String)data.get("orderId");
				    			payurl = (String)data.get("payUrl");
			    			}else {
			    				result.put("resultMsg", (String)resBody.get("resultMsg"));
			    			}
			    		}else {			    			
			    			if("0".equals(sresultCode)) {
			    				payurl = (String)resBody.get("data");
					    		payorderId = (String)resBody.get("orderId");
			    			}else {
			    				result.put("resultMsg", (String)resBody.get("resultMsg"));
			    			}
			    			
			    		}			    		
			    		result.put("data", payurl);
			    		result.put("payorderId", payorderId);
			    	}else {
			    		//接口返回的数据里没有RESPONSE_BODY
			    		logger.error("responseBody from payService is null, request json::"+json);
			    		logger.error("payService response::"+response);
			    	}
		    	}catch(Exception e) {
		    		//调用远程接口或者解析接口出错，该保单不列入核保单
		    		e.printStackTrace();
		    	}
			}
		}catch(Exception e1) {
			//构造接口请求数据出错
			e1.printStackTrace();
		}
		return result;
	}
	@Override
	public void payback(Map<String, String> back) {
		String result =(String) back.get("result");
		if("0".equals(result)) {
			String proposalNos = back.get("proposalNo");
			String policyNos = back.get("policyNo");
			String orderNo = back.get("orderNoMall");
			String payorderId = back.get("orderId");
			String message = back.get("message");
			String type = back.get("type");
			String signMsg = back.get("signMsg");
			proposalNos = proposalNos.substring(1, proposalNos.length());
			policyNos = policyNos.substring(1, policyNos.length());
			String[] proposalNoArray = proposalNos.split(":");
			String[] policyNoArray = policyNos.split(":");
			for(int i=0; i<proposalNoArray.length; i++) {
				//调接口生成电子保单
				ApiPackage package_ = new ApiPackage();
		    	Head head = new Head();
		    	head.setCLIENT_ENCODE(apiConfig.CLIENT_ENCODE);
		    	head.setSERVE_CODE(apiConfig.SERVE_CODE);    	
		    	head.setCHANNEL_CODE(apiConfig.CHANNEL_CODE);
		    	head.setINTERFACE_CODE(apiConfig.INTERFACE_CODE_POLICY);//生成电子保单接口
		    	head.setINTERFACE_USER_CODE(apiConfig.INTERFACE_USER_CODE);
		    	head.setINTERFACE_PWD(apiConfig.INTERFACE_PWD);
		    	head.setBUSINESS_UUID(proposalNoArray[i]);
		    	Date now = new Date();	    	
		    	head.setREQUEST_TIME(DateUtils.datetimeFormatSSSS(now));
		    	head.setIFTEST(apiConfig.IFTEST);
		    	head.setXML_LIST_SUFFIX("");
		    	
		    	package_.setHEAD(head);
		    	Body body = new Body();
		    	package_.setBODY(body);
		    	body.setPolicyNo(policyNoArray[i]);		
		    	
		    	Map package_map = new HashMap();
		    	package_map.put("PACKAGE", package_);
		    	String json = gson.toJson(package_map);
		    	String policyurl = "#";
		    	try {
			    	try {
				    	String response = HttpClientUtil.httpPostRequest(apiConfig.API_URL, json);	
				    	Map mapRes = gson.fromJson(response, Map.class);
				    	if(mapRes.get("RESPONSE_BODY") != null) {
				    		Map resBody = (Map)mapRes.get("RESPONSE_BODY");
				    		policyurl = (String)resBody.get("data");			    		
				    	}else {
				    		//接口返回的数据里没有RESPONSE_BODY
				    		logger.error("responseBody from generate policy is null, request json::"+json);
				    		logger.error("payback response::"+response);
				    	}
			    	}catch(Exception e) {
			    		//	调用远程接口或者解析接口出错，该保单不列入核保单			    		
			    		e.printStackTrace();
			    	}
			    	//	修改保单状态为已支付；写入支付时间、写入保单号、生成电子保单、修改电子保单url
					String updSql = "update wb_insurance_policy set status=3, pay_status=1, pay_time=SYSDATE(), policy_no=?, policy_url=? where proposal_no=? and pay_status=0";
					int updCnt = super.executeSql(updSql, policyNoArray[i], policyurl, proposalNoArray[i]);
		    		if(updCnt < 1) {
		    			logger.error("update count is 0000!!!");
		    		}
		    	}catch(Exception e1) {
		    		e1.printStackTrace();
		    		//1个保单没有处理有问题，继续处理下一个保单
		    	}
		    	
			}
		}
		
	}
}
