package com.hoolai.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.dao.CustomReportModelDao;
import com.hoolai.manage.model.CustomReportModel;
import com.hoolai.manage.service.CustomReportModelService;
import com.hoolai.manage.vo.CustomReportModelVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class CustomReportModelServiceImpl extends GenericServiceImpl<CustomReportModel, Long> implements
		CustomReportModelService {

	@Autowired
	private CustomReportModelDao dao;

	@Override
	public GenericDao<CustomReportModel, Long> getGenricDao() {
		return this.dao;
	}

	@Override
	public CustomReportModelVO selectEditModel(CustomReportModelVO customReportModelVO) {
		return this.dao.selectEditModel(customReportModelVO);
	}

	@Override
	public List<CustomReportModel> selectGameAllModel(CustomReportModelVO vo) {
		return this.dao.selectGameAllModel(vo);
	}

	@Override
	public Long isExist(String code) {
		return this.dao.isExist(code);
	}

}
