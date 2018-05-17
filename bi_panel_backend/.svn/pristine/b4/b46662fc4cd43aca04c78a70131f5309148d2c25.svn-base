package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.HourLtvSourceDailyReportDao;
import com.hoolai.bi.report.model.entity.HourLtvSourceDailyReport;
import com.hoolai.bi.report.service.HourLtvSourceDailyReportService;
import com.hoolai.bi.report.vo.HourLtvSourceDailyReportVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class HourLtvSourceDailyReportServiceImpl extends GenericServiceImpl<HourLtvSourceDailyReport, Long> implements HourLtvSourceDailyReportService {

	@Autowired
	private HourLtvSourceDailyReportDao entityDao;
	
	@Override
    public GenericDao<HourLtvSourceDailyReport, Long> getGenricDao() {
            return this.entityDao;
    }


	@Override
	public List<String> selectGameSources(HourLtvSourceDailyReportVO sourceDailyReportVO) {
		return entityDao.selectGameSources(sourceDailyReportVO);
	}

	@Override
	public long selectCount(HourLtvSourceDailyReportVO sourceDailyReportVO) {
		try {
			return entityDao.selecCount(sourceDailyReportVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}


	@Override
	public List<HourLtvSourceDailyReport> selectList(HourLtvSourceDailyReportVO sourceDailyReportVO) {
		if(!StringUtils.isEmpty(sourceDailyReportVO.getOrderCol())){
			sourceDailyReportVO.setOrderValue("order by "+sourceDailyReportVO.getOrderCol() 
					+" "+ sourceDailyReportVO.getOrderType());
		}else{
			sourceDailyReportVO.setOrderValue("order by day ");
		}
		
		return entityDao.selectList(sourceDailyReportVO);
	}


	@Override
	public void removeHourDaily(HourLtvSourceDailyReport hourSourceDailyReport) {
		entityDao.removeHourDaily(hourSourceDailyReport);
	}


	@Override
	public void saveList(List<HourLtvSourceDailyReport> hourLtvDailyReportList) {
		entityDao.saveList(hourLtvDailyReportList);
	}

}
