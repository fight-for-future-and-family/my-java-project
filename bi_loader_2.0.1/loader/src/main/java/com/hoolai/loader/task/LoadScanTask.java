package com.hoolai.loader.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.apache.log4j.Logger;

public class LoadScanTask implements Runnable {

	private static Logger logger = Logger.getLogger(LoadScanTask.class);
	
	private List<String> strs = null;
	private String folderStr = null;
	private boolean isUniq = false;
	
	public LoadScanTask(List<String> strs, String folderStr, boolean isUniq){
		this.strs = strs;
		this.folderStr = folderStr;
		this.isUniq = isUniq;
	}
	
	@Override
	public void run() {
		logger.info("current dir : " + folderStr);
		
		File folder = new File(folderStr);
		File[] files = folder.listFiles();
		
		for(File file : files){
			// 是文件且不能为meta文件，若此任务是去重任务则不能为"uniq"后缀
			if (file.isFile() && file.getName().indexOf("META") < 0 && (isUniq ? file.getName().indexOf("uniq") < 0 : true)) {
				
				File metafile = new File(file.getParent() + File.separator + "." + file.getName() + "_META");
				
				if (metafile.exists()) {// 元数据文件存在，读取内容并比较大小
					BufferedReader reader = null;
					try {
						reader = new BufferedReader(new FileReader(metafile));
						if (Long.parseLong(reader.readLine()) != file.length()) {
							strs.add(file.getAbsolutePath());
						}
						reader.close();
					} catch (Exception e) {
						logger.error(e);
						strs.add(file.getAbsolutePath());
					}
				} else {// 元数据文件不存在，将此文件添加至hdfs拷贝列表
					strs.add(file.getAbsolutePath());
				}
			}
		}
	}
}
