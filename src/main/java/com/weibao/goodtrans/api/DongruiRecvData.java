package com.weibao.goodtrans.api;

import java.util.Map;

import org.jeecgframework.core.common.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weibao.goodtrans.service.DongruiApiServiceI;

@Controller
@RequestMapping("/dongrui")
public class DongruiRecvData extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DongruiRecvData.class);
	@Autowired
	private DongruiApiServiceI dongruiApiService;
	
	@ResponseBody
	@RequestMapping(value="payback", method = RequestMethod.POST)
	public String payCallback(@RequestBody Object json) {
		try {
			logger.info("payCallback message:{}", json);			
			Map<String, Object> map = (Map<String, Object>)json;
			//	修改订单状态为已支付；写入支付时间、写入保单号、生成电子保单
			dongruiApiService.payback(map);
			return "success";
		}catch(Exception e) {
			logger.error("东瑞支付回调处理失败!", e.getMessage(), e);
			return "fail";
		}	
	}
}
