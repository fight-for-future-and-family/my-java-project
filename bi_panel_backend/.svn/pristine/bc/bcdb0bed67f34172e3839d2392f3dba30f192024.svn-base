package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.CustomReportTaskDao;
import com.hoolai.bi.report.model.entity.CustomReportTask;
import com.hoolai.bi.report.vo.CustomReportTaskVO;
import com.hoolai.dao.impl.GenericDaoImpl;


@Repository
public class CustomReportTaskDaoImpl extends GenericDaoImpl<CustomReportTask, Long> implements CustomReportTaskDao {

	@Override
	public String namespace() {
		return CustomReportTask.class.getName();
	}

	@Override
	public List<CustomReportTask> selectList(CustomReportTaskVO taskVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", taskVO);
	}

	@Override
	public List<CustomReportTask> findTaskByTaskCode(CustomReportTaskVO taskVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".findTaskByTaskCode", taskVO);
	}

	@Override
	public List<CustomReportTask> selectOneByTaskCode(CustomReportTaskVO taskVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectOneByTaskCode", taskVO);
	}

	@Override
	public long selectManagerCount(CustomReportTaskVO taskVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectManagerCount", taskVO);
	}

	@Override
	public List<CustomReportTask> selectManagerList(CustomReportTaskVO taskVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectManagerList", taskVO);
	}

	@Override
	public long delTask(CustomReportTaskVO taskVO) {
		return this.sqlSessionTemplate.delete("delTask", taskVO);
	}

	
	
}
