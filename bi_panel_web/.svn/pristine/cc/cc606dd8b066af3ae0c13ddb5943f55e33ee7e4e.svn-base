package com.hoolai.panel.web.testReport;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.hoolai.bi.report.model.entity.SeriesAll;
import com.hoolai.bi.report.model.entity.TestReport;
import com.hoolai.bi.report.model.entity.TestReportConversion;
import com.hoolai.bi.report.model.entity.TestReportGoldObtain;
import com.hoolai.bi.report.model.entity.TestReportLeaveSave;
import com.hoolai.bi.report.model.entity.TestReportLevel;
import com.hoolai.bi.report.model.entity.TestReportSbwd;
import com.hoolai.bi.report.model.entity.TestReportSource;
import com.hoolai.bi.report.model.entity.TestReportZtZsb;
import com.hoolai.panel.web.processor.PrestoJdbcTemplate;

import freemarker.template.Configuration;

@Controller
public class PrestoTestReport {

	@Autowired
	@Qualifier("sourceCpaConfiguration")
	private static Configuration seriesAllConfiguration;
	
	private static ApplicationContext applicationContext=null;
	
	public static Object getObject(String id) {
		Object object = null;
		object = applicationContext.getBean(id);
		return object;
	}
	
	
	//总体(主要为基础数据) 
	public List<TestReport> TestReportGamesData(String sql){
		PrestoJdbcTemplate prestoJdbcTemplate = new PrestoJdbcTemplate();
		List<TestReport> OverallResult = new ArrayList<TestReport>();
		try{
			ResultSet rs = null;
			rs = prestoJdbcTemplate.exec(sql);
			OverallResult = (List<TestReport>)(List)columnToField(rs, TestReport.class);
			//保存mysql
			//seriesAllService.saveSeriesAllList(seriesAllResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			prestoJdbcTemplate.destory();
		}
		return OverallResult;
						
    }
	//总体(主要为留存数据)
	public List<TestReportLeaveSave> TestReportGamesLeaveSaveData(String sql){
		PrestoJdbcTemplate prestoJdbcTemplate = new PrestoJdbcTemplate();
		List<TestReportLeaveSave> leaveSaveList = new ArrayList<TestReportLeaveSave>();
		try{
			ResultSet rs = null;
			rs = prestoJdbcTemplate.exec(sql);
			leaveSaveList = (List<TestReportLeaveSave>)(List)columnToField(rs, TestReportLeaveSave.class);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			prestoJdbcTemplate.destory();
		}
		return leaveSaveList;
						
    }
	//设备维度
		public List<TestReportSbwd> TestReportGamesSbwdData(String sql){
			PrestoJdbcTemplate prestoJdbcTemplate = new PrestoJdbcTemplate();
			List<TestReportSbwd> leaveSaveList = new ArrayList<TestReportSbwd>();
			try{
				ResultSet rs = null;
				rs = prestoJdbcTemplate.exec(sql);
				leaveSaveList = (List<TestReportSbwd>)(List)columnToField(rs, TestReportSbwd.class);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				prestoJdbcTemplate.destory();
			}
			return leaveSaveList;
							
	    }
	
		//总体(主要为注收比)
		public List<TestReportZtZsb> TestReportGamesZtZsbData(String sql){
			PrestoJdbcTemplate prestoJdbcTemplate = new PrestoJdbcTemplate();
			List<TestReportZtZsb> leaveSaveList = new ArrayList<TestReportZtZsb>();
			try{
				ResultSet rs = null;
				rs = prestoJdbcTemplate.exec(sql);
				leaveSaveList = (List<TestReportZtZsb>)(List)columnToField(rs, TestReportZtZsb.class);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				prestoJdbcTemplate.destory();
			}
			return leaveSaveList;
							
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//等级分布
	public List<TestReportLevel> TestReportGamesLevelData(String sql){
		PrestoJdbcTemplate prestoJdbcTemplate = new PrestoJdbcTemplate();
		List<TestReportLevel> OverallResult = new ArrayList<TestReportLevel>();
		try{
			ResultSet rs = null;
			rs = prestoJdbcTemplate.exec(sql);
			OverallResult = (List<TestReportLevel>)(List)columnToField(rs, TestReportLevel.class);
			//保存mysql
			//seriesAllService.saveSeriesAllList(seriesAllResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			prestoJdbcTemplate.destory();
		}
		return OverallResult;
						
    }
	//新手引导转化
	public List<TestReportConversion> TestReportGamesConversionData(String sql){
		PrestoJdbcTemplate prestoJdbcTemplate = new PrestoJdbcTemplate();
		List<TestReportConversion> OverallResult = new ArrayList<TestReportConversion>();
		try{
			ResultSet rs = null;
			rs = prestoJdbcTemplate.exec(sql);
			OverallResult = (List<TestReportConversion>)(List)columnToField(rs, TestReportConversion.class);
			//保存mysql
			//seriesAllService.saveSeriesAllList(seriesAllResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			prestoJdbcTemplate.destory();
		}
		return OverallResult;
						
    }
	//金币消耗&获取
	public List<TestReportGoldObtain> TestReportGamesGoldObtainData(String sql){
		PrestoJdbcTemplate prestoJdbcTemplate = new PrestoJdbcTemplate();
		List<TestReportGoldObtain> OverallResult = new ArrayList<TestReportGoldObtain>();
		try{
			ResultSet rs = null;
			rs = prestoJdbcTemplate.exec(sql);
			OverallResult = (List<TestReportGoldObtain>)(List)columnToField(rs, TestReportGoldObtain.class);
			//保存mysql
			//seriesAllService.saveSeriesAllList(seriesAllResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			prestoJdbcTemplate.destory();
		}
		return OverallResult;
						
    }
	
	
	public static List<Object> columnToField(ResultSet rs, Class clazz) throws Exception{
		
        Field[] fields=clazz.getDeclaredFields();
		List<Object> objList = new ArrayList<Object>();
		
		while (rs.next()) {
			Object obj=clazz.newInstance();
			
			for (Field f : fields) {
				String name = f.getName();
				Class type = f.getType();
				Method method = null;
				try {
					method = clazz.getMethod("set" + name.replaceFirst(name.substring(0, 1), 
							name.substring(0, 1).toUpperCase()), type);
					Object qqq = ConvertUtils.convert(rs.getString(name), type);
					//System.out.println(qqq);
					method.invoke(obj,ConvertUtils.convert(rs.getString(name), type));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		
			objList.add(obj);
		}
		
		return objList;
	}
}
