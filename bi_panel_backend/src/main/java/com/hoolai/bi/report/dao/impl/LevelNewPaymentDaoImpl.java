package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.LevelNewPaymentDao;
import com.hoolai.bi.report.model.entity.LevelInstallDauReport;
import com.hoolai.bi.report.model.entity.LevelNewPayment;
import com.hoolai.bi.report.vo.LevelNewPaymentVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class LevelNewPaymentDaoImpl extends GenericDaoImpl<LevelNewPayment, Long> implements LevelNewPaymentDao {

	@Override
	public String namespace() {
		return LevelNewPayment.class.getName();
	}
	
	@Override
	public List<LevelInstallDauReport> getLevelNewPayReportList(LevelNewPaymentVO levelNewPaymentVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".getLevelNewPayReportList", levelNewPaymentVO);
	}

}
