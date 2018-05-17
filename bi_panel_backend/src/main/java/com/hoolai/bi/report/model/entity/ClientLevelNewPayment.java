package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class ClientLevelNewPayment {
    private Long id;

    private String snid;

    private String gameid;

    private String ds;

    private Integer clientid;

    private Integer userLevel;

    private Integer newPayers;

    private Double amount;

    private Date createTime;

    public ClientLevelNewPayment() {
    }
    
    public ClientLevelNewPayment(String snid, String gameid,int clientid) {
		this.snid = snid;
		this.gameid = gameid;
		this.clientid = clientid;
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

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public Integer getClientid() {
        return clientid;
    }

    public void setClientid(Integer clientid) {
        this.clientid = clientid;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Integer getNewPayers() {
        return newPayers;
    }

    public void setNewPayers(Integer newPayers) {
        this.newPayers = newPayers;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}