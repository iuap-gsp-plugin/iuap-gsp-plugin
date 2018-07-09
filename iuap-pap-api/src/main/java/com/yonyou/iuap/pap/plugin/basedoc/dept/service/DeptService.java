package com.yonyou.iuap.pap.plugin.basedoc.dept.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.pap.plugin.basedoc.dept.dao.DeptMapper;
import com.yonyou.iuap.pap.plugin.basedoc.dept.entity.Dept;

@Component
public class DeptService implements IDeptService {

	private Logger log = LoggerFactory.getLogger(DeptService.class);

    public List<Dept> queryList(String name, Object value){
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	queryParams.put(name, value);
    	return this.queryList(queryParams);
    }
    
    public List<Dept> queryList(Map<String,Object> params){
    	return this.deptMapper.queryList(params);
    }
    
	/***************************************************/
	protected DeptMapper deptMapper;

	@Autowired
	public void setDeptMapper(DeptMapper deptMapper) {
		this.deptMapper = deptMapper;
	}
}
