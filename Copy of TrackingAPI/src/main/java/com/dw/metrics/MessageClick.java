package com.dw.metrics;

import org.apache.commons.lang.StringUtils;

import com.dw.services.Utils;
import com.hoolai.util.DateUtils;

/**
 * 
 * 记录消息被点击的信息，消息类型包括Feed，Request，Wall to wall等等
 * 
 */
@Deprecated
public class MessageClick extends TrackBase {

	private String sendkey;

	private String channel;

	private String fromUid;

	private String status;

	private String kingdom;

	private String phylum;

	private String classfield;

	private String family;

	private String genus;

	private String sendDate;

	private String sendTime;

	private String clickDate;

	private String clickTime;

	public MessageClick() {
		init();
	}

	private void init() {
		this.sendkey = "";
		this.channel = "";
		this.fromUid = "";
		this.status = "ok";
		this.kingdom = "";
		this.phylum = "";
		this.classfield = "";
		this.family = "";
		this.genus = "";
		this.sendDate = "";
		this.sendTime = "";
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getGenus() {
		return genus;
	}

	public void setGenus(String genus) {
		this.genus = genus;
	}

	public String getSendkey() {
		return sendkey;
	}

	public void setSendkey(String sendkey) {
		this.sendkey = sendkey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKingdom() {
		return kingdom;
	}

	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}

	public String getFromUid() {
		return fromUid;
	}

	public void setFromUid(String fromUid) {
		this.fromUid = fromUid;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getClickDate() {
		return clickDate;
	}

	public void setClickDate(String clickDate) {
		this.clickDate = clickDate;
	}

	public String getClickTime() {
		return clickTime;
	}
	
	public void setClickTime(String clickTime) {
		this.clickTime = clickTime;
    }

	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getUserId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.checkAndConvertToInteger(clientid));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.sendkey);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.channel);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.kingdom);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.phylum);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.classfield);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.family);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.genus);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.fromUid);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.status);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.sendDate);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.sendTime);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.clickDate);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.clickTime);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.getExtra());
		return buf.toString();
	}

	public String getClassfield() {
		return classfield;
	}

	public void setClassfield(String classfield) {
		this.classfield = classfield;
	}

	public String getPhylum() {
		return phylum;
	}

	public void setPhylum(String phylum) {
		this.phylum = phylum;
	}

	@Override
	public boolean checkBaseParmIsNull() {
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(channel)){
			return true;
		}
		return false;
	}

	@Override
	public void processDate() {
		this.clickDate = (DateUtils.verifyDate(clickDate));
		this.clickTime = (DateUtils.verifyTime(clickTime));
		
		this.setSendDate(DateUtils.verifyDate(this.sendDate));
		this.setSendTime(DateUtils.verifyTime(this.sendTime));
	}

	@Override
	public String metric() {
		return "messageClick";
	}

}
