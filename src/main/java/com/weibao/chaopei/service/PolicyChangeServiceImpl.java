package com.weibao.chaopei.service;

import com.weibao.chaopei.dao.PolicyMainDao;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("policyChangeService")
@Transactional
public class PolicyChangeServiceImpl extends CommonServiceImpl implements PolicyChangeServiceI {
	private static final Logger logger = Logger.getLogger(PolicyChangeServiceImpl.class);
	
	@Autowired
	private PolicyMainDao policyMainDao;
}
