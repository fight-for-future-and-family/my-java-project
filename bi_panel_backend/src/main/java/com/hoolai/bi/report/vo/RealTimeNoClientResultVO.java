package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.RealTimeNoClientResult;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class RealTimeNoClientResultVO extends AbstractObjectVO<RealTimeNoClientResult>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4862956885373318170L;
	
	private String beginDate;
	private String endDate;

	public RealTimeNoClientResultVO() {
		super();
		this.entity=new RealTimeNoClientResult();
	}

	public RealTimeNoClientResultVO(RealTimeNoClientResult entity) {
		super(entity);
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
}
