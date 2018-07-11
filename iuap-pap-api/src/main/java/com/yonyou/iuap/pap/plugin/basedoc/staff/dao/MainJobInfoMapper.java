package com.yonyou.iuap.pap.plugin.basedoc.staff.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.yonyou.iuap.pap.plugin.basedoc.staff.entity.MainJobInfo;

public interface MainJobInfoMapper {

	public List<MainJobInfo> queryList(@Param("condition")Map<String,Object> params);

    public int insert(MainJobInfo entity);

	public int update(MainJobInfo entity);

	public int delete(@Param("condition")Map<String,Object> params);

}