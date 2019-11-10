package org.jeecgframework.web.system.sms.util.task;

import org.jeecgframework.web.system.sms.service.TSSmsServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weibao.chaopei.task.BasicTask;



/**
 * 
 * @ClassName:SmsSendTask 
 * @Description: 消息推送定时任务
 * @date 2014-11-13 下午5:06:34
 * 
 */
@Service("smsSendTask")
public class SmsSendTask extends BasicTask{
	
	@Autowired
	private TSSmsServiceI tSSmsService; 
	
	@Override
	public void run() {
		long start = System.currentTimeMillis();
		org.jeecgframework.core.util.LogUtil.info("===================推送消息定时任务开始===================");
		try {			
			tSSmsService.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		org.jeecgframework.core.util.LogUtil.info("===================推送消息定时任务结束===================");
		long end = System.currentTimeMillis();
		long times = end - start;
		org.jeecgframework.core.util.LogUtil.info("总耗时"+times+"毫秒");
	}

		
}
