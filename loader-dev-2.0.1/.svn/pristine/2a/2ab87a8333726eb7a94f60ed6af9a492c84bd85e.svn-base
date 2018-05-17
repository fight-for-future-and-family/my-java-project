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
 * @author ruijie
 * @date 2013-9-13
 * @version V1.0
 */
public class LoadScheduler implements Scheduler {
	
	private static Logger log = Logger.getLogger(LoadScheduler.class);
	
	/**
	 * 上传本地文件到hdfs
	 */
	public void execute() {
		
		for(String metric : Constant.metrics){
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
