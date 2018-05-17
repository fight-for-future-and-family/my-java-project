package com.hoolai.bi.report.job;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * 同步用户中心线程池 对外部不提供执行普通任务的方法
 * 
 * @author jiong
 * 
 */
public class SystemThreadPoolExecutor {

	private static final Logger logger = Logger.getLogger(SystemThreadPoolExecutor.class);

	// 初始大小
	private static final int corePoolSize = 10;
	
	private String name;
	
	private final ExecutorService executor;

	public SystemThreadPoolExecutor() {
		this("default",corePoolSize);
	}
	
	public SystemThreadPoolExecutor(String name,int corePoolSize) {
		super();
		this.name=name;
		executor=Executors.newFixedThreadPool(corePoolSize, new SystemThreadFactory(this.name));
	}
	
	public <T> Future<T> submit(Callable<T> task){
		return this.executor.submit(task);
	}

	public <T> Future<T> submit(Runnable task, T result){
    	return this.executor.submit(task, result);
    }

	public Future<?> submit(Runnable task){
    	return this.executor.submit(task);
    }
	
	public static class SystemThreadFactory implements ThreadFactory {

		private static final AtomicInteger poolNumber = new AtomicInteger(1);
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		public  SystemThreadFactory(String name) {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			namePrefix = name+"-" + poolNumber.getAndIncrement() + "-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon())
				t.setDaemon(false);
			if (t.getPriority() != Thread.NORM_PRIORITY)
				t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}

	}

}
