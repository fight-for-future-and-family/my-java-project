package com.hoolai.bi.tracking.log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dw.metrics.AdGameInstall;
import com.dw.metrics.Beat;
import com.dw.metrics.Bugly;
import com.dw.metrics.Counter;
import com.dw.metrics.Dau;
import com.dw.metrics.Demographic;
import com.dw.metrics.Economy;
import com.dw.metrics.Eqbeat;
import com.dw.metrics.Friends;
import com.dw.metrics.GameInfo;
import com.dw.metrics.Install;
import com.dw.metrics.Landing;
import com.dw.metrics.Message;
import com.dw.metrics.MessageClick;
import com.dw.metrics.Milestone;
import com.dw.metrics.Mirror;
import com.dw.metrics.Online;
import com.dw.metrics.Payment;
import com.dw.metrics.Promote;
import com.dw.metrics.TrackBase;
import com.dw.services.TrackServices;
import com.hoolai.bi.tracking.tools.Constants;
import com.hoolai.bi.tracking.tools.HttpUtils;
import com.hoolai.bi.tracking.tools.RegularUtils;
import com.hoolai.bi.tracking.web.AdTrackingCallbackManager;
import com.hoolai.bi.tracking.web.AdTrackingCallbackManager.AdInstallNotifer;
import com.hoolai.util.DateUtils;

/**
 * 
 * @author hoolai
 * 
 */
public class StatsManager {
	
	private static final List<String> METRICS_LIST=new ArrayList<String>();
	
	static{
		String[] legal_metrics_list = new String[] { 
				"Counter", "Dau","Demographic", "Economy", "Friends", 
				"Install", "Message","MessageClick", "Milestone", 
				"Payment", "GameInfo", "Bugly", "Online", "Promote", 
				"Eqbeat", "Landing", "Beat", "AdGameInstall","Mirror" };
		List<String> tmpList=Arrays.asList(legal_metrics_list);
		METRICS_LIST.addAll(tmpList);
	}

	public static String GAMEID = "";
	public static String HOST = "";
	public static String CORESIZE = "";
	public static String MAXSIZE = "";
	public static String QUEUESIZE = "";
	public static String TRACKINGON = ""; // 1=记录数据，0=不记录数据
	public static final Integer KEY = 1;
	public static final Integer KEY2 = 2;
	
	private static final AdTrackingCallbackManager ADTRACKING_CALLBACK_MANAGER=AdTrackingCallbackManager.getInstance();
	
	public static Integer log(String metric, HttpServletRequest request) {
		if (StatsManager.TRACKINGON.equals("1") && metrics_validation(metric)) {
			if (request.getParameter("snid") != null
					&& !request.getParameter("snid").equals("")
					&& request.getParameter("clientid") != null
					&& !request.getParameter("clientid").equals("")
					&& RegularUtils.isNumber(request.getParameter("snid"))
					&& RegularUtils.isNumber(request.getParameter("clientid"))) {
				TrackBase base = getObject(metric, request);
				String clientid = request.getParameter("clientid");
				String snid = request.getParameter("snid");
				if (base != null) {
					if(base instanceof Install){
						
						String snId = get_param(request, "snid");
						String gameId = GAMEID;
						String clientId = get_param(request, "clientid");
						String ds = get_param(request, "date");
						
						AdInstallNotifer adInstallNotifer = new AdInstallNotifer(snId, gameId, clientId, ds, (Install)base);
						
						ADTRACKING_CALLBACK_MANAGER.tracking(adInstallNotifer);
					}
					if (!get_param(request, "timekey").equals("") && KEY.equals(Integer.valueOf(get_param(request,"timekey")))) {
						if (!get_param(request, "date").equals("")&&!get_param(request, "time").equals("")&&DateUtils.verifyDateForSendingData(request.getParameter("date"))) {
							TrackServices.add(snid, clientid, GAMEID, base, request.getParameter("date"));
							return 0;
						} else {
							TrackServices.add(snid, clientid, GAMEID, base);
							return 1;
						}
					} else if (!get_param(request, "timekey").equals("") && KEY2.equals(Integer.valueOf(get_param(request,"timekey")))){
						if(!get_param(request, "time").equals("")&&DateUtils.isValidTimeStr(request.getParameter("time"))){
							Map<String, String> map = new HashMap<String, String>();
							map = DateUtils.convertTimeToDateStr(request.getParameter("time"));
							TrackServices.add(snid, clientid, GAMEID, base, map.get("date"));
							return 0;
						} else{
							TrackServices.add(snid, clientid, GAMEID, base);
							return 1;
						}							
					} else {
						TrackServices.add(snid, clientid, GAMEID, base);
						return 2;
					}
				} else {
					if (request.getParameter("metric") != null && request.getParameter("metric").equals("Counter")) {
						return 3;
					} else if (request.getParameter("metric") != null && request.getParameter("metric").equals("Dau")) {
						return 4;
					} else if (request.getParameter("metric") != null && request.getParameter("metric").equals("Demographic")) {
						return 5;
					} else if (request.getParameter("metric") != null && request.getParameter("metric").equals("Economy")) {
						return 6;
					} else if (request.getParameter("metric") != null && request.getParameter("metric").equals("Friends")) {
						return 7;
					} else if (request.getParameter("metric") != null && request.getParameter("metric").equals("Install")) {
						return 8;
					} else if (request.getParameter("metric") != null && request.getParameter("metric").equals("Message")) {
						return 9;
					} else if (request.getParameter("metric") != null && request.getParameter("metric").equals("MessageClick")) {
						return 10;
					} else if (request.getParameter("metric") != null && request.getParameter("metric").equals("Milestone")) {
						return 11;
					} else if (request.getParameter("metric") != null && request.getParameter("metric").equals("Payment")) {
						return 12;
					} else if (request.getParameter("metric") != null && request.getParameter("metric").equals("GameInfo")) {
						return 13;
					}else if (request.getParameter("metric") != null && request.getParameter("metric").equals("Bugly")) {
						return 15;
					} else {
						return 14;
					}
				}
			} else {
				return 15;
			}
		} else {
			return 14;
			// Tracking has been disabled
		}
	}

