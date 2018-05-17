package com.hoolai.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.dao.TblGroupSnBindDao;
import com.hoolai.manage.model.TblGroupSnBind;
import com.hoolai.manage.service.TblGroupSnBindService_;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class TblGroupSnBindServiceImpl extends GenericServiceImpl<TblGroupSnBind, Long> implements
TblGroupSnBindService_ {

	@Autowired
	private TblGroupSnBindDao dao;

	public GenericDao<TblGroupSnBind, Long> getGenricDao() {
		return this.dao;
	}

	@Override
	public boolean isHaveAdminAuth(Integer snId) {
		long count = this.dao.isHaveAdminAuth(snId);
		return count > 0l;
	}
	

}
