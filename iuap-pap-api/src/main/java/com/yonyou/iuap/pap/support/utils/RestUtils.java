package com.yonyou.iuap.pap.support.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.generic.sign.SignEntity;
import com.yonyou.iuap.generic.sign.SignMake;
import com.yonyou.iuap.generic.utils.PropertiesUtils;
import com.yonyou.iuap.pap.support.context.SpringContexts;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

@Component
@SuppressWarnings("all")
public class RestUtils {

	private static RestUtils restUtils = null;
	private RestTemplate restTemplate;
	
	public static RestUtils getInstance() {
		if (restUtils == null) {
			restUtils = new RestUtils();
			restUtils.restTemplate = SpringContexts.getBean("restTemplate", RestTemplate.class);
		}
		return restUtils;
	}

	public <T> T doGet(String url, Object request, Class<T> responseType) {
		return doExe(url, request, responseType, HttpMethod.GET);
	}

	public <T> T doPost(String url, Object request, Class<T> responseType) {
		return doExe(url, request, responseType, HttpMethod.POST);
	}

	public <T> T doDelete(String url, Object request, Class<T> responseType) {
		return doExe(url, request, responseType, HttpMethod.DELETE);
	}

	private <T> T doExe(String url, Object request, Class<T> responseType, HttpMethod method) {
		ResponseEntity<T> rss = doService(url, request, responseType, method);
		return rss.getBody();
	}

	public <T> ResponseEntity<T> doService(String url, Object request, Class<T> responseType, HttpMethod method) {
		HttpHeaders requestHeaders = new HttpHeaders();
		String cvalue = invocationToStr();

		requestHeaders.add("Authority", cvalue);
		requestHeaders.add("X-Requested-With", "XMLHttpRequest");
		requestHeaders.add("custmer", "jvm-http-client");
		HttpEntity<Object> requestEntity = new HttpEntity(request, requestHeaders);

		JSONObject jsonObj = new JSONObject();
		if ((method.equals(HttpMethod.GET)) && (request != null)) {
			String json = JSON.toJSONString(request);
			jsonObj = JSON.parseObject(json);
		}
		ResponseEntity<T> rss = null;
		try {
			rss = this.restTemplate.exchange(url, method, requestEntity, responseType, jsonObj);
		} catch (RuntimeException e) {
			throw e;
		}
		return rss;
	}

	public <T> T doGetWithSign(String url, Object request, Class<T> responseType) {
		return doExeWithSign(url, request, responseType, HttpMethod.GET);
	}

	public <T> T doPostWithSign(String url, Object request, Class<T> responseType) {
		return doExeWithSign(url, request, responseType, HttpMethod.POST);
	}

	public <T> T doDeleteWithSign(String url, Object request, Class<T> responseType) {
		return doExeWithSign(url, request, responseType, HttpMethod.DELETE);
	}

	private <T> T doExeWithSign(String url, Object request, Class<T> responseType, HttpMethod method) {
		ResponseEntity<T> rss = doServiceWithSign(url, request, responseType, method);
		return rss.getBody();
	}

	public <T> ResponseEntity<T> doServiceWithSign(String url, Object request, Class<T> responseType,
			HttpMethod method) {
		String prefix = null;
		String authFilePath = null;
		try {
			prefix = PropertiesUtils.getCustomerProperty("context.name");
			authFilePath = PropertiesUtils.getCustomerProperty(prefix + ".client.credential.path");
		} catch (IOException e) {
		}
		String json = "{}";
		if (request != null) {
			json = JSON.toJSONString(request);
		}

		SignEntity signEntity;
		if (method == HttpMethod.POST) {
			signEntity = SignMake.signEntity(url, json, prefix, authFilePath);
		} else {
			signEntity = SignMake.signEntity(url, null, method.toString(), prefix, authFilePath);
		}
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("sign", signEntity.getSign());
		HttpEntity<Object> requestEntity = new HttpEntity(request, requestHeaders);

		JSONObject jsonObj = new JSONObject();
		if (method.equals(HttpMethod.GET)) {
			jsonObj = JSON.parseObject(json);
		}
		ResponseEntity<T> rss = this.restTemplate.exchange(signEntity.getSignURL(), method, requestEntity, responseType,
				jsonObj);
		return rss;
	}

	private String invocationToStr() {
		String cvalue = "";
		Map<String, String> data = setInvocationInfo();
		if (MapUtil.isNotEmpty(data)) {
			Iterator<Map.Entry<String, String>> iterator = data.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry) iterator.next();
				if ((entry.getValue() != null) && (StrUtil.isNotEmpty((CharSequence) entry.getValue()))) {
					cvalue = cvalue + (String) entry.getKey() + "=" + (String) entry.getValue() + ";";
				}
			}
		}
		return cvalue;
	}

	private Map<String, String> setInvocationInfo() {
		Map<String, String> map = new HashMap<String, String>();
		if (InvocationInfoProxy.getCallid() != null) {
			map.put("u_callid", InvocationInfoProxy.getCallid());
		}
		if (InvocationInfoProxy.getLocale() != null) {
			map.put("u_locale", InvocationInfoProxy.getLocale());
		}
		if (InvocationInfoProxy.getSysid() != null) {
			map.put("u_sysid", InvocationInfoProxy.getSysid());
		}
		if (InvocationInfoProxy.getTenantid() != null) {
			map.put("tenantid", InvocationInfoProxy.getTenantid());
			map.put("current_tenant_id", InvocationInfoProxy.getTenantid());
		}
		if (InvocationInfoProxy.getTheme() != null) {
			map.put("u_theme", InvocationInfoProxy.getTheme());
		}
		if (InvocationInfoProxy.getTimeZone() != null) {
			map.put("u_timezone", InvocationInfoProxy.getTimeZone());
		}
		if (InvocationInfoProxy.getUserid() != null) {
			map.put("u_usercode", InvocationInfoProxy.getUserid());
			map.put("current_user_name", InvocationInfoProxy.getUserid());
		}
		if (InvocationInfoProxy.getUsername() != null) {
			map.put("u_username", InvocationInfoProxy.getUsername());
		}
		if (InvocationInfoProxy.getAppCode() != null) {
			map.put("u_appCode", InvocationInfoProxy.getAppCode());
		}
		if (InvocationInfoProxy.getProviderCode() != null) {
			map.put("u_providerCode", InvocationInfoProxy.getProviderCode());
		}
		if (InvocationInfoProxy.getToken() != null) {
			map.put("token", InvocationInfoProxy.getToken());
		}
		if (InvocationInfoProxy.getLogints() != null) {
			map.put("u_logints", InvocationInfoProxy.getLogints());
		}
		if (InvocationInfoProxy.getParamters() != null) {
			map.putAll(InvocationInfoProxy.getParamters());
		}
		map.put("call_thread_id", String.valueOf(MDC.get("call_thread_id")));
		return map;
	}

	public static String createParam(String url, Map<String, Object> map) {
		url = url + "?";
		for (String key : map.keySet()) {
			if (map.get(key) != null) {
				url = url + key + "=" + map.get(key) + "&";
			}
		}
		return url.substring(0, url.length() - 1);
	}
	
}