	private static boolean metrics_validation(String metrics) {
		return METRICS_LIST.contains(metrics);
	}

	private static TrackBase getObject(String metric, HttpServletRequest request) {
		if (metric.equalsIgnoreCase("Counter")) {
			return build_counter(request);
		} else if (metric.equalsIgnoreCase("Dau")) {
			return build_dau(request);
		} else if (metric.equalsIgnoreCase("Install")) {
			return build_install(request);
		} else if (metric.equalsIgnoreCase("Economy")) {
			return build_economy(request);
		} else if (metric.equalsIgnoreCase("Payment")) {
			return build_payment(request);
		} else if (metric.equalsIgnoreCase("Demographic")) {
			return build_demographic(request);
		} else if (metric.equalsIgnoreCase("Friends")) {
			return build_friends(request);
		} else if (metric.equalsIgnoreCase("Message")) {
			return build_message(request);
		} else if (metric.equalsIgnoreCase("MessageClick")) {
			return build_messageclick(request);
		} else if (metric.equalsIgnoreCase("Milestone")) {
			return build_milestone(request);
		} else if (metric.equalsIgnoreCase("GameInfo")) {
			return build_gameinfo(request);
		}else if (metric.equalsIgnoreCase("Bugly")) {
			return build_bugly(request);
		}else if (metric.equalsIgnoreCase("Online")){
			return build_online(request);
		}else if(metric.equalsIgnoreCase("Promote")){
			return build_promote(request);
		}else if(metric.equalsIgnoreCase("Eqbeat")){
			return build_eqbeat(request);
		}else if(metric.equalsIgnoreCase("Landing")){
			return build_landing(request);
		}else if(metric.equalsIgnoreCase("Mirror")){
			return build_landing(request);
		}else if(metric.equalsIgnoreCase("Beat")){
			return build_beat(request);
		}else if(metric.equalsIgnoreCase("AdGameInstall")){
			return build_adGameInstall(request);
		}
		
		return null;
	}
	
