package com.hoolai.bi;

import in.ankushs.dbip.api.GeoEntity;

import java.util.Date;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaStringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

public class EnterGate extends UDF {
	
	private static  IpResolve ir=new IpResolve();
	
	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = EnterGate.class.getClassLoader();
		}
	
	
	public  String evaluate(String str) {
		try {
			System.out.println("string method"+new Date());
			
			 HiveConf hcatConf = new HiveConf();
			 hiveDefaultURL = classLoader.getResource("hive-default.xml");
			 hcatConf.set("hive.exec.parallel", "true");
			 SessionState.start(hcatConf);
//			if(null==ipr){
//			ipr = new IpResolve();}
			// ipr.dbIpInit();
			JavaStringObjectInspector stringInspector = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
			String ip = stringInspector.getPrimitiveJavaObject(str);
			GeoEntity temp = ir.lookup(ip);
			String country = temp.getCountry();
			String province = temp.getProvince();
			String city = temp.getCity();
			return country + "-" + province + "-" + city;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("the string method has error");
		}
		return "string method wl";
	}

	public   Text evaluate(Text str) {
		try {
			System.out.println("text method"+new Date());
			
			 HiveConf hcatConf = new HiveConf(); 
			 hcatConf.set("hive.exec.parallel", "true");
			 SessionState.start(hcatConf);
			
//			if(null==ipr){
//			ipr = new IpResolve();}
			JavaStringObjectInspector stringInspector = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
			String ip = stringInspector.getPrimitiveJavaObject(str);
			GeoEntity temp = ir.lookup(ip);
			String country = temp.getCountry();
			String province = temp.getProvince();
			String city = temp.getCity();
			return new Text(country + "-" + province + "-" + city);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("the text method has error");
		}
		return new Text("text method wl");
	}
}
