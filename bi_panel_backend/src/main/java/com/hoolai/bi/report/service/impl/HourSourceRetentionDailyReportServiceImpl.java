package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.HourSourceRetentionDailyReportDao;
import com.hoolai.bi.report.model.entity.HourSourceRetentionDailyReport;
import com.hoolai.bi.report.service.HourSourceRetentionDailyReportService;
import com.hoolai.bi.report.vo.HourSourceRetentionDailyReportVo;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;
@Service
public class HourSourceRetentionDailyReportServiceImpl extends GenericServiceImpl<HourSourceRetentionDailyReport, Long> implements HourSourceRetentionDailyReportService {

	@Autowired
	private HourSourceRetentionDailyReportDao entityDao;
	
	@Override
    public GenericDao<HourSourceRetentionDailyReport, Long> getGenricDao() {
            return this.entityDao;
    }


	@Override
	public List<String> selectGameSources(HourSourceRetentionDailyReportVo hourSourceRetentionDailyReportVo) {
		return entityDao.selectGameSources(hourSourceRetentionDailyReportVo);
	}

	

	@Override
	public long selectCount(HourSourceRetentionDailyReportVo hourSourceRetentionDailyReportVo) {
		try {
			return entityDao.selecCount(hourSourceRetentionDailyReportVo);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}


	@Override
	public List<HourSourceRetentionDailyReport> selectList(HourSourceRetentionDailyReportVo hourSourceRetentionDailyReportVo) {
		if(!StringUtils.isEmpty(hourSourceRetentionDailyReportVo.getOrderCol())){
			hourSourceRetentionDailyReportVo.setOrderValue("order by "+hourSourceRetentionDailyReportVo.getOrderCol() 
					+" "+ hourSourceRetentionDailyReportVo.getOrderType());
		}else{
			hourSourceRetentionDailyReportVo.setOrderValue("order by day ");
		}
		
		return entityDao.selectList(hourSourceRetentionDailyReportVo);
	}


	@Override
	public void removeHourDaily(HourSourceRetentionDailyReport hourSourceRetentionDailyReport) {
		entityDao.removeHourDaily(hourSourceRetentionDailyReport);
	}


	@Override
	public void saveList(List<HourSourceRetentionDailyReport> hourSourceRetentionDailyReport) {
		entityDao.saveList(hourSourceRetentionDailyReport);
	}

	
	



}
