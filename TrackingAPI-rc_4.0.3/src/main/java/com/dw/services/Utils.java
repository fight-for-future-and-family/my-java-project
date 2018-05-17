package com.dw.services;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Utils {

	public static String getDate() {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(date);
	}

	public static String getTime() {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("hh:mm:ss");
		return sf.format(date);
	}

	public static long IP2Long(String IP) {
		long f1, f2, f3, f4;
		String tokens[] = IP.split("\\.");
		if (tokens.length != 4)
			return -1;
		try {
			f1 = Long.parseLong(tokens[0]) << 24;
			f2 = Long.parseLong(tokens[1]) << 16;
			f3 = Long.parseLong(tokens[2]) << 8;
			f4 = Long.parseLong(tokens[3]);
			return f1 + f2 + f3 + f4;
		} catch (Exception e) {
			return -1;
		}
	}

	public static String numToIP(long ip) {
		StringBuilder sb = new StringBuilder();
		for (int i = 3; i >= 0; i--) {
			sb.append((ip >>> (i * 8)) & 0x000000ff);
			if (i != 0) {
				sb.append('.');
			}
		}
		// System.out.println(sb);
		return sb.toString();
	}

	public static String char_replace(String chars) {
		return chars != null && chars.length() > 0 ? chars.toLowerCase()
				.replaceAll("[\"\'\\\\\\s#&]", "") : "";
	}

	public static String CutDownCreative(String creative) {
		if(StringUtils.isNotEmpty(creative)){
			return creative.length() >= 100 ? creative.substring(0, 100) : creative;
		}else{
			return creative;
		}
		
	}

	public static String convertUid(String openId) {
		String uid = new BigInteger(openId, 16).toString(10);
		System.out.println(uid);
		return null;
	}
	
	public static String checkAndConvertToInteger(String clientid){
		Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(clientid);
		   if(!isNum.matches()){
		       return "0"; 
		   } 
		   return clientid; 
	}

	public static String convert16Uid(String id) {
		String uid = new BigInteger(id, 10).toString(16);
		System.out.println(uid.toUpperCase());
		return null;
	}

}
