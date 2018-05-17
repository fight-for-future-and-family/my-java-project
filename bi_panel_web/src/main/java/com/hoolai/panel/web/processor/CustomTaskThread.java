package com.hoolai.panel.web.processor;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hoolai.bi.report.model.entity.CustomReportTask;
import com.hoolai.bi.report.model.entity.CustomReportTask.CustomReportTaskExecuteTimes;
import com.hoolai.bi.report.model.entity.CustomReportTask.CustomReportTaskStatus;
import com.hoolai.bi.report.service.CustomReportTaskService;

public class CustomTaskThread implements Runnable{
	
	private static final Logger logger=Logger.getLogger("executeJob");
	
	private CustomTaskJob customTaskJob;
	private CustomReportTaskService customReportTaskService;
	private Long taskId;
	private String templateSql;
	private Integer isPresto;
	private CustomReportTaskExecuteTimes taskTimes;
	

	public CustomTaskThread(Long taskId, String templateSql, Integer isPresto,CustomReportTaskExecuteTimes taskTimes,
			CustomTaskJob customTaskJob,CustomReportTaskService customReportTaskService) {
		this.taskId = taskId;
		this.templateSql = templateSql;
		this.isPresto = isPresto;
		this.customTaskJob = customTaskJob;
		this.customReportTaskService = customReportTaskService;
		this.taskTimes = taskTimes;
	}

	@Override
	public void run() {
		int rowId = 0;
		try {
			List<Map<String,Object>> hiveDatas = customTaskJob.queryDatasFromHiveDB(templateSql, isPresto);
			if(taskTimes != CustomReportTaskExecuteTimes.FIRST){
				customTaskJob.deleteDatasFromMySql(taskId);
			}
			if(hiveDatas == null || hiveDatas.size() < 1){
				updateTask(CustomReportTaskStatus.SUCCESS, 0);
			}else{
				rowId = 1;
				for(Map<String,Object> data:hiveDatas){
					customTaskJob.insertDatasToMySql(taskId, rowId++, data);
				}
				updateTask(CustomReportTaskStatus.SUCCESS, hiveDatas.size());
			}
		} catch (Exception e) {
			logger.info("queryDatasFromHiveDB or insertDatasToMySql error! taskId:"+taskId+",rowId:"+(rowId-1)
					+",cause:"+e.getMessage());
			updateTask(CustomReportTaskStatus.FAIL, 0);
		}
		
	}
	
	private void updateTask(CustomReportTaskStatus status,int recordCount){
		CustomReportTask task = new CustomReportTask(taskId,status.ordinal(),recordCount,new Date());
		try {
			customReportTaskService.modifyEntitySelective(task);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("update task execute status error! taskId:"+taskId+",cause:"+e.getMessage());
		}
	}

}
