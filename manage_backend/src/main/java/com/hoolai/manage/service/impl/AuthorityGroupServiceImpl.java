package com.hoolai.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.dao.AuthorityGroupDao;
import com.hoolai.manage.model.AuthorityGroup;
import com.hoolai.manage.service.AuthorityGroupService;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class AuthorityGroupServiceImpl extends GenericServiceImpl<AuthorityGroup, Long> implements
		AuthorityGroupService {

	@Autowired
	private AuthorityGroupDao dao;

	@Override
	public GenericDao<AuthorityGroup, Long> getGenricDao() {
		return this.dao;
	}

	


}
