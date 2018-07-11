package com.yonyou.iuap.pap.plugin.gsp.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.pap.plugin.basedoc.dept.api.vo.SyncDept;
import com.yonyou.iuap.pap.plugin.basedoc.dept.entity.Dept;
import com.yonyou.iuap.pap.plugin.basedoc.dept.service.IDeptService;
import com.yonyou.iuap.pap.plugin.basedoc.user.entity.WBUser;
import com.yonyou.iuap.pap.support.utils.RestUtils;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;

@Component
public class DeptSyncApi {

	private Logger log = LoggerFactory.getLogger(DeptSyncApi.class);

	/**
	 * 部门同步Api服务
	 * @param dept
	 */
	public Result syncDept(SyncDept syncDept) {
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
				log.error("同步用户信息出错，系统存在多条用户信息：dept="+syncDept.getCode());
				return Result.failure(999, "同步用户信息出错，系统存在多条用户信息：dept="+syncDept.getCode(), syncDept);
			}
			
			//部门数据对象转换
			WBUser response = RestUtils.getInstance().doDeleteWithSign(restUrl, dept, WBUser.class);
			return Result.success(response);

		}catch(Exception exp) {
			log.error("同步用户信息出错,user="+JSON.toJSONString(syncDept), exp);
			return Result.failure(998, "同步用户信息出错,dept="+JSON.toJSONString(syncDept), syncDept);
		}
	}
	
	/**********************************************/
	@Autowired
	private IDeptService deptService;

}