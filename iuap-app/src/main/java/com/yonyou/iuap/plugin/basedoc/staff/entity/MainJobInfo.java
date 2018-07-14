package com.yonyou.iuap.plugin.basedoc.staff.entity;

import java.util.Date;

import javax.persistence.Table;

@Table(name = "bd_main_job")
@SuppressWarnings("all")
public class MainJobInfo {

	private static final long serialVersionUID = -949745434650315264L;

	//主键ID
	private String id;
	//"所属人员"
	private String staffid;
	//"所属组织"
	private String orgid;
	//"所属部门"
	private String deptid;
	//"人员类别"
	private String psnlcatgid;
	//"职务"
	private String dutyid;
	//"职位"
	private String positionid;
	//"职级"
	private String rankid;
	//"是否主职"
	private Boolean mainjob;
	//"任职开始时间"
	private Date startservetime;
	//"任职结束时间"
	private Date endservetime;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getStaffid() {
		return staffid;
	}
	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getPsnlcatgid() {
		return psnlcatgid;
	}
	public void setPsnlcatgid(String psnlcatgid) {
		this.psnlcatgid = psnlcatgid;
	}

	public String getDutyid() {
		return dutyid;
	}
	public void setDutyid(String dutyid) {
		this.dutyid = dutyid;
	}

	public String getPositionid() {
		return positionid;
	}
	public void setPositionid(String positionid) {
		this.positionid = positionid;
	}

	public String getRankid() {
		return rankid;
	}
	public void setRankid(String rankid) {
		this.rankid = rankid;
	}

	public Boolean getMainjob() {
		return mainjob;
	}
	public void setMainjob(Boolean mainjob) {
		this.mainjob = mainjob;
	}

	public Date getStartservetime() {
		return startservetime;
	}
	public void setStartservetime(Date startservetime) {
		this.startservetime = startservetime;
	}

	public Date getEndservetime() {
		return endservetime;
	}
	public void setEndservetime(Date endservetime) {
		this.endservetime = endservetime;
	}

}