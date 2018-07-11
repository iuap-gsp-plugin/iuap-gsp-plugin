package com.yonyou.iuap.pap.plugin.gsp.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.iuap.pap.plugin.basedoc.staff.api.vo.SyncStaff;
import com.yonyou.iuap.pap.plugin.basedoc.staff.entity.Staff;
import com.yonyou.iuap.pap.plugin.basedoc.staff.service.IStaffService;
import com.yonyou.iuap.pap.support.utils.RestUtils;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;
import com.alibaba.fastjson.JSON;

@Component
public class StaffSyncApi {

	private Logger log = LoggerFactory.getLogger(StaffSyncApi.class);

	/**
	 * 人员同步Rest服务
	 * @param Position
	 */
	public Result syncStaff(SyncStaff syncStaff) {
		List<Staff> listData = staffService.queryList("code", syncStaff.getCode());
		try {
			String restUrl = "";
			//岗位|职位数据对象转换
			Staff staff = null;
			if(listData==null || listData.size()==0) {						//新增人员信息
				restUrl = PropertyUtil.getPropertyByKey("iuap.staff.create.rest");
				staff = staffService.sync2Staff(syncStaff);
			}else if(listData!=null && listData.size()==1){					//更新人员信息
				restUrl = PropertyUtil.getPropertyByKey("iuap.staff.update.rest");
				staff = staffService.sync2Staff(syncStaff, listData.get(0));
			}else {
				log.error("同步人员信息出错，系统存在多条人员信息：staff="+syncStaff.getCode());
				return Result.failure(999, "同步人员信息出错，系统存在多条人员信息：staff="+syncStaff.getCode(), syncStaff);
			}

			Staff response = RestUtils.getInstance().doPost(restUrl, staff, Staff.class);
			return Result.success(response);
		}catch(Exception exp) {
			log.error("同步人员信息出错,staff="+JSON.toJSONString(syncStaff), exp);
			return Result.failure(998, "同步人员信息出错,staff="+syncStaff.getCode(), syncStaff);
		}
	}

	/**********************************************/
	@Autowired
	private IStaffService staffService;

}