package com.dw.metrics;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dw.services.Utils;
import com.hoolai.util.DateUtils;


public class Mirror extends TrackBase {

	private String userid;
	private String roleid;
	private String role_name;
	private String level;
	private String exp;
	private String createtime;
	private String updatetime;
	private String yb;
	private String extra;
	private String currency_1;
	private String currency_2;
	private String currency_3;
	private String status_1;
	private String status_2;
	private String status_3;
	private String status_4;
	private String status_5;
	private String status_6;
	private String status_7;
	private String status_8;
	private String status_9;
	private String status_10;
	private String item_1;
	private String item_2;
	private String item_3;
	private String item_4;
	private String item_5;
	private String mirror;
	
	public Mirror(){
		this.init();
	}
	
	private void init(){
		this.userid = "";
		this.roleid = "";
		this.role_name = "";
		this.level = "";
		this.exp = "";
		this.createtime = "";
		this.updatetime = "";
		this.yb = "";
		this.extra = "";
		this.currency_1 = "";
		this.currency_2 = "";
		this.currency_3 = "";
		this.status_1 = "";
		this.status_2 = "";
		this.status_3 = "";
		this.status_4 = "";
		this.status_5 = "";
		this.status_6 = "";
		this.status_7 = "";
		this.status_8 = "";
		this.status_9 = "";
		this.status_10 = "";
		this.item_1 = "";
		this.item_2 = "";
		this.item_3 = "";
		this.item_4 = "";
		this.item_5 = "";
		this.mirror = "";
}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getYb() {
		return yb;
	}

	public void setYb(String yb) {
		this.yb = yb;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getCurrency_1() {
		return currency_1;
	}

	public void setCurrency_1(String currency_1) {
		this.currency_1 = currency_1;
	}

	public String getCurrency_2() {
		return currency_2;
	}

	public void setCurrency_2(String currency_2) {
		this.currency_2 = currency_2;
	}

	public String getCurrency_3() {
		return currency_3;
	}

	public void setCurrency_3(String currency_3) {
		this.currency_3 = currency_3;
	}

	public String getStatus_1() {
		return status_1;
	}

	public void setStatus_1(String status_1) {
		this.status_1 = status_1;
	}

	public String getStatus_2() {
		return status_2;
	}

	public void setStatus_2(String status_2) {
		this.status_2 = status_2;
	}

	public String getStatus_3() {
		return status_3;
	}

	public void setStatus_3(String status_3) {
		this.status_3 = status_3;
	}

	public String getStatus_4() {
		return status_4;
	}

	public void setStatus_4(String status_4) {
		this.status_4 = status_4;
	}

	public String getStatus_5() {
		return status_5;
	}

	public void setStatus_5(String status_5) {
		this.status_5 = status_5;
	}

	public String getStatus_6() {
		return status_6;
	}

	public void setStatus_6(String status_6) {
		this.status_6 = status_6;
	}

	public String getStatus_7() {
		return status_7;
	}

	public void setStatus_7(String status_7) {
		this.status_7 = status_7;
	}

	public String getStatus_8() {
		return status_8;
	}

	public void setStatus_8(String status_8) {
		this.status_8 = status_8;
	}

	public String getStatus_9() {
		return status_9;
	}

	public void setStatus_9(String status_9) {
		this.status_9 = status_9;
	}

	public String getStatus_10() {
		return status_10;
	}

	public void setStatus_10(String status_10) {
		this.status_10 = status_10;
	}

	public String getItem_1() {
		return item_1;
	}

	public void setItem_1(String item_1) {
		this.item_1 = item_1;
	}

	public String getItem_2() {
		return item_2;
	}

	public void setItem_2(String item_2) {
		this.item_2 = item_2;
	}

	public String getItem_3() {
		return item_3;
	}

	public void setItem_3(String item_3) {
		this.item_3 = item_3;
	}

	public String getItem_4() {
		return item_4;
	}

	public void setItem_4(String item_4) {
		this.item_4 = item_4;
	}

	public String getItem_5() {
		return item_5;
	}

	public void setItem_5(String item_5) {
		this.item_5 = item_5;
	}

	public String getMirror() {
		return mirror;
	}

	public void setMirror(String mirror) {
		this.mirror = mirror;
	}

	
	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.userid);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.checkAndConvertToInteger(clientid));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.roleid);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.role_name);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.level);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.exp);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.createtime);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.updatetime);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.yb);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.extra);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.currency_1);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.currency_2);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.currency_3);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.status_1);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.status_2);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.status_3);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.status_4);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.status_5);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.status_6);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.status_7);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.status_8);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.status_9);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.status_10);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.item_1);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.item_2);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.item_3);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.item_4);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.item_5);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.mirror);
		return buf.toString();
	}

	@Override
	public boolean checkBaseParmIsNull() {
		
		return false;
	}

	@Override
	public void processDate() {
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		this.date = sdf.format(date);
//		sdf = new SimpleDateFormat("HH:mm:ss");
//		this.time = sdf.format(date);
		
		this.createtime = (DateUtils.verifyDateAndTime(createtime));
		this.updatetime = (DateUtils.verifyDateAndTime(updatetime));
	}

	@Override
	public String metric() {
		return "mirrorBasic";
	}
}
