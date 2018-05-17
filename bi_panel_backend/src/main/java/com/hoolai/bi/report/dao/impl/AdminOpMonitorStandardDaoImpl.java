package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.AdminOpMonitorStandardDao;
import com.hoolai.bi.report.model.entity.AdminOpMonitorStandard;
import com.hoolai.bi.report.vo.AdminOpMonitorStandardVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class AdminOpMonitorStandardDaoImpl extends GenericDaoImpl<AdminOpMonitorStandard, Long> implements AdminOpMonitorStandardDao {

	@Override
	public String namespace() {
		return AdminOpMonitorStandard.class.getName();
	}

	@Override
	public AdminOpMonitorStandard getByCode(AdminOpMonitorStandardVO monitor) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".getByCode", monitor);
	}

	@Override
	public List<AdminOpMonitorStandard> selectList(AdminOpMonitorStandardVO monitorStandardVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", monitorStandardVO);
	}


	
}
