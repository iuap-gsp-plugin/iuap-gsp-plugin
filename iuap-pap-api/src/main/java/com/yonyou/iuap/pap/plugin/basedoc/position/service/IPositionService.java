package com.yonyou.iuap.pap.plugin.basedoc.position.service;

import java.util.List;
import java.util.Map;

import com.yonyou.iuap.pap.plugin.basedoc.position.api.vo.SyncPosition;
import com.yonyou.iuap.pap.plugin.basedoc.position.entity.Position;

public interface IPositionService {

	/**
	 * 查询列表——返回List<Postion>
	 * @param name
	 * @param value
	 * @return
	 */
	public List<Position> queryList(String name, Object value);
	
	/**
	 * 根据字段查询列表——返回List<Map>
	 * @param name
	 * @param value
	 * @return
	 */
	public List<Map<String,Object>> queryListMap(String name, Object value);

	/**
	 * 查询列表——返回List<Map>
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryListMap(Map<String,Object> params);

	/**
	 * 根据字段查询唯一
	 * @param name
	 * @param value
	 * @return
	 */
	public Position findUnique(String name, Object value);
	
	/**
	 * 根据code转id
	 * @return
	 */
	public String getIdByCode(String orgCode);
	
	/**
	 * SyncPosition转Position
	 * @param SyncPosition
	 * @return
	 */
	public Position sync2Position(SyncPosition syncPosition);
	
	/**
	 * SyncOrg转Position
	 * @param position
	 * @return
	 */
	public Position sync2Position(SyncPosition syncPosition, Position position);

}