package com.dw.metrics;

import org.apache.commons.lang.StringUtils;

import com.dw.services.Utils;
import com.hoolai.util.DateUtils;

/** 
 * <b>类说明：</b>
 * <blockquote>
 *   记录游戏异常日志信息
 * </blockquote> 
 * @author jiangqm E-mail: jiangqiming@jiangqiming.cn
 * @version 创建时间：2016年8月9日 下午3:52:47  
 */

public class Bugly extends TrackBase{

	
	private String language;
	private String buglyType;
	private String buglyMessage;
	private String buglyStack;
	private String ip;
	private String clientVersion;
	private String creative;
	private String phone;
	private String system;
	private String buglyDate;
	private String buglyTime;
	
	
	/**
	 * 程序语言
	 * @return
	 */
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * 错误类型
	 *（eg：Java.lang.RuntimeException）
	 * @return
	 */
	public String getBuglyType() {
		return buglyType;
	}

	public void setBuglyType(String buglyType) {
		this.buglyType = buglyType;
	}

	
	/**
	 * 具体错误信息 (自定义信息)
	 * @return
	 */
	public String getBuglyMessage() {
		return buglyMessage;
	}

	public void setBuglyMessage(String buglyMessage) {
		this.buglyMessage = buglyMessage;
	}

	
	/**
	 * 详细错误堆栈
	 * @return
	 */
	public String getBuglyStack() {
		return buglyStack;
	}

	public void setBuglyStack(String buglyStack) {
		this.buglyStack = buglyStack;
	}

	
	/**
	 * IP地址
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
	/**
	 * 客户端版本号
	 * @return
	 */
	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	
	/**
	 * 渠道包id，服务端报送为空
	 * @return
	 */
	public String getCreative() {
		return creative;
	}

	public void setCreative(String creative) {
		this.creative = creative;
	}

	/**
	 * 手机型号
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 操作系统版本号(安卓为内核版本)
	 * @return
	 */
	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getBuglyDate() {
		return buglyDate;
	}

	public void setBuglyDate(String buglyDate) {
		this.buglyDate = buglyDate;
	}

	public String getBuglyTime() {
		return buglyTime;
	}

	public void setBuglyTime(String buglyTime) {
		this.buglyTime = buglyTime;
	}

	@Override
	public String metric() {
		return "bugly";
	}

	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getUserId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.checkAndConvertToInteger(clientid));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.udid);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.language));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.buglyType));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.buglyMessage));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.buglyStack));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.ip);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.clientVersion));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(Utils.CutDownCreative(this.creative)));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.phone));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.system));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.buglyDate);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.buglyTime);
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
		this.setBuglyDate(DateUtils.verifyDate(buglyDate, ds));
		this.setBuglyTime(DateUtils.verifyTime(buglyTime));
		
	}
	
	@Override
	public void processIp(String ip) {
		if(StringUtils.isEmpty(this.ip)){
			this.setIp(ip);
		}
		
	}

}
