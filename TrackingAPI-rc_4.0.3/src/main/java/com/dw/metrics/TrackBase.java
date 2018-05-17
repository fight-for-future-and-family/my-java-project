package com.dw.metrics;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

public abstract class TrackBase {

	public static final String FIELD_SEPARATOR = "\t";
	
	protected String userId;

	private Map<String, String> extraCache;
	
	protected String extra = "";
	
	protected String udid;
	
	protected String roleId;
	
	protected String ds;
	
	/**
	 * 获取用户ID 
	 * 
	 * @return userid 联运的游戏即为平台帐号，腾讯的游戏即为openid
	 */
	public String getUserId() {
		return userId;
	}
	
	public String getUserid() {
		return userId;
	}

	/**
	 * 设置用户ID 
	 * 
	 * @param userId 联运的游戏即为平台帐号，腾讯的游戏即为openid
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserid(String userId) {
		this.userId = userId;
	}

	public String getUdid() {
		return udid;
	}

	/**
	 * 设置udid
	 * @param udid
	 */
	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getRoleId() {
		return roleId;
	}

	public String getRoleid() {
		return roleId;
	}
	
	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}
	
	/**
	 * 设置玩家角色ID
	 * @param roleId
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public void setRoleid(String roleId) {
		this.roleId = roleId;
	}
	

	/**
	 * 
	 * @return 随机的uuid字符串
	 */
	public String sendKey() {
		UUID uuid = UUID.randomUUID();
		String result = uuid.toString();
		return result;
	}

	/**
	 * 通过键值的方式添加扩展分类
	 * 
	 * @param key 扩展分类名称
	 * @param value 扩展分类值
	 */
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
	 * 获取扩展分类
	 * 
	 * @return json格式的extra数据
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
	
	/**
	 * 获取扩展分类中的某个分类
	 * @param key 分类的名称
	 * @return 如果分类存在，返回此分类的值；不存在，返回null
	 */
	public String findExtraValue(String key){
		try {
			if(this.extraCache != null){
				String v = this.extraCache.get(key);
				if(StringUtils.isEmpty(v)){
					return URLDecoder.decode(v, "UTF-8");
				}
				return v;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 设置扩展分类的值,不建议使用，
	 * 另有更安全的设置扩展分类的方法 {@linkplain com.dw.metrics.TrackBase addExtra} 
	 * @param extra 类json格式的扩展分类数据，例如：download_from:qqHall,udid:1300110000,phone_type:XIAOMI 3
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	/**
	 * 把json格式的extra字符串转换成键值map对象
	 */
	public void fromExtra(){
		if(!StringUtils.isEmpty(extra)){
			this.extraCache =  this.extraCache==null ? new HashMap<String, String>() : extraCache;
			String[] kvArr=extra.split(",");
			for (String kv : kvArr) {
				if(!StringUtils.isEmpty(kv)){
					String[] tempArr=kv.split(":");
					if(tempArr.length >= 2){
						String k=tempArr[0];
						String v=tempArr[1];
						this.extraCache.put(k, v);
					}
				}
			}
		}
	}

	/**
	 * 去除字符串中的空格
	 * @param value
	 * @return 如果传入值为null，返回空字符串。如果不为null，去空格。
	 */
	public String cleanString(String value) {
		return value == null ? "" : value.trim();
	}

	/**
	 * 获取指标英文代称
	 * @return 指标名称
	 */
	public abstract String metric();
	
	/**
	 * 持久化指标类，转换成存储格式
	 * @param clientid 服务器ID
	 * @return 存储格式的字符串
	 */
	public abstract String prepareForDB(String clientid);
	
	/**
	 * 验证指标类中不能为空的字段
	 * @return 必填字段中有字段为null或者"",返回true；反之，返回false。
	 */
	public abstract boolean checkBaseParmIsNull();

	/**
	 * 验证指标类中传入的日期与时间。日期格式(yyyy-MM-dd),时间格式(HH:mm:ss)
	 * 如果传入的日期或者时间为null、""、格式不正确默认当前日期与时间。
	 */
	public abstract void processDate();
	
	/**
	 * 验证前端报送是否报送了ip数据，如果没有，web项目直接从request中获取ip并set到对象中
	 * @param ip
	 */
	public void processIp(String ip){
		
	}
}
