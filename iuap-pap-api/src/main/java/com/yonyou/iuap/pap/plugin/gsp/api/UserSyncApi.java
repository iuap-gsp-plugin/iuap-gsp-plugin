package com.yonyou.iuap.pap.plugin.gsp.api;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.pap.plugin.basedoc.org.entity.Organization;
import com.yonyou.iuap.pap.plugin.basedoc.org.service.IOrganizationService;
import com.yonyou.iuap.pap.plugin.basedoc.user.api.vo.SyncUser;
import com.yonyou.iuap.pap.plugin.basedoc.user.entity.WBUser;
import com.yonyou.iuap.pap.plugin.basedoc.user.service.IUserService;
import com.yonyou.iuap.pap.support.utils.RestUtils;
import com.yonyou.iuap.pap.surface.Result;
import com.yonyou.iuap.utils.PropertyUtil;
import com.yonyou.uap.wb.utils.JsonResponse;

import cn.hutool.core.date.DateUtil;
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
			List<WBUser> listUser = UserService.queryList("loginName", syncUser.getUserAccount());
			
			String restUrl = "";
			if(listUser==null || listUser.size()==0) {
				restUrl = PropertyUtil.getPropertyByKey("iuap.user.create.rest");
			}else if(listUser!=null && listUser.size()==1){
				restUrl = PropertyUtil.getPropertyByKey("iuap.user.update.rest");
			}else {
				log.error("同步用户信息出错，系统存在多条用户信息：user="+syncUser.getUserAccount());
				return Result.failure(1001, "同步用户信息出错，系统存在多条用户信息：user="+syncUser.getUserAccount(), syncUser);
			}

			if(StrUtil.isBlank(restUrl)) {
				log.error("无法同步用户信息，用户同步URL为空，请联系系统管理员!");
				return Result.failure(1001, "无法同步用户信息，用户同步URL为空，请联系系统管理员：user="+syncUser.getUserAccount(), syncUser);
			}else {
				WBUser wbUser = this.sanyUser2WBUser(syncUser);
				JsonResponse response = RestUtils.getInstance().doPostWithSign(restUrl, JSON.toJSONString(wbUser), JsonResponse.class);
				if(response.isfailed()) {
					return Result.failure(1001, response.get(response.MESSAGE));
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
			WBUser wbUser = this.outUser2WBUser(syncUser);
			String restUrl = PropertyUtil.getPropertyByKey("iuap.user.create.rest");
			if(StrUtil.isBlank(restUrl)) {
				log.error("供应商用户注册失败，供应商用户注册URL为空，请联系系统管理员!");
				return Result.failure(1002, "供应商用户注册失败，供应商用户注册URL为空，请联系系统管理员：user="+syncUser.getUserAccount(), syncUser);
			}
			
			JsonResponse response = RestUtils.getInstance().doPostWithSign(restUrl, JSON.toJSONString(wbUser), JsonResponse.class);
			if(response.isfailed()) {
				return Result.failure(1002, response.get(response.MESSAGE));
			}else {
				return Result.success(wbUser);
			}
		}else {
			log.error("供应商用户注册出错，系统已存在该用户：user="+syncUser.getUserAccount());
			return Result.failure(1002, "供应商用户注册出错，系统已存在该用户：user="+syncUser.getUserAccount(), syncUser);
		}

	}
	
	/**
	 * syncUser转WBUser
	 * @param syncUser
	 * @return
	 */
	private WBUser sanyUser2WBUser(SyncUser syncUser) {
		WBUser wbUser = new WBUser();
		wbUser.setLoginName(syncUser.getUserAccount());
		wbUser.setPassword(syncUser.getPassword());
		wbUser.setName(syncUser.getUserName());
		wbUser.setType(syncUser.getType());
		wbUser.setEmail(syncUser.getEmail());
		wbUser.setPhone(syncUser.getPhone());
		wbUser.setCreateDate(syncUser.getCreateDate());
		wbUser.setModifyDate(syncUser.getModifyDate());
		wbUser.setAvator("images/dot.png");
		wbUser.setIslock(syncUser.getIslock());
		wbUser.setRoles("user");
		wbUser.setStates("1");
		wbUser.setTenantId("tenant");
		wbUser.setRemark(syncUser.getRemark());
		wbUser.setRegisterDate(new Date());
		wbUser.setStates("1");
		wbUser.setDr(0);
		wbUser.setTs(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));
		if(!StrUtil.isBlank(syncUser.getOrganizationCode())) {
			Organization organization = organizationService.findUnique("code", syncUser.getOrganizationCode());
			wbUser.setOrganizationId(organization.getId());
			wbUser.setOrganizationName(organization.getName());
		}
		return wbUser;
	}
	
	/**
	 * 外部用户转WBUser
	 * @param syncUser
	 * @return
	 */
	private WBUser outUser2WBUser(SyncUser syncUser) {
		WBUser wbUser = new WBUser();
		wbUser.setLoginName(syncUser.getUserAccount());
		wbUser.setPassword(syncUser.getPassword());
		wbUser.setName(syncUser.getUserName());
		wbUser.setType(syncUser.getType());
		wbUser.setEmail(syncUser.getEmail());
		wbUser.setPhone(syncUser.getPhone());
		wbUser.setCreateDate(syncUser.getCreateDate());
		wbUser.setModifyDate(syncUser.getModifyDate());
		wbUser.setAvator("images/dot.png");
		wbUser.setIslock(syncUser.getIslock());
		wbUser.setRoles("user");
		wbUser.setStates("1");
		wbUser.setTenantId("tenant");
		wbUser.setRemark(syncUser.getRemark());
		wbUser.setRegisterDate(new Date());
		wbUser.setStates("1");
		wbUser.setDr(0);
		wbUser.setTs(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));
		
		return wbUser;
	}

	/**********************************************/
	@Autowired
	private IUserService UserService;
	@Autowired
	private IOrganizationService organizationService;
	
}
