package com.hoolai.bi.tracking.log;

import com.dw.metrics.AdGameInstall;
import com.dw.metrics.AdTracking;
import com.dw.metrics.Beat;
import com.dw.metrics.Bugly;
import com.dw.metrics.Counter;
import com.dw.metrics.Dau;
import com.dw.metrics.Demographic;
import com.dw.metrics.Economy;
import com.dw.metrics.Eqbeat;
import com.dw.metrics.Equipment;
import com.dw.metrics.Friends;
import com.dw.metrics.GameInfo;
import com.dw.metrics.Install;
import com.dw.metrics.Landing;
import com.dw.metrics.Message;
import com.dw.metrics.MessageClick;
import com.dw.metrics.Milestone;
import com.dw.metrics.Mirror;
import com.dw.metrics.Mirrorinfo;
import com.dw.metrics.Online;
import com.dw.metrics.Payment;
import com.dw.metrics.Promote;
import com.hoolai.util.JSONUtils;

@SuppressWarnings("rawtypes")
public enum TrackingType {

	COUNTER("Counter",Counter.class) {
		private Counter counter;
		@Override
		public Object convertObj(String jsonString) {
			counter = (Counter) this.formJson(this.getName(),jsonString);
			return counter;
		}
	}, 
	DAU("Dau",Dau.class) {
		private Dau dau;
		@Override
		public Object convertObj(String jsonString) {
			dau = (Dau) this.formJson(this.getName(),jsonString);
			return dau;
		}
	},
	DEMOGRAPHIC("Demographic",Demographic.class) {
		@Override
		public Object convertObj(String jsonString) {
			return (Demographic)this.formJson(this.getName(),jsonString);
		}
	}, 
	ECONOMY("Economy",Economy.class) {
		@Override
		public Object convertObj(String jsonString) {
			return (Economy)this.formJson(this.getName(),jsonString);
		}
	}, 
	FRIENDS("Friends",Friends.class) {
		@Override
		public Object convertObj(String jsonString) {
			return (Friends)this.formJson(this.getName(),jsonString);
		}
	}, 
	INSTALL("Install",Install.class) {
		@Override
		public Object convertObj(String jsonString) {
			return (Install)this.formJson(this.getName(),jsonString);
		}
	}, 
	MESSAGE("Message",Message.class) {
		@Override
		public Object convertObj(String jsonString) {
			return (Message)this.formJson(this.getName(),jsonString);
		}
	},
	MESSAGE_CLICK("MessageClick",MessageClick.class) {
		@Override
		public Object convertObj(String jsonString) {
			return (MessageClick)this.formJson(this.getName(),jsonString);
		}
	}, 
	MILESTONE("Milestone",Milestone.class) {
		@Override
		public Object convertObj(String jsonString) {
			return (Milestone)this.formJson(this.getName(),jsonString);
		}
	}, 
	PAYMENT("Payment",Payment.class) {
		@Override
		public Object convertObj(String jsonString) {
			return (Payment)this.formJson(this.getName(),jsonString);
		}
	}, 
	GAMEINFO("GameInfo",GameInfo.class) {
		@Override
		public Object convertObj(String jsonString) {
			return (GameInfo)this.formJson(this.getName(),jsonString);
		}
	},
	ADTRACKING("AdTracking",AdTracking.class) {
		@Override
		public Object convertObj(String jsonString) {
			return (AdTracking)this.formJson(this.getName(),jsonString);
		}
	},
	MIRRORINFO("Mirrorinfo",Mirrorinfo.class){

		@Override
		public Object convertObj(String jsonString) {
			return (Mirrorinfo)this.formJson(this.getName(),jsonString);
		}
	},
	EQUIPMENT("Equipment",Equipment.class){

		@Override
		public Object convertObj(String jsonString) {
			return (Equipment)this.formJson(this.getName(),jsonString);
		}
	},
	BUGLY("Bugly", Bugly.class){
		@Override
		public Object convertObj(String jsonString) {
			return (Bugly)this.formJson(this.getName(),jsonString);
		}
	},
	Online("Online", Online.class){
		@Override
		public Object convertObj(String jsonString) {
			return (Online)this.formJson(this.getName(),jsonString);
		}
	},
	Promote("Promote", Promote.class){
		@Override
		public Object convertObj(String jsonString) {
			return (Promote)this.formJson(this.getName(),jsonString);
		}
	},
	Eqbeat("Eqbeat", Eqbeat.class){
		@Override
		public Object convertObj(String jsonString) {
			return (Eqbeat)this.formJson(this.getName(),jsonString);
		}
	},
	Landing("Landing", Landing.class){
		@Override
		public Object convertObj(String jsonString) {
			return (Landing)this.formJson(this.getName(),jsonString);
		}
		
	},
	Mirror("Mirror", Mirror.class){
		@Override
		public Object convertObj(String jsonString) {
			return (Mirror)this.formJson(this.getName(),jsonString);
		}
		
	},
	Beat("Beat", Beat.class){
		@Override
		public Object convertObj(String jsonString) {
			return (Beat)this.formJson(this.getName(),jsonString);
		}
	},
	AdGameInstall("AdGameInstall", AdGameInstall.class){
		@Override
		public Object convertObj(String jsonString) {
			return (AdGameInstall)this.formJson(this.getName(),jsonString);
		}
	}
	;
	
	private final String name;
	private final Class typeClass; 
	
	private TrackingType(String name,Class typeClass){
		this.name = name;
		this.typeClass = typeClass;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Class getTypeClass(){
		return this.typeClass;
	}
	
	public static boolean isMetricsValidation(String metrics){
		for(TrackingType type:values()){
			if(type.getName().equalsIgnoreCase(metrics)){
				return true;
			}
		}
		return false;
	}
	
	public abstract Object convertObj(String jsonString);
	
	public static TrackingType convert(String metrics){
		for(TrackingType type:values()){
			if(type.getName().equalsIgnoreCase(metrics)){
				return type;
			}
		}
		return null;
	} 
	
	@SuppressWarnings("unchecked")
	protected <T> Object formJson(String metrics,String jsonString){
		for(TrackingType type:values()){
			if(type.getName().equals(metrics)){
				return JSONUtils.fromJSON(jsonString, type.getTypeClass());
			}
		}
		return null;
	}
	
}
