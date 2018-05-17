package com.hoolai.bi.report.vo;

import java.util.Calendar;

import com.hoolai.bi.report.model.entity.RealTimeGame;
import com.hoolai.bi.report.util.DateUtils;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class RealTimeGameVo  extends AbstractObjectVO<RealTimeGame>{
	private String snid;
	
	private String gameid;

	private double dnu;
	
	private double dau;
	
	private double dau_total;
	
	private double payer;
	
	private double payer_total;
	
	private double total_amount;


	
	private String ds;
	
	private String start_time;
	
	private String finish_time;
	
	private String day;
	private String day2;
	
	private double equips;
	
	
	private String startdate;
	private String enddate;
	private String status;
	
	
	private String today;
	private String yesterday;
	private String lastMonthSToday;
	private String last7Day;
	
	
	private String yesterday_dnu;
	
	private String yesterday_dau;
	
	private String yesterday_equips;
	
	private String yesterday_total_amount;
	
	private String yesterday_payer;
	
	private String yesterday_dau_total;
	
	public RealTimeGameVo() {
		super();
		this.entity=new RealTimeGame();
		initDate();
	}

	public RealTimeGameVo(RealTimeGame entity) {
		super(entity);
		
	}

	public RealTimeGameVo(String snid,String gameid) {
		this.snid =  snid;
		this.gameid = gameid;
	}
	private void initDate(){
		Calendar calendar = Calendar.getInstance();
		
		this.today = DateUtils.getDateStr(calendar);
		this.yesterday = DateUtils.getYesterday();
		
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-7);
		this.last7Day = DateUtils.getDateStr(calendar);
		
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-23);
		this.lastMonthSToday = DateUtils.getDateStr(calendar);
	}
	
	
	public double getEquips() {
		return equips;
	}

	public String getDay2() {
		return day2;
	}

	public void setDay2(String day2) {
		this.day2 = day2;
	}

	public void setEquips(double equips) {
		this.equips = equips;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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

	public double getDau_total() {
		return dau_total;
	}

	public void setDau_total(double dau_total) {
		this.dau_total = dau_total;
	}

	public double getDnu() {
		return dnu;
	}

	public void setDnu(double dnu) {
		this.dnu = dnu;
	}

	public double getDau() {
		return dau;
	}

	public void setDau(double dau) {
		this.dau = dau;
	}

	public double getPayer() {
		return payer;
	}

	public void setPayer(double payer) {
		this.payer = payer;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}


	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(String finish_time) {
		this.finish_time = finish_time;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getYesterday() {
		return yesterday;
	}

	public void setYesterday(String yesterday) {
		this.yesterday = yesterday;
	}

	public String getLastMonthSToday() {
		return lastMonthSToday;
	}

	public void setLastMonthSToday(String lastMonthSToday) {
		this.lastMonthSToday = lastMonthSToday;
	}

	public String getLast7Day() {
		return last7Day;
	}

	public void setLast7Day(String last7Day) {
		this.last7Day = last7Day;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getYesterday_dnu() {
		return yesterday_dnu;
	}

	public void setYesterday_dnu(String yesterday_dnu) {
		this.yesterday_dnu = yesterday_dnu;
	}

	public String getYesterday_dau() {
		return yesterday_dau;
	}

	public void setYesterday_dau(String yesterday_dau) {
		this.yesterday_dau = yesterday_dau;
	}

	public String getYesterday_equips() {
		return yesterday_equips;
	}

	public void setYesterday_equips(String yesterday_equips) {
		this.yesterday_equips = yesterday_equips;
	}

	public String getYesterday_total_amount() {
		return yesterday_total_amount;
	}

	public void setYesterday_total_amount(String yesterday_total_amount) {
		this.yesterday_total_amount = yesterday_total_amount;
	}

	public String getYesterday_payer() {
		return yesterday_payer;
	}

	public void setYesterday_payer(String yesterday_payer) {
		this.yesterday_payer = yesterday_payer;
	}

	public String getYesterday_dau_total() {
		return yesterday_dau_total;
	}

	public void setYesterday_dau_total(String yesterday_dau_total) {
		this.yesterday_dau_total = yesterday_dau_total;
	}

	public double getPayer_total() {
		return payer_total;
	}

	public void setPayer_total(double payer_total) {
		this.payer_total = payer_total;
	}
	
	
}
