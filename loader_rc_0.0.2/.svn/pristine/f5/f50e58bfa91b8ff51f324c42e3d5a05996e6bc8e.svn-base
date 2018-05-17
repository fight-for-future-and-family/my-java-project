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
	
	public static DBUtil newInstance() {
		if (dbUtil == null) {
			dbUtil = new DBUtil();
		}
		return dbUtil;
	}
	
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
	
	public PreparedStatement getStatement(Connection conn, String sql){
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		return pstmt;
	}
	
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
