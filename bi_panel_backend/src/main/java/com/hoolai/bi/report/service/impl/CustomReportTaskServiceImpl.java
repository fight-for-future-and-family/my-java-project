package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.CustomReportTaskDao;
import com.hoolai.bi.report.model.entity.CustomReportTask;
import com.hoolai.bi.report.service.CustomReportTaskService;
import com.hoolai.bi.report.vo.CustomReportTaskVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class CustomReportTaskServiceImpl extends GenericServiceImpl<CustomReportTask, Long> implements CustomReportTaskService {

	@Autowired
	private CustomReportTaskDao entityDao;
	
	@Override
    public GenericDao<CustomReportTask, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public long selectCount(CustomReportTaskVO taskVO) {
		try {
			return this.entityDao.selecCount(taskVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}
	
	@Override
	public long selectManagerCount(CustomReportTaskVO taskVO) {
		try {
			return this.entityDao.selectManagerCount(taskVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}

	@Override
	public List<CustomReportTask> selectList(CustomReportTaskVO taskVO) {
		return this.entityDao.selectList(taskVO);
	}
	
	@Override
	public List<CustomReportTask> selectManagerList(CustomReportTaskVO taskVO) {
		return this.entityDao.selectManagerList(taskVO);
	}

	@Override
	public List<CustomReportTask> findTaskByTaskCode(CustomReportTaskVO taskVO) {
		return this.entityDao.findTaskByTaskCode(taskVO);
	}

	@Override
	public List<CustomReportTask> selectOneByTaskCode(CustomReportTaskVO taskVO) {
		return this.entityDao.selectOneByTaskCode(taskVO);
	}

	@Override
	public long delTask(CustomReportTaskVO taskVO) {
		return this.entityDao.delTask(taskVO);
	}

}
