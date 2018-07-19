package com.yonyou.iuap.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.mvc.type.SearchParams;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(PageRequest pageRequest, SearchParams searchParams) {

		System.out.print(pageRequest.getPageNumber()+"----"+pageRequest.getPageSize()
							+"----"+pageRequest.getSort());
		
		return JSON.toJSONString(searchParams);
	}

}
