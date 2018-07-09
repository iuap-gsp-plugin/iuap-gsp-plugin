package com.yonyou.iuap.pap.plugin.basedoc.position.service;

import java.util.List;
import com.yonyou.iuap.pap.plugin.basedoc.position.entity.Position;

public interface IPositionService {

	public List<Position> queryList(String name, Object value);
	
}