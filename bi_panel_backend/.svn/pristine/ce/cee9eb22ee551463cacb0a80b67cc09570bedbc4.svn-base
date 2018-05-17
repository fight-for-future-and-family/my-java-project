package com.hoolai.bi.report.model.entity.mongo;

import com.hoolai.bi.mongo.SimpleEntity;
import com.hoolai.bi.mongo.annotations.Entity;
import com.hoolai.bi.mongo.annotations.SplitType;

/**
 * 测试mongo流程使用
 * @date 2017年8月14日 下午2:42:39
 * @author 邹友
 *
 */
@Entity(split = SplitType.STRING)
public class SourceDaily extends SimpleEntity{
	
	private static final long serialVersionUID = -4926656477344355018L;
	
	private String day; //日期
	
	private String snid; //广告平台id
	
	private String gameid; //游戏id
	
	private String source; //渠道来源
	
	private Integer actvies; //激活数
	
	private Integer registers; //注册数

	
	public SourceDaily() {
		super();
	}

	public SourceDaily(String id, String day, String snid, String gameid, String source, Integer actvies,
			Integer registers) {
		this.id = id;
		this.day = day;
		this.snid = snid;
		this.gameid = gameid;
		this.source = source;
		this.actvies = actvies;
		this.registers = registers;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getSnid() {
		return snid;
	}

	public void setSnid(String snid) {
		this.snid = snid;
	}

	public String getGameid() {
		return gameid;
	}

	public void setGameid(String gameid) {
		this.gameid = gameid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getActvies() {
		return actvies;
	}

	public void setActvies(Integer actvies) {
		this.actvies = actvies;
	}

	public Integer getRegisters() {
		return registers;
	}

	public void setRegisters(Integer registers) {
		this.registers = registers;
	}
	
	
}
