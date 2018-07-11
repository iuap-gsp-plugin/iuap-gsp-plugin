package com.yonyou.iuap.pap.plugin.basedoc.staff.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.pap.plugin.basedoc.position.dao.PositionMapper;
import com.yonyou.iuap.pap.plugin.basedoc.position.entity.Position;
import com.yonyou.iuap.pap.plugin.basedoc.staff.api.vo.SyncStaff;
import com.yonyou.iuap.pap.plugin.basedoc.staff.dao.StaffMapper;
import com.yonyou.iuap.pap.plugin.basedoc.staff.entity.Staff;

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
	public Staff sync2Staff(SyncStaff syncStaff) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Staff sync2Staff(SyncStaff syncStaff, Staff staff) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*************************************************/
	@Autowired
	protected StaffMapper staffMapper;

}