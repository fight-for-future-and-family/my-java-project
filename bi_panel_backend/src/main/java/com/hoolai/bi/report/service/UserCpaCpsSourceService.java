package com.hoolai.bi.report.service;

import com.hoolai.bi.report.model.entity.UserCpaCpsSource;
import com.hoolai.bi.report.vo.UserCpaCpsSourceVO;
import com.jian.service.GenericService;

public interface UserCpaCpsSourceService extends GenericService<UserCpaCpsSource, Long>{

	void removeAuth(UserCpaCpsSourceVO userSourceVO);

	UserCpaCpsSource selectBySource(UserCpaCpsSourceVO userSourceVO);
	
	void removeCpaAuth(UserCpaCpsSourceVO userSourceVO);

	UserCpaCpsSource selectByCpaSource(UserCpaCpsSourceVO userSourceVO);
	
	Long saveCpaEntity(UserCpaCpsSource userSource);


}
