package com.hoolai.bi.report.dao;

import com.hoolai.bi.report.model.entity.UserCpaCpsSource;
import com.hoolai.bi.report.vo.UserCpaCpsSourceVO;
import com.hoolai.dao.GenericDao;


public interface UserCpaCpsSourceDao extends GenericDao<UserCpaCpsSource, Long> {

	void removeAuth(UserCpaCpsSourceVO userSourceVO);

	UserCpaCpsSource selectBySource(UserCpaCpsSourceVO userSourceVO);
	
	void removeCpaAuth(UserCpaCpsSourceVO userSourceVO);

	UserCpaCpsSource selectByCpaSource(UserCpaCpsSourceVO userSourceVO);
	
	Long insertCpaEntity(UserCpaCpsSource userSource);

}
