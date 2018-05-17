package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.PaylevelAmountCntDao;
import com.hoolai.bi.report.model.entity.PaylevelAmountCnt;
import com.hoolai.bi.report.service.PaylevelAmountCntService;
import com.hoolai.bi.report.vo.PaylevelAmountCntVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class PaylevelAmountCntServiceImpl extends GenericServiceImpl<PaylevelAmountCnt, Long> implements PaylevelAmountCntService {

	@Autowired
	private PaylevelAmountCntDao dao;
	
	@Override
    public GenericDao<PaylevelAmountCnt, Long> getGenricDao() {
            return this.dao;
    }

	@Override
	public List<PaylevelAmountCntVO> selectHorizontalUserList(PaylevelAmountCntVO paylevelAmountCntVO) {
		return dao.selectHorizontalUserList(paylevelAmountCntVO);
	}

	@Override
	public List<PaylevelAmountCntVO> selectHorizontalPayList(PaylevelAmountCntVO paylevelAmountCntVO) {
		return dao.selectHorizontalPayList(paylevelAmountCntVO);
	}

	@Override
	public PaylevelAmountCntVO selectHorizontalPayView(PaylevelAmountCntVO paylevelAmountCntVO) {
		return dao.selectHorizontalPayView(paylevelAmountCntVO);
	}

	@Override
	public PaylevelAmountCntVO selectHorizontalUserView(PaylevelAmountCntVO paylevelAmountCntVO) {
		return dao.selectHorizontalUserView(paylevelAmountCntVO);
	}

}
