package com.weibao.chaopei.service;

import org.jeecgframework.core.common.service.CommonService;

import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.page.PolicyMainPage;

public interface PolicyServiceI extends CommonService {
	public PolicyMainPage addMain(PolicyMainPage policyMainPage);
}
