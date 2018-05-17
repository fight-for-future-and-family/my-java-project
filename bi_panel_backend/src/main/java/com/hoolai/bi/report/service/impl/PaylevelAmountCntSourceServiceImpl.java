package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.PaylevelAmountCntSourceDao;
import com.hoolai.bi.report.model.entity.PaylevelAmountCntSource;
import com.hoolai.bi.report.service.PaylevelAmountCntSourceService;
import com.hoolai.bi.report.vo.PaylevelAmountCntSourceVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class PaylevelAmountCntSourceServiceImpl extends GenericServiceImpl<PaylevelAmountCntSource, Long> implements PaylevelAmountCntSourceService {

	@Autowired
	private PaylevelAmountCntSourceDao dao;
	
	@Override
    public GenericDao<PaylevelAmountCntSource, Long> getGenricDao() {
            return this.dao;
    }
	
	@Override
	public List<PaylevelAmountCntSourceVO> selectHorizontalUserList(PaylevelAmountCntSourceVO sourceVO) {
		return this.dao.selectHorizontalUserList(sourceVO);
	}

	@Override
	public List<PaylevelAmountCntSourceVO> selectHorizontalPayList(PaylevelAmountCntSourceVO sourceVO) {
		return this.dao.selectHorizontalPayList(sourceVO);
	}

	@Override
	public PaylevelAmountCntSourceVO selectHorizontalPayView(PaylevelAmountCntSourceVO sourceVO) {
		return this.dao.selectHorizontalPayView(sourceVO);
	}

	@Override
	public PaylevelAmountCntSourceVO selectHorizontalUserView(PaylevelAmountCntSourceVO sourceVO) {
		return this.dao.selectHorizontalUserView(sourceVO);
	}

	@Override
	public long selectCount(PaylevelAmountCntSourceVO sourceVO) {
		try {
			return this.dao.selecCount(sourceVO);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<PaylevelAmountCntSourceVO> selectPayList(PaylevelAmountCntSourceVO sourceVO) {
		String col = sourceVO.getOrderCol();
		String type = sourceVO.getOrderType();
		type = StringUtils.isEmpty(type) ? " desc" : type; 
		if(col == null){
			sourceVO.setOrderValue(" order by stat_day desc");
		}else{
			sourceVO.setOrderValue(" order by "+col+" "+type);
		}
		return this.dao.selectPayList(sourceVO);
	}

	@Override
	public List<PaylevelAmountCntSourceVO> selectUserList(PaylevelAmountCntSourceVO sourceVO) {
		String col = sourceVO.getOrderCol();
		String type = sourceVO.getOrderType();
		type = StringUtils.isEmpty(type) ? " desc" : type; 
		if(col == null){
			sourceVO.setOrderValue(" order by stat_day desc");
		}else{
			sourceVO.setOrderValue(" order by "+col+" "+type);
		}
		return this.dao.selectUserList(sourceVO);
	}

}
