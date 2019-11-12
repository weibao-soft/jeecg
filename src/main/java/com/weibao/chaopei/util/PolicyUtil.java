package com.weibao.chaopei.util;

import java.lang.reflect.Field;

import javax.persistence.Column;

import org.apache.log4j.Logger;

import com.weibao.chaopei.entity.CompanyRewardedDetailEntity;
import com.weibao.chaopei.entity.PolicyEntity;
import com.weibao.chaopei.entity.ProductDetailEntity;
import com.weibao.chaopei.entity.ProductEntity;

public class PolicyUtil {
	private static final Logger logger = Logger.getLogger(PolicyUtil.class);
	
	/**
	 * 根据实体Bean的属性名获取字段名称
	 * @param propName
	 * @return
	 */
	public static String getColumnName(String propName) {
		String column1 = null;
		boolean hasField = false;
		try {
			Field field = PolicyEntity.class.getDeclaredField(propName);
			Column column = field.getAnnotation(Column.class);
			column1 = column.name();
			hasField = true;
		} catch (NoSuchFieldException e) {
			//logger.error(e);
		}
		try {
			if(!hasField) {
				Field field = ProductEntity.class.getDeclaredField(propName);
				Column column = field.getAnnotation(Column.class);
				column1 = column.name();
				hasField = true;
			}
		} catch (NoSuchFieldException e) {
			//logger.error(e);
		}
		try {
			if(!hasField) {
				Field field = ProductDetailEntity.class.getDeclaredField(propName);
				Column column = field.getAnnotation(Column.class);
				column1 = column.name();
				hasField = true;
			}
		} catch (NoSuchFieldException e) {
			//logger.error(e);
		}
		try {
			if(!hasField) {
				Field field = CompanyRewardedDetailEntity.class.getDeclaredField(propName);
				Column column = field.getAnnotation(Column.class);
				column1 = column.name();
				hasField = true;
			}
		} catch (NoSuchFieldException e) {
			logger.error(e);
		}
		if(!hasField && "userName".equals(propName)) {
			column1 = "realname";
		}
		return column1;
	}
}
