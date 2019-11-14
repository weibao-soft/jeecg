package com.weibao.chaopei.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.web.system.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.util.http.ApiPackage;
import com.weibao.chaopei.util.http.Body;
import com.weibao.chaopei.util.http.Element;
import com.weibao.chaopei.util.http.GuorenApiConfig;
import com.weibao.chaopei.util.http.Head;
import com.weibao.chaopei.util.http.RelFieldName;

@Service("guorenApiService")
@Transactional
public class GuorenApiService {
	@Autowired
	GuorenApiConfig apiConfig;
	
	Gson gson = new GsonBuilder().create();	
	/**
	 * 核保接口，根据传入的保单List，组装成国任核保接口要求的数据，调用核保接口，然后解析得到投保单号、订单号，写回policyEntityList
	 * @param policyEntityList
	 */
	public void insuredService(List<PolicyEntity> policyEntityList) {
		
		for (PolicyEntity policyEntity : policyEntityList) {
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
	    	body.setPlan_code(policyEntity.getPlanId());//TODO
	    	body.setPlan_name(policyEntity.getPlanId());//TODO
	    	body.setGood_code(policyEntity.getProdId());
	    	body.setGood_name("国任升华茂林物流责任险");
	    	body.setAppnum("1");
	    	
	    	Element base = new Element();
	    	body.setBase(base);
	    	base.setProdCode("1531");
	    	base.setRelTbl("ins_appl_main");
	    	RelFieldName relFieldName_base = new RelFieldName();
	    	base.setRelFieldName(relFieldName_base);
	    	relFieldName_base.setPlcy_bgn_time("2019-11-30");
	    	relFieldName_base.setPlcy_end_time("2019-11-29");
	    	relFieldName_base.setCall_back_url("http://yundmz.pcic:90/jcx-host/guoren/guorenrecvData.ashx");
	    	
	    	Element applicant = new Element();
	    	body.setApplicant(applicant);
	    	applicant.setProdCode("1531");
	    	applicant.setRelTbl("ins_appl_applnt");
	    	RelFieldName relFieldName_applicant = new RelFieldName();
	    	applicant.setRelFieldName(relFieldName_applicant);
	    	relFieldName_applicant.setAppl_name("东莞市大朗超捷货运服务部");
	    	relFieldName_applicant.setClnt_type("2");
	    	relFieldName_applicant.setCertf_type("99");
	    	relFieldName_applicant.setCertf_no("92441900L06692572F");
	    	relFieldName_applicant.setEmail("");
	    	relFieldName_applicant.setMobile("13826300503");
	    	relFieldName_applicant.setBank_account("");
	    	relFieldName_applicant.setBank_account_name("");
	    	relFieldName_applicant.setBank_code("");
	    	
	    	Element insured = new Element();
	    	body.setInsured(insured);
	    	insured.setProdCode("1531");
	    	insured.setRelTbl("ins_appl_insrnt");
	    	RelFieldName relFieldName_insured = new RelFieldName();
	    	insured.setRelFieldName(relFieldName_insured);    	
	    	relFieldName_insured.setCertf_type("99");
	    	relFieldName_insured.setCertf_no("92441900L06692572F");
	    	relFieldName_insured.setInsrnt_name("东莞市大朗超捷货运服务部");
	    	relFieldName_insured.setMobile("13826300503");
	    	
	    	Element tgtCar = new Element();
	    	body.setTgtCar(tgtCar);
	    	tgtCar.setProdCode("1531");
	    	tgtCar.setRelTbl("ins_appl_tgt_car");
	    	RelFieldName relFieldName_tgtCar = new RelFieldName();
	    	tgtCar.setRelFieldName(relFieldName_tgtCar);    	
	    	relFieldName_tgtCar.setLicense_no("粤S98684");
	    	relFieldName_tgtCar.setFrame_no("LGGR2BG42CL714501");
	    	relFieldName_tgtCar.setEngine_no("J64L1C00023");
	    	relFieldName_tgtCar.setSeat_num("5");
	    	relFieldName_tgtCar.setToncount("4.5");
	    	
	    	List limit = new ArrayList();
	    	body.setLimit(limit);
	    	
	    	Map package_map = new HashMap();
	    	package_map.put("PACKAGE", package_);
	    	String json = gson.toJson(package_map);
	    	
	    	String response = HttpClientUtil.httpPostRequest(apiConfig.API_URL, json);
			
		}
	}
}
