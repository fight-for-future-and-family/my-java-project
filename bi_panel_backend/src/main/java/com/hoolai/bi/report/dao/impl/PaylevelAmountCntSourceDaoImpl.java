package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.PaylevelAmountCntSourceDao;
import com.hoolai.bi.report.model.entity.PaylevelAmountCntSource;
import com.hoolai.bi.report.vo.PaylevelAmountCntSourceVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class PaylevelAmountCntSourceDaoImpl extends GenericDaoImpl<PaylevelAmountCntSource, Long> implements PaylevelAmountCntSourceDao {

	@Override
	public String namespace() {
		return PaylevelAmountCntSource.class.getName();
	}

	@Override
	public List<PaylevelAmountCntSourceVO> selectHorizontalUserList(PaylevelAmountCntSourceVO sourceVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontalUserList", sourceVO);
	}

	@Override
	public List<PaylevelAmountCntSourceVO> selectHorizontalPayList(PaylevelAmountCntSourceVO sourceVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontalPayList", sourceVO);
	}

	@Override
	public PaylevelAmountCntSourceVO selectHorizontalPayView(PaylevelAmountCntSourceVO sourceVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectHorizontalPayView", sourceVO);
	}

	@Override
	public PaylevelAmountCntSourceVO selectHorizontalUserView(PaylevelAmountCntSourceVO sourceVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectHorizontalUserView", sourceVO);
	}

	@Override
	public List<PaylevelAmountCntSourceVO> selectPayList(PaylevelAmountCntSourceVO sourceVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectPayList", sourceVO);
	}

	@Override
	public List<PaylevelAmountCntSourceVO> selectUserList(
			PaylevelAmountCntSourceVO sourceVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectUserList", sourceVO);
	}
	
}
