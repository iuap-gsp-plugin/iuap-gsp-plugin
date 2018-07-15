package com.yonyou.iuap.pap.plugin.basedoc.staff.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.pap.plugin.basedoc.position.service.IPositionService;
import com.yonyou.iuap.pap.plugin.basedoc.staff.api.vo.SyncMainJob;
import com.yonyou.iuap.pap.plugin.basedoc.staff.api.vo.SyncStaff;
import com.yonyou.iuap.pap.plugin.basedoc.staff.dao.StaffMapper;
import com.yonyou.iuap.pap.plugin.basedoc.staff.entity.MainJobInfo;
import com.yonyou.iuap.pap.plugin.basedoc.staff.entity.Staff;

import cn.hutool.core.map.MapUtil;

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
		Staff staff = new Staff();
		staff.setCode(syncStaff.getCode());
		staff.setName(syncStaff.getName());
		staff.setBirthdate(syncStaff.getBirthdate());
		staff.setCerttypeid(syncStaff.getCerttypeid());
		staff.setCertnum(syncStaff.getCertnum());
		staff.setEducationbg(syncStaff.getEducationbg());
		staff.setEnable(syncStaff.getEnable());
		staff.setMaritalstatus(syncStaff.getMaritalstatus());
		staff.setMobile(syncStaff.getMobile());
		staff.setEmail(syncStaff.getEmail());
		staff.setParticipateworkdate(syncStaff.getParticipateworkdate());
		staff.setUserid(syncStaff.getUserCode());							//需要调整逻辑
		staff.setGender(syncStaff.getGender());
		
		if(syncStaff.getSyncMainJob()!=null) {
			List<MainJobInfo> listMainjobinfo = new ArrayList<MainJobInfo>();
			listMainjobinfo.add(this.sync2MainJobInfo(staff.getId(), syncStaff.getSyncMainJob()));
			staff.setMainjobinfo(listMainjobinfo);
		}
		return staff;
	}

	@Override
	public Staff sync2Staff(SyncStaff syncStaff, Staff staff) {
		staff.setCode(syncStaff.getCode());
		staff.setName(syncStaff.getName());
		staff.setBirthdate(syncStaff.getBirthdate());
		staff.setCerttypeid(syncStaff.getCerttypeid());						//需转换
		staff.setCertnum(syncStaff.getCertnum());
		staff.setEducationbg(syncStaff.getEducationbg());
		staff.setEnable(syncStaff.getEnable());
		staff.setMaritalstatus(syncStaff.getMaritalstatus());				//需转换
		staff.setMobile(syncStaff.getMobile());
		staff.setEmail(syncStaff.getEmail());
		staff.setParticipateworkdate(syncStaff.getParticipateworkdate());
		staff.setUserid(syncStaff.getUserCode());							//需转换
		staff.setGender(syncStaff.getGender());
		
		//转换主职信息
		if(syncStaff.getSyncMainJob()!=null) {
			List<MainJobInfo> listMainjobinfo = new ArrayList<MainJobInfo>();
			listMainjobinfo.add(this.sync2MainJobInfo(staff.getId(), syncStaff.getSyncMainJob()));
			staff.setMainjobinfo(listMainjobinfo);
		}
		return staff;
	}
	
	public MainJobInfo sync2MainJobInfo(String staffId, SyncMainJob syncMainJob) {
		MainJobInfo mainJob = new MainJobInfo();
		mainJob.setMainjob(true);
		mainJob.setStaffid(staffId);
		//校验人员——主职信息所属岗位、部门、组织机构是否合法
		this.checkSetPosition(syncMainJob, mainJob);
		mainJob.setStartservetime(syncMainJob.getStartservetime());
		mainJob.setEndservetime(syncMainJob.getEndservetime());
		return mainJob;
	}
	
	private void checkSetPosition(SyncMainJob syncMainJob, MainJobInfo mainJob) {
		List<Map<String, Object>> listPosition = positionService.queryListMap("code", syncMainJob.getPositionCode());
		if(listPosition!=null && listPosition.size()==1) {
			String positionCode = MapUtil.getStr(listPosition.get(0), "code");
			String positionId = MapUtil.getStr(listPosition.get(0), "id");
			String deptCode = MapUtil.getStr(listPosition.get(0), "deptCode");
			String deptId = MapUtil.getStr(listPosition.get(0), "dept_id");
			String orgCode = MapUtil.getStr(listPosition.get(0), "orgCode");
			String orgId = MapUtil.getStr(listPosition.get(0), "org_id");
			if(syncMainJob.getOrgCode().equals(orgCode) && syncMainJob.getDeptCode().equals(deptCode)) {
				mainJob.setPositionid(positionId);
				mainJob.setDeptid(deptId);
				mainJob.setOrgid(orgId);
				return;
			}else {
				StringBuffer strbError = new StringBuffer("组织机构编码、部门编码、岗位编码与数据库映射关系不一致：");
				strbError.append("接口数据——{position:").append(syncMainJob.getPositionCode())
						.append(", dept:").append(syncMainJob.getDeptCode())
						.append(", organization:").append(syncMainJob.getOrgCode())
						.append("}; 数据库数据——{position:").append(positionCode)
						.append(", dept:").append(deptCode).append("}; organization:").append(orgCode).append("}");
				log.error(strbError.toString());
				throw new RuntimeException(strbError.toString());
			}
		} else {
			log.error("人员主职信息所属岗位、部门、组织机构不合法，记录数="+listPosition.size()+";");
			throw new RuntimeException("人员主职信息所属岗位、部门、组织机构不合法，记录数="+listPosition.size());
		}
	}
	
	/*************************************************/
	@Autowired
	protected StaffMapper staffMapper;
	@Autowired
	private IPositionService positionService;

}