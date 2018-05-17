package com.hoolai.bi.collectdata.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
 * <b>类说明：</b> <blockquote>
 * 
 * </blockquote>
 * 
 * @author jiangqm E-mail: jiangqiming@jiangqiming.cn
 * @version 创建时间：2015年12月10日 下午7:29:59
 */

public class SendInstallDatasProcesser {
	private static Log log = LogFactory.getLog(SendInstallDatasProcesser.class);

	public static String postStringResource(String url,Map<String, String> callbackParamsMap, String charsetName) {
		long begin = System.currentTimeMillis();
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			try {
				RequestConfig.Builder reqConfigBuilder = RequestConfig.custom();
				reqConfigBuilder.setConnectionRequestTimeout(5000);
				reqConfigBuilder.setSocketTimeout(5000);

				HttpPost httpPost = new HttpPost(url);

				MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
						.create();
				if (null != callbackParamsMap && !callbackParamsMap.isEmpty()) {
					for (String key : callbackParamsMap.keySet()) {
						multipartEntityBuilder.addPart(key, new StringBody(callbackParamsMap.get(key)));
					}
				}

				HttpEntity reqEntity = multipartEntityBuilder.build();

				httpPost.setEntity(reqEntity);

				log.debug("executing request " + httpPost.getRequestLine());

				CloseableHttpResponse response = httpclient.execute(httpPost);
				try {
					StatusLine statusLine = response.getStatusLine();
					if (statusLine.getStatusCode() == 200) {
						HttpEntity resEntity = response.getEntity();
						if (resEntity != null) {
							String content = EntityUtils.toString(resEntity, "UTF-8");
							log.debug("Response content: " + content);
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
		} finally {
			long end = System.currentTimeMillis();
			log.info("http executing send url:" + url + " finished spendMills:"
					+ (end - begin));
		}
		return "";
	}

}
