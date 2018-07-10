package com.yonyou.iuap.pap.plugin.gsp.api;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.pap.plugin.basedoc.org.api.vo.SyncOrg;
import com.yonyou.iuap.pap.plugin.basedoc.org.entity.Organization;
import com.yonyou.iuap.pap.plugin.basedoc.org.service.IOrganizationService;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganizationSyncApi {
	
	private Logger log = LoggerFactory.getLogger(OrganizationSyncApi.class);

	/**
	 * 用户同步Rest服务
	 * @param Organization
	 */
	public Result syncOrganization(SyncOrg syncOrg) {
		try {
			List<Organization> listData = organizationService.queryList("code", syncOrg.getCode());
			String restUrl = "";
			Organization organization = null;
			if(listData==null || listData.size()==0) {						//新增组织机构信息
				restUrl = PropertyUtil.getPropertyByKey("iuap.organization.create.rest");
				organization = organizationService.sync2Orgnization(syncOrg);
			}else if(listData!=null && listData.size()==1){					//更新组织机构信息
				restUrl = PropertyUtil.getPropertyByKey("iuap.organization.update.rest");
				organization = organizationService.sync2Orgnization(syncOrg, listData.get(0));
			}else {
				log.error("同步组织机构信息出错，系统存在多条组织机构信息：organization=" + syncOrg.getCode());
				return Result.failure(999, "同步组织机构信息出错，系统存在多条组织机构信息：organization="+syncOrg.getCode(), syncOrg);
			}
			
			//组织机构数据对象转换
			Organization response = RestUtils.getInstance().doPost(restUrl, organization, Organization.class);
			return Result.success(response);
		}catch(Exception exp) {
			log.error("同步组织机构信息出错,organization="+JSON.toJSONString(syncOrg), exp);
			return Result.failure(998, "同步用户信息出错,user="+JSON.toJSONString(syncOrg), "");
		}
	}
	
	/**********************************************/
	@Autowired
	private IOrganizationService organizationService;
	
}