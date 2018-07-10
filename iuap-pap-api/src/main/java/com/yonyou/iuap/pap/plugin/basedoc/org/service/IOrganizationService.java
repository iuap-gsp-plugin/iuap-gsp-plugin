package com.yonyou.iuap.pap.plugin.basedoc.org.service;

import java.util.List;

import com.yonyou.iuap.pap.plugin.basedoc.org.api.vo.SyncOrg;
import com.yonyou.iuap.pap.plugin.basedoc.org.entity.Organization;

public interface IOrganizationService {

	/**
	 * 根据字段查询列表
	 * @param name
	 * @param value
	 * @return
	 */
	public List<Organization> queryList(String name, Object value);
	
	/**
	 * 根据字段查询唯一
	 * @param name
	 * @param value
	 * @return
	 */
	public Organization findUnique(String name, Object value);
	
	/**
	 * 根据code转id
	 * @return
	 */
	public String getIdByCode(String orgCode);
	
	/**
	 * SyncOrg转Organization
	 * @param syncOrg
	 * @return
	 */
	public Organization sync2Orgnization(SyncOrg syncOrg);
	
	/**
	 * SyncOrg转Organization
	 * @param syncOrg
	 * @return
	 */
	public Organization sync2Orgnization(SyncOrg syncOrg, Organization orgnization);

}