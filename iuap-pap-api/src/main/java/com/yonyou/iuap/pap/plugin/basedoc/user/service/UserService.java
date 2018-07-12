package com.yonyou.iuap.pap.plugin.basedoc.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.pap.plugin.basedoc.org.entity.Organization;
import com.yonyou.iuap.pap.plugin.basedoc.org.service.IOrganizationService;
import com.yonyou.iuap.pap.plugin.basedoc.user.api.vo.SyncUser;
import com.yonyou.iuap.pap.plugin.basedoc.user.dao.WBUserMapper;
import com.yonyou.iuap.pap.plugin.basedoc.user.entity.WBUser;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Component
public class UserService implements IUserService{
	
	private Logger log = LoggerFactory.getLogger(UserService.class);

    public List<WBUser> queryList(String name, Object value){
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	queryParams.put(name, value);
    	return this.queryList(queryParams);
    }
    
    public List<WBUser> queryList(Map<String,Object> params){
    	return this.wBUserMapper.queryList(params);
    }

	@Override
	public WBUser findUnique(String name, Object value) {
		List<WBUser> listData = this.queryList(name, value);
		if(listData!=null && listData.size()==1) {
			return listData.get(0);
		}else {
			log.error("用户数据查询出错,数据记录不唯一, field="+name+", value="+value+", size="+listData.size());
			throw new RuntimeException("用户数据查询出错,size="+listData.size());
		}
	}

	@Override
	public String getIdByCode(String orgCode) {
		WBUser wbUser = this.findUnique("code", orgCode);
		return wbUser.getId();
	}

	@Override
	public WBUser sync2WBUser(SyncUser syncUser) {
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

	@Override
	public WBUser sync2WBUser(SyncUser syncUser, WBUser wbUser) {
		wbUser.setLoginName(syncUser.getUserAccount());
		wbUser.setPassword("-1");
		wbUser.setName(syncUser.getUserName());
		wbUser.setType(syncUser.getType());
		wbUser.setEmail(syncUser.getEmail());
		wbUser.setPhone(syncUser.getPhone());
		wbUser.setCreateDate(syncUser.getCreateDate());
		wbUser.setModifyDate(syncUser.getModifyDate());
		wbUser.setIslock(syncUser.getIslock());
		wbUser.setRemark(syncUser.getRemark());
		wbUser.setRegisterDate(new Date());
		wbUser.setDr(0);
		if(!StrUtil.isBlank(syncUser.getOrganizationCode())) {
			Organization organization = organizationService.findUnique("code", syncUser.getOrganizationCode());
			wbUser.setOrganizationId(organization.getId());
			wbUser.setOrganizationName(organization.getName());
		}
		return wbUser;
	}
	

	@Override
	public WBUser sync2WBUser4Regist(SyncUser syncUser) {
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
		wbUser.setTenantId("tenant");
		wbUser.setRemark(syncUser.getRemark());
		wbUser.setRegisterDate(new Date());
		wbUser.setStates("1");
		wbUser.setDr(0);
		wbUser.setTs(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));
		return wbUser;
	}
    
	/***************************************************/
	@Autowired
	protected WBUserMapper wBUserMapper;
	@Autowired
	private IOrganizationService organizationService;

}