package com.yonyou.iuap.pap.plugin.basedoc.user.service;

import java.util.List;

import com.yonyou.iuap.pap.plugin.basedoc.user.entity.WBUser;

public interface IUserService {
	
	public List<WBUser> queryList(String name, Object value);

}