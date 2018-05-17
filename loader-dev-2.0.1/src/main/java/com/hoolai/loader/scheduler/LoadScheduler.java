package com.hoolai.loader.scheduler;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Test;

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
	
	
	@Test
	public	void test(){
        // 创建一个可重用固定线程数的线程池  
        ExecutorService pool = Executors.newFixedThreadPool(3);  
        ExecutorService pool2 = Executors.newFixedThreadPool(1);  
        // 创建线程  
        Thread t1 = new MyThread();  
        Thread t2 = new MyThread();  
        Thread t3 = new MyThread();  
        Thread t4 = new MyThread();  
        Thread t5 = new MyThread();  
        Thread t6 = new MyThread();  
        // 将线程放入池中进行执行  
        for (int i = 1; i < 5; i++) {
			
        	pool.execute(t1);  
        	pool.execute(t2);  
        	pool.execute(t3);  
        	pool.execute(t4);  
        	pool.execute(t5);  
        	pool.execute(t6);  
        	
        	System.out.println("the %d is completed !"+i);
        	System.out.println("\n");
		}
        // 关闭线程池  
        pool.shutdown();  
	}
}
	class MyThread extends Thread {  
	    @Override  
	    public void run() {  
	        System.out.println(Thread.currentThread().getName() + "正在执行。。。"+this.getName());  
	    }  
	}
