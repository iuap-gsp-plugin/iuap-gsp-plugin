package com.yonyou.iuap.plugin.basedoc.org.service;

import java.util.List;
import java.util.Map;

import com.yonyou.iuap.pap.plugin.basedoc.org.api.vo.SyncOrg;
import com.yonyou.iuap.plugin.basedoc.org.entity.Organization;
import com.yonyou.uap.wb.utils.JsonResponse;

public interface IOrgService {
	
	/**
	 * 组织机构新增同步
	 * @param syncOrg
	 * @return
	 */
	public JsonResponse sync4Create(SyncOrg syncOrg);
	
	
	/**
	 * 组织机构更新同步
	 * @param syncOrg
	 * @param organization
	 * @return
	 */
	public JsonResponse sync4Update(SyncOrg syncOrg, Organization organization);
	
	
	/**
	 * 列表查询
	 * @param params
	 * @return
	 */
	public List<Organization> queryList(Map<String,Object> params);

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

}