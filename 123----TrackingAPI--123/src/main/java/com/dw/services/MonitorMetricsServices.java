package com.dw.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.dw.metrics.MetricRecordMess;
import com.hoolai.bi.monitor.MonitorCounterGroup;
import com.hoolai.bi.monitor.MonitorCounterMess;
import com.hoolai.bi.thread.ScheduledSystemThreadFactory;
import com.hoolai.bi.thread.ScheduledSystemThreadFactory.ScheduledSystemRejectedExecutionHandler;
import com.hoolai.util.IPUtils;

public class MonitorMetricsServices {
	
	private static final Logger EXCEPTION_LOGGER = Logger.getLogger("exceptionbi");
	
	private final ScheduledThreadPoolExecutor threadPoolExecutor;
	
	private final ReciveMonitorCounter succReciveMonitorCounter;
	
	private final ReciveMonitorCounter failReciveMonitorCounter;
	
	public MonitorMetricsServices() {
		super();
		 this.threadPoolExecutor= new ScheduledThreadPoolExecutor(
					1, new ScheduledSystemThreadFactory("bi-monitor-metrics-services"),
					new ScheduledSystemRejectedExecutionHandler());
		this.succReciveMonitorCounter=new ReciveMonitorCounter("succ");
		this.failReciveMonitorCounter=new ReciveMonitorCounter("fail");
		
		this.init();
	}
	
	private void init(){
		
		class SendMonitorCounterThread implements Runnable{
			
			private final DateFormat DAY_DATE_FORMAT = new SimpleDateFormat(
					"yyyy-MM-dd");

			private final DateFormat TIME_DATE_FORMAT = new SimpleDateFormat(
					"HH:mm:ss");

			@Override
			public void run() {
				this.sendSucc();
				this.sendFail();
			}
			
			private void sendSucc(){
				this.send(succReciveMonitorCounter);
			}
			
			private void sendFail(){
				this.send(failReciveMonitorCounter);
			}
			
			private void send(MonitorCounterGroup monitorCounterGroup){
				Map<String,MonitorCounterMess> succMap=monitorCounterGroup.prepareSendCurrentMonitorCounterMess();
				if(succMap.isEmpty()){
					return ;
				}
				Date now = new Date();

				for (Entry<String, MonitorCounterMess> entry : succMap.entrySet()) {
					try {
						// 如果记录是0，则不发送
						if(entry.getValue().getCount()<1){
							continue;
						}
						String key = entry.getKey();
						String[] keys = key.split("_");
						if (keys.length < 4) {
							continue;
						}
						String metric = keys[0];
						String snid = keys[1];
						String gameid = keys[2];
						String ds = keys[3];
						MetricRecordMess metricRecordMess = new MetricRecordMess();
						metricRecordMess.setFamily(monitorCounterGroup.getType()+"_"+monitorCounterGroup.getName());
						metricRecordMess.setDate(DAY_DATE_FORMAT.format(now));
						metricRecordMess.setMetric(metric);
						metricRecordMess.setNum(new Long(entry.getValue().getCount()).intValue());
						metricRecordMess.setTime(TIME_DATE_FORMAT.format(now));
						metricRecordMess.setServerIp(IPUtils.findLocalServerIPStr());
//						TrackServices.add(snid, "0", gameid, metricRecordMess,
//								ds);
					} catch (Exception e) {
						EXCEPTION_LOGGER.error(e.getMessage(), e);
					}
				}
			}
		}
		
		this.threadPoolExecutor.scheduleAtFixedRate(
				new SendMonitorCounterThread(), 1L, 1L, TimeUnit.MINUTES);
	}
	
	public void succ(String event){
		this.succReciveMonitorCounter.increment(event);
	}
	
	public void fail(String event){
		this.failReciveMonitorCounter.increment(event);
	}

	public class ReciveMonitorCounter extends MonitorCounterGroup{

		protected ReciveMonitorCounter(String name) {
			super("receive", name);
		}
		
	}


}
