package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.UserRetentionSourceLtvDao;
import com.hoolai.bi.report.model.entity.UserRetentionSourceLtv;
import com.hoolai.bi.report.service.UserRetentionSourceLtvService;
import com.hoolai.bi.report.vo.UserRetentionSourceLtvVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class UserRetentionSourceLtvServiceImpl extends GenericServiceImpl<UserRetentionSourceLtv, Long> implements UserRetentionSourceLtvService {

	@Autowired
	private UserRetentionSourceLtvDao entityDao;
	
	@Override
    public GenericDao<UserRetentionSourceLtv, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public List<UserRetentionSourceLtvVO> selectAvgInstallLTVDataList(UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectAvgInstallLTVDataList(ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAvgInstallRetentionDataList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectAvgInstallRetentionDataList(ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAvgRoleRetentionDataList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectAvgRoleRetentionDataList(ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAvgRoleChioceLTVDataList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectAvgRoleChioceLTVDataList(ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectHorizontal4InstallRetentionRateList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4InstallRetentionRateList(ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectHorizontal4RoleRetentionRateList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4RoleRetentionRateList(ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectHorizontal4InstallPayList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4InstallPayList(ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectHorizontal4RolePayList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4RolePayList(ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectPaymentForBarList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectPaymentForBarList(ltvVO);
	}

	@Override
	public long selectCount(UserRetentionSourceLtvVO userRetentionSourceLtvVO) {
		try {
			return entityDao.selectCount(userRetentionSourceLtvVO);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAllRoleRetentionList(UserRetentionSourceLtvVO userRetentionSourceLtvVO) {
		return this.entityDao.selectAllRoleRetentionList(userRetentionSourceLtvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAllInstallRetentionList(
			UserRetentionSourceLtvVO userRetentionSourceLtvVO) {
		return this.entityDao.selectAllInstallRetentionList(userRetentionSourceLtvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAllRolePayList(UserRetentionSourceLtvVO userRetentionSourceLtvVO) {
		return this.entityDao.selectAllRolePayList(userRetentionSourceLtvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAllInstallPayList(UserRetentionSourceLtvVO userRetentionSourceLtvVO) {
		return this.entityDao.selectAllInstallPayList(userRetentionSourceLtvVO);
	}

	@Override
	public UserRetentionSourceLtvVO selectHorizontal4TotalRolePayList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4TotalRolePayList(ltvVO);
	}

	@Override
	public UserRetentionSourceLtvVO selectHorizontal4TotalInstallPayList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4TotalInstallPayList(ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectHorizontal4TotalInstallPayList_export(
			UserRetentionSourceLtvVO ltvVO) {
		
		return this.entityDao.selectHorizontal4TotalInstallPayList_export(ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAllRolePayList_export(
			UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectAllRolePayList_export(ltvVO);
	}

	@Override
	public UserRetentionSourceLtvVO selectHorizontal4TotalInstallPayList2(
			UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4TotalInstallPayList2(ltvVO);
	}

	@Override
	public UserRetentionSourceLtvVO selectHorizontal4TotalRolePayList2(
			UserRetentionSourceLtvVO ltvVO) {
		return this.entityDao.selectHorizontal4TotalRolePayList2(ltvVO);

	}
}
