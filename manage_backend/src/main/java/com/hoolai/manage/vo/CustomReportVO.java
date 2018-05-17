package com.hoolai.manage.vo;


import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.model.CustomReport;

public class CustomReportVO extends AbstractObjectVO<CustomReport>{
	
	private static final long serialVersionUID = -4862956885373318170L;
	
	
	private String testJsonString;
	
	public CustomReportVO() {
		super();
		this.entity=new CustomReport();
	}

	public CustomReportVO(CustomReport customreport) {
		this.entity = customreport;
	}

	public String getTestJsonString() {
		return testJsonString;
	}

	public void setTestJsonString(String testJsonString) {
		this.testJsonString = testJsonString;
	}

}
