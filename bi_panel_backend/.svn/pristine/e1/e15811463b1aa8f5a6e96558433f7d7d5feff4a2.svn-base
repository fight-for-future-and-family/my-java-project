package com.hoolai.bi.report.job;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * 同步用户中心线程池 对外部不提供执行普通任务的方法
 * 
 * @author jiong
 * 
 */
public class ScheduledSystemThreadPoolExecutor {

	private static final Logger logger = Logger.getLogger(ScheduledSystemThreadPoolExecutor.class);

	// 初始大小
	private static final int corePoolSize = 10;
	
	private String name;
	
	private final ScheduledThreadPoolExecutor threadPoolExecutor;
	
	private final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingDeque<Runnable>(1000);

	public ScheduledSystemThreadPoolExecutor() {
		this("default",corePoolSize);
	}
	
	public ScheduledSystemThreadPoolExecutor(String name,int corePoolSize) {
		super();
		this.name=name;
		threadPoolExecutor=new ScheduledThreadPoolExecutor(corePoolSize,new  ScheduledSystemThreadFactory(this.name), new  ScheduledSystemRejectedExecutionHandler());
		this.startUp();
	}
	
	private void startUp(){
		Runnable timeTask=new Runnable() {
			@Override
			public void run() {
				try {
					if(blockingQueue.isEmpty()){
						return ;
					}
					Runnable runalbe=blockingQueue.take();
					threadPoolExecutor.execute(runalbe);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		this.threadPoolExecutor.scheduleAtFixedRate(timeTask, 500, 1000, TimeUnit.MILLISECONDS);
	}
	
	public void add(Runnable  runnable) {
		try {
			this.blockingQueue.add(runnable);
		} catch (Exception e) {
			throw new BlockingQueueException("add runnable meet error!",e);
		}
	}

	public static class  ScheduledSystemThreadFactory implements ThreadFactory {

		static final AtomicInteger poolNumber = new AtomicInteger(1);
		final ThreadGroup group;
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final String namePrefix;

		public  ScheduledSystemThreadFactory(String name) {
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

	public static class  ScheduledSystemRejectedExecutionHandler implements RejectedExecutionHandler {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			logger.error("ThreadPoolExecutor rejected runnable ");
			if (!executor.isShutdown()) {
				executor.getQueue().poll();
			}
		}
	}
	
	public static class BlockingQueueException extends RuntimeException {

		public BlockingQueueException() {
			super();
		}

		public BlockingQueueException(String message, Throwable cause) {
			super("BlockingQueueException "+message, cause);
		}

		public BlockingQueueException(String message) {
			super("BlockingQueueException "+message);
		}

		public BlockingQueueException(Throwable cause) {
			super(cause);
		}
		
	}

}
