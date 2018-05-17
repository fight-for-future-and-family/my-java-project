package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.RealtimeSourceReport;
import com.hoolai.bi.report.vo.RealtimeSourceReportVO;
import com.hoolai.dao.GenericDao;

public interface RealtimeSourceReportDao extends GenericDao<RealtimeSourceReport, Long> {


	List<RealtimeSourceReport> selectList(RealtimeSourceReportVO realtimeSourceReport);
    
}
