package com.lbq.mybatis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3222843904349310362L;
	private Long id;
	private String roleName;
//	private Integer enabled;
//	private Long createBy;
//	private Date createTime;
	private SysUser user;
	private CreateInfo createInfo;
	private List<SysPrivilege> privilegeList;
	private Enabled enabled;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
//	public Integer getEnabled() {
//		return enabled;
//	}
//	public void setEnabled(Integer enabled) {
//		this.enabled = enabled;
//	}
//	public Long getCreateBy() {
//		return createBy;
//	}
//	public void setCreateBy(Long createBy) {
//		this.createBy = createBy;
//	}
//	public Date getCreateTime() {
//		return createTime;
//	}
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
	public List<SysPrivilege> getPrivilegeList() {
		return privilegeList;
	}
	public void setPrivilegeList(List<SysPrivilege> privilegeList) {
		this.privilegeList = privilegeList;
	}
//	@Override
//	public String toString() {
//		return "SysRole [id=" + id + ", roleName=" + roleName + ", enabled=" + enabled + ", createBy=" + createBy
//				+ ", createTime=" + createTime + ", user=" + user + ", privilegeList=" + privilegeList + "]";
//	}
	public CreateInfo getCreateInfo() {
		return createInfo;
	}
	public void setCreateInfo(CreateInfo createInfo) {
		this.createInfo = createInfo;
	}
	public Enabled getEnabled() {
		return enabled;
	}
	public void setEnabled(Enabled enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "SysRole [id=" + id + ", roleName=" + roleName + ", enabled=" + enabled + ", user=" + user
				+ ", createInfo=" + createInfo + ", privilegeList=" + privilegeList + "]";
	}
	
}
