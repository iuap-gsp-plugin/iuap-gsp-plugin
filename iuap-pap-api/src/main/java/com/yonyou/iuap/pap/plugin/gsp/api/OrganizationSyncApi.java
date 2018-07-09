package com.yonyou.iuap.pap.plugin.gsp.api;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.pap.plugin.basedoc.org.api.vo.SyncOrg;
import com.yonyou.iuap.pap.plugin.basedoc.org.entity.Organization;
import com.yonyou.iuap.pap.plugin.basedoc.org.service.IOrganizationService;
import com.yonyou.iuap.pap.plugin.basedoc.user.api.rest.UserSyncRest;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganizationSyncApi {
	
	private Logger log = LoggerFactory.getLogger(UserSyncRest.class);

	/**
	 * 用户同步Rest服务
	 * @param Organization
	 */
	public Result syncOrganization(SyncOrg syncOrg) {
		try {
			List<Organization> listUser = organizationService.queryList("code", syncOrg.getCode());
			String restUrl = "";
			if(listUser==null || listUser.size()==0) {
				restUrl = PropertyUtil.getPropertyByKey("iuap.organization.create.rest");
			}else if(listUser!=null && listUser.size()==1){
				restUrl = PropertyUtil.getPropertyByKey("iuap.organization.update.rest");
			}else {
				log.error("同步组织机构信息出错，系统存在多条组织机构信息：organization=" + syncOrg.getCode());
				return Result.failure(999, "同步组织机构信息出错，系统存在多条组织机构信息：organization="+syncOrg.getCode(), syncOrg);
			}
			
			//组织机构数据对象转换
			Organization org = this.sanyOrg2Orgnization(syncOrg);
			Organization response = RestUtils.getInstance().doPost(restUrl, org, Organization.class);
			return Result.success(response);
		}catch(Exception exp) {
			log.error("同步组织机构信息出错,organization="+JSON.toJSONString(syncOrg), exp);
			return Result.failure(998, "同步用户信息出错,user="+JSON.toJSONString(syncOrg), "");
		}
	}
	
	/**
	 * SyncOrg转Organization
	 * @param syncOrg
	 * @return
	 */
	private Organization sanyOrg2Orgnization(SyncOrg syncOrg) {
		Organization organization = new Organization();
		organization.setCode(syncOrg.getCode());
		organization.setName(syncOrg.getName());
		organization.setShort_name(syncOrg.getShortName());
		organization.setParent_id(syncOrg.getParentCode());		//需调整
		organization.setContact(syncOrg.getContact());
		organization.setContact_phone(syncOrg.getContactPhone());
		organization.setContact_address(syncOrg.getContactAddress());
		organization.setCreate_date(syncOrg.getCreateDate());
		organization.setDescription(syncOrg.getDescription());
		organization.setEffective_date(syncOrg.getEffectiveDate());
		organization.setSys_id("wbalone");
		organization.setTenant_id("tenant");
		organization.setType("Organization");
		
		organization.setDr(0);
		organization.setTs(new Date());
		return organization;
	}
	
	/**********************************************/
	@Autowired
	private IOrganizationService organizationService;
	
}