package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.TotalPaymentLevel;
import com.hoolai.bi.report.vo.TotalPaymentLevelVO;
import com.hoolai.bi.report.vo.UserRetentionLtvVO;
import com.hoolai.dao.GenericDao;


public interface TotalPaymentLevelDao extends GenericDao<TotalPaymentLevel, Long> {

	Long selectPaymentForPieListCount(TotalPaymentLevelVO totalPaymentLevelVO);
	List<TotalPaymentLevelVO> selectPaymentForPieList(TotalPaymentLevelVO totalPaymentLevelVO);
	Long selectPayUserForPieListCount(TotalPaymentLevelVO totalPaymentLevelVO);
	List<TotalPaymentLevelVO> selectPayUserForPieList(TotalPaymentLevelVO totalPaymentLevelVO);
}
