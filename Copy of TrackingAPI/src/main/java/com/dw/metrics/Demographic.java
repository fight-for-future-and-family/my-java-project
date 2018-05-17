package com.dw.metrics;

import org.apache.commons.lang.StringUtils;

import com.dw.services.Utils;
import com.hoolai.util.DateUtils;

/**
 * 
 * 通过调用平台API，记录游戏用户的真实信息。
 * 一般用户在游戏安装时报送且按照一定的时间间隔来更新信息。
 * 
 */
@Deprecated
public class Demographic extends TrackBase {

	/**
	 * 年龄
	 */
	private String age;

	/**
	 * 用户名
	 */
	private String first;

	/**
	 * 用户姓
	 */
	private String last;

	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * 生日
	 */
	private String birthday;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 所在的城市，或者县，或者村
	 */
	private String city;

	/**
	 * 所在的省，或者州
	 */
	private String state;

	/**
	 * 所在的国家
	 */
	private String country;

	/**
	 * 用户所处地区的语言
	 */
	private String locale;

	/**
	 * 用户所处地区时区
	 */
	private String timezone;

	/**
	 * IP地址
	 */
	private String ip;

	/**
	 * 用户头像地址
	 */
	private String picurl;

	/**
	 * 是否是vip用户
	 */
	private String isVip;

	/**
	 * vip的类型
	 */
	private String vipType;

	/**
	 * 记录的日期 yyyy-MM-dd
	 */
	private String demographicDate;

	/**
	 * 记录的时间 HH:mm:ss
	 */
	private String demographicTime;

	public Demographic() {
		init();
	}

	private void init() {
		this.age = "";
		this.birthday = "";
		this.city = "";
		this.country = "";
		this.email = "";
		this.first = "";
		this.gender = "";
		this.ip = "";
		this.picurl = "";
		this.isVip = "";
		this.vipType = "";
		this.name = "";
		this.timezone = "";
		this.locale = "";
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
	}

	public String getDemographicDate() {
		return demographicDate;
	}

	public void setDemographicDate(String demographicDate) {
		this.demographicDate = demographicDate;
	}

	public String getDemographicTime() {
		return demographicTime;
	}

	public void setDemographicTime(String demographicTime) {
		this.demographicTime = demographicTime;
	}

	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getUserId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.checkAndConvertToInteger(clientid));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.age));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.first));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.last));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.name));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.gender));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.email));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.city));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.state));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.country));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.locale));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.timezone));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.IP2Long(this.ip));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.picurl));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.isVip);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.vipType));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.demographicDate);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.demographicTime);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.getExtra());
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
		this.setDemographicDate(DateUtils.verifyDate(demographicDate));
		this.setDemographicTime(DateUtils.verifyTime(demographicTime));
	}

	@Override
	public String metric() {
		return "demographic";
	}
}
