package com.dw.metrics;

import org.apache.commons.lang.StringUtils;

import com.dw.services.Utils;
import com.hoolai.util.DateUtils;

/**
 * 活跃镜像拉取
 * 
 */
public class Mirrorinfo extends TrackBase {

	private String roleName;
	private String level;
	private String sex;
	private String job;
	private String exp;
	private String vipLevel;
	private String yuanbao;
	private String bindYuanbao;
	private String coin;
	private String bindCoin;
	private String createTime;
	private String updateTime;
	private String fightValue;
	
	/**
	 * 获取游戏角色名称
	 * @return
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 设置游戏角色名称
	 * @param roleName
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 获取角色等级
	 * @return
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * 设置角色等级
	 * @param level
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * 获取游戏中性别
	 * @return
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 设置游戏中性别
	 * @param sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 获取游戏中职业
	 * @return
	 */
	public String getJob() {
		return job;
	}

	/**
	 * 设置游戏中职业
	 * @param job
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * 获取经验值
	 * @return
	 */
	public String getExp() {
		return exp;
	}

	/**
	 * 设置经验值
	 * @param exp
	 */
	public void setExp(String exp) {
		this.exp = exp;
	}

	/**
	 * 获取vip等级
	 * @return
	 */
	public String getVipLevel() {
		return vipLevel;
	}

	/**
	 * 设置vip等级
	 * @param vipLevel
	 */
	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}

	/**
	 * 获取元宝数
	 * @return
	 */
	public String getYuanbao() {
		return yuanbao;
	}

	/**
	 * 设置元宝数
	 * @param yuanbao
	 */
	public void setYuanbao(String yuanbao) {
		this.yuanbao = yuanbao;
	}

	/**
	 * 获取绑定元宝数
	 * @return
	 */
	public String getBindYuanbao() {
		return bindYuanbao;
	}

	/**
	 * 设置绑定元宝数
	 * @param bindYuanbao
	 */
	public void setBindYuanbao(String bindYuanbao) {
		this.bindYuanbao = bindYuanbao;
	}

	/**
	 * 获取金币数量
	 * @return
	 */
	public String getCoin() {
		return coin;
	}

	/**
	 * 设置金币数量
	 * @param coin
	 */
	public void setCoin(String coin) {
		this.coin = coin;
	}

	/**
	 * 获取绑定金币数
	 * @return
	 */
	public String getBindCoin() {
		return bindCoin;
	}

	/**
	 * 设置绑定金币数
	 * @param bindCoin
	 */
	public void setBindCoin(String bindCoin) {
		this.bindCoin = bindCoin;
	}

	/**
	 * 获取创建时间 格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间 格式：yyyy-MM-dd HH:mm:ss
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取最后更新时间 格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置最后更新时间 格式：yyyy-MM-dd HH:mm:ss
	 * @param createTime
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取战斗力
	 * @return
	 */
	public String getFightValue() {
		return fightValue;
	}

	/**
	 * 设置战斗力
	 * @param fightValue
	 */
	public void setFightValue(String fightValue) {
		this.fightValue = fightValue;
	}
	
	@Override
	public String prepareForDB(String clientid) {
		StringBuffer buf = new StringBuffer();
		buf.append(super.getUserId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.checkAndConvertToInteger(clientid));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.getUdid());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.getRoleId());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.getRoleName());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.getLevel());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.getSex());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.job));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(Utils.char_replace(this.exp));
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.getVipLevel());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.getYuanbao());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.getBindYuanbao());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.getCoin());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.getBindCoin());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.createTime);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.updateTime);
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.getFightValue());
		buf.append(TrackBase.FIELD_SEPARATOR);
		buf.append(this.getExtra());
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
			this.setCreateTime(DateUtils.verifyDateAndTime(createTime));
			this.setUpdateTime(DateUtils.verifyDateAndTime(updateTime));
	}

	@Override
	public String metric() {
		return "mirrorinfo";
	}
	
}
