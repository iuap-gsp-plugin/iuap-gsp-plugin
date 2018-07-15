package com.yonyou.iuap.pap.plugin.basedoc.staff.api.vo;

import java.util.Date;

public class SyncMainJob {
	
	private String orgCode;					//所属组织编号
	private String deptCode;				//所属部门编号
	private String positionCode;			//岗位编号
	private Date startservetime;			//任职开始时间
	private Date endservetime;				//任职结束时间
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
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