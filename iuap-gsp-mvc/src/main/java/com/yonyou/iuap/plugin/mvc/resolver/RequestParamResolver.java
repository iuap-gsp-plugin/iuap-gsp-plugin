package com.yonyou.iuap.plugin.mvc.resolver;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.context.request.NativeWebRequest;

import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.exchange.ParamExchanger;
import com.yonyou.iuap.mvc.type.SearchParams;

/**
 * 说明：自定义扩展RequestParamResolver
 * @author Aton
 * 2018年7月19日
 */
public class RequestParamResolver extends com.yonyou.iuap.mvc.RequestArgumentResolver{
	
	private ParamConvertor paramConvertor;
	
	public void setParamConvertor(ParamConvertor paramConvertor) {
		this.paramConvertor = paramConvertor;
	}

	public ParamConvertor getParamConvertor() {
		return paramConvertor;
	}

	@Override
	protected Object resolveName(String name, MethodParameter methodParameter, NativeWebRequest request) throws Exception {
		Class<?> clazz = methodParameter.getParameterType();
		if (PageRequest.class.equals(clazz)) {					//装配PageRequest
			return this.getPageRequest(request);
		}
		if (Sort.class.equals(clazz)) {							//装配Sort
			return getQuerySort(request);
		}
		if (SearchParams.class.equals(clazz)) {					//装配查询参数
			return this.getSearchParams(request, methodParameter);
		}
		return null;
	}
	
	protected PageRequest getPageRequest(NativeWebRequest request) {
		String no = request.getParameter("pageIndex");
		String size = request.getParameter("pageSize");
		int pageNum = 0;
		int pageSize = 10;
		if ((StringUtils.isNumeric(no)) || (StringUtils.isNumeric(size))) {
			pageSize = Integer.valueOf(size).intValue();
			pageNum = Integer.valueOf(no).intValue();
		}
		if (pageSize == 0) {
			pageSize = 10;
		}
		Sort sort = this.getQuerySort(request);
		return new PageRequest(pageNum, pageSize, sort);
	}
	
	protected SearchParams getSearchParams(NativeWebRequest request, MethodParameter methodParameter) {
		FrontModelExchange annotation = methodParameter.getParameterAnnotation(FrontModelExchange.class);
		if(annotation == null && paramConvertor!=null) {
			return paramConvertor.convert(request, methodParameter);
		}else {
			return new ParamExchanger(request, methodParameter).exchange();
		}
	}

}