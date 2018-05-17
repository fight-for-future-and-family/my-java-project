package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class WeekReport {
    private Long id;

    private String snid;

    private String gameid;

    private String currency;

    private Double paymentAmount;

    private Integer wau;

    private Integer install;

    private Integer wnu;

    private Double arpu;

    private Double arppu;

    private Integer pu;

    private Integer paymentCnt;

    private Float payRate;

    private Integer rollUsers;

    private Integer rollPayUsers;

    private Double rollAmount;

    private Integer newPu;

    private Double newPayAmount;

    private Integer installPu;

    private Double installPayAmount;

    private String week;

    private Date createTime;

    public WeekReport(){}
    
    public WeekReport(String snid, String gameid) {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Integer getWau() {
        return wau;
    }

    public void setWau(Integer wau) {
        this.wau = wau;
    }

    public Integer getInstall() {
        return install;
    }

    public void setInstall(Integer install) {
        this.install = install;
    }

    public Integer getWnu() {
        return wnu;
    }

    public void setWnu(Integer wnu) {
        this.wnu = wnu;
    }

    public Double getArpu() {
        return arpu;
    }

    public void setArpu(Double arpu) {
        this.arpu = arpu;
    }

    public Double getArppu() {
        return arppu;
    }

    public void setArppu(Double arppu) {
        this.arppu = arppu;
    }

    public Integer getPu() {
        return pu;
    }

    public void setPu(Integer pu) {
        this.pu = pu;
    }

    public Integer getPaymentCnt() {
        return paymentCnt;
    }

    public void setPaymentCnt(Integer paymentCnt) {
        this.paymentCnt = paymentCnt;
    }

    public Float getPayRate() {
        return payRate;
    }

    public void setPayRate(Float payRate) {
        this.payRate = payRate;
    }

    public Integer getRollUsers() {
        return rollUsers;
    }

    public void setRollUsers(Integer rollUsers) {
        this.rollUsers = rollUsers;
    }

    public Integer getRollPayUsers() {
        return rollPayUsers;
    }

    public void setRollPayUsers(Integer rollPayUsers) {
        this.rollPayUsers = rollPayUsers;
    }

    public Double getRollAmount() {
        return rollAmount;
    }

    public void setRollAmount(Double rollAmount) {
        this.rollAmount = rollAmount;
    }

    public Integer getNewPu() {
        return newPu;
    }

    public void setNewPu(Integer newPu) {
        this.newPu = newPu;
    }

    public Double getNewPayAmount() {
        return newPayAmount;
    }

    public void setNewPayAmount(Double newPayAmount) {
        this.newPayAmount = newPayAmount;
    }

    public Integer getInstallPu() {
        return installPu;
    }

    public void setInstallPu(Integer installPu) {
        this.installPu = installPu;
    }

    public Double getInstallPayAmount() {
        return installPayAmount;
    }

    public void setInstallPayAmount(Double installPayAmount) {
        this.installPayAmount = installPayAmount;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}