package com.hoolai.manage.vo;


import java.util.Date;

import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.model.CustomReportModel;
import com.hoolai.manage.model.CustomReportTemParams;

public class CustomReportTemParamsVO extends AbstractObjectVO<CustomReportTemParams>{
	
	private static final long serialVersionUID = -4862956885373318170L;
	
	    private Long id;

	    private String taskName;

	    private String taskCode;

	    private Date executeTime;

	    private Date endTime;

	    private Date executeUserId;

	    private String executeUserName;

	    private Byte status;

	    private Integer recordCount;

	    private Integer intervalTime;

	    private Date createTime;
	
	public CustomReportTemParamsVO() {
		super();
		this.entity=new CustomReportTemParams();
	}

	public CustomReportTemParamsVO(CustomReportTemParams customReportTemParams) {
		this.entity = customReportTemParams;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getExecuteUserId() {
		return executeUserId;
	}

	public void setExecuteUserId(Date executeUserId) {
		this.executeUserId = executeUserId;
	}

	public String getExecuteUserName() {
		return executeUserName;
	}

	public void setExecuteUserName(String executeUserName) {
		this.executeUserName = executeUserName;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	
	
}
