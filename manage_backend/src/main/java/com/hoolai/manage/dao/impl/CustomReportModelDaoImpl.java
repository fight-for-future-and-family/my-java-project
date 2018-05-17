package com.hoolai.manage.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.CustomReportModelDao;
import com.hoolai.manage.model.CustomReportModel;
import com.hoolai.manage.vo.CustomReportModelVO;

@Repository
public class CustomReportModelDaoImpl extends GenericDaoImpl<CustomReportModel, Long> implements CustomReportModelDao {

	@Override
	public String namespace() {
		return CustomReportModel.class.getName();
	}

	@Override
	public CustomReportModelVO selectEditModel(CustomReportModelVO customReportModelVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectEditModel", customReportModelVO);
	}

	@Override
	public List<CustomReportModel> selectGameAllModel(CustomReportModelVO vo) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectGameAllModel", vo);
	}

	@Override
	public Long isExist(String code) {
		return sqlSessionTemplate.selectOne(this.namespace()+".isExist", code);
	}
	
}
