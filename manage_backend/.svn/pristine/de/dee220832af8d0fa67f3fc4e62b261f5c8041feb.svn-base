package com.hoolai.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.dao.EtlengineManageDao;
import com.hoolai.manage.model.EtlengineManage;
import com.hoolai.manage.service.EtlengineManageService;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class EtlengineManageServiceImpl extends GenericServiceImpl<EtlengineManage, Long> implements
		EtlengineManageService {

	@Autowired
	private EtlengineManageDao dao;

	@Override
	public GenericDao<EtlengineManage, Long> getGenricDao() {
		return this.dao;
	}

}
