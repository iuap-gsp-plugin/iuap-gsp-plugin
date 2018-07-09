package com.yonyou.iuap.pap.surface;

import java.io.Serializable;
import com.alibaba.fastjson.JSON;

public class Result implements Serializable{

	private static final long serialVersionUID = -942213417509597481L;
	
	private boolean success;
	private Integer code;
	private String msg;
	private Object data;
	
	public static Result success(Object data){
		return instance(true, 0, "", data);		
	}
	
	public static Result failure(Integer code, Object data) {
		return instance(false, code, "", data);
	}
	
	public static Result failure(Integer code, String message, Object data) {
		return instance(false, code, message, data);
	}
	
	public static Result instance(boolean success, Integer code, String msg, Object data){
		Result result = new Result();
		result.success = success;
		result.code = code;
		result.msg = msg;
		result.data = data;
		return result;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public String toJSONString(){
		return JSON.toJSONString(this);
	}
	
}