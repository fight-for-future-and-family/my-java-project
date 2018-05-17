package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.PaylevelAmountCntClientDao;
import com.hoolai.bi.report.model.entity.PaylevelAmountCntClient;
import com.hoolai.bi.report.vo.PaylevelAmountCntClientVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class PaylevelAmountCntClientDaoImpl extends GenericDaoImpl<PaylevelAmountCntClient, Long> implements PaylevelAmountCntClientDao {

	@Override
	public String namespace() {
		return PaylevelAmountCntClient.class.getName();
	}

	@Override
	public List<PaylevelAmountCntClientVO> selectHorizontalUserList(PaylevelAmountCntClientVO paylevelAmountCntVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontalUserList", paylevelAmountCntVO);
	}

	@Override
	public List<PaylevelAmountCntClientVO> selectHorizontalPayList(PaylevelAmountCntClientVO paylevelAmountCntVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectHorizontalPayList", paylevelAmountCntVO);
	}

	@Override
	public PaylevelAmountCntClientVO selectHorizontalPayView(PaylevelAmountCntClientVO paylevelAmountCntVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectHorizontalPayView", paylevelAmountCntVO);
	}

	@Override
	public PaylevelAmountCntClientVO selectHorizontalUserView(PaylevelAmountCntClientVO paylevelAmountCntVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectHorizontalUserView", paylevelAmountCntVO);
	}

	@Override
	public List<PaylevelAmountCntClientVO> selectPayList(PaylevelAmountCntClientVO clientVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectPayList", clientVO);
	}

	@Override
	public List<PaylevelAmountCntClientVO> selectUserList(PaylevelAmountCntClientVO clientVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectUserList", clientVO);
	}
	
}
