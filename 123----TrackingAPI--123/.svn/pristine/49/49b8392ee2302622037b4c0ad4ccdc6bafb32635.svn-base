package com.dw.appender;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.dw.metrics.MetricRecordMess;
import com.dw.services.TrackServices;
import com.hoolai.core.Constants;
import com.hoolai.util.IPUtils;

public class CountMetricsRecords {

	private static final Logger EXCEPTION_LOGGER = Logger
			.getLogger("exceptionbi");

	private static final Logger SUCC_LOGGER = Logger.getLogger("succRecords");

	private static final Logger FAIL_LOGGER = Logger.getLogger("failRecords");

	private static CountMetricsRecords countMetricsRecords;

	public static CountMetricsRecords getInstance() {
		if (countMetricsRecords == null) {
			countMetricsRecords = new CountMetricsRecords();
		}
		return countMetricsRecords;
	}

	private CountMetricsRecords() {
		super();
		this.init();
	}

	private final ConcurrentMap<String, Long> SUCC_CATEGROY_RECORDS = new ConcurrentHashMap<String, Long>(
			8);

	private final ConcurrentMap<String, Long> FAIL_CATEGROY_RECORDS = new ConcurrentHashMap<String, Long>(
			8);

	private final ReentrantLock SUCC_REENTRANT_LOCK = new ReentrantLock();

	private final ReentrantLock FAIL_REENTRANT_LOCK = new ReentrantLock();

	private final ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(
			2, new ScheduledSystemThreadFactory("bi-count-metrics-records"),
			new ScheduledSystemRejectedExecutionHandler());

	private void init() {

		SendPersistentRecords succPersistentRecords = new SendPersistentRecords(
				"succ", SUCC_LOGGER, SUCC_CATEGROY_RECORDS, SUCC_REENTRANT_LOCK);

		SendPersistentRecords failPersistentRecords = new SendPersistentRecords(
				"fail", FAIL_LOGGER, FAIL_CATEGROY_RECORDS, FAIL_REENTRANT_LOCK);

		this.threadPoolExecutor.scheduleAtFixedRate(
				new PersistentRecordsThread(succPersistentRecords,
						failPersistentRecords), 30L, 30L, TimeUnit.SECONDS);
	}

	private class PersistentRecordsThread implements Runnable {

		private final SendPersistentRecords[] persistentRecords;

		public PersistentRecordsThread(
				SendPersistentRecords... persistentRecords) {
			super();
			this.persistentRecords = persistentRecords;
		}

		@Override
		public void run() {
			if (persistentRecords == null || persistentRecords.length < 1) {
				return;
			}
			for (SendPersistentRecords sendPersistentRecords : persistentRecords) {
				sendPersistentRecords.exec();
			}
		}
	}

	private class SendPersistentRecords {

		private final DateFormat DAY_DATE_FORMAT = new SimpleDateFormat(
				"yyyy-MM-dd");

		private final DateFormat TIME_DATE_FORMAT = new SimpleDateFormat(
				"HH:mm:ss");

		private final String family;

		private final Logger logger;

		private final ConcurrentMap<String, Long> records;

		private final ReentrantLock reentrantLock;

		public SendPersistentRecords(String family, Logger logger,
				ConcurrentMap<String, Long> records, ReentrantLock reentrantLock) {
			super();
			this.family = family;
			this.logger = logger;
			this.records = records;
			this.reentrantLock = reentrantLock;
		}

		public void exec() {
			try {
				this.reentrantLock.lock();
				this.persistent(records);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				this.reentrantLock.unlock();
			}
		}

		private void persistent(ConcurrentMap<String, Long> records) {
			try {
				if (records.isEmpty()) {
					return;
				}
				Map<String, Long> tmpMap = new HashMap<String, Long>();
				tmpMap.putAll(records);
				records.clear();

				Date now = new Date();

				for (Entry<String, Long> entry : tmpMap.entrySet()) {
					try {
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
						metricRecordMess.setFamily(this.family);
						metricRecordMess.setDate(DAY_DATE_FORMAT.format(now));
						metricRecordMess.setMetric(metric);
						metricRecordMess.setNum(entry.getValue().intValue());
						metricRecordMess.setTime(TIME_DATE_FORMAT.format(now));
						metricRecordMess.setServerIp(IPUtils
								.findLocalServerIPStr());

//						TrackServices.add(snid, "0", gameid, metricRecordMess,
//								ds);
					} catch (Exception e) {
						EXCEPTION_LOGGER.error(e.getMessage(), e);
					}
				}
			} catch (Exception e) {
				EXCEPTION_LOGGER.error(e.getMessage(), e);
			}
		}
	}

	public void succ(String parentCategory, String subCategory) {
		
		if(!Constants.IS_COUNT_METRICS){
			return ;
		}
		if(!this.isRecord(parentCategory, subCategory)){
			return ;
		}
		try {
			this.SUCC_REENTRANT_LOCK.lock();
			this.record(parentCategory, subCategory, SUCC_CATEGROY_RECORDS);
		} catch (Exception e) {
			EXCEPTION_LOGGER.error(e.getMessage(), e);
		} finally {
			this.SUCC_REENTRANT_LOCK.unlock();
		}
	}

	public void fail(String parentCategory, String subCategory) {
		
		if(!Constants.IS_COUNT_METRICS){
			return ;
		}
		if(!this.isRecord(parentCategory, subCategory)){
			return ;
		}
		try {
			this.FAIL_REENTRANT_LOCK.lock();
			this.record(parentCategory, subCategory, FAIL_CATEGROY_RECORDS);
		} catch (Exception e) {
			EXCEPTION_LOGGER.error(e.getMessage(), e);
		} finally {
			this.FAIL_REENTRANT_LOCK.unlock();
		}
	}
	
	private boolean isRecord(String parentCategory, String subCategory){
		if("metricrecordmess".equalsIgnoreCase(parentCategory)){
			return false;
		}
		return true;
	}

	private void record(String parentCategory, String subCategory,
			ConcurrentMap<String, Long> concurrentMap) {
		Long recordNum = concurrentMap.get(subCategory);
		if (recordNum == null) {
			recordNum = 1L;
		} else {
			recordNum += 1;
		}
		concurrentMap.put(subCategory, recordNum);
	}

	public static class ScheduledSystemThreadFactory implements ThreadFactory {

		static final AtomicInteger poolNumber = new AtomicInteger(1);
		final ThreadGroup group;
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final String namePrefix;

		public ScheduledSystemThreadFactory(String name) {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread()
					.getThreadGroup();
			namePrefix = name + "-" + poolNumber.getAndIncrement() + "-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix
					+ threadNumber.getAndIncrement(), 0);
			if (t.isDaemon())
				t.setDaemon(false);
			if (t.getPriority() != Thread.NORM_PRIORITY)
				t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}

	}

	public static class ScheduledSystemRejectedExecutionHandler implements
			RejectedExecutionHandler {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			EXCEPTION_LOGGER.error("ThreadPoolExecutor rejected runnable ");
			if (!executor.isShutdown()) {
				executor.getQueue().poll();
			}
		}
	}

}
