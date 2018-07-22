package com.yonyou.iuap.pap.plugin.gsp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.pap.plugin.basedoc.dept.api.vo.SyncDept;
import com.yonyou.iuap.pap.support.utils.RestUtils;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;
import com.yonyou.uap.wb.utils.JsonResponse;

import cn.hutool.core.util.StrUtil;

@Component
public class DeptSyncApi {

	private Logger log = LoggerFactory.getLogger(DeptSyncApi.class);

	/**
	 * 部门同步Api服务
	 * @param dept
	 */
	public Result syncDept(SyncDept syncDept) {
		if(StrUtil.isBlank(syncDept.getCode())) {
			return Result.failure(999, "部门编码为空!");
		}
		if(StrUtil.isBlank(syncDept.getName())) {
			return Result.failure(999, "部门名称为空!");
		}
		if(StrUtil.isBlank(syncDept.getOrganizationCode())) {
			return Result.failure(999, "所属组织机构编码为空!");
		}
		
		String syncUrl = PropertyUtil.getPropertyByKey("iuap.dept.sync.rest");
		try {
			JsonResponse response = RestUtils.getInstance().doPostWithSign(syncUrl, 
					JSON.toJSONString(syncDept), JsonResponse.class);
			return response.isfailed() ? Result.failure(999, response.toString(), syncDept):Result.success(response);
		}catch(Exception exp) {
			log.error("同步部门信息出错, organization="+JSON.toJSONString(syncDept), exp);
			return Result.failure(998, "同步部门信息出错, organization="+JSON.toJSONString(syncDept), "");
		}
		
	}
	
	
/*	public Result syncDept(SyncDept syncDept) {
		List<Dept> listDept = deptService.queryList("code", syncDept.getCode());
		try {
			String restUrl = "";
			Dept dept  = null;
			if(listDept==null || listDept.size()==0) {						//新增部门信息
				restUrl = PropertyUtil.getPropertyByKey("iuap.dept.create.rest");
				dept = deptService.sync2Dept(syncDept);
			}else if(listDept!=null && listDept.size()==1){					//修改更新部门信息
				restUrl = PropertyUtil.getPropertyByKey("iuap.dept.update.rest");
				dept = deptService.sync2Dept(syncDept, listDept.get(0));
			}else {
				log.error("同步部门信息出错，系统存在多条部门信息：dept="+syncDept.getCode());
				return Result.failure(999, "同步部门信息出错，系统存在多条部门信息：dept="+syncDept.getCode(), syncDept);
			}
			
			//提交同步部门信息
			JsonResponse response = RestUtils.getInstance().doPostWithSign(restUrl, 
												JSON.toJSONString(dept), JsonResponse.class);
			return response.isfailed()?Result.failure(999, response.toString(), syncDept):Result.success(response);
		}catch(Exception exp) {
			log.error("同步部门信息出错,dept="+JSON.toJSONString(syncDept), exp);
			return Result.failure(998, "同步部门信息出错,dept="+JSON.toJSONString(syncDept), syncDept);
		}
	}*/


}