package com.lbq.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建信息
 * @author 14378
 *
 */
public class CreateInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4810528227483556251L;

	private String createBy;
	
	private Date createTime;

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "CreateInfo [createBy=" + createBy + ", createTime=" + createTime + "]";
	}
	
	
}
