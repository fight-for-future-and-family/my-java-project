package com.hoolai.panel.web.processor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomReportThreadPool {

	private final ThreadPoolExecutor executor;

    static class CustomReportThreadPoolFactory implements ThreadFactory {
        final ThreadGroup group;

        final AtomicInteger threadNumber = new AtomicInteger(1);

        final String namePrefix;

        CustomReportThreadPoolFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "CustomReportThreadPool" + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
    
    public CustomReportThreadPool(int minThreadNum, int maxThreadNum) {
        this(minThreadNum, maxThreadNum, 1000);
    }
    
    public CustomReportThreadPool(int minThreadNum, int maxThreadNum, int queueTaskNum) {
        executor = new ThreadPoolExecutor(minThreadNum, maxThreadNum, 30000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(queueTaskNum), new CustomReportThreadPoolFactory());
    }

    public int getThreadNum() {
        return executor.getPoolSize();
    }

    public int getQueueNum() {
        return executor.getQueue().size();
    }

    public long getCompleteTaskNum() {
        return executor.getCompletedTaskCount();
    }

    public long getScheduleTaskNum() {
        return executor.getTaskCount();
    }

    public int getActiveThreadNum() {
        return executor.getActiveCount();
    }

    public int getMaxThreadNumInHistory() {
        return executor.getLargestPoolSize();
    }

    public void execute(Runnable command) {
        executor.execute(command);
    }
    
    public static void main(String[] args) {
    	CustomReportThreadPool customReportThreadPool = new CustomReportThreadPool(10, 100);
	}
}
