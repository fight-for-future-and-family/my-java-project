package com.hoolai.bi.report.dao.impl;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.UserCpaCpsSourceDao;
import com.hoolai.bi.report.model.entity.UserCpaCpsSource;
import com.hoolai.bi.report.vo.UserCpaCpsSourceVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class UserCpaCpsSourceDaoImpl extends GenericDaoImpl<UserCpaCpsSource, Long> implements UserCpaCpsSourceDao {

	@Override
	public String namespace() {
		return UserCpaCpsSource.class.getName();
	}

	@Override
	public void removeAuth(UserCpaCpsSourceVO userSourceVO) {
		this.sqlSessionTemplate.delete(this.namespace()+".removeAuth", userSourceVO);
	}

	@Override
	public UserCpaCpsSource selectBySource(UserCpaCpsSourceVO userSourceVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectByCpaSource", userSourceVO);
	}
	
	@Override
	public void removeCpaAuth(UserCpaCpsSourceVO userSourceVO) {
		this.sqlSessionTemplate.delete(this.namespace()+".removeCpaAuth", userSourceVO);
	}

	@Override
	public UserCpaCpsSource selectByCpaSource(UserCpaCpsSourceVO userSourceVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectByCpaSource", userSourceVO);
	}

	@Override
	public Long insertCpaEntity(UserCpaCpsSource userSource) {
		return (long) this.sqlSessionTemplate.insert("insertCpaEntity", userSource);
	}
	
}
