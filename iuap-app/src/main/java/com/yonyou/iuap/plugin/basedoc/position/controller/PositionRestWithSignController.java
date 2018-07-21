package com.yonyou.iuap.plugin.basedoc.position.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.plugin.basedoc.position.entity.Position;
import com.yonyou.iuap.plugin.basedoc.position.service.IPositionService;
import com.yonyou.uap.wb.utils.JsonResponse;
import com.yonyou.uap.wb.utils.jsonutils.JsonMapper;

//@Controller
//@RequestMapping(value="/position/restWithSign")
@SuppressWarnings("all")
public class PositionRestWithSignController{
	
	private static final Logger log = LoggerFactory.getLogger(PositionRestWithSignController.class);
	
	/**
	 * 新增保存岗位
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse create(@RequestBody String data) {
		JsonResponse results = new JsonResponse();
		try {
			String parse = JSON.parse(data).toString();
			Position position = (Position) jsonToObj(parse);
			positionService.save(position);
			if (position.getId()==null || position.getId().trim().length()==0) {
				results.failed("新增岗位信息失败:"+JSON.toJSONString(position));
				return results;
			}
			results.success("保存成功!", "data", position.getId());
		} catch (Exception e) {
			results.failed("服务异常，请稍后重试！");
			log.error("服务异常：", e);
		}
		return results;
	}
	
	/**
	 * 更新保存岗位
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse update(@RequestBody String data) {
		JsonResponse results = new JsonResponse();
		try {
			String parse = JSON.parse(data).toString();
			Position position = (Position) jsonToObj(parse);
			position.setEnable(1);
			positionService.update(position);
			results.success("保存成功!", "data", JSON.toJSONString(position));
		} catch (Exception e) {
			results.failed("服务异常，请稍后重试！");
			log.error("服务异常：", e);
		}
		return results;
	}
	
	public static Object jsonToObj(String jsonString) {
		String fullPathClassName = "com.yonyou.iuap.plugin.basedoc.position.entity.Position";
		try {
			JsonMapper jsonMapper = JsonMapper.buildNormalMapper();
			return jsonMapper.fromJson(jsonString, Class.forName(fullPathClassName));
		} catch (ClassNotFoundException e) {
			log.error("映射类不存在：{}", e);
		} 
		return null;
	}
	
	/******************************************************/
	@Autowired
	private IPositionService positionService;

}