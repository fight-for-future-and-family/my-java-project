package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class UserRetentionLtv {
    private Long id;

    private String snid;

    private String gameid;

    private String installDay;

    private String statDay;

    private Integer install;

    private Integer retentionDay;

    private Double retentionRate;

    private Integer retentionNum;

    private Double totalAmount;

    private Date createTime;

    private String day;
    
    public UserRetentionLtv() {
    }
    
    public UserRetentionLtv(String snid, String gameid) {
		this.snid = snid;
		this.gameid = gameid;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getInstallDay() {
        return installDay;
    }

    public void setInstallDay(String installDay) {
        this.installDay = installDay;
    }

    public String getStatDay() {
        return statDay;
    }

    public void setStatDay(String statDay) {
        this.statDay = statDay;
    }

    public Integer getInstall() {
        return install;
    }

    public void setInstall(Integer install) {
        this.install = install;
    }

    public Integer getRetentionDay() {
        return retentionDay;
    }

    public void setRetentionDay(Integer retentionDay) {
        this.retentionDay = retentionDay;
    }

    public Double getRetentionRate() {
        return retentionRate;
    }

    public void setRetentionRate(Double retentionRate) {
        this.retentionRate = retentionRate;
    }

    public Integer getRetentionNum() {
        return retentionNum;
    }

    public void setRetentionNum(Integer retentionNum) {
        this.retentionNum = retentionNum;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
    
}