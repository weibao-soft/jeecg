package com.weibao.goodtrans.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import com.weibao.goodtrans.entity.FreightPolicyEntity;

public interface DongruiApiServiceI extends CommonService {
	/**
	 * 调用永安货运险支付接口
	 * @param policy
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public String freightPolicyPay(FreightPolicyEntity policy) 
			throws UnsupportedEncodingException, NoSuchAlgorithmException;
	/**
	 * 支付成功后的回调处理
	 * @param result
	 */
	public void payback(Map<String, Object> back);
}
