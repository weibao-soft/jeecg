package com.weibao.chaopei.api;

import java.util.Map;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weibao.chaopei.service.GuorenApiServiceI;

@Controller
@RequestMapping("/guoren")
public class GuorenRecvData extends BaseController {
		
	private static final Logger logger = LoggerFactory.getLogger(GuorenRecvData.class);
	@Autowired
	private GuorenApiServiceI guorenApiService;
	
	@ResponseBody
	@RequestMapping(value="payback", method = RequestMethod.POST)
	public ResponseMessage<?> payCallback(@RequestBody Object json) {
		try {
			logger.info("payCallback message:{}", json);			
			Map<String, String> map = (Map<String, String>)json;
			//	修改订单状态为已支付；写入支付时间、写入保单号、生成电子保单
			guorenApiService.payback(map);
			return Result.success();
		}catch(Exception e) {
			logger.error(e.getMessage());
			return Result.error("支付回调处理失败!");
		}	
	}
}
