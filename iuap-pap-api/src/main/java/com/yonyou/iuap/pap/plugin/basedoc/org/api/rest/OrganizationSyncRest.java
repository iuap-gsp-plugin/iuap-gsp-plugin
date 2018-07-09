package com.yonyou.iuap.pap.plugin.basedoc.org.api.rest;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.pap.plugin.basedoc.org.entity.Organization;
import com.yonyou.iuap.pap.plugin.basedoc.org.service.IOrganizationService;
import com.yonyou.iuap.pap.plugin.basedoc.user.api.rest.UserSyncRest;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganizationSyncRest {
	
	private Logger log = LoggerFactory.getLogger(UserSyncRest.class);

	/**
	 * 用户同步Rest服务
	 * @param Organization
	 */
	public Result syncOrganization(Organization org) {
		List<Organization> listUser = organizationService.queryList("code", org.getCode());
		try {
			if(listUser==null || listUser.size()==0) {
				String restUrl = PropertyUtil.getPropertyByKey("iuap.organization.create.rest");
				Organization response = RestUtils.getInstance().doPost(restUrl, org, Organization.class);
				return Result.success(response);
			}else if(listUser!=null && listUser.size()==1){
				String restUrl = PropertyUtil.getPropertyByKey("iuap.organization.update.rest");
				Organization response = RestUtils.getInstance().doPost(restUrl, org, Organization.class);
				return Result.success(response);
			}else {
				log.error("同步组织机构信息出错，系统存在多条组织机构信息：organization=" + org.getCode());
				return Result.failure(999, "同步组织机构信息出错，系统存在多条组织机构信息：organization="+org.getCode(), org);
			}
		}catch(Exception exp) {
			log.error("同步组织机构信息出错,organization="+JSON.toJSONString(org), exp);
			return Result.failure(998, "同步用户信息出错,user="+JSON.toJSONString(org), "");
		}
	}
	
	/**********************************************/
	@Autowired
	private IOrganizationService organizationService;
	
}