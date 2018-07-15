package com.yonyou.iuap.pap.plugin.basedoc.staff.api.vo;

import java.util.Date;

public class SyncStaff {

	
	private String code;						//编码	
	private String name;						//名称
	//上级节点主键
	//private String parentCode;	
	private String certtypeid;					//证件类型	
	private String certnum;						//证件号	
	private String gender;						//性别	
	private Date birthdate;						//出生日期	
	private String maritalstatus;				//婚姻状况	
	private String educationbg;					//学历	
	private String email;						//邮箱	
	private String mobile;						//手机号	
	private Date participateworkdate;			//参加工作日期	
	private Integer enable;						//启用状态	
	private SyncMainJob syncMainJob;			//主职信息
	//关联用户:登录用户名：三一为域账号
	private String userCode;	

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCerttypeid() {
		return certtypeid;
	}
	public void setCerttypeid(String certtypeid) {
		this.certtypeid = certtypeid;
	}

	public String getCertnum() {
		return certnum;
	}
	public void setCertnum(String certnum) {
		this.certnum = certnum;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getMaritalstatus() {
		return maritalstatus;
	}
	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public String getEducationbg() {
		return educationbg;
	}
	public void setEducationbg(String educationbg) {
		this.educationbg = educationbg;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getParticipateworkdate() {
		return participateworkdate;
	}
	public void setParticipateworkdate(Date participateworkdate) {
		this.participateworkdate = participateworkdate;
	}

	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public SyncMainJob getSyncMainJob() {
		return syncMainJob;
	}
	public void setSyncMainJob(SyncMainJob syncMainJob) {
		this.syncMainJob = syncMainJob;
	}

	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}