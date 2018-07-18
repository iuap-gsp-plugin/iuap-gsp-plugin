package com.yonyou.iuap.plugin.gsp.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.plugin.gsp.security.utils.UserType;

import iuap.portal.model.User;
import iuap.portal.runtime.service.IUserManagerService;

@Service
@SuppressWarnings("all")
public class AuthService {
	
	private Logger log = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private IUserManagerService userManagerService;

	/**
	 * 检查并获取用户
	 * @param username
	 * @return
	 */
	public User checkGetUser(String username) {
		User user = userManagerService.getUser(username);
		return user;
	}
	
	/**
	 * 判断用户类型是否为三一内部用户
	 * @param user
	 * @return
	 */
	public boolean isSanyUser(String userType) {
		if(userType!=null) {
			if(userType.equals(UserType.commerce.getCode()) || userType.equals(UserType.financial.getCode())
					|| userType.equals(UserType.logistics.getCode()) || userType.equals(UserType.inspector.getCode())) {
				return true;
			}
		}
		return false;
	}

}