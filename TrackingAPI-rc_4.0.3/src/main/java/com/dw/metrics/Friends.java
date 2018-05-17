package com.dw.metrics;

import org.apache.commons.lang.StringUtils;

import com.dw.services.Utils;
import com.hoolai.util.DateUtils;

/**
 * 
 * 记录跟增加、删除好友有关的信息，包括增加好友和删除好友 每个人可以有多条记录，每增加一个好友，添加一条记录
 * 
 *
 */
@Deprecated
public class Friends extends TrackBase {

	private String friendUid;
	private String kingdom; // 好友的类型，可以分类，比如是Neighbor Game friend
	private String phylum;
	private String classfield;
	private String friendDate; // 增加或者删除好友的时间
	private String friendTime;
	private int deleted; // 是否是删除好友

	public Friends() {
		init();
	}

	private void init() {
		this.friendUid = "";
		this.kingdom = "";
		this.phylum = "";
		this.classfield = "";
		this.friendDate = "";
		this.friendTime = "";
		this.deleted = 0;
	}

	public String getClassfield() {
		return classfield;
	}

	public void setClassfield(String classfield) {
		this.classfield = classfield;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
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

	public String getFriendUid() {
		return friendUid;
	}

	public void setFriendUid(String friendUid) {
		this.friendUid = friendUid;
	}

	public String getFriendDate() {
		return friendDate;
	}

	public void setFriendDate(String friendDate) {
		this.friendDate = friendDate;
	}

	public String getFriendTime() {
		return friendTime;
	}

	public void setFriendTime(String friendTime) {
		this.friendTime = friendTime;
	}

	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getUserId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.checkAndConvertToInteger(clientid));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.friendUid);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.kingdom);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.phylum);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.classfield);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.deleted);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.friendDate);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.friendTime);
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
		this.setFriendDate(DateUtils.verifyDate(friendDate, ds));
		this.setFriendTime(DateUtils.verifyTime(friendTime));
	}

	@Override
	public String metric() {
		return "friends";
	}
}
