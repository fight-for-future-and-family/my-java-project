package com.hoolai.loader.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hoolai.loader.exception.PersistenceException;

/**
 * 数据库工具类
 * @author ruijie
 * @date 2013-12-3
 * @version 1.0
 */
public class DBUtil {
	
	private static DBUtil dbUtil;
	
	private static String driverClass = Constant.driverClass;
	
	private static String url = Constant.url;
	
	private static String username = Constant.username;
	
	private static String password = Constant.password;
	
	private DBUtil() {
		
	}
	
	/**
	 *返回单实例对象
	 */
	public static DBUtil newInstance() {
		if (dbUtil == null) {
			dbUtil = new DBUtil();
		}
		return dbUtil;
	}
	
	/**
	 * 返回数据库连接对象
	 */
	public Connection getConn(){
		Connection conn = null;
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			throw new PersistenceException(e);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return conn;
	}
	
	/**
	 * @param conn
	 * @param sql
	 * @return
	 */
	public PreparedStatement getStatement(Connection conn, String sql){
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		return pstmt;
	}
	
	/**
	 * @param rs
	 * @param pstmt
	 * @param conn
	 * 关闭 结果集  执行语句  和 连接对象
	 */
	public void release(ResultSet rs, PreparedStatement pstmt, Connection conn){
		try {
			if (rs != null){
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			try {
				if (pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e);
			} finally {
				try{
					if (conn != null){
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					throw new PersistenceException(e);
				}
			}
		}
	}
	
	public void release(ResultSet rs, Statement stmt, Connection conn){
		try {
			if (rs != null){
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			try {
				if (stmt != null){
					stmt.close();
					stmt = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e);
			} finally {
				try{
					if (conn != null){
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					throw new PersistenceException(e);
				}
			}
		}
	}
}
