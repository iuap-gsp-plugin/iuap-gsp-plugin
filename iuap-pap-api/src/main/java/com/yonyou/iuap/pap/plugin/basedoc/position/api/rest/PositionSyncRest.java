package com.yonyou.iuap.pap.plugin.basedoc.position.api.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.pap.plugin.basedoc.position.service.IPositionService;
import com.yonyou.iuap.pap.plugin.basedoc.position.entity.Position;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;
import com.alibaba.fastjson.JSON;

@Component
public class PositionSyncRest {

	private Logger log = LoggerFactory.getLogger(PositionSyncRest.class);

	/**
	 * 岗位同步Rest服务
	 * @param wbUser
	 */
	public Result syncPosition(Position position) {
		List<Position> listUser = positionService.queryList("loginName", position.getCode());
		try {
			if(listUser==null || listUser.size()==0) {
				String restUrl = PropertyUtil.getPropertyByKey("iuap.position.create.rest");
				Position response = RestUtils.getInstance().doPost(restUrl, position, Position.class);
				return Result.success(response);
			}else if(listUser!=null && listUser.size()==1){
				String restUrl = PropertyUtil.getPropertyByKey("iuap.position.update.rest");
				Position response = RestUtils.getInstance().doPost(restUrl, position, Position.class);
				return Result.success(response);
			}else {
				log.error("同步岗位信息出错，系统存在多条岗位信息：position="+position.getCode());
				return Result.failure(999, "同步岗位信息出错，系统存在多条岗位信息：position="+position.getCode(), position);
			}
		}catch(Exception exp) {
			log.error("同步岗位信息出错,user="+JSON.toJSONString(position), exp);
			return Result.failure(998, "同步岗位信息出错,user="+JSON.toJSONString(position), position);
		}
	}

	/**********************************************/
	@Autowired
	private IPositionService positionService;

}