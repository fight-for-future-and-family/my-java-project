package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class RealtimeSourceReport {
    private Long id;

    private String snid;

    private String gameid;

    private String ds;

    private String createive;

    private Long install;
    
    private Long newEquip;

    private Long installPayer;

    private Double installPayment;

    private Long idfa;

    private Long distinctIdfa;

    private Long ip;

    private Long distinctIp;

    private Date createTime;
    
    private String adPlace;

    public RealtimeSourceReport(){}
    
    public RealtimeSourceReport(String snid, String gameid) {
		this.snid = snid;
		this.gameid = gameid;
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

    public String getCreateive() {
        return createive;
    }

    public void setCreateive(String createive) {
        this.createive = createive;
    }

    public Long getInstall() {
        return install;
    }

    public void setInstall(Long install) {
        this.install = install;
    }

    public Long getNewEquip() {
		return newEquip;
	}

	public void setNewEquip(Long newEquip) {
		this.newEquip = newEquip;
	}

	public Long getInstallPayer() {
        return installPayer;
    }

    public void setInstallPayer(Long installPayer) {
        this.installPayer = installPayer;
    }

    public Double getInstallPayment() {
        return installPayment;
    }

    public void setInstallPayment(Double installPayment) {
        this.installPayment = installPayment;
    }

    public Long getIdfa() {
        return idfa;
    }

    public void setIdfa(Long idfa) {
        this.idfa = idfa;
    }

    public Long getDistinctIdfa() {
        return distinctIdfa;
    }

    public void setDistinctIdfa(Long distinctIdfa) {
        this.distinctIdfa = distinctIdfa;
    }

    public Long getIp() {
        return ip;
    }

    public void setIp(Long ip) {
        this.ip = ip;
    }

    public Long getDistinctIp() {
        return distinctIp;
    }

    public void setDistinctIp(Long distinctIp) {
        this.distinctIp = distinctIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getAdPlace() {
		return adPlace;
	}

	public void setAdPlace(String adPlace) {
		this.adPlace = adPlace;
	}
}