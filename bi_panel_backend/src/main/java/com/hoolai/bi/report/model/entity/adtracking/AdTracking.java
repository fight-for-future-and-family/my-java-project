package com.hoolai.bi.report.model.entity.adtracking;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AdTracking {
    private Long id;

    private String appkey;
    
    private String snid;

    private String gameid;

    private String mac;

    private String macMd5;

    private String ifa;

    private String ifaMd5;

    private String uuid;

    private Integer actType;

    private Date actTime;

    private String pf;

    private String ip;

    private String userAgent;

    private String ds;

    private String extra;

    private Integer status;

    private String remark;
    
    private Date callbackTime;
    
    private Map<String, String> extraCache;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getSnid() {
		return snid;
	}

	public void setSnid(String snid) {
		this.snid = snid;
	}

	public String getGameid() {
		return gameid;
	}

	public void setGameid(String gameid) {
		this.gameid = gameid;
	}

	public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMacMd5() {
        return macMd5;
    }

    public void setMacMd5(String macMd5) {
        this.macMd5 = macMd5;
    }

    public String getIfa() {
        return ifa;
    }

    public void setIfa(String ifa) {
        this.ifa = ifa;
    }

    public String getIfaMd5() {
        return ifaMd5;
    }

    public void setIfaMd5(String ifaMd5) {
        this.ifaMd5 = ifaMd5;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getActType() {
        return actType;
    }

    public void setActType(Integer actType) {
        this.actType = actType;
    }

    public Date getActTime() {
        return actTime;
    }

    public void setActTime(Date actTime) {
        this.actTime = actTime;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
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

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public Date getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}
    
    
}