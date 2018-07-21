package com.yonyou.iuap.plugin.basedoc.org.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.pap.plugin.basedoc.org.api.vo.SyncOrg;
import com.yonyou.iuap.plugin.basedoc.org.dao.OrgMapper;
import com.yonyou.iuap.plugin.basedoc.org.entity.Organization;
import com.yonyou.iuap.utils.PropertyUtil;
import com.yonyou.uap.wb.utils.JsonResponse;

import cn.hutool.core.util.StrUtil;

@Service("plugin.orgService")
public class OrgService implements IOrgService{
	
	private Logger log = LoggerFactory.getLogger(OrgService.class);
	private String createRestUrl = PropertyUtil.getPropertyByKey("iuap.organization.create.rest");
	private String updateRestUrl = PropertyUtil.getPropertyByKey("iuap.organization.update.rest");

	public JsonResponse sync4Create(SyncOrg syncOrg) {
		Organization organization = this.convert2Create(syncOrg);
		return RestUtils.getInstance().doPostWithSign(createRestUrl, 
					JSON.toJSONString(organization), JsonResponse.class);
	}
	
	public JsonResponse sync4Update(SyncOrg syncOrg, Organization organization) {
		this.convert2Update(syncOrg, organization);
		return RestUtils.getInstance().doPostWithSign(updateRestUrl, 
					JSON.toJSONString(organization), JsonResponse.class);
	}
	
	private Organization convert2Create(SyncOrg syncOrg) {
		Organization organization = new Organization();
		organization.setCode(syncOrg.getCode());
		organization.setName(syncOrg.getName());
		organization.setShort_name(syncOrg.getShortName());
		
		if(!StrUtil.isBlankIfStr(syncOrg.getParentCode())) {		//存在上级组织机构
			String parentId = this.getIdByCode(syncOrg.getParentCode());
			organization.setParent_id(parentId);
		}
		
		organization.setContact(syncOrg.getContact());
		organization.setContact_phone(syncOrg.getContactPhone());
		organization.setContact_address(syncOrg.getContactAddress());
		organization.setEffective_date(syncOrg.getEffectiveDate());
		organization.setDescription(syncOrg.getDescription());
		organization.setCreate_date(syncOrg.getCreateDate());
		organization.setType("Organization");
		organization.setSys_id("wbalone");
		organization.setTenant_id("tenant");
		
		organization.setDr(0);
		organization.setTs(new Date());
		return organization;
	}

	private void convert2Update(SyncOrg syncOrg, Organization organization) {
		organization.setCode(syncOrg.getCode());
		organization.setName(syncOrg.getName());
		organization.setShort_name(syncOrg.getShortName());
		
		if(!StrUtil.isBlankIfStr(syncOrg.getParentCode())) {		//存在上级组织机构
			String parentId = this.getIdByCode(syncOrg.getParentCode());
			organization.setParent_id(parentId);
		}
		
		organization.setContact(syncOrg.getContact());
		organization.setContact_phone(syncOrg.getContactPhone());
		organization.setContact_address(syncOrg.getContactAddress());
		organization.setEffective_date(syncOrg.getEffectiveDate());
		organization.setDescription(syncOrg.getDescription());
		organization.setCreate_date(syncOrg.getCreateDate());
		organization.setDr(0);
	}
	

	@Override
	public List<Organization> queryList(Map<String, Object> params) {
		return organizationMapper.queryList(params);
	}

	@Override
	public List<Organization> queryList(String name, Object value) {
		Map<String,Object> queryParams = new HashMap<String,Object>();
		queryParams.put(name, value);
		return this.queryList(queryParams);
	}

	@Override
	public Organization findUnique(String name, Object value) {
		List<Organization> listOrgs = this.queryList(name, value);
		if(listOrgs!=null && listOrgs.size()==1) {
			return listOrgs.get(0);
		}else {
			log.error("组织机构数据查询出错,数据记录不唯一, field="+name+", value="+value+", size="+listOrgs.size());
			throw new RuntimeException("组织机构数据查询出错,size="+listOrgs.size());
		}
	}

	@Override
	public String getIdByCode(String orgCode) {
		Organization organization = this.findUnique("code", orgCode);
		return organization.getId();
	}
	
	/******************************************************/
	@Autowired
	private OrgMapper organizationMapper;

}
