package com.weibao.goodtrans.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ishdr.pay.model.PayModel;
import com.ishdr.pay.utils.AesUtil;
import com.ishdr.pay.utils.PayClient;
import com.weibao.common.util.DongruiApiConfig;
import com.weibao.common.util.IshdrPayUtil;
import com.weibao.goodtrans.entity.FreightPolicyEntity;


@Service("dongruiApiService")
@Transactional
public class DongruiApiServiceImpl extends CommonServiceImpl implements DongruiApiServiceI {
	private static final Logger logger = LoggerFactory.getLogger(DongruiApiServiceImpl.class);

	@Autowired
	DongruiApiConfig apiConfig;
	
	//东瑞支付客户端
    private PayClient payClient = null;
    
    private PayClient getPayClient() {
    	if(payClient == null) {
    		return new PayClient(apiConfig.MD5_KEY, apiConfig.PAY_AES_KEY, apiConfig.REFUND_AES_KEY);
    	} else {
    		return payClient;
    	}
    }
	
	/**
	 * 调用永安货运险支付接口
	 * @param policy
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public String freightPolicyPay(FreightPolicyEntity policy) 
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
        PayModel payModel = new PayModel();
        //账号
        payModel.setPartnerCode(IshdrPayUtil.WEIBAO);
        //商品
        payModel.setSubject(policy.getGoodsName());
        BigDecimal premium = policy.getAllPremium().multiply(new BigDecimal(100));
        int price = premium.intValue();
        //保费
        payModel.setPrice(price);
        String partnerTransCode = policy.getId();
        //微保订单号
        payModel.setPartnerTransCode(partnerTransCode);
        System.err.println(partnerTransCode);
        String payUrl = getPayClient().getPayUrl(payModel);
        return payUrl;
	}

	/**
	 * 支付成功后的回调处理
	 * @param result
	 */
	public void payback(Map<String, Object> back) {
		//合作伙伴代码
		String partnerCode = (String)back.get("PartnerCode");
		//支付平台
		String payPlat = (String)back.get("PayPlat");
		//合作伙伴订单号
		String partnerTransCode = (String)back.get("PartnerTransCode");
		//支付平台订单号
		String outTradeNo = (String)back.get("OutTradeNo");
		//金额
		Integer totalFee = (Integer)back.get("TotalFee");
		//签名
		String sign = (String)back.get("Sign");
		//拼接验签字符串（合作伙伴code+合作伙伴订单号+支付平台订单号+金额+md5加密字符），使用支付密钥签名
		String unencryptedSign = (new StringBuilder()).append(partnerCode).append(partnerTransCode)
				.append(outTradeNo).append(totalFee.toString()).append(IshdrPayUtil.MD5_KEY).toString();
        String encryptSign = AesUtil.encryptByEcbPkcs7Padding(unencryptedSign, IshdrPayUtil.PAY_AES_KEY);
        if(!encryptSign.equals(sign)) {
            throw new BusinessException("验签失败，签名不正确");
        } else {
        	logger.info("订单：{0}验签通过", outTradeNo);

	    	//	修改保单状态为已支付；写入支付时间、写入保单号、生成电子保单、修改电子保单url
			String updSql = "update wb_freight_insurance_policy set status='3', pay_status='1', pay_time=SYSDATE(), order_no=? where id=? and pay_status='0'";
			int updCnt = super.executeSql(updSql, outTradeNo, partnerTransCode);
    		if(updCnt < 1) {
    			logger.error("update count is 0000!!!");
    		}
        }
	}
}
