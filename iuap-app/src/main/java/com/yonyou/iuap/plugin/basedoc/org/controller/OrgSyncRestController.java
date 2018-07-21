package com.yonyou.iuap.plugin.basedoc.org.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.pap.plugin.basedoc.org.api.vo.SyncOrg;
import com.yonyou.iuap.plugin.basedoc.org.entity.Organization;
import com.yonyou.iuap.plugin.basedoc.org.service.IOrgService;
import com.yonyou.uap.wb.utils.JsonResponse;
import com.alibaba.fastjson.JSON;
import cn.hutool.core.util.StrUtil;

@Controller
@RequestMapping("/restWithSign/organization")
public class OrgSyncRestController {
	
	private static final Logger log = LoggerFactory.getLogger(OrgSyncRestController.class);
	
	/**
	 * 同步保存组织机构信息
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse sync(@RequestBody String data) {
		log.info("开始同步组织机构信息.......................\r\n"+data);
		String jsonData = JSON.parse(data).toString();
		SyncOrg syncOrg = JSON.parseObject(jsonData, SyncOrg.class);
		
		JsonResponse response = new JsonResponse();
		if(StrUtil.isBlank(syncOrg.getCode())) {
			response.failed("组织机构编码为空!");
			return response;
		}
		
		try {
			List<Organization> listData = orgService.queryList("code", syncOrg.getCode());
			if(listData==null || listData.size()==0) {
				return this.orgService.sync4Create(syncOrg);
			}else if(listData.size()==1) {
				return this.orgService.sync4Update(syncOrg, listData.get(0));
			}else {
				log.error("同步组织机构信息出错，系统存在多条组织机构信息：" + data);
				response.failed("同步组织机构信息出错，系统存在多条组织机构信息：organization="+syncOrg.getCode());
			}
		}catch(Exception exp) {
			log.error("同步组织机构信息出错:"+data, exp);
			response.failed("同步组织机构信息出错:"+exp.getMessage());
		}
		return response;
	}
	
	/**
	 * 新增保存组织机构
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse create(@RequestBody String data) {
		log.info("开始同步组织机构信息【新增】.......................\r\n"+data);
		String jsonData = JSON.parse(data).toString();
		SyncOrg syncOrg = JSON.parseObject(jsonData, SyncOrg.class);
		
		JsonResponse response = new JsonResponse();
		if(StrUtil.isBlank(syncOrg.getCode())) {
			response.failed("组织机构编码为空!");
			return response;
		}
		
		try {
			List<Organization> listData = orgService.queryList("code", syncOrg.getCode());
			if(listData==null || listData.size()==0) {
				return this.orgService.sync4Create(syncOrg);
			}else {
				log.error("同步组织机构信息【新建】出错，记录数="+listData.size()+"\r\n"+data);
				response.failed("同步组织机构信息【新建】出错，记录数="+(listData==null?0:listData.size()));
			}
		}catch(Exception exp) {
			log.error("同步组织机构信息【新建】出错:"+data, exp);
			response.failed("同步组织机构信息【新建】出错:"+exp.getMessage());
		}
		return response;
		
	}
	
	/**
	 * 更新保存组织机构
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse update(@RequestBody String data) {
		log.info("开始同步组织机构信息【变更】.......................\r\n"+data);
		String jsonData = JSON.parse(data).toString();
		SyncOrg syncOrg = JSON.parseObject(jsonData, SyncOrg.class);
		
		JsonResponse response = new JsonResponse();
		if(StrUtil.isBlank(syncOrg.getCode())) {
			response.failed("组织机构编码为空!");
			return response;
		}
		
		try {
			List<Organization> listData = orgService.queryList("code", syncOrg.getCode());
			if(listData!=null && listData.size()==1) {
				return this.orgService.sync4Update(syncOrg, listData.get(0));
			}else {
				log.error("同步组织机构信息【变更】出错，记录数="+(listData==null?0:listData.size())+"\r\n"+data);
				response.failed("同步组织机构信息【变更】出错，记录数="+(listData==null?0:listData.size()));
			}
		}catch(Exception exp) {
			log.error("同步组织机构信息【变更】出错:"+data, exp);
			response.failed("同步组织机构信息【变更】出错:"+exp.getMessage());
		}
		return response;

	}

	/*******************************************************/
	@Autowired
	private IOrgService orgService;
	
}