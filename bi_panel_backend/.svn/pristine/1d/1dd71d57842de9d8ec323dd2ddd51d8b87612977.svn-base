package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.SourceCpaDailyReportDao;
import com.hoolai.bi.report.model.entity.SourceCpaDailyReport;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.hoolai.bi.report.service.SourceCpaDailyReportService;
import com.hoolai.bi.report.vo.SourceCpaDailyReportVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class SourceCpaDailyReportServiceImpl extends GenericServiceImpl<SourceCpaDailyReport, Long> implements SourceCpaDailyReportService {
	
	@Autowired
	private SourceCpaDailyReportDao entityDao;
	
	@Override
    public GenericDao<SourceCpaDailyReport, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public List<String> selectGameSources(SourceCpaDailyReportVO cpaSourceDailyReportVO) {
		return entityDao.selectGameSources(cpaSourceDailyReportVO);
	}

	@Override
	public List<SourceCpaDailyReport> selectListBySource(SourceCpaDailyReportVO cpaSourceDailyReportVO) {
		String col = cpaSourceDailyReportVO.getOrderCol();
		if(col == null){
			cpaSourceDailyReportVO.setOrderValue(" order by pu desc");
		}else{
			cpaSourceDailyReportVO.setOrderValue(" order by "+col+" "+cpaSourceDailyReportVO.getOrderType());
		}
		return entityDao.selectListBySource(cpaSourceDailyReportVO);
	}

	@Override
	public Long selectBySourceCount(SourceCpaDailyReportVO cpaSourceDailyReportVO) {
		try {
			return entityDao.selectBySourceCount(cpaSourceDailyReportVO);
		} catch (Exception e) {
			return 0l;
		}
	}
	
	@Override
	public int saveCpaSourceDailysReport(List<SourceCpaDailyReport> resultList) {
		return this.entityDao.saveCpaSourceDailysReport(resultList);
	}

	@Override
	public void delSourceDailyReportByDay(SourceCpaDailyReportVO sourceDailyReportVO) {
		this.entityDao.delSourceDailyReportByDay(sourceDailyReportVO);
	}
	
}
