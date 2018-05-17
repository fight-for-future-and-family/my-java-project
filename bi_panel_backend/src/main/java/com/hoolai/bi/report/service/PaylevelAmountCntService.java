package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.PaylevelAmountCnt;
import com.hoolai.bi.report.vo.PaylevelAmountCntVO;
import com.jian.service.GenericService;

public interface PaylevelAmountCntService extends GenericService<PaylevelAmountCnt, Long>{

	List<PaylevelAmountCntVO> selectHorizontalUserList(PaylevelAmountCntVO paylevelAmountCntVO);

	List<PaylevelAmountCntVO> selectHorizontalPayList(PaylevelAmountCntVO paylevelAmountCntVO);
	
	PaylevelAmountCntVO selectHorizontalPayView(PaylevelAmountCntVO paylevelAmountCntVO);
	
	PaylevelAmountCntVO selectHorizontalUserView(PaylevelAmountCntVO paylevelAmountCntVO);

}
