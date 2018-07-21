package com.yonyou.iuap.plugin.basedoc.position.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.baseservice.support.generator.GeneratorManager;
import com.yonyou.iuap.cache.CacheManager;
import com.yonyou.iuap.plugin.basedoc.dept.service.IDeptService;
import com.yonyou.iuap.plugin.basedoc.org.service.IOrgService;
import com.yonyou.iuap.plugin.basedoc.position.dao.PositionMapper;
import com.yonyou.iuap.plugin.basedoc.position.entity.Position;
import com.yonyou.uap.wb.utils.JsonResponse;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.pap.plugin.basedoc.position.api.vo.SyncPosition;

import cn.hutool.core.util.StrUtil;

@Service
public class PositionService implements IPositionService{
	
	private static final Logger log = LoggerFactory.getLogger(PositionService.class);

	@Override
	public JsonResponse sync4Create(SyncPosition syncPosition) {
		Position position = this.convertPosition(syncPosition);
		JsonResponse response = new JsonResponse();
		if(this.insert(position) > 0) {
			response.success();
		}else {
			response.failed("新增岗位信息出错!");
		}
		return response;
	}

	@Override
	public JsonResponse sync4Update(SyncPosition syncPosition, Position position) {
		this.convertPosition(syncPosition, position);
		JsonResponse response = new JsonResponse();
		if(this.update(position) > 0) {
			response.success();
		}else {
			response.failed("新增岗位信息出错!");
		}
		return response;
	}
	
	public List<Position> queryList(Map<String,Object> params){
		return positionMapper.queryList(params);
	}
	
	public List<Position> queryList(String name, Object value){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(name, value);
		return positionMapper.queryList(params);
	}
	
	public Position findUnique(String name, Object value) {
		List<Position> listData = this.queryList(name, value);
		if(listData!=null && listData.size()==1) {
			return listData.get(0);
		}else {
			log.error("数据不唯一,请联系系统管理员!");
			throw new RuntimeException("数据不唯一,请联系系统管理员!");
		}
	}

	@Override
	public int save(Position entity) {
		if(StrUtil.isBlank(entity.getId())) {
			return this.insert(entity);
		}else {
			return this.update(entity);
		}
	}
	
	@Transactional
	@Override
	public int insert(Position entity) {
		List<Position> listData = this.queryList("code", entity.getCode());
		if(listData!=null && listData.size()>0) {
			log.error("岗位编码已存在，code="+entity.getCode());
			return -1;
		}
		
		Date now = new Date();
		entity.setId(GeneratorManager.generateUUID());
		entity.setCreationtime(now);
		entity.setModifiedtime(now);
		if(InvocationInfoProxy.getUserid()!=null) {
			entity.setCreator(InvocationInfoProxy.getUserid());
			entity.setModifier(InvocationInfoProxy.getUserid());
		}
		int resultCode = this.positionMapper.insert(entity);
		if(resultCode>0) {
			//cacheManager.getCacheDetail(PositionCacheDetail.class).save(entity.getId(),entity);
			cacheManager.hdel(this.getCacheKey(), entity.getId());
		}
		return resultCode;
	}
	
	@Transactional
	public int update(Position entity) {
		List<Position> listData = this.queryList("code", entity.getCode());
		int resultCode = 0;
		if(listData==null || listData.size()==0) {
			entity.setNewTs(new Date());
			resultCode = this.positionMapper.update(entity);
		}else if(listData.size()==1) {
			Position dbData = listData.get(0);
			if(entity.getId().equals(dbData.getId())) {			//是本条记录
				entity.setNewTs(new Date());
				resultCode = this.positionMapper.update(entity);
			}else {
				log.error("岗位编码已存在，请联系管理员：数据库记录={id:"+dbData.getId()+",code:"+dbData.getCode()
								+ "}; 待入库数据={"+entity.getId()+", code:"+entity.getCode()+"}");
				resultCode = -1;
			}
		}else {
			log.error("岗位编码存在多条记录，请联系管理员：岗位编码="+entity.getCode()+";\t 数据库记录数="+listData.size());
			resultCode = -1;
		}
		//缓存岗位信息
		if(resultCode>0) {
			//cacheManager.getCacheDetail(PositionCacheDetail.class).save(entity.getId(),entity);
			cacheManager.hdel(this.getCacheKey(), entity.getId());
		}
		return resultCode;
	}
	
	private String getCacheKey() {
		return "wbalone:tenant:position";
	}
	
	private Position convertPosition(SyncPosition syncPosition) {
		Position position = new Position();
		position.setCode(syncPosition.getCode());
		position.setName(syncPosition.getName());
		
		if(StrUtil.isBlankIfStr(syncPosition.getOrgCode())) {
			throw new RuntimeException("所属组织机构为空!");
		}else {
			String orgId = orgService.getIdByCode(syncPosition.getOrgCode());
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


	private Position convertPosition(SyncPosition syncPosition, Position position) {
		position.setCode(syncPosition.getCode());
		position.setName(syncPosition.getName());
		
		if(StrUtil.isBlankIfStr(syncPosition.getOrgCode())) {
			throw new RuntimeException("所属组织机构为空!");
		}else {
			String orgId = orgService.getIdByCode(syncPosition.getOrgCode());
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
	

	/*************************************/
	@Autowired
	private PositionMapper positionMapper;
	@Autowired
	private IOrgService orgService;
	@Autowired
	private IDeptService deptService;
	@Autowired
	private CacheManager cacheManager;

}