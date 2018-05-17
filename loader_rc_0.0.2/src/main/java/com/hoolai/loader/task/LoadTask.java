package com.hoolai.loader.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
 */
public class LoadTask implements Runnable {
	
	private static Logger log = Logger.getLogger(LoadTask.class);

	private String filePath;
	
	/**
	 * 传入的是   文件路径 字符串
	 */
	public LoadTask(String filePath){
		this.filePath = filePath;
	}
	
	/* 多线程的方法
	 * 根据创建对象时--指定的 文件路径，获取它的父目录，然后判断父目录  的版本
	 */
	@Override
	public void run() {
		String hdfsLzoFilePath = null;
		try {
			FileSystem hdfs = FileSystem.get(new Configuration());
			
			File localFile = new File(filePath);
			
			String path = localFile.getParentFile().getName();
			
			if(StringUtil.isRevision(path)){
				// 根据 父目录 获取 指标名称    snid  gameid  ds  
				String param[] = StringUtil.getParams(path, true);
				//  这里 根据  父目录 拼写出了  要上传到 集群中的 文件的名字
				hdfsLzoFilePath = "/user/hive/warehouse/nt_" + param[0] +"/snid=" + param[1] + "/gameid=" + param[2] + "/ds=" + param[3] + "/" + localFile.getName() + Constant.lzoSuffix;
			} else {
				// 如果是 之前的版本，那么就 上传到  /user/hive/warehouse/rawdata   集群的这个路径当中
				hdfsLzoFilePath = filePath.replace(Constant.localRootDir, Constant.hdfsRootDir) + Constant.lzoSuffix;
			}
			
			Path hdfsLzoFile = new Path(hdfsLzoFilePath);
			
			Class<?> codecClass = Class.forName(Constant.lzoClass);
			CompressionCodec codec = (CompressionCodec)ReflectionUtils.newInstance(codecClass, new Configuration());
			
			//获得hdfs的文件输出流
			FSDataOutputStream os = hdfs.create(hdfsLzoFile, true);
			CompressionOutputStream out = codec.createOutputStream(os);
			
			//获得本地文件的文件输入流
			FileInputStream fis = new FileInputStream(localFile);
			
			// 提前记录，预防在传输过程中，原始文件被改变，造成文件大小不一直的情况
			long localFileLength=localFile.length();
			
			//将本地文件拷贝至hdfs并用lzo压缩算法压缩
			IOUtils.copyBytes(fis, out, 4096, true);
			
			// 上传完后，再  检查一遍  集群中是否存在这个文件   且  这个文件的大小大于42
			if(hdfs.exists(hdfsLzoFile) && hdfs.getFileStatus(hdfsLzoFile).getLen() > 42){
				log.info("load local file SUCCESS from: [" + filePath + "] to: [" + hdfsLzoFilePath + "]");
				
				//将本地文件大小写入 到 与文件名对应的meta文件中
				File metafile = new File(localFile.getParent() + File.separator + "." + localFile.getName() + "_META");
				fileWrite(metafile, localFileLength + "");
				
				//  根据父目录判断  在memcache中是否存在，  如果不存在，那么就添加到 memcache中，并添加分区信息
				if(!MemcacheUtil.getInstance().exist(path)){
					PartitionDao.newInstance().addPartition(path);
					RelationDao.newInstance().addRelation(path);
					MemcacheUtil.getInstance().set(path);
				}
			}else{//文件拷贝结束但文件在hdfs上不存在
				log.warn("load local file FAILD from: [" + filePath + "] to: [" + hdfsLzoFilePath + "]");
			}
			
		} catch (IOException e) {
			log.error("load local file FAILD from: [" + filePath + "] to: [" + hdfsLzoFilePath + "]", e);
			return ;
		}catch(Exception e){
			log.error("load local file FAILD from: [" + filePath + "] to: [" + hdfsLzoFilePath + "]", e);
			return ;
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
