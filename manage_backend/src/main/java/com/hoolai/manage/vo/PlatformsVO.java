package com.hoolai.manage.vo;

import java.io.Serializable;
import java.util.List;

import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.model.Platforms;
public class PlatformsVO extends AbstractObjectVO<Platforms> {

	private static final long serialVersionUID = 8867277237698758218L;
	
	private String paramValues;
	
	private String searchValue;
	
	private String platformId;
	
	public PlatformsVO() {
		super();
		this.entity=new Platforms();
	}

	public PlatformsVO(Platforms entity) {
		super(entity);
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getParamValues() {
		return paramValues;
	}

	public void setParamValues(String paramValues) {
		this.paramValues = paramValues;
	}
	
	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public static class PlatformsParamsTemp implements Serializable{
		private static final long serialVersionUID = 1L;
		private PlatformsParam paramObj;
		private PlatformsCallback callbackObj;
		public PlatformsParam getParamObj() {
			return paramObj;
		}
		public void setParamObj(PlatformsParam paramObj) {
			this.paramObj = paramObj;
		}
		public PlatformsCallback getCallbackObj() {
			return callbackObj;
		}
		public void setCallbackObj(PlatformsCallback callbackObj) {
			this.callbackObj = callbackObj;
		}
	}
	
	public static class PlatformsParam implements Serializable{
		private static final long serialVersionUID = 1L;
		private String mac;
		private String macMd5;
		private String ifa;
		private String ifaMd5;
		private String ip;
		private String userAgent;
		private String extra;
		
		public String getMac() {
			return mac;
		}
		public void setMac(String mac) {
			this.mac = mac;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getExtra() {
			return extra;
		}
		public void setExtra(String extra) {
			this.extra = extra;
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
		public String getUserAgent() {
			return userAgent;
		}
		public void setUserAgent(String userAgent) {
			this.userAgent = userAgent;
		}
	}
	
	public static class PlatformsCallback implements Serializable{
		private static final long serialVersionUID = 1L;
		private String id;
		private String mac;
		private String macMd5;
		private String ifa;
		private String ifaMd5;
		private String ip;
		private String userAgent;
		private String extra;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getMac() {
			return mac;
		}
		public void setMac(String mac) {
			this.mac = mac;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
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
		public String getUserAgent() {
			return userAgent;
		}
		public void setUserAgent(String userAgent) {
			this.userAgent = userAgent;
		}
		public String getExtra() {
			return extra;
		}
		public void setExtra(String extra) {
			this.extra = extra;
		}
		
	}
	
	public static class InteractiveDatas {
			
			private String model;
			
			private String operator;
			
			private String jsonModelDatas;
			
			public InteractiveDatas(){}
			
			public InteractiveDatas(String model,String operator,String jsonModelDatas){
				this.model = model;
				this.operator = operator;
				this.jsonModelDatas = jsonModelDatas;
			}

			public String getModel() {
				return model;
			}

			public void setModel(String model) {
				this.model = model;
			}

			public String getOperator() {
				return operator;
			}

			public void setOperator(String operator) {
				this.operator = operator;
			}

			public String getJsonModelDatas() {
				return jsonModelDatas;
			}

			public void setJsonModelDatas(String jsonModelDatas) {
				this.jsonModelDatas = jsonModelDatas;
			}

		}
}
