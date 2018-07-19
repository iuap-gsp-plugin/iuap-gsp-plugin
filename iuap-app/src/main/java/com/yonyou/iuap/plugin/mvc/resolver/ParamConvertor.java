package com.yonyou.iuap.plugin.mvc.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

import com.yonyou.iuap.mvc.type.SearchParams;

public interface ParamConvertor {
	
	public SearchParams convert(NativeWebRequest request, MethodParameter methodParameter);

}