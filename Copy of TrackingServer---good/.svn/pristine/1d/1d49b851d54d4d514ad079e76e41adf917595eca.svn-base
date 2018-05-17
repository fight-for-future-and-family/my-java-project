package com.hoolai.bi.tracking.web;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.dw.metrics.Install;
import com.hoolai.bi.tracking.clients.rollfile.RollFileClient.SystemThreadFactory;
import com.hoolai.bi.tracking.tools.SpiderUtilities;
import com.hoolai.core.Constants;
import com.hoolai.util.JSONUtils;

public class AdTrackingCallbackManager {
	
	private static AdTrackingCallbackManager INSTANCE=null;
	
	private BlockingQueue<AdInstallNotifer> blockingQueue;
	
	private ScheduledExecutorService rollService;

	private AdTrackingCallbackManager() {
		super();
		this.init();
	}
	
	private void init(){
		if(Constants.AD_TRACKING_CALLBACK_ISOPEN){
			this.blockingQueue = new LinkedBlockingDeque<AdInstallNotifer>(100000);
			this.rollService = Executors.newScheduledThreadPool(1,
					new SystemThreadFactory("adtracking-manager-" + Thread.currentThread().getId() + "-%d"));
			AdTrackingCallbackThread adTrackingCallbackThread=new AdTrackingCallbackThread(this.blockingQueue);
			rollService.scheduleAtFixedRate(adTrackingCallbackThread, 10, 10, TimeUnit.SECONDS);
		}
	}
	
	public static synchronized AdTrackingCallbackManager getInstance(){
		if(INSTANCE==null){
			INSTANCE=new AdTrackingCallbackManager();
		}
		return INSTANCE;
	}
	
	
	public void tracking(AdInstallNotifer adInstallNotifer){
		if(!Constants.AD_TRACKING_CALLBACK_ISOPEN){
			return ;
		}
		try {
			this.blockingQueue.add(adInstallNotifer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getCacheSize(){
		return this.blockingQueue.size();
	}
	
	private class AdTrackingCallbackThread implements Runnable{
		
		private final Logger LOGGER=Logger.getLogger(AdTrackingCallbackThread.class);
		
		private final BlockingQueue<AdInstallNotifer> blockingQueue;
		
		public AdTrackingCallbackThread(BlockingQueue<AdInstallNotifer> blockingQueue) {
			super();
			this.blockingQueue = blockingQueue;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				AdInstallNotifer adInstallNotifer = null;
				try {
					adInstallNotifer = blockingQueue.poll();
					// 如果缓存池中，没有，则直接退出
					if(null == adInstallNotifer){
						break ;
					}
					Map<String, String> callbackParamsMap = buildCallbackUrl(adInstallNotifer);
					if(null == callbackParamsMap || callbackParamsMap.isEmpty()){
						continue ;
					}
					int replayIndex = 0;
					do{
						try {
							String res = SpiderUtilities.postStringResource(Constants.AD_TRACKING_CALLBACK_URL,callbackParamsMap, "UTF-8");
							TrackingResult trackingResult = JSONUtils.fromJSON(res, TrackingResult.class);
							if(null != trackingResult && 0 == trackingResult.getStatus()){
								break;
							}else{
								replayIndex++;
							}
							LOGGER.info("tracking callback url:" + Constants.AD_TRACKING_CALLBACK_URL + "; params:" + callbackParamsMap.get("callbackParams") + ";status:" + trackingResult.getStatus() + " ;msg:"+trackingResult.getErrMsg());
						} catch (Exception e) {
							replayIndex++;
							e.printStackTrace();
						}
						
					}while(replayIndex < 3);
					
				}catch (Exception e) {
					e.printStackTrace();
					// 做兼容处理
					if(null != adInstallNotifer){
						this.blockingQueue.add(adInstallNotifer);
					}
				}
			}
		}
	}
	
	private Map<String, String> buildCallbackUrl(AdInstallNotifer adInstallNotifer){
		if(null == adInstallNotifer || null == adInstallNotifer.getInstall()){
			return null;
		}
		try {
			
			Map<String, String> paramsMap = new HashMap<String, String>();
			
			adInstallNotifer.getInstall().fromExtra();
			String ip= adInstallNotifer.getInstall().getFromUid();
			//String userAgent=adInstallNotifer.getInstall().getExtraValue("User-Agent");
			String idfa=adInstallNotifer.getInstall().findExtraValue("IDFA");
			//String mac=adInstallNotifer.getInstall().getExtraValue("MAC");
			if(ip==null&&"".equals(ip)&&idfa==null&&"".equals(idfa)){
				return null;
			}
			String callbackParams=JSONUtils.toJSON(adInstallNotifer);
			paramsMap.put("callbackParams", callbackParams);
			
			//StringBuilder sb=new StringBuilder(Constants.AD_TRACKING_CALLBACK_URL);
			/*AdTracking adTracking=new AdTracking();
			adTracking.setIfa(idfa);
			adTracking.setMac(mac);
			adTracking.setIp(ip);
			adTracking.setUserAgent(userAgent);
			adTracking.setDs(install.getInstallDate());
			adTracking.setUserId(install.getUserId());*/
			//sb.append("?callbackParams=").append(URLEncoder.encode(callbackParams, "UTF-8"));
			//return sb.toString();
			return paramsMap;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static class TrackingResult {
		
		private int status;
		
		private String errMsg;
		
		public TrackingResult() {
			super();
		}

		public TrackingResult(int status) {
			super();
			this.status = status;
		}

		public TrackingResult(int status, String errMsg) {
			super();
			this.status = status;
			this.errMsg = errMsg;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getErrMsg() {
			return errMsg;
		}

		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}

	}
	//与广告系统进行对接
	public static class  AdInstallNotifer{
		
		private String snId;
		private String gameId;
		private String clientId;
		private String ds;
		private Install install;
		
		public AdInstallNotifer(){}
		
		public AdInstallNotifer(String snId, String gameId,
				String clientId, String ds, Install install) {
			super();
			this.snId = snId;
			this.gameId = gameId;
			this.clientId = clientId;
			this.ds = ds;
			this.install = install;
		}

		public Install getInstall() {
			return install;
		}
		public void setInstall(Install install) {
			this.install = install;
		}
		public String getSnId() {
			return snId;
		}
		public void setSnId(String snId) {
			this.snId = snId;
		}
		public String getGameId() {
			return gameId;
		}
		public void setGameId(String gameId) {
			this.gameId = gameId;
		}
		public String getClientId() {
			return clientId;
		}
		public void setClientId(String clientId) {
			this.clientId = clientId;
		}
		public String getDs() {
			return ds;
		}
		public void setDs(String ds) {
			this.ds = ds;
		}
		
	}
}

