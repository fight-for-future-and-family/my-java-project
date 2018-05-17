package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class SourceInstallRetention {
    private Long id;

    private String snid;

    private String gameid;

    private String source;

    private String installDay;

    private String statDay;

    private Integer intall;

    private Integer retentionDay;

    private Double retentionRate;

    private Integer retentionNum;

    private Date createTime;

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

    public Integer getIntall() {
        return intall;
    }

    public void setIntall(Integer intall) {
        this.intall = intall;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}