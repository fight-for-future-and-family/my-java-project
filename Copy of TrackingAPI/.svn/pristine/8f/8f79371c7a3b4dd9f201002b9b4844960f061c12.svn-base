package com.dw.metrics;

import org.apache.commons.lang.StringUtils;

import com.dw.services.Utils;
import com.hoolai.util.DateUtils;

/**
 * 
 * 记录消息发送数据，包括Feed，Request，Wall to wall，Email等等 示例例如： Channel=request,
 * kingdom='gift', affiliate='mysterygift'
 * 
 */
@Deprecated
public class Message extends TrackBase {

	private String sendkey;

	private String channel;

	private String toUid; // 接受者ID

	private String status;

	private String kingdom;

	private String phylum;

	private String classfield;

	private String family;

	private String genus;

	private String messageDate;

	private String messageTime;

	public Message() {
		init();
	}

	private void init() {
		this.sendkey = "";
		this.channel = "";
		this.toUid = "";
		this.status = "ok";
		this.kingdom = "";
		this.phylum = "";
		this.classfield = "";
		this.family = "";
		this.genus = "";
	}

	public String getChannel() {
		return channel;
	}

	public String getKingdom() {
		return kingdom;
	}

	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
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

	public String getToUid() {
		return toUid;
	}

	public void setToUid(String toUid) {
		this.toUid = toUid;
	}

	public String getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}

	public String getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(String messageTime) {
		this.messageTime = messageTime;
	}


	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getUserId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.checkAndConvertToInteger(clientid));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.toUid);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.sendkey);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.channel);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.status);
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
		buf.append(this.messageDate);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.messageTime);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.getExtra());
		return buf.toString();
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
		this.messageDate = (DateUtils.verifyDate(messageDate));
		this.messageTime = (DateUtils.verifyTime(messageTime));
	}

	@Override
	public String metric() {
		return "message";
	}
}
