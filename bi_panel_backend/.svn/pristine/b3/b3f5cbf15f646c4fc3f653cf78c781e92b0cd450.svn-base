package com.hoolai.bi.report.model.entity;

public class GamePrediction {

	private String snid;
	private String gameid;
	private Double avgDnu;
	private Double avgDau;
	private Double avgOldUser;
	private Double avgD1;
	private Double avgPayment;
	private Double sumPayment;
	private Double avgArpu;
	private Double avgArppu;
	private Double avgPayRate;
	private Double avgOldUserLossRate;
	
	public GamePrediction(){}
	
	public GamePrediction(String snid, String gameid) {
		this.snid = snid;
		this.gameid = gameid;
	}
	public Double getAvgD1() {
		return avgD1;
	}
	public void setAvgD1(Double avgD1) {
		this.avgD1 = avgD1;
	}
	public Double getAvgPayment() {
		return avgPayment;
	}
	public void setAvgPayment(Double avgPayment) {
		this.avgPayment = avgPayment;
	}
	public Double getSumPayment() {
		return sumPayment;
	}
	public void setSumPayment(Double sumPayment) {
		this.sumPayment = sumPayment;
	}
	public Double getAvgArpu() {
		return avgArpu;
	}
	public void setAvgArpu(Double avgArpu) {
		this.avgArpu = avgArpu;
	}
	public Double getAvgOldUserLossRate() {
		return avgOldUserLossRate;
	}

	public void setAvgOldUserLossRate(Double avgOldUserLossRate) {
		this.avgOldUserLossRate = avgOldUserLossRate;
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

	public Double getAvgArppu() {
		return avgArppu;
	}

	public void setAvgArppu(Double avgArppu) {
		this.avgArppu = avgArppu;
	}

	public Double getAvgPayRate() {
		return avgPayRate;
	}

	public void setAvgPayRate(Double avgPayRate) {
		this.avgPayRate = avgPayRate;
	}

	public Double getAvgDnu() {
		return avgDnu;
	}

	public void setAvgDnu(Double avgDnu) {
		this.avgDnu = avgDnu;
	}

	public Double getAvgDau() {
		return avgDau;
	}

	public void setAvgDau(Double avgDau) {
		this.avgDau = avgDau;
	}

	public Double getAvgOldUser() {
		return avgOldUser;
	}

	public void setAvgOldUser(Double avgOldUser) {
		this.avgOldUser = avgOldUser;
	}

	public void setup(GamePrediction avgGamePrediction,GamePrediction avgRetentionGamePrediction,GamePrediction sumGamePrediction) {
		if(avgGamePrediction != null){
			this.avgDnu = avgGamePrediction.getAvgDnu();
			this.avgDau = avgGamePrediction.getAvgDau();
			this.avgOldUser = avgGamePrediction.getAvgOldUser();
			this.avgPayment = avgGamePrediction.getAvgPayment();
			this.avgArpu = avgGamePrediction.getAvgArpu();
			this.avgArppu = avgGamePrediction.getAvgArppu();
			this.avgPayRate = avgGamePrediction.getAvgPayRate();
		}
		if(avgRetentionGamePrediction != null){
			this.avgD1 = avgRetentionGamePrediction.getAvgD1();
			this.avgOldUserLossRate = avgRetentionGamePrediction.getAvgOldUserLossRate();
		}
		if(sumGamePrediction != null){
			this.sumPayment = sumGamePrediction.getSumPayment();
		}
	}
	
	
}
