package com.hoolai.manage.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <b>类说明：</b> <blockquote>
 * 
 * </blockquote>
 * 
 * @author jiangqm E-mail: jiangqiming@jiangqiming.cn
 * @version 创建时间：2015年11月18日 下午2:15:50
 */

public class CleanupSqlUtil {
	//private static String sqlFileName = "cleanupsql/cleanupsql.txt";

	private static String readTxtFile(String sqlFileName) throws Exception {
		StringBuffer sb = new StringBuffer();
		try {
			String encoding = "UTF-8";
			InputStreamReader read = new InputStreamReader(CleanupSqlUtil.class
					.getClassLoader().getResourceAsStream(sqlFileName),
					encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				sb.append(lineTxt);
			}
			read.close();

		} catch (Exception e) {
			throw new Exception();
		}
		return sb.toString();
	}
	
	public static List<String> getHiveQLList(String sqlFileName){
		List<String> sqlList = new ArrayList<String>();
		try {
			String sqlString = readTxtFile(sqlFileName);
			String[] sqlArray = sqlString.split(";");
			sqlList = Arrays.asList(sqlArray);
		} catch (Exception e) {
			return sqlList;
		}
		return sqlList;
	}
}
