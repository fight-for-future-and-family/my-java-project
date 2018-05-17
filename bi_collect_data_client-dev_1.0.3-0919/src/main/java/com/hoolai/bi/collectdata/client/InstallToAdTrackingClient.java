package com.hoolai.bi.collectdata.client;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hoolai.bi.collectdata.client.CollectDataClient.ScheduledSystemRejectedExecutionHandler;
import com.hoolai.bi.collectdata.client.thread.ScheduledSystemThreadFactory;

/** 
 * <b>类说明：</b>
 * <blockquote>
 *   
 * </blockquote> 
 * @author jiangqm E-mail: jiangqiming@jiangqiming.cn
 * @version 创建时间：2015年12月10日 下午6:11:13  
 */

public class InstallToAdTrackingClient {
	private static Log log = LogFactory.getLog(InstallToAdTrackingClient.class);
	
	private final ScheduledThreadPoolExecutor threadPoolExecutor;
	
	private final AnalyInstallFileProcesser analyInstallFileProcesser;
	
	private final AdTrackingCallbackManager adTrackingCallbackManager;
	
	public InstallToAdTrackingClient(){
		threadPoolExecutor=new ScheduledThreadPoolExecutor(1,new  ScheduledSystemThreadFactory("install-to-adtracking-client"), new  ScheduledSystemRejectedExecutionHandler());
		adTrackingCallbackManager = new AdTrackingCallbackManager();
		analyInstallFileProcesser = new AnalyInstallFileProcesser(adTrackingCallbackManager);
	}
	
	
	public void start(){
		this.threadPoolExecutor.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try {
					analyInstallFileProcesser.detect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1, 60, TimeUnit.SECONDS);
	}
	
}
