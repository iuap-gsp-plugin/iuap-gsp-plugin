package com.yonyou.iuap.pap.plugin.basedoc.position.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.iuap.pap.plugin.basedoc.position.dao.PositionMapper;
import com.yonyou.iuap.pap.plugin.basedoc.position.entity.Position;
import com.yonyou.iuap.pap.plugin.basedoc.user.service.UserService;

public class PositionService implements IPositionService {

	private Logger log = LoggerFactory.getLogger(UserService.class);

    public List<Position> queryList(String name, Object value){
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	queryParams.put(name, value);
    	return this.queryList(queryParams);
    }
    
    public List<Position> queryList(Map<String,Object> params){
    	return this.positionMapper.queryList(params);
    }
    
	/***************************************************/
	protected PositionMapper positionMapper;

	@Autowired
	public void setPositionMapper(PositionMapper positionMapper) {
		this.positionMapper = positionMapper;
	}

}
