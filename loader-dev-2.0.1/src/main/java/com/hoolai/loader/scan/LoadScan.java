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
 * @author ruijie
 * @date 2013-9-13
 * @version V1.0
 */
public class LoadScan {
	
	private static Logger log = Logger.getLogger(LoadScan.class);
	
	private static final Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
	
	
	/**
	 * 遍历本地目录文件并返回待上传文件列表
	 * @param dir 本地目录/data/scribe/${metric}
	 * @return  根据 loadForwardDays=3 指定的日期，进行路径的遍历
	 */
	public static List<String> execute(String dir, boolean isUniq){
		List<String> result = new Vector<String>(2000);
		
		File root = new File(dir);  //dir=/data/bi_collect_web/loader_new_path/counter/
		if(!root.exists()){
			return null;
		}
		
		File[] folders = root.listFiles(); //folders=/data/bi_collect_web/loader_new_path/counter/counter_200_213_2017-08-14
		
		Matcher matcher = null;
		
		ExecutorService exec = Executors.newFixedThreadPool(Constant.threadNum);
		
		for(File folder : folders){// folder=/data/bi_collect_web/loader_new_path/counter/counter_200_213_2017-08-14
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
