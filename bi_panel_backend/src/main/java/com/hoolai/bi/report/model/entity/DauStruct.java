package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class DauStruct {
    private Long id;

    private String snid;

    private String gameid;

    private String dauDay;

    private String statDay;

    private Integer dau;

    private Integer structDay;

    private Double structRate;

    private Integer structNum;

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

    public String getDauDay() {
        return dauDay;
    }

    public void setDauDay(String dauDay) {
        this.dauDay = dauDay;
    }

    public String getStatDay() {
        return statDay;
    }

    public void setStatDay(String statDay) {
        this.statDay = statDay;
    }

    public Integer getDau() {
        return dau;
    }

    public void setDau(Integer dau) {
        this.dau = dau;
    }

    public Integer getStructDay() {
        return structDay;
    }

    public void setStructDay(Integer structDay) {
        this.structDay = structDay;
    }

    public Double getStructRate() {
        return structRate;
    }

    public void setStructRate(Double structRate) {
        this.structRate = structRate;
    }

    public Integer getStructNum() {
        return structNum;
    }

    public void setStructNum(Integer structNum) {
        this.structNum = structNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}