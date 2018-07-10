package com.yonyou.iuap.pap.plugin.gsp.api;

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
		List<Position> listData = positionService.queryList("loginName", syncPosition.getCode());
		try {
			String restUrl = "";
			//岗位|职位数据对象转换
			Position position = null;
			if(listData==null || listData.size()==0) {						//新增岗位-职位信息
				restUrl = PropertyUtil.getPropertyByKey("iuap.position.create.rest");
				position = positionService.sync2Position(syncPosition);
			}else if(listData!=null && listData.size()==1){					//更新岗位-职位信息
				restUrl = PropertyUtil.getPropertyByKey("iuap.position.update.rest");
				position = positionService.sync2Position(syncPosition, listData.get(0));
			}else {
				log.error("同步岗位信息出错，系统存在多条岗位信息：position="+syncPosition.getCode());
				return Result.failure(999, "同步岗位信息出错，系统存在多条岗位信息：position="+syncPosition.getCode(), syncPosition);
			}

			Position response = RestUtils.getInstance().doPost(restUrl, position, Position.class);
			return Result.success(response);
		}catch(Exception exp) {
			log.error("同步岗位信息出错,user="+JSON.toJSONString(syncPosition), exp);
			return Result.failure(998, "同步岗位信息出错,position="+syncPosition.getCode(), syncPosition);
		}
	}

	/**********************************************/
	@Autowired
	private IPositionService positionService;

}