	private static TrackBase build_beat(HttpServletRequest request){
		Beat beat = null;
		if(!get_param(request, "userid").equals("")){
			beat = new Beat();
			beat.setUserId(get_param(request, "userid"));
			beat.setUdid(get_param(request, "udid"));
			beat.setKingdom(get_param(request, "kingdom"));
			beat.setPhylum(get_param(request, "phylum"));
			beat.setClassfield(get_param(request, "classfield"));
			beat.setFamily(get_param(request, "family"));
			beat.setGenus(get_param(request, "genus"));
			beat.setValue(get_param(request, "value"));
			beat.setIp(get_param(request, "ip"));
			beat.setCreative(get_param(request, "creative"));
			beat.setExtra(get_param(request, "extra"));
			beat.setDate(get_param(request, "date"));
			beat.setTime(get_param(request, "time"));
		} else {
			// System.out.println("userid not found or counter not found");
		}
		return beat;
	}
	private static TrackBase build_mirror(HttpServletRequest request){
		Mirror mirror = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		mirror = new Mirror();
		mirror.setUserid(get_param(request, "userid"));
		mirror.setRoleid(get_param(request, "roleid"));
		mirror.setRole_name(get_param(request, "role_name"));
		mirror.setLevel(get_param(request, "level"));
		mirror.setExp(get_param(request, "exp"));
		mirror.setCreatetime(get_param(request, "createtime"));
		mirror.setUpdatetime(get_param(request, "updatetime"));
		mirror.setYb(get_param(request, "yb"));
		mirror.setExtra(get_param(request, "extra"));
		mirror.setCurrency_1(get_param(request, "currency_1"));
		mirror.setCurrency_2(get_param(request, "currency_2"));
		mirror.setCurrency_3(get_param(request, "currency_3"));
		mirror.setStatus_1(get_param(request, "status_1"));
		mirror.setStatus_2(get_param(request, "status_2"));
		mirror.setStatus_3(get_param(request, "status_3"));
		mirror.setStatus_4(get_param(request, "status_4"));
		mirror.setStatus_5(get_param(request, "status_5"));
		mirror.setStatus_6(get_param(request, "status_6"));
		mirror.setStatus_7(get_param(request, "status_7"));
		mirror.setStatus_8(get_param(request, "status_8"));
		mirror.setStatus_9(get_param(request, "status_9"));
		mirror.setStatus_10(get_param(request, "status_10"));
		mirror.setItem_1(get_param(request, "item_1"));
		mirror.setItem_2(get_param(request, "item_2"));
		mirror.setItem_3(get_param(request, "item_3"));
		mirror.setItem_4(get_param(request, "item_4"));
		mirror.setItem_5(get_param(request, "item_5"));
		mirror.setMirror(get_param(request, "mirror"));
		mirror.setCreatetime(sdf.format(date));
		mirror.setUpdatetime(sdf.format(date));
		return mirror;
	}
	private static TrackBase build_landing(HttpServletRequest request){
		Landing landing = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		landing = new Landing();
		landing.setCookie(get_param(request, "cookie"));
		landing.setAct(get_param(request, "act"));
		landing.setCurrentUrl(get_param(request, "currentUrl"));
		landing.setReferer(get_param(request, "referer"));
		landing.setDownloadUrl(get_param(request, "downloadUrl"));
		landing.setDate(sdf.format(date));
		sdf = new SimpleDateFormat("HH:mm:ss");
		landing.setTime(sdf.format(date));
		return landing;
	}
	
	private static TrackBase build_promote(HttpServletRequest request){
		Promote promote = null;
		if(!get_param(request, "udid").equals("")){
			promote = new Promote();
			promote.setDate(get_param(request, "date"));
			promote.setTime(get_param(request, "time"));
			promote.setUdid(get_param(request, "udid"));
		}
		return promote;
	}
	
	private static TrackBase build_adGameInstall(HttpServletRequest request){
		AdGameInstall adGameInstall = null;
		if(!get_param(request, "udid").equals("") && !get_param(request, "family").equals("")){
			adGameInstall = new AdGameInstall();
			adGameInstall.setAdGameInstallDate(get_param(request, "adGameInstallDate"));
			adGameInstall.setAdGameInstallTime(get_param(request, "adGameInstallTime"));
			adGameInstall.setUdid(get_param(request, "udid"));
		}
		return adGameInstall;
	}
	
	private static TrackBase build_eqbeat(HttpServletRequest request){
		Eqbeat eqbeat = null;
		if(!get_param(request, "udid").equals("")){
			eqbeat = new Eqbeat();
			eqbeat.setEqbeatDate(get_param(request, "eqbeatDate"));
			eqbeat.setEqbeatTime(get_param(request, "eqbeatTime"));
			eqbeat.setUdid(get_param(request, "udid"));
		}
		return eqbeat;
	}
	
