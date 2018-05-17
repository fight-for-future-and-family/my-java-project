package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class SourceCpaDailyReport {
    private Long id;

    private String snid;

    private String gameid;

    private String source;

    private String currency;

    private Double paymentAmount;

    private Integer dau;

    private Integer install;

    private Integer dnu;
    
    private Integer newEquip;

    public Integer getNewEquip() {
		return newEquip;
	}

	public void setNewEquip(Integer newEquip) {
		this.newEquip = newEquip;
	}

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

    private Float d1;

    private Float d3;

    private Float d7;

    private Double acu;

    private Integer avgUserTime;

    private Double pcu;

    private String day;
    
    private Integer roleChoice;
    
    private Integer idfa;
    
    private Integer distinctIdfa;
    
    private Integer distinctIp;

    private String createTime;
    
    public SourceCpaDailyReport(){}
    
    public SourceCpaDailyReport(String snid, String gameid){
    	this();
    	this.snid = snid;
		this.gameid = gameid;
    }

    public SourceCpaDailyReport(String snid, String gameid, String source) {
		this(snid,gameid);
		this.source = source;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public Integer getDnu() {
        return dnu;
    }

    public void setDnu(Integer dnu) {
        this.dnu = dnu;
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

    public Float getD1() {
        return d1;
    }

    public void setD1(Float d1) {
        this.d1 = d1;
    }

    public Float getD3() {
        return d3;
    }

    public void setD3(Float d3) {
        this.d3 = d3;
    }

    public Float getD7() {
        return d7;
    }

    public void setD7(Float d7) {
        this.d7 = d7;
    }

    public Double getAcu() {
        return acu;
    }

    public void setAcu(Double acu) {
        this.acu = acu;
    }

    public Integer getAvgUserTime() {
        return avgUserTime;
    }

    public void setAvgUserTime(Integer avgUserTime) {
        this.avgUserTime = avgUserTime;
    }

    public Double getPcu() {
        return pcu;
    }

    public void setPcu(Double pcu) {
        this.pcu = pcu;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

	public Integer getRoleChoice() {
		return roleChoice;
	}

	public void setRoleChoice(Integer roleChoice) {
		this.roleChoice = roleChoice;
	}

	public Integer getIdfa() {
		return idfa;
	}

	public void setIdfa(Integer idfa) {
		this.idfa = idfa;
	}

	public Integer getDistinctIdfa() {
		return distinctIdfa;
	}

	public void setDistinctIdfa(Integer distinctIdfa) {
		this.distinctIdfa = distinctIdfa;
	}

	public Integer getDistinctIp() {
		return distinctIp;
	}

	public void setDistinctIp(Integer distinctIp) {
		this.distinctIp = distinctIp;
	}
}