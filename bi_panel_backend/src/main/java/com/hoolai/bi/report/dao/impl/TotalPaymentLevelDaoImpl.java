package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.TotalPaymentLevelDao;
import com.hoolai.bi.report.model.entity.TotalPaymentLevel;
import com.hoolai.bi.report.vo.TotalPaymentLevelVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class TotalPaymentLevelDaoImpl extends GenericDaoImpl<TotalPaymentLevel, Long> implements TotalPaymentLevelDao {

	@Override
	public String namespace() {
		return TotalPaymentLevel.class.getName();
	}

	@Override
	public Long selectPaymentForPieListCount(TotalPaymentLevelVO totalPaymentLevelVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectPaymentForPieListCount", totalPaymentLevelVO);
	}

	@Override
	public List<TotalPaymentLevelVO> selectPaymentForPieList(TotalPaymentLevelVO totalPaymentLevelVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectPaymentForPieList", totalPaymentLevelVO);
	}

	@Override
	public Long selectPayUserForPieListCount(TotalPaymentLevelVO totalPaymentLevelVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectPayUserForPieListCount", totalPaymentLevelVO);
	}

	@Override
	public List<TotalPaymentLevelVO> selectPayUserForPieList(TotalPaymentLevelVO totalPaymentLevelVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectPayUserForPieList", totalPaymentLevelVO);
	}

}
