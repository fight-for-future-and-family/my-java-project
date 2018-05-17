package com.hoolai.bi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BiTest {
	// public static final String url =
	// "jdbc:mysql://192.168.90.119:3306/bitest?useUnicode=true&characterEncoding=utf8mb4";
	// public static final String url =
	// "jdbc:mysql://192.168.90.119:3306/bitest?useUnicode=true&characterEncoding=UTF8";
//	public static final String url = "jdbc:mysql://192.168.90.119:3306/bitest?useUnicode=true&characterEncoding=gbk";
	public static final String url = "jdbc:mysql://192.168.90.119:3306/bitest?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";

	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "hoolaibi";

	public Connection conn = null;
	public PreparedStatement pst = null;

	public BiTest(String sql) {
		try {
			Class.forName(name);// 指定连接类型
			conn = DriverManager.getConnection(url, user, password);// 获取连接
			pst = conn.prepareStatement(sql);// 准备执行语句
			// conn = DriverManager.getConnection(url, "root", "hoolaibi");
			// pst = conn.prepareStatement("SET NAMES 'utf8mb4'");
			// pst.setBytes(12, temp);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		BiTest bt = null;
		ResultSet ret = null;
		String str = "";
		String sql = "SELECT m   FROM   bitest   WHERE m IS NOT NULL AND m<>'' and  m like '%善待平民玩家%'";// SQL语句
		bt = new BiTest(sql);// 创建MySqljdbc对象

		try {
			String sqlCharset = "set names utf8mb4"  ;
			ret = bt.pst.executeQuery();// 执行语句，得到结果集

			while (ret.next()) {
				String reStr = ret.getCursorName();
				System.out.println(reStr);
				System.out.println(str + "_________________----");
				System.out.println(Character.emojiRecovery2(reStr));
				System.out.println('\n');
			}

			ret.close();
			bt.close();// 关闭连接
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
