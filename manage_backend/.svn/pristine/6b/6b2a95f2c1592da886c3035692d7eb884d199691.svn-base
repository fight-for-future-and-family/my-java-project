package com.hoolai.manage.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.TblGroupGameBindDao;
import com.hoolai.manage.model.TblGroupGameBind;

@Repository
public class TblGroupGameBindDaoImpl extends GenericDaoImpl<TblGroupGameBind, Long> implements TblGroupGameBindDao {

	@Override
	public String namespace() {
		return TblGroupGameBind.class.getName();
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
	public Long isHaveAdminAuth(Integer gameId) {
		return sqlSessionTemplate.selectOne(this.namespace()+".isHaveAdminAuth",gameId);
	}
	
}
