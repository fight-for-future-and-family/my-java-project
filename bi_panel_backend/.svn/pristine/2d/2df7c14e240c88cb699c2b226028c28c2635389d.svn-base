package com.hoolai.bi.report.model.entity;

public class MonitorMetricsMsg {
	
	private String snid;
	
	private String gameid;
    
	private Double yesterdayNum;
	
	private Double todayNum;
	
	private Double lastHourNum;
	
	private Double rate;
	
	private Double lastHourRate;
	
	private String columnName;
	
    private String type;
    
    private String lastHourType;
    
    private String name;
    
    private boolean isEmail;
    
    private boolean isPhone;
    
	public MonitorMetricsMsg(String snid, String gameid) {
		this.snid = snid;
		this.gameid = gameid;
	}
	
	public MonitorMetricsMsg() {
		
	}
	
	public String msgStr(){
		StringBuilder msgStr = new StringBuilder();
		msgStr = new StringBuilder(this.getColumnName()+" 昨日数据："+this.getYesterdayNum()+"， 今日数据"+this.getTodayNum()
				+"，上小时数据："+this.getLastHourNum()
				+"，相比昨日"+this.getType()+(this.getRate()>0d?this.getRate():(0-this.getRate()))
				+"%，相比上小时"+this.getLastHourType()+(this.getLastHourRate()>0d?this.getLastHourRate():(0-this.getLastHourRate()))+"%\r\n");
		return msgStr.toString();
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

	public Double getYesterdayNum() {
		return yesterdayNum;
	}

	public void setYesterdayNum(Double yesterdayNum) {
		this.yesterdayNum = yesterdayNum;
	}

	public Double getTodayNum() {
		return todayNum;
	}

	public void setTodayNum(Double todayNum) {
		this.todayNum = todayNum;
	}

	public Double getLastHourNum() {
		return lastHourNum;
	}

	public void setLastHourNum(Double lastHourNum) {
		this.lastHourNum = lastHourNum;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getLastHourRate() {
		return lastHourRate;
	}

	public void setLastHourRate(Double lastHourRate) {
		this.lastHourRate = lastHourRate;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getLastHourType() {
		return lastHourType;
	}

	public void setLastHourType(String lastHourType) {
		this.lastHourType = lastHourType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
