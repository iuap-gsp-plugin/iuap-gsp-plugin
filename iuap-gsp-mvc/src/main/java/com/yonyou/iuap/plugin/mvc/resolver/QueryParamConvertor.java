package com.yonyou.iuap.plugin.mvc.resolver;

import java.util.Iterator;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

import com.yonyou.iuap.mvc.type.SearchParams;

import cn.hutool.core.util.StrUtil;

public class QueryParamConvertor implements ParamConvertor{
	
	private String search_prefix = "search_";
	private int search_prefix_length = search_prefix.length();

	@Override
	public SearchParams convert(NativeWebRequest request, MethodParameter methodParameter) {
		SearchParams searchParams = new SearchParams();
		Iterator<String> paramNames = request.getParameterNames();
		paramNames.forEachRemaining(paramName->{
			if(paramName.startsWith(search_prefix)){
				String propertyName = paramName.substring(search_prefix_length);
		        String[] values = request.getParameterValues(paramName);
		        this.addParam(searchParams, propertyName, values);
			}
		});
		return searchParams;
	}
	
	private void addParam(SearchParams params, String propertyName, String[] values) {
		if (values != null && values.length != 0) {
			if (values.length > 1) {
				params.addCondition(propertyName, values);
			} else {
				if(!StrUtil.isBlankIfStr(values[0])) {			//不为空串，则条件有效，否则忽略
					params.addCondition(propertyName, values[0]);
				}
			}
		}
	}

}