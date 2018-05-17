package com.hoolai.loader.scheduler;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.hoolai.loader.scan.LoadScan;
import com.hoolai.loader.task.LoadTask;
import com.hoolai.loader.util.Constant;
import com.hoolai.loader.util.ValidateUtil;

/**
 * 方法调度类
 * 这个类 实现了   接口  Scheduler  中仅有的一个   execute  方法
 */
public class LoadScheduler implements Scheduler {
	
	private static Logger log = Logger.getLogger(LoadScheduler.class);
	
	/**
	 * 上传本地文件到hdfs
	 * 
	 * 读取 properties 文件中 指定的   metrics  数组
	 * 一个指标是  32个线程  
	 */
	public void execute() {
		
		for(String metric : Constant.metrics){
			
			//  通过多线程的 方式  扫描   /data/bi_collect/loader_new_path/install   dau  payment/   中需要 操作的  文件的列表
			List<String> fileNames = LoadScan.execute(Constant.localRootDir + File.separator + metric, false);
			
			log.info("scan metric : " + metric + " finish");
			
			if (ValidateUtil.isValid(fileNames)) {
				
				ExecutorService exec = Executors.newFixedThreadPool(Constant.threadNum);
				
				for(String fileName : fileNames){
					exec.execute(new LoadTask(fileName));
				}
				
				exec.shutdown();
				
				try {
					exec.awaitTermination(Constant.timeout, TimeUnit.HOURS);
				} catch (InterruptedException e) {
					log.error(e);
					continue ;
				}
			} else {
				log.info("no more new file in " + metric + " or " + metric + " is not exist!");
			}
		}
	}
}
