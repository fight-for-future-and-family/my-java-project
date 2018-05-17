package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.UserRetentionLtvDao;
import com.hoolai.bi.report.model.entity.UserRetentionLtv;
import com.hoolai.bi.report.service.UserRetentionLtvService;
import com.hoolai.bi.report.vo.UserRetentionLtvVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class UserRetentionLtvServiceImpl extends GenericServiceImpl<UserRetentionLtv, Long> implements UserRetentionLtvService {

	@Autowired
	private UserRetentionLtvDao entityDao;
	
	@Override
    public GenericDao<UserRetentionLtv, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public List<UserRetentionLtvVO> selectAvgInstallLTVDataList(UserRetentionLtvVO ltvVO) {
		return this.entityDao.selectAvgInstallLTVDataList(ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectAvgInstallRetentionDataList(
			UserRetentionLtvVO ltvVO) {
		return this.entityDao.selectAvgInstallRetentionDataList(ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectAvgRoleRetentionDataList(
			UserRetentionLtvVO ltvVO) {
		return this.entityDao.selectAvgRoleRetentionDataList(ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectAvgRoleChioceLTVDataList(
			UserRetentionLtvVO ltvVO) {
		return this.entityDao.selectAvgRoleChioceLTVDataList(ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectHorizontal4InstallRetentionRateList(
			UserRetentionLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4InstallRetentionRateList(ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectHorizontal4RoleRetentionRateList(
			UserRetentionLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4RoleRetentionRateList(ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectHorizontal4InstallPayList(
			UserRetentionLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4InstallPayList(ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectHorizontal4RolePayList(
			UserRetentionLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4RolePayList(ltvVO);
	}

	@Override
	public UserRetentionLtvVO selectHorizontal4TotalPayList(
			UserRetentionLtvVO ltvVO) {
		
		return this.entityDao.selectHorizontal4TotalPayList(ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectHorizontal4MoneyPayList(
			UserRetentionLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4MoneyPayList(ltvVO);
	}

	@Override
	public UserRetentionLtvVO selectHorizontal4MoneyTotalPayList(
			UserRetentionLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4MoneyTotalPayList(ltvVO);
	}

	@Override
	public UserRetentionLtvVO selectHorizontal4TotalRolePayList(
			UserRetentionLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4TotalRolePayList(ltvVO);
	}

	@Override
	public UserRetentionLtvVO selectHorizontal4TotalRolePayList2(
			UserRetentionLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4TotalRolePayList2(ltvVO);
	}

	@Override
	public UserRetentionLtvVO selectHorizontal4TotalPayList2(
			UserRetentionLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4TotalPayList2(ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectClientUserRetentionLtv(UserRetentionLtvVO userRetentionLtvVO) {
		return this.entityDao.selectClientUserRetentionLtv(userRetentionLtvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectClientUserRetentionLtvInstallUserCount(UserRetentionLtvVO userRetentionLtvVO) {
		return this.entityDao.selectClientUserRetentionLtvInstallUserCount(userRetentionLtvVO);
	}

}
