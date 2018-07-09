package com.yonyou.iuap.pap.plugin.basedoc.dept.service;

import java.util.List;
import com.yonyou.iuap.pap.plugin.basedoc.dept.entity.Dept;

public interface IDeptService {

	public List<Dept> queryList(String name, Object value);

}