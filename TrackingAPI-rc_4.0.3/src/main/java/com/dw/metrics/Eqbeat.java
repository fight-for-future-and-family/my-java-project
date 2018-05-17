package com.dw.metrics;

import com.hoolai.util.DateUtils;

public class Eqbeat extends TrackBase {
	
	private String creative;
	private String kingdom;
	private String phylum;
	private String classfield;
	private String phone;
	private String ratio;
	private String cpuload;
	private String phoneFreemem;
	private String phoneMaxmem;
	private String phoneFreedisk;
	private String phoneMaxdisk;
	private String eqbeatDate;
	private String eqbeatTime;

	public String getCreative() {
		return creative;
	}

	public void setCreative(String creative) {
		this.creative = creative;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public String getCpuload() {
		return cpuload;
	}

	public void setCpuload(String cpuload) {
		this.cpuload = cpuload;
	}

	public String getPhoneFreemem() {
		return phoneFreemem;
	}

	public void setPhoneFreemem(String phoneFreemem) {
		this.phoneFreemem = phoneFreemem;
	}

	public String getPhoneMaxmem() {
		return phoneMaxmem;
	}

	public void setPhoneMaxmem(String phoneMaxmem) {
		this.phoneMaxmem = phoneMaxmem;
	}

	public String getPhoneFreedisk() {
		return phoneFreedisk;
	}

	public void setPhoneFreedisk(String phoneFreedisk) {
		this.phoneFreedisk = phoneFreedisk;
	}

	public String getPhoneMaxdisk() {
		return phoneMaxdisk;
	}

	public void setPhoneMaxdisk(String phoneMaxdisk) {
		this.phoneMaxdisk = phoneMaxdisk;
	}

	public String getEqbeatDate() {
		return eqbeatDate;
	}

	public void setEqbeatDate(String eqbeatDate) {
		this.eqbeatDate = eqbeatDate;
	}

	public String getEqbeatTime() {
		return eqbeatTime;
	}

	public void setEqbeatTime(String eqbeatTime) {
		this.eqbeatTime = eqbeatTime;
	}

	@Override
	public String metric() {
		return "eqbeat";
	}

	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(super.udid);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.creative);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.kingdom);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.phylum);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.classfield);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.phone);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.ratio);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.cpuload);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.phoneFreemem);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.phoneMaxmem);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.phoneFreedisk);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.phoneMaxdisk);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.eqbeatDate);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.eqbeatTime);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.extra);
		return buf.toString();
	}

	@Override
	public boolean checkBaseParmIsNull() {
		return false;
	}

	@Override
	public void processDate() {
		this.setEqbeatDate(DateUtils.verifyDate(eqbeatDate, ds));
		this.setEqbeatTime(DateUtils.verifyTime(eqbeatTime));
	}
	
	

}
