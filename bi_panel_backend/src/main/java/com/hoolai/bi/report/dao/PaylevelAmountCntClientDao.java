package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.PaylevelAmountCntClient;
import com.hoolai.bi.report.vo.PaylevelAmountCntClientVO;
import com.hoolai.dao.GenericDao;


public interface PaylevelAmountCntClientDao extends GenericDao<PaylevelAmountCntClient, Long> {

	List<PaylevelAmountCntClientVO> selectHorizontalUserList(PaylevelAmountCntClientVO paylevelAmountCntVO);

	List<PaylevelAmountCntClientVO> selectHorizontalPayList(PaylevelAmountCntClientVO paylevelAmountCntVO);
	
	PaylevelAmountCntClientVO selectHorizontalPayView(PaylevelAmountCntClientVO paylevelAmountCntVO);
	
	PaylevelAmountCntClientVO selectHorizontalUserView(PaylevelAmountCntClientVO paylevelAmountCntVO);

	List<PaylevelAmountCntClientVO> selectPayList(
			PaylevelAmountCntClientVO clientVO);

	List<PaylevelAmountCntClientVO> selectUserList(
			PaylevelAmountCntClientVO clientVO);

    
}
