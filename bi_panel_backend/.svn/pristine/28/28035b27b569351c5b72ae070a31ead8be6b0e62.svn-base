package com.hoolai.bi.report.vo;

import java.util.ArrayList;
import java.util.List;

import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class GamesVO extends AbstractObjectVO<Games>{
	
	private static final long serialVersionUID = -4862956885373318170L;
	
	private String gameManageUsers;
	
	private String onlineDate;
	
	private String status;
	
	private double yesterdayPayAmount;
	private List<Games> entities = new ArrayList<Games>();
	private String yesterday;
	
	public GamesVO() {
		super();
		this.entity=new Games();
	}

	public GamesVO(Games entity) {
		super(entity);
	}

	public String getGameManageUsers() {
		return gameManageUsers;
	}

	public void setGameManageUsers(String gameManageUsers) {
		this.gameManageUsers = gameManageUsers;
	}

	public String getYesterday() {
		return yesterday;
	}

	public void setYesterday(String yesterday) {
		this.yesterday = yesterday;
	}

	public String getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}

	public double getYesterdayPayAmount() {
		return yesterdayPayAmount;
	}

	public void setYesterdayPayAmount(double yesterdayPayAmount) {
		this.yesterdayPayAmount = yesterdayPayAmount;
	}

	public List<Games> getEntities() {
		return entities;
	}

	public void setEntities(List<Games> entities) {
		this.entities = entities;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
