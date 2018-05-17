package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.PaylevelAmountCntSource;
import com.hoolai.bi.report.vo.PaylevelAmountCntSourceVO;
import com.hoolai.dao.GenericDao;


public interface PaylevelAmountCntSourceDao extends GenericDao<PaylevelAmountCntSource, Long> {

	List<PaylevelAmountCntSourceVO> selectHorizontalUserList(PaylevelAmountCntSourceVO sourceVO);

	List<PaylevelAmountCntSourceVO> selectHorizontalPayList(PaylevelAmountCntSourceVO sourceVO);
	
	PaylevelAmountCntSourceVO selectHorizontalPayView(PaylevelAmountCntSourceVO sourceVO);
	
	PaylevelAmountCntSourceVO selectHorizontalUserView(PaylevelAmountCntSourceVO sourceVO);

	List<PaylevelAmountCntSourceVO> selectPayList(
			PaylevelAmountCntSourceVO sourceVO);

	List<PaylevelAmountCntSourceVO> selectUserList(
			PaylevelAmountCntSourceVO sourceVO);
    
}
