package com.yonyou.iuap.plugin.basedoc.position.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.baseservice.support.generator.GeneratorManager;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.plugin.basedoc.position.dao.PositionMapper;
import com.yonyou.iuap.plugin.basedoc.position.entity.Position;

import cn.hutool.core.util.StrUtil;

@Service
public class PositionService implements IPositionService{
	
	private static final Logger log = LoggerFactory.getLogger(PositionService.class);
	
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
		return this.positionMapper.insert(entity);
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
		return resultCode;
	}
	

	/*************************************/
	@Autowired
	private PositionMapper positionMapper;

}