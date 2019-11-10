package com.weibao.chaopei.page;

import java.util.ArrayList;
import java.util.List;

import com.weibao.chaopei.entity.ProductDetailEntity;

public class ProductMainPage implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -724566306691255205L;
	private java.lang.String id;
	private java.lang.String name;
	private java.lang.String title;
	
	private List<ProductDetailEntity> productDetailsList = new ArrayList<ProductDetailEntity>();

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public List<ProductDetailEntity> getProductDetailsList() {
		return productDetailsList;
	}

	public void setProductDetailsList(List<ProductDetailEntity> productDetailsList) {
		this.productDetailsList = productDetailsList;
	}		
}
