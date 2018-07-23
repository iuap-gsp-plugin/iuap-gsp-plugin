package com.yonyou.iuap.plugin.gsp.security.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.yonyou.iuap.plugin.gsp.security.service.AuthService;
import com.yonyou.iuap.plugin.gsp.security.utils.SecurityConfig;
import com.yonyou.iuap.portal.base.web.Response;
import com.yonyou.uap.wb.common.BusinessException;
import com.yonyou.uap.wb.entity.management.WBUser;
import com.yonyou.uap.wb.service.IWBUserService;

import iuap.portal.login.ILoginService;
import iuap.portal.login.LoginController;
import iuap.portal.login.LoginException;
import iuap.portal.model.User;

/**
 * 说明：通过iUAP提供的登录页面进行登录认证【包含iuap自有认证和ldap认证两类】
 * @author Aton
 * 2018年7月19日
 */
@Controller
@RequestMapping(value="/sso/gsp")
public class GspLoginController extends LoginController{
	
	private Logger log = LoggerFactory.getLogger(GspLoginController.class);
	
	private static int LDAP_TIMEOUT = SecurityConfig.getInt("gsp.ldap.authenticate.timeout", 1500);
	private static String LDAP_URL = SecurityConfig.getString("gsp.ldap.authenticate.url");
	private static String LDAP_SUCCESS = SecurityConfig.getString("gsp.ldap.authenticate.success");
	
	/**
	 * 用户登录认证
	 */
    @RequestMapping(value="/dologin")
    @Override
    public Response login(HttpServletRequest request){
        String uname = this.getParameter("username");
        String encoderPswd = this.getParameter("password");				//密文
        String orgPswd = this.getParameter("orgPassword");				//明文
		try {
		    String username = URLDecoder.decode(uname, "utf-8");
			WBUser wbUser = wBUserService.getByLoginName(username);
			if(wbUser != null) {
				boolean isSanyInner = authService.isSanyUser(wbUser.getType());
				if(isSanyInner) {									//三一内部用户——采购商
				    String orgPassword = URLDecoder.decode(orgPswd, "utf-8");
					return this.login4Ldap(wbUser, username, orgPassword);
				} else {											//三一外部用户——供应商
				    String password = URLDecoder.decode(encoderPswd, "utf-8");
					return this.login4iuap(wbUser, username, password);
				}
			} else {
				log.error("用户登录认证失败，未找到该用户:" + uname);
				return this.failure("用户登录认证失败，未找到该用户:" + uname);
			}
		} catch (BusinessException e1) {
			log.error("用户登录认证出错【BusinessException】:user="+uname, e1);
			return this.failure("用户登录认证出错:user=" + uname);
		} catch (UnsupportedEncodingException e2) {
			log.error("用户登录认证出错【UnsupportedEncodingException】:user="+uname, e2);
			return this.failure("用户登录认证出错:user=" + uname);
		}
    }
    
    /**
     * 三一内部人员（采购商）登录
     * @param username
     * @param password
     * @return
     */
    @SuppressWarnings("all")
	private Response login4Ldap(WBUser wbUser, String username,String password){
        HttpClient httpclient = new HttpClient();
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(LDAP_TIMEOUT);
        httpclient.getHttpConnectionManager().getParams().setSoTimeout(LDAP_TIMEOUT);
        
        if(LDAP_URL==null || LDAP_URL.trim().length()==0) {
        	log.error("未获取LDAP认证URL，请检查配置：gsp.ldap.authenticate.url");
        	return this.failure("未获取LDAP认证URL，请联系系统管理员检查配置信息!");
        }else {
            try{
                PostMethod httpPost = new PostMethod(LDAP_URL);
                NameValuePair[] dataParams = { new NameValuePair("username", username),
                							   new NameValuePair("password", password),
                							   new NameValuePair("login-form-type", "pwd") };
                httpPost.setRequestBody(dataParams);
                httpPost.setHttp11(true);
                int returnCode = httpclient.executeMethod(httpPost);
                if(returnCode == HttpStatus.SC_OK && this.checkLdap(httpclient, httpPost)) {
                   	User user = authService.checkGetUser(username);
                   	return this.loadUserResource(user);
                }
                log.error("GSP登录LDAP认证出错,user="+username+",HttpClient执行结果:returnCode=" + returnCode
                					+ "\r\n" + httpPost.getResponseBodyAsString());
                return this.failure("GSP登录LDAP认证出错!");
            }catch(Exception exp){
                log.error("GSP登录LDAP认证出错!", exp);
                return this.failure("GSP登录LDAP认证出错!");
            }
        }
    }
    
    /**
     * iuap用户登录认证
     * @param username
     * @param password
     * @return
     */
    @SuppressWarnings("all")
	private Response login4iuap(WBUser wbUser, String username, String password){
        try {
			User user = this.loginService.veryfiy(username, password);
        	Response response = this.loadUserResource(user);
        	return response;
		} catch (LoginException exp) {
			log.error("用户登录认证失败:user="+username, exp);
			return this.failure("用户登录认证失败:user="+username);
		}
    }
    
    /**
     * 登录认证后加载相关用户资源
     * @param user
     * @return
     */
    private Response loadUserResource(User user){
    	if (user != null){
            try {
    	    	this.loginService.beforeLogin(user);
    	    	Cookie[] cookies = this.loginService.getLoginCookies(user);
    	        this.addCookies(cookies);
    	        this.loginService.afterLogin(user);
    	    	return this.success("登录认证成功!", user);
            }catch(Exception exp) {
            	log.error("系统登录认证失败!", exp);
            	return this.failure("系统登录认证失败!");
            }
		} else {
			log.error("系统登录认证失败，未找到用户!");
			return this.failure("系统登录认证失败，未找到用户!");
		}
    }
    
    /**
     * 检查校验LDAP认证
     * @param httpclient
     * @param httpPost
     * @return
     */
    private boolean checkLdap(HttpClient httpclient, PostMethod httpPost) {
    	try {
			String response = httpPost.getResponseBodyAsString();
			if(response.contains(LDAP_SUCCESS)) {
				return true;
			}
    	} catch (IOException e) {
    		log.error("GSP登录LDAP认证失败!", e);
		}
    	return false;
    }
    
    /*************************************************/
    @Autowired
    private IWBUserService wBUserService;
    @Autowired
    private ILoginService loginService;
	@Autowired
	private AuthService authService;

}