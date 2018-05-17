package com.hoolai.bi.collectdata.client.metrics;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * 
 * @author dbai Base Class, Sub Class must implements log method of ITracker
 *         interface individually
 * 
 */
public abstract class TrackBase implements ITracker {

	public static final String FIELD_SEPARATOR = "\t";

	private String userId; // must be passed in, represent for a part of the
							// unique key

	private Map<String, String> extraCache;
	
	private String extra = "";

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String buildkey(String channel, String category, String subcategory,
			String family, String genus, String useruid) {
		return "";
	}

	public String get_send_key() {
		UUID uuid = UUID.randomUUID();
		String result = uuid.toString();
		return result;
	}

	public void addExtra(String key, String value) {
		try {
			if(this.extraCache==null){
				this.extraCache = new HashMap<String, String>();
			}
			if(value!=null&&value!=""){
				this.extraCache.put(key, URLEncoder.encode(value, "UTF-8"));
			}else{
				this.extraCache.put(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 以JSON的格式返回extra数据
	 * 
	 * @return
	 */
	public String getExtra() {
		if (this.extraCache==null || this.extraCache.isEmpty()) {
			return this.extra;
		}
		StringBuilder sb=new StringBuilder();
		for (Entry<String,String> extraEntry : this.extraCache.entrySet()) {
			sb.append(extraEntry.getKey()).append(":").append(extraEntry.getValue()).append(",");
		}
		return sb.toString();
	}
	
	public String getExtraValue(String key){
		try {
			if(this.extraCache==null){
				return null;
			}
			String v=this.extraCache.get(key);
			if(v!=null&&v!=""){
				return URLDecoder.decode(v, "UTF-8");
			}
			return v;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	/**
	 * 把字符串extra，转换为map对象
	 */
	public void fromExtra(){
		if(this.extra==null||"".equals(this.extra)){
			return ;
		}
		if(this.extraCache==null){
			this.extraCache=new HashMap<String, String>();
		}
		String[] kvArr=extra.split(",");
		for (String kv : kvArr) {
			if(kv==null||"".equals(kv)){
				continue;
			}
			String[] tempArr=kv.split(":");
			if(tempArr.length<2){
				continue;
			}
			String k=tempArr[0];
			String v=tempArr[1];
			this.extraCache.put(k, v);
		}
	}

	public String getProcessValue(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	public abstract String get_messages();

	public abstract String get_event();

	public abstract String get_message_for_serverlet(String clientid);
}
