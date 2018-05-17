package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.LevelInstallDauReport;
import com.hoolai.bi.report.model.entity.LevelNewPayment;
import com.hoolai.bi.report.vo.LevelNewPaymentVO;
import com.hoolai.dao.GenericDao;

public interface LevelNewPaymentDao extends GenericDao<LevelNewPayment, Long> {

	List<LevelInstallDauReport> getLevelNewPayReportList(LevelNewPaymentVO levelNewPaymentVO);


    
}
