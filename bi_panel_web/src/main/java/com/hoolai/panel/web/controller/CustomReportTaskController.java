package com.hoolai.panel.web.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.report.model.entity.CustomReportTask;
import com.hoolai.bi.report.model.entity.CustomReportTask.CustomReportTaskExecuteTimes;
import com.hoolai.bi.report.model.entity.CustomReportTask.CustomReportTaskStatus;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.service.CustomReportCacheService;
import com.hoolai.bi.report.service.CustomReportTaskService;
import com.hoolai.bi.report.vo.CustomReportCacheVO;
import com.hoolai.bi.report.vo.CustomReportTaskVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.manage.model.CustomReportModel;
import com.hoolai.manage.model.CustomReportTemParams;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.CustomReportModelService;
import com.hoolai.manage.service.CustomReportTemParamsService;
import com.hoolai.manage.util.CsvUtils;
import com.hoolai.manage.vo.CustomReportModelVO;
import com.hoolai.panel.web.processor.CustomReportTaskProcessor;
import com.hoolai.panel.web.processor.CustomReportTaskProcessor.AddTaskRetStatus;
import com.hoolai.panel.web.processor.CustomReportThreadPool;
import com.hoolai.panel.web.processor.CustomTaskJob;
import com.hoolai.panel.web.processor.CustomTaskThread;

@Controller
@RequestMapping("/panel_bi/customTask")
public class CustomReportTaskController extends AbstractPanelController{
	
	private static final CustomReportThreadPool CUSTOM_REPORT_THREAD_POOL = new CustomReportThreadPool(10, 100);
	private static final Long MAX_EXCUTE_TIME = 40l * 60l * 1000l;
	
	@Autowired
	private CustomReportTaskService customReportTaskService;
	@Autowired
	private CustomReportModelService customReportModelService;
	@Autowired
	private CustomReportTemParamsService customReportTemParamsService;
	@Autowired
	private CustomReportCacheService customReportCacheService;
	@Autowired
	private CustomTaskJob customTaskJob;
	
	private static List<File> srcfile = new ArrayList<File>();

