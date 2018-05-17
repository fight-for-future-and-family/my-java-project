package com.hoolai.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.dao.TblGroupGameBindDao;
import com.hoolai.manage.model.TblGroupGameBind;
import com.hoolai.manage.service.TblGroupGameBindService_;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class TblGroupGameBindServiceImpl extends GenericServiceImpl<TblGroupGameBind, Long> implements
TblGroupGameBindService_ {

	@Autowired
	private TblGroupGameBindDao dao;

	@Override
	public GenericDao<TblGroupGameBind, Long> getGenricDao() {
		return this.dao;
	}

	@Override
	public boolean isHaveAdminAuth(Integer gameId) {
		long count = this.dao.isHaveAdminAuth(gameId);
		return count > 0l;
	}
	

}
