package com.hoolai.panel.web.processor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;

import com.hoolai.bi.report.core.Constant;

public class PrestoJdbcTemplate extends JdbcTemplate {

	protected Connection connection;

	protected Statement statement;

	protected boolean isDestory = false;

	public PrestoJdbcTemplate() {

		Properties properties = Constant.BI_JDBC_CONFIGS;
		String driveName = properties.getProperty("prestoConnectionDriver", "com.facebook.presto.jdbc.PrestoDriver");
		String hiveUrl = properties.getProperty("prestoConnectionURL");
		String username = properties.getProperty("prestoConnectionUser");
		String password = properties.getProperty("prestoConnectionPassword");
		
		try {
			Class.forName(driveName);
			connection = DriverManager.getConnection(hiveUrl, username, password);
			statement = connection.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ResultSet exec(String hql) throws Exception {
		return this.statement.executeQuery(hql);
	}

	public void destory() {
		if (isDestory) {
			return;
		}
		try {
			if (this.statement != null && !this.statement.isClosed()) {
				JdbcUtils.closeStatement(this.statement);
			}
			if (this.connection != null && !this.connection.isClosed()) {
				JdbcUtils.closeConnection(this.connection);
			}
			isDestory = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.destory();
	}

	public boolean isDestory() {
		return isDestory;
	}

}
