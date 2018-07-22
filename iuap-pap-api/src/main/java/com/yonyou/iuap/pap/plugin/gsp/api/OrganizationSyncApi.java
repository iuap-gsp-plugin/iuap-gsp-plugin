package com.yonyou.iuap.pap.plugin.gsp.api;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.pap.plugin.basedoc.org.api.vo.SyncOrg;
import com.yonyou.iuap.pap.support.utils.RestUtils;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;
import com.yonyou.uap.wb.utils.JsonResponse;

import cn.hutool.core.util.StrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrganizationSyncApi {
	
	private Logger log = LoggerFactory.getLogger(OrganizationSyncApi.class);
	
	
	/**
	 * 用户同步Rest服务
	 * @param Organization
	 */
	public Result syncOrganization(SyncOrg syncOrg) {
		if(StrUtil.isBlank(syncOrg.getCode())) {
			return Result.failure(999, "组织机构编码为空!");
		}
		if(StrUtil.isBlank(syncOrg.getName())) {
			return Result.failure(999, "组织机构名称为空!");
		}
		
		String syncUrl = PropertyUtil.getPropertyByKey("iuap.organization.sync.rest");
		try {
			JsonResponse response = RestUtils.getInstance().doPostWithSign(syncUrl, 
					JSON.toJSONString(syncOrg), JsonResponse.class);
			return response.isfailed() ? Result.failure(999, response.toString(), syncOrg):Result.success(response);
		}catch(Exception exp) {
			log.error("同步组织机构信息出错, organization="+JSON.toJSONString(syncOrg), exp);
			return Result.failure(998, "同步组织机构信息出错, organization="+JSON.toJSONString(syncOrg), "");
		}
	}
	

/*	*//**
	 * 用户同步Rest服务
	 * @param Organization
	 *//*
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
			
			//提交保存组织机构
			JsonResponse response = RestUtils.getInstance().doPostWithSign(restUrl, 
											JSON.toJSONString(organization), JsonResponse.class);
			return response.isfailed()?Result.failure(999, response.toString(), syncOrg) : Result.success(response);
		}catch(Exception exp) {
			log.error("同步组织机构信息出错,organization="+JSON.toJSONString(syncOrg), exp);
			return Result.failure(998, "同步用户信息出错,user="+JSON.toJSONString(syncOrg), "");
		}
	}*/
	
}