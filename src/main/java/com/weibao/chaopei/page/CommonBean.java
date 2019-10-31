package com.weibao.chaopei.page;

import java.io.Serializable;

/**
 * 公共组件属性
 */
public class CommonBean implements Serializable {
	private static final long serialVersionUID = 1L;

	//属性id
	private java.lang.String id;
	
	//属性代码
	private java.lang.String code;
	
	//属性名称
	private java.lang.String name;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}
	
}
