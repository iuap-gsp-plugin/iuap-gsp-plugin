package com.yonyou.iuap.plugin.basedoc.position.controller;

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
import com.yonyou.iuap.pap.plugin.basedoc.position.api.vo.SyncPosition;
import com.yonyou.iuap.plugin.basedoc.position.entity.Position;
import com.yonyou.iuap.plugin.basedoc.position.service.IPositionService;
import com.yonyou.uap.wb.utils.JsonResponse;

import cn.hutool.core.util.StrUtil;

@Controller
@RequestMapping(value="/restWithSign/position")
public class PostionSyncRestController {
	
	private static final Logger log = LoggerFactory.getLogger(PostionSyncRestController.class);
	
	/**
	 * 同步保存岗位信息
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse sync(@RequestBody String data) {
		log.info("开始同步岗位信息.......................\r\n"+data);
		String jsonData = JSON.parse(data).toString();
		SyncPosition syncPosition = JSON.parseObject(jsonData, SyncPosition.class);
		
		JsonResponse response = new JsonResponse();
		if(StrUtil.isBlank(syncPosition.getCode())) {
			response.failed("岗位编码为空!");
			return response;
		}
		
		try {
			List<Position> listData = this.positionService.queryList("code", syncPosition.getCode());
			if(listData==null || listData.size()==0) {
				return this.positionService.sync4Create(syncPosition);
			}else if(listData.size()==1) {
				return this.positionService.sync4Update(syncPosition, listData.get(0));
			}else {
				log.error("同步岗位信息出错，系统存在多条岗位信息：" + data);
				response.failed("同步岗位信息出错，系统存在多条岗位信息：Position="+syncPosition.getCode());
			}
		}catch(Exception exp) {
			log.error("同步岗位信息出错:"+data, exp);
			response.failed("同步岗位信息出错:"+exp.getMessage());
		}
		return response;
	}
	
	/**
	 * 新增保存岗位
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse create(@RequestBody String data) {
		log.info("开始同步岗位信息【新增】.......................\r\n"+data);
		String jsonData = JSON.parse(data).toString();
		SyncPosition syncPosition = JSON.parseObject(jsonData, SyncPosition.class);
		
		JsonResponse response = new JsonResponse();
		if(StrUtil.isBlank(syncPosition.getCode())) {
			response.failed("岗位编码为空!");
			return response;
		}
		
		try {
			List<Position> listData = this.positionService.queryList("code", syncPosition.getCode());
			if(listData==null || listData.size()==0) {
				return this.positionService.sync4Create(syncPosition);
			}else {
				log.error("同步岗位信息【新建】出错，记录数="+listData.size()+"\r\n"+data);
				response.failed("同步岗位信息【新建】出错，记录数="+(listData==null?0:listData.size()));
			}
		}catch(Exception exp) {
			log.error("同步岗位信息【新建】出错:"+data, exp);
			response.failed("同步岗位信息【新建】出错:"+exp.getMessage());
		}
		return response;
		
	}
	
	/**
	 * 更新保存岗位
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse update(@RequestBody String data) {
		log.info("开始同步岗位信息【变更】.......................\r\n"+data);
		String jsonData = JSON.parse(data).toString();
		SyncPosition syncPosition = JSON.parseObject(jsonData, SyncPosition.class);
		
		JsonResponse response = new JsonResponse();
		if(StrUtil.isBlank(syncPosition.getCode())) {
			response.failed("岗位编码为空!");
			return response;
		}
		
		try {
			List<Position> listData = this.positionService.queryList("code", syncPosition.getCode());
			if(listData!=null && listData.size()==1) {
				return this.positionService.sync4Update(syncPosition, listData.get(0));
			}else {
				log.error("同步岗位信息【变更】出错，记录数="+(listData==null?0:listData.size())+"\r\n"+data);
				response.failed("同步岗位信息【变更】出错，记录数="+(listData==null?0:listData.size()));
			}
		}catch(Exception exp) {
			log.error("同步岗位信息【变更】出错:"+data, exp);
			response.failed("同步岗位信息【变更】出错:"+exp.getMessage());
		}
		return response;
	}
	
	/**********************************************************/
	@Autowired
	private IPositionService positionService;

}