package com.dw.metrics;

import java.util.Date;

import com.hoolai.util.DateUtils;


public class AdTracking extends TrackBase{

    private String appkey;

    private String mac;

    private String macMd5;

    private String ifa;

    private String ifaMd5;

    private String uuid;

    private Integer actType;

    private Date actTime;

    private String pf;

    private String ip;

    private String userAgent;

    private String ds;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMacMd5() {
        return macMd5;
    }

    public void setMacMd5(String macMd5) {
        this.macMd5 = macMd5;
    }

    public String getIfa() {
        return ifa;
    }

    public void setIfa(String ifa) {
        this.ifa = ifa;
    }

    public String getIfaMd5() {
        return ifaMd5;
    }

    public void setIfaMd5(String ifaMd5) {
        this.ifaMd5 = ifaMd5;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getActType() {
        return actType;
    }

    public void setActType(Integer actType) {
        this.actType = actType;
    }

    public Date getActTime() {
        return actTime;
    }

    public void setActTime(Date actTime) {
        this.actTime = actTime;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

	@Override
	public String prepareForDB(String clientid) {
		StringBuilder buf = new StringBuilder();
		buf.append(super.cleanString(this.appkey));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.cleanString(this.mac));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.cleanString(this.macMd5));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.cleanString(this.ifa));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.cleanString(this.ifaMd5));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.cleanString(this.uuid));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.actType!=null?this.actType.intValue():"");
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.actTime!=null?DateUtils.formatSeconds(this.actTime):"");
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.cleanString(this.pf));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.cleanString(this.ip));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.cleanString(this.userAgent));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.cleanString(this.ds));
		buf.append(TrackBase.FIELD_SEPARATOR);
		String jsonExtra = super.getExtra();
		buf.append(jsonExtra);
		return buf.toString();
	}

	@Override
	public String metric() {
		return "adtracking";
	}

	@Override
	public boolean checkBaseParmIsNull() {
		return false;
	}

	@Override
	public void processDate() {
	}
}