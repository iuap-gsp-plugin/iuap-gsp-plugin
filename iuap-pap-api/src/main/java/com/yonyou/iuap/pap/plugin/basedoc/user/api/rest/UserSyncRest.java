package com.yonyou.iuap.pap.plugin.basedoc.user.api.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.pap.plugin.basedoc.user.entity.WBUser;
import com.yonyou.iuap.pap.plugin.basedoc.user.service.IUserService;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;

@Component
public class UserSyncRest {
	
	private Logger log = LoggerFactory.getLogger(UserSyncRest.class);

	/**
	 * 用户同步Rest服务
	 * @param wbUser
	 */
	public Result syncUser(WBUser wbUser) {
		List<WBUser> listUser = UserService.queryList("loginName", wbUser.getLoginName());
		try {
			if(listUser==null || listUser.size()==0) {
				String restUrl = PropertyUtil.getPropertyByKey("iuap.user.create.rest");
				WBUser response = RestUtils.getInstance().doPost(restUrl, wbUser, WBUser.class);
				return Result.success(response);
			}else if(listUser!=null && listUser.size()==1){
				String restUrl = PropertyUtil.getPropertyByKey("iuap.user.update.rest");
				WBUser response = RestUtils.getInstance().doPost(restUrl, wbUser, WBUser.class);
				return Result.success(response);
			}else {
				log.error("同步用户信息出错，系统存在多条用户信息：user="+wbUser.getLoginName());
				return Result.failure(999, "同步用户信息出错，系统存在多条用户信息：user="+wbUser.getLoginName(), wbUser);
			}
		}catch(Exception exp) {
			log.error("同步用户信息出错,user="+JSON.toJSONString(wbUser), exp);
			return Result.failure(998, "同步用户信息出错,user="+JSON.toJSONString(wbUser), wbUser);
		}
	}

	/**********************************************/
	@Autowired
	private IUserService UserService;
	
}
