package com.hoolai.loader.util;

import org.apache.log4j.Logger;


/**
 * 配置文件常量工具类
 * @author ruijie
 * @date 2013-9-25
 * @version V1.0
 */
public class Constant {
	
	private static Logger log = Logger.getLogger(Constant.class);

	/**
	 * 本地根目录
	 */
	public final static String localRootDir = Config.newInstance().getString("localRootDir");
	
	/**
	 * metric目录列表
	 */
	public final static String[] metrics = Config.newInstance().getStringArray("metrics");
	
	/**
	 * hdfs根目录
	 */
	public final static String hdfsRootDir = Config.newInstance().getString("hdfsRootDir");
	
	/**
	 * 线程数
	 */
	public final static int threadNum = getThreadNum();
	
	/**
	 * 超时时间（单位：小时）
	 */
	public final static int timeout = getTimeout();
	
	/**
	 * 从今天起往前加载数据的天数
	 */
	public final static int loadForwardDays = getLoadForwardDays();
	
	/**
	 * 删除备份本地几天前的数据
	 */
	public final static int delForwardDays = getDelForwardDays();
	
	/**
	 * lzo文件后缀
	 */
	public final static String lzoSuffix = Config.newInstance().getString("lzoSuffix");
	
	/**
	 * lzo压缩算法类
	 */
	public final static String lzoClass = Config.newInstance().getString("lzoClass");
	
	/**
	 * 检查当前运行loader个数命令
	 */
	public final static String checkNumCmd = Config.newInstance().getString("checkNumCmd");
	
	/**
	 * mysql相关配置
	 */
	public final static String driverClass = Config.newInstance().getString("jdbc.driverClass");
	
	public final static String url = Config.newInstance().getString("jdbc.url");
	
	public final static String username = Config.newInstance().getString("jdbc.username");
	
	public final static String password = Config.newInstance().getString("jdbc.password");
	
	/**
	 * memcache配置
	 */
	public final static String memcacheHost = Config.newInstance().getString("memcache.host");
	
	public final static String memcachePort = Config.newInstance().getString("memcache.port");
	
	/**
	 * 获取线程数
	 * @return
	 */
	private static int getThreadNum() {
		int result = 32;
		
		try{
			result = Integer.parseInt(Config.newInstance().getString("threadNum"));
		}catch (NumberFormatException e){
			log.error("threadNum config error, use default value 32", e);
		}catch (Exception e){
			log.error(e);
		}
		
		return result;
	}
	
	/**
	 * 获取超时时间
	 * @return
	 */
	private static int getTimeout() {
		
		int result = 1;
		
		try{
			result = Integer.parseInt(Config.newInstance().getString("timeout"));
		}catch (NumberFormatException e){
			log.error("timeout config error, use default value 1", e);
		}catch (Exception e){
			log.error(e);
		}
		
		return result;
	}
	
	/**
	 * 获取从今天起往前加载数据的天数
	 * @return
	 */
	private static int getLoadForwardDays() {
		
		int result = 3;
		
		try{
			result = Integer.parseInt(Config.newInstance().getString("loadForwardDays"));
		}catch (NumberFormatException e){
			log.error("loadForwardDays config error, use default value 3", e);
		}catch (Exception e){
			log.error(e);
		}
		
		return result;
	}

	/**
	 * 获取删除备份本地几天前数据的天数
	 * @return
	 */
	private static int getDelForwardDays() {
		
		int result = 7;
		
		try{
			result = Integer.parseInt(Config.newInstance().getString("delForwardDays"));
		}catch (NumberFormatException e){
			log.error("delForwardDays config error, use default value 7", e);
		}catch (Exception e){
			log.error(e);
		}
		
		return result;
	}
	
	/**
	 * 验证配置的有效性
	 * @return
	 */
	public static boolean validate(){
		boolean result = false;
		
		if(ValidateUtil.isValid(localRootDir) && ValidateUtil.isValid(metrics) && ValidateUtil.isValid(hdfsRootDir)
				&& ValidateUtil.isValid(lzoSuffix) && ValidateUtil.isValid(lzoClass) && ValidateUtil.isValid(checkNumCmd)
				&& ValidateUtil.isValid(driverClass) && ValidateUtil.isValid(url) && ValidateUtil.isValid(username)
				&& ValidateUtil.isValid(password) && ValidateUtil.isValid(memcacheHost) && ValidateUtil.isValid(memcachePort)){
			result = true;
		}
		
		return result;
	}
	
}
