package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.HourSourceDailyReportDao;
import com.hoolai.bi.report.model.entity.HourSourceDailyReport;
import com.hoolai.bi.report.service.HourSourceDailyReportService;
import com.hoolai.bi.report.vo.HourSourceDailyReportVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class HourSourceDailyReportServiceImpl extends GenericServiceImpl<HourSourceDailyReport, Long> implements HourSourceDailyReportService {

	@Autowired
	private HourSourceDailyReportDao entityDao;
	
	@Override
    public GenericDao<HourSourceDailyReport, Long> getGenricDao() {
            return this.entityDao;
    }


	@Override
	public List<String> selectGameSources(HourSourceDailyReportVO sourceDailyReportVO) {
		return entityDao.selectGameSources(sourceDailyReportVO);
	}

	@Override
	public long selectCount(HourSourceDailyReportVO sourceDailyReportVO) {
		try {
			return entityDao.selecCount(sourceDailyReportVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}


	@Override
	public List<HourSourceDailyReport> selectList(HourSourceDailyReportVO sourceDailyReportVO) {
		if(!StringUtils.isEmpty(sourceDailyReportVO.getOrderCol())){
			sourceDailyReportVO.setOrderValue("order by "+sourceDailyReportVO.getOrderCol() 
					+" "+ sourceDailyReportVO.getOrderType());
		}else{
			sourceDailyReportVO.setOrderValue("order by day ");
		}
		
		return entityDao.selectList(sourceDailyReportVO);
	}


	@Override
	public void removeHourDaily(HourSourceDailyReport hourSourceDailyReport) {
		entityDao.removeHourDaily(hourSourceDailyReport);
	}

}
