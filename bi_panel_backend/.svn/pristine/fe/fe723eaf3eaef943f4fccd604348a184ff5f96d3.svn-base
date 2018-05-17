package com.hoolai.bi.report.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static final SimpleDateFormat DATE_FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 获取当前周的第一天
	 * @return
	 */
	public static String getCurrWeekBeginDate(){
		Calendar calendar = Calendar.getInstance();
		return getCurrWeekBeginDate(calendar);
	}

	private static String getCurrWeekBeginDate(Calendar calendar) {
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1){
			calendar.add(Calendar.WEEK_OF_MONTH,-1);
		}
		calendar.set(Calendar.DAY_OF_WEEK,2);
		return getDateStr(calendar);
	}
	
	/**
	 * 获取当前周的最后一天
	 * @return
	 */
	public static String getCurrWeekEndDate(){
		Calendar calendar = Calendar.getInstance();
		return getCurrWeekEndDate(calendar);
	}

	private static String getCurrWeekEndDate(Calendar calendar) {
		if(calendar.get(Calendar.DAY_OF_WEEK) != 1){
			calendar.set(Calendar.DAY_OF_WEEK,7);
			calendar.add(Calendar.DAY_OF_WEEK, 1);
		}
		
		return getDateStr(calendar);
	}
	
	/**
	 * 获取当月的最后一天
	 * @return
	 */
	public static String getCurrMonthEndDate(){
		Calendar calendar = Calendar.getInstance();
		return getCurrMonthEndDate(calendar);
	}

	public static String getCurrMonthEndDate(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.add(Calendar.MONTH,1);
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		
		return getDateStr(calendar);
	}
	
	/**
	 * 获取当月的第一天
	 * @return
	 */
	public static String getCurrMonthBeginDate(){
		Calendar calendar = Calendar.getInstance();
		return getCurrMonthBeginDate(calendar);
	}

	public static String getCurrMonthBeginDate(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH,1);
		
		return getDateStr(calendar);
	}
	
	/**
	 * 获取某年某月的第某周的开始时间
	 * @param year 年份
	 * @param month 一年中的第几个月
	 * @param week 一个月中的第几周
	 * @return
	 */
	public static String getCurrMonthWeekBeginDate(int year,int month,int week){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH,month-1);
		calendar.set(Calendar.YEAR,year);
		calendar.set(Calendar.WEEK_OF_MONTH,week);
		if(week == 1){
			calendar.set(Calendar.DAY_OF_MONTH,1);
			return getDateStr(calendar);
		}else{
			return getCurrWeekBeginDate(calendar);
		}
	}
	
	/**
	 * 获取某年某月的第某周的开始时间
	 * @param year 年份
	 * @param month 一年中的第几个月
	 * @param week 一个月中的第几周
	 * @return
	 */
	public static String getCurrMonthWeekEndDate(int year,int month,int week){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.WEEK_OF_MONTH,week);
		calendar.set(Calendar.MONTH,month-1);
		calendar.set(Calendar.YEAR,year);
		if(week == calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)){
			return getCurrMonthEndDate(calendar);
		}
		
		return getCurrWeekEndDate(calendar);
	}
	
	/**
	 * 获取本月有几个自然周
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getActualMaximum(int year,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH,month-1);
		calendar.set(Calendar.YEAR,year);
		return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
	}
	
	/**
	 * 获取当前时间的前一天
	 * @return
	 */
	public static String getYesterday(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
		return getDateStr(calendar);
	}
	
	/**
	 * 格式（yyyy-MM-dd）
	 * @param calendar
	 * @return
	 */
	public static String getDateStr(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		String dateStr = "";
		if(month > 9){
			dateStr += year + "-" + month;
		}else{
			dateStr += year + "-0" + month;
		}
		
		if(day > 9){
			dateStr += "-" + day;
		}else{
			dateStr += "-0" + day;
		}
		
		return dateStr;
	}
	
	public static void main(String[] args) {
		
		System.out.println("getCurrWeekBeginDate:"+getCurrWeekBeginDate());
		System.out.println("getCurrWeekEndDate:"+getCurrWeekEndDate());
		System.out.println("getCurrMonthBeginDate:"+getCurrMonthBeginDate());
		System.out.println("getCurrMonthEndDate:"+getCurrMonthEndDate());
		System.out.println("getCurrMonthWeekBeginDate:"+getCurrMonthWeekBeginDate(2015,2,5));
		System.out.println("getCurrMonthWeekEndDate:"+getCurrMonthWeekEndDate(2015,2,5));
		System.out.println("getActualMaximum:"+getActualMaximum(2015,1));
		
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.get(Calendar.WEEK_OF_MONTH));
		System.out.println(getYesterday());
	}

	public static long getDateDiff(String dateStr, String lastdautime) {
		try {
			Date date1 = DATE_FORMAT_DAY.parse(dateStr);
			Date date2 = DATE_FORMAT_DAY.parse(lastdautime);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTimeInMillis(date1.getTime());
			
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTimeInMillis(date2.getTime());
			
			long between_days=(calendar1.getTimeInMillis()-calendar2.getTimeInMillis())/(1000l*3600l*24l); 
			return between_days;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
			//throw new RuntimeException();
		}
		
	}

	public static boolean isCorrectFormat(String opDate, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			df.parse(opDate);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
}
