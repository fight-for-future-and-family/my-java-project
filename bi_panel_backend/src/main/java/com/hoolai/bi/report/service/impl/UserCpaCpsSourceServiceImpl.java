package com.hoolai.bi.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.UserCpaCpsSourceDao;
import com.hoolai.bi.report.model.entity.UserCpaCpsSource;
import com.hoolai.bi.report.service.UserCpaCpsSourceService;
import com.hoolai.bi.report.vo.UserCpaCpsSourceVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class UserCpaCpsSourceServiceImpl extends GenericServiceImpl<UserCpaCpsSource, Long> implements UserCpaCpsSourceService {

	@Autowired
	private UserCpaCpsSourceDao entityDao;
	
	@Override
    public GenericDao<UserCpaCpsSource, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public void removeAuth(UserCpaCpsSourceVO userSourceVO) {
		this.entityDao.removeAuth(userSourceVO);
	}

	@Override
	public UserCpaCpsSource selectBySource(UserCpaCpsSourceVO userSourceVO) {
		return this.entityDao.selectBySource(userSourceVO);
	}
	
	@Override
	public void removeCpaAuth(UserCpaCpsSourceVO userSourceVO) {
		this.entityDao.removeCpaAuth(userSourceVO);
	}

	@Override
	public UserCpaCpsSource selectByCpaSource(UserCpaCpsSourceVO userSourceVO) {
		return this.entityDao.selectByCpaSource(userSourceVO);
	}
	
	@Override
	public Long saveCpaEntity(UserCpaCpsSource userSource) {
		return this.entityDao.insertCpaEntity(userSource);
	}
	
}
