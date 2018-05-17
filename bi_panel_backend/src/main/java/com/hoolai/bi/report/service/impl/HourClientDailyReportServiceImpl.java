package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.HourClientDailyReportDao;
import com.hoolai.bi.report.model.entity.HourClientDailyReport;
import com.hoolai.bi.report.model.entity.HourSourceDailyReport;
import com.hoolai.bi.report.service.HourClientDailyReportService;
import com.hoolai.bi.report.vo.HourClientDailyReportVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class HourClientDailyReportServiceImpl extends GenericServiceImpl<HourClientDailyReport, Long> implements HourClientDailyReportService {

	@Autowired
	private HourClientDailyReportDao entityDao;
	
	@Override
    public GenericDao<HourClientDailyReport, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public List<String> selectGameClients(HourClientDailyReportVO clientDailyReportVO) {
		return entityDao.selectGameClients(clientDailyReportVO);
	}

	@Override
	public long selectCount(HourClientDailyReportVO clientDailyReportVO) {
		try {
			return entityDao.selecCount(clientDailyReportVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public List<HourClientDailyReport> selectList(HourClientDailyReportVO clientDailyReportVO) {
		if(!StringUtils.isEmpty(clientDailyReportVO.getOrderCol())){
			clientDailyReportVO.setOrderValue("order by "+clientDailyReportVO.getOrderCol() 
					+" "+ clientDailyReportVO.getOrderType());
		}else{
			clientDailyReportVO.setOrderValue("order by day ");
		}
		
		return entityDao.selectList(clientDailyReportVO);
	}
	
	@Override
	public void removeHourDaily(HourClientDailyReport hourClientDailyReport) {
		entityDao.removeHourDaily(hourClientDailyReport);
	}
	
}
