package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.ClientLevelNewPaymentDao;
import com.hoolai.bi.report.model.entity.ClientLevelNewPayment;
import com.hoolai.bi.report.model.entity.LevelInstallDauReport;
import com.hoolai.bi.report.vo.ClientLevelNewPaymentVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class ClientLevelNewPaymentDaoImpl extends GenericDaoImpl<ClientLevelNewPayment, Long> implements ClientLevelNewPaymentDao {

	@Override
	public String namespace() {
		return ClientLevelNewPayment.class.getName();
	}
	
	@Override
	public List<LevelInstallDauReport> getLevelNewPayReportList(ClientLevelNewPaymentVO clientLevelNewPaymentVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".getLevelNewPayReportList", clientLevelNewPaymentVO);
	}

	@Override
	public List<Integer> selectClientids(ClientLevelNewPaymentVO clientLevelNewPaymentVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectClientids", clientLevelNewPaymentVO);
	}

}
