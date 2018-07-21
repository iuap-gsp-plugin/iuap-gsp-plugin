package com.yonyou.iuap.plugin.basedoc.dept.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.pap.plugin.basedoc.dept.api.vo.SyncDept;
import com.yonyou.iuap.plugin.basedoc.dept.dao.DeptMapper;
import com.yonyou.iuap.plugin.basedoc.dept.entity.Dept;
import com.yonyou.iuap.plugin.basedoc.org.entity.Organization;
import com.yonyou.iuap.plugin.basedoc.org.service.IOrgService;
import com.yonyou.iuap.utils.PropertyUtil;
import com.yonyou.uap.wb.utils.JsonResponse;

import cn.hutool.core.util.StrUtil;

@Component
public class DeptService implements IDeptService {

	private Logger log = LoggerFactory.getLogger(DeptService.class);
	private String createRestUrl = PropertyUtil.getPropertyByKey("iuap.dept.create.rest");
	private String updateRestUrl = PropertyUtil.getPropertyByKey("iuap.dept.update.rest");

	
	@Override
	public JsonResponse sync4Create(SyncDept syncDept) {
		Dept dept = this.convert4Create(syncDept);
		return RestUtils.getInstance().doPostWithSign(createRestUrl, 
						JSON.toJSONString(dept), JsonResponse.class);
	}

	@Override
	public JsonResponse sync4Update(SyncDept syncDept, Dept dept) {
		this.convert4Update(syncDept, dept);
		return RestUtils.getInstance().doPostWithSign(updateRestUrl, 
						JSON.toJSONString(dept), JsonResponse.class);
	}
	

    public List<Dept> queryList(String name, Object value){
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	queryParams.put(name, value);
    	return this.queryList(queryParams);
    }
    
    private List<Dept> queryList(Map<String,Object> params){
    	return this.deptMapper.queryList(params);
    }
    

	@Override
	public List<Map<String, Object>> queryListMap(String name, Object value) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put(name, value);
		return this.deptMapper.queryListMap(params);
	}
    
	@Override
	public Dept findUnique(String name, Object value) {
		List<Dept> listData = this.queryList(name, value);
		if(listData!=null && listData.size()==1) {
			return listData.get(0);
		}else {
			log.error("部门数据查询出错,数据记录不唯一, field="+name+",value="+value+", size="+listData.size());
			throw new RuntimeException("部门数据查询出错,size="+listData.size());
		}
	}

	@Override
	public String getIdByCode(String orgCode) {
		Dept dept = this.findUnique("code", orgCode);
		return dept.getId();
	}
	
	private Dept convert4Create(SyncDept syncDept) {
		Dept dept = new Dept();
		dept.setCode(syncDept.getCode());
		dept.setName(syncDept.getName());
		dept.setShort_name(syncDept.getShortName());
		
		if(!StrUtil.isBlankIfStr(syncDept.getParentCode())) {
			dept.setParent_id(this.getIdByCode(syncDept.getParentCode()));
		}
		
		if(StrUtil.isBlankIfStr(syncDept.getOrganizationCode())) {
			throw new RuntimeException("所属组织机构为空!");
		}else {
			Organization organization = orgService.findUnique("code", syncDept.getOrganizationCode());
			dept.setOrganization_id(organization.getId());
		}
		
		dept.setCreate_date(syncDept.getCreateDate());
		dept.setDescription(syncDept.getDescription());
		dept.setEffective_date(syncDept.getEffectiveDate());
		dept.setTenant_id("tenant");
		dept.setSys_id("wbalone");
		dept.setType("Dept");
		dept.setDr(0);
		dept.setTs(new Date());
		return dept;
	}

	private Dept convert4Update(SyncDept syncDept, Dept dept) {
		dept.setCode(syncDept.getCode());
		dept.setName(syncDept.getName());
		dept.setShort_name(syncDept.getShortName());
		if(!StrUtil.isBlankIfStr(syncDept.getParentCode())) {
			dept.setParent_id(this.getIdByCode(syncDept.getParentCode()));
		}
		
		if(StrUtil.isBlankIfStr(syncDept.getOrganizationCode())) {
			throw new RuntimeException("所属组织机构为空!");
		}else {
			Organization organization = orgService.findUnique("code", syncDept.getOrganizationCode());
			dept.setOrganization_id(organization.getId());
		}
		
		dept.setParent_id(syncDept.getParentCode());		//需调整
		dept.setDescription(syncDept.getDescription());
		dept.setCreate_date(syncDept.getCreateDate());
		dept.setEffective_date(syncDept.getEffectiveDate());
		dept.setDr(0);
		return dept;
	}
    
	/***************************************************/
	@Autowired
	protected DeptMapper deptMapper;
	@Autowired
	private IOrgService orgService;
	
}