package com.hoolai.manage.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.TblGroupSnBindDao;
import com.hoolai.manage.model.TblGroupSnBind;

@Repository
public class TblGroupSnBindDaoImpl extends GenericDaoImpl<TblGroupSnBind, Long> implements TblGroupSnBindDao {

	@Override
	public String namespace() {
		return TblGroupSnBind.class.getName();
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

	@Override
	public Long isHaveAdminAuth(Integer snId) {
		return sqlSessionTemplate.selectOne(this.namespace()+".isHaveAdminAuth",snId);
	}
	
}
