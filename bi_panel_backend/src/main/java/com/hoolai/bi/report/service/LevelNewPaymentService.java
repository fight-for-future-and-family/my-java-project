package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.LevelInstallDauReport;
import com.hoolai.bi.report.model.entity.LevelNewPayment;
import com.hoolai.bi.report.vo.LevelNewPaymentVO;
import com.jian.service.GenericService;

public interface LevelNewPaymentService extends GenericService<LevelNewPayment, Long>{

	List<LevelInstallDauReport> getLevelNewPayReportList(LevelNewPaymentVO levelNewPaymentVO,String queryType);


}
