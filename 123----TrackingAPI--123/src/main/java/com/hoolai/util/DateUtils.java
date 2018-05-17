package com.hoolai.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {

	 @SuppressWarnings("rawtypes")
	private static ThreadLocal threadlocal = new ThreadLocal() {
	  protected synchronized SimpleDateFormat initialValue() {
	   return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  }
	 };
	 @SuppressWarnings("rawtypes")
	 private static ThreadLocal threadlocal_day = new ThreadLocal() {
		 protected synchronized SimpleDateFormat initialValue() {
			 return new SimpleDateFormat("yyyy-MM-dd");
		 }
	 };
	 
	 @SuppressWarnings("rawtypes")
	 private static ThreadLocal threadlocal_seconds = new ThreadLocal() {
		 protected synchronized SimpleDateFormat initialValue() {
			 return new SimpleDateFormat("HH:mm:ss");
		 }
	 };
	 
	 private static SimpleDateFormat getDateFormatDaySeconds() {
	  return (SimpleDateFormat) threadlocal.get();
	 }
	 
	 private static SimpleDateFormat getDateFormatDay() {
		 return (SimpleDateFormat) threadlocal_day.get();
	 }
	 
	 private static SimpleDateFormat getDateFormatSeconds() {
		 return (SimpleDateFormat) threadlocal_seconds.get();
	 }
	 
	
	public static String format(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	public static String formatSeconds(Date date) {
		return getDateFormatDaySeconds().format(date);
	}

	public static String formatDay(Date date) {
		return getDateFormatDay().format(date);
	}

	public static Map<String, String> convertTimeToDateStr(String timeStr) {
		Map<String, String> map = new HashMap<String, String>();
		String date = "";
		String time = "";
		try {
			String dateTimeStr = getDateFormatDaySeconds().format(Long.parseLong(timeStr));
			date = dateTimeStr.substring(0, 10);
			time = dateTimeStr.substring(11);
		} catch (Exception e) {
			date = String.valueOf(getDateFormatDay().format(new Date()));
			time = String.valueOf(getDateFormatSeconds().format(new Date()));
		}
		map.put("date", date);
		map.put("time", time);
		return map;
	}

	public static boolean isValidTimeStr(String timeStr) {
		try {
			getDateFormatDaySeconds().format(Long.parseLong(timeStr));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static String verifyTime(String timeStr) {
		Date time = new Date();
		try {
			time = getDateFormatSeconds().parse(timeStr);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(timeStr);
			return getDateFormatSeconds().format(new Date());
		}
		return getDateFormatSeconds().format(time);
	}

	public static String getDate() {
		return getDateFormatDay().format(new Date());
	}

	public static String getTime() {
		Date date = new Date();
		return getDateFormatSeconds().format(date);
	}

	public static String verifyDate(String dateStr) {
		
		try {
			getDateFormatDay().parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(dateStr);
			return getDateFormatDay().format(new Date());
			
		}
		return dateStr;
	}

	public static boolean verifyDateForSendingData(String dateStr) {
		try {
			getDateFormatDay().parse(dateStr);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static String verifyDateAndTime(String dateStr) {
		try {
			getDateFormatDaySeconds().parse(dateStr);
		} catch (Exception e) {
			return getDateFormatDaySeconds().format(new Date());
		}
		return dateStr;
	}

}
