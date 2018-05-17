package com.hoolai.bi.report.vo;

import java.util.Calendar;

import com.hoolai.bi.report.model.entity.RealTimeGameClient;
import com.hoolai.bi.report.util.DateUtils;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class RealTimeGameClientVo  extends AbstractObjectVO<RealTimeGameClient>{
	private String snid;
	
	private String gameid;

	private String clientid;
	
	private double dnu;
	
	private double dau;
	
	private double payer;
	
	private double total_amount;

	
	private String ds;
	
	private String start_time;
	private String finish_time;
	
	
	private double dau_total;
	
	private double payer_total;
	
	private String day;
	
	
	private String searchValue;
	private String orderCol;
	private String orderType;
	private String orderValue;
	
	
	private String today;
	private String yesterday;
	private String lastMonthSToday;
	private String last7Day;
	
	
	private double yesterday_dnu;
	
	private double yesterday_dau;
	
	private double yesterday_equips;
	
	private double yesterday_total_amount;
	
	private double yesterday_payer;
	
	private double yesterday_dau_total;
	
	private double  yesterday_clientid;
	
	private void initDate(){
		Calendar calendar = Calendar.getInstance();
		
		this.today = DateUtils.getDateStr(calendar);
		this.yesterday = DateUtils.getYesterday();
		
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-7);
		this.last7Day = DateUtils.getDateStr(calendar);
		
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-23);
		this.lastMonthSToday = DateUtils.getDateStr(calendar);
	}
	
	
	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getOrderCol() {
		return orderCol;
	}

	public void setOrderCol(String orderCol) {
		this.orderCol = orderCol;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
	}

	public RealTimeGameClientVo() {
		super();
		this.entity=new RealTimeGameClient();
		initDate();
	}

	public RealTimeGameClientVo(RealTimeGameClient entity) {
		super(entity);
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

	
	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public double getDau_total() {
		return dau_total;
	}

	public void setDau_total(double dau_total) {
		this.dau_total = dau_total;
	}

	public double getPayer_total() {
		return payer_total;
	}

	public void setPayer_total(double payer_total) {
		this.payer_total = payer_total;
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

	public double getYesterday_dnu() {
		return yesterday_dnu;
	}

	public void setYesterday_dnu(double yesterday_dnu) {
		this.yesterday_dnu = yesterday_dnu;
	}

	public double getYesterday_dau() {
		return yesterday_dau;
	}

	public void setYesterday_dau(double yesterday_dau) {
		this.yesterday_dau = yesterday_dau;
	}

	public double getYesterday_equips() {
		return yesterday_equips;
	}

	public void setYesterday_equips(double yesterday_equips) {
		this.yesterday_equips = yesterday_equips;
	}

	public double getYesterday_total_amount() {
		return yesterday_total_amount;
	}

	public void setYesterday_total_amount(double yesterday_total_amount) {
		this.yesterday_total_amount = yesterday_total_amount;
	}

	public double getYesterday_payer() {
		return yesterday_payer;
	}

	public void setYesterday_payer(double yesterday_payer) {
		this.yesterday_payer = yesterday_payer;
	}

	public double getYesterday_dau_total() {
		return yesterday_dau_total;
	}

	public void setYesterday_dau_total(double yesterday_dau_total) {
		this.yesterday_dau_total = yesterday_dau_total;
	}

	public double getYesterday_clientid() {
		return yesterday_clientid;
	}

	public void setYesterday_clientid(double yesterday_clientid) {
		this.yesterday_clientid = yesterday_clientid;
	}


}
