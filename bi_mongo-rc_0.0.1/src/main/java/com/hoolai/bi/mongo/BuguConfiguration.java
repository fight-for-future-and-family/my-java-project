package com.hoolai.bi.mongo;

import java.util.List;

/**
 * mongo的配置信息 <br>
 * 当List不为空时，直接取List中的配置列表
 * 
 * @date 2017年8月14日 下午6:22:19
 * @author 邹友
 *
 */
public class BuguConfiguration {
	private String host;//ip
	private Integer port;//端口
	private String database;//数据库
	private String username;//用户名 可为空
	private String password;//密码 可为空
	
	private List<BuguConfiguration> configurations; //多个链接配置

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<BuguConfiguration> getConfigurations() {
		return configurations;
	}

	public void setConfigrations(List<BuguConfiguration> configurations) {
		this.configurations = configurations;
	}
	
}
