package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.FirstPaymentLevelDao;
import com.hoolai.bi.report.model.entity.FirstPaymentLevel;
import com.hoolai.bi.report.model.entity.TotalPaymentLevel;
import com.hoolai.bi.report.vo.FirstPaymentLevelVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class FirstPaymentLevelDaoImpl extends GenericDaoImpl<FirstPaymentLevel, Long> implements FirstPaymentLevelDao {

	@Override
	public String namespace() {
		return FirstPaymentLevel.class.getName();
	}

	@Override
	public Long selectPaymentForPieListCount(FirstPaymentLevelVO totalPaymentLevelVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectPaymentForPieListCount", totalPaymentLevelVO);
	}

	@Override
	public List<FirstPaymentLevelVO> selectPaymentForPieList(FirstPaymentLevelVO totalPaymentLevelVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectPaymentForPieList", totalPaymentLevelVO);
	}

	@Override
	public Long selectPayUserForPieListCount(FirstPaymentLevelVO totalPaymentLevelVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectPayUserForPieListCount", totalPaymentLevelVO);
	}

	@Override
	public List<FirstPaymentLevelVO> selectPayUserForPieList(FirstPaymentLevelVO totalPaymentLevelVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectPayUserForPieList", totalPaymentLevelVO);
	}

}
