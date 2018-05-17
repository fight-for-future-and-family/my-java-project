package com.dw.services;

import org.apache.log4j.Logger;

import com.dw.metrics.TrackBase;
import com.hoolai.bi.tracking.service.TrackingService;
import com.hoolai.bi.tracking.service.impl.RollFileTrackingServiceImpl;
import com.hoolai.util.DateUtils;

public class TrackServices {
	
	public static Logger log4j = Logger.getLogger("pubbi");
	
	private static final TrackingService TRACKING_SERVICE=new RollFileTrackingServiceImpl();
	
	private static final MonitorMetricsServices MONITOR_METRICS_SERVICES=new MonitorMetricsServices();

	private TrackServices() {
		
	};

	/**
	 * 报送数据，日期默认系统当前日期
	 * @param sn_id 平台ID
	 * @param client_id 服务器Id
	 * @param game_id 游戏ID
	 * @param bean metric类
	 */
	public static void add(String sn_id, String client_id, String game_id,
			TrackBase bean) {
		addMessageToScribed(sn_id, client_id, game_id, bean);
	}
	
	/**
	 * 报送数据
	 * @param sn_id 平台ID
	 * @param client_id 服务器Id
	 * @param game_id 游戏ID
	 * @param bean metric类
	 * @param date 数据日期，格式（yyyy-MM-dd）,格式错误默认当天日期
	 */
	public static void add(String sn_id, String client_id, String game_id,
			TrackBase bean, String date) {
		addMessageToScribed(sn_id, client_id, game_id, bean, date);
	}

	private static void addMessageToScribed(String sn_id, String client_id,
			String game_id, TrackBase bean) {
		if (sn_id != null && client_id != null && game_id != null) {
			
			
			String category=bean.metric() + "_" + sn_id+ "_" + game_id + "_" + Utils.getDate();
			String sourceCategory=bean.metric();
			String body=bean.prepareForDB(client_id);
			
			persistentToFile(category, sourceCategory, body);
			
		} else {
			log4j.error("AddMessageToScribed Failed, Caused by SN/Client/Game ID is null");
		}
	}

	private static void addMessageToScribed(String sn_id, String client_id,
			String game_id, TrackBase bean, String date) {
		if (sn_id != null && client_id != null && game_id != null) {
			
			String category=bean.metric() + "_" + sn_id+ "_" + game_id + "_" + DateUtils.verifyDate(date);
			String sourceCategory=bean.metric();
			String body=bean.prepareForDB(client_id);
			
			persistentToFile(category, sourceCategory, body);
			
		} else {
			log4j.error("AddMessageToScribed Failed, Caused by SN/Client/Game ID is null");
		}
	}

	
	private static void persistentToFile(final String category,final String sourceCategory,final String body){
		// 此处的category即使event（带snid和gameid）
		boolean isSucc=TRACKING_SERVICE.send(sourceCategory, category, body);
		
//		if(isMonitor(category, sourceCategory)){
//			if(isSucc){
//				MONITOR_METRICS_SERVICES.succ(category);
//			}else{
//				MONITOR_METRICS_SERVICES.fail(category);
//			}
//		}
	}
	
	private static boolean isMonitor(String category, String sourceCategory){
		if("metricrecordmess".equalsIgnoreCase(sourceCategory)){
			return false;
		}
		return true;
	}
}
