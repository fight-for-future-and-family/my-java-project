package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.UserRetentionClientLtvDao;
import com.hoolai.bi.report.model.entity.UserRetentionClientLtv;
import com.hoolai.bi.report.service.UserRetentionClientLtvService;
import com.hoolai.bi.report.vo.UserRetentionClientLtvVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class UserRetentionClientLtvServiceImpl extends GenericServiceImpl<UserRetentionClientLtv, Long> implements UserRetentionClientLtvService {

	@Autowired
	private UserRetentionClientLtvDao entityDao;
	
	@Override
    public GenericDao<UserRetentionClientLtv, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public List<UserRetentionClientLtvVO> selectAvgDataList(UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return entityDao.selectAvgDataList(userRetentionClientLtvVO);
	}

	@Override
	public List<UserRetentionClientLtvVO> selectHorizontal4RateList(UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return entityDao.selectHorizontal4RateList(userRetentionClientLtvVO);
	}

	@Override
	public List<UserRetentionClientLtvVO> selectHorizontalList(UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return entityDao.selectHorizontalList(userRetentionClientLtvVO);
	}

	@Override
	public long selectCount(UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return entityDao.selectCount(userRetentionClientLtvVO);
	}

	@Override
	public List<UserRetentionClientLtvVO> selectAllInstallRetentionList(
			UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return entityDao.selectAllInstallRetentionList(userRetentionClientLtvVO);
	}

	@Override
	public List<UserRetentionClientLtvVO> selectAllInstallPayList(
			UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return entityDao.selectAllInstallPayList(userRetentionClientLtvVO);
	}

	@Override
	public UserRetentionClientLtvVO selectHorizontalTotalList(
			UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return entityDao.selectHorizontalTotalList(userRetentionClientLtvVO);
	}

	@Override
	public List<UserRetentionClientLtvVO> selectAllInstallPayList_exporp(
			UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return entityDao.selectAllInstallPayList_exporp(userRetentionClientLtvVO);
	}


}
