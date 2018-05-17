package com.hoolai.hdfs.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class TestHttp  {


             public static void main(String[] args)  {
		try {
//			String urlStr = "http://119.29.186.189/tracking/?snid=999&gameid=118&metric=Equipment&clientid=8888888888&ip=4.3.2.1&ds=2017-05-11&jsonString={\"userid\":\"1111111111111\",\"udid\":\"666666666666666666666666\",\"equipment\":\"early\",\"kingdom\":\"342009\",\"phylum\":342009,\"classfield\":\"ok\",\"family\":\"\",\"genus\":\"\",\"value\":\"\",\"simulator\":\"\",\"creative\":\"2\",\"client_version\":\"1.0.0\",\"ratio\":\"1920*1080\",\"phone\":\"LeMobile Le X620\",\"system\":\"Android OS 6.0 / API-23 (HEXCNFN5902303291S/1490722571)\",\"eqDate\":\"2017-05-11\",\"eqTime\":\"16:0:28\",\"extra\":\"ram:2795,CPU:王亮\"}";
			String urlStr = "http://119.29.186.189/tracking/?snid=999&gameid=999&metric=Economy&clientid=1496&ip=119.29.117.106&ds=2016-09-22&jsonString={\"userid\":\"hoolaiyyb_0E8920504903708670C05C39040BE887\",\"udid\":\"9405aeb8d496e47b\",\"roleid\":\"1\",\"currency\":\"yb\",\"amount\":\"50\",\"value\":\"10\",\"kingdom\":\"expenditure\",\"phylum\":\"商城\",\"classfield\":\"限时抢购\",\"family\":\"王亮\",\"genus\":\"5级宝石\",\"economy_date\":\"2015-12-25\",\"economy_time\":\"10:12:38\",\"extra\":\"download_from:yyb,user_level:24\"}";
		    URL url= new URL(urlStr);
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			
			urlStr =uri.toASCIIString();
			             
			url=new URL(urlStr);
			URLConnection conn=url.openConnection();

				    StringBuilder result = new StringBuilder();
				      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				      String line;
				      while ((line = rd.readLine()) != null) {
				         result.append(line);
				      }
				      rd.close();
				      System.out.println( result.toString());
				    System.out.println(url.getProtocol());
				    System.out.println(url.getHost());
				    System.out.println(url.getPort());
				    System.out.println(url.getPath());
				    System.out.println(url.getQuery());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			    
			    
			    
//		String urlStr = "http://119.29.186.189/tracking/?snid=999&gameid=118&metric=Equipment&clientid=7001&ip=4.3.2.1&ds=2017-05-11&jsonString={\"userid\":\"22222222222222222\",\"udid\":\"666666666666666666666666\",\"equipment\":\"early\",\"kingdom\":\"342009\",\"phylum\":342009,\"classfield\":\"ok\",\"family\":\"\",\"genus\":\"\",\"value\":\"\",\"simulator\":\"\",\"creative\":\"2\",\"client_version\":\"1.0.0\",\"ratio\":\"1920*1080\",\"phone\":\"LeMobile Le X620\",\"system\":\"Android OS 6.0 / API-23 (HEXCNFN5902303291S/1490722571)\",\"eqDate\":\"2017-05-11\",\"eqTime\":\"16:0:28\",\"extra\":\"ram:2795,CPU:王亮\"}";
//		URL url = new URL(urlStr);
//			    URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
//		             urlStr =uri.toASCIIString();
//			      StringBuilder result = new StringBuilder();
//			      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			      conn.setRequestProperty("Content-Language", "UTF-8"); 
//			      conn.setRequestMethod("GET");
//			      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			      String line;
//			      while ((line = rd.readLine()) != null) {
//			         result.append(line);
//			      }
//			      rd.close();
//			      System.out.println( result.toString());
			   }
			    
          }
		
