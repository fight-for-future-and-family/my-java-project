package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class HourDailyReport {
    private Long id;

    private String snid;

    private String gameid;

    private String day;

    private Integer hour;
    
    private Integer minute;
    
    private Double acu;

    private Integer avgUserTime;

    private Double pcu;

    private Integer dau;

    private Integer dnu;
    
    private Integer newEquip;

    private Integer roleChoice;

    private Double roleChoiceRate;

    private Integer loyalUser;

    private Double d1;

    private Double payRate;

    private Double arpu;

    private Double arppu;

    private Integer pu;

    private Double payment;

    private Integer paymentTimes;

    private Integer newPu;

    private Double newPayment;

    private Integer installPu;

    private Double installPayAmount;

    private Double installPayRate;

    private Date createTime;
    
    public HourDailyReport(){}

    public HourDailyReport(String snid, String gameid) {
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
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

	public Integer getDau() {
        return dau;
    }

    public Integer getNewEquip() {
		return newEquip;
	}

	public void setNewEquip(Integer newEquip) {
		this.newEquip = newEquip;
	}

	public void setDau(Integer dau) {
        this.dau = dau;
    }

    public Integer getDnu() {
        return dnu;
    }

    public void setDnu(Integer dnu) {
        this.dnu = dnu;
    }

    public Integer getRoleChoice() {
        return roleChoice;
    }

    public void setRoleChoice(Integer roleChoice) {
        this.roleChoice = roleChoice;
    }

    public Double getRoleChoiceRate() {
        return roleChoiceRate;
    }

    public void setRoleChoiceRate(Double roleChoiceRate) {
        this.roleChoiceRate = roleChoiceRate;
    }

    public Integer getLoyalUser() {
        return loyalUser;
    }

    public void setLoyalUser(Integer loyalUser) {
        this.loyalUser = loyalUser;
    }

    public Double getD1() {
        return d1;
    }

    public void setD1(Double d1) {
        this.d1 = d1;
    }

    public Double getPayRate() {
        return payRate;
    }

    public void setPayRate(Double payRate) {
        this.payRate = payRate;
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

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Integer getPaymentTimes() {
        return paymentTimes;
    }

    public void setPaymentTimes(Integer paymentTimes) {
        this.paymentTimes = paymentTimes;
    }

    public Integer getNewPu() {
        return newPu;
    }

    public void setNewPu(Integer newPu) {
        this.newPu = newPu;
    }

    public Double getNewPayment() {
        return newPayment;
    }

    public void setNewPayment(Double newPayment) {
        this.newPayment = newPayment;
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

    public Double getInstallPayRate() {
        return installPayRate;
    }

    public void setInstallPayRate(Double installPayRate) {
        this.installPayRate = installPayRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}