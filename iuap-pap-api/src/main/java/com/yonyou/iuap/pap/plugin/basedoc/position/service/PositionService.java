package com.yonyou.iuap.pap.plugin.basedoc.position.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.pap.plugin.basedoc.dept.service.IDeptService;
import com.yonyou.iuap.pap.plugin.basedoc.org.service.IOrganizationService;
import com.yonyou.iuap.pap.plugin.basedoc.position.api.vo.SyncPosition;
import com.yonyou.iuap.pap.plugin.basedoc.position.dao.PositionMapper;
import com.yonyou.iuap.pap.plugin.basedoc.position.entity.Position;
import com.yonyou.iuap.pap.plugin.basedoc.user.service.UserService;

import cn.hutool.core.util.StrUtil;

@Service
public class PositionService implements IPositionService {

	private Logger log = LoggerFactory.getLogger(UserService.class);

    public List<Position> queryList(String name, Object value){
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	queryParams.put(name, value);
    	return this.queryList(queryParams);
    }
    

	@Override
	public List<Map<String, Object>> queryListMap(String name, Object value) {
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	queryParams.put(name, value);
		return this.queryListMap(queryParams);
	}
    
    public List<Position> queryList(Map<String,Object> params){
    	return this.positionMapper.queryList(params);
    }
    

	@Override
	public List<Map<String, Object>> queryListMap(Map<String, Object> params) {
		return this.positionMapper.queryListMap(params);
	}

	@Override
	public Position findUnique(String name, Object value) {
		List<Position> listData = this.queryList(name, value);
		if(listData!=null && listData.size()==1) {
			return listData.get(0);
		}else {
			log.error("岗位数据查询出错,数据记录不唯一, field="+name+", value="+value+", size="+listData.size());
			throw new RuntimeException("岗位数据查询出错,size="+listData.size());
		}
	}

	@Override
	public String getIdByCode(String orgCode) {
		Position position = this.findUnique("code", orgCode);
		return position.getId();
	}

	@Override
	public Position sync2Position(SyncPosition syncPosition) {
		Position position = new Position();
		position.setCode(syncPosition.getCode());
		position.setName(syncPosition.getName());
		
		if(StrUtil.isBlankIfStr(syncPosition.getOrgCode())) {
			throw new RuntimeException("所属组织机构为空!");
		}else {
			String orgId = organizationService.getIdByCode(syncPosition.getOrgCode());
			if(StrUtil.isBlank(orgId)) {
				throw new RuntimeException("所属组织机构不存在, orgCode="+syncPosition.getOrgCode());
			}
			position.setOrg_id(orgId);
		}
		
		if(StrUtil.isBlankIfStr(syncPosition.getDeptCode())) {
			throw new RuntimeException("所属部门为空!");
		}else {
			String deptId = deptService.getIdByCode(syncPosition.getDeptCode());
			if(StrUtil.isBlank(deptId)) {
				throw new RuntimeException("所属部门不存在, orgCode="+syncPosition.getOrgCode());
			}
			position.setDept_id(deptId);;
		}
		
		position.setEnable(syncPosition.getEnable());
		position.setSysid("wbalone");
		position.setTenantid("tenant");
		position.setDr(0);
		position.setTs(new Date());
		return position;
	}

	@Override
	public Position sync2Position(SyncPosition syncPosition, Position position) {
		position.setCode(syncPosition.getCode());
		position.setName(syncPosition.getName());
		
		if(StrUtil.isBlankIfStr(syncPosition.getOrgCode())) {
			throw new RuntimeException("所属组织机构为空!");
		}else {
			String orgId = organizationService.getIdByCode(syncPosition.getOrgCode());
			if(StrUtil.isBlank(orgId)) {
				throw new RuntimeException("所属组织机构不存在, orgCode="+syncPosition.getOrgCode());
			}
			position.setOrg_id(orgId);
		}
		
		if(StrUtil.isBlankIfStr(syncPosition.getDeptCode())) {
			throw new RuntimeException("所属部门为空!");
		}else {
			String deptId = deptService.getIdByCode(syncPosition.getDeptCode());
			if(StrUtil.isBlank(deptId)) {
				throw new RuntimeException("所属部门不存在, orgCode="+syncPosition.getOrgCode());
			}
			position.setDept_id(deptId);;
		}
		
		position.setEnable(syncPosition.getEnable());
		position.setDr(0);
		return position;
	}
    
	/***************************************************/
	@Autowired
	protected PositionMapper positionMapper;
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IDeptService deptService;

}