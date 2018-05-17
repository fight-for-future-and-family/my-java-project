package com.hoolai.loader.scan;

import java.io.File;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.hoolai.loader.task.LoadScanTask;
import com.hoolai.loader.util.Constant;
import com.hoolai.loader.util.DateUtil;
import com.hoolai.loader.util.ValidateUtil;

/**
 * 目录递归遍历工具类
 * 这个多线程 的  线程池 最多 32个 （默认的）
 * 	 * 遍历本地目录文件并返回待上传文件列表
	 * @param dir 本地目录/data/scribe/${metric}
	 * 
	 * 根据  properties 中 指定的  线程数目，创建线程池
	 * 遍历   调用这个静态方法时，指定的  路径， 如果指定的这个目录中包含的  子目录 中  的命名包含    2017-09-07  这种类型的正则
	 * 
	 * 
	 * 这里只是  创建了一个  容纳 2000 个对象的  list  对象
 */
public class LoadScan {
	
	private static Logger log = Logger.getLogger(LoadScan.class);
	
	private static final Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
	
	/**
	 * 遍历本地目录文件并返回待上传文件列表
	 * @param dir 本地目录/data/scribe/${metric}
	 * 
	 * 根据  properties 中 指定的  线程数目，创建线程池
	 * 遍历   调用这个静态方法时，指定的  路径， 如果指定的这个目录中包含的  子目录 中  的命名包含    2017-09-07  这种类型的正则
	 * 
	 * 
	 * 这里只是  创建了一个  容纳 2000 个对象的  list  对象
	 */
	public static List<String> execute(String dir, boolean isUniq){
		List<String> result = new Vector<String>(2000);
		
		File root = new File(dir);
		if(!root.exists()){
			return null;
		}
		
		File[] folders = root.listFiles();
		
		Matcher matcher = null;
		
		ExecutorService exec = Executors.newFixedThreadPool(Constant.threadNum);
		
		for(File folder : folders){
			if (folder.isDirectory() && (matcher = datePattern.matcher(folder.getName())).find()) {// 此路径为目录且包含日期
				
				if (ValidateUtil.isValid(DateUtil.processAloneDayStr)) {// 单独处理日期不为空
					if (folder.getName().indexOf(DateUtil.processAloneDayStr) > 0){
						exec.execute(new LoadScanTask(result, folder.getAbsolutePath(), isUniq));
					}
				} else {
					// 单独处理日期为空
					if (DateUtil.isInDateRange(matcher.group())) {
						// 此目录包含日期在指定时间范围内
						exec.execute(new LoadScanTask(result, folder.getAbsolutePath(), isUniq));
					}
				}
			}
		}
		
		exec.shutdown();
		
		try {
			exec.awaitTermination(Constant.timeout, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			log.error(e);
		}
		
		return result;
	}
}
