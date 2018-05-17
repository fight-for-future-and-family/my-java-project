package com.hoolai.bi.report.dao.impl;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.AdminOpMonitorDao;
import com.hoolai.bi.report.model.entity.AdminOpMonitor;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class AdminOpMonitorDaoImpl extends GenericDaoImpl<AdminOpMonitor, Long> implements AdminOpMonitorDao {

	@Override
	public String namespace() {
		return AdminOpMonitor.class.getName();
	}

	
}
