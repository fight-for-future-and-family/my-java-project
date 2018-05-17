package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.MonitorMetrics;
import com.hoolai.dao.GenericDao;

public interface MonitorMetricsDao extends GenericDao<MonitorMetrics, Long> {

	List<MonitorMetrics> selectList(MonitorMetrics monitorMetric);

	int updateById(MonitorMetrics monitorMetric);

	int deleteById(MonitorMetrics monitorMetric);

	int saveMonitorMetrics(MonitorMetrics monitorMetrics);

	int selectMonitorMetricExists(MonitorMetrics monitorMetrics);

}
