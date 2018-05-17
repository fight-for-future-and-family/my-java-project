package com.hoolai.panel.web.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hoolai.bi.monitor.model.entity.AlarmUserConcat;
import com.hoolai.bi.monitor.service.GameAlarmUserService;
import com.hoolai.bi.notifyer.mail.MailSenderProxy;
import com.hoolai.bi.notifyer.sms.SmsSenderProxy;
import com.hoolai.bi.report.job.AbstractExecuteJob;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.MonitorMetrics;
import com.hoolai.bi.report.model.entity.MonitorMetricsMsg;
import com.hoolai.bi.report.model.entity.RealTimeGame;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.MonitorMetricsService;
import com.hoolai.bi.report.service.RealTimeGamesService;

public class MonitorMetricsJob extends AbstractExecuteJob {
	
	private static Log log = LogFactory.getLog(MonitorMetricsJob.class);
	
	@Autowired
	private RealTimeGamesService realTimeGamesService;
	
	@Autowired
	private GamesService gamesService;
	
	@Autowired
	private MonitorMetricsService monitorMetricsService;
	
	@Autowired
	private GameAlarmUserService gameAlarmUserService;

	@Override
	public Map<String, Object> executeJob() throws Exception {
		log.info("MonitorMetricsJob is begin:"+System.currentTimeMillis());
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String finishDs = sdf.format(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String startDs = sdf.format(calendar.getTime());
		
		String lastHourStartTime = "";
		String lastHourFinishTime = "";
		String lastHourDs = "";
		
		int startHour = date.getHours();
		String startTime = "";
		String finishTime = "";
//		startHour = 12;
		if(startHour==0) {
			finishDs = sdf.format(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			startDs = sdf.format(calendar.getTime());
			startTime = "23:00:00";
			finishTime = "24:00:00";
			lastHourDs = finishDs;
			lastHourStartTime = "22:00:00";
			lastHourFinishTime = startTime;
		}else {
			startTime = (startHour-1>9?startHour-1:"0"+(startHour-1))+":00:00";
			finishTime = (startHour>9?startHour:"0"+startHour)+":00:00";
			if(startHour>=2){
				lastHourDs = finishDs;
				lastHourStartTime = (startHour-2>9?startHour-2:"0"+(startHour-2))+":00:00";
				lastHourFinishTime = startTime;
			}else{
				lastHourDs = startDs;
				lastHourStartTime = "23:00:00";
				lastHourFinishTime = "24:00:00";
			}
		}
		List<Games> gamesList = gamesService.selectAllGames2();
		log.info("MonitorMetricsJob gamesList:"+gamesList.size());
		for (Games games : gamesList) {
			List<AlarmUserConcat> alarmUserConcats=this.gameAlarmUserService.getAlarmUserConcat(games.getId());
			log.info("MonitorMetricsJob alarmUserConcats:"+games.getSnid()+"_"+games.getGameid()+"_"+alarmUserConcats.size());
			if(alarmUserConcats.size()>0){
//				judgeData(games, "11:00:00", "12:00:00", "2017-06-11", "2017-06-12", 
//						"2017-06-12", "10:00:00", "11:00:00");
				log.info("MonitorMetricsJob "+games+"_"+startTime+"_"+finishTime+"_"+startDs+"_"+finishDs+"_"+
						lastHourDs+"_"+lastHourStartTime+"_"+lastHourFinishTime);
				judgeData(games, startTime, finishTime, startDs, finishDs, 
						lastHourDs, lastHourStartTime, lastHourFinishTime);
			}
		}
		
		log.info("MonitorMetricsJob is end:"+System.currentTimeMillis());
		return null;
	}
	
	private void judgeData(Games games, String startTime, String finishTime, String startDs, String endDs, 
				String lastHourDs, String lastHourStartTime, String lastHourFinishTime){
		List<MonitorMetricsMsg> monitorMetricsMsgList = new ArrayList<MonitorMetricsMsg>();
		MonitorMetrics monitorMetrics = new MonitorMetrics(games.getSnid(), games.getGameid());
		List<MonitorMetrics> monitorMetricsList = monitorMetricsService.selectList(monitorMetrics);
		
		if(monitorMetricsList.size()>0) {
			RealTimeGame realTimeGame = new RealTimeGame();
			realTimeGame.setSnid(games.getSnid());
			realTimeGame.setGameid(games.getGameid());
			realTimeGame.setStart_time(startTime);
			realTimeGame.setFinish_time(finishTime);
			
			realTimeGame.setDay(startDs);
			RealTimeGame yesterdayData = realTimeGamesService.selectRealTimeData(realTimeGame);
			realTimeGame.setDay(endDs);
			RealTimeGame todayData = realTimeGamesService.selectRealTimeData(realTimeGame);
			realTimeGame.setStart_time(lastHourStartTime);
			realTimeGame.setFinish_time(lastHourFinishTime);
			realTimeGame.setDay(lastHourDs);
			RealTimeGame lastHourData = realTimeGamesService.selectRealTimeData(realTimeGame);
			
			if(yesterdayData == null || todayData ==null || lastHourData == null){
				return ;
			}
			log.info("MonitorMetricsJob monitorMetricsList:"+monitorMetricsList.size());
			for (MonitorMetrics monitorMetric : monitorMetricsList) {
				Double yesterdayNum = null;
				Double todayNum = null;
				Double lastHourNum = null;
				boolean status = true;
				if("dnu".equals(monitorMetric.getColumnName())) {
					yesterdayNum = yesterdayData.getDnu();
					todayNum = todayData.getDnu();
					lastHourNum = lastHourData.getDnu();
				}else if("dau".equals(monitorMetric.getColumnName())) {
					yesterdayNum = yesterdayData.getDau();
					todayNum = todayData.getDau();
					lastHourNum = lastHourData.getDau();
				}else if("payer".equals(monitorMetric.getColumnName())) {
					yesterdayNum = yesterdayData.getPayer();
					todayNum = todayData.getPayer();
					lastHourNum = lastHourData.getPayer();
				}else if("totalAmount".equals(monitorMetric.getColumnName())) {
					yesterdayNum = yesterdayData.getTotal_amount();
					todayNum = todayData.getTotal_amount();
					lastHourNum = lastHourData.getTotal_amount();
				}
				Double rate = null;
				Double lastHourRate = null;
				if(yesterdayNum!=null&&todayNum!=null&&lastHourNum!=null
						&&yesterdayNum!=0&&lastHourNum!=0) {
					rate = Double.valueOf(Math.round(((yesterdayNum - todayNum)/yesterdayNum)*100*100))/100;
					lastHourRate = Double.valueOf(Math.round(((lastHourNum - todayNum)/lastHourNum)*100*100))/100;
					Double lowerLimit = monitorMetric.getLowerLimit();
					Double topLimit = 0 - monitorMetric.getTopLimit();
					String type = "";
					String lastHourType = "";
					if(monitorMetric.getLowerLimit()!=0&&monitorMetric.getTopLimit()!=0){
						if( !((rate>0&&rate>lowerLimit)||(rate<0&&rate<topLimit))
								|| !((lastHourRate>0&&lastHourRate>lowerLimit)||(lastHourRate<0&&lastHourRate<topLimit)) ){
							continue;
						}
						
						type = rate<0 ? "增加" : "减少";
						lastHourType = lastHourRate<0 ? "增加" : "减少";
						
						MonitorMetricsMsg monitorMetricsMsg = new MonitorMetricsMsg(games.getSnid(), games.getGameid());
						monitorMetricsMsg.setName(games.getName());
						monitorMetricsMsg.setRate(rate);
						monitorMetricsMsg.setLastHourRate(lastHourRate);
						monitorMetricsMsg.setYesterdayNum(yesterdayNum);
						monitorMetricsMsg.setTodayNum(todayNum);
						monitorMetricsMsg.setLastHourNum(lastHourNum);
						monitorMetricsMsg.setType(type);
						monitorMetricsMsg.setIsEmail(monitorMetric.getIsEmail());
						monitorMetricsMsg.setIsPhone(monitorMetric.getIsPhone());
						monitorMetricsMsg.setLastHourType(lastHourType);
						monitorMetricsMsg.setColumnName(monitorMetric.getColumnName());
						monitorMetricsMsgList.add(monitorMetricsMsg);
					}else {
						continue;
					}
				}
			}
			if(monitorMetricsMsgList.size()>0) {
				baojing(monitorMetricsMsgList, games.getName(), games.getId(),  startDs, endDs, startTime, finishTime,
						lastHourDs, lastHourStartTime, lastHourFinishTime);
			}
		}
	}
	
	private void baojing(List<MonitorMetricsMsg> monitorMetricsMsgList, String gameName, Long gameId,
					String startDs, String endDs, 
					String startTime, String finishTime,
					String lastHourDs, String lastHourStartTime, String lastHourFinishTime) {
		String subject = gameName+"数据监控预警【"+endDs+"___"+startTime+"——"+finishTime+"】";
//		System.out.println(subject);
		StringBuilder conceptLevel1 = new StringBuilder("昨日时间段【"+startTime+"——"+finishTime+"】");
		conceptLevel1 = new StringBuilder();
		StringBuilder conceptLevel2 = new StringBuilder("今日时间段【"+startTime+"——"+finishTime+"】");
		conceptLevel2 = new StringBuilder(gameName+"数据监控预警【"+endDs+"】【"+startTime+"至"+finishTime+"】\r\n");
		for (MonitorMetricsMsg monitorMetricsMsg : monitorMetricsMsgList) {
			if( !monitorMetricsMsg.getIsEmail() && !monitorMetricsMsg.getIsPhone() ){
				continue;
			}
			
			if("dnu".equals(monitorMetricsMsg.getColumnName())) {
				monitorMetricsMsg.setColumnName("新增用户数");
			}else if("dau".equals(monitorMetricsMsg.getColumnName())) {
				monitorMetricsMsg.setColumnName("活跃用户数");
			}else if("payer".equals(monitorMetricsMsg.getColumnName())) {
				monitorMetricsMsg.setColumnName("付费用户数");
			}else if("totalAmount".equals(monitorMetricsMsg.getColumnName())) {
				monitorMetricsMsg.setColumnName("付费金额");
			}
			
			String str = monitorMetricsMsg.msgStr();
			if(monitorMetricsMsg.getIsEmail()) {
				conceptLevel1.append(str);
			}
			if(monitorMetricsMsg.getIsPhone()) {
				conceptLevel2.append(str+"\r\n");
			}
		}
		
		List<AlarmUserConcat> alarmUserConcats=this.gameAlarmUserService.getAlarmUserConcat(gameId);
		List<String> emails=new ArrayList<String>();
		List<String> phones=new ArrayList<String>();
		if(alarmUserConcats.size()>0){
			for (AlarmUserConcat alarmUserConcat : alarmUserConcats) {
				String email=alarmUserConcat.getEmail();
				String phone=alarmUserConcat.getPhone();
				if(StringUtils.isNotEmpty(email)){
					emails.add(email);
				}
				if(StringUtils.isNotEmpty(phone)){
					phones.add(phone);
				}
			}
		}
		
		if(conceptLevel1.toString().length()>0){
//			System.out.println("邮件:\r\n"+conceptLevel1.toString());
			log.info("MonitorMetricsJob emails:"+emails.size());
			sendMails(emails, subject, conceptLevel1.toString());
		}
		if(conceptLevel2.toString().length()>50){
//			System.out.println("短信:\r\n"+conceptLevel2.toString());
			log.info("MonitorMetricsJob phones:"+phones.size());
			sendPhones(phones, conceptLevel2.toString());
		}
	}
	
	private static void sendMails(List<String> emails, String subject, String concept){
		MailSenderProxy mailSenderProxy=new MailSenderProxy();
		mailSenderProxy.sendMail(emails, subject, concept);
	}
	
	private static void sendPhones(List<String> phones, String concept){
		SmsSenderProxy smsSenderProxy=new SmsSenderProxy();
		smsSenderProxy.send(phones, concept+"【互爱BI】");
	}

}
