package com.hoolai.manage.dao;

import java.util.List;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.model.CustomReportTemColumns;


public interface CustomReportTemColumnsDao extends GenericDao<CustomReportTemColumns, Long> {

	void deleteByTemplateId(Long id);

	List<CustomReportTemColumns> selectColumnsByTemplateId(Long id);

}
