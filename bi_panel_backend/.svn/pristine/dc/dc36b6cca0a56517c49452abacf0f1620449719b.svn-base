package com.hoolai.bi.report.util;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class HttpParams {

	private String decoding = "UTF-8";

	private String requestUrl;

	private String requestUri;

	private Map<String, String> params;

	private HttpParams() {
		super();
		params = new HashMap<String, String>();
	}

	public HttpParams(String requestUrl) {
		this();

		this.requestUrl = requestUrl;
		this.parse();
	}

	public HttpParams(String requestUrl,String decoding) {
		this();
		if (StringUtils.isNotBlank(decoding)) {
			try {
				this.requestUrl = URLDecoder.decode(requestUrl, decoding);
				this.decoding=decoding;
			} catch (Exception e) {
				e.printStackTrace();
				this.requestUrl = requestUrl;
			}
		} else {
			this.requestUrl = requestUrl;
		}
		this.parse();
	}

	private void parse() {
		int splitIndex = this.requestUrl.indexOf("?");
		if (splitIndex == -1) {
			this.requestUri = this.requestUrl;
			return;
		}
		this.requestUri = this.requestUrl.substring(0, splitIndex);
		String paras = this.requestUrl.substring(splitIndex + 1);
		this.parseParams(paras);
	}

	private void parseParams(String paras) {
		if (paras == null || paras == "") {
			return;
		}
		String[] tempParamsArr = paras.split("&");
		if (tempParamsArr.length < 1) {
			return;
		}
		for (String para : tempParamsArr) {
			int tempIndex = para.indexOf("=");
			if (tempIndex == -1) {
				continue;
			}
			String key = para.substring(0, tempIndex);
			String value = para.substring(tempIndex + 1);
			this.params.put(key, value);
		}
	}

	public boolean isContains(String key) {
		return this.params.containsKey(key);
	}

	public String getValue(String key) {
		return this.params.get(key);
	}

	public Set<String> keys() {
		return this.params.keySet();
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

}
