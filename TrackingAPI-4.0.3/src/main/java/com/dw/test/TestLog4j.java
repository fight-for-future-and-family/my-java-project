package com.dw.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestLog4j {

//	 static{
//	 try {
//	 InputStream globleIn =
//	 TestLog4j.class.getClassLoader().getResourceAsStream("log4j-bi.properties");
//	 Properties globleProperties = new Properties();
//	 if(globleIn==null){
//	 throw new RuntimeException("log4j-bi.properties is not exists!");
//	 }
//	 globleProperties.load(globleIn);
//	
//	 PropertyConfigurator.configure(globleProperties);
//	 } catch (IOException e) {
//	 e.printStackTrace();
//	 }
//	 }

	private static final Logger counter = Logger.getLogger("counter");

	private static final Logger dau = Logger.getLogger("dau");

	private static final Logger demographic = Logger.getLogger("demographic");

	private static final Logger economy = Logger.getLogger("economy");

	private static final Logger friends = Logger.getLogger("friends");

	private static final Logger install = Logger.getLogger("install");

	private static final Logger message = Logger.getLogger("message");

	private static final Logger messageClick = Logger.getLogger("messageClick");

	private static final Logger milestone = Logger.getLogger("milestone");

	private static final Logger payment = Logger.getLogger("payment");

	private static final Logger gameInfo = Logger.getLogger("gameInfo");

	private static final Logger stdout = Logger.getLogger("stdout");

	public static void main(String args[]) {
		for (int i = 0; i < 6; i++) {
			String info = "info-" + i;
			counter.debug(info);
			dau.info(info);
			demographic.info(info);
			economy.info(info);
			friends.info(info);
			install.info(info);
			message.info(info);
			messageClick.info(info);
			milestone.info(info);
			payment.info(info);
			gameInfo.info(info);
			stdout.info(info);
		}
	}

}
