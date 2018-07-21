package com.yonyou.iuap.plugin.basedoc.dept.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.yonyou.iuap.plugin.basedoc.dept.entity.Dept;

public interface DeptMapper {

	public List<Dept> queryList(@Param("condition")Map<String,Object> params);
	
	public List<Map<String,Object>> queryListMap(@Param("condition")Map<String,Object> params);

    public int insert(Dept entity);
	
	public int update(Dept entity);

	public int delete(@Param("condition")Map<String,Object> params);
	
}