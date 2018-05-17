package com.hoolai.bi.collectdata.client.core;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.hoolai.bi.compress.CompressType;

public class Constant {

	public static final String DEFAULT_FORMAT_DATE_DAY="yyyy-MM-dd";
	
	public static final String DEFAULT_FORMAT_DATE_MINUTES="yyyy-MM-dd HH:mm";
	
	public static final String DEFAULT_FORMAT_DATE_SECONDS="yyyy-MM-dd HH:mm:ss";
	
	public static final String DEFAULT_CHARSET="UTF-8";
	
	public static final int DEFAULT_INDEX_ZEAO = 0;
	
	public static final int DEFAULT_INDEX_FIRST= 1;
	
	// 默认long
	public static long DEFAULT_LONG_ZEAO = 0;
	
	// 默认long
	public static long DEFAULT_LONG_FIRST = 1;
	
	// 默认分隔符
	public static final String DEFAULT_SPLIT = ",";

	// 默认空格
	public static final String DEFAULT_BLANK = " ";

	// 默认下划线
	public static final String DEFAULT_UNDERLINE = "_";

	// 默认斜线
	public static final String DEFAULT_SPRIT = "/";
	
	public static final String CONSTANT_GLOBLE_FILE_PATH = "constant_globle.properties";

	public static final Properties constantProperties=new Properties();
	
	public static String SERVER_URL;
	
	public static String ADTRACKING_SERVER_URL;
	
	public static String[] MONITOR_SYS_EMAILS;
	
	// 检测的目录
	public static String DETECT_PATH;
	
	public static String SEND_MATCH_FILE_SUBFIX;
	
	public static String[] SEND_MATCH_FILE_INDEX;
	
	public static String SEND_MATCH_FILE_AD_SUBFIX;
	
	public static boolean IS_PUBLISHED=false;
	
	public static CompressType compressType;
	
	public static boolean IS_DELETE_SOURCE_FILE =false;
	
	public static boolean AD_TRACKING_CALLBACK_ISOPEN=false;
	public static boolean IS_RUN_INSTALL_SEND = false;
	
	// 所有待传输文件，最大传输时间
	public static int MAX_DETECT_FILES_MINUTES=10;
	
	public static void init(String constantFilePathIn){
		try {
			InputStream globleIn = Constant.class.getClassLoader().getResourceAsStream(CONSTANT_GLOBLE_FILE_PATH);
			Properties globleProperties = new Properties();
			if(globleIn==null){
				throw new RuntimeException("constant_globle.properties is not exists!");
			}
			globleProperties.load(globleIn);
			
			// 项目本事的常量文件
			String constantFilePath=globleProperties.getProperty("constant_file_path");
			InputStream constantIn=Constant.class.getClassLoader().getResourceAsStream(constantFilePath);
			if(constantFilePathIn!=null){
				constantFilePath=constantFilePathIn;
				constantIn = new FileInputStream(constantFilePathIn);
			}
			
			processProjectConstant(constantIn);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void processProjectConstant(InputStream constantIn) throws Exception{
		constantProperties.load(constantIn);
		
		SERVER_URL=constantProperties.getProperty("server_url");
		ADTRACKING_SERVER_URL = constantProperties.getProperty("adtracking_server_url");
		String monitorSysEmails=constantProperties.getProperty("monitor_sys_emails");
		if(monitorSysEmails!=null){
			MONITOR_SYS_EMAILS=monitorSysEmails.split(",");
		}
		DETECT_PATH=constantProperties.getProperty("detect_path");
		SEND_MATCH_FILE_SUBFIX=constantProperties.getProperty("send_match_file_subfix");
		
		SEND_MATCH_FILE_INDEX = constantProperties.getProperty("send_match_file_index").split(",");
		SEND_MATCH_FILE_AD_SUBFIX = constantProperties.getProperty("send_match_file_ad_subfix");
		IS_PUBLISHED=Boolean.parseBoolean(constantProperties.getProperty("is_published"));
		String compressTypeStr=constantProperties.getProperty("compress_type");
		if(compressTypeStr!=null){
			compressType=CompressType.findByName(compressTypeStr);
		}
		String isDeleteSourceFileStr=constantProperties.getProperty("is_delete_source_file");
		if(isDeleteSourceFileStr!=null){
			IS_DELETE_SOURCE_FILE=Boolean.parseBoolean(isDeleteSourceFileStr);
		}
		String maxDetectFilesMinutesStr=constantProperties.getProperty("max_detect_files_minutes");
		if(maxDetectFilesMinutesStr!=null){
			MAX_DETECT_FILES_MINUTES=Integer.parseInt(maxDetectFilesMinutesStr);
		}
		String isRunInstallSend = constantProperties.getProperty("isrun_install_send");
		if (StringUtils.isNotEmpty(isRunInstallSend)){
			IS_RUN_INSTALL_SEND = Boolean.parseBoolean(isRunInstallSend);
		}
		String adTrackingCallbackIsopen = constantProperties.getProperty("ad_tracking_callback_isopen");
		if (StringUtils.isNotEmpty(adTrackingCallbackIsopen)){
			AD_TRACKING_CALLBACK_ISOPEN = Boolean.parseBoolean(adTrackingCallbackIsopen);
		}
		
	}
	
}
