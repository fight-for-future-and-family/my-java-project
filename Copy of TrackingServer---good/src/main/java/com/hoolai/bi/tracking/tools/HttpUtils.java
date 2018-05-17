package com.hoolai.bi.tracking.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtils {

	public static String pushData(String url, Map<String, String> params){
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuilder sb = new StringBuilder();
		
//		sb.append("openuuid=").append(params.get("openuuid")).append("&date=").
//			append(params.get("date")).append("&time=").append(params.get("time")).
//				append("&mac=").append(params.get("mac")).append("&idfa=").append(params.get("idfa"));
		
		if(params!=null&&!params.isEmpty()){
			for (Entry<String,String> entry : params.entrySet()) {
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		
		String param = sb.toString();
		
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}
	
//	public static void main(String[] args) {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("uuid", "zhangxinyang");
//		params.put("date", "2013-12-24");
//		params.put("time", "12:00:00");
//		
//		System.out.println(params);
//		System.out.println(Constants.URL);
//		
//		String sb = HttpUtils.pushData(Constants.URL, params);
//		System.out.println(sb);
//	}
}
