package com.hoolai.loader.main;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.hoolai.loader.scheduler.Scheduler;
import com.hoolai.loader.schedulerfactory.SchedulerFactory;
import com.hoolai.loader.schedulerfactory.SchedulerFactoryConstant;
import com.hoolai.loader.util.Constant;
import com.hoolai.loader.util.CountLoadUtil;
import com.hoolai.loader.util.DateUtil;
import com.hoolai.loader.util.ValidateUtil;
/**
 * 程序入口
 * @author ruijie
 * @date 2013-9-13
 * @version V1.0
 */
public class Main {
	private static Logger log = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		try {
			//首先验证配置文件是否放正确配置，只要各个 属性不为空就ok
			if (!Constant.validate()) {
				log.error("conf.properties config error,please check!");
				return ;
			}
			
			PropertyConfigurator.configure("./conf/log4j.properties");
			
			
			//  当没有参数传入时，根据配置的命令查看是否有已经存在允许的  loader 包，如果有，那么这个loader将不会启动
			if (args.length == 0) {
				if (!CountLoadUtil.check()) {
					log.info("Already have a loader running now! This will be stop!");
					return ;
				}
				
				Scheduler loadScheduler = SchedulerFactory.create(SchedulerFactoryConstant.load);
				
				//   程序的入口
				if (ValidateUtil.isValid(loadScheduler)) {
					loadScheduler.execute();
				} else {
					log.error("get scheduler error, program is going to stop!");
				}
			} else if(args[0].equals("-uniq")) {
				if (!CountLoadUtil.check()) {
					log.info("Already have a loader running now! This will be stop!");
					return ;
				}
				
				Scheduler uniqLoadScheduler = SchedulerFactory.create(SchedulerFactoryConstant.uniqLoad);
				
				if (ValidateUtil.isValid(uniqLoadScheduler)) {
					uniqLoadScheduler.execute();
				} else {
					log.error("get scheduler error, program is going to stop!");
				}
			} else if(args[0].equals("-day")) {
				Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
				Matcher matcher = pattern.matcher(args[1]);
				if (matcher.matches()) {
					DateUtil.processAloneDayStr = args[1];
					
					Scheduler loadScheduler = SchedulerFactory.create(SchedulerFactoryConstant.load);
					
					if(ValidateUtil.isValid(loadScheduler)){
						loadScheduler.execute();
					} else{
						log.error("get scheduler error, program is going to stop!");
					}
				} else {
					System.out.println("Please input like -day yyyy-MM-dd");
				}
			} else {
				System.out.println("Please input like -day yyyy-MM-dd");
				System.out.println("Please input like -rm xx_xx_xx_yyyy-MM-dd");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
	}
	
	@SuppressWarnings("restriction")
	public static void trackingLoadJars(){
		 // 测试bootstrap classloader 的类加载路径
        try {
//			URL[] urls=sun.misc.Launcher.getBootstrapClassPath().getURLs();
//			for (int i = 0; i < urls.length; i++) {
//			  System.out.println("[Bootstrap] "+urls[i].toExternalForm());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
       
		try {
			//        测试extension classloader 的类加载路径，先打印一个路径，再打印出其parent，然后再打印出类加载路径中的所有jar包
	        System.out.println("-------------------------------------");
	        System.out.println(System.getProperty("java.ext.dirs"));
	        ClassLoader extensionClassloader=ClassLoader.getSystemClassLoader().getParent();
	        System.out.println("the parent of extension classloader : "+extensionClassloader.getParent());
	        System.out.println("extension classloader can use thess jars:");
	        URL[] extURLs = ((URLClassLoader)ClassLoader.getSystemClassLoader().getParent()).getURLs();
	        for (int i = 0; i < extURLs.length; i++) {
	               System.out.println("[ext] "+extURLs[i]);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}       
       
//      测试system classloader 的类加载路径，其实也就时classpath的路径，并打印出它的parent
        System.out.println("-------------------------------------");
        try {
			//System.out.println(System.getProperty("java.class.path"));
			//System.out.println(ClassLoader.getSystemResource(""));
			ClassLoader systemClassloader=ClassLoader.getSystemClassLoader();
			URL[] extURLs = ((URLClassLoader)ClassLoader.getSystemClassLoader()).getURLs();
			 for (int i = 0; i < extURLs.length; i++) {
	               System.out.println("[system] "+extURLs[i]);
	        }
			System.out.println("the parent of system classloader : "+systemClassloader.getParent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
