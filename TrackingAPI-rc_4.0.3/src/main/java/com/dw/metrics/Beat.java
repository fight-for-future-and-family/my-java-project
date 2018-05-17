package com.dw.metrics;

import org.apache.commons.lang.StringUtils;

import com.dw.services.Utils;
import com.hoolai.util.DateUtils;

/**
 * 心跳
 *
 */
public class Beat extends TrackBase {
	//userid、udid、clientid、kingdom、phylum、classfield、family、genus、value、ip、creative、date、time、extra
	private String kingdom;
	private String phylum;
	private String classfield;
	private String family;
	private String genus;
	private String value;
	private String ip;
	private String creative;
	private String date;
	private String time;

	public Beat() {
		init();
	}

	private void init() {
		this.kingdom = "";
		this.phylum = "";
		this.classfield = "";
		this.family = "";
		this.genus = "";
		this.value = "";
		this.date = "";
		this.time = "";
		this.ip = "";
		this.creative = "";
	}

	public String getKingdom() {
		return kingdom;
	}

	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}

	public String getPhylum() {
		return phylum;
	}

	public void setPhylum(String phylum) {
		this.phylum = phylum;
	}

	public String getClassfield() {
		return classfield;
	}

	public void setClassfield(String classfield) {
		this.classfield = classfield;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCreative() {
		return creative;
	}

	public void setCreative(String creative) {
		this.creative = creative;
	}

	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getUserId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.udid);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.checkAndConvertToInteger(clientid));
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
		buf.append(this.value);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.ip);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.creative);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.date);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.time);
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
		this.setDate(DateUtils.verifyDate(date, ds));
		this.setTime(DateUtils.verifyTime(time));
	}

	@Override
	public String metric() {
		return "beat";
	}

}
