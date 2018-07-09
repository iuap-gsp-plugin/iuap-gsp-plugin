package com.yonyou.iuap.pap.plugin.gsp.api;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.pap.plugin.basedoc.dept.api.vo.SyncDept;
import com.yonyou.iuap.pap.plugin.basedoc.dept.entity.Dept;
import com.yonyou.iuap.pap.plugin.basedoc.dept.service.IDeptService;
import com.yonyou.iuap.pap.plugin.basedoc.org.api.vo.SyncOrg;
import com.yonyou.iuap.pap.plugin.basedoc.org.entity.Organization;
import com.yonyou.iuap.pap.plugin.basedoc.user.entity.WBUser;
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
			if(listDept==null || listDept.size()==0) {
				restUrl = PropertyUtil.getPropertyByKey("iuap.dept.create.rest");
			}else if(listDept!=null && listDept.size()==1){
				restUrl = PropertyUtil.getPropertyByKey("iuap.dept.update.rest");
			}else {
				log.error("同步用户信息出错，系统存在多条用户信息：dept="+syncDept.getCode());
				return Result.failure(999, "同步用户信息出错，系统存在多条用户信息：dept="+syncDept.getCode(), syncDept);
			}
			
			//部门数据对象转换
			Dept dept = this.sanyDept2Dept(syncDept);
			WBUser response = RestUtils.getInstance().doPost(restUrl, dept, WBUser.class);
			return Result.success(response);

		}catch(Exception exp) {
			log.error("同步用户信息出错,user="+JSON.toJSONString(syncDept), exp);
			return Result.failure(998, "同步用户信息出错,dept="+JSON.toJSONString(syncDept), syncDept);
		}
	}
	
	/**
	 * SyncDept转Dept
	 * @param syncDept
	 * @return
	 */
	private Dept sanyDept2Dept(SyncDept syncDept) {
		Dept dept = new Dept();
		dept.setCode(syncDept.getCode());
		dept.setName(syncDept.getName());
		dept.setShort_name(syncDept.getShortName());
		dept.setParent_id(syncDept.getParentCode());		//需调整
		dept.setCreate_date(syncDept.getCreateDate());
		dept.setDescription(syncDept.getDescription());
		dept.setEffective_date(syncDept.getEffectiveDate());
		dept.setSys_id("wbalone");
		dept.setTenant_id("tenant");
		dept.setType("Dept");
		
		dept.setDr(0);
		dept.setTs(new Date());
		return dept;
	}

	/**********************************************/
	@Autowired
	private IDeptService deptService;

}