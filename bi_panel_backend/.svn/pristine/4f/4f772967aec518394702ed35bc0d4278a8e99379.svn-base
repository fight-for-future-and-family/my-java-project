package com.hoolai.bi.report.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.ReportingEachTimeNCDao;
import com.hoolai.bi.report.model.entity.ReportingEachTimeNC;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class ReportingEachTimeNCDaoImpl extends GenericDaoImpl<ReportingEachTimeNC, Long> implements ReportingEachTimeNCDao {

	@Override
	public String namespace() {
		return ReportingEachTimeNC.class.getName();
	}

	@Autowired
	@Qualifier("realTimeSqlSessionTemplate")
	protected SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public SqlSessionTemplate getSqlSessionTemplate() {
		if(this.sqlSessionTemplate==null){
			throw new RuntimeException("sqlSessionTemplate not to init!");
		}
		return sqlSessionTemplate;
	}
	
}
