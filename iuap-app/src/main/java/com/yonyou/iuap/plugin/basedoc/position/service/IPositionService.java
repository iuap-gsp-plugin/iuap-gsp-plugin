package com.yonyou.iuap.plugin.basedoc.position.service;

import java.util.List;

import com.yonyou.iuap.plugin.basedoc.position.entity.Position;

public interface IPositionService {
	
	/**
	 * 根据对象属性查询数据列表
	 * @param name
	 * @param value
	 * @return
	 */
	public List<Position> queryList(String name, Object value);
	
	/**
	 * 根据对象属性查询唯一数据
	 * @param name
	 * @param value
	 * @return
	 */
	public Position findUnique(String name, Object value);

	/**
	 * 保存数据【新增/更新，根据ID判断】
	 * @param entity
	 * @return
	 */
	public int save(Position entity);
	
	/**
	 * 新增保存
	 * @param entity
	 * @return
	 */
	public int insert(Position entity);
	
	/**
	 * 更新保存
	 * @param entity
	 * @return
	 */
	public int update(Position entity);
	
}