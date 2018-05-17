package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.MonitorMetricsDao;
import com.hoolai.bi.report.model.entity.MonitorMetrics;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class MonitorMetricsDaoImpl extends GenericDaoImpl<MonitorMetrics, Long> implements MonitorMetricsDao {

	@Override
	public String namespace() {
		return MonitorMetrics.class.getName();
	}

	@Override
	public List<MonitorMetrics> selectList(MonitorMetrics monitorMetric) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", monitorMetric);
	}

	@Override
	public int updateById(MonitorMetrics monitorMetric) {
		return this.sqlSessionTemplate.update(this.namespace()+".updateById", monitorMetric);
	}

	@Override
	public int deleteById(MonitorMetrics monitorMetric) {
		return this.sqlSessionTemplate.delete(this.namespace()+".deleteById", monitorMetric);
	}

	@Override
	public int saveMonitorMetrics(MonitorMetrics monitorMetrics) {
		return this.sqlSessionTemplate.insert("saveMonitorMetrics", monitorMetrics);
	}

	@Override
	public int selectMonitorMetricExists(MonitorMetrics monitorMetrics) {
		return this.sqlSessionTemplate.selectOne("selectMonitorMetricExists", monitorMetrics);
	}
	
}
