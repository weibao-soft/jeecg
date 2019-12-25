package com.weibao.goodtrans.service;

import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

public interface DongruiApiServiceI extends CommonService {
	/**
	 * 支付成功后的回调处理
	 * @param result
	 */
	public void payback(Map<String, Object> back);
}
