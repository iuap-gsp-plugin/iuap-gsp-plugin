package com.yonyou.iuap.plugin.gsp.security.utils;

public enum UserType {
	
	commerce("4001","上午工程师/招标工程师"),
	logistics("4002","物流工程师/仓储物流/验收人员"),
	financial("4003","财务人员"),
	inspector("4004","质检人员"),
	supplier("4005","供应商用户"),
	warehouse("4006","第三方仓管员");
	
	private String code;
	private String name;

	private UserType(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

}