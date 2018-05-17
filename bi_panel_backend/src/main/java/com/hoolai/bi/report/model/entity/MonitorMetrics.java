package com.hoolai.bi.report.model.entity;


public class MonitorMetrics {
	
	private Long id;

	private String snid;

    private String gameid;
    
    private String columnName;
    
    private Double lowerLimit;
    
    private Double topLimit;
    
    private boolean isEmail;
    
    private boolean isPhone;
    
    private Double rate;
    
    private String type;
    
	public MonitorMetrics(String snid, String gameid) {
		this.snid = snid;
		this.gameid = gameid;
	}
	
	public MonitorMetrics() {
		
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

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Double getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(Double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public Double getTopLimit() {
		return topLimit;
	}

	public void setTopLimit(Double topLimit) {
		this.topLimit = topLimit;
	}

	public boolean getIsEmail() {
		return isEmail;
	}

	public void setIsEmail(boolean isEmail) {
		this.isEmail = isEmail;
	}

	public boolean getIsPhone() {
		return isPhone;
	}

	public void setIsPhone(boolean isPhone) {
		this.isPhone = isPhone;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}
