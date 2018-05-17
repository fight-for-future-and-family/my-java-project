package com.hoolai.manage.service;


import java.util.List;

import com.hoolai.manage.model.CustomReportModel;
import com.hoolai.manage.vo.CustomReportModelVO;
import com.jian.service.GenericService;

public interface CustomReportModelService extends GenericService<CustomReportModel, Long> {

	CustomReportModelVO selectEditModel(CustomReportModelVO customReportModelVO);

	List<CustomReportModel> selectGameAllModel(CustomReportModelVO vo);

	Long isExist(String code);
}
