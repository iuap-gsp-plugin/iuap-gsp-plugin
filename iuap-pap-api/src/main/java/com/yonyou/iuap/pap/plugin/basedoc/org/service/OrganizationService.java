package com.yonyou.iuap.pap.plugin.basedoc.org.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.pap.plugin.basedoc.org.api.vo.SyncOrg;
import com.yonyou.iuap.pap.plugin.basedoc.org.dao.OrganizationMapper;
import com.yonyou.iuap.pap.plugin.basedoc.org.entity.Organization;

import cn.hutool.core.util.StrUtil;

@Component
public class OrganizationService implements IOrganizationService{

	private Logger log = LoggerFactory.getLogger(OrganizationService.class);

	@Override
	public List<Organization> queryList(String name, Object value) {
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	queryParams.put(name, value);
    	return this.queryList(queryParams);
	}
    
    private List<Organization> queryList(Map<String,Object> params){
    	return this.organizationMapper.queryList(params);
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
	
	@Override
	public Organization sync2Orgnization(SyncOrg syncOrg) {
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

	@Override
	public Organization sync2Orgnization(SyncOrg syncOrg, Organization organization) {
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
		return organization;
	}
    
	/***************************************************/
	@Autowired
	protected OrganizationMapper organizationMapper;

}