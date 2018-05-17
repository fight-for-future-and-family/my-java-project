package com.hoolai.bi.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.UserLoginLogDao;
import com.hoolai.bi.report.model.entity.UserLoginLog;
import com.hoolai.bi.report.model.entity.UserOperationLog;
import com.hoolai.bi.report.service.UserLoginLogService;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class UserLoginLogServiceImpl extends GenericServiceImpl<UserLoginLog, Long>
		implements UserLoginLogService {
	
	@Autowired
	private UserLoginLogDao dao;

	@Override
	public int saveLoginLogs(UserLoginLog userLoginLog) {
		return dao.saveLoginLogs(userLoginLog);
	}

	@Override
	public int saveOperationLogs(final UserOperationLog userOperationLog) {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				dao.saveOperationLogs(userOperationLog);
			}
		});
		t.start();
		return 1; 
	}

}
