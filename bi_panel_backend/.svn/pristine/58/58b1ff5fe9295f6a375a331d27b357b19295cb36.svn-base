package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.RealtimeSourceReportDao;
import com.hoolai.bi.report.model.entity.RealtimeSourceReport;
import com.hoolai.bi.report.vo.RealtimeSourceReportVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class RealtimeSourceReportDaoImpl extends GenericDaoImpl<RealtimeSourceReport, Long> implements RealtimeSourceReportDao {

	@Override
	public String namespace() {
		return RealtimeSourceReport.class.getName();
	}
	@Override
	public List<RealtimeSourceReport> selectList(RealtimeSourceReportVO adShortUrlVO) {
		return sqlSessionTemplate.selectList(this.namespace()+".selectList", adShortUrlVO);
	}

	
}
