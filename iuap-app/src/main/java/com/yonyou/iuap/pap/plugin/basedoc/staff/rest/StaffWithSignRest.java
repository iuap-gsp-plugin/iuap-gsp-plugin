package com.yonyou.iuap.pap.plugin.basedoc.staff.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.entity.staff.Staff;
import com.yonyou.iuap.excp.BaseDocException;
import com.yonyou.iuap.service.staff.itf.StaffService;
import com.yonyou.uap.wb.utils.jsonutils.JsonMapper;

@Controller
@RequestMapping(value="/staffRestWithSign4Sany")
public class StaffWithSignRest {

	private static final Logger log = LoggerFactory.getLogger(StaffWithSignRest.class);

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
			Staff staff = JSON.parseObject(data, Staff.class);
			staffService.save(staff);							//新增保存人员信息
			if (staff.getId()==null || staff.getId().trim().length()==0) {
				results.failed("新增人员信息失败:"+JSON.toJSONString(staff));
				return results;
			}
			results.success("保存成功!", "data", JSON.toJSONString(staff));
		} catch (BaseDocException e) {
			log.error("新增人员信息保存出错!", e);
			results.failed("新增人员信息保存出错:"+e.getMessage());
		} catch (Exception e) {
			log.error("服务异常：", e);
			results.failed("服务异常，请稍后重试或联系系统管理员！");
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
			Staff staff = JSON.parseObject(data, Staff.class);
			staff.setEnable(1);
			staffService.update(staff);
			results.success("保存成功!", "data", JSON.toJSONString(staff));
		} catch (BaseDocException e) {
			results.failed("人员信息更新保存出错:"+e.getMessage());
			log.error("人员信息更新保存出错!", e);
		} catch (Exception e) {
			results.failed("人员信息更新服务异常，请稍后重试！");
			log.error("人员信息更新服务异常：", e);
		}
		return results;
	}
	
	public static Object jsonToObj(String jsonString) {
		String fullPathClassName = "com.yonyou.iuap.entity.staff.Staff";
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
	private StaffService staffService;	
}