package com.yonyou.iuap.plugin.basedoc.user.service;

import java.util.List;

import com.yonyou.iuap.pap.plugin.basedoc.user.api.vo.SyncUser;
import com.yonyou.iuap.plugin.basedoc.user.entity.WBUser;
import com.yonyou.uap.wb.utils.JsonResponse;

public interface IUserService {
	
	/**
	 * 用户信息新增同步
	 * @param syncUser
	 * @return
	 */
	public JsonResponse sync4Create(SyncUser syncUser);
	
	/**
	 * 用户信息更新同步
	 * @param syncUser
	 * @param wbUser
	 * @return
	 */
	public JsonResponse sync4Update(SyncUser syncUser, WBUser wbUser);
	
	/**
	 * 根据对象属性查询数据列表
	 * @param name
	 * @param value
	 * @return
	 */
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

}