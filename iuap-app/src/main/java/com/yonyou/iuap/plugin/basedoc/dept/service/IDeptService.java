package com.yonyou.iuap.plugin.basedoc.dept.service;

import java.util.List;
import java.util.Map;

import com.yonyou.iuap.pap.plugin.basedoc.dept.api.vo.SyncDept;
import com.yonyou.iuap.plugin.basedoc.dept.entity.Dept;
import com.yonyou.uap.wb.utils.JsonResponse;

public interface IDeptService {
	
	/**
	 * 部门信息新增同步
	 * @param syncDept
	 * @return
	 */
	public JsonResponse sync4Create(SyncDept syncDept);
	
	
	/**
	 * 部门信息更新同步
	 * @param syncDept
	 * @param dept
	 * @return
	 */
	public JsonResponse sync4Update(SyncDept syncDept, Dept dept);

	/**
	 * 根据字段查询列表
	 * @param name
	 * @param value
	 * @return
	 */
	public List<Dept> queryList(String name, Object value);
	
	/**
	 * 根据对象属性查询数据列表:返回值List<Map<String,Object>>
	 * @param name
	 * @param value
	 * @return
	 */
	public List<Map<String,Object>> queryListMap(String name, Object value);
	
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

}