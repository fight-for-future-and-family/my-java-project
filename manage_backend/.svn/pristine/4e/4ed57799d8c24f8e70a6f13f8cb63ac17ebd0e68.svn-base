package com.hoolai.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.dao.AuthorityResourcesDao;
import com.hoolai.manage.model.AuthorityResources;
import com.hoolai.manage.service.AuthorityResourcesService;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class AuthorityResourcesServiceImpl extends GenericServiceImpl<AuthorityResources, Long> implements
AuthorityResourcesService {

	@Autowired
	private AuthorityResourcesDao dao;

	@Override
	public GenericDao<AuthorityResources, Long> getGenricDao() {
		return this.dao;
	}

	


}
