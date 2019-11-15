package com.weibao.chaopei.service;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import com.weibao.chaopei.entity.PolicyEntity;

public interface GuorenApiServiceI extends CommonService  {
	
	/**
	 * 核保接口，根据传入的保单List，组装成国任核保接口要求的数据，调用核保接口，然后解析得到投保单号、订单号，写回policyEntityList
	 * @param policyEntityList 保单list
	 * @return result list里面是包含多个map对象，map对象包含：保单id、投保单号、订单号
	 */
	public List<Map<String, String>> insuredService(List<PolicyEntity> policyEntityList);
	
	/**
	 * 支付接口，根据传入的保单List，组装成国任支付接口要求的数据，调用支付接口，返回支付页面链接与支付订单号
	 * @param policyEntityList
	 * @return map {"data": "https://devyun.guorenpcic.com/paycenter/?orderId=2800d9bc23743a89711&code=&payOrderNo=js02&platform=pc", "payorderId": "201911141765037469GUORENSCPC"}
	 */
	public Map<String, String> payService(List<PolicyEntity> policyEntityList);
	
}
