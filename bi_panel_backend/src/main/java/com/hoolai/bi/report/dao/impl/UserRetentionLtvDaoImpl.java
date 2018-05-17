package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.UserRetentionLtvDao;
import com.hoolai.bi.report.model.entity.UserRetentionLtv;
import com.hoolai.bi.report.vo.UserRetentionLtvVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class UserRetentionLtvDaoImpl extends GenericDaoImpl<UserRetentionLtv, Long> implements UserRetentionLtvDao {

	@Override
	public String namespace() {
		return UserRetentionLtv.class.getName();
	}

	@Override
	public List<UserRetentionLtvVO> selectAvgInstallLTVDataList(
			UserRetentionLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAvgInstallLTVDataList", ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectAvgInstallRetentionDataList(
			UserRetentionLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAvgInstallRetentionDataList", ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectAvgRoleRetentionDataList(
			UserRetentionLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAvgRoleRetentionDataList", ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectAvgRoleChioceLTVDataList(
			UserRetentionLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAvgRoleChioceLTVDataList", ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectHorizontal4InstallRetentionRateList(
			UserRetentionLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontal4InstallRetentionRateList", ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectHorizontal4RoleRetentionRateList(
			UserRetentionLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontal4RoleRetentionRateList", ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectHorizontal4InstallPayList(
			UserRetentionLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontal4InstallPayList", ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectHorizontal4RolePayList(
			UserRetentionLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontal4RolePayList", ltvVO);
	}

	@Override
	public UserRetentionLtvVO selectHorizontal4TotalPayList(
			UserRetentionLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectOne("selectHorizontal4TotalPayList", ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectHorizontal4MoneyPayList(
			UserRetentionLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontal4MoneyPayList", ltvVO);
	}

	@Override
	public UserRetentionLtvVO selectHorizontal4MoneyTotalPayList(
			UserRetentionLtvVO ltvVO) {
		
		return this.sqlSessionTemplate.selectOne("selectHorizontal4MoneyTotalPayList", ltvVO);
	}

	@Override
	public UserRetentionLtvVO selectHorizontal4TotalRolePayList(
			UserRetentionLtvVO ltvVO) {
		
		return this.sqlSessionTemplate.selectOne("selectHorizontal4_TotalRolePayList", ltvVO);
	}

	@Override
	public UserRetentionLtvVO selectHorizontal4TotalRolePayList2(
			UserRetentionLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectOne("selectHorizontal4_TotalRolePayList2", ltvVO);
	}

	@Override
	public UserRetentionLtvVO selectHorizontal4TotalPayList2(
			UserRetentionLtvVO ltvVO) {
	
		return this.sqlSessionTemplate.selectOne("selectHorizontal4TotalPayList2", ltvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectClientUserRetentionLtv(UserRetentionLtvVO userRetentionLtvVO) {
		return this.sqlSessionTemplate.selectList("selectClientUserRetentionLtv", userRetentionLtvVO);
	}

	@Override
	public List<UserRetentionLtvVO> selectClientUserRetentionLtvInstallUserCount(UserRetentionLtvVO userRetentionLtvVO) {
		return this.sqlSessionTemplate.selectList("selectClientUserRetentionLtvInstallUserCount", userRetentionLtvVO);
	}
	
}
