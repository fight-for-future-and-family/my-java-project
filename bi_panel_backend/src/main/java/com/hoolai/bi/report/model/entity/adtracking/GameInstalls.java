package com.hoolai.bi.report.model.entity.adtracking;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GameInstalls {
    private Long id;

    private Integer snid;

    private Integer gameid;

    private String clientid;

    private String ds;

    private String source;

    private String udid;

    private String userid;

    private String roleid;

    private String installTime;

    private String ip;

    private String clientVersion;

    private String phoneType;

    private String phoneVersion;

    private String ratio;

    private String netService;

    private String downloadFrom;

    private String extra;
    
    private Map<String, String> extraCache;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSnid() {
        return snid;
    }

    public void setSnid(Integer snid) {
        this.snid = snid;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getInstallTime() {
        return installTime;
    }

    public void setInstallTime(String installTime) {
        this.installTime = installTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneVersion() {
        return phoneVersion;
    }

    public void setPhoneVersion(String phoneVersion) {
        this.phoneVersion = phoneVersion;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getNetService() {
        return netService;
    }

    public void setNetService(String netService) {
        this.netService = netService;
    }

    public String getDownloadFrom() {
        return downloadFrom;
    }

    public void setDownloadFrom(String downloadFrom) {
        this.downloadFrom = downloadFrom;
    }

    public void setExtra(String extra) {
        this.extra = extra;
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
}