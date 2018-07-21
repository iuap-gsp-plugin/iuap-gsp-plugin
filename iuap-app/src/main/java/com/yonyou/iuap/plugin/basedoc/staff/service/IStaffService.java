package com.yonyou.iuap.plugin.basedoc.staff.service;

import java.util.List;

import com.yonyou.iuap.pap.plugin.basedoc.staff.api.vo.SyncStaff;
import com.yonyou.iuap.plugin.basedoc.staff.entity.Staff;
import com.yonyou.uap.wb.utils.JsonResponse;

public interface IStaffService {
	
	/**
	 * 人员信息新增同步
	 * @param syncStaff
	 * @return
	 */
	public JsonResponse sync4Create(SyncStaff syncStaff);
	
	/**
	 * 人员信息更新同步
	 * @param syncStaff
	 * @param staff
	 * @return
	 */
	public JsonResponse sync4Update(SyncStaff syncStaff, Staff staff);

	/**
	 * 根据对象属性查询数据列表
	 * @param name
	 * @param value
	 * @return
	 */
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