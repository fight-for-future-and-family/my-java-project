package com.hoolai.bi.report.model.entity;

import java.util.Date;

public class CustomReportTask {
    private Long id;

    private Integer snid;

    private Integer gameid;

    private String taskName;

    private String taskCode;

    private Date executeTime;

    private Date endTime;

    private Long executeUserId;

    private String executeUserName;

    private Integer status;

    private Integer recordCount;

    private Integer intervalTime;
    
    private Long templateId;

    private Date createTime;
    
    public enum CustomReportTaskStatus{
    	FAIL,SUCCESS,RUNNING;
    	
    	public static CustomReportTaskStatus convert(int status){
    		for(CustomReportTaskStatus taskStatus:values()){
    			if(taskStatus.ordinal() == status){
    				return taskStatus;
    			}
    		}
			return null;
    	}
    }
    
    public enum CustomReportTaskExecuteTimes{
    	FIRST,REFULSH;
    }
    
    public CustomReportTask(){}

    public CustomReportTask(String snid, String gameid) {
		this.snid = Integer.valueOf(snid);
		this.gameid = Integer.valueOf(gameid);
	}

	public CustomReportTask(String snid, String gameid, String taskCode) {
		this(snid, gameid);
		this.taskCode = taskCode;
	}

	public CustomReportTask(Long taskId, int status, int recordCount,Date endTime) {
		this.id = taskId;
		this.status = status;
		this.recordCount = recordCount;
		this.endTime = endTime;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSnid() {
        return snid;
    }

    public void setSnid(Integer snid) {
        this.snid = snid;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
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


    public Long getExecuteUserId() {
		return executeUserId;
	}

	public void setExecuteUserId(Long executeUserId) {
		this.executeUserId = executeUserId;
	}

	public String getExecuteUserName() {
        return executeUserName;
    }

    public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
}