package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.TestReport;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class TestReportVo extends AbstractObjectVO<TestReport>{

	private static final long serialVersionUID = 1L;
	
	private String snid;
	
	private String gameid;

	
	private String start_date;
	
	private String end_date;
	
	public TestReportVo() {
		super();
		this.entity=new TestReport();
	}

	public TestReportVo(TestReport entity) {
		super(entity);
	}

	
	
	public TestReportVo(String snid, String gameid, String start_date,
			String end_date) {
		super();
		this.snid = snid;
		this.gameid = gameid;
		this.start_date = start_date;
		this.end_date = end_date;
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

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	
	
}
