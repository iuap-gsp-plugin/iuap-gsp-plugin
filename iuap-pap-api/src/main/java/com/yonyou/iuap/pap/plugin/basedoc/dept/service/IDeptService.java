package com.yonyou.iuap.pap.plugin.basedoc.dept.service;

import java.util.List;

import com.yonyou.iuap.pap.plugin.basedoc.dept.api.vo.SyncDept;
import com.yonyou.iuap.pap.plugin.basedoc.dept.entity.Dept;

public interface IDeptService {

	/**
	 * 根据字段查询列表
	 * @param name
	 * @param value
	 * @return
	 */
	public List<Dept> queryList(String name, Object value);
	
	/**
	 * 根据字段查询唯一
	 * @param name
	 * @param value
	 * @return
	 */
	public Dept findUnique(String name, Object value);
	
	/**
	 * 根据code转id
	 * @return
	 */
	public String getIdByCode(String orgCode);
	
	/**
	 * SyncDept转Dept
	 * @param syncDept
	 * @return
	 */
	public Dept sync2Dept(SyncDept syncDept);
	
	/**
	 * SyncDept转Dept
	 * @param syncDept
	 * @return
	 */
	public Dept sync2Dept(SyncDept syncDept, Dept dept);

}