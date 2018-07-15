package com.yonyou.iuap.plugin.basedoc.staff.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.plugin.basedoc.staff.entity.Staff;
import com.yonyou.iuap.plugin.basedoc.staff.service.IStaffService;
import com.yonyou.uap.wb.utils.JsonResponse;
import com.yonyou.uap.wb.utils.jsonutils.JsonMapper;

@Controller
@RequestMapping(value="/staff/restWithSign")
public class StaffWithSignRestController {

	private static final Logger log = LoggerFactory.getLogger(StaffWithSignRestController.class);

	/**
	 * 新增人员信息
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse create(@RequestBody String data) {
		JsonResponse results = new JsonResponse();
		try {
			String parse = JSON.parse(data).toString();
			Staff staff = (Staff)jsonToObj(parse);
			staff = JSON.parseObject(parse, Staff.class);
			
			staffService.save(staff);							//新增保存人员信息
			if (staff.getId()==null || staff.getId().trim().length()==0) {
				results.failed("新增人员信息失败:"+JSON.toJSONString(staff));
				return results;
			}
			results.success("保存成功!", "data", JSON.toJSONString(staff));
		} catch (Exception e) {
			log.error("新增人员信息保存出错!", e);
			results.failed("新增人员信息保存出错:"+e.getMessage());
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
			Staff staff = JSON.parseObject(parse, Staff.class);
			staff.setEnable(1);
			staffService.update(staff);
			results.success("保存成功!", "data", JSON.toJSONString(staff));
		} catch (Exception e) {
			results.failed("人员信息更新保存出错:"+e.getMessage());
			log.error("人员信息更新保存出错!", e);
		}
		return results;
	}
	
	public static Object jsonToObj(String jsonString) {
		String fullPathClassName = "com.yonyou.iuap.plugin.basedoc.staff.entity.Staff";
		try {
			JsonMapper jsonMapper = JsonMapper.buildNormalMapper();
			return jsonMapper.fromJson(jsonString, Class.forName(fullPathClassName));
		} catch (ClassNotFoundException e) {
			log.error("映射类不存在：{}", e);
		} 
		return null;
	}
	
	/********************************************/
	@Autowired
	private IStaffService staffService;	

}