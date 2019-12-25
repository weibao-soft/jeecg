package com.weibao.goodtrans.service;

import java.util.Map;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ishdr.pay.utils.AesUtil;
import com.weibao.common.util.IshdrPayUtil;


@Service("dongruiApiService")
@Transactional
public class DongruiApiServiceImpl extends CommonServiceImpl implements DongruiApiServiceI {
	private static final Logger logger = LoggerFactory.getLogger(DongruiApiServiceImpl.class);

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
