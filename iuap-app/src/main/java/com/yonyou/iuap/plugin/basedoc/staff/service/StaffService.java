package com.yonyou.iuap.plugin.basedoc.staff.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.baseservice.support.generator.GeneratorManager;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.plugin.basedoc.staff.dao.StaffMapper;
import com.yonyou.iuap.plugin.basedoc.staff.entity.Staff;

@Service
public class StaffService implements IStaffService {
	
	private Logger log = LoggerFactory.getLogger(StaffService.class);

	@Override
	public List<Staff> queryList(String name, Object value) {
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	queryParams.put(name, value);
    	return this.queryList(queryParams);
	}
	
    public List<Staff> queryList(Map<String,Object> params){
    	return this.staffMapper.queryList(params);
    }

	@Override
	public Staff findUnique(String name, Object value) {
		List<Staff> listData = this.queryList(name, value);
		if(listData!=null && listData.size()==1) {
			return listData.get(0);
		}else {
			log.error("人员数据查询出错,数据记录不唯一, field="+name+", value="+value+", size="+listData.size());
			throw new RuntimeException("人员数据查询出错,size="+listData.size());
		}
	}

	@Override
	public String getIdByCode(String staffCode) {
		Staff staff = this.findUnique("code", staffCode);
		return staff.getId();
	}
	
	@Override
	public void save(Staff staff) {
		try {
			if(staff.getId()==null||staff.getId().trim().length()==0) {
				this.insert(staff);
			}else {
				this.update(staff);
			}
		}catch (Exception exp) {
			throw new RuntimeException("人员信息保存出错！", exp);
		}
	}

	@Override
	public int insert(Staff staff) {
		//保存人员信息
		Date now = new Date();
		staff.setId(GeneratorManager.generateUUID());
		staff.setCreationtime(now);
		staff.setCreator(InvocationInfoProxy.getUserid());
		staff.setModifiedtime(now);
		staff.setModifier(InvocationInfoProxy.getUserid());
		staff.setTs(new Date());
		staff.setDr(0);
		int resultCode = staffMapper.insert(staff);
		//保存主职信息
		mainJobInfoService.save4Staff(staff.getId(), staff.getMainjobinfo());
		//保存兼职信息
				
		//保存银行账户
		
		return resultCode;
	}

	@Override
	public int update(Staff staff) {
		//更新保存用户信息
		staff.setModifiedtime(new Date());
		staff.setModifier(InvocationInfoProxy.getUserid());
		staff.setDr(0);
		staff.setNewTs(new Date());
		int resultCode = staffMapper.update(staff);

		//保存主职信息
		mainJobInfoService.save4Staff(staff.getId(), staff.getMainjobinfo());

		return resultCode;
	}
	
	/*************************************************/
	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private MainJobInfoService mainJobInfoService;

}