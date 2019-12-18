package com.weibao.goodtrans.service;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weibao.goodtrans.entity.FreightPolicyEntity;
import com.weibao.goodtrans.page.FreightPolicyPage;

@Service("freightService")
@Transactional
public class FreightServiceImpl extends CommonServiceImpl implements FreightServiceI {
	private static final Logger logger = Logger.getLogger(FreightServiceImpl.class);
	
	/**
	 * 新增保存货运险保单、投保人、被保人、法人、货物等信息
	 */
	@Override
	public FreightPolicyEntity addMain(FreightPolicyPage freightPolicyPage) {
		FreightPolicyEntity freight = null;
		
		try {
			
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return freight;
	}
	
	/**
	 * 修改保存货运险保单、投保人、被保人、法人、货物等信息
	 */
	@Override
	public FreightPolicyEntity updateMain(FreightPolicyPage freightPolicyPage) {
		FreightPolicyEntity freight = null;
		
		try {
			
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return freight;
	}
}
