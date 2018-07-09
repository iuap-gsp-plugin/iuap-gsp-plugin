package com.yonyou.iuap.pap.plugin.basedoc.dept.api.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.pap.plugin.basedoc.dept.entity.Dept;
import com.yonyou.iuap.pap.plugin.basedoc.dept.service.IDeptService;
import com.yonyou.iuap.pap.plugin.basedoc.user.entity.WBUser;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;

@Component
public class DeptSyncRest {

	private Logger log = LoggerFactory.getLogger(DeptSyncRest.class);

	/**
	 * 部门同步Rest服务
	 * @param dept
	 */
	public Result syncDept(Dept dept) {
		List<Dept> listDept = deptService.queryList("code", dept.getCode());
		try {
			if(listDept==null || listDept.size()==0) {
				String restUrl = PropertyUtil.getPropertyByKey("iuap.dept.create.rest");
				WBUser response = RestUtils.getInstance().doPost(restUrl, dept, WBUser.class);
				return Result.success(response);
			}else if(listDept!=null && listDept.size()==1){
				String restUrl = PropertyUtil.getPropertyByKey("iuap.dept.update.rest");
				WBUser response = RestUtils.getInstance().doPost(restUrl, dept, WBUser.class);
				return Result.success(response);
			}else {
				log.error("同步用户信息出错，系统存在多条用户信息：dept="+dept.getCode());
				return Result.failure(999, "同步用户信息出错，系统存在多条用户信息：dept="+dept.getCode(), dept);
			}
		}catch(Exception exp) {
			log.error("同步用户信息出错,user="+JSON.toJSONString(dept), exp);
			return Result.failure(998, "同步用户信息出错,dept="+JSON.toJSONString(dept), dept);
		}
	}

	/**********************************************/
	@Autowired
	private IDeptService deptService;

}