	private static TrackBase build_online(HttpServletRequest request){
		Online online = null;
		if(!get_param(request, "userid").equals("")){
			online = new Online();
			online.setUserid(get_param(request, "userid"));
			online.setUdid(get_param(request, "udid"));
			online.setAffiliate(get_param(request, "affiliate"));
			online.setUserLevel(get_param(request, "userLevel"));
			online.setCreative(get_param(request, "creative"));
			online.setPhylum(get_param(request, "phylum"));
			online.setOnlineDate(get_param(request, "onlineDate"));
			online.setOnlineTime(get_param(request, "onlineTime"));
			online.setIp(get_param(request, "ip"));
			online.setExtra(get_param(request, "extra"));
			if (!get_param(request, "timekey").equals("") && KEY.equals(Integer.valueOf(get_param(request, "timekey")))) {
				online.setOnlineDate(DateUtils.verifyDate(get_param(request, "date")));
				online.setOnlineTime(DateUtils.verifyTime(get_param(request, "time")));
			} else if (!get_param(request, "timekey").equals("")&& KEY2.equals(Integer.valueOf(get_param(request, "timekey")))){
				Map<String, String> map = DateUtils.convertTimeToDateStr(get_param(request, "time"));
				online.setOnlineDate(map.get("date"));
				online.setOnlineTime(map.get("time"));
			} else {
				online.setOnlineDate(DateUtils.getDate());
				online.setOnlineTime(DateUtils.getTime());
			}
		} else {
			// System.out.println("userid not found or counter not found");
		}
		
		return online;
	} 

	private static TrackBase build_bugly(HttpServletRequest request) {
		Bugly bugly = null;
		if (!get_param(request, "userid").equals("")) {
			bugly = new Bugly();
			bugly.setUserId(get_param(request, "userid"));
			bugly.setUdid(get_param(request, "udid"));
			bugly.setLanguage(get_param(request, "language"));
			bugly.setBuglyType(get_param(request, "bugly_type"));
			bugly.setBuglyMessage(get_param(request, "bugly_message"));
			bugly.setBuglyStack(get_param(request, "bugly_stack"));
			bugly.setIp(get_param(request, "ip"));
			bugly.setClientVersion(get_param(request, "client_version"));
			bugly.setCreative(get_param(request, "creative"));
			bugly.setPhone(get_param(request, "phone"));
			bugly.setSystem(get_param(request, "system"));
			bugly.setExtra(get_param(request, "extra"));
			if (!get_param(request, "timekey").equals("") && KEY.equals(Integer.valueOf(get_param(request, "timekey")))) {
				bugly.setBuglyDate(DateUtils.verifyDate(get_param(request, "date")));
				bugly.setBuglyTime(DateUtils.verifyTime(get_param(request, "time")));
			} else if (!get_param(request, "timekey").equals("")&& KEY2.equals(Integer.valueOf(get_param(request, "timekey")))){
				Map<String, String> map = DateUtils.convertTimeToDateStr(get_param(request, "time"));
				bugly.setBuglyDate(map.get("date"));
				bugly.setBuglyTime(map.get("time"));
			} else {
				bugly.setBuglyDate(DateUtils.getDate());
				bugly.setBuglyTime(DateUtils.getTime());
			}
		} else {
			// System.out.println("userid not found or counter not found");
		}
			
		
		return bugly;
	}

