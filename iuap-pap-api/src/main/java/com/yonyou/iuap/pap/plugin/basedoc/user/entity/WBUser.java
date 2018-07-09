package com.yonyou.iuap.pap.plugin.basedoc.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class WBUser {

	@Id
	@GeneratedValue()
	@Column(name = "id")
	private String id;
	private String loginName;
	private String name;
	private String password;
	private String salt;
	private String roles;
	private String states;
	private Date registerDate;
	private String tenantId;
	private String avator;
	private String type;
	private String phone;
	private String email;
	private String img;
	private String islock;
	private String remark;
	private Date createDate;
	private String createPerson;
	private Date modifyDate;
	private String modifyPerson;
	private String organizationId;
	private String organizationName;
	private String groupId;
	private String groupName;
	private int dr = 0;
	private String ts;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}
	
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public String getAvator() {
		return avator;
	}
	public void setAvator(String avator) {
		this.avator = avator;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	public String getIslock() {
		return islock;
	}
	public void setIslock(String islock) {
		this.islock = islock;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public String getModifyPerson() {
		return modifyPerson;
	}
	public void setModifyPerson(String modifyPerson) {
		this.modifyPerson = modifyPerson;
	}
	
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public int getDr() {
		return dr;
	}
	public void setDr(int dr) {
		this.dr = dr;
	}
	
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}

}