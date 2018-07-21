package com.yonyou.iuap.plugin.basedoc.staff.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.baseservice.support.generator.GeneratorManager;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.pap.plugin.basedoc.staff.api.vo.SyncMainJob;
import com.yonyou.iuap.pap.plugin.basedoc.staff.api.vo.SyncStaff;
import com.yonyou.iuap.plugin.basedoc.dept.service.IDeptService;
import com.yonyou.iuap.plugin.basedoc.position.service.IPositionService;
import com.yonyou.iuap.plugin.basedoc.staff.dao.StaffMapper;
import com.yonyou.iuap.plugin.basedoc.staff.entity.MainJobInfo;
import com.yonyou.iuap.plugin.basedoc.staff.entity.Staff;
import com.yonyou.uap.wb.utils.JsonResponse;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

@Service
public class StaffService implements IStaffService {
	
	private Logger log = LoggerFactory.getLogger(StaffService.class);
	

	@Override
	public JsonResponse sync4Create(SyncStaff syncStaff) {
		Staff staff = this.convertStaff(syncStaff);
		JsonResponse response = new JsonResponse();
		if(this.insert(staff) > 0) {
			response.success();
		}else {
			response.failed("新增岗位信息出错!");
		}
		return response;
	}

	@Override
	public JsonResponse sync4Update(SyncStaff syncStaff, Staff staff) {
		this.convertStaff(syncStaff, staff);
		JsonResponse response = new JsonResponse();
		if(this.update(staff) > 0) {
			response.success();
		}else {
			response.failed("更新岗位信息出错!");
		}
		return response;
	}

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
		//保存兼职信息——如需要可添加
		//保存银行账户——如需要可添加
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
	
	
	private Staff convertStaff(SyncStaff syncStaff) {
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
			listMainjobinfo.add(this.convertMainJobInfo(staff.getId(), syncStaff.getSyncMainJob()));
			staff.setMainjobinfo(listMainjobinfo);
		}
		return staff;
	}


	public Staff convertStaff(SyncStaff syncStaff, Staff staff) {
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
			listMainjobinfo.add(this.convertMainJobInfo(staff.getId(), syncStaff.getSyncMainJob()));
			staff.setMainjobinfo(listMainjobinfo);
		}
		return staff;
	}
	
	private MainJobInfo convertMainJobInfo(String staffId, SyncMainJob syncMainJob) {
		MainJobInfo mainJob = new MainJobInfo();
		//校验人员——主职信息所属岗位、部门、组织机构是否合法
		boolean ibSetOrg = false;
		if(StrUtil.isBlank(syncMainJob.getPositionCode())) {
			ibSetOrg = this.checkSetPosition(syncMainJob, mainJob);
		}else {
			ibSetOrg = this.checkSetDept(syncMainJob, mainJob);
		}
		if(ibSetOrg) {									//职信息所属岗位、部门、组织机构合法
			mainJob.setMainjob(true);
			mainJob.setStaffid(staffId);
			mainJob.setStartservetime(syncMainJob.getStartservetime());
			mainJob.setEndservetime(syncMainJob.getEndservetime());
			return mainJob;
		}else {
			throw new RuntimeException("人员主职信息设置失败!");
		}
	}
	
	private boolean checkSetPosition(SyncMainJob syncMainJob, MainJobInfo mainJob) {
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
				return true;
			}else {
				StringBuffer strbError = new StringBuffer("组织机构编码、部门编码、岗位编码与数据库映射关系不一致：");
				strbError.append("接口数据——{position:").append(syncMainJob.getPositionCode())
						.append(", dept:").append(syncMainJob.getDeptCode())
						.append(", organization:").append(syncMainJob.getOrgCode())
						.append("}; 数据库数据——{position:").append(positionCode)
						.append(", dept:").append(deptCode).append("}; organization:").append(orgCode).append("}");
				log.error(strbError.toString());
			}
		} else {
			log.error("人员主职信息所属岗位、部门、组织机构不合法，记录数="+listPosition.size()+";");
		}
		return false;
	}
	
	/**
	 * 校验部分是否合法、并设置部门、组织机构信息
	 * @param syncMainJob
	 * @param mainJob
	 */
	private boolean checkSetDept(SyncMainJob syncMainJob, MainJobInfo mainJob) {
		if(StrUtil.isBlankIfStr(syncMainJob.getDeptCode())) {
			log.error("部门编码为空:"+syncMainJob.getDeptCode());
			return false;
		}
		List<Map<String,Object>> listData = deptService.queryListMap("code", syncMainJob.getDeptCode());
		if(listData!=null && listData.size()==1) {
			Map<String,Object> dbDeptMap = listData.get(0);
			String dbOrgCode = MapUtil.getStr(dbDeptMap, "orgCode");
			if(syncMainJob.getOrgCode()!=null && syncMainJob.getOrgCode().equals(dbOrgCode)) {
				mainJob.setDeptid(MapUtil.getStr(dbDeptMap, "id"));
				mainJob.setOrgid(MapUtil.getStr(dbDeptMap, "orgId"));
				return true;
			}else {
				log.error("组织机构与部门数据关系与系统不一致，请检查数据:\r\n"+JSON.toJSONString(syncMainJob));
			}
		}else {
			log.error("系统中检索到的部门记录数不合法，记录数=" + (listData==null?0:listData.size()) + 
						"\r\n" + JSON.toJSONString(syncMainJob));
		}
		return false;
	}
	
	/*************************************************/
	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private MainJobInfoService mainJobInfoService;
	@Autowired
	private IPositionService positionService;
	@Autowired
	private IDeptService deptService;
	

}