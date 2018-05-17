package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.PaylevelAmountCntDao;
import com.hoolai.bi.report.model.entity.PaylevelAmountCnt;
import com.hoolai.bi.report.vo.PaylevelAmountCntVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class PaylevelAmountCntDaoImpl extends GenericDaoImpl<PaylevelAmountCnt, Long> implements PaylevelAmountCntDao {

	@Override
	public String namespace() {
		return PaylevelAmountCnt.class.getName();
	}

	@Override
	public List<PaylevelAmountCntVO> selectHorizontalUserList(PaylevelAmountCntVO paylevelAmountCntVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontalUserList", paylevelAmountCntVO);
	}

	@Override
	public List<PaylevelAmountCntVO> selectHorizontalPayList(PaylevelAmountCntVO paylevelAmountCntVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontalPayList", paylevelAmountCntVO);
	}

	@Override
	public PaylevelAmountCntVO selectHorizontalPayView(PaylevelAmountCntVO paylevelAmountCntVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectHorizontalPayView", paylevelAmountCntVO);
	}

	@Override
	public PaylevelAmountCntVO selectHorizontalUserView(PaylevelAmountCntVO paylevelAmountCntVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectHorizontalUserView", paylevelAmountCntVO);
	}
	
}
