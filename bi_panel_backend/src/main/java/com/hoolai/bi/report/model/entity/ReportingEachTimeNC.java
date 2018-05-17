package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class ReportingEachTimeNC {
    private Integer id;

    private String snid;

    private String gameid;

    private String ds;

    private String times;

    private Integer dau;

    private Integer install;

    private Integer payUsers;

    private Integer payTimes;

    private Double amount;

    private Integer ispredict;

    private Date createTime;
    
    public ReportingEachTimeNC(){}

    public ReportingEachTimeNC(String snid, String gameid) {
		this.snid = snid;
		this.gameid = gameid;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public Integer getDau() {
        return dau;
    }

    public void setDau(Integer dau) {
        this.dau = dau;
    }

    public Integer getInstall() {
        return install;
    }

    public void setInstall(Integer install) {
        this.install = install;
    }

    public Integer getPayUsers() {
        return payUsers;
    }

    public void setPayUsers(Integer payUsers) {
        this.payUsers = payUsers;
    }

    public Integer getPayTimes() {
        return payTimes;
    }

    public void setPayTimes(Integer payTimes) {
        this.payTimes = payTimes;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getIspredict() {
		return ispredict;
	}

	public void setIspredict(Integer ispredict) {
		this.ispredict = ispredict;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}