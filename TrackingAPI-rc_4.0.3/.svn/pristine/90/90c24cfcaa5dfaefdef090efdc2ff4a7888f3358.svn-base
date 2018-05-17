package com.dw.metrics;

import org.apache.commons.lang.StringUtils;

import com.dw.services.Utils;
import com.hoolai.util.DateUtils;

public class AdGameInstall extends TrackBase {
	
	private String source;
	private String affiliate;
	private String creative;
	private String kingdom;
	private String classfield;
	private String family;
	private String simulator;
	private String clientVersion;
	private String adGameInstallDate;
	private String adGameInstallTime;
	private String phoneType;
	private String phoneVersion;
	
	public AdGameInstall(){
		this.init();
	}
	
	private void init(){
		this.source = "";
		this.affiliate = "";
		this.creative = "";
		this.kingdom = "";
		this.classfield = "";
		this.simulator = "";
		this.clientVersion = "";
	}

	@Override
	public String metric() {
		return "adGameInstall";
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAffiliate() {
		return affiliate;
	}

	public void setAffiliate(String affiliate) {
		this.affiliate = affiliate;
	}

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

	public String getSimulator() {
		return simulator;
	}

	public void setSimulator(String simulator) {
		this.simulator = simulator;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getAdGameInstallDate() {
		return adGameInstallDate;
	}

	public void setAdGameInstallDate(String adGameInstallDate) {
		this.adGameInstallDate = adGameInstallDate;
	}

	public String getAdGameInstallTime() {
		return adGameInstallTime;
	}

	public void setAdGameInstallTime(String adGameInstallTime) {
		this.adGameInstallTime = adGameInstallTime;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getPhoneVersion() {
		return phoneVersion;
	}

	public void setPhoneVersion(String phoneVersion) {
		this.phoneVersion = phoneVersion;
	}

	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getUdid());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.getUserId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.source));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.affiliate));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(Utils.CutDownCreative(this.creative)));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.checkAndConvertToInteger(clientid));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.kingdom));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.classfield));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.family));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.simulator));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.clientVersion);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.adGameInstallDate);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.adGameInstallTime);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.phoneType);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.phoneVersion);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.getExtra());
		
		return buf.toString();
	}

	@Override
	public boolean checkBaseParmIsNull() {
		if(StringUtils.isEmpty(udid) || StringUtils.isEmpty(family)){
			return true;
		}
		return false;
	}

	@Override
	public void processDate() {
		this.setAdGameInstallDate(DateUtils.verifyDate(adGameInstallDate, ds));
		this.setAdGameInstallTime(DateUtils.verifyTime(adGameInstallTime));
	}

}