	@RequestMapping(value = {"/toTask.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toTask(HttpServletRequest request,HttpSession session,@ModelAttribute("customReportTaskVO") CustomReportTaskVO customReportTaskVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		Games games = this.getSessionGames(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		// 去任务列表页面
		return "task/taskList.jsp";
	}
	
	@RequestMapping(value = {"/getTaskList.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getTaskList(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		CustomReportTask task = new CustomReportTask(games.getSnid(),games.getGameid());
		CustomReportTaskVO taskVO = new CustomReportTaskVO(task);
		
		Map<String, Object> pageInfo = super.findPageInfo(request);
		
		long count = customReportTaskService.selectCount(taskVO);
		ret.put("recordsTotal", count);
		
		taskVO.setSearchValue((String)pageInfo.get("searchValue"));
		long scount = customReportTaskService.selectCount(taskVO);
		ret.put("recordsFiltered", scount);
		
		taskVO.setOffset((Long) pageInfo.get("start"));
		taskVO.setRows((Long) pageInfo.get("length"));
		ret.put("data", customReportTaskService.selectList(taskVO));
		ret.put("game", games);
		return ret;
	}
	
	@RequestMapping(value = {"/getManagerTaskList.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getManagerTaskList(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		CustomReportTask task = new CustomReportTask();
		CustomReportTaskVO taskVO = new CustomReportTaskVO(task);
		
		Map<String, Object> pageInfo = super.findPageInfo(request);
		
		long count = customReportTaskService.selectManagerCount(taskVO);
		ret.put("recordsTotal", count);
		
		taskVO.setSearchValue((String)pageInfo.get("searchValue"));
		long scount = customReportTaskService.selectManagerCount(taskVO);
		ret.put("recordsFiltered", scount);
		if(scount==0){
			ret.put("data", new ArrayList<CustomReportTask>());
			return ret;
		}
		
		taskVO.setOffset((Long) pageInfo.get("start"));
		taskVO.setRows((Long) pageInfo.get("length"));
		ret.put("data", customReportTaskService.selectManagerList(taskVO));
		return ret;
	}
	
	@RequestMapping(value = {"/toAddTask.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	@ResponseBody
	public Map<String,Object> toAddTask(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		CustomReportModelVO vo = new CustomReportModelVO(new CustomReportModel());
		vo.setSnid(Integer.valueOf(games.getSnid()));
		vo.setGameid(Integer.valueOf(games.getGameid()));
		vo.getEntity().setStatus(1);//有效的报表
		List<CustomReportModel> models = customReportModelService.selectGameAllModel(vo);
		
		ret.put("models", models);
		ret.put("game", games);
		
		return ret;
	}
	
	@RequestMapping(value = {"/getTempPram.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getTempPram(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		Map<String,Object> ret=new HashMap<String, Object>();
		String templateId = request.getParameter("templateId");
		
		CustomReportModel customReportModel = customReportModelService.getById(Long.valueOf(templateId));
		List<CustomReportTemParams> params = customReportTemParamsService.selectListByTemplateId(Long.valueOf(templateId));
		
		ret.put("params", params);
		ret.put("customReportModel", customReportModel);
		return ret;
	}
	
	
	@RequestMapping(value = {"/addTask.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> addTask(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String valueArrStr = request.getParameter("valueArr");
		String nameArrStr = request.getParameter("nameArr");
		String reportModel = request.getParameter("reportModel");
		
		if(StringUtils.isEmpty(valueArrStr)
			|| StringUtils.isEmpty(nameArrStr)
			|| StringUtils.isEmpty(reportModel)){
			ret.put("msg", AddTaskRetStatus.PARM_NULL.ordinal());
			return ret;
		}
		
		String[] nameArr = nameArrStr.split(",");
		String[] valueArr = valueArrStr.split(",");
		int status = checkValue(nameArr, valueArr);
		if(status != 0){
			ret.put("msg", status);
			return ret;
		}else{
			
			CustomReportModel customReportModel = customReportModelService.getById(Long.valueOf(reportModel));
			if(customReportModel == null){
				ret.put("msg", AddTaskRetStatus.PARM_NULL.ordinal());
				return ret;
			}
			CustomReportTaskProcessor processor = new CustomReportTaskProcessor(customReportModel,nameArr,valueArr);
           
            // 查询此任务是否存在 此模板是否还有未完成的任务在执行
            String taskCode = processor.getTaskCode();
            String taskName = processor.getTaskName();
            CustomReportTask task = new CustomReportTask(games.getSnid(),games.getGameid(),customReportModel.getCode()+"_"+taskCode);
    		CustomReportTaskVO taskVO = new CustomReportTaskVO(task);
    		taskVO.setTemplateCode(customReportModel.getCode());//查询相同模板不同任务参数
    		List<CustomReportTask> reportTasks = customReportTaskService.findTaskByTaskCode(taskVO);
            
    		if(reportTasks != null && reportTasks.size() > 0){
    			Map<String,Object> retTemp = null;
    			if((retTemp = checkSameTask(customReportModel, task, reportTasks)) != null){
    				return retTemp;
    			}
    		}
    		
			// 新建任务
			task.setTaskName(customReportModel.getName()+"("+taskName+")");
			task.setCreateTime(new Date());
			task.setExecuteTime(new Date());
			task.setExecuteUserId(users.getId());
			task.setExecuteUserName(users.getLoginName());
			task.setIntervalTime(customReportModel.getIntervalTime());
			task.setStatus(CustomReportTaskStatus.RUNNING.ordinal());
			task.setTemplateId(customReportModel.getId());
			customReportTaskService.saveEntity(task);
			
			// 执行任务、 分析接口返回数据 、入库，展示数据
			CUSTOM_REPORT_THREAD_POOL.execute(new CustomTaskThread(task.getId(), processor.sql(), processor.isPresto(),
					CustomReportTaskExecuteTimes.FIRST,customTaskJob,customReportTaskService));
    		
			
            ret.put("msg", status);
			return ret;
		}
	}

	private Map<String,Object> checkSameTask(CustomReportModel customReportModel, CustomReportTask task,
			List<CustomReportTask> reportTasks) throws Exception {
		Map<String,Object> ret = new HashMap<String, Object>();
		int sameModelCount = 0;
		for(CustomReportTask taskTemp:reportTasks){
			if(taskTemp.getTaskCode().equals(task.getTaskCode())){// 如果任务列表有相同参数的任务
				CustomReportTaskStatus taskStatus = CustomReportTaskStatus.convert(taskTemp.getStatus());
				switch(taskStatus){
					case SUCCESS:
						ret.put("msg", AddTaskRetStatus.TASK_EXIST.ordinal());
					    ret.put("taskName", taskTemp.getTaskName());
					    return ret;
					case RUNNING:
						if(!this.isExecuteTimeOut(taskTemp)){
							ret.put("msg", AddTaskRetStatus.TASK_EXIST.ordinal());
						    ret.put("taskName", taskTemp.getTaskName());
						    return ret;
						}
					}
			}else if(taskTemp.getTaskCode().contains(customReportModel.getCode())){// 如果任务列表中有相同模板不同参数的任务在执行
				if(!this.isExecuteTimeOut(taskTemp)){
					sameModelCount ++;
				}
			}
		}
		if(sameModelCount >= 3){
			ret.put("msg", AddTaskRetStatus.NO_TIME.ordinal());
			ret.put("taskInterval", customReportModel.getIntervalTime());
			return ret;
		}
		return null;
	}	
	
	private boolean isExecuteTimeOut(CustomReportTask taskTemp) throws Exception{
		if(Calendar.getInstance().getTimeInMillis() - taskTemp.getExecuteTime().getTime() >= MAX_EXCUTE_TIME){
			// 执行时间超过最大执行时长的默认失败，重新执行
			taskTemp.setEndTime(new Date());
			taskTemp.setStatus(CustomReportTaskStatus.FAIL.ordinal());
			customReportTaskService.saveEntitySelective(taskTemp);
			return true;
		}
		return false;
	}
	
	/**
	 * 判断传入的参数是否完整
	 * @param nameArr
	 * @param valueArr
	 * @return
	 */
	private int checkValue(String[] nameArr,String[] valueArr) {
		if(nameArr.length < 1 || valueArr.length < 1){
			return AddTaskRetStatus.PARM_NULL.ordinal();
		}
		if(nameArr.length != valueArr.length){
			return AddTaskRetStatus.ERROR.ordinal();
		}
		for(String name:nameArr){
			if(StringUtils.isEmpty(name)){
				return AddTaskRetStatus.PARM_NULL.ordinal();
			}
		}
		for(String value:valueArr){
			if(StringUtils.isEmpty(value)){
				return AddTaskRetStatus.PARM_NULL.ordinal();
			}
		}
		return AddTaskRetStatus.SUCCESS.ordinal();
	}
	
	@RequestMapping(value = {"/downloadCsv.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public void downloadCsv(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		String taskIdStr = request.getParameter("taskId");
		if(!StringUtils.isEmpty(taskIdStr)){
			long taskId = Long.valueOf(taskIdStr);
			CustomReportTask task = customReportTaskService.getById(taskId);
			List<CustomReportCacheVO> paramsNamesList = customReportCacheService.selectAllParamsNames(taskId);
			
			List<String> paramCodes = new ArrayList<String>();
			LinkedHashMap paramNames = new LinkedHashMap();
			for(CustomReportCacheVO vo:paramsNamesList){
				paramCodes.add(vo.getColumnCode());
				paramNames.put(vo.getColumnCode(),vo.getColumnName());
			}
//			List<Map<String,String>> values = customReportCacheService.selectValuesList(taskId,paramCodes);
//			values = sort(values);
//			String filepath = request.getSession().getServletContext().getRealPath("/");
//			File file = CsvUtils.createCSVFile(values, paramNames, filepath, "自定义报表");
//			CsvUtils.exportCsv(response, filepath+File.separator + file.getName(), "自定义报表.csv");
			
			int number = (int)customReportCacheService.countValuesList(taskId,paramCodes);
			int beginNum = 0;
			String filepath = request.getSession().getServletContext().getRealPath("/");
			File file = createCSVFile(paramNames, filepath, "自定义报表");
			List<Map<String,String>> values = new ArrayList<Map<String,String>>();
			for (int i = 0; i <number-1; ) {
				beginNum = i;
				values = customReportCacheService.selectValuesListCustom(taskId,paramCodes,beginNum);
//				values = sort(values);
				file = flushData(values, paramNames, file);
				i+=10000;
			}
			CsvUtils.exportCsv(response, filepath+File.separator + file.getName(), "自定义报表.csv");
		}
	}
	
	public static File createCSVFile(LinkedHashMap map, String outPutPath, String fileName) {
		File csvFile = null;
		BufferedWriter bufferedWriter = null;
		try {
			File file = new File(outPutPath); 
			if (!file.exists()) {
				file.mkdir();
			}
			List<String> listStr = new ArrayList<String>();
			// 定义文件名格式并创建
			csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
			// UTF-8使正确读取分隔符","
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile,true), "GBK"), 1024);
			// 写入文件头部
			for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
				bufferedWriter.write("" + (String) propertyEntry.getValue() != null ? (String) propertyEntry
								.getValue() : "" + "");
				if (propertyIterator.hasNext()) {
					bufferedWriter.write(",");
				}
				listStr.add(propertyEntry.getKey().toString());
			}
			bufferedWriter.newLine();

			srcfile.add(csvFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}
	
	public static File flushData(List exportData, LinkedHashMap map, File file) {
//		File csvFile = null;
		BufferedWriter bufferedWriter = null;
		BufferedWriter writer = null;
		try {
			List<String> listStr = new ArrayList<String>();
			// UTF-8使正确读取分隔符","
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "gbk"), 1024);
//			writer = new BufferedWriter(new FileWriter(file,true));
			for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
				listStr.add(propertyEntry.getKey().toString());
			}
			// 写入文件内容
			for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
				Map row = (Map) iterator.next();
				for (Iterator iter = listStr.iterator(); iter.hasNext();) {
					String values = iter.next().toString();
					writer.write(row.get(values).toString());
					if (iter.hasNext()) {
						writer.write(",");
					}
				}
				writer.newLine();
			}
			writer.flush();

			srcfile.add(file);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	private List<Map<String, String>> sort(List<Map<String, String>> values) {
		String columnsName = null;
		Map map = values.get(0);
		Iterator iter = map.entrySet().iterator();
		String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		Pattern p = Pattern.compile(eL);
		while(iter.hasNext()){
			Entry entry = (Entry) iter.next();
			Matcher m = p.matcher(entry.getValue().toString());
			if(m.matches()){
				columnsName = entry.getKey().toString();
				break;
			}
		}
		if(!StringUtils.isEmpty(columnsName)){
			for(int i=0;i<values.size()-1;i++){
				for(int j=i+1;j<values.size();j++){
					Map map1 = values.get(i);
					Map map2 = values.get(j);
					String date1 = map1.get(columnsName).toString();
					String date2 = map2.get(columnsName).toString();
					if(date1.compareTo(date2)>0){
						Map tempMap = values.get(i);
						values.set(i, values.get(j));
						values.set(j, tempMap);
					}
				}
			}
		}
		
		return values;
	}

	@RequestMapping(value = {"/getTaskDataCache.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getTaskDataCache(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String taskIdStr = request.getParameter("taskId");
		if(StringUtils.isEmpty(taskIdStr)){
			return null;
		}
		long taskId = Long.valueOf(taskIdStr);
		
		CustomReportTask task = customReportTaskService.getById(taskId);
		
		List<CustomReportCacheVO> paramsNamesList = customReportCacheService.selectAllParamsNames(taskId);
		
		List<String> paramCodes = new ArrayList<String>();
		List<String> paramNames = new ArrayList<String>();
		for(CustomReportCacheVO vo:paramsNamesList){
			paramCodes.add(vo.getColumnCode());
			paramNames.add(vo.getColumnName());
		}
		List<Map<String,String>> values = customReportCacheService.selectValuesList(taskId,paramCodes);
		
		CustomReportModel reportModel = customReportModelService.getById(task.getTemplateId());
		ret.put("paramCodes", paramCodes);
		ret.put("paramNames", paramNames);
		ret.put("values", values);
		ret.put("reportModel", reportModel);
		return ret;
	}
	
	@RequestMapping(value = {"/delTask.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> delTask(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		String taskIdStr = request.getParameter("taskId");
		if(StringUtils.isEmpty(taskIdStr)){
			ret.put("msg", 1);
			return ret;
		}
		
		 String[] strArray =  taskIdStr.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
	      for(int i=0;i<strArray.length;i++){
	    	  
	    	 long taskId = Long.valueOf(strArray[i]);
	    	 System.out.println("taskId："+taskId);
	  		CustomReportTask task = new CustomReportTask();
	  		task.setId(taskId);
	  		CustomReportTaskVO taskVO= new CustomReportTaskVO(task);
	  		customReportTaskService.delTask(taskVO);
	      }
		
		
		
		ret.put("msg", "success");
		return ret;
	}
	
	@RequestMapping(value = {"/flushTask.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> flushTask(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String taskIdStr = request.getParameter("taskId");
		if(StringUtils.isEmpty(taskIdStr)){
			ret.put("msg", 1);
			return ret;
		}
		long taskId = Long.valueOf(taskIdStr);
		
		CustomReportTask task = customReportTaskService.getById(taskId);
		CustomReportModel customReportModel = customReportModelService.getById(task.getTemplateId());
		
		// 任务刷新时间大于报表间隔时间
		long interval = customReportModel.getIntervalTime()*60*1000;
		long now_interval = (new Date().getTime()) - task.getEndTime().getTime();
//		if(now_interval < interval){
//			ret.put("msg", 3);
//			ret.put("taskInterval",customReportModel.getIntervalTime());
//			return ret;
//		}
		
		// 修改任务执行状态
		Users users = super.getSessionUsers(request);
		task.setExecuteTime(new Date());
		task.setEndTime(null);
		task.setRecordCount(null);
		task.setExecuteUserId(users.getId());
		task.setExecuteUserName(users.getLoginName());
		task.setStatus(CustomReportTaskStatus.RUNNING.ordinal());
		customReportTaskService.modifyEntity(task);
		
		// 重新装载sql
		String[] values = task.getTaskCode().replace(customReportModel.getCode()+"_", "").split(",");
		Map<String,String> envParams = new HashMap<String, String>();
		for(String value:values){
			String[] temp = value.split("=");
			envParams.put(temp[0], temp[1]);
		}
		
		// 执行任务、 分析接口返回数据 、入库，展示数据
		CUSTOM_REPORT_THREAD_POOL.execute(new CustomTaskThread(task.getId(), 
				CustomReportTaskProcessor.sql(envParams,customReportModel.getTemplateSql()), customReportModel.getIsPresto(), 
				CustomReportTaskExecuteTimes.REFULSH,
				customTaskJob,customReportTaskService));
		
		ret.put("msg", 0);
		return ret;
	}
	
}
