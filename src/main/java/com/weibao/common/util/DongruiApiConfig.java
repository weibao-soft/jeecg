package com.weibao.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:dongrui_api.properties"})
public class DongruiApiConfig {
	//md5加密Key
	@Value("${MD5_KEY}")
	public String MD5_KEY = "83JD89UER-WEIBAO";
	//支付加密Key
	@Value("${PAY_AES_KEY}")
	public String PAY_AES_KEY = "EF6AD27F389B01A247CD5A12F8E08EA5";
	//退款加密Key
	@Value("${REFUND_AES_KEY}")
	public String REFUND_AES_KEY = "EO6AD27F389X01A247CD5A12F8E021K1";
	//账号
	@Value("${WEIBAO}")
	public String WEIBAO = "WEIBAO";

}
