package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class UserRetentionSourceLtv {
    private Long id;

    private String snid;

    private String gameid;

    private String source;

    private String installDay;

    private String statDay;

    private Integer install;

    private Integer retentionDay;

    private Double retentionRate;

    private Integer retentionNum;

    private Double totalAmount;

    private String currency;

    private Date createTime;

    public UserRetentionSourceLtv() {
    }
    public UserRetentionSourceLtv(String snid, String gameid) {
    	this.snid = snid;
    	this.gameid = gameid;
    }
    public UserRetentionSourceLtv(String snid, String gameid, String source) {
		this(snid, gameid);
		this.source = source;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}