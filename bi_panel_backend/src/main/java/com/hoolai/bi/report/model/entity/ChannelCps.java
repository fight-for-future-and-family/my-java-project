package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class ChannelCps {
    private Integer id;

    private String ds;

    private String snid;

    private String gameid;

    private String channelId;

    private String channelName;

    private Integer payUser;

    private Integer payTimes;

    private Double paymentAmount;

    private Date createTime;

    public ChannelCps(String snid, String gameid) {
		this.snid = snid;
		this.gameid = gameid;
	}

	public ChannelCps() {
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getPayUser() {
        return payUser;
    }

    public void setPayUser(Integer payUser) {
        this.payUser = payUser;
    }

    public Integer getPayTimes() {
        return payTimes;
    }

    public void setPayTimes(Integer payTimes) {
        this.payTimes = payTimes;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}