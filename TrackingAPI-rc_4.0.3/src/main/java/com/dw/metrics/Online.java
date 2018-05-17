package com.dw.metrics;

import org.apache.commons.lang.StringUtils;

import com.dw.services.Utils;
import com.hoolai.util.DateUtils;

/**
 * Online是记录玩家在线时长的类.
 *
 */
public class Online extends TrackBase {

	/**
	 * 网络类型(2g,3g,wifi等)
	 */
	private String affiliate;

	/**
	 * 用户等级
	 */
	private String userLevel;

	/**
	 * 渠道
	 */
	private String creative;

	/**
	 * 开始或结束(begin,end)
	 */
	private String phylum;

	/**
	 * 在线日期
	 */
	private String onlineDate;

	/**
	 * 具体时间(begin_time,end_time)
	 */
	private String onlineTime;

	/**
	 * 取到就报送，取不到就报-1
	 */
	private String ip;

	public Online() {
		init();
	}

	private void init() {
		this.affiliate = "";
		this.userLevel = "";
		this.creative = "";
		this.phylum = "";
		this.ip = "";
	}

	public String getAffiliate() {
		return affiliate;
	}

	public void setAffiliate(String affiliate) {
		this.affiliate = affiliate;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getCreative() {
		return creative;
	}

	public void setCreative(String creative) {
		this.creative = creative;
	}

	public String getPhylum() {
		return phylum;
	}

	public void setPhylum(String phylum) {
		this.phylum = phylum;
	}

	public String getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}

	public String getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getUserId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.checkAndConvertToInteger(clientid));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.affiliate));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.userLevel);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(Utils.CutDownCreative(this.creative)));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.phylum));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.onlineDate);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.onlineTime);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.ip);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.getExtra());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.udid);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.roleId);
		return buf.toString();
	}

	@Override
	public boolean checkBaseParmIsNull() {
		if(StringUtils.isEmpty(userId)){
			return true;
		}
		return false;
	}

	@Override
	public void processDate() {
		this.setOnlineDate(DateUtils.verifyDate(onlineDate, ds));
		this.setOnlineTime(DateUtils.verifyTime(onlineTime));
	}

	@Override
	public String metric() {
		return "online";
	}

	public String getDate() {
		return onlineDate;
	}

	public void setDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}

	public String getTime() {
		return onlineTime;
	}

	public void setTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

}
