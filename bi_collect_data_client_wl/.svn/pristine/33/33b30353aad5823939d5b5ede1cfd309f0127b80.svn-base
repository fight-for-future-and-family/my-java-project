package com.hoolai.bi.collectdata.client.metrics;

import com.hoolai.bi.collectdata.client.util.Utils;


/**
 * 
 * 按Install来源记录注册信息
 * 
 * from_uid说明： 如果当前记录的用户是通过病毒渠道安装的，则可以取到From uid，也就是邀请者，或者病毒消息发送者的ID
 * 
 * @author baidongli@gmail.com
 *
 */
public class Install extends TrackBase {

	private String source = "";

	private String affiliate = "";

	private String creative = "";

	private String family = "";

	private String genus = "";

	private String from_uid = "";

	private String install_date;

	private String install_time;

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

	public String getFrom_uid() {
		return from_uid;
	}

	public void setFrom_uid(String from_uid) {
		this.from_uid = from_uid;
	}

	public String getInstall_date() {
		return install_date;
	}

	public void setInstall_date(String install_date) {
		this.install_date = install_date;
	}

	public String getInstall_time() {
		return install_time;
	}

	public void setInstall_time(String install_time) {
		this.install_time = install_time;
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

	@Override
	public String get_messages() {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getUserId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		//buf.append(TrackServices.client_id);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.source));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.affiliate));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(Utils.CutDownCreative(this.creative)));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.family));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.genus));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.from_uid);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.install_date);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.install_time);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.getExtra());
		return buf.toString();
	}

	@Override
	public String get_message_for_serverlet(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getUserId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(clientid);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.source));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.affiliate));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(Utils.CutDownCreative(this.creative)));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.family));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.genus));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.from_uid);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.install_date);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.install_time);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.getExtra());
		return buf.toString();
	}

	@Override
	public String get_event() {
		return "install";
	}

}
