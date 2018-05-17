package com.hoolai.bi.report.model.entity;

public class SeriesAll {

	private String seriesid;

	private String seriesName;

	private Long dnu;

	private Long install;

	private double paymentAmount;

	private Long dau;

	private Long payer;

	private Long equipment;

	private double arpu;

	private double arppu;

	private String dataType;
	
	private String beginDate;
	
	private String endDate;
	
	private String ds;
	
	private String isAll;
	
	public SeriesAll() {
		init();
	}
	
	private void init(){
		setDataType(" ");
	}

	public String getSeriesid() {
		return seriesid;
	}

	public void setSeriesid(String seriesid) {
		this.seriesid = seriesid;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public Long getDnu() {
		return dnu;
	}

	public void setDnu(Long dnu) {
		this.dnu = dnu;
	}

	public Long getInstall() {
		return install;
	}

	public void setInstall(Long install) {
		this.install = install;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Long getDau() {
		return dau;
	}

	public void setDau(Long dau) {
		this.dau = dau;
	}

	public Long getPayer() {
		return payer;
	}

	public void setPayer(Long payer) {
		this.payer = payer;
	}

	public Long getEquipment() {
		return equipment;
	}

	public void setEquipment(Long equipment) {
		this.equipment = equipment;
	}

	public double getArpu() {
		return arpu;
	}

	public void setArpu(double arpu) {
		this.arpu = arpu;
	}

	public double getArppu() {
		return arppu;
	}

	public void setArppu(double arppu) {
		this.arppu = arppu;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getIsAll() {
		return isAll;
	}

	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}
	
}
