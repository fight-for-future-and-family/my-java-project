package com.hoolai.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.dao.DRelationshipDao;
import com.hoolai.manage.model.DRelationship;
import com.hoolai.manage.service.DRelationshipService_;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class DRelationshipServiceImpl extends GenericServiceImpl<DRelationship, Long> implements
DRelationshipService_ {

	@Autowired
	private DRelationshipDao dao;

	@Override
	public GenericDao<DRelationship, Long> getGenricDao() {
		return this.dao;
	}

}
