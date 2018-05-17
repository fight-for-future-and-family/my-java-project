package com.hoolai.loader.util;

import org.apache.log4j.Logger;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * 根据配置文件中的 host port 创建memcache 的连接池
 *
 */
public class MemcacheUtil {
	
	private static Logger log = Logger.getLogger(MemcacheUtil.class);

	private String host = Constant.memcacheHost;
	private String port = Constant.memcachePort;
	
	private static MemcacheUtil instance = new MemcacheUtil();
	
	private MemcacheUtil () {
		String[] serverlist = { host + ":" + port };
		
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(serverlist);
		pool.initialize();
		
		log.info("connect memcache server : " + host + ":" + port);
	}
	
	public static MemcacheUtil getInstance(){
		return instance;
	}
	
	public boolean exist(String path){
		MemCachedClient mc = new MemCachedClient();
		
		boolean result = false;
		try {
			if(ValidateUtil.isValid(mc.get(path))){
				result = true;
				log.info("the path [ " + path + " ] is exist in memcache!");
			}
		} catch (Exception e){
			log.warn(e);
		}
		return result;
	}
	
	/**
	 * 将 path 作为key 保存到memcache当中，并设置超期时间为3天
	 */
	public boolean set(String path){
		MemCachedClient mc = new MemCachedClient();
		log.info("the path [ " + path + " ] is not exist in memcache, have been added!");
		return mc.set(path, 0, DateUtil.getAfterDate(3));
	}
}
