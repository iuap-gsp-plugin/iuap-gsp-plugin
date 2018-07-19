package com.yonyou.iuap.plugin.gsp.security.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import com.yonyou.iuap.plugin.gsp.security.service.AuthService;
import com.yonyou.iuap.plugin.gsp.security.utils.SecurityConfig;

import iuap.portal.login.ILoginService;
import iuap.portal.login.LoginException;
import iuap.portal.web.BaseController;
import iuap.portal.model.User;

/**
 * 说明：从三一集团portal门户登录认证
 * @author Aton
 * 2018年7月19日
 */
@Controller
@RequestMapping(value="/sso/gsp")
public class GspSSOController extends BaseController{
	
	private Logger log = LoggerFactory.getLogger(GspSSOController.class);

    @RequestMapping(value="/ssologin", method = RequestMethod.GET)
    public String ssologin(HttpServletRequest request) {
    	String ivUser = request.getHeader("iv_user");
    	log.info("获取三一集团Portal门户用户登录认证，iv_user="+ivUser);

    	//IUAP-GSP进行用户校验：三一确认iv_user为三一人员域账号，对应iUAP平台userCode
    	String successUrl = SecurityConfig.getString("gsp.homepage.url");
    	String unauthorizedUrl = SecurityConfig.getString("gsp.sso.unauthorized.url");

    	if(ivUser==null || ivUser.trim().length()==0) {
			log.error("三一集团Portal门户用户登录认证出错,未获取到iv_user:" + ivUser);
	        return "redirect:" + unauthorizedUrl;
    	}
		
	    User user = authService.checkGetUser(ivUser);
	    if(user != null) {
	    	//若本系统中存在该用户【三一内部用户】,进入iUAP页面
	    	if(authService.isSanyUser(user.getType())) {
		    	try {
			    	loginService.beforeLogin(user);
			        Cookie[] cookies = this.loginService.getLoginCookies(user);
			        addCookies(cookies);
			        loginService.afterLogin(user);
			        
			        log.info("三一集团Portal门户用户登录认证成功:" + ivUser);
			        return "redirect:" + successUrl;
		    	}catch(LoginException exp) {
					log.error("三一集团Portal门户用户登录认证出错:" + ivUser, exp);
			        return "redirect:" + unauthorizedUrl;
		    	}
	    	}else {
	    		log.error("该用户非三一集团内部用户类型，无法进行登录认证!");
	    		return "redirect:" + unauthorizedUrl;
	    	}
	    }else {
	    	//若本系统中不存在该用户，redrect 到http://portal.sany.com.cn
			log.error("三一集团Portal门户用户登录认证出错,未找到用户:" + ivUser);
	        return "redirect:" + unauthorizedUrl;
	    }
    	//若用户存在，用友进行入库，并完成后续系统内部登录认证，则调用主数据接口拉取用户信息:用户相关信息拉取接口由三一提供
    	//若拉取用户信息失败，则提示无用户、非法用户，
    }
    
    /*********************************************************/
    @Autowired
    private ILoginService loginService;
    @Autowired
    private AuthService authService;

}