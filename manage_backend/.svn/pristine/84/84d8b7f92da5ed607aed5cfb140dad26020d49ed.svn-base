package com.hoolai.manage.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.OldReportUserDao;
import com.hoolai.manage.model.OldReportUser;
import com.hoolai.manage.vo.OldReportUserVO;

@Repository
public class OldReportUserDaoImpl extends GenericDaoImpl<OldReportUser, Long> implements OldReportUserDao {

	@Override
	public String namespace() {
		return OldReportUser.class.getName();
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
	public OldReportUser getUserByEmail(OldReportUserVO oldReportUserVO) {
		return sqlSessionTemplate.selectOne(this.namespace()+".getUserByEmail",oldReportUserVO);
	}

	@Override
	public Long getMaxUserId() {
		return sqlSessionTemplate.selectOne(this.namespace()+".getMaxUserId");
	}

	@Override
	public void removeByEmail(String email) {
		sqlSessionTemplate.delete(this.namespace()+".removeByEmail", email);
	}
	
}
