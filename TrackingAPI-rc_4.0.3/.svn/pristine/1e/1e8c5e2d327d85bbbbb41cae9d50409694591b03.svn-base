package com.dw.metrics;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hoolai.util.DateUtils;


public class Landing extends TrackBase {

	private String cookie;
	private String act;
	private String currentUrl;
	private String referer;
	private String downloadUrl;
	private String date;
	private String time;
	
	public Landing(){
		this.init();
	}
	
	private void init(){
		this.cookie = "";
		this.act = "";
		this.currentUrl = "";
		this.referer = "";
		this.downloadUrl = "";
		this.date = "";
		this.time = "";
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getCookie());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.act);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.currentUrl);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.referer);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.downloadUrl);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.date);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.time);
		return buf.toString();
	}

	@Override
	public boolean checkBaseParmIsNull() {
		
		return false;
	}

	@Override
	public void processDate() {
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		this.date = sdf.format(date);
//		sdf = new SimpleDateFormat("HH:mm:ss");
//		this.time = sdf.format(date);
		
		this.date = (DateUtils.verifyDate(date, ds));
		this.time = (DateUtils.verifyTime(time));
	}

	@Override
	public String metric() {
		return "landing";
	}
}
