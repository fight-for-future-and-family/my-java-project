package com.hoolai.bi;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class IpResolve extends  UDF{

//	static Properties upload = new Properties();
//	static {
//		try {
//			upload.load(IpResolve.class.getClassLoader().getResourceAsStream(
//					"contain.properties"));
//		} catch (IOException e) {
//			System.out.println("the properties  file can not find !");
//		}
//	}
//	static String host = upload.getProperty("host");
//	static String port = upload.getProperty("port");
	static String host = "192.168.90.83";
	static String port = "7080";
	static HttpGet request;
	static HttpClient hc;
	static HttpResponse response;
	static String result;
    
	public static void main(String[] sdf) throws Exception {
		try {
                new IpResolve().evaluate(new Text("1.2.3.4"));

		} catch (Exception e) {
			System.out.println("the remote response is error !");
		}
	}

	public Text evaluate(Text str) throws Exception{
		request = new HttpGet("http://" + host + ":" + port + "/" + str.toString());
		hc = new DefaultHttpClient();
		response = hc.execute(request);

		try {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
				JSONObject identify = new JSONObject(result.toString());
				result=identify.getString("country") + "-"+ identify.getString("stateprov") + "-"+ identify.getString("city");
				System.out.println(result);
			}
		} catch (Exception e) {
			System.out.println("the remote response is error !");
		}

		return new Text(result);

	}

}
