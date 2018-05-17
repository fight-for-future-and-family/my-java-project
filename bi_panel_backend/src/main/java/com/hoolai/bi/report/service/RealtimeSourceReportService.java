package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.RealtimeSourceReport;
import com.hoolai.bi.report.vo.RealtimeSourceReportVO;
import com.jian.service.GenericService;

public interface RealtimeSourceReportService extends GenericService<RealtimeSourceReport, Long>{

	List<RealtimeSourceReport> selectList(RealtimeSourceReportVO sourceReportVO);

	Long selecCount(RealtimeSourceReportVO sourceReportVO);

}
