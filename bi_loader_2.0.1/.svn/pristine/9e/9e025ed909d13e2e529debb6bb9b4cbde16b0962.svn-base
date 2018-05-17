package com.hoolai.loader.schedulerfactory;

import org.apache.log4j.Logger;

import com.hoolai.loader.proxy.SchedulerTimeLogProxy;
import com.hoolai.loader.scheduler.Scheduler;
import com.hoolai.loader.util.StringUtil;

/**
 * Scheduler工厂类
 * @author ruijie
 * @date 2013-10-11
 * @version V1.0
 */
public class SchedulerFactory {
	
	private static Logger log = Logger.getLogger(SchedulerFactory.class);
	
	public static Scheduler create(String str){
		
		SchedulerTimeLogProxy proxy = new SchedulerTimeLogProxy();
		
		Scheduler result = null;
		
		try {
			Scheduler scheduler = (Scheduler)Class.forName(getSchedulerFullName(str)).newInstance();
			
			result = (Scheduler)proxy.bind(scheduler);
		} catch (InstantiationException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	private static String getSchedulerFullName(String str){
		return Scheduler.class.getPackage().getName() + "." + StringUtil.toUpperCaseInitial(str) + Scheduler.class.getSimpleName();
	}
	
}
