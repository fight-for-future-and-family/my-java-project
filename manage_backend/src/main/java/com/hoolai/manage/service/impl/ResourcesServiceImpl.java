package com.hoolai.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.dao.ResourcesDao;
import com.hoolai.manage.model.Resources;
import com.hoolai.manage.service.ResourcesService;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class ResourcesServiceImpl extends GenericServiceImpl<Resources, Long> implements
		ResourcesService {

	@Autowired
	private ResourcesDao dao;

	@Override
	public GenericDao<Resources, Long> getGenricDao() {
		return this.dao;
	}

	@Override
	public List<Resources> getResourcesByAuthorityId(Long authId) {
		return this.dao.getResourcesByAuthorityId(authId);
	}

	@Override
	public List<Resources> getResourcesByUserId(Long userId) {
		return this.dao.getResourcesByUserId(userId);
	}

	


}
