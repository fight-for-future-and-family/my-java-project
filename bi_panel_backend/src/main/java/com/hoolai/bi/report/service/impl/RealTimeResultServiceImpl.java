package com.hoolai.bi.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.RealTimeResultDao;
import com.hoolai.bi.report.model.entity.RealTimeResult;
import com.hoolai.bi.report.service.RealTimeResultService;
import com.hoolai.bi.report.vo.RealTimeResultVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.Pagination;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class RealTimeResultServiceImpl extends GenericServiceImpl<RealTimeResult, Long> implements RealTimeResultService {

	@Autowired
	private RealTimeResultDao entityDao;
	
	@Override
    public GenericDao<RealTimeResult, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public long selecCount(RealTimeResultVO realTimeResultVO) {
		try {
			return this.entityDao.selecCount(realTimeResultVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<RealTimeResult> selectList(RealTimeResultVO realTimeResultVO) {
		Pagination pagination = new Pagination();
		pagination.setPageOffset(realTimeResultVO.getOffset());
		pagination.setPageSize(Long.valueOf(realTimeResultVO.getRows()).intValue());
		
		if(!StringUtils.isEmpty(realTimeResultVO.getOrderCol())){
			realTimeResultVO.setOrderValue("order by "+realTimeResultVO.getOrderCol() 
					+ " " +realTimeResultVO.getOrderType() +",clientid+0 desc");
		}else{
			realTimeResultVO.setOrderValue("order by clientid+0 desc");
		}
		
		try {
			return this.entityDao.selectList(realTimeResultVO, pagination);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<RealTimeResult>();
		}
	}

}
