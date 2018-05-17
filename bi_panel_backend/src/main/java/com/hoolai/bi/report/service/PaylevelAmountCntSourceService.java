package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.PaylevelAmountCntSource;
import com.hoolai.bi.report.vo.PaylevelAmountCntSourceVO;
import com.jian.service.GenericService;

public interface PaylevelAmountCntSourceService extends GenericService<PaylevelAmountCntSource, Long>{

	List<PaylevelAmountCntSourceVO> selectHorizontalUserList(PaylevelAmountCntSourceVO clientVO);

	List<PaylevelAmountCntSourceVO> selectHorizontalPayList(PaylevelAmountCntSourceVO sourceVO);
	
    PaylevelAmountCntSourceVO selectHorizontalPayView(PaylevelAmountCntSourceVO sourceVO);
	
	PaylevelAmountCntSourceVO selectHorizontalUserView(PaylevelAmountCntSourceVO sourceVO);

	long selectCount(PaylevelAmountCntSourceVO sourceVO);

	List<PaylevelAmountCntSourceVO> selectPayList(PaylevelAmountCntSourceVO sourceVO);

	List<PaylevelAmountCntSourceVO> selectUserList(PaylevelAmountCntSourceVO sourceVO);

}
