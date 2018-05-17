package com.hoolai.bi.report.dao.impl;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.DauStructDao;
import com.hoolai.bi.report.model.entity.DauStruct;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class DauStructDaoImpl extends GenericDaoImpl<DauStruct, Long> implements DauStructDao {

	@Override
	public String namespace() {
		return DauStruct.class.getName();
	}
	
}
