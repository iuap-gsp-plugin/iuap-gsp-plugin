package com.yonyou.iuap.pap.plugin.gsp.api;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.pap.plugin.basedoc.user.api.vo.SyncUser;
import com.yonyou.iuap.pap.plugin.basedoc.user.entity.WBUser;
import com.yonyou.iuap.pap.plugin.basedoc.user.service.IUserService;
import com.yonyou.iuap.pap.support.utils.RestUtils;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;
import com.yonyou.uap.wb.utils.JsonResponse;
import cn.hutool.core.util.StrUtil;

@Component
public class UserSyncApi {
	
	private Logger log = LoggerFactory.getLogger(UserSyncApi.class);
	
	/**
	 * 用户同步Rest服务
	 * @param wbUser
	 */
	public Result syncSanyUser(SyncUser syncUser) {
		try {
			List<WBUser> listData = UserService.queryList("loginName", syncUser.getUserAccount());
			
			String restUrl = "";
			WBUser wbUser = null;
			if(listData==null || listData.size()==0) {						//新增用户信息
				restUrl = PropertyUtil.getPropertyByKey("iuap.user.create.rest");
				wbUser = UserService.sync2WBUser(syncUser);
			}else if(listData!=null && listData.size()==1){					//修改更新用户信息
				restUrl = PropertyUtil.getPropertyByKey("iuap.user.update.rest");
				wbUser = UserService.sync2WBUser(syncUser, listData.get(0));
			}else {
				log.error("同步用户信息出错，系统存在多条用户信息：user="+syncUser.getUserAccount());
				return Result.failure(1001, "同步用户信息出错，系统存在多条用户信息：user="+syncUser.getUserAccount(), syncUser);
			}

			if(StrUtil.isBlank(restUrl)) {
				log.error("无法同步用户信息，用户同步URL为空，请联系系统管理员!");
				return Result.failure(1001, "无法同步用户信息，用户同步URL为空，请联系系统管理员：user="+syncUser.getUserAccount(), syncUser);
			}else {
				JsonResponse response = RestUtils.getInstance().doPostWithSign(restUrl, JSON.toJSONString(wbUser), JsonResponse.class);
				if(response.isfailed()) {
					return Result.failure(1001, response.get(JsonResponse.MESSAGE));
				}else {
					return Result.success(wbUser);
				}
			}
		}catch(Exception exp) {
			log.error("同步用户信息出错,user="+JSON.toJSONString(syncUser), exp);
			return Result.failure(1001, "同步用户信息出错,user="+syncUser.getUserAccount(), syncUser);
		}
	}
	
	/**
	 * 供应商用户注册
	 * @param syncUser
	 */
	public Result registSupplier(SyncUser syncUser) {
		List<WBUser> listUser = UserService.queryList("loginName", syncUser.getUserAccount());
		if(listUser==null || listUser.size()==0) {
			WBUser wbUser = UserService.sync2WBUser4Regist(syncUser);
			String restUrl = PropertyUtil.getPropertyByKey("iuap.user.create.rest");
			if(StrUtil.isBlank(restUrl)) {
				log.error("供应商用户注册失败，供应商用户注册URL为空，请联系系统管理员!");
				return Result.failure(1002, "供应商用户注册失败，供应商用户注册URL为空，请联系系统管理员：user="+syncUser.getUserAccount(), syncUser);
			}
			
			JsonResponse response = RestUtils.getInstance().doPostWithSign(restUrl, JSON.toJSONString(wbUser), JsonResponse.class);
			if(response.isfailed()) {
				return Result.failure(1002, response.get(JsonResponse.MESSAGE));
			}else {
				return Result.success(wbUser);
			}
		}else {
			log.error("供应商用户注册出错，系统已存在该用户：user="+syncUser.getUserAccount());
			return Result.failure(1002, "供应商用户注册出错，系统已存在该用户：user="+syncUser.getUserAccount(), syncUser);
		}

	}

	/**********************************************/
	@Autowired
	private IUserService UserService;

}