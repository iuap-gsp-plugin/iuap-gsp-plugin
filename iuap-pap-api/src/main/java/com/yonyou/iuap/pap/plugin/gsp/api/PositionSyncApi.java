package com.yonyou.iuap.pap.plugin.gsp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.pap.plugin.basedoc.position.api.vo.SyncPosition;
import com.yonyou.iuap.pap.support.utils.RestUtils;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;
import com.yonyou.uap.wb.utils.JsonResponse;

import cn.hutool.core.util.StrUtil;

@Component
public class PositionSyncApi {

	private Logger log = LoggerFactory.getLogger(PositionSyncApi.class);

	
	/**
	 * 岗位|职位同步Rest服务
	 * @param Position
	 */
	public Result syncPosition(SyncPosition syncPosition) {
		if(StrUtil.isBlank(syncPosition.getCode())) {
			return Result.failure(999, "岗位编码为空!");
		}
		if(StrUtil.isBlank(syncPosition.getName())) {
			return Result.failure(999, "岗位名称为空!");
		}
		if(StrUtil.isBlank(syncPosition.getDeptCode())) {
			return Result.failure(999, "所属部门编码为空!");
		}
		if(StrUtil.isAllBlank(syncPosition.getOrgCode())) {
			return Result.failure(999, "所属组织机构编码为空!");
		}
		
		String syncUrl = PropertyUtil.getPropertyByKey("iuap.position.sync.rest");
		try {
			JsonResponse response = RestUtils.getInstance().doPostWithSign(syncUrl, 
											JSON.toJSONString(syncPosition), JsonResponse.class);
			return response.isfailed() ? Result.failure(999, response.toString(), syncPosition):Result.success(response);
		}catch(Exception exp) {
			log.error("同步岗位信息出错, position="+JSON.toJSONString(syncPosition), exp);
			return Result.failure(998, "同步岗位信息出错, position="+JSON.toJSONString(syncPosition), "");
		}
	}
	
	
/*	*//**
	 * 岗位|职位同步Rest服务
	 * @param Position
	 *//*
	public Result syncPosition(SyncPosition syncPosition) {
		List<Position> listData = positionService.queryList("code", syncPosition.getCode());
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

			JsonResponse response = RestUtils.getInstance().doPostWithSign(restUrl, 
												JSON.toJSONString(position), JsonResponse.class);
			return response.isfailed()?Result.failure(999, response.toString(), syncPosition):Result.success(response);
		}catch(Exception exp) {
			log.error("同步岗位信息出错,user="+JSON.toJSONString(syncPosition), exp);
			return Result.failure(998, "同步岗位信息出错,position="+syncPosition.getCode(), syncPosition);
		}
	}*/


}