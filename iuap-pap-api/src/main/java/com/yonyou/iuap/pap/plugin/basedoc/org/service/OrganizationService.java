package com.yonyou.iuap.pap.plugin.basedoc.org.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.pap.plugin.basedoc.org.dao.OrganizationMapper;
import com.yonyou.iuap.pap.plugin.basedoc.org.entity.Organization;

@Component
public class OrganizationService implements IOrganizationService{

	private Logger log = LoggerFactory.getLogger(OrganizationService.class);

	@Override
	public List<Organization> queryList(String name, Object value) {
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	queryParams.put(name, value);
    	return this.queryList(queryParams);
	}
    
    public List<Organization> queryList(Map<String,Object> params){
    	return this.organizationMapper.queryList(params);
    }
    
	/***************************************************/
	protected OrganizationMapper organizationMapper;

	public void setOrganizationMapper(OrganizationMapper organizationMapper) {
		this.organizationMapper = organizationMapper;
	}
	
}
