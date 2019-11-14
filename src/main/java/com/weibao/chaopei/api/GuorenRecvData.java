package com.weibao.chaopei.api;

import java.util.Map;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/guoren")
public class GuorenRecvData extends BaseController {
		
	private Gson gson = new GsonBuilder().create();
	
	private static final Logger logger = LoggerFactory.getLogger(GuorenRecvData.class);
	
	@ResponseBody
	@RequestMapping(value="payback", method = RequestMethod.POST)
	public ResponseMessage<?> payCallback(@RequestBody Object json) {
		try {
			logger.info("payCallback message:{}", json);
			Map map = (Map)json;
			String jsonStr = gson.toJson(json);
			//	修改订单状态为已支付；写入支付时间、写入保单号、生成电子保单
			return Result.success();
		}catch(Exception e) {
			logger.error(e.getMessage());
			return Result.error("支付回调处理失败!");
		}	
	}
}
