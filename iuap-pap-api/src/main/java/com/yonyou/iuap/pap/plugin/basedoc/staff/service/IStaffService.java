package com.yonyou.iuap.pap.plugin.basedoc.staff.service;

import java.util.List;
import com.yonyou.iuap.pap.plugin.basedoc.staff.api.vo.SyncStaff;
import com.yonyou.iuap.pap.plugin.basedoc.staff.entity.Staff;

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
	 * SyncStaff转Staff
	 * @param syncStaff
	 * @return
	 */
	public Staff sync2Staff(SyncStaff syncStaff);
	
	/**
	 * SyncStaff转Staff
	 * @param syncStaff
	 * @return
	 */
	public Staff sync2Staff(SyncStaff syncStaff, Staff staff);
	
}