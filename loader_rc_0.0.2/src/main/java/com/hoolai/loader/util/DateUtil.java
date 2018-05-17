package com.hoolai.loader.util;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 日期工具类
 * @author ruijie
 * @date 2013-9-16
 * @version V1.0
 */
public class DateUtil {
	
	private static Logger log = Logger.getLogger(DateUtil.class);
	
	/**
	 * 从今天起往前加载数据的天数
	 * 默认是3天
	 */
	private static int loadForwardDays = Constant.loadForwardDays;
	
	/**
	 * 删除备份本地几天前的数据
	 * 默认是 7天
	 */
	private static int delForwardDays = Constant.delForwardDays;
	
	/**
	 * 不知道这个参数是做什么的
	 */
	public static String processAloneDayStr;
	

	/**
	 * 判断一个日期字符串是否在待删除日期之前
	 * 默认是删除 7天
	 */
	public static boolean isBeforeDelDate(String dateStr){
		Date delDate = null;
		Date fileDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, - delForwardDays);
		delDate = c.getTime();
		
		try {
			fileDate = sdf.parse(dateStr);
		} catch (ParseException e) {
			log.error(e);
		}
		
		return fileDate.before(delDate);
	}

	/**
	 * 判断一个目录路径的日期是否在日期范围内
	 * 传入的是 日期字符串
	 * 如果传入的日期  是在默认的  从现在起的三天之内  那么返回真
	 * 当天 的日期会  返回 false
	 */
	public static boolean isInDateRange(String filePath){
		boolean result = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date now = null;
		try {
			now = sdf.parse(filePath);
			sdf = null;
		} catch (ParseException e) {
			log.error(e);
			result = false;
		}
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, - (loadForwardDays));
		Date start = c.getTime();
		
		c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 0);
		Date end = c.getTime();
		
		c = null;
		
		return result ? now.after(start) && now.before(end) : result;
	}
	/**
	 * 获得   指定天数   后的日期
	 */
	public static Date getAfterDate(int n){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, n);
		return c.getTime();
	}
	
}
