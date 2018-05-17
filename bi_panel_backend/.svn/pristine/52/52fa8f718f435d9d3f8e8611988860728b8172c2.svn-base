package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.TotalPaymentLevelDao;
import com.hoolai.bi.report.model.entity.TotalPaymentLevel;
import com.hoolai.bi.report.service.TotalPaymentLevelService;
import com.hoolai.bi.report.vo.TotalPaymentLevelVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class TotalPaymentLevelServiceImpl extends GenericServiceImpl<TotalPaymentLevel, Long> implements TotalPaymentLevelService {

	@Autowired
	private TotalPaymentLevelDao dao;
	
	@Override
	public GenericDao<TotalPaymentLevel, Long> getGenricDao() {
		return dao;
	}
	
	@Override
	public Long selectPaymentForPieListCount(TotalPaymentLevelVO totalPaymentLevelVO) {
		return this.dao.selectPaymentForPieListCount(totalPaymentLevelVO);
	}

	@Override
	public List<TotalPaymentLevelVO> selectPaymentForPieList(TotalPaymentLevelVO totalPaymentLevelVO) {
		return this.dao.selectPaymentForPieList(totalPaymentLevelVO);
	}

	@Override
	public Long selectPayUserForPieListCount(TotalPaymentLevelVO totalPaymentLevelVO) {
		return this.dao.selectPayUserForPieListCount(totalPaymentLevelVO);
	}

	@Override
	public List<TotalPaymentLevelVO> selectPayUserForPieList(TotalPaymentLevelVO totalPaymentLevelVO) {
		return this.dao.selectPayUserForPieList(totalPaymentLevelVO);
	}

}
