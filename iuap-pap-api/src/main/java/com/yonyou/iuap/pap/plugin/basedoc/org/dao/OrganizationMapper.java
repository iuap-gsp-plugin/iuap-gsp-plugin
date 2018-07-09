package com.yonyou.iuap.pap.plugin.basedoc.org.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.yonyou.iuap.pap.plugin.basedoc.org.entity.Organization;

public interface OrganizationMapper {

	public List<Organization> queryList(@Param("condition")Map<String,Object> params);

    public int insert(Organization entity);
	
	public int update(Organization entity);

	public int delete(@Param("condition")Map<String,Object> params);

}