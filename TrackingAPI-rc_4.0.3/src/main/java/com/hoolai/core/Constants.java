package com.hoolai.core;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class Constants {
	
	public static final String CONFIG_PATH="trackingConfig/tracking.properties";

	private static final Properties CONFIGS=new Properties();
	
	public final static long ONE_DAY_MILLS=24*60*60*1000;
	
	public static boolean IS_COUNT_METRICS=true; 
	
	public static boolean IS_OPEN_SCRIBE=true;
	
	public static boolean AD_TRACKING_CALLBACK_ISOPEN=false;
	
	public static String AD_TRACKING_CALLBACK_URL;
	
	public static final Integer TIME_KEY1 = 1;
	
	public static final Integer TIME_KEY2 = 2;
	
	public static boolean IS_TRACKINGON = true;
	
	public static String SNID;
	
	public static String GAMEID;
	
	static {
		init(CONFIG_PATH);
	}
	
	/**
	 * 初始化配置参数<br>
	 * 可以根据不同环境初始化不同的参数
	 * @param configPath 配置文件的路径
	 */
	public static void init(String configPath){
		try {
			InputStream globleIn = Constants.class.getClassLoader().getResourceAsStream(configPath);
			if(globleIn==null){
				throw new RuntimeException(configPath+" is not exists!");
			}
			CONFIGS.load(globleIn);
			
			parse();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void parse(){
		if(CONFIGS.containsKey("is_count_metrics")){
			IS_COUNT_METRICS=Boolean.parseBoolean(CONFIGS.getProperty("is_count_metrics"));
		}
		if(CONFIGS.containsKey("is_open_scribe")){
			IS_OPEN_SCRIBE=Boolean.parseBoolean(CONFIGS.getProperty("is_open_scribe"));
		}
		if(CONFIGS.containsKey("ad_tracking_callback_isopen")){
			AD_TRACKING_CALLBACK_ISOPEN=Boolean.parseBoolean(CONFIGS.getProperty("ad_tracking_callback_isopen"));
		}
		if(CONFIGS.containsKey("ad_tracking_callback_url")){
			AD_TRACKING_CALLBACK_URL=CONFIGS.getProperty("ad_tracking_callback_url");
		}
		if(CONFIGS.containsKey("tracking_on")){
			String tracking_on = CONFIGS.getProperty("tracking_on");
			if(!StringUtils.isEmpty(tracking_on) && "1".equals(tracking_on)){
				IS_TRACKINGON = true;
			}else{
				IS_TRACKINGON = false;
			}
		}
		if(CONFIGS.containsKey("sn_id")){
			String sn_id = CONFIGS.getProperty("sn_id");
			if(!StringUtils.isEmpty(sn_id)){
				SNID = sn_id;
			}
		}
		if(CONFIGS.containsKey("game_id")){
			String game_id = CONFIGS.getProperty("game_id");
			if(!StringUtils.isEmpty(game_id)){
				GAMEID = game_id;
			}
		}
	}

	public static Properties getConfigs() {
		return CONFIGS;
	}
	
}
