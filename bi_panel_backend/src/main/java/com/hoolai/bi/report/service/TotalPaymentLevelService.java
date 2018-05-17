package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.TotalPaymentLevel;
import com.hoolai.bi.report.vo.TotalPaymentLevelVO;
import com.jian.service.GenericService;

public interface TotalPaymentLevelService extends GenericService<TotalPaymentLevel, Long>{

	Long selectPaymentForPieListCount(TotalPaymentLevelVO totalPaymentLevelVO);
	List<TotalPaymentLevelVO> selectPaymentForPieList(TotalPaymentLevelVO totalPaymentLevelVO);
	Long selectPayUserForPieListCount(TotalPaymentLevelVO totalPaymentLevelVO);
	List<TotalPaymentLevelVO> selectPayUserForPieList(TotalPaymentLevelVO totalPaymentLevelVO);
	
}
