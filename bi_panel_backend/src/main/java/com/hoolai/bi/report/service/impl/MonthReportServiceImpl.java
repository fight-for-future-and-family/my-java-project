package com.hoolai.bi.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.MonthReportDao;
import com.hoolai.bi.report.model.entity.MonthReport;
import com.hoolai.bi.report.service.MonthReportService;
import com.hoolai.bi.report.vo.MonthReportVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class MonthReportServiceImpl extends GenericServiceImpl<MonthReport, Long> implements MonthReportService {

	@Autowired
	private MonthReportDao entityDao;
	
	@Override
    public GenericDao<MonthReport, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public MonthReport get(String snid, String gameid, String month) {
		try {
			MonthReportVO monthReportVO=new MonthReportVO();
			MonthReport entity=monthReportVO.getEntity();
			entity.setSnid(snid);
			entity.setGameid(gameid);
			entity.setMonth(month);
			return this.entityDao.selectOne(monthReportVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
