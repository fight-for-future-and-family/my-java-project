package com.hoolai.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.dao.OldReportUserDao;
import com.hoolai.manage.model.OldReportUser;
import com.hoolai.manage.service.OldReportUserService_;
import com.hoolai.manage.vo.OldReportUserVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class OldReportUserServiceImpl extends GenericServiceImpl<OldReportUser, Long> implements
OldReportUserService_ {

	@Autowired
	private OldReportUserDao dao;

	@Override
	public GenericDao<OldReportUser, Long> getGenricDao() {
		return this.dao;
	}

	@Override
	public OldReportUser getUserByEmail(OldReportUserVO oldReportUserVO) {
		return this.dao.getUserByEmail(oldReportUserVO);
	}

	@Override
	public Long getMaxUserId() {
		return this.dao.getMaxUserId();
	}

	@Override
	public void removeByEmail(String email) {
		this.dao.removeByEmail(email);
	}
	

}
