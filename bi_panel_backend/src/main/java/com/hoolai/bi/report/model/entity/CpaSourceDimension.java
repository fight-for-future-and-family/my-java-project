package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class CpaSourceDimension {
    private Long id;

    private String snid;

    private String gameId;

    private String sourceCode;

    private String sourceName;

    private Double payRate;

    private Double payUserRate;

    private Double payTimesRate;

    private Long updateUserId;

    private Date updateTime;
    
    public CpaSourceDimension(){}

    public CpaSourceDimension(String snid, String gameId) {
		this.snid = snid;
		this.gameId = gameId;
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

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Double getPayRate() {
        return payRate;
    }

    public void setPayRate(Double payRate) {
        this.payRate = payRate;
    }

    public Double getPayUserRate() {
        return payUserRate;
    }

    public void setPayUserRate(Double payUserRate) {
        this.payUserRate = payUserRate;
    }

    public Double getPayTimesRate() {
        return payTimesRate;
    }

    public void setPayTimesRate(Double payTimesRate) {
        this.payTimesRate = payTimesRate;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}