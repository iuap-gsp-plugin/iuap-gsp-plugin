package com.yonyou.iuap.plugin.basedoc.staff.service;

import java.util.List;

import com.yonyou.iuap.plugin.basedoc.staff.entity.Staff;

public interface IStaffService {

	public List<Staff> queryList(String name, Object value);
	
	/**
	 * 根据字段查询唯一
	 * @param name
	 * @param value
	 * @return
	 */
	public Staff findUnique(String name, Object value);
	
	/**
	 * 根据code转id
	 * @return
	 */
	public String getIdByCode(String orgCode);
	
	/**
	 * 保存人员信息
	 * @param staff
	 */
	public void save(Staff staff);
	
	/**
	 * 新增人员信息
	 * @param staff
	 */
	public int insert(Staff staff);

	/**
	 * 更新人员信息
	 * @param staff
	 */
	public int update(Staff staff);

}