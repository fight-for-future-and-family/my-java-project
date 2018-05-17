package com.hoolai.bi.tracking.log;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dw.metrics.Bugly;
import com.dw.metrics.Dau;
import com.dw.metrics.Install;
import com.dw.metrics.TrackBase;
import com.dw.services.TrackServices;
import com.hoolai.bi.tracking.tools.RegularUtils;
import com.hoolai.bi.tracking.web.AdTrackingCallbackManager;
import com.hoolai.bi.tracking.web.AdTrackingCallbackManager.AdInstallNotifer;
import com.hoolai.core.Constants;
import com.hoolai.util.JSONUtils;

public class TrackingProcessor {
	
	private static final AdTrackingCallbackManager ADTRACKING_CALLBACK_MANAGER=AdTrackingCallbackManager.getInstance();
	
	private TrackStandardAccess access;
	private TrackBase trackBase;
	TrackingType trackingType;
	
	private Map<String,Object> ret = new HashMap<String,Object>();

	public TrackingProcessor(TrackStandardAccess access){
		this.access = access;
		this.ret.put("requestJsonStr", access);
	}
	
	public void log(){
		if(isLog() && !isGameBaseParmNull()){
			trackingType = TrackingType.convert(access.getMetric());
			trackBase = (TrackBase) trackingType.convertObj(access.getMetricObjJsonStr());
			if(!isBaseParmNull()){
				trackBase.processDate();
				trackBase.processIp(access.getIp());
				if(trackBase instanceof Install){
					
					AdInstallNotifer adInstallNotifer = new AdInstallNotifer(access.getSnid(), access.getGameid(), access.getClientid(), access.getDs(), (Install)trackBase);
					
					ADTRACKING_CALLBACK_MANAGER.tracking(adInstallNotifer);
					
				}
				addTracking();
			}
		}
	}

	private void addTracking() {
		TrackServices.add(access.getSnid(), access.getClientid(), access.getGameid(), trackBase, access.getDs());
		ret.put("status", TrackingStatus.SECCESS);
	}
	
	private boolean isBaseParmNull(){
		if(trackBase.checkBaseParmIsNull()){
			ret.put("status", TrackingStatus.BASE_PARM_NULL);
			ret.put("cause", access.getMetric()+" need main parameter is null.");
			return true;
		}
		return false;
	}

	private boolean isGameBaseParmNull() {
		String snid = access.getSnid();
		String gameid = access.getGameid();
		String clientid = access.getClientid();
		if(!StringUtils.isEmpty(snid) && !StringUtils.isEmpty(gameid)){
			if(StringUtils.isEmpty(clientid)
				|| !RegularUtils.isNumber(snid)
				|| !RegularUtils.isNumber(gameid)
				|| !RegularUtils.isNumber(clientid)){
				ret.put("status", TrackingStatus.BASE_PARM_NULL);
				ret.put("cause", "snid,gameid,clientid is null or not number or no config");
				return true;
			}
		}else if(StringUtils.isEmpty(Constants.SNID) || StringUtils.isEmpty(Constants.GAMEID)){
			ret.put("status", TrackingStatus.BASE_PARM_NULL);
			ret.put("cause", "snid,gameid,clientid is null or not number or no config");
			return true;
		}else{
			access.setSnid(Constants.SNID);
			access.setGameid(Constants.GAMEID);
		}
		return false;
	}

	private boolean isLog() {
		boolean isMetricsValidation = TrackingType.isMetricsValidation(access.getMetric());
		if(Constants.IS_TRACKINGON && isMetricsValidation){
			return true;
		}else if(!Constants.IS_TRACKINGON){
			ret.put("status", TrackingStatus.NO_CONFIG_LOG);
			ret.put("cause", "config traking_on is false.");
		}else if(!isMetricsValidation){
			ret.put("status", TrackingStatus.NO_MATCH_METRIC);
			ret.put("cause", "The parameter(metric): "+access.getMetric()+", did not match success.");
		}
		return false;
	}

	public Map<String, Object> getRet() {
		return ret;
	}
	
	public static void main(String[] args) {
		Bugly bugly = new Bugly();
		bugly.setUserId("123");
		bugly.setBuglyDate("2015-12-12");
		bugly.setBuglyTime("12:12:12");
		System.out.println(JSONUtils.toJSON(bugly));
		
		TrackServices.add("200", "1001", "1", bugly);
	}
}
