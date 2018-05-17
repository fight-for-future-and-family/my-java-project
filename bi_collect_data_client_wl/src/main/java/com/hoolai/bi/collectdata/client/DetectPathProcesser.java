package com.hoolai.bi.collectdata.client;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.hoolai.bi.collectdata.client.core.Constant;
import com.hoolai.bi.compress.Compress;
import com.hoolai.bi.report.job.SystemThreadPoolExecutor;

public class DetectPathProcesser {
	
	private static final Logger LOGGER=Logger.getLogger(DetectPathProcesser.class.getSimpleName());
	
	private AtomicInteger detectTimes=new AtomicInteger(0);
	
	public void detect() {
		
		try {
			
			String executorName="collect-data-client-DetectPathProcesser-"+detectTimes.incrementAndGet();
			
			SystemThreadPoolExecutor systemThreadPoolExecutor=new SystemThreadPoolExecutor(executorName, 1);
			
			LOGGER.info("executorName:"+executorName+" is created!");
			
			class AsyncDetectMatchFilesThread implements Runnable{
				@Override
				public void run() {
					doDetect();
				}
			}
			
			systemThreadPoolExecutor.submit(new AsyncDetectMatchFilesThread());
			systemThreadPoolExecutor.getExecutor().shutdown();
			systemThreadPoolExecutor.getExecutor().awaitTermination(Constant.MAX_DETECT_FILES_MINUTES, TimeUnit.MINUTES);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		
	}
	
	private void doDetect(){
		try {
			Iterator<File> iterator=this.matchFiles();
			while(iterator.hasNext()){
				File file=iterator.next();
				this.processFile(file);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}finally{
			LOGGER.info("doDetect finished!");
		}
	}
	
	private void processFile(File file){
		try {
			long begin=System.currentTimeMillis();
			File compressFile=this.compressFile(file);
			SendCategoryDatasProcesser sendCategoryDatasProcesser=new SendCategoryDatasProcesser(compressFile);
			boolean isSucc=sendCategoryDatasProcesser.send();
			if(isSucc){
				this.processSendSuccFile(file);
				this.deleteCompressFile(compressFile);
			}else{
				this.deleteCompressFile(compressFile);
				LOGGER.info("send file fail! fileName:"+file.getAbsolutePath());
			}
			long end=System.currentTimeMillis();
			LOGGER.info("send file:"+file.getAbsolutePath()+" finished spendMills:"+(end-begin));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	//配置中要删除文件，并且send Install 不运行 才真正删除文件
	private void processSendSuccFile(File file){
		if(Constant.IS_DELETE_SOURCE_FILE && !Constant.IS_RUN_INSTALL_SEND){
			this.deleteSendSuccFile(file);
		}else{
			this.renameSendSuccFile(file);
		}
	}
	
	private void deleteSendSuccFile(File file){
		try {
			file.delete();
			LOGGER.info("delete source file :"+file.getAbsolutePath());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void renameSendSuccFile(File file){
		try {
			if(file==null){
				return ;
			}
			String currPath=file.getPath();
			File dest = new File(currPath + ".COMPLATED");
			boolean renamed = file.renameTo(dest);
			if (renamed) {
				LOGGER.debug("Successfully rolled file "+file.getPath()+" to "+dest.getPath());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void deleteCompressFile(File compressFile){
		try {
			compressFile.delete();
			LOGGER.info("delete compress file :"+compressFile.getAbsolutePath());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private File compressFile(File file){
		try {
			long begin=System.currentTimeMillis();
			Compress compress=Constant.compressType.instanceToClass();
			File targetFile=new File(file.getAbsolutePath()+"."+Constant.compressType.getSubfix());;
			compress.compress(file, targetFile);
			long end=System.currentTimeMillis();
			LOGGER.info("compress file :"+file.getAbsolutePath()+" finished! spendMills:"+(end-begin));
			return targetFile;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	private Iterator<File> matchFiles() {
		List<File> candidateFiles = Collections.emptyList();

		FileFilter filter = new FileFilter() {
			public boolean accept(File candidate) {
				String fileName = candidate.getName();
				if (fileName.endsWith(Constant.SEND_MATCH_FILE_SUBFIX)) {
					return true;
				}
				return false;
			}
		};
		File spoolDirectory = new File(Constant.DETECT_PATH);
		candidateFiles = Arrays.asList(spoolDirectory.listFiles(filter));
		Collections.sort(candidateFiles, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				long compare = o1.lastModified() - o2.lastModified();
				if (compare == 0) {
					long cl = o1.length() - o2.length();
					if (cl > 0) {
						return 1;
					} else if (cl < 0) {
						return -1;
					}
				} else if (compare > 0) {
					return 1;
				} else {
					return -1;
				}
				return 0;
			}
		});
		return candidateFiles.iterator();
	}

}
