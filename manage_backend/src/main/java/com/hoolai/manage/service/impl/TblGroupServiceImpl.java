package com.hoolai.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.dao.TblGroupDao;
import com.hoolai.manage.model.TblGroup;
import com.hoolai.manage.service.TblGroupService_;
import com.hoolai.manage.vo.TblGroupVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class TblGroupServiceImpl extends GenericServiceImpl<TblGroup, Long> implements
TblGroupService_ {

	@Autowired
	private TblGroupDao dao;

	@Override
	public GenericDao<TblGroup, Long> getGenricDao() {
		return this.dao;
	}

	@Override
	public Long getGroupMaxId() {
		return this.dao.getGroupMaxId();
	}

	@Override
	public TblGroupVO getOldReportGameInfo(TblGroupVO games) {
		return this.dao.getOldReportGameInfo(games);
	}
	

}
