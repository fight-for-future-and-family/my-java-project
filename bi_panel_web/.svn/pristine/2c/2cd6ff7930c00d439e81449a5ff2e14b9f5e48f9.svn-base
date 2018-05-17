package com.hoolai.panel.web.job;


import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hoolai.bi.report.job.AbstractExecuteJob;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.RealTimeGame;
import com.hoolai.bi.report.model.entity.RealTimeGameClient;
import com.hoolai.bi.report.model.entity.RealTimeGameSource;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.RealTimeGameClientidService;
import com.hoolai.bi.report.service.RealTimeGameSourceService;
import com.hoolai.bi.report.service.RealTimeGamesService;
import com.hoolai.bi.report.vo.RealTimeGameClientVo;
import com.hoolai.bi.report.vo.RealTimeGameSourceVo;
import com.hoolai.bi.report.vo.RealTimeGameVo;
import com.hoolai.panel.web.processor.PrestoJdbcTemplate;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class RealTimeClientJob  extends AbstractExecuteJob{
	
	private static Log log = LogFactory.getLog(RealTimeClientJob.class);

	
	@Autowired
	private GamesService gamesService;
	@Autowired
	private RealTimeGamesService realTimeGamesService;
	
	@Autowired
	private RealTimeGameClientidService realTimeGameClientidService;
	
	@Autowired
	private RealTimeGameSourceService realTimeGameSourceService;
	@Autowired
	@Qualifier("sourceCpaConfiguration")
	private Configuration sourceCpaConfiguration;
	
	public boolean is=true;

	
	@Override
	public Map<String, Object> executeJob() throws Exception {

		
		
		//实时总览
		System.out.println("任务开始执行执行时间："+new Date());
		//获取所有游戏snid, gameid
		List<Games> gamesList = gamesService.selectAllGames2();
		RealTimeGame realtTimeGame = new RealTimeGame();
		RealTimeGameVo realtTimeGameVo = new RealTimeGameVo();
		
		RealTimeGameSourceVo realTimeGameSourceVo = new RealTimeGameSourceVo();
		RealTimeGameClientVo realTimeGameClientVo = new RealTimeGameClientVo(); 
		String day=this.getDayDate();
		 Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String finishDs = sdf.format(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			String startDs = sdf.format(calendar.getTime());
			
			int startHour = date.getHours();
			int startMinute = date.getMinutes();
			String start_time = "";
			String finish_time = "";
			if(startMinute>=0 && startMinute<30){
				if(startHour==00){
					start_time="23:00:00";
					finish_time="23:59:59";
				}else{
					start_time = (startHour-1>9?startHour-1:"0"+(startHour-1))+":00:00";
					finish_time = (startHour>9?startHour:"0"+startHour)+":00:00";
				}
			}else{
				start_time = (startHour>9?startHour:"0"+startHour)+":00:00";
				finish_time = (startHour>9?startHour:"0"+startHour)+":00:00";
			}
			
			//实时分服
			System.out.println("开始实时分服"+new Date());
			for(int i=0;i<gamesList.size();i++){
				realTimeGameClientVo.setSnid(gamesList.get(i).getSnid());
				realTimeGameClientVo.setGameid(gamesList.get(i).getGameid());
				realTimeGameClientVo.setDs(day);
				realTimeGameClientVo.setStart_time(start_time);
				realTimeGameClientVo.setFinish_time(finish_time);
				//System.out.println("第"+i+"条");
				List<RealTimeGameClient> clientidlist = this.realTime(realTimeGameClientVo,"realTimeClientid");
			}
			System.out.println("结束实时分服"+new Date());
			
			/*List<String> snid=new ArrayList<String>(); 
			snid.add("200");
			snid.add("200");
			snid.add("200");
			snid.add("200");
			snid.add("200");
			snid.add("200");
			snid.add("200");
			snid.add("200");
			snid.add("200");
			snid.add("200");
			snid.add("200");
			List<String> Gameid=new ArrayList<String>(); 
			Gameid.add("92");
			Gameid.add("213");
			Gameid.add("214");
			Gameid.add("285");
			Gameid.add("272");
			Gameid.add("270");
			Gameid.add("258");
			Gameid.add("276");
			Gameid.add("219");
			Gameid.add("182");
			Gameid.add("165");
			day="2017-07-05";
			
			List<String> start_time2=new ArrayList<String>(); 
		
			start_time2.add("09:00:00");
			start_time2.add("10:00:00");
			start_time2.add("11:00:00");
			start_time2.add("12:00:00");
			start_time2.add("13:00:00");
			start_time2.add("14:00:00");
			start_time2.add("15:00:00");
			start_time2.add("16:00:00");
			start_time2.add("17:00:00");
			
			List<String> finish_time2=new ArrayList<String>();
			
			finish_time2.add("10:00:00");
			finish_time2.add("11:00:00");
			finish_time2.add("12:00:00");
			finish_time2.add("13:00:00");
			finish_time2.add("14:00:00");
			finish_time2.add("15:00:00");
			finish_time2.add("16:00:00");
			finish_time2.add("17:00:00");
			finish_time2.add("18:00:00");
			for(int i=0;i<snid.size();i++){
				for(int j=0;j<finish_time2.size();j++){
					realTimeGameClientVo.setSnid(snid.get(i));
					realTimeGameClientVo.setGameid(Gameid.get(i));
					realTimeGameClientVo.setDs(day);
					realTimeGameClientVo.setStart_time(start_time2.get(j));
					realTimeGameClientVo.setFinish_time(finish_time2.get(j));
					//System.out.println("第"+i+"条");
					List<RealTimeGameClient> clientidlist = this.realTime(realTimeGameClientVo,"realTimeClientid");
				}
				
			}*/
		return null;
		
	}
	
	//系统day
	public  String getDayDate() {
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 String beginDate ;
		 Calendar calendar = Calendar.getInstance(); //得到日历
		 Date date = new Date();
		 int startHour = date.getHours();
		 if(startHour == 00){
			 Date dNow = new Date();   //当前时间
			 calendar.setTime(dNow);//把当前时间赋给日历
			 calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
			 beginDate = sdf.format(calendar.getTime());   //得到前一天的时间
		 }else{
			  beginDate = sdf.format(calendar.getTime());
		 }
		return beginDate;
	}
	
	//实时分服
	public List<RealTimeGameClient> realTime(RealTimeGameClientVo realTimeGameClientidVo,String systemType){
		PrestoJdbcTemplate prestoJdbcTemplate = new PrestoJdbcTemplate();
		List<RealTimeGameClient> leaveSaveList = new ArrayList<RealTimeGameClient>();
		try{
			String sql = this.getRealTimeClientidSql(realTimeGameClientidVo, systemType);
			ResultSet rs = null;
			rs = prestoJdbcTemplate.exec(sql);
			leaveSaveList =(List<RealTimeGameClient>)(List)columnToField(rs, RealTimeGameClient.class);
			
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 String beginDate ;
			//判断如果是00点 ,查询的是前一天的数据，插入到数据库日期是前一天的
			 Calendar beforeTime = Calendar.getInstance();
			 SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
			 String endDate = dateFormat.format(beforeTime.getTime());
			 if(endDate.equals("00")){
				 Calendar calendar = Calendar.getInstance(); //得到日历
				 calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
				 beginDate = sdf.format(calendar.getTime());   //得到前一天的时间
				 leaveSaveList.get(0).setDay(beginDate);
			 }
			if(leaveSaveList.size()>0){
				realTimeGameClientidService.saveRealTimeClientidDataList(leaveSaveList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			prestoJdbcTemplate.destory();
		}
		return leaveSaveList;
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
	
	

	
	//实时分服
	private String getRealTimeClientidSql(RealTimeGameClientVo realtTimeGameClientVo,String systemType){
		Configuration configuration = new Configuration();
		Template template= null;
		StringWriter sw = new StringWriter();
		try {
			template = sourceCpaConfiguration.getTemplate(systemType+".ftl");
			template.process(realtTimeGameClientVo, sw);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("get sql template error!"+e.getMessage()+" path:"+"templates/ios.ftl");
		}
		return sw.toString();
	}
	
	
	
	
}
