package com.hoolai.loader.scheduler;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.hoolai.loader.scan.LoadScan;
import com.hoolai.loader.task.UniqLoadTask;
import com.hoolai.loader.task.UniqTask;
import com.hoolai.loader.util.Constant;
import com.hoolai.loader.util.ValidateUtil;

/**
 * 方法调度类
 * @author ruijie
 * @date 2013-9-13
 * @version V1.0
 */
public class UniqLoadScheduler implements Scheduler {
	
	private static Logger log = Logger.getLogger(UniqLoadScheduler.class);
	
	/**
	 * 上传本地文件到hdfs
	 */
	public void execute() {
		
		for(String metric : Constant.metrics){
			List<String> fileNames = LoadScan.execute(Constant.localRootDir + File.separator + metric, true);
			
			log.info("scan metric : " + metric + " finish");
			
			if (!ValidateUtil.isValid(fileNames)) {
				log.info("no more new file in " + metric + " or " + metric + " is not exist!");
				continue ;
			}
			
			// 添加文件去重功能
			ExecutorService uniqExec = Executors.newFixedThreadPool(Constant.threadNum);
			
			for(String fileName : fileNames){
				uniqExec.execute(new UniqTask(fileName));
			}
			uniqExec.shutdown();
			try {
				uniqExec.awaitTermination(Constant.timeout, TimeUnit.HOURS);
			} catch (InterruptedException e) {
				log.error(e);
				continue ;
			}
			// 添加文件去重功能
			
			ExecutorService exec = Executors.newFixedThreadPool(Constant.threadNum);
			
			for(String fileName : fileNames){
				exec.execute(new UniqLoadTask(fileName + ".uniq"));
			}
			exec.shutdown();
			try {
				exec.awaitTermination(Constant.timeout, TimeUnit.HOURS);
			} catch (InterruptedException e) {
				log.error(e);
				continue ;
			}
		}
	}
}
