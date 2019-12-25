package com.weibao.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

import com.ishdr.pay.model.PayModel;
import com.ishdr.pay.utils.PayClient;
import com.weibao.goodtrans.entity.FreightPolicyEntity;

/**
 * 支付工具类
 * @author dms
 *
 */
public class IshdrPayUtil {
	//md5加密Key
	public static String MD5_KEY = "83JD89UER-WEIBAO";
	//支付加密Key
	public static String PAY_AES_KEY = "EF6AD27F389B01A247CD5A12F8E08EA5";
	//退款加密Key
	public static String REFUND_AES_KEY = "EO6AD27F389X01A247CD5A12F8E021K1";
	//账号
	public static String WEIBAO = "WEIBAO";
	
	//调用永安货运险支付接口
	public static String freightPolicyPay(FreightPolicyEntity policy) 
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
        PayClient payClient = new PayClient(MD5_KEY, PAY_AES_KEY, REFUND_AES_KEY);
        PayModel payModel = new PayModel();
        payModel.setSubject(policy.getGoodsName());
        BigDecimal premium = policy.getAllPremium().multiply(new BigDecimal(100));
        int price = premium.intValue();
        payModel.setPrice(price);
        payModel.setPartnerCode(WEIBAO);
        String partnerTransCode = policy.getId();
        System.err.println(partnerTransCode);
        payModel.setPartnerTransCode(partnerTransCode);
        String payUrl = payClient.getPayUrl(payModel);
        return payUrl;
	}
}
