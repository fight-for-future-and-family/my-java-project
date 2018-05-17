package com.hoolai.manage.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.DRelationshipDao;
import com.hoolai.manage.model.DRelationship;

@Repository
public class DRelationshipDaoImpl extends GenericDaoImpl<DRelationship, Long> implements DRelationshipDao {

	@Override
	public String namespace() {
		return DRelationship.class.getName();
	}

	@Autowired
	@Qualifier("oldReportSqlSessionTemplate")
	protected SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public SqlSessionTemplate getSqlSessionTemplate() {
		if(this.sqlSessionTemplate==null){
			throw new RuntimeException("sqlSessionTemplate not to init!");
		}
		return sqlSessionTemplate;
	}

	
}
