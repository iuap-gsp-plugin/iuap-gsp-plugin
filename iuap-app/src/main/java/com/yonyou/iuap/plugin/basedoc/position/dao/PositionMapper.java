package com.yonyou.iuap.plugin.basedoc.position.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.yonyou.iuap.plugin.basedoc.position.entity.Position;

public interface PositionMapper {

	public List<Position> queryList(@Param("condition")Map<String,Object> params);
	
	public List<Map<String,Object>> queryListMap(@Param("condition")Map<String,Object> params);

    public int insert(Position entity);

	public int update(Position entity);

	public int delete(@Param("condition")Map<String,Object> params);

}