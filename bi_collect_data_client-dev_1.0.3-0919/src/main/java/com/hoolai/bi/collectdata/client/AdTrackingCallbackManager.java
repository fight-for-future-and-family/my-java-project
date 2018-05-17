package com.hoolai.bi.collectdata.client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.hoolai.bi.collectdata.client.core.Constant;
import com.hoolai.bi.collectdata.client.metrics.Install;
import com.hoolai.bi.collectdata.client.thread.ScheduledSystemThreadFactory;
import com.hoolai.bi.collectdata.client.util.JSONUtils;

public class AdTrackingCallbackManager {
	
	private final int sendDataPoolNumMax = 10;
	
	private BlockingQueue<AdInstallNotifer> blockingQueue;
	
	private ScheduledExecutorService rollService;

	public AdTrackingCallbackManager() {
		super();
		this.init();
	}
	
	private void init(){
		if(Constant.AD_TRACKING_CALLBACK_ISOPEN){
			this.blockingQueue = new LinkedBlockingDeque<AdInstallNotifer>(100000);
			this.rollService = Executors.newScheduledThreadPool(1, new ScheduledSystemThreadFactory("adtracking-manager-" + Thread.currentThread().getId() + "-%d"));
			AdTrackingCallbackThread adTrackingCallbackThread=new AdTrackingCallbackThread(this.blockingQueue);
			rollService.scheduleAtFixedRate(adTrackingCallbackThread, 10, 10, TimeUnit.SECONDS);
		}
	}
	

	public void tracking(AdInstallNotifer adInstallNotifer){
		if(!Constant.AD_TRACKING_CALLBACK_ISOPEN){
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
		
		private final ThreadPoolExecutor threadPool; 
		
		
		
		public AdTrackingCallbackThread(BlockingQueue<AdInstallNotifer> blockingQueue) {
			super();
			this.blockingQueue = blockingQueue;
			threadPool = new ThreadPoolExecutor(1, sendDataPoolNumMax , 30, TimeUnit.SECONDS, 
		            new ArrayBlockingQueue<Runnable>(100000,true), 
		            new ThreadPoolExecutor.DiscardOldestPolicy()); 
		}

		@Override
		public void run() {
			try {
				int blockingQueueSize = getCacheSize();
				int idieSize = new Double(blockingQueueSize / 1000 ).intValue() + 1;
				if(blockingQueueSize > 0){
					for(int i = 0 ; i < idieSize ; i++){
						threadPool.submit(new SendAdtrackingDataThread("adtracking-send" ,1000, blockingQueue));
					}
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
	
	private static class SendAdtrackingDataThread implements Runnable{
		private final Logger LOGGER=Logger.getLogger(SendAdtrackingDataThread.class);
		private static final AtomicInteger poolNumber = new AtomicInteger(1);
		private final BlockingQueue<AdInstallNotifer> blockingQueue;
		private int sendSize;
		
		public SendAdtrackingDataThread(String name, int sendSize, BlockingQueue<AdInstallNotifer> blockingQueue){
			Thread.currentThread().setName(name+"-" + poolNumber.getAndIncrement() + "-thread-");
			this.sendSize = sendSize;
			this.blockingQueue = blockingQueue;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < sendSize; i++) {
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
							LOGGER.info("params:" + callbackParamsMap.get("callbackParams"));
							String res = SendInstallDatasProcesser.postStringResource(Constant.ADTRACKING_SERVER_URL,callbackParamsMap, "UTF-8");
							System.out.println(res);
							TrackingResult trackingResult = JSONUtils.fromJSON(res, TrackingResult.class);
							if(null != trackingResult){
								break;
							}else{
								replayIndex++;
							}
							LOGGER.info("tracking callback url:" + Constant.ADTRACKING_SERVER_URL + "; params:" + callbackParamsMap.get("callbackParams") + ";status:" + trackingResult.getStatus() + " ;msg:"+trackingResult.getErrMsg());
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
		
		private Map<String, String> buildCallbackUrl(AdInstallNotifer adInstallNotifer){
			if(null == adInstallNotifer || null == adInstallNotifer.getInstall()){
				return null;
			}
			try {
				Map<String, String> paramsMap = new HashMap<String, String>();
				
				adInstallNotifer.getInstall().fromExtra();
				String ip= adInstallNotifer.getInstall().getFrom_uid();
				String idfa=adInstallNotifer.getInstall().getExtraValue("IDFA");
				if(ip==null&&"".equals(ip)&&idfa==null&&"".equals(idfa)){
					return null;
				}
				String callbackParams=JSONUtils.toJSON(adInstallNotifer);
				paramsMap.put("callbackParams", callbackParams);
				
				return paramsMap;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
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

