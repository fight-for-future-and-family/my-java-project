package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.FirstPaymentLevelDao;
import com.hoolai.bi.report.model.entity.FirstPaymentLevel;
import com.hoolai.bi.report.service.FirstPaymentLevelService;
import com.hoolai.bi.report.vo.FirstPaymentLevelVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class FirstPaymentLevelServiceImpl extends GenericServiceImpl<FirstPaymentLevel, Long> implements FirstPaymentLevelService {

	@Autowired
	private FirstPaymentLevelDao dao;
	
	@Override
	public GenericDao<FirstPaymentLevel, Long> getGenricDao() {
		return dao;
	}
	
	@Override
	public Long selectPaymentForPieListCount(FirstPaymentLevelVO firstPaymentLevelVO) {
		return this.dao.selectPaymentForPieListCount(firstPaymentLevelVO);
	}

	@Override
	public List<FirstPaymentLevelVO> selectPaymentForPieList(FirstPaymentLevelVO firstPaymentLevelVO) {
		return this.dao.selectPaymentForPieList(firstPaymentLevelVO);
	}

	@Override
	public Long selectPayUserForPieListCount(FirstPaymentLevelVO firstPaymentLevelVO) {
		return this.dao.selectPayUserForPieListCount(firstPaymentLevelVO);
	}

	@Override
	public List<FirstPaymentLevelVO> selectPayUserForPieList(FirstPaymentLevelVO firstPaymentLevelVO) {
		return this.dao.selectPayUserForPieList(firstPaymentLevelVO);
	}

}
