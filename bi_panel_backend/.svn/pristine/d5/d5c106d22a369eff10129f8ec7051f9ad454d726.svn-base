package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.SourceDailyReportDao;
import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.service.SourceDailyReportService;
import com.hoolai.bi.report.vo.SourceDailyReportVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class SourceDailyReportServiceImpl extends GenericServiceImpl<SourceDailyReport, Long> implements SourceDailyReportService {

	@Autowired
	private SourceDailyReportDao entityDao;
	
	@Override
    public GenericDao<SourceDailyReport, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public List<SourceDailyReportVO> selectInstallForPieList(SourceDailyReportVO sourceDailyReportVO) {
		return entityDao.selectForPieList(sourceDailyReportVO);
	}

	@Override
	public Long selectInstallForPieListCount(SourceDailyReportVO sourceDailyReportVO) {
		return entityDao.selectForPieListCount(sourceDailyReportVO);
	}

	@Override
	public List<SourceDailyReportVO> selectDauForPieList(SourceDailyReportVO sourceDailyReportVO) {
		return entityDao.selectDauForPieList(sourceDailyReportVO);
	}

	@Override
	public Long selectDauForPieListCount(SourceDailyReportVO sourceDailyReportVO) {
		return entityDao.selectDauForPieListCount(sourceDailyReportVO);
	}

	@Override
	public List<String> selectGameSources(SourceDailyReportVO sourceDailyReportVO) {
		return entityDao.selectGameSources(sourceDailyReportVO);
	}

	@Override
	public List<SourceDailyReportVO> selectPaymentForPieList(SourceDailyReportVO sourceDailyReportVO) {
		return entityDao.selectPaymentForPieList(sourceDailyReportVO);
	}

	@Override
	public Long selectPaymentForPieListCount(SourceDailyReportVO sourceDailyReportVO) {
		return entityDao.selectPaymentForPieListCount(sourceDailyReportVO);
	}

	@Override
	public long selectCount(SourceDailyReportVO sourceDailyReportVO) {
		try {
			return entityDao.selecCount(sourceDailyReportVO);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<SourceDailyReport> selectList(SourceDailyReportVO sourceDailyReportVO) {
		String col = sourceDailyReportVO.getOrderCol();
		if(col == null){
			sourceDailyReportVO.setOrderValue(" order by day desc");
		}else{
			sourceDailyReportVO.setOrderValue(" order by "+col+" "+sourceDailyReportVO.getOrderType());
		}
		
		return entityDao.selectList(sourceDailyReportVO);
	}

	@Override
	public List<SourceDailyReport> selectListBySource(SourceDailyReportVO sourceDailyReportVO) {
		String col = sourceDailyReportVO.getOrderCol();
		if(col == null){
			sourceDailyReportVO.setOrderValue(" order by pu desc");
		}else{
			sourceDailyReportVO.setOrderValue(" order by "+col+" "+sourceDailyReportVO.getOrderType());
		}
		return entityDao.selectListBySource(sourceDailyReportVO);
	}

	@Override
	public List<SourceDailyReport> selectListByMonth(SourceDailyReportVO sourceDailyReportVO) {
		String col = sourceDailyReportVO.getOrderCol();
		if(col == null){
			sourceDailyReportVO.setOrderValue(" order by day desc");
		}else{
			sourceDailyReportVO.setOrderValue(" order by "+col+" "+sourceDailyReportVO.getOrderType());
		}
		return entityDao.selectListByMonth(sourceDailyReportVO);
	}

	@Override
	public Long selectByMonthCount(SourceDailyReportVO sourceDailyReportVO) {
		try {
			return entityDao.selectByMonthCount(sourceDailyReportVO);
		} catch (Exception e) {
			return 0l;
		}
	}

	@Override
	public Long selectBySourceCount(SourceDailyReportVO sourceDailyReportVO) {
		try {
			return entityDao.selectBySourceCount(sourceDailyReportVO);
		} catch (Exception e) {
			return 0l;
		}
	}

	@Override
	public List<SourceDailyReport> getMatchJoin(
			SourceDailyReportVO sourceDailyReportVO) {
		return this.entityDao.getMatchJoin(sourceDailyReportVO);
	}

	@Override
	public List<SourceDailyReport> selectListJoin(SourceDailyReportVO sourceDailyReportVO) {
		String col = sourceDailyReportVO.getOrderCol();
		if(col == null){
			sourceDailyReportVO.setOrderValue(" order by day desc");
		}else{
			sourceDailyReportVO.setOrderValue(" order by "+col+" "+sourceDailyReportVO.getOrderType());
		}
		
		return this.entityDao.selectListJoin(sourceDailyReportVO);
	}

	@Override
	public SourceDailyReport getSumMatchSourceJoin(SourceDailyReportVO sourceDailyReportVO) {
		return this.entityDao.getSumMatchSourceJoin(sourceDailyReportVO);
	}
	
}
