package com.hoolai.bi;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.hive.ql.exec.UDF;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class HooLaiBi extends  UDF{
	
	 public static ComboPooledDataSource comboPooledDataSource = null;
	 public static Connection con = null;
	 public static PreparedStatement crunchifyPrepareStat = null;
	 public static String result;
	 
	 
	static {
        // 初始化 c3p0 JDBC数据连接与控制池
         comboPooledDataSource = new ComboPooledDataSource();
        try {
        	log("Congrats - Seems your MySQL JDBC Driver Registered!----------开始注册");
            comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
        	log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly----找不到JDBC 的连接包");
            e.printStackTrace();
        }
        comboPooledDataSource
                .setJdbcUrl("jdbc:mysql://hlbi_db01:5599/ipresolve?useUnicode=true&amp;characterEncoding=UTF-8");
        comboPooledDataSource.setUser("root");
        comboPooledDataSource.setPassword("hoolaibi");
        comboPooledDataSource.setMinPoolSize(6);
        comboPooledDataSource.setMaxPoolSize(5000);
    }
 
	/**
	 * 连接mysql，并返回 connections 
	 */
	 @SuppressWarnings("finally")
	public static synchronized Connection getC3p0Connection() {
	        try {
	        	con= comboPooledDataSource.getConnection();
				if (con != null) {
					log("Connection Successful! Enjoy. Now it's time to push data------已经陈功连接");
				} else {
					log("Failed to make connection!");
				}
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
				return con;
	        }
	    }
	
	 
 
	public static  String  evaluate(String ip){
		String target="select country from dbip_inter_2 force index(ip_index) where start_ip<=inet_aton('"+ip+"') and end_ip>=inet_aton('"+ip+"')";
		return  getDataFromDB(target);
	}

 
	
	/**
	 * 从数据库中查询数据
	 */
	private static String getDataFromDB(String sql) {
		ResultSet rs=null;
		try {
			// MySQL Select Query Tutorial
//			String getQueryStatement = "SELECT * FROM dbip_inter_2 limit 100;";
 
			crunchifyPrepareStat = getC3p0Connection().prepareStatement(sql);
 
			// Execute the Query, and get a java ResultSet
			rs = crunchifyPrepareStat.executeQuery();
 
			// Let's iterate through the java ResultSet
			while (rs.next()) {
//				Integer start_ip = rs.getInt("start_ip");
//				Integer end_ip = rs.getInt("end_ip");
				String country = rs.getString("country");
//				String province = rs.getString("province");
//				String city = rs.getString("city");
 
				// Simply Print the results
//				System.out.format("%d, %d, %s, %s, %s\n", start_ip, end_ip, country, province,city);
				
				if(country != null){
					result=country;
				}else{
					result="^_^ not found ";
				}
				
			}
			crunchifyPrepareStat.close();
			con.close();
 
		} catch ( SQLException e) {
			log(e.toString());
			 e.printStackTrace( );
		}
				return result;
 
	}
 
	
	/**
	 * 自定义方法，实现 控制台输出
	 */
	private static void log(String string) {
//		System.out.println(string);
 
	}
	
}
