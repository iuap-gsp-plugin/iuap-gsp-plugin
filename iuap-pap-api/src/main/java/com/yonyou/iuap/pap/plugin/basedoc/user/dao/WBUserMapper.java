package com.yonyou.iuap.pap.plugin.basedoc.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.yonyou.iuap.pap.plugin.basedoc.user.entity.WBUser;

public interface WBUserMapper {

	public List<WBUser> queryList(@Param("condition")Map<String,Object> params);

    public int insert(WBUser entity);
	
	public int update(WBUser entity);

	public int delete(@Param("condition")Map<String,Object> params);
	
}