package com.yonyou.iuap.plugin.basedoc.staff.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		staff.setId(UUID.randomUUID().toString());
		staff.setCreationtime(new Date());
		staff.setCreator(InvocationInfoProxy.getUserid());
		staff.setModifiedtime(new Date());
		staff.setModifier(InvocationInfoProxy.getUserid());
		staff.setTs(new Date());
		staff.setDr(0);
		return staffMapper.insert(staff);
	}

	@Override
	public int update(Staff staff) {
		staff.setModifiedtime(new Date());
		staff.setModifier(InvocationInfoProxy.getUserid());
		staff.setDr(0);
		staff.setNewTs(new Date());
		return staffMapper.update(staff);		
	}
	
	/*************************************************/
	@Autowired
	protected StaffMapper staffMapper;

}