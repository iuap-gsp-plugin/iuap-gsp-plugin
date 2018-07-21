package com.yonyou.iuap.plugin.basedoc.org.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.yonyou.iuap.plugin.basedoc.org.entity.Organization;

public interface OrgMapper {

	public List<Organization> queryList(@Param("condition")Map<String,Object> params);
	
	public List<Map<String,Object>> queryListMap(@Param("condition")Map<String,Object> params);

    public int insert(Organization entity);
	
	public int update(Organization entity);

	public int delete(@Param("condition")Map<String,Object> params);

}