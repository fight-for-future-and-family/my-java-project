package monitor;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hoolai.bi.monitor.service.MonitorMetricRecordService;
import com.hoolai.bi.test.BaseTest;

public class TestMonitorMetric extends BaseTest {
	
	@Autowired
	private MonitorMetricRecordService metricRecordService;
	
	@Test
	public void sync() throws Exception {
		this.metricRecordService.syncMetricRecord();
	}

}
