package com.yonyou.iuap.plugin.basedoc.staff.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Table;

import com.yonyou.iuap.baseservice.entity.AbsModel;
import com.yonyou.iuap.baseservice.entity.Model;

@Table(name="bd_staff")
@SuppressWarnings("all")
public class Staff {

	private String id;						//主键	
	private String code;					//编码	
	private String name;					//名称	
	private String parentid;				//上级节点主键	
	private String creator;					//创建者	
	private String innercode;				//内部码	
	private String avator;					//头像	
	private String certtypeid;				//证件类型	
	private String certnum;					//证件号	
	private String gender;					//性别
	private Date birthdate;					//出生日期	
	private String maritalstatus;			//婚姻状况	
	private String educationbg;				//学历	
	private String email;					//邮箱	
	private String mobile;					//手机号	
	private Date participateworkdate;		//参加工作日期	
	private String userid;					//关联用户
	private String tenantid;				//租户标识	
	private String sysid;					//系统标识
	private Integer enable;					//启用状态	
	private Date creationtime;				//创建时间	
	private String modifier;				//修改人	
	private Date modifiedtime;				//修改时间	
	private Date ts;						//时间戳	
	private Date newTs;						//新时间戳--版本号
	private Integer dr;	
	private List<MainJobInfo> mainjobinfo;	//主职信息
	private List<PtJobInfo> ptjobinfo;		//兼职信息
	private List<BankAcct> bankacct;		//银行账户

	
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

	public List<MainJobInfo> getMainjobinfo() {
		return mainjobinfo;
	}
	public void setMainjobinfo(List<MainJobInfo> mainjobinfo) {
		this.mainjobinfo = mainjobinfo;
	}

	public List<PtJobInfo> getPtjobinfo() {
		return ptjobinfo;
	}
	public void setPtjobinfo(List<PtJobInfo> ptjobinfo) {
		this.ptjobinfo = ptjobinfo;
	}

	public List<BankAcct> getBankacct() {
		return bankacct;
	}
	public void setBankacct(List<BankAcct> bankacct) {
		this.bankacct = bankacct;
	}
	
	public Date getNewTs() {
		return newTs;
	}
	public void setNewTs(Date newTs) {
		this.newTs = newTs;
	}
	
}