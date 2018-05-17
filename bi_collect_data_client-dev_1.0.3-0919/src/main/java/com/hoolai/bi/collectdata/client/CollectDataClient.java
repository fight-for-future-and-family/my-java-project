package com.hoolai.bi.collectdata.client;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.hoolai.bi.collectdata.client.core.Constant;
import com.hoolai.bi.collectdata.client.thread.ScheduledSystemThreadFactory;

public class CollectDataClient {
	
	private final ScheduledThreadPoolExecutor threadPoolExecutor;
	
	private final DetectPathProcesser detectPathProcesser;
	
	public CollectDataClient() {
		super();
		threadPoolExecutor=new ScheduledThreadPoolExecutor(1,new  ScheduledSystemThreadFactory("collect-data-client"), new  ScheduledSystemRejectedExecutionHandler());
		this.detectPathProcesser=new DetectPathProcesser();
	}

	public void start(){
		this.threadPoolExecutor.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try {
					detectPathProcesser.detect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1, 60, TimeUnit.SECONDS);
	}

	public static class  ScheduledSystemRejectedExecutionHandler implements RejectedExecutionHandler {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			if (!executor.isShutdown()) {
				executor.getQueue().poll();
			}
		}
	}

	public static void main(String[] args) {
		if(args.length<1){
			System.out.println("please input config path !");
			return ;
		}
		String constantFilePathIn=args[0];
		Constant.init(constantFilePathIn);
		CollectDataClient collectDataClient=new CollectDataClient();
		collectDataClient.start();
		
		if(Constant.IS_RUN_INSTALL_SEND){
			InstallToAdTrackingClient installToAdTrackingClient = new InstallToAdTrackingClient();
			installToAdTrackingClient.start();
		}
	}

}
