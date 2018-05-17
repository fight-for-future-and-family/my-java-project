package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class SummaryGDT {
	
	private String date;

    private Long id;

    private String snid;

    private String gameid;

    private Long exposure;

    private Long clickCount;

    private Double clickRate;

    private Long install;

    private Double conversionRate;

    private Double allCost;

    private Double d0Cost;

    private Double cpc;

    private Double cpa;

    private Date createTime;
    
    public SummaryGDT(){}
    
    public SummaryGDT(String snid, String gameid) {
		this.snid = snid;
		this.gameid = gameid;
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

    public Long getExposure() {
        return exposure;
    }

    public void setExposure(Long exposure) {
        this.exposure = exposure;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Double getClickRate() {
        return clickRate;
    }

    public void setClickRate(Double clickRate) {
        this.clickRate = clickRate;
    }

    public Long getInstall() {
        return install;
    }

    public void setInstall(Long install) {
        this.install = install;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public Double getAllCost() {
        return allCost;
    }

    public void setAllCost(Double allCost) {
        this.allCost = allCost;
    }

    public Double getD0Cost() {
        return d0Cost;
    }

    public void setD0Cost(Double d0Cost) {
        this.d0Cost = d0Cost;
    }

    public Double getCpc() {
        return cpc;
    }

    public void setCpc(Double cpc) {
        this.cpc = cpc;
    }

    public Double getCpa() {
        return cpa;
    }

    public void setCpa(Double cpa) {
        this.cpa = cpa;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}