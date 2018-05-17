package com.dw.metrics;

import org.apache.commons.lang.StringUtils;

import com.dw.services.Utils;
import com.hoolai.util.DateUtils;

/**
 * 设备激活信息<br>
 * udid:安卓IMEI码/IOS IDFA(与gameinfo中userid对应）<br>
 * 注意：<br>
 * 	1.以上报送为前端报送,主要用途是检查前期流失点。<br>
 * 	2.phylum为步骤顺序号。<br>
 * 	3.玩家还没创号完成未知服务器id时，clientid=0。<br>
 * 
 *  扩展分类extra:"ram:内存,CPU:CPU型号"
 * 
 */
public class Equipment extends TrackBase {

	private String equipment;
	private String kingdom;
	private String phylum;
	private String classfield;
	private String family;
	private String genus;
	private String value;
	private String simulator;
	private String creative;
	private String clientVersion;
	private String ratio;
	private String phone;
	private String system;
	private String eqDate;
	private String eqTime;
	
	public Equipment(){
		this.init();
	}
	
	private void init(){
		this.value = "";
		this.kingdom = "";
		this.phylum = "";
		this.classfield = "";
		this.family = "";
		this.genus = "";
	}

	/**
	 * 激活前期的步骤比如打开应用、热更版本检查等等以early标志，删除应用以uninstall标志
	 * @return
	 */
	public String getEquipment() {
		return equipment;
	}

	/**
	 * 激活前期的步骤比如打开应用、热更版本检查等等以early标志，删除应用以uninstall标志
	 * @param equipment
	 */
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	/**
	 * 获取激活步骤名称
	 * @return
	 */
	public String getKingdom() {
		return kingdom;
	}

	/**
	 * 设置激活步骤名称,比如打开应用（open_app）、热更版本检查（update_check）等等
	 * @param kingdom
	 */
	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}

	/**
	 * 获取激活步骤顺序号
	 * @return
	 */
	public String getPhylum() {
		return phylum;
	}

	/**
	 * 设置激活步骤顺序号，比如打开应用（1）、热更版本检查（2）等等
	 * @param phylum
	 */
	public void setPhylum(String phylum) {
		this.phylum = phylum;
	}

	/**
	 * 获取激活步骤状态
	 * @return
	 */
	public String getClassfield() {
		return classfield;
	}

	/**
	 * 设置激活步骤状态，比如热更版本检查（start）、热更成功（[ok/fail]）等等
	 * @param classfield
	 */
	public void setClassfield(String classfield) {
		this.classfield = classfield;
	}

	public String getFamily() {
		return family;
	}

	/**
	 * 该字段因ad_tracking匹配ip渠道需要,填充了ip
	 */
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

	/**
	 * 获取模拟器版本
	 * @return
	 */
	public String getSimulator() {
		return simulator;
	}

	/**
	 * 设置模拟器版本(没有填0)
	 * @param simulator
	 */
	public void setSimulator(String simulator) {
		this.simulator = simulator;
	}

	/**
	 * 获取渠道ID
	 * @return
	 */
	public String getCreative() {
		return creative;
	}

	/**
	 * 设置渠道ID
	 * @param creative
	 */
	public void setCreative(String creative) {
		this.creative = creative;
	}

	/**
	 * 获取客户端版本
	 * @return
	 */
	public String getClientVersion() {
		return clientVersion;
	}

	/**
	 * 设置客户端版本
	 * @param clientVersion
	 */
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	/**
	 * 获取分辨率
	 * @return
	 */
	public String getRatio() {
		return ratio;
	}

	/**
	 * 设置分辨率
	 * @param ratio
	 */
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	/**
	 * 获取手机型号
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置手机型号
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取操作系统版本号(安卓为内核版本)
	 * @return
	 */
	public String getSystem() {
		return system;
	}

	/**
	 * 设置操作系统版本号(安卓为内核版本)
	 * @param system
	 */
	public void setSystem(String system) {
		this.system = system;
	}

	/**
	 * 获取激活日期 格式：yyyy-MM-dd
	 * @return
	 */
	public String getEqDate() {
		return eqDate;
	}

	/**
	 * 设置激活日期 格式：yyyy-MM-dd
	 * @param eqDate
	 */
	public void setEqDate(String eqDate) {
		this.eqDate = eqDate;
	}

	/**
	 * 获取激活日期 格式：HH:mm:ss
	 * @return
	 */
	public String getEqTime() {
		return eqTime;
	}

	/**
	 * 设置激活日期 格式：HH:mm:ss
	 * @param eqTime
	 */
	public void setEqTime(String eqTime) {
		this.eqTime = eqTime;
	}

	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getUdid());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.checkAndConvertToInteger(clientid));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.equipment));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.kingdom));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.phylum));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.classfield));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.family));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.genus));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.value));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.simulator));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(Utils.CutDownCreative(this.creative)));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.clientVersion);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.ratio));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.phone));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.system));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.eqDate);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.eqTime);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.getExtra());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.getUserId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(super.roleId);
		return buf.toString();
	}

	@Override
	public boolean checkBaseParmIsNull() {
//		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(equipment)){
		if(StringUtils.isEmpty(equipment)){
			return true;
		}
		return false;
	}

	@Override
	public void processDate() {
			this.setEqDate(DateUtils.verifyDate(eqDate, ds));
			this.setEqTime(DateUtils.verifyTime(eqTime));
	}

	@Override
	public String metric() {
		return "equipment";
	}
	
}
