package com.yonyou.iuap.pap.plugin.basedoc.user.api.vo;

import java.util.Date;

public class SyncUser {
	
	private String userAccount;				//用户帐号、登录名
	private String userName;				//用户名称，用于显示
	private String password;
	private String type;					//
	private String phone;					//电话号码
	private String email;					//电子邮件
	private String islock;					//是否锁定
	private String remark;					//备注
	private Date createDate;				//创建时间
	private Date modifyDate;				//修改时间
	private String modifyPerson;
	private String organizationCode;		//所属组织机构编码 | 供应商编码

	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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

	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

}