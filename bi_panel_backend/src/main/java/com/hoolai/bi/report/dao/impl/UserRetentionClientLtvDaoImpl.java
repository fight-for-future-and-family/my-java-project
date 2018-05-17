package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.UserRetentionClientLtvDao;
import com.hoolai.bi.report.model.entity.UserRetentionClientLtv;
import com.hoolai.bi.report.vo.UserRetentionClientLtvVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class UserRetentionClientLtvDaoImpl extends GenericDaoImpl<UserRetentionClientLtv, Long> implements UserRetentionClientLtvDao {

	@Override
	public String namespace() {
		return UserRetentionClientLtv.class.getName();
	}

	@Override
	public List<UserRetentionClientLtvVO> selectAvgDataList(UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAvgDataList", userRetentionClientLtvVO);
	}

	@Override
	public List<UserRetentionClientLtvVO> selectHorizontal4RateList(UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontal4RateList", userRetentionClientLtvVO);
	}

	@Override
	public List<UserRetentionClientLtvVO> selectHorizontalList(UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontalList", userRetentionClientLtvVO);
	}

	@Override
	public Long selectCount(UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectInstallCount", userRetentionClientLtvVO);
	}

	@Override
	public List<UserRetentionClientLtvVO> selectAllInstallRetentionList(
			UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAllInstallRetentionList", userRetentionClientLtvVO);
	}

	@Override
	public List<UserRetentionClientLtvVO> selectAllInstallPayList(
			UserRetentionClientLtvVO userRetentionClientLtvVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAllInstallPayList", userRetentionClientLtvVO);
	}

	@Override
	public UserRetentionClientLtvVO selectHorizontalTotalList(
			UserRetentionClientLtvVO userRetentionClientLtvVO) {
		
		return this.sqlSessionTemplate.selectOne("selectHorizontalTotalList",userRetentionClientLtvVO);
	}

	@Override
	public List<UserRetentionClientLtvVO> selectAllInstallPayList_exporp(
			UserRetentionClientLtvVO userRetentionClientLtvVO) {
		
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAllInstallPayList_export", userRetentionClientLtvVO);
	}
	
}
