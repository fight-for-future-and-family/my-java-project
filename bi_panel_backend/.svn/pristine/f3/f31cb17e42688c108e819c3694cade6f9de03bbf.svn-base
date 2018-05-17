package com.hoolai.bi.report.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.RealTimeResultDao;
import com.hoolai.bi.report.model.entity.RealTimeResult;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class RealTimeResultDaoImpl extends GenericDaoImpl<RealTimeResult, Long> implements RealTimeResultDao {

	@Override
	public String namespace() {
		return RealTimeResult.class.getName();
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
