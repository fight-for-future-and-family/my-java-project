package com.hoolai.panel.web.job;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;

import com.hoolai.bi.report.job.AbstractExecuteJob;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.SeriesAll;
import com.hoolai.bi.report.model.entity.SeriesGame;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.SeriesAllService;
import com.hoolai.manage.model.SysConfig;
import com.hoolai.manage.service.SysConfigService;
import com.hoolai.panel.web.processor.PrestoJdbcTemplate;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
public class SeriesGamesJob extends AbstractExecuteJob {
	
	@Autowired
	private SeriesAllService seriesAllService;
	@Autowired
	private GamesService gamesService;
	@Autowired
	private SysConfigService sysConfigService;
	
	private Map<String, String> runJobSet = new HashMap<String, String>();
	
	@Autowired
	@Qualifier("sourceCpaConfiguration")
	private static Configuration seriesAllConfiguration;
	
	private static ApplicationContext applicationContext=null;
	
	public static Object getObject(String id) {
		Object object = null;
		object = applicationContext.getBean(id);
		return object;
	}

	@Override
	public Map<String, Object> executeJob() throws Exception {
		List<SysConfig> sysList = new ArrayList<SysConfig>();
		List<Games> gameList = new ArrayList<Games>();
		List<Games> queryGameList = null;
		sysList = sysConfigService.selectAllSysConfig();
		gameList = gamesService.selectAllGames();
		String beginDate = new String();
		String endDate = new String();
		Calendar cal = Calendar.getInstance();//获取当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(Calendar.DAY_OF_MONTH, -1);
		endDate = sdf.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, -2);
		beginDate = sdf.format(cal.getTime());
		
		runJobSet.clear();
		
		for (SysConfig syc : sysList) {
			
			queryGameList = new ArrayList<Games>();
			for (Games games : gameList) {
				if(String.valueOf(syc.getId()).equals(String.valueOf(games.getParentId()))
						&&"1".equals(games.getEtlStatus())
						&&"1".equals(games.getStats())) {
					queryGameList.add(games);
				}
			}
			if(queryGameList!=null&&queryGameList.size()>0){
				seriesAllService.removeAllBySeriesid(String.valueOf(syc.getId()));
				seriesAllService.removeGamesBySeriesid(String.valueOf(syc.getId()));
				runSeriesGamesData(beginDate, endDate, syc.getName(), String.valueOf(syc.getId()), queryGameList);
			}
		}
		
