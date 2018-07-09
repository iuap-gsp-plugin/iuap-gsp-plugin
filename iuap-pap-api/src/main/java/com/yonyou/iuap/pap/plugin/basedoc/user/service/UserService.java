package com.yonyou.iuap.pap.plugin.basedoc.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.pap.plugin.basedoc.user.dao.WBUserMapper;
import com.yonyou.iuap.pap.plugin.basedoc.user.entity.WBUser;

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
    
	/***************************************************/
	protected WBUserMapper wBUserMapper;

	@Autowired
	public void setwBUserMapper(WBUserMapper wBUserMapper) {
		this.wBUserMapper = wBUserMapper;
	}
	
}