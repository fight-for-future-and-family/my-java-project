package com.hoolai.loader.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * 文件去重任务
 * @author ruijie
 * @date 2013-9-13
 * @version V1.0
 */
public class UniqTask implements Runnable {
	
	private static Logger log = Logger.getLogger(UniqTask.class);

	private String filePath;
	
	public UniqTask(String filePath){
		this.filePath = filePath;
	}
	
	@Override
	public void run() {
		try {
			
			File localFile = new File(filePath);
			
			File metafile = new File(localFile.getParent() + File.separator + "." + localFile.getName() + "_META");
			
			// 将本地文件大小写入meta文件中（提前写入文件大小，避免因为uniq造成数据不一致）
			fileWrite(metafile, localFile.length() + "");
			
			String cmd = "sort " + filePath + " | uniq > " + filePath + ".uniq";
			
			String[] cmds = new String[]{"/bin/bash", "-c", cmd};
			
			Runtime.getRuntime().exec(cmds).waitFor();
			
			log.info("uniq the file to [" + filePath + ".uniq]");
			
			
		} catch (IOException e){
			log.error(e);
		} catch (Exception e){
			log.error(e);
		}
		
	}
	
	/**
	 * 将内容写入文件中
	 * @param file
	 * @param str
	 */
	private static void fileWrite(File file, String str){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			log.error("local meta file write error", e);
		}
	}

}
