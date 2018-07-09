package com.yonyou.iuap.pap.plugin.basedoc.org.service;

import java.util.List;

import com.yonyou.iuap.pap.plugin.basedoc.org.entity.Organization;

public interface IOrganizationService {

	public List<Organization> queryList(String name, Object value);
	
	public Organization findUnique(String name, Object value);

}