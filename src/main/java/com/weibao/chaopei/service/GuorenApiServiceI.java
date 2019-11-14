package com.weibao.chaopei.service;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weibao.chaopei.entity.PolicyEntity;

public interface GuorenApiServiceI extends CommonService  {
	
	Gson gson = new GsonBuilder().create();	
	/**
	 * 核保接口，根据传入的保单List，组装成国任核保接口要求的数据，调用核保接口，然后解析得到投保单号、订单号，写回policyEntityList
	 * @param policyEntityList 保单list
	 * @return result list里面是一个map对象，map对象包含：保单id、投保单号、订单号
	 */
	public List<Map<String, String>> insuredService(List<PolicyEntity> policyEntityList);
}
