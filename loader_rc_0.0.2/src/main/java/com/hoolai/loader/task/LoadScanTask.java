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
	
	/**
	 * 传入  字符串列表     目录字符串    是否唯一（booolean）
	 */
	public LoadScanTask(List<String> strs, String folderStr, boolean isUniq){
		this.strs = strs;
		this.folderStr = folderStr;
		this.isUniq = isUniq;
	}
	
	/* 这个方法是 多线程的  run  方法
	 * 根据传入的 目录字符串创建文件对象（file）
	 * 然后获取里面的  文件，得到一个 文件对象数组
	 * 
	 * 遍历这个 目录中的每个文件，如果不存在 以这个文件命名的隐藏文件（里面记录了对应文件的大小），或者是   文件大小和记录的大小不一致，那么就把这个文件加入到  字符串对象数组当中，
	 * 并返回这个  字符串对象数组
	 */
	@Override
	public void run() {
		logger.info("current dir : " + folderStr);
		
		File folder = new File(folderStr);
		File[] files = folder.listFiles();
		
		for(File file : files){
			// 是文件   且   文件名不包含meta  且 文件名不包含 tar.gz  的文件  且  传入的参数是 去重时（true），需要文件名中不能包含 uniq    如果传入的是 false时，那么就不检查 uniq 这个关键字了
			if (file.isFile() && file.getName().indexOf("META") < 0 &&  file.getName().indexOf("tar.gz") < 0 && (isUniq ? file.getName().indexOf("uniq") < 0 : true)) {
				
				File metafile = new File(file.getParent() + File.separator + "." + file.getName() + "_META");
				
				if (metafile.exists()) {// 元数据文件存在，读取内容并比较大小，此文件中存放的是这个文件的大小
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
