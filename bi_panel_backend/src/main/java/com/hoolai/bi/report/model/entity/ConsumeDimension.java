package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class ConsumeDimension {
    private Long id;

    private String snid;

    private String gameId;

    private String consumeCode;

    private String consumeName;

    private Long updateUserId;

    private Date updateTime;
    
    public ConsumeDimension(){}

    public ConsumeDimension(String snid2, String gameId2) {
		this.snid = snid2;
		this.gameId = gameId2;
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

    public String getConsumeCode() {
        return consumeCode;
    }

    public void setConsumeCode(String consumeCode) {
        this.consumeCode = consumeCode;
    }

    public String getConsumeName() {
        return consumeName;
    }

    public void setConsumeName(String consumeName) {
        this.consumeName = consumeName;
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