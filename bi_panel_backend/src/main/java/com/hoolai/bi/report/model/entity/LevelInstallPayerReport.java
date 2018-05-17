package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class LevelInstallPayerReport {
    private Long id;

    private String snid;

    private String gameid;

    private String installDate;

    private Integer retentionDay;

    private Integer level;

    private Integer install;

    private Integer totalLevel;

    private Integer dauLevel;

    private Integer lostUsers;

    private Integer newPayers;

    private Double newPayment;

    private Date createTime;
    
    public LevelInstallPayerReport(){}

    public LevelInstallPayerReport(String snid, String gameid) {
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

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public Integer getRetentionDay() {
        return retentionDay;
    }

    public void setRetentionDay(Integer retentionDay) {
        this.retentionDay = retentionDay;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getInstall() {
        return install;
    }

    public void setInstall(Integer install) {
        this.install = install;
    }

    public Integer getTotalLevel() {
        return totalLevel;
    }

    public void setTotalLevel(Integer totalLevel) {
        this.totalLevel = totalLevel;
    }

    public Integer getDauLevel() {
        return dauLevel;
    }

    public void setDauLevel(Integer dauLevel) {
        this.dauLevel = dauLevel;
    }

    public Integer getLostUsers() {
        return lostUsers;
    }

    public void setLostUsers(Integer lostUsers) {
        this.lostUsers = lostUsers;
    }

    public Integer getNewPayers() {
        return newPayers;
    }

    public void setNewPayers(Integer newPayers) {
        this.newPayers = newPayers;
    }

    public Double getNewPayment() {
        return newPayment;
    }

    public void setNewPayment(Double newPayment) {
        this.newPayment = newPayment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}