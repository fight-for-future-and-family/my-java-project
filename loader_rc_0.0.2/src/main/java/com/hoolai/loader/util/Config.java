package com.hoolai.loader.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.junit.Test;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.StreamGobbler;

/**
 * conf.properties配置文件读取类
 *在类加载的过程中，读取配置文件
 *创建单实例对象
 */
public class Config {
	
	private static Logger log = Logger.getLogger(Config.class);
	
	private static Configuration configuration = null;
	
	private static Config config = null;

	static{
		try {
			configuration = new PropertiesConfiguration("./conf/conf.properties");
		} catch (ConfigurationException e) {
			log.error("file not found:conf.properties", e);
		}
	}
	
	private Config(){
		
	}
	
	public static Config newInstance(){
		if(null == config){
			config = new Config();
		}
		return config;
	}
	
	/**
	 * 返回指定 key 的  value数组
	 */
	public String[] getStringArray(String key){
		return configuration.getStringArray(key);
	}
	
	/**
	 * 查找指定key 的值，如果没有则用  空  进行代替
	 */
	public String getString(String key){
		return configuration.getString(key, "");
	}
	
	/**
	 * 一个  鸡肋的 方法
	 */
	public String getString(String key, String defaultValue){
		return "".equals(configuration.getString(key, defaultValue)) ? defaultValue : configuration.getString(key, defaultValue);
	}
	
}




