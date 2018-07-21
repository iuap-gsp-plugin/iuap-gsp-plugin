package com.yonyou.iuap.plugin.basedoc.dept.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.pap.plugin.basedoc.dept.api.vo.SyncDept;
import com.yonyou.iuap.plugin.basedoc.dept.entity.Dept;
import com.yonyou.iuap.plugin.basedoc.dept.service.IDeptService;
import com.yonyou.uap.wb.utils.JsonResponse;

import cn.hutool.core.util.StrUtil;


@Controller
@RequestMapping(value="/restWithSign/dept")
public class DeptSyncRestController {

	private static final Logger log = LoggerFactory.getLogger(DeptSyncRestController.class);
	
	/**
	 * 同步保存部门信息
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse sync(@RequestBody String data) {
		log.info("开始同步部门信息.......................\r\n"+data);
		String jsonData = JSON.parse(data).toString();
		SyncDept syncDept = JSON.parseObject(jsonData, SyncDept.class);
		
		JsonResponse response = new JsonResponse();
		if(StrUtil.isBlank(syncDept.getCode())) {
			response.failed("部门编码为空!");
			return response;
		}
		
		try {
			List<Dept> listData = deptService.queryList("code", syncDept.getCode());
			if(listData==null || listData.size()==0) {
				return deptService.sync4Create(syncDept);
			}else if(listData.size()==1) {
				return deptService.sync4Update(syncDept, listData.get(0));
			}else {
				log.error("同步部门信息出错，系统存在多条部门信息：" + data);
				response.failed("同步部门信息出错，系统存在多条部门信息：Dept="+syncDept.getCode());
			}
		}catch(Exception exp) {
			log.error("同步部门信息出错:"+data, exp);
			response.failed("同步部门信息出错:"+exp.getMessage());
		}
		return response;
	}
	
	/**
	 * 新增保存部门
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse create(@RequestBody String data) {
		log.info("开始同步部门信息【新增】.......................\r\n"+data);
		String jsonData = JSON.parse(data).toString();
		SyncDept syncDept = JSON.parseObject(jsonData, SyncDept.class);
		
		JsonResponse response = new JsonResponse();
		if(StrUtil.isBlank(syncDept.getCode())) {
			response.failed("部门编码为空!");
			return response;
		}
		
		try {
			List<Dept> listData = this.deptService.queryList("code", syncDept.getCode());
			if(listData==null || listData.size()==0) {
				return this.deptService.sync4Create(syncDept);
			}else {
				log.error("同步部门信息【新建】出错，记录数="+listData.size()+"\r\n"+data);
				response.failed("同步部门信息【新建】出错，记录数="+(listData==null?0:listData.size()));
			}
		}catch(Exception exp) {
			log.error("同步部门信息【新建】出错:"+data, exp);
			response.failed("同步部门信息【新建】出错:"+exp.getMessage());
		}
		return response;
		
	}
	
	/**
	 * 更新保存部门
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse update(@RequestBody String data) {
		log.info("开始同步部门信息【变更】.......................\r\n"+data);
		String jsonData = JSON.parse(data).toString();
		SyncDept syncDept = JSON.parseObject(jsonData, SyncDept.class);
		
		JsonResponse response = new JsonResponse();
		if(StrUtil.isBlank(syncDept.getCode())) {
			response.failed("部门编码为空!");
			return response;
		}
		
		try {
			List<Dept> listData = this.deptService.queryList("code", syncDept.getCode());
			if(listData!=null && listData.size()==1) {
				return this.deptService.sync4Update(syncDept, listData.get(0));
			}else {
				log.error("同步部门信息【变更】出错，记录数="+(listData==null?0:listData.size())+"\r\n"+data);
				response.failed("同步部门信息【变更】出错，记录数="+(listData==null?0:listData.size()));
			}
		}catch(Exception exp) {
			log.error("同步部门信息【变更】出错:"+data, exp);
			response.failed("同步部门信息【变更】出错:"+exp.getMessage());
		}
		return response;
	}
	
	
	/*******************************************************/
	@Autowired
	private IDeptService deptService;

}