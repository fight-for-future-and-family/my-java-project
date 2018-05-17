package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.GameLtv;
import com.hoolai.bi.report.model.entity.UserRetentionClientLtv;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class UserRetentionClientLtvVO extends AbstractObjectVO<UserRetentionClientLtv> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Long gameId;
	private String indicators;
	private String beginDate;
	private String endDate;
	
	private double avgLtv;
	private double avgRetentionRate;
	
	private String searchValue;
	
	private GameLtv gameLtv;
	private String day;
	public UserRetentionClientLtvVO() {
		super();
		this.entity=new UserRetentionClientLtv();
	}

	public UserRetentionClientLtvVO(UserRetentionClientLtv entity) {
		super(entity);
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public String getIndicators() {
		return indicators;
	}

	public void setIndicators(String indicators) {
		this.indicators = indicators;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
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

	public void setDate(String beginDate, String endDate) {
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public double getAvgLtv() {
		return avgLtv;
	}

	public void setAvgLtv(double avgLtv) {
		this.avgLtv = avgLtv;
	}

	public double getAvgRetentionRate() {
		return avgRetentionRate;
	}

	public void setAvgRetentionRate(double avgRetentionRate) {
		this.avgRetentionRate = avgRetentionRate;
	}

	public GameLtv getGameLtv() {
		return gameLtv;
	}

	public void setGameLtv(GameLtv gameLtv) {
		this.gameLtv = gameLtv;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	

}
