package com.yonyou.iuap.pap.plugin.basedoc.staff.entity;

import java.util.Date;
import java.util.List;


public class Staff implements java.io.Serializable {

	private static final long serialVersionUID = 5653752865155224688L;
  
	//"主键"
	private String id;
	
	//"编码"
	private String code;
	
	//"名称"
	private String name;
	
	//"上级节点主键"
	private String parentid;
	
	//"创建者"
	private String creator;
	
	//"内部码"
	private String innercode;
	
	//"创建时间"
	private Date creationtime;
	
	//"修改人"
	private String modifier;
	
	//"修改时间"
	private Date modifiedtime;
	
	//"租户标识"
	private String tenantid;
	
	//"系统标识"
	private String sysid;

	//"启用状态"
	private Integer enable;
	
	//"时间戳"
	private Date ts;
	
	//"版本号"
	private Integer dr;
	
	//"头像"
	private String avator;

	//"证件类型"
	private String certtypeid;

	//"证件号"
	private String certnum;

	//"性别"
	private String gender;

	//"出生日期"
	private Date birthdate;

	//"婚姻状况"
	private String maritalstatus;

	//"学历"
	private String educationbg;

	//"邮箱"
	private String email;

	//"手机号"
	private String mobile;

	//"参加工作日期"
	private Date participateworkdate;

	//"关联用户"
	private String userid;

	//"主职信息"
	private List<MainJobInfo> mainjobinfo;

	//"兼职信息"
	//private List<PtJobInfo> ptjobinfo;

	//"银行账户"
	//private List<BankAcct> bankacct;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getInnercode() {
		return innercode;
	}

	public void setInnercode(String innercode) {
		this.innercode = innercode;
	}

	public Date getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Date creationtime) {
		this.creationtime = creationtime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModifiedtime() {
		return modifiedtime;
	}

	public void setModifiedtime(Date modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public String getAvator() {
		return avator;
	}

	public void setAvator(String avator) {
		this.avator = avator;
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<MainJobInfo> getMainjobinfo() {
		return mainjobinfo;
	}

	public void setMainjobinfo(List<MainJobInfo> mainjobinfo) {
		this.mainjobinfo = mainjobinfo;
	}

}