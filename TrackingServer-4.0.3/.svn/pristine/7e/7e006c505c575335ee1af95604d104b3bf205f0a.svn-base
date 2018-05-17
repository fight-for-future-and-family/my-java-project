package com.hoolai.bi.tracking.log;

import com.hoolai.util.DateUtils;

public class TrackStandardAccess {

	private String snid;
	private String gameid;
	private String clientid;
	private String ip;
	private String ds;
	private String metric;
	private String metricObjJsonStr;
	
	public TrackStandardAccess(){}
	
	public TrackStandardAccess(String snid, String gameid, String clientid,
			String ds,String metric, String jsonString, String ip) {
		this.snid = snid;
		this.clientid = clientid;
		this.gameid = gameid;
		this.ds = DateUtils.verifyDate(ds);
		this.metric = metric;
		this.metricObjJsonStr = jsonString;
		this.ip = ip;
	}

	public String getSnid() {
		return snid;
	}
	
	public void setSnid(String snid) {
		this.snid = snid;
	}
	
	public String getGameid() {
		return gameid;
	}

	public void setGameid(String gameid) {
		this.gameid = gameid;
	}

	public String getClientid() {
		return clientid;
	}
	
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	
	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getMetric() {
		return metric;
	}
	
	public void setMetric(String metric) {
		this.metric = metric;
	}
	
	public String getMetricObjJsonStr() {
		return metricObjJsonStr;
	}

	public void setMetricObjJsonStr(String metricObjJsonStr) {
		this.metricObjJsonStr = metricObjJsonStr;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