		return null;
	}
	
	public static String daysOfTwo(Date beginDate, Date endDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(beginDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(endDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return String.valueOf(day2 - day1 + 1);
	}
	
	public String runSeriesGamesData(String beginDate, String endDate, String seriesName, String seriesid, List<Games> gameList){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dataType = "";
		try {
			dataType = daysOfTwo(sdf.parse(beginDate), sdf.parse(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(runJobSet.get(beginDate+"_"+endDate+"_"+seriesid) != null) {
			return "0";
		}
		runJobSet.put(beginDate+"_"+endDate+"_"+seriesid, "1");
		runSeriesGamesData(beginDate, endDate, seriesName, seriesid, dataType, gameList, endDate);
		String tempBeginDate = beginDate;
		do {
			runSeriesGamesData(beginDate, beginDate, seriesName, seriesid, dataType, gameList, endDate);
//			System.out.println(beginDate);
			beginDate = addDay(beginDate);
		} while (!beginDate.equals(addDay(endDate)));
		runJobSet.remove(tempBeginDate+"_"+endDate+"_"+seriesid);
		return "1";
	}
	
	public String addDay(String beginDate) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(beginDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.DAY_OF_MONTH, 1);
		str = sdf.format(cal.getTime());
		
		return str;
	}
	
	public void runSeriesGamesData(String beginDate, String endDate, String seriesName, String seriesid, String dataType, List<Games> gameList, String ds){
		
		if((beginDate==null||"".equals(beginDate))&&(dataType!=null||"".equals(dataType))){
			beginDate = getBeginDate(endDate, dataType);
		}
		PrestoJdbcTemplate prestoJdbcTemplate = new PrestoJdbcTemplate();
		SeriesGame seriesGame = new SeriesGame();
		seriesGame.setSeriesName(seriesName);
		seriesGame.setBeginDate(beginDate);
		seriesGame.setEndDate(endDate);
		seriesGame.setSeriesid(seriesid);
		seriesGame.setDs(ds);
		if(dataType!=null&&!"".equals(dataType)){
			seriesGame.setDataType(dataType);
		}
		
		String snGameIdsSql = getSnGameIdsSql(gameList, seriesid);
		if(snGameIdsSql.length()>10){
			seriesGame.setSnGameIdsSql(snGameIdsSql);
			String allSql = getSeriesAllSql(seriesGame, "All");
			ResultSet rs = null;
			List<SeriesAll> seriesAllResult = new ArrayList<SeriesAll>();
			List<SeriesGame> seriesGameResult = new ArrayList<SeriesGame>();
			try{
				rs = prestoJdbcTemplate.exec(allSql);
				seriesAllResult = (List<SeriesAll>)(List)columnToField(rs, SeriesAll.class);
				seriesAllService.saveSeriesAllList(seriesAllResult);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				prestoJdbcTemplate.destory();
			}
			seriesGame.setSnGameIdsSql("");
			String gameSql = "";
			List<SeriesGame> resultList = new ArrayList<SeriesGame>();
			for (Games games : gameList) {
				if(seriesid.equals(String.valueOf(games.getParentId()))) {
					seriesGame.setSnid(games.getSnid());
					seriesGame.setGameid(games.getGameid());
					seriesGame.setGameName(games.getName());
					gameSql = getSeriesAllSql(seriesGame, "Game");
					try{
						prestoJdbcTemplate = new PrestoJdbcTemplate();
						rs = prestoJdbcTemplate.exec(gameSql);
						seriesGameResult = (List<SeriesGame>)(List)columnToField(rs, SeriesGame.class);
						if(seriesGameResult.size()>0){
							resultList.addAll(seriesGameResult);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						prestoJdbcTemplate.destory();
					}
				}
			}
			if(resultList.size()>0) {
				seriesAllService.saveSeriesGameList(resultList);
			}
		}
	}
		
	public static List<Object> columnToField(ResultSet rs, Class clazz) throws Exception{
		Object obj=clazz.newInstance();
        Field[] fields=clazz.getDeclaredFields();
		List<Object> objList = new ArrayList<Object>();
		
		while (rs.next()) {
			obj=clazz.newInstance();
			for (Field f : fields) {
				String name = f.getName();
				Class type = f.getType();
				Method method = null;
				try {
					method = clazz.getMethod("set" + name.replaceFirst(name.substring(0, 1), 
							name.substring(0, 1).toUpperCase()), type);
//					Object qqq = ConvertUtils.convert(rs.getString(name), type);
//					System.out.println(qqq);
					method.invoke(obj,ConvertUtils.convert(rs.getString(name), type));
				} catch (Exception e) {
//					e.printStackTrace();
				}
			}
			objList.add(obj);
		}
		
		return objList;
	}
	
	private static String getSeriesAllSql(SeriesGame seriesGame, String type) {
		applicationContext=ContextLoader.getCurrentWebApplicationContext();
		seriesAllConfiguration = (Configuration) getObject("sourceCpaConfiguration");
		Template template= null;
		StringWriter sw = new StringWriter();
		try {
			template = seriesAllConfiguration.getTemplate("series"+type+".ftl");
			template.process(seriesGame, sw);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("get sql template error!"+e.getMessage()+" path:"+"templates/ios.ftl");
		}
		return sw.toString();
	}
	
	private static String getSnGameIdsSql(List<Games> gameList, String seriesid) {
		String snGameIdsSql = " and (";
		
		for (Games games : gameList) {
			if(seriesid.equals(String.valueOf(games.getParentId()))) {
				if(snGameIdsSql.length()>20) {
					snGameIdsSql += " or ";
				} 
				snGameIdsSql += " ( ";
				snGameIdsSql += " snid='"+games.getSnid()+"' and gameid='"+games.getGameid()+"'";
				snGameIdsSql += " ) ";
			}
		}
		snGameIdsSql += " ) ";
		
		return snGameIdsSql;
	}
	
	public static String getBeginDate(String endDate, String dataType) {
		Calendar cal = Calendar.getInstance();//获取当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(Calendar.DAY_OF_MONTH, -(Integer.valueOf(dataType)));
		String beginDate = sdf.format(cal.getTime());
		return beginDate;
	}

}
