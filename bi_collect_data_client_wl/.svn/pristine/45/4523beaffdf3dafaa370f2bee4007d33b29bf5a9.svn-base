package com.hoolai.bi.collectdata.client;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.hoolai.bi.collectdata.client.core.Constant;
import com.hoolai.bi.collectdata.client.util.JSONUtils;

public class SendCategoryDatasProcesser {
	
	private static final Logger LOGGER=Logger.getLogger(SendCategoryDatasProcesser.class.getSimpleName());

	private File file;

	public SendCategoryDatasProcesser(File file) {
		super();
		this.file = file;
	}

	public boolean send() {
		long begin=System.currentTimeMillis(); 
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			try {
				RequestConfig.Builder reqConfigBuilder=RequestConfig.custom();
				reqConfigBuilder.setConnectionRequestTimeout(5000);
				reqConfigBuilder.setConnectTimeout(5000);
				reqConfigBuilder.setSocketTimeout(10000);
				
				HttpPost httpPost = new HttpPost(Constant.SERVER_URL + "/collect.hlhtml");

				FileBody uploadFiles = new FileBody(file);
				StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);
				
				MultipartEntityBuilder multipartEntityBuilder=MultipartEntityBuilder.create();
				multipartEntityBuilder.addPart("uploadFiles", uploadFiles);
				multipartEntityBuilder.addPart("comment", comment);
				HttpEntity reqEntity = multipartEntityBuilder.build();

				httpPost.setEntity(reqEntity);

				LOGGER.debug("executing request " + httpPost.getRequestLine());
				
				CloseableHttpResponse response = httpclient.execute(httpPost);
				try {
					StatusLine statusLine=response.getStatusLine();
					if(statusLine.getStatusCode()!=200){
						return false;
					}
					HttpEntity resEntity = response.getEntity();
					if(resEntity==null){
						return false;
					}
					
					String content=EntityUtils.toString(resEntity, "UTF-8");
					LOGGER.debug("Response content: " + content);
					
					Map<String,Object> result=JSONUtils.fromJSON(content,HashMap.class);
					String status=(String)result.get("status");
					
					if(!"succ".equals(status)){
						return false;
					}
					
					EntityUtils.consume(resEntity);
					long end=System.currentTimeMillis();
					LOGGER.info("http executing send file:"+file.getAbsolutePath()+" succ spendMills:"+(end-begin));
					return true;
					
				} finally {
					response.close();
				}
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}finally{
			long end=System.currentTimeMillis();
			LOGGER.info("http executing send file:"+file.getAbsolutePath()+" finished spendMills:"+(end-begin));
		}
		return false;
	}

}
