package com.hoolai.manage.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.EtlengineManageDao;
import com.hoolai.manage.model.EtlengineManage;


@Repository
public class EtlengineManageDaoImpl extends GenericDaoImpl<EtlengineManage, Long> implements EtlengineManageDao {

	@Override
	public String namespace() {
		return EtlengineManage.class.getName();
	}

	@Autowired
	@Qualifier("etlEngineSqlSessionTemplate")
	protected SqlSessionTemplate sqlSessionTemplate;

	@Override
	public SqlSessionTemplate getSqlSessionTemplate() {
		if(this.sqlSessionTemplate==null){
			throw new RuntimeException("sqlSessionTemplate not to init!");
		}
		return sqlSessionTemplate;
	}

	
}
