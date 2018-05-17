package com.hoolai.bi.report.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.util.StringUtils;

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
	
	public static String RESOURCE_URL;
	
	public static String SEARCH_INIT_IP;
	
	public static String SITE_NAME;
	
	public static String UPLOAD_PATH;
	
	public static String MANAGE_AUTH_EXCLUDE_URL;
	
	public static boolean IS_PUBLISHED=false;
	
	public static String VERSION;
	
	public static String ELT_ENGINE_TRIGGER_URL;
	
	public static String[] MONITOR_SYS_EMAILS;
	
	public static String SYN_GAME_RATE_SHELL;
	public static String SYN_GAME_RATE_FILE_PATH;
	
	public static String GAME_ETL_FINISHED_TOPIC;
	
	public static String QUASI_GAME_ETL_FINISHED_TOPIC;
	
	public static String RUN_QUASI_GAME_ETL_TOPIC;
	
	public static final Properties BI_PRODUCER_CONFIGS=new Properties();
	
	public static final Properties BI_CONSUMER_CONFIGS=new Properties();
	
	public static final Properties BI_JDBC_CONFIGS=new Properties();
	
	public static boolean IS_MAINTAIN = false;
	
	public static String PLATFORM_SYN_URL = null;
	
	public static String PLATFORM_PARAM_SYN_URL = null;
	
	public static String TRACKING_URL = null;
	
	public static Map<String,Double> TO_RMB_RATE= null;
	
	static {
		init();
	}
	
	public static void init(){
		try {
			InputStream globleIn = Constant.class.getClassLoader().getResourceAsStream(CONSTANT_GLOBLE_FILE_PATH);
			Properties globleProperties = new Properties();
			if(globleIn==null){
				throw new RuntimeException("constant_globle.properties is not exists!");
			}
			globleProperties.load(globleIn);
			// 项目本事的常量文件
			String constantFilePath=globleProperties.getProperty("constant_file_path");
			if(StringUtils.isEmpty(constantFilePath)){
				throw new RuntimeException("constant_file_path is not contain in the constant_globle.properties!");
			}
			
			processProjectConstant(constantFilePath);
			
			String producerFilePath=globleProperties.getProperty("bi_producer_configs");
			processProducer(producerFilePath);
			
			String consumerFilePath=globleProperties.getProperty("bi_consumer_configs");
			processConsumer(consumerFilePath);
			
			String jdbcFilePath=globleProperties.getProperty("bi_jdbc_configs");
			processJdbc(jdbcFilePath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void processProjectConstant(String constantFilePath) throws Exception{
		InputStream constantIn = Constant.class.getClassLoader().getResourceAsStream(constantFilePath);
		constantProperties.load(constantIn);
		
		SERVER_URL=constantProperties.getProperty("server_url");
		RESOURCE_URL=constantProperties.getProperty("resource_url");
		SEARCH_INIT_IP=constantProperties.getProperty("search_init_ip");
		SITE_NAME=constantProperties.getProperty("site_name");
		MANAGE_AUTH_EXCLUDE_URL=constantProperties.getProperty("manage_auth_exclude_url");
		UPLOAD_PATH=constantProperties.getProperty("upload_path");
		IS_PUBLISHED=Boolean.parseBoolean(constantProperties.getProperty("is_published"));
		VERSION=constantProperties.getProperty("version");
		ELT_ENGINE_TRIGGER_URL=constantProperties.getProperty("elt_engine_trigger_url");
		SYN_GAME_RATE_FILE_PATH=constantProperties.getProperty("syn_game_rate_file_path");
		SYN_GAME_RATE_SHELL=constantProperties.getProperty("syn_game_rate_shell");
		
		String monitorSysEmails=constantProperties.getProperty("monitor_sys_emails");
		if(!StringUtils.isEmpty(monitorSysEmails)){
			MONITOR_SYS_EMAILS=monitorSysEmails.split(",");
		}
		
		String trackingUrl=constantProperties.getProperty("trackingUrl");
		if(!StringUtils.isEmpty(trackingUrl)){
			TRACKING_URL=trackingUrl;
		}
		
		String platformSynUrl=constantProperties.getProperty("platformSynUrl");
		if(!StringUtils.isEmpty(platformSynUrl)){
			PLATFORM_SYN_URL=platformSynUrl;
		}
		
		String platformParamSynUrl=constantProperties.getProperty("platformParamSynUrl");
		if(!StringUtils.isEmpty(platformParamSynUrl)){
			PLATFORM_PARAM_SYN_URL=platformParamSynUrl;
		}
		
		String to_rmb_rate=constantProperties.getProperty("currency_converted_to_rmb_rate");
		if(!StringUtils.isEmpty(to_rmb_rate)){
			TO_RMB_RATE = new HashMap<String, Double>();
			String[] ces = to_rmb_rate.split(",",-1);
			for(String c:ces){
				String[] m = c.split(":");
				TO_RMB_RATE.put(m[0], Double.valueOf(m[1]));
			}
		}
		
		GAME_ETL_FINISHED_TOPIC=constantProperties.getProperty("game_etl_finished_topic");
		
		QUASI_GAME_ETL_FINISHED_TOPIC=constantProperties.getProperty("quasi_game_etl_finished_topic");
		
		RUN_QUASI_GAME_ETL_TOPIC=constantProperties.getProperty("run_quasi_game_etl_topic");
	}
	
	private static void processProducer(String filePath) throws Exception{
		
		if(StringUtils.isEmpty(filePath)){
			return ;
		}
		InputStream constantIn = Constant.class.getClassLoader().getResourceAsStream(filePath);
		BI_PRODUCER_CONFIGS.load(constantIn);
		
	}
	
	private static void processConsumer(String filePath) throws Exception{
		
		if(StringUtils.isEmpty(filePath)){
			return ;
		}
		InputStream constantIn = Constant.class.getClassLoader().getResourceAsStream(filePath);
		BI_CONSUMER_CONFIGS.load(constantIn);
		
	}
	private static void processJdbc(String filePath) throws Exception{
		
		if(StringUtils.isEmpty(filePath)){
			return ;
		}
		InputStream constantIn = Constant.class.getClassLoader().getResourceAsStream(filePath);
		BI_JDBC_CONFIGS.load(constantIn);
		
	}
	
}
