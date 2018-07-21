package com.yonyou.iuap.plugin.basedoc.staff.controller;

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
import com.yonyou.iuap.pap.plugin.basedoc.staff.api.vo.SyncStaff;
import com.yonyou.iuap.plugin.basedoc.staff.service.IStaffService;
import com.yonyou.iuap.plugin.basedoc.staff.entity.Staff;
import com.yonyou.uap.wb.utils.JsonResponse;

import cn.hutool.core.util.StrUtil;

@Controller
@RequestMapping(value="/restWithSign/staff")
public class StaffSyncRestController {
	
	private static final Logger log = LoggerFactory.getLogger(StaffSyncRestController.class);
	
	/**
	 * 同步保存人员信息
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse sync(@RequestBody String data) {
		log.info("开始同步人员信息.......................\r\n"+data);
		String jsonData = JSON.parse(data).toString();
		SyncStaff syncStaff = JSON.parseObject(jsonData, SyncStaff.class);
		
		JsonResponse response = new JsonResponse();
		if(StrUtil.isBlank(syncStaff.getCode())) {
			response.failed("人员编码为空!");
			return response;
		}
		
		try {
			List<Staff> listData = this.staffService.queryList("code", syncStaff.getCode());
			if(listData==null || listData.size()==0) {
				return this.staffService.sync4Create(syncStaff);
			}else if(listData.size()==1) {
				return this.staffService.sync4Update(syncStaff, listData.get(0));
			}else {
				log.error("同步人员信息出错，系统存在多条人员信息：" + data);
				response.failed("同步人员信息出错，系统存在多条人员信息：Position="+syncStaff.getCode());
			}
		}catch(Exception exp) {
			log.error("同步人员信息出错:"+data, exp);
			response.failed("同步人员信息出错:"+exp.getMessage());
		}
		return response;
	}
	
	/**
	 * 新增保存人员
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse create(@RequestBody String data) {
		log.info("开始同步人员信息【新增】.......................\r\n"+data);
		String jsonData = JSON.parse(data).toString();
		SyncStaff syncStaff = JSON.parseObject(jsonData, SyncStaff.class);
		
		JsonResponse response = new JsonResponse();
		if(StrUtil.isBlank(syncStaff.getCode())) {
			response.failed("人员编码为空!");
			return response;
		}
		
		try {
			List<Staff> listData = this.staffService.queryList("code", syncStaff.getCode());
			if(listData==null || listData.size()==0) {
				return this.staffService.sync4Create(syncStaff);
			}else {
				log.error("同步人员信息【新建】出错，记录数="+listData.size()+"\r\n"+data);
				response.failed("同步人员信息【新建】出错，记录数="+(listData==null?0:listData.size()));
			}
		}catch(Exception exp) {
			log.error("同步人员信息【新建】出错:"+data, exp);
			response.failed("同步人员信息【新建】出错:"+exp.getMessage());
		}
		return response;
		
	}
	
	/**
	 * 更新保存人员
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse update(@RequestBody String data) {
		log.info("开始同步人员信息【变更】.......................\r\n"+data);
		String jsonData = JSON.parse(data).toString();
		SyncStaff syncStaff = JSON.parseObject(jsonData, SyncStaff.class);
		
		JsonResponse response = new JsonResponse();
		if(StrUtil.isBlank(syncStaff.getCode())) {
			response.failed("人员编码为空!");
			return response;
		}
		
		try {
			List<Staff> listData = this.staffService.queryList("code", syncStaff.getCode());
			if(listData!=null && listData.size()==1) {
				return this.staffService.sync4Update(syncStaff, listData.get(0));
			}else {
				log.error("同步人员信息【变更】出错，记录数="+(listData==null?0:listData.size())+"\r\n"+data);
				response.failed("同步人员信息【变更】出错，记录数="+(listData==null?0:listData.size()));
			}
		}catch(Exception exp) {
			log.error("同步人员信息【变更】出错:"+data, exp);
			response.failed("同步人员信息【变更】出错:"+exp.getMessage());
		}
		return response;
	}
	
	/**********************************************************/
	@Autowired
	private IStaffService staffService;

}