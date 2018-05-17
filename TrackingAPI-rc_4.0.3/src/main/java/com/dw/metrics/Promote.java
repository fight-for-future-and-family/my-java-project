package com.dw.metrics;

import com.hoolai.util.DateUtils;

public class Promote extends TrackBase{
	
	/**
	 * 日期,格式(yyyy-MM-dd)
	 */
	private String date;
	/**
	 * 时间,格式(HH:mm:ss)
	 */
	private String time;
	
	@Override
	public String metric() {
		return "promote";
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
		buf.append(super.udid);
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
		this.setDate(DateUtils.verifyDate(date, ds));
		this.setTime(DateUtils.verifyTime(time));
	}
	
}
