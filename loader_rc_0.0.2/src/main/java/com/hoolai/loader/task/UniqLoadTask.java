package com.hoolai.loader.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.log4j.Logger;

import com.hoolai.loader.dao.PartitionDao;
import com.hoolai.loader.dao.RelationDao;
import com.hoolai.loader.util.Constant;
import com.hoolai.loader.util.MemcacheUtil;
import com.hoolai.loader.util.StringUtil;

/**
 * 上传文件到hdfs任务
 * @author ruijie
 * @date 2013-9-13
 * @version V1.0
 */
public class UniqLoadTask implements Runnable {
	
	private static Logger log = Logger.getLogger(UniqLoadTask.class);

	private String filePath;
	
	/**
	 * 创建对象时，指定  文件的路径
	 */
	public UniqLoadTask(String filePath){
		this.filePath = filePath;
	}
	
	/* 这里是  多线程的 run  方法
	 * 
	 */
	@Override
	public void run() {
		String hdfsLzoFilePath = null;
		try {
			FileSystem hdfs = FileSystem.get(new Configuration());
			
			File localFile = new File(filePath);
			//  首先判断   传入的 文件是否存在
			if (localFile.exists()) { 
				
				String path = localFile.getParentFile().getName();
				
				if(StringUtil.isRevision(path)){
					String param[] = StringUtil.getParams(path, true);
					
					hdfsLzoFilePath = "/user/hive/warehouse/nt_" + param[0] +"/snid=" + param[1] + "/gameid=" + param[2] + "/ds=" + param[3] + "/" + StringUtil.getFileNameNoEx(localFile.getName()) + Constant.lzoSuffix;
				} else {
					String tmpHdfs = filePath.replace(Constant.localRootDir, Constant.hdfsRootDir);
					
					hdfsLzoFilePath = tmpHdfs.substring(0, tmpHdfs.indexOf(".uniq")) + Constant.lzoSuffix;
				}
				
				Path hdfsLzoFile = new Path(hdfsLzoFilePath);
				
				Class<?> codecClass = Class.forName(Constant.lzoClass);
				CompressionCodec codec = (CompressionCodec)ReflectionUtils.newInstance(codecClass, new Configuration());
				
				// 获得hdfs的文件输出流
				FSDataOutputStream os = hdfs.create(hdfsLzoFile, true);
				CompressionOutputStream out = codec.createOutputStream(os);
				
				// 获得本地文件的文件输入流
				FileInputStream fis = new FileInputStream(localFile);
				
				// 将本地文件拷贝至hdfs并用lzo压缩算法压缩
				IOUtils.copyBytes(fis, out, 4096, true);
				
				if (hdfs.exists(hdfsLzoFile) && hdfs.getFileStatus(hdfsLzoFile).getLen() > 42) {
					log.info("load local file SUCCESS from: [" + filePath + "] to: [" + hdfsLzoFilePath + "]");
					
					if(!MemcacheUtil.getInstance().exist(path)){
						PartitionDao.newInstance().addPartition(path);
						RelationDao.newInstance().addRelation(path);
						MemcacheUtil.getInstance().set(path);
					}
				} else {// 文件拷贝结束但文件在hdfs上不存在或大小小于42字节，删除meta文件
					File localRawFile = new File(filePath.substring(0, filePath.indexOf(".uniq")));
					
					File metafile = new File(localRawFile.getParent() + File.separator + "." + localRawFile.getName() + "_META");
					if (metafile.exists()) {
						metafile.delete();
					}
					log.warn("load local file FAILD from: [" + filePath + "] to: [" + hdfsLzoFilePath + "]");
				}
			} else {// 去重后的文件不存在，删除原文件的meta文件
				File localRawFile = new File(filePath.substring(0, filePath.indexOf(".uniq")));
				
				File metafile = new File(localRawFile.getParent() + File.separator + "." + localRawFile.getName() + "_META");
				if (metafile.exists()) {
					metafile.delete();
				}
				log.warn("Uniq file not found, delete meta file!");
			}
			
		} catch (IOException e) {
			log.error("load local file FAILD from: [" + filePath + "] to: [" + hdfsLzoFilePath + "]", e);
			return ;
		}catch(Exception e){
			log.error("load local file FAILD from: [" + filePath + "] to: [" + hdfsLzoFilePath + "]", e);
			return ;
		}
	}
}
