package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.UserRetentionSourceLtvDao;
import com.hoolai.bi.report.model.entity.UserRetentionSourceLtv;
import com.hoolai.bi.report.vo.UserRetentionSourceLtvVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class UserRetentionSourceLtvDaoImpl extends GenericDaoImpl<UserRetentionSourceLtv, Long> implements UserRetentionSourceLtvDao {

	@Override
	public String namespace() {
		return UserRetentionSourceLtv.class.getName();
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAvgInstallLTVDataList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAvgInstallLTVDataList", ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAvgInstallRetentionDataList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAvgInstallRetentionDataList", ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAvgRoleRetentionDataList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAvgRoleRetentionDataList", ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAvgRoleChioceLTVDataList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAvgRoleChioceLTVDataList", ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectHorizontal4InstallRetentionRateList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontal4InstallRetentionRateList", ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectHorizontal4RoleRetentionRateList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontal4RoleRetentionRateList", ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectHorizontal4InstallPayList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontal4InstallPayList", ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectHorizontal4RolePayList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontal4RolePayList", ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectPaymentForBarList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectPaymentForBarList", ltvVO);
	}

	@Override
	public Long selectCount(UserRetentionSourceLtvVO userRetentionSourceLtvVO) {
		return (Long)this.sqlSessionTemplate.selectOne(this.namespace()+".selectAllInstallCount", userRetentionSourceLtvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAllInstallRetentionList(
			UserRetentionSourceLtvVO userRetentionSourceLtvVO) {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectAllInstallRetentionList",userRetentionSourceLtvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAllRoleRetentionList(
			UserRetentionSourceLtvVO userRetentionSourceLtvVO) {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectAllRoleRetentionList",userRetentionSourceLtvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAllRolePayList(
			UserRetentionSourceLtvVO userRetentionSourceLtvVO) {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectAllRolePayList",userRetentionSourceLtvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAllInstallPayList(
			UserRetentionSourceLtvVO userRetentionSourceLtvVO) {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectAllInstallPayList",userRetentionSourceLtvVO);
	}

	@Override
	public UserRetentionSourceLtvVO selectHorizontal4TotalRolePayList(
			UserRetentionSourceLtvVO ltvVO) {
		return this.getSqlSessionTemplate().selectOne("selectHorizontal4TotalRolePayList",ltvVO);
	}

	@Override
	public UserRetentionSourceLtvVO selectHorizontal4TotalInstallPayList(
			UserRetentionSourceLtvVO ltvVO) {
		
		return this.getSqlSessionTemplate().selectOne("selectHorizontal4TotalInstallPayList",ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectHorizontal4TotalInstallPayList_export(UserRetentionSourceLtvVO ltvVO) {
		
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectHorizontal4TotalInstallPayList_export",ltvVO);
	}

	@Override
	public List<UserRetentionSourceLtvVO> selectAllRolePayList_export(UserRetentionSourceLtvVO ltvVO) {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectAllRolePayList_export",ltvVO);
	}

	@Override
	public UserRetentionSourceLtvVO selectHorizontal4TotalInstallPayList2(
			UserRetentionSourceLtvVO ltvVO) {
		return this.getSqlSessionTemplate().selectOne("selectHorizontal4TotalInstallPayList2",ltvVO);
	}

	@Override
	public UserRetentionSourceLtvVO selectHorizontal4TotalRolePayList2(
			UserRetentionSourceLtvVO ltvVO) {
		return this.getSqlSessionTemplate().selectOne("selectHorizontal4TotalRolePayList2",ltvVO);
	}
}
