package com.yonyou.iuap.pap.plugin.basedoc.user.service;

import java.util.List;

import com.yonyou.iuap.pap.plugin.basedoc.user.api.vo.SyncUser;
import com.yonyou.iuap.pap.plugin.basedoc.user.entity.WBUser;

public interface IUserService {
	
	public List<WBUser> queryList(String name, Object value);

	/**
	 * 根据字段查询唯一
	 * @param name
	 * @param value
	 * @return
	 */
	public WBUser findUnique(String name, Object value);
	
	/**
	 * 根据code转id
	 * @return
	 */
	public String getIdByCode(String orgCode);
	
	/**
	 * SyncUser转WBUser【三一内部用户】
	 * @param syncUser
	 * @return
	 */
	public WBUser sync2WBUser(SyncUser syncUser);
	
	/**
	 * SyncUser转WBUser【三一内部用户】
	 * @param syncUser
	 * @param wbUser
	 * @return
	 */
	public WBUser sync2WBUser(SyncUser syncUser, WBUser wbUser);
	
	/**
	 * SyncUser转WBUser【三一外部用户注册、新增】
	 * @param syncUser
	 * @return
	 */
	public WBUser sync2WBUser4Regist(SyncUser syncUser);

}