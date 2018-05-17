package com.hoolai.bi.report.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.PayUserRetentionLtvDao;
import com.hoolai.bi.report.model.entity.whaleUser.PayUserRetentionLtv;
import com.hoolai.bi.report.vo.PayUserRetentionLtvVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class PayUserRetentionLtvDaoImpl extends GenericDaoImpl<PayUserRetentionLtv, Long> implements PayUserRetentionLtvDao {

	@Override
	public String namespace() {
		return PayUserRetentionLtv.class.getName();
	}

	@Override
	public List<Map<String, Object>> selectMapList(PayUserRetentionLtvVO retentionVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectMapList", retentionVO);
	}

	@Override
	public List<PayUserRetentionLtvVO> selectAvgRetentionDataList(
			PayUserRetentionLtvVO retentionVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAvgRetentionDataList", retentionVO);
	}

	
}
