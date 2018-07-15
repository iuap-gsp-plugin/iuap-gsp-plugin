package com.yonyou.iuap.plugin.basedoc.staff.entity;

import javax.persistence.Table;

@Table(name = "bd_bank_acct")
@SuppressWarnings("all")
public class BankAcct {

	private static final long serialVersionUID = -5180527576480354410L;

	//主键id
	private String id;
	//"银行信息"
	private String bank;
	//"银行账号"
	private String account;
	//"开户行名称"
	private String bankname;
	//"账号类型"
	private String accttype;
	//"人员主键"
	private String staffid;
	//"业务单元主键"
	private String buid;
	//"记录序号"
	private Integer recordnum;
	//"最新记录标志"
	private Boolean lastflag;

	public String getId() {
		return this.id;
	}
	public void setBankacct(String id) {
		this.id = id;
	}

	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getAccttype() {
		return accttype;
	}
	public void setAccttype(String accttype) {
		this.accttype = accttype;
	}

	public String getStaffid() {
		return staffid;
	}
	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getBuid() {
		return buid;
	}
	public void setBuid(String buid) {
		this.buid = buid;
	}

	public Integer getRecordnum() {
		return recordnum;
	}
	public void setRecordnum(Integer recordnum) {
		this.recordnum = recordnum;
	}

	public Boolean getLastflag() {
		return lastflag;
	}
	public void setLastflag(Boolean lastflag) {
		this.lastflag = lastflag;
	}

}