package com.dw.metrics;

import com.dw.services.Utils;


/**
 * 
 * 记录用户的Session信息
 * 
 */
@Deprecated
public class Session extends TrackBase {

	private String startTimestamp;

	private String endTimestamp;

	private String kingdom;

	private String phylum;

	private String classfield;

	private String family;

	private String genus;

	public Session() {
		init();
	}

	private void init() {
		this.kingdom = "";
		this.phylum = "";
		this.classfield = "";
		this.family = "";
		this.genus = "";
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

	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getUserId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.checkAndConvertToInteger(clientid));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.startTimestamp);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.endTimestamp);
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
		buf.append(super.getExtra());
		return buf.toString();
	}

	@Override
	public boolean checkBaseParmIsNull() {
		return false;
	}

	@Override
	public String metric() {
		return "session";
	}

	@Override
	public void processDate() {
		
	}

}
