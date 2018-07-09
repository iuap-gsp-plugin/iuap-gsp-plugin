package com.yonyou.iuap.pap.plugin.gsp.api;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.pap.plugin.basedoc.position.service.IPositionService;
import com.yonyou.iuap.pap.plugin.basedoc.position.api.vo.SyncPosition;
import com.yonyou.iuap.pap.plugin.basedoc.position.entity.Position;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;
import com.alibaba.fastjson.JSON;

@Component
public class PositionSyncApi {

	private Logger log = LoggerFactory.getLogger(PositionSyncApi.class);

	/**
	 * 岗位|职位同步Rest服务
	 * @param Position
	 */
	public Result syncPosition(SyncPosition syncPosition) {
		List<Position> listUser = positionService.queryList("loginName", syncPosition.getCode());
		try {
			String restUrl = "";
			if(listUser==null || listUser.size()==0) {
				restUrl = PropertyUtil.getPropertyByKey("iuap.position.create.rest");
			}else if(listUser!=null && listUser.size()==1){
				restUrl = PropertyUtil.getPropertyByKey("iuap.position.update.rest");
			}else {
				log.error("同步岗位信息出错，系统存在多条岗位信息：position="+syncPosition.getCode());
				return Result.failure(999, "同步岗位信息出错，系统存在多条岗位信息：position="+syncPosition.getCode(), syncPosition);
			}

			//岗位|职位数据对象转换
			Position position = this.sanyPosition2Position(syncPosition);
			Position response = RestUtils.getInstance().doPost(restUrl, position, Position.class);
			return Result.success(response);
		}catch(Exception exp) {
			log.error("同步岗位信息出错,user="+JSON.toJSONString(syncPosition), exp);
			return Result.failure(998, "同步岗位信息出错,position="+syncPosition.getCode(), syncPosition);
		}
	}
	
	/**
	 * SyncPosition转Position
	 * @param syncPosition
	 * @return
	 */
	private Position sanyPosition2Position(SyncPosition syncPosition) {
		Position position = new Position();
		position.setCode(syncPosition.getCode());
		position.setName(syncPosition.getName());
		position.setOrg_id(syncPosition.getOrgCode());				//需调整
		position.setDept_id(syncPosition.getDeptCode());			//需调整
		position.setEnable(syncPosition.getEnable());
		position.setSysid("wbalone");
		position.setTenantid("tenant");
		position.setDr(0);
		position.setTs(new Date());
		return position;
	}	

	/**********************************************/
	@Autowired
	private IPositionService positionService;

}