	private static TrackBase build_gameinfo(HttpServletRequest request) {
		GameInfo gameinfo = null;

		if (!get_param(request, "userid").equals("")
				&& !get_param(request, "gameinfo").equals("")) {
			gameinfo = new GameInfo();
			gameinfo.setUserId(get_param(request, "userid"));
			gameinfo.setGameinfo(get_param(request, "gameinfo"));

			gameinfo.setUserLevel(get_param(request, "user_level"));
			gameinfo.setKingdom(get_param(request, "kingdom"));
			gameinfo.setPhylum(get_param(request, "phylum"));
			gameinfo.setClassfield(get_param(request, "classfield"));
			gameinfo.setFamily(get_param(request, "family"));
			gameinfo.setGenus(get_param(request, "genus"));
			gameinfo.setExtra(get_param(request, "extra"));
			gameinfo.setValue(get_param(request, "value"));

			if (!get_param(request, "timekey").equals("")&& KEY.equals(Integer.valueOf(get_param(request, "timekey")))) {
				gameinfo.setGameinfoDate(DateUtils.verifyDate(get_param(request, "date")));
				gameinfo.setGameinfoTime(DateUtils.verifyTime(get_param(request, "time")));
				
			} else if (!get_param(request, "timekey").equals("")&& KEY2.equals(Integer.valueOf(get_param(request, "timekey")))){
				Map<String, String> map = DateUtils.convertTimeToDateStr(get_param(request, "time"));
				gameinfo.setGameinfoDate(map.get("date"));
				gameinfo.setGameinfoTime(map.get("time"));
			} else {
				gameinfo.setGameinfoDate(DateUtils.getDate());
				gameinfo.setGameinfoTime(DateUtils.getTime());
			}
			
			//临时解决办法，帮助项目组报送激活信息到SDK
			if(gameinfo!=null && !get_param(request, "snid").equals("34") && gameinfo.getGameinfo()!=null 
					&& gameinfo.getGameinfo().equals("open_app")
						&& gameinfo.getValue()!=null && gameinfo.getExtra()!=null){
				
				String[] extras = gameinfo.getExtra().split(",");
				
				if(extras!=null && extras.length >=4){
					if(extras[1].split(":")!=null && extras[2].split(":")!=null && extras[3].split(":")!=null &&
							extras[1].split(":").length >= 1 && extras[2].split(":").length >= 1 && extras[3].split(":").length >= 1){
						
						String openuuid = extras[1].split(":")[1];
						String mac = extras[2].split("mac")[1].substring(1);
						String idfa = extras[3].split(":")[1];
						
						System.out.println("000, prepare to send a activation to DragonForver, openuuid is: " + openuuid + ", date is: " + gameinfo.getGameinfoDate() + 
								", time is: " + gameinfo.getGameinfoTime() + ", macid is: " + mac + ", idfa is: " + idfa);
						
						Map<String, String> params = new HashMap<String, String>();
						params.put("openuuid", openuuid);
						params.put("date", gameinfo.getGameinfoDate());
						params.put("time", gameinfo.getGameinfoTime());
						params.put("mac", mac);
						params.put("idfa", idfa);
						HttpUtils.pushData(Constants.URL, params);
						System.out.println("111, successfully send a activation to DragonForever, openuuid is: " + params.get("openuuid") + ", date is: " + params.get("date") + 
								", time is: " + params.get("time") + ", macid is: " + params.get("mac") + ", idfa is: " + params.get("idfa"));
					}
				}
			}
			
		} else {
			// System.out.println("userid not found or gameinfo not found");
		}
		return gameinfo;
	}

	private static Counter build_counter(HttpServletRequest request) {
		Counter counter = null;

		if (!get_param(request, "userid").equals("")
				&& !get_param(request, "counter").equals("")) {
			counter = new Counter();
			counter.setUserId(get_param(request, "userid"));
			counter.setCounter(get_param(request, "counter"));
			counter.setUserLevel(get_param(request, "user_level"));
			counter.setKingdom(get_param(request, "kingdom"));
			counter.setPhylum(get_param(request, "phylum"));
			counter.setClassfield(get_param(request, "classfield"));
			counter.setFamily(get_param(request, "family"));
			counter.setGenus(get_param(request, "genus"));
			counter.setExtra(get_param(request, "extra"));
			counter.setValue(get_param(request, "value"));

			if (!get_param(request, "timekey").equals("") && KEY.equals(Integer.valueOf(get_param(request, "timekey")))) {
				counter.setCounterDate(DateUtils.verifyDate(get_param(request, "date")));
				counter.setCounterTime(DateUtils.verifyTime(get_param(request, "time")));
			} else if (!get_param(request, "timekey").equals("")&& KEY2.equals(Integer.valueOf(get_param(request, "timekey")))){
				Map<String, String> map = DateUtils.convertTimeToDateStr(get_param(request, "time"));
				counter.setCounterDate(map.get("date"));
				counter.setCounterTime(map.get("time"));
			} else {
				counter.setCounterDate(DateUtils.getDate());
				counter.setCounterTime(DateUtils.getTime());
			}
		} else {
			// System.out.println("userid not found or counter not found");
		}
		return counter;
	}

	private static Message build_message(HttpServletRequest request) {
		Message message = null;

		if (!get_param(request, "userid").equals("")
				&& !get_param(request, "channel").equals("")) {
			message = new Message();
			message.setUserId(get_param(request, "userid"));
			message.setToUid(get_param(request, "to_userid"));
			message.setChannel(get_param(request, "channel"));
			message.setStatus(get_param(request, "status"));

			message.setKingdom(get_param(request, "kingdom"));
			message.setPhylum(get_param(request, "phylum"));
			message.setClassfield(get_param(request, "classfield"));
			message.setFamily(get_param(request, "family"));
			message.setGenus(get_param(request, "genus"));
			message.setExtra(get_param(request, "extra"));

			if (!get_param(request, "timekey").equals("") && KEY.equals(Integer.valueOf(get_param(request, "timekey")))) {
				message.setMessageDate(DateUtils.verifyDate(get_param(request, "date")));
				message.setMessageTime(DateUtils.verifyTime(get_param(request, "time")));
			} else if (!get_param(request, "timekey").equals("")&& KEY2.equals(Integer.valueOf(get_param(request, "timekey")))){
				Map<String, String> map = DateUtils.convertTimeToDateStr(get_param(request, "time"));
				message.setMessageDate(map.get("date"));
				message.setMessageTime(map.get("time"));
			} else {
				message.setMessageDate(DateUtils.getDate());
				message.setMessageTime(DateUtils.getTime());
			}
		} else {
			// System.out.println("userid not found or channel not found");
		}
		return message;
	}

