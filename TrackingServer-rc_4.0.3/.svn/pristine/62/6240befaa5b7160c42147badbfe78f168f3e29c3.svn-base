package com.hoolai.bi.tracking.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class SpiderUtilities {

	private static Logger LOGGER = Logger.getLogger(SpiderUtilities.class);


	/**
	 * Returns an InputStream from a connection. Retries 3 times.
	 * 
	 * @param connection
	 *            a connection
	 * @return an InputStream
	 * @since 26 novembre 2001
	 */
	public static InputStream getSafeInputStream(HttpURLConnection connection) {

		InputStream inputstream = null;

		for (int i = 0; i < 3;) {
			try {
				inputstream = connection.getInputStream();
				break;
			} catch (IOException ioexception1) {
				LOGGER.info("error opening connection " + ioexception1);
				i++;
			}
		}

		return inputstream;
	}

	public static InputStream getSafeInputStream(String url) {
		HttpURLConnection connection = getValidConnection(url);
		if (connection == null) {
			return null;
		}

		return getSafeInputStream(connection);
	}

	public static String getStringResource(HttpURLConnection connection,
			String charsetName) {
		// LOGGER.info("fetchURL started");

		InputStream inputstream = getSafeInputStream(connection);
		if (inputstream == null) {
			return null;
		}

		// load the Stream in a StringBuffer
		InputStreamReader isr = null;
		StringBuffer content = new StringBuffer();

		try {
			isr = new InputStreamReader(inputstream, charsetName);
			char buf[] = new char[1024];
			int cnt = 0;
			while ((cnt = isr.read(buf, 0, 1024)) != -1) {
				content.append(buf, 0, cnt);
			}
			isr.close();
			inputstream.close();
		} catch (IOException ioexception) {
			LOGGER.info(ioexception);
		}

		return content.toString();
	}


	/**
	 * 抽取网页源代码
	 * 
	 * @param url
	 * @return
	 */
	public static String getStringResource(String url, String charsetName) {
		HttpURLConnection connection = getValidConnection(url);
		if (connection == null) {
			return null;
		}
		return getStringResource(connection, charsetName);
	}

	public static HttpURLConnection getValidConnection(String url) {
		HttpURLConnection httpurlconnection = null;

		try {
			URLConnection urlconnection = new URL(url).openConnection();
			urlconnection.connect();

			if (!(urlconnection instanceof HttpURLConnection)) {
				LOGGER.info("Not an http connection: " + url);
				// urlconnection.disconnect();
				return null;
			}

			httpurlconnection = (HttpURLConnection) urlconnection;
			// httpurlconnection.setFollowRedirects(true);

			int responsecode = httpurlconnection.getResponseCode();

			switch (responsecode) {
			// here valid codes!
			case HttpURLConnection.HTTP_OK:
			case HttpURLConnection.HTTP_MOVED_PERM:
			case HttpURLConnection.HTTP_MOVED_TEMP:
				break;
			default:
				LOGGER.info("Invalid response code: " + responsecode + " " + url);
				httpurlconnection.disconnect();
				return null;
			}
		} catch (IOException ioexception) {
			LOGGER.info("unable to connect: " + ioexception);
			if (httpurlconnection != null) {
				httpurlconnection.disconnect();
			}
			return null;
		}

		return httpurlconnection;
	}

	
	
	
	public static String postStringResource(String url, Map<String, String> callbackParamsMap, String charsetName) {
		long begin=System.currentTimeMillis(); 
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			try {
				RequestConfig.Builder reqConfigBuilder=RequestConfig.custom();
				reqConfigBuilder.setConnectionRequestTimeout(5000);
				reqConfigBuilder.setSocketTimeout(5000);
				
				HttpPost httpPost = new HttpPost(url);

				MultipartEntityBuilder multipartEntityBuilder=MultipartEntityBuilder.create();
				if(null != callbackParamsMap && !callbackParamsMap.isEmpty()){
					for(String key : callbackParamsMap.keySet()){
						multipartEntityBuilder.addPart(key, new StringBody(callbackParamsMap.get(key)));
					}
				}
				
				HttpEntity reqEntity = multipartEntityBuilder.build();

				httpPost.setEntity(reqEntity);

				LOGGER.debug("executing request " + httpPost.getRequestLine());
				
				CloseableHttpResponse response = httpclient.execute(httpPost);
				try {
					StatusLine statusLine=response.getStatusLine();
					if(statusLine.getStatusCode() == 200){
						HttpEntity resEntity = response.getEntity();
						if (resEntity != null) {
							String content=EntityUtils.toString(resEntity, "UTF-8");
							LOGGER.debug("Response content: " + content);
							return content;
						}
						return "";
					}
				} finally {
					response.close();
				}
			} finally {
				httpclient.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			long end=System.currentTimeMillis();
			LOGGER.info("http executing send url:" + url + " finished spendMills:"+(end-begin));
		}
		return "";
	}
	
	

}
