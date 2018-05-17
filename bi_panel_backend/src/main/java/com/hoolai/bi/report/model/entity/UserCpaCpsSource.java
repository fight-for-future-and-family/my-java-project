package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class UserCpaCpsSource {
    private Long id;

    private Long userId;

    private Long sourceId;

    private Long allotUserId;

    private String allotUserName;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getAllotUserId() {
        return allotUserId;
    }

    public void setAllotUserId(Long allotUserId) {
        this.allotUserId = allotUserId;
    }

    public String getAllotUserName() {
        return allotUserName;
    }

    public void setAllotUserName(String allotUserName) {
        this.allotUserName = allotUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}