	private static MessageClick build_messageclick(HttpServletRequest request) {
		MessageClick messageclick = null;

		if (!get_param(request, "userid").equals("")
				&& !get_param(request, "channel").equals("")) {
			messageclick = new MessageClick();
			messageclick.setUserId(get_param(request, "userid"));
			messageclick.setFromUid(get_param(request, "from_userid"));
			messageclick.setChannel(get_param(request, "channel"));
			messageclick.setStatus(get_param(request, "status"));

			messageclick.setKingdom(get_param(request, "kingdom"));
			messageclick.setPhylum(get_param(request, "phylum"));
			messageclick.setClassfield(get_param(request, "classfield"));
			messageclick.setFamily(get_param(request, "family"));
			messageclick.setGenus(get_param(request, "genus"));
			messageclick.setExtra(get_param(request, "extra"));

			messageclick.setSendDate(DateUtils.verifyDate(get_param(request, "send_date")));
			messageclick.setSendTime(DateUtils.verifyTime(get_param(request, "send_time")));

			if (!get_param(request, "timekey").equals("")&& KEY.equals(Integer.valueOf(get_param(request, "timekey")))) {
				messageclick.setClickDate(DateUtils.verifyDate(get_param(request, "date")));
				messageclick.setClickTime(DateUtils.verifyTime(get_param(request, "time")));
			} else if (!get_param(request, "timekey").equals("")&& KEY2.equals(Integer.valueOf(get_param(request, "timekey")))){
				Map<String, String> map = DateUtils.convertTimeToDateStr(get_param(request, "time"));
				messageclick.setClickDate(map.get("date"));
				messageclick.setClickTime(map.get("time"));
			} else {
				messageclick.setClickDate(DateUtils.getDate());
				messageclick.setClickTime(DateUtils.getTime());
			}
		} else {
			// System.out.println("userid not found or channel not found");
		}
		return messageclick;
	}

	private static Dau build_dau(HttpServletRequest request) {
		Dau dau = null;

		if (!get_param(request, "userid").equals("")) {
			dau = new Dau();
			dau.setUserId(get_param(request, "userid"));

			dau.setSource(get_param(request, "source"));
			dau.setAffiliate(get_param(request, "affiliate"));
			dau.setCreative(get_param(request, "creative"));
			dau.setFamily(get_param(request, "family"));
			dau.setGenus(get_param(request, "genus"));

			dau.setIp(get_param(request, "ip"));
			dau.setFromUid(get_param(request, "from_uid"));
			dau.setExtra(get_param(request, "extra"));

			if (!get_param(request, "timekey").equals("")&& KEY.equals(Integer.valueOf(get_param(request, "timekey")))) {
				dau.setDauDate(DateUtils.verifyDate(get_param(request, "date")));
				dau.setDauTime(DateUtils.verifyTime(get_param(request, "time")));
			} else if (!get_param(request, "timekey").equals("")&& KEY2.equals(Integer.valueOf(get_param(request, "timekey")))){
				Map<String, String> map = DateUtils.convertTimeToDateStr(get_param(request, "time"));
				dau.setDauDate(map.get("date"));
				dau.setDauTime(map.get("time"));
			} else {
				dau.setDauDate(DateUtils.getDate());
				dau.setDauTime(DateUtils.getTime());
			}
		} else {
			// System.out.println("userid not found");
		}
		return dau;
	}

