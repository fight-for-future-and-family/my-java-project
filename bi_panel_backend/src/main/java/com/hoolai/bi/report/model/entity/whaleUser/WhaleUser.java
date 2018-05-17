package com.hoolai.bi.report.model.entity.whaleUser;

import org.apache.commons.lang3.StringUtils;

import com.hoolai.bi.report.util.DateUtils;

public class WhaleUser {
    private Long id;

    private String snid;

    private String gameid;

    private String ds;
    
    private String creative;

    private String userid;

    private String installDate;

    private String firstPayTime;

    private Double firstPayAmount;

    private String lastPayTime;

    private Double totalPayAmount;

    private String firstServer;

    private String lastServer;

    private Integer level;

    private String lastDauTime;

    private String clientid;

    private String roleName;

    private String roleid;

    private Integer vipLevel;

    public WhaleUser(String snid, String gameid) {
		this.snid = snid;
		this.gameid = gameid;
	}

	public WhaleUser() {
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
    	if(StringUtils.isEmpty(ds)){
    		this.ds = DateUtils.getYesterday();
    	}else{
    		this.ds = ds;
    	}
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public String getFirstPayTime() {
        return firstPayTime;
    }

    public void setFirstPayTime(String firstPayTime) {
        this.firstPayTime = firstPayTime;
    }

    public Double getFirstPayAmount() {
        return firstPayAmount;
    }

    public void setFirstPayAmount(Double firstPayAmount) {
        this.firstPayAmount = firstPayAmount;
    }

    public String getLastPayTime() {
        return lastPayTime;
    }

    public void setLastPayTime(String lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    public Double getTotalPayAmount() {
        return totalPayAmount;
    }

    public void setTotalPayAmount(Double totalPayAmount) {
        this.totalPayAmount = totalPayAmount;
    }

    public String getFirstServer() {
        return firstServer;
    }

    public void setFirstServer(String firstServer) {
        this.firstServer = firstServer;
    }

    public String getLastServer() {
        return lastServer;
    }

    public void setLastServer(String lastServer) {
        this.lastServer = lastServer;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLastDauTime() {
        return lastDauTime;
    }

    public void setLastDauTime(String lastDauTime) {
        this.lastDauTime = lastDauTime;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

	public String getCreative() {
		return creative;
	}

	public void setCreative(String creative) {
		this.creative = creative;
	}
}