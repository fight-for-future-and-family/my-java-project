package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.CustomReportTask;
import com.hoolai.bi.report.vo.CustomReportTaskVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;

public interface CustomReportTaskDao extends GenericDao<CustomReportTask, Long> {

	List<CustomReportTask> selectList(CustomReportTaskVO taskVO);

	List<CustomReportTask> findTaskByTaskCode(CustomReportTaskVO taskVO);

	List<CustomReportTask> selectOneByTaskCode(CustomReportTaskVO taskVO);

	long selectManagerCount(CustomReportTaskVO taskVO);

	List<CustomReportTask> selectManagerList(CustomReportTaskVO taskVO);

	long delTask(CustomReportTaskVO taskVO);
	
}
