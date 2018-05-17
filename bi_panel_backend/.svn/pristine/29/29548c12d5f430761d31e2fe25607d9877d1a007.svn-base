package com.hoolai.bi.report.service;


import java.util.List;

import com.hoolai.bi.report.model.entity.CustomReportTask;
import com.hoolai.bi.report.vo.CustomReportTaskVO;
import com.jian.service.GenericService;

public interface CustomReportTaskService extends GenericService<CustomReportTask, Long>{

	long selectCount(CustomReportTaskVO taskVO);

	List<CustomReportTask> selectList(CustomReportTaskVO taskVO);
	
	List<CustomReportTask> findTaskByTaskCode(CustomReportTaskVO taskVO);

	List<CustomReportTask> selectOneByTaskCode(CustomReportTaskVO taskVO2);

	long selectManagerCount(CustomReportTaskVO taskVO);

	List<CustomReportTask> selectManagerList(CustomReportTaskVO taskVO);

	long delTask(CustomReportTaskVO taskVO);

}