	private static Install build_install(HttpServletRequest request) {
		Install install = null;

		if (!get_param(request, "userid").equals("")) {
			install = new Install();
			install.setUserId(get_param(request, "userid"));

			install.setSource(get_param(request, "source"));
			install.setAffiliate(get_param(request, "affiliate"));
			install.setCreative(get_param(request, "creative"));
			install.setFamily(get_param(request, "family"));
			install.setGenus(get_param(request, "genus"));

			install.setFromUid(get_param(request, "from_uid"));
			install.setExtra(get_param(request, "extra"));
			install.setFromUid(get_param(request, "ip"));

			if (!get_param(request, "timekey").equals("")&& KEY.equals(Integer.valueOf(get_param(request, "timekey")))) {
				install.setInstallDate(DateUtils.verifyDate(get_param(request, "date")));
				install.setInstallTime(DateUtils.verifyTime(get_param(request, "time")));
			} else if (!get_param(request, "timekey").equals("")&& KEY2.equals(Integer.valueOf(get_param(request, "timekey")))){
				Map<String, String> map = DateUtils.convertTimeToDateStr(get_param(request, "time"));
				install.setInstallDate(map.get("date"));
				install.setInstallTime(map.get("time"));
			} else {
				install.setInstallDate(DateUtils.getDate());
				install.setInstallTime(DateUtils.getTime());
			}
		} else {
			// System.out.println("userid not found");
		}
		return install;
	}

	private static Demographic build_demographic(HttpServletRequest request) {
		Demographic demo = null;

		if (!get_param(request, "userid").equals("")) {
			demo = new Demographic();
			demo.setUserId(get_param(request, "userid"));
			demo.setAge(get_param(request, "age"));
			demo.setName(get_param(request, "name"));
			demo.setGender(get_param(request, "gender"));
			demo.setCity(get_param(request, "city"));
			demo.setState(get_param(request, "state"));
			demo.setCountry(get_param(request, "country"));
			demo.setLocale(get_param(request, "locale"));
			demo.setPicurl(get_param(request, "picurl"));
			demo.setIp(get_param(request, "ip"));
			demo.setBirthday(get_param(request, "birthday"));
			demo.setEmail(get_param(request, "email"));
			demo.setFirst(get_param(request, "first"));
			demo.setLast(get_param(request, "last"));
			demo.setIsVip(get_param(request, "is_vip"));
			demo.setVipType(get_param(request, "vip_type"));
			demo.setExtra(get_param(request, "extra"));

			if (!get_param(request, "timekey").equals("")&& KEY.equals(Integer.valueOf(get_param(request, "timekey")))) {
				demo.setDemographicDate(DateUtils.verifyDate(get_param(request, "date")));
				demo.setDemographicTime(DateUtils.verifyTime(get_param(request, "time")));
			} else if (!get_param(request, "timekey").equals("")&& KEY2.equals(Integer.valueOf(get_param(request, "timekey")))){
				Map<String, String> map = DateUtils.convertTimeToDateStr(get_param(request, "time"));
				demo.setDemographicDate(map.get("date"));
				demo.setDemographicTime(map.get("time"));
			} else {
				demo.setDemographicDate(DateUtils.getDate());
				demo.setDemographicTime(DateUtils.getTime());
			}
		} else {
			// System.out.println("userid not found");
		}
		return demo;
	}

	private static Economy build_economy(HttpServletRequest request) {
		Economy economy = null;

		if (!get_param(request, "userid").equals("")
				&& !get_param(request, "amount").equals("")) {
			economy = new Economy();
			economy.setUserId(get_param(request, "userid"));

			economy.setCurrency(get_param(request, "currency"));
			economy.setAmount(get_param(request, "amount"));
			economy.setValue(get_param(request, "value"));

			economy.setKingdom(get_param(request, "kingdom"));
			economy.setPhylum(get_param(request, "phylum"));
			economy.setClassfield(get_param(request, "classfield"));
			economy.setFamily(get_param(request, "family"));
			economy.setGenus(get_param(request, "genus"));
			economy.setExtra(get_param(request, "extra"));

			if (!get_param(request, "timekey").equals("")&& KEY.equals(Integer.valueOf(get_param(request, "timekey")))) {
				economy.setEconomyDate(DateUtils.verifyDate(get_param(request, "date")));
				economy.setEconomyTime(DateUtils.verifyTime(get_param(request, "time")));
			} else if (!get_param(request, "timekey").equals("")&& KEY2.equals(Integer.valueOf(get_param(request, "timekey")))){
				Map<String, String> map = DateUtils.convertTimeToDateStr(get_param(request, "time"));
				economy.setEconomyDate(map.get("date"));
				economy.setEconomyTime(map.get("time"));
			} else {
				economy.setEconomyDate(DateUtils.getDate());
				economy.setEconomyTime(DateUtils.getTime());
			}
		} else {
			// System.out.println("userid not found or amount not found");
		}
		return economy;
	}

