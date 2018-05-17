package com.hoolai.manage.model;

import java.awt.List;
import java.util.Date;

public class CustomReport {
    private Long id;

    private Integer snid;

    private Integer gameid;

   private Long templateId; 

   public CustomReport(){}
    public CustomReport(Long id, Integer snid, Integer gameid) {
	this.templateId = id;
	this.snid = snid;
	this.gameid = gameid;
}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSnid() {
        return snid;
    }

    public void setSnid(Integer snid) {
        this.snid = snid;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

}