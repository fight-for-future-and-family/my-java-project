package com.hoolai.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.dao.CustomReportTemColumnsDao;
import com.hoolai.manage.model.CustomReportTemColumns;
import com.hoolai.manage.service.CustomReportTemColumnsService;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class CustomReportTemColumnsImpl extends GenericServiceImpl<CustomReportTemColumns, Long> implements
		CustomReportTemColumnsService {

	@Autowired
	private CustomReportTemColumnsDao dao;

	@Override
	public GenericDao<CustomReportTemColumns, Long> getGenricDao() {
		return this.dao;
	}

	@Override
	public void deleteByTemplateId(Long id) {
		this.dao.deleteByTemplateId(id);
	}

	@Override
	public List<CustomReportTemColumns> selectColumnsByTemplateId(Long id) {
		return dao.selectColumnsByTemplateId(id);
	}

}
