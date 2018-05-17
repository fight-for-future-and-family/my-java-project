package com.hoolai.bi.report.vo;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

import com.hoolai.bi.report.model.entity.whaleUser.WhaleUser;
import com.hoolai.bi.report.util.DateUtils;
import com.hoolai.dao.pagination.AbstractObjectVO;
public class WhaleUserVO extends AbstractObjectVO<WhaleUser> {

	private static final long serialVersionUID = 8867277237698758218L;
	
	private String searchValue;
	private String orderValue;
	private String orderCol;
	private String orderType;
	
	private String beginDate;
	private String endDate;
	private String beginDauDate;
	private String endDauDate;
	private Double beginPay;
	private Double endPay;
	private String indicators;
	private String today;
	
	private String queryType = "limit";
	
	public WhaleUserVO() {
		super();
		this.entity=new WhaleUser();
	}

	public WhaleUserVO(WhaleUser entity) {
		super(entity);
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

	public Double getBeginPay() {
		return beginPay;
	}

	public void setBeginPay(Double beginPay) {
		this.beginPay = beginPay;
	}

	public Double getEndPay() {
		return endPay;
	}

	public void setEndPay(Double endPay) {
		this.endPay = endPay;
	}

	public String getIndicators() {
		return indicators;
	}

	public String getBeginDauDate() {
		return beginDauDate;
	}

	public void setBeginDauDate(String beginDauDate) {
		this.beginDauDate = beginDauDate;
	}

	public String getEndDauDate() {
		return endDauDate;
	}

	public void setEndDauDate(String endDauDate) {
		this.endDauDate = endDauDate;
	}

	public void setIndicators(String indicators) {
		if(!StringUtils.isEmpty(indicators)){
			this.indicators = indicators;
			if("wastage".equals(indicators)){
				this.today = DateUtils.getDateStr(Calendar.getInstance());
			}
		}
	}

	public String getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
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

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public void setDate(String beginDate, String endDate) {
		if(!StringUtils.isEmpty(beginDate)){
			this.beginDate = beginDate;
		}
		if(!StringUtils.isEmpty(endDate)){
			this.endDate = endDate;
		}
	}

	public void setPayRange(String beginPay, String endPay) {
		if(!StringUtils.isEmpty(beginPay)){
			this.beginPay = Double.valueOf(beginPay);
		}
		if(!StringUtils.isEmpty(endPay)){
			this.endPay = Double.valueOf(endPay);
		}
	}
	
	public static void main(String[] args) {
		String a="INSERT INTO `bi_report_db`.`whale_user` (`snid`, `gameid`, `userid`, `installDate`, `firstPayTime`, `firstPayAmount`, `lastPayTime`, `totalPayAmount`, `firstServer`, `lastServer`, `level`, `lastDauTime`) VALUES ('2', '20', ";
		for(int i=0;i<30;i++){
			String c = a;
			c += "'"+(5657567658l+i+1)+"',";
			c += i < 10 ? "'2015-03-0"+(i+1)+"'," : "'2015-03-"+(i+1)+"',";
			c += i < 10 ? "'2015-06-0"+(i+1)+"',": "'2015-06-"+(i+1)+"',";
			c += Math.round(56.25d+Math.random()*12*100d)/100d+",";
			c += i < 10 ? "'2015-07-0"+(i+1)+"',": "'2015-07-"+(i+1)+"',";
			c += Math.round((258d+Math.random()*12)*100d)/100d+",";
			c += "'"+(i+1)+"',";
			c += "'"+(i+1)+"',";
			c += Math.round(25+Math.random()*12) +",";
			c += i < 10 ? "'2015-12-0"+(i+1)+"');": "'2015-12-"+(i+1)+"');";
			System.out.println(c);
		}
	}

	public void setDauDate(String beginDauDate, String endDauDate) {
		if(!StringUtils.isEmpty(beginDauDate)){
			this.beginDauDate = beginDauDate;
		}
		if(!StringUtils.isEmpty(endDauDate)){
			this.endDauDate = endDauDate;
		}
	}
}
