package com.hoolai.bi.tracking.web;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hoolai.bi.tracking.log.StatsManager;


public class TrackingListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("The Tracking Server Has Been Started Up");
		ResourceBundle bundle = ResourceBundle.getBundle("trackingConfig/tracking",Locale.getDefault());
		String game_id  = bundle.getString("game_id");
		String sn_id  = bundle.getString("sn_id");
//		String scribed_host  = bundle.getString("scribed_host");
		int core_pool_size  = Integer.parseInt(bundle.getString("core_pool_size"));
		int max_pool_size  = Integer.parseInt(bundle.getString("max_pool_size"));
		int queue_capacity  = Integer.parseInt(bundle.getString("queue_capacity"));
//		if(bundle.containsKey("port")){
//			ScribedJob.port = Integer.parseInt(bundle.getString("port"));
//		}else if(bundle.containsKey("scribed_port")){
//			ScribedJob.port = Integer.parseInt(bundle.getString("scribed_port"));
//		}
		StatsManager.TRACKINGON = bundle.getString("tracking_on");
		StatsManager.GAMEID = game_id;
		
		//TrackServices.init_without_id(core_pool_size, max_pool_size, queue_capacity);
	}
}
