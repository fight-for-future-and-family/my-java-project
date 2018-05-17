package com.hoolai.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.dao.AuthoritiesDao;
import com.hoolai.manage.model.Authorities;
import com.hoolai.manage.service.AuthoritiesService;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class AuthoritiesServiceImpl extends GenericServiceImpl<Authorities, Long> implements
		AuthoritiesService {

	@Autowired
	private AuthoritiesDao dao;

	@Override
	public GenericDao<Authorities, Long> getGenricDao() {
		return this.dao;
	}

	@Override
	public List<Authorities> getAuthoritiesByUserId(Long userId) {
		return this.dao.getAuthoritiesByUserId(userId);
	}

	@Override
	public List<Authorities> getAuthoritiesByResourcesId(Long resourceId) {
		return this.dao.getAuthoritiesByResourcesId(resourceId);
	}

	@Override
	public List<Authorities> getAllAuthorities() {
		return this.dao.getAllAuthorities();
	}

	


}
