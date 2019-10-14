package com.weibao.chaopei.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 *机构-产品分配关系实体
 *
 */
@Entity
@Table(name = "wb_depart_product_rel")
public class DepartProductRefEntity implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4393233078178318082L;
	
	/**主键*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")	
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String id;
	@Column(name ="depart_id",nullable=true,length=32)
	private String departId;
	@Column(name ="product_detail_id",nullable=true,length=32)
    private String productDetailId;
	@Column(name ="assign_status",nullable=true,length=1)
    private String assignStatus;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	public String getProductDetailId() {
		return productDetailId;
	}
	public void setProductDetailId(String productDetailId) {
		this.productDetailId = productDetailId;
	}
	public String getAssignStatus() {
		return assignStatus;
	}
	public void setAssignStatus(String assignStatus) {
		this.assignStatus = assignStatus;
	}
	
}
