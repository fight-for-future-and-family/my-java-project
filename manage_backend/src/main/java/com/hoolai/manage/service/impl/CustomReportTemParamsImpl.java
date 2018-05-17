package com.hoolai.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.dao.CustomReportTemParamsDao;
import com.hoolai.manage.model.CustomReportTemParams;
import com.hoolai.manage.service.CustomReportTemParamsService;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class CustomReportTemParamsImpl extends GenericServiceImpl<CustomReportTemParams, Long> implements
		CustomReportTemParamsService {

	@Autowired
	private CustomReportTemParamsDao dao;

	@Override
	public GenericDao<CustomReportTemParams, Long> getGenricDao() {
		return this.dao;
	}

	@Override
	public List<CustomReportTemParams> selectListByTemplateId(Long templateId) {
		return this.dao.selectListByTemplateId(templateId);
	}

	@Override
	public void deleteByTemplateId(Long id) {
		this.dao.deleteByTemplateId(id);
	}

}
