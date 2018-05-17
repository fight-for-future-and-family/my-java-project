package com.hoolai.manage.service;

import java.util.List;

import com.hoolai.manage.model.CustomReportTemParams;
import com.jian.service.GenericService;

public interface CustomReportTemParamsService extends GenericService<CustomReportTemParams, Long> {

	List<CustomReportTemParams> selectListByTemplateId(Long valueOf);

	void deleteByTemplateId(Long id);

}
