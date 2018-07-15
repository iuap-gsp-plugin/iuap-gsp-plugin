package com.yonyou.iuap.plugin.basedoc.staff.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.baseservice.support.generator.GeneratorManager;
import com.yonyou.iuap.plugin.basedoc.staff.dao.MainJobInfoMapper;
import com.yonyou.iuap.plugin.basedoc.staff.entity.MainJobInfo;

import cn.hutool.core.util.StrUtil;


@Service
public class MainJobInfoService {
	
	private Logger log = LoggerFactory.getLogger(MainJobInfoService.class);
	
	@Autowired
	private MainJobInfoMapper mainJobInfoMapper;
	
    public List<MainJobInfo> queryList(Map<String,Object> params){
    	return this.mainJobInfoMapper.queryList(params);
    }

    /**
     * 根据字段值查询列表
     * @param name
     * @param value
     * @return
     */
	public List<MainJobInfo> queryList(String name, Object value) {
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	queryParams.put(name, value);
    	return mainJobInfoMapper.queryList(queryParams);
	}
	
    /**
     * 查询唯一数据
     * @param name
     * @param value
     * @return
     */
	public MainJobInfo findUnique(String name, Object value) {
		List<MainJobInfo> listData = this.queryList(name, value);
		if(listData!=null && listData.size()==1) {
			return listData.get(0);
		}else {
			log.error("人员数据查询出错,数据记录不唯一, field="+name+", value="+value+", size="+listData.size());
			throw new RuntimeException("人员数据查询出错,size="+listData.size());
		}
	}
	
	/**
	 * 保存人员主职信息：先删除被移除的数据
	 * @param staffId
	 * @param listMainJob
	 */
	public void save4Staff(String staffId, List<MainJobInfo> listMainJob) {
		//删除人员应被移除的主职信息
		this.deleteByStaff(staffId, listMainJob);
		//批量保存人员主职信息
		this.saveBatch(listMainJob);
	}
	
	public void saveBatch(List<MainJobInfo> listMainJob) {
		if(listMainJob!=null) {
			listMainJob.forEach((curMainJob)->{
				this.save(curMainJob);
			});
		}
	}
	
	/**
	 * 保存人员主职信息
	 * @param mainJob
	 * @return
	 */
	public int save(MainJobInfo mainJob) {
		if(StrUtil.isBlank(mainJob.getId())) {
			return this.insert(mainJob);
		} else {
			return this.update(mainJob);
		}
	}
	
	/**
	 * 新增保存
	 * @param entity
	 * @return
	 */
	public int insert(MainJobInfo entity) {
		entity.setId(GeneratorManager.generateUUID());
		return this.mainJobInfoMapper.insert(entity);
	}
	
	/**
	 * 更新保存
	 * @param mainJob
	 * @return
	 */
	public int update(MainJobInfo mainJob) {
		int resultCode = this.mainJobInfoMapper.update(mainJob);
		if(resultCode!=1) {
			log.error("数据更新出错,mainJob="+JSON.toJSONString(mainJob));
			throw new RuntimeException("数据更新出错,mainJob="+JSON.toJSONString(mainJob));
		}else {
			return resultCode;
		}
	}
	
	/**
	 * 根据删除主职信息
	 * @param staffId
	 * @param listMainJob
	 */
	public void deleteByStaff(String staffId, List<MainJobInfo> listMainJob) {
		Set<String> mainJobIds = new HashSet<String>();
		if(listMainJob!=null) {
			listMainJob.forEach(curMainJob->{
				mainJobIds.add(curMainJob.getId());
			});
		}
		
		//获取数据库当前存取的主职信息ID
		List<MainJobInfo> listDBDatas = this.queryList("staffId", staffId);
		Set<String> mainJobIds2Del = new HashSet<String>();
		listDBDatas.forEach(dbMainJob->{
			if(!mainJobIds.contains(dbMainJob.getId())) {
				mainJobIds2Del.add(dbMainJob.getId());
			}
		});

		//执行删除操作
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staffId", staffId);
		params.put("ids", mainJobIds2Del);
		this.delete(params);
	}
	
	/**
	 * 批量删除
	 * @param listMainJob
	 */
	public void deleteBatch(List<MainJobInfo> listMainJob) {
		Set<String> mainJobIds2Del = new HashSet<String>();
		listMainJob.forEach(mainJob->{
			mainJobIds2Del.add(mainJob.getId());
		});
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ids", listMainJob);
		this.delete(params);
	}
	
	public int delete(Map<String,Object> params) {
		return mainJobInfoMapper.delete(params);
	}

}