	private static Payment build_payment(HttpServletRequest request) {
		Payment payment = null;

		if (!get_param(request, "userid").equals("")
				&& !get_param(request, "amount").equals("")) {
			payment = new Payment();
			payment.setUserId(get_param(request, "userid"));

			payment.setCurrency(get_param(request, "currency"));
			payment.setAmount(get_param(request, "amount"));
			payment.setProvider(get_param(request, "provider"));
			payment.setIp(get_param(request, "ip"));
			payment.setStatus(get_param(request, "status"));
			payment.setTransactionid(get_param(request, "transactionid"));
			payment.setValue2(get_param(request, "value2"));
			payment.setKingdom(get_param(request, "kingdom"));
			payment.setPhylum(get_param(request, "phylum"));
			payment.setExtra(get_param(request, "extra"));

			if (!get_param(request, "timekey").equals("")&& KEY.equals(Integer.valueOf(get_param(request, "timekey")))) {
				payment.setPaymentDate(DateUtils.verifyDate(get_param(request, "date")));
				payment.setPaymentTime(DateUtils.verifyTime(get_param(request, "time")));
			} else if (!get_param(request, "timekey").equals("")&& KEY2.equals(Integer.valueOf(get_param(request, "timekey")))){
				Map<String, String> map = DateUtils.convertTimeToDateStr(get_param(request, "time"));
				payment.setPaymentDate(map.get("date"));
				payment.setPaymentTime(map.get("time"));
			} else {
				payment.setPaymentDate(DateUtils.getDate());
				payment.setPaymentTime(DateUtils.getTime());
			}
		} else {
			// System.out.println("userid not found or amount not found");
		}
		return payment;
	}

	private static Friends build_friends(HttpServletRequest request) {
		Friends friends = null;

		if (!get_param(request, "userid").equals("")) {
			friends = new Friends();
			friends.setUserId(get_param(request, "userid"));
			friends.setDeleted(Integer
					.valueOf(get_param(request, "is_deleted")));
			friends.setFriendUid(get_param(request, "friend_uid"));
			friends.setKingdom(get_param(request, "kingdom"));
			friends.setPhylum(get_param(request, "phylum"));
			friends.setClassfield(get_param(request, "classfield"));
			friends.setExtra(get_param(request, "extra"));

			if (!get_param(request, "timekey").equals("")&& KEY.equals(Integer.valueOf(get_param(request, "timekey")))) {
				friends.setFriendDate(DateUtils.verifyDate(get_param(request, "date")));
				friends.setFriendTime(DateUtils.verifyTime(get_param(request, "time")));
			} else if (!get_param(request, "timekey").equals("")&& KEY2.equals(Integer.valueOf(get_param(request, "timekey")))){
				Map<String, String> map = DateUtils.convertTimeToDateStr(get_param(request, "time"));
				friends.setFriendDate(map.get("date"));
				friends.setFriendTime(map.get("time"));
			} else {
				friends.setFriendDate(DateUtils.getDate());
				friends.setFriendTime(DateUtils.getTime());
			}
		} else {
			// System.out.println("userid not found");
		}
		return friends;
	}

	private static Milestone build_milestone(HttpServletRequest request) {
		Milestone milestone = null;

		if (!get_param(request, "userid").equals("")
				&& !get_param(request, "milestone").equals("")) {
			milestone = new Milestone();
			milestone.setUserId(get_param(request, "userid"));
			milestone.setMilestone(get_param(request, "milestone"));
			milestone.setValue(get_param(request, "value"));
			milestone.setExtra(get_param(request, "extra"));

			if (!get_param(request, "timekey").equals("")&& KEY.equals(Integer.valueOf(get_param(request, "timekey")))) {
				milestone.setMilestoneDate(DateUtils.verifyDate(get_param(request,"date")));
				milestone.setMilestoneTime(DateUtils.verifyTime(get_param(request,"time")));
			} else if (!get_param(request, "timekey").equals("")&& KEY2.equals(Integer.valueOf(get_param(request, "timekey")))){
				Map<String, String> map = DateUtils.convertTimeToDateStr(get_param(request, "time"));
				milestone.setMilestoneDate(map.get("date"));
				milestone.setMilestoneTime(map.get("time"));
			} else {
				milestone.setMilestoneDate(DateUtils.getDate());
				milestone.setMilestoneTime(DateUtils.getTime());
			}
		} else {
			// System.out.println("userid not found or milestone not found");
		}
		return milestone;
	}

	public static String get_param(HttpServletRequest request, String key) {
		String value = request.getParameter(key);
		return value != null ? value : "";
	}
}