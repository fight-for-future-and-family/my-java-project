package com.hoolai.manage.dao.impl;

import org.springframework.stereotype.Repository;

import com.hoolai.manage.dao.AuthorityGroupDao;
import com.hoolai.manage.model.AuthorityGroup;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class AuthorityGroupDaoImpl extends GenericDaoImpl<AuthorityGroup, Long> implements AuthorityGroupDao {

	@Override
	public String namespace() {
		return AuthorityGroup.class.getName();
	}

	
}
