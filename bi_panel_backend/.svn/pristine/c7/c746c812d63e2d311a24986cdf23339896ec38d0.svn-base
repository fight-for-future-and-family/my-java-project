package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.RealtimeSourceReportDao;
import com.hoolai.bi.report.model.entity.RealtimeSourceReport;
import com.hoolai.bi.report.service.RealtimeSourceReportService;
import com.hoolai.bi.report.vo.RealtimeSourceReportVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class RealtimeSourceReportServiceImpl extends GenericServiceImpl<RealtimeSourceReport, Long> implements RealtimeSourceReportService {

	@Autowired
	private RealtimeSourceReportDao entityDao;
	
	@Override
    public GenericDao<RealtimeSourceReport, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public List<RealtimeSourceReport> selectList(RealtimeSourceReportVO sourceReportVO) {
		if(!StringUtils.isEmpty(sourceReportVO.getOrderCol())){
			sourceReportVO.setOrderValue("order by "+sourceReportVO.getOrderCol() 
					+" "+ sourceReportVO.getOrderType());
		}else{
			sourceReportVO.setOrderValue("order by ds ");
		}
		return this.entityDao.selectList(sourceReportVO);
	}

	@Override
	public Long selecCount(RealtimeSourceReportVO sourceReportVO) {
		try {
			return this.entityDao.selecCount(sourceReportVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}

}
