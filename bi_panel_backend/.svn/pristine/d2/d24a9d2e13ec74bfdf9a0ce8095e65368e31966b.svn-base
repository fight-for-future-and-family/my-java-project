package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.MonitorMetricsDao;
import com.hoolai.bi.report.model.entity.MonitorMetrics;
import com.hoolai.bi.report.service.MonitorMetricsService;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class MonitorMetricsServiceImpl extends GenericServiceImpl<MonitorMetrics, Long> implements MonitorMetricsService {
	
	@Autowired
	private MonitorMetricsDao entityDao;

	@Override
	public GenericDao<MonitorMetrics, Long> getGenricDao() {
		return this.entityDao;
	}

	@Override
	public List<MonitorMetrics> selectList(MonitorMetrics monitorMetric) {
		return entityDao.selectList(monitorMetric);
	}

	@Override
	public int updateById(MonitorMetrics monitorMetric) {
		return entityDao.updateById(monitorMetric);
	}

	@Override
	public int deleteById(MonitorMetrics monitorMetric) {
		return entityDao.deleteById(monitorMetric);
	}

	@Override
	public int saveMonitorMetrics(MonitorMetrics monitorMetrics) {
		return entityDao.saveMonitorMetrics(monitorMetrics);
	}

	@Override
	public int selectMonitorMetricExists(MonitorMetrics monitorMetrics) {
		return entityDao.selectMonitorMetricExists(monitorMetrics);
	}


}
