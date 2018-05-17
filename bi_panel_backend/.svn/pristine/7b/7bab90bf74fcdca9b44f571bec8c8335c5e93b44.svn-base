package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.PaylevelAmountCntClientDao;
import com.hoolai.bi.report.model.entity.PaylevelAmountCntClient;
import com.hoolai.bi.report.service.PaylevelAmountCntClientService;
import com.hoolai.bi.report.vo.PaylevelAmountCntClientVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class PaylevelAmountCntClientServiceImpl extends GenericServiceImpl<PaylevelAmountCntClient, Long> implements PaylevelAmountCntClientService {


	@Autowired
	private PaylevelAmountCntClientDao dao;
	
	@Override
    public GenericDao<PaylevelAmountCntClient, Long> getGenricDao() {
            return this.dao;
    }
	
	@Override
	public List<PaylevelAmountCntClientVO> selectHorizontalUserList(PaylevelAmountCntClientVO paylevelAmountCntVO) {
		return this.dao.selectHorizontalUserList(paylevelAmountCntVO);
	}

	@Override
	public List<PaylevelAmountCntClientVO> selectHorizontalPayList(PaylevelAmountCntClientVO paylevelAmountCntVO) {
		return this.dao.selectHorizontalPayList(paylevelAmountCntVO);
	}

	@Override
	public PaylevelAmountCntClientVO selectHorizontalPayView(PaylevelAmountCntClientVO paylevelAmountCntVO) {
		return this.dao.selectHorizontalPayView(paylevelAmountCntVO);
	}

	@Override
	public PaylevelAmountCntClientVO selectHorizontalUserView(PaylevelAmountCntClientVO paylevelAmountCntVO) {
		return this.dao.selectHorizontalUserView(paylevelAmountCntVO);
	}

	@Override
	public long selectCount(PaylevelAmountCntClientVO clientVO) {
		try {
			return this.dao.selecCount(clientVO);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<PaylevelAmountCntClientVO> selectPayList(PaylevelAmountCntClientVO clientVO) {
		String col = clientVO.getOrderCol();
		String type = clientVO.getOrderType();
		type = StringUtils.isEmpty(type) ? " desc" : type; 
		if(col == null){
			clientVO.setOrderValue(" order by stat_day desc");
		}else{
			clientVO.setOrderValue(" order by "+col+" "+type);
		}
		return this.dao.selectPayList(clientVO);
	}

	@Override
	public List<PaylevelAmountCntClientVO> selectUserList(PaylevelAmountCntClientVO clientVO) {
		String col = clientVO.getOrderCol();
		String type = clientVO.getOrderType();
		type = StringUtils.isEmpty(type) ? " desc" : type; 
		if(col == null){
			clientVO.setOrderValue(" order by stat_day desc");
		}else{
			clientVO.setOrderValue(" order by "+col+" "+type);
		}
		return this.dao.selectUserList(clientVO);
	}

}
