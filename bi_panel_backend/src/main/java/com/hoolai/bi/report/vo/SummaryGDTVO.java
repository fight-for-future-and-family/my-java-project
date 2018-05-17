package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.SummaryGDT;
import com.hoolai.dao.pagination.AbstractObjectVO;
public class SummaryGDTVO extends AbstractObjectVO<SummaryGDT> {

	private static final long serialVersionUID = 8867277237698758218L;
	
	private String beginDate;
	
	private String endDate;
	
	public SummaryGDTVO() {
		super();
		this.entity=new SummaryGDT();
	}

	public SummaryGDTVO(SummaryGDT entity) {
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

	public void setDate(String beginDate, String endDate) {
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
}
