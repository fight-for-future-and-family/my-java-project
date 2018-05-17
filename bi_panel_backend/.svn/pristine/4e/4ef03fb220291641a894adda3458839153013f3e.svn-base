package com.hoolai.bi.report.core;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hoolai.bi.mongo.BuguConfiguration;
import com.hoolai.bi.mongo.BuguConnection;
import com.hoolai.bi.mongo.BuguFramework;

/**
 * mongo工厂类，用于创造链接
 * 
 * @date 2017年8月14日 下午6:28:23
 * @author 邹友
 *
 */
//@Component
public class MongoFactory {

	private static Logger logger = Logger.getLogger(MongoFactory.class);
	
	@Autowired
	BuguConfiguration buguConfigration;
	
//	@PostConstruct
	public void initConnections() {
		logger.info(">>> begin init mongo db connection...");
		List<BuguConfiguration> configurations = buguConfigration.getConfigurations();
		if (CollectionUtils.isEmpty(configurations)) {
			// 初始化一个链接
			initConnection(buguConfigration);
		} else {
			// 初始化N个链接
			initConnection(configurations);
		}
	}

	/**
	 * 初始化单独的一个链接信息
	 * @date 2017年8月14日 下午6:38:57
	 * @author 邹友
	 * @param configuration
	 */
	public static void initConnection(BuguConfiguration configuration) {
		BuguConnection connection = BuguFramework.getInstance().createConnection();
		connection.setHost(configuration.getHost()).setPort(configuration.getPort())
				.setDatabase(configuration.getDatabase());
		connection.connect();
		logger.info(">>> mongo db connection init success...");
	}

	/**
	 * 初始化多个副本信息
	 * @date 2017年8月14日 下午6:39:10
	 * @author 邹友
	 * @param configurations
	 */
	public static void initConnection(List<BuguConfiguration> configurations) {

	}

}
