package com.yonyou.iuap.pap.plugin.basedoc.staff.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.yonyou.iuap.pap.plugin.basedoc.staff.entity.Staff;

public interface StaffMapper {

	public List<Staff> queryList(@Param("condition")Map<String,Object> params);

    public int insert(Staff entity);
	
	public int update(Staff entity);

	public int delete(@Param("condition")Map<String,Object> params);
}
