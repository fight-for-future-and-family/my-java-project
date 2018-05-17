package com.hoolai.bi.report.model;

import com.hoolai.bi.report.core.Constant;


public class Types {
	public enum GameplayerAnalysisPage{
		DAU("dau"),RETENTION("retention"),LIFE("life"),INSTALL("install"),ROLE_CHOICE("roleChoice");
		
		private final String page;
		
		private GameplayerAnalysisPage(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * 默认返回新安装页面
		 * @param view
		 * @return
		 */
		public static GameplayerAnalysisPage convertToPage(String view) {
			for (GameplayerAnalysisPage t : GameplayerAnalysisPage.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameplayerAnalysisPage.values()[3];
        }
	}
	
	public enum GameAnalysisChannel{
		ALL("all"),SOURCE("source"),CLIENT("client");
		
		private final String channel;
		
		private GameAnalysisChannel(String view){
			this.channel = view;
		}
		
		public String value(){
			return channel;
		}
		
		/**
		 * 默认返回 总览
		 * @param view
		 * @return
		 */
		public static GameAnalysisChannel convertToChannel(String view) {
			for (GameAnalysisChannel t : GameAnalysisChannel.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameAnalysisChannel.values()[0];
        }
	}
	
	public enum GameRoleAnalysisChannel{
		ALL("all"),SOURCE("source"),
		INSTALL_ALL("install_all"),INSTALL_SOURCE("install_source"),
		ROLE_ALL("role_all"),ROLE_SOURCE("role_source"),
		CLIENT("client"),MONEY("money");
		
		
		
		private final String channel;
		
		private GameRoleAnalysisChannel(String view){
			this.channel = view;
		}
		
		public String value(){
			return channel;
		}
		
		/**
		 * 默认返回 总览
		 * @param view
		 * @return
		 */
		public static GameRoleAnalysisChannel convertToChannel(String view) {
			for (GameRoleAnalysisChannel t : GameRoleAnalysisChannel.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameRoleAnalysisChannel.values()[0];
        }
	}
	
	public enum GamePaymentAnalysisPage{
		All_PAY("allPay"),NEW_PAY("newPay"),PAY_BEHAVIOR("payBehavior");
		
		private final String page;
		
		private GamePaymentAnalysisPage(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * 默认返回付费总览页面
		 * @param view
		 * @return
		 */
		public static GamePaymentAnalysisPage convertToPage(String view) {
			for (GamePaymentAnalysisPage t : GamePaymentAnalysisPage.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GamePaymentAnalysisPage.values()[0];
        }
	}
	
	public enum GameViewPage{
		OVERVIEW("overview"),SOURCES("sources");
		
		private final String page;
		
		private GameViewPage(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * 默认返回总览页面
		 * @param view
		 * @return
		 */
		public static GameViewPage convertToPage(String view) {
			for (GameViewPage t : GameViewPage.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameViewPage.values()[0];
        }
	}
	
	public enum GameViewTestReportPage{
		ALLVIEW("allview"),YHWDVIEW("yhwdview"),SBWDVIEW("sbwdview");
		
		private final String page;
		
		private GameViewTestReportPage(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * 默认返回总览页面
		 * @param view
		 * @return
		 */
		public static GameViewTestReportPage convertToPage(String view) {
			for (GameViewTestReportPage t : GameViewTestReportPage.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameViewTestReportPage.values()[0];
        }
	}
	public enum GameViewTestReport{
		ALL("all"),YHWD("yhwd"),SBWD("sbwd");
		
		private final String page;
		
		private GameViewTestReport(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * 默认返回总览页面
		 * @param view
		 * @return
		 */
		public static GameViewTestReport convertToPage(String view) {
			for (GameViewTestReport t : GameViewTestReport.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameViewTestReport.values()[0];
        }
	}
	
	public enum GameAnalysisDimension{
		DAY("day"),WEEK("week"),MONTH("month");
		
		private final String dimension;
		
		private GameAnalysisDimension(String view){
			this.dimension = view;
		}
		
		public String value(){
			return dimension;
		}
		
		/**
		 * 默认返回日维度
		 * @param view
		 * @return
		 */
		public static GameAnalysisDimension convertToDimension(String dimension) {
			for (GameAnalysisDimension t : GameAnalysisDimension.values()) {
                if (t.value().equals(dimension)) {
                        return t;
                }
            }
            return GameAnalysisDimension.values()[0];
        }
	}
	
	//测试报告
	public enum GameTestReport{
		ALL("all"),ZSB("zsb"),LEAVE("leave");
		
		private final String dimension;
		
		private GameTestReport(String view){
			this.dimension = view;
		}
		
		public String value(){
			return dimension;
		}
		
		/**
		 * 默认返回总览维度
		 * @param view
		 * @return
		 */
		public static GameTestReport convertToDimension(String dimension) {
			for (GameTestReport t : GameTestReport.values()) {
                if (t.value().equals(dimension)) {
                        return t;
                }
            }
            return GameTestReport.values()[0];
        }
	}
	public enum GameToolPage{
		TREND("trend"),MULTI_INDICATORS("multi_indicators"),PREDICTION("prediction");
		
		private final String page;
		
		private GameToolPage(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * 默认返回趋势对比页面
		 * @param view
		 * @return
		 */
		public static GameToolPage convertToPage(String view) {
			for (GameToolPage t : GameToolPage.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameToolPage.values()[0];
        }
	}
	
	public enum GameDataPage{
		DAILY("daily"),RETENTION("retention"),LIFE("life"),MONEY("money"),REALTIME("realTime"),
		HOUR_REPORT("hourReport"),HOUR_REPORT_SOURCE_LTV("hourReportSourceLtv"),HOUR_REPORT_SOURCE_RETENTION("hourReportSourceRetention"),ECONOMY("economy"),FORECAST_HOUR_REPORT("forecastHourReport"),
		WHALE_USER("whaleUser"),EQUIP_DAU("equipDau"),VERSION_DAU("versionDau"),
		INSTALL_RETENTION("installRetention"),TESTREPORT("testReport"),SBWDVIEW("sbwdview"),TASK_LIST("taskList");
		
		private final String page;
		
		private GameDataPage(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * 默认返回实时页面
		 * @param view
		 * @return
		 */
		public static GameDataPage convertToPage(String view) {
			for (GameDataPage t : GameDataPage.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameDataPage.values()[4];
        }
	}
	
	public enum GameplayerType {
		INSATLL("install"),ROLE_CHOICE("roleChoice");
       private final String page;
		
		private GameplayerType(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * 默认返回实时页面
		 * @param view
		 * @return
		 */
		public static GameplayerType convertToType(String view) {
			for (GameplayerType t : GameplayerType.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameplayerType.values()[0];
        }
	}
	
	public enum GameEconomyPage{
		ECONOMY_POINT_DATA("economyData"),ECONOMY_ITEM_DATA("economyItem"),ECONOMY_DIMENSION("economyDimension");
		
		private final String page;
		
		private GameEconomyPage(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * @param view
		 * @return
		 */
		public static GameEconomyPage convertToPage(String view) {
			for (GameEconomyPage t : GameEconomyPage.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameEconomyPage.values()[0];
        }
	}
	
	public enum GameCPAPage{
		CPA("cpa"),CPS("cps"),DIMENSION("dimension"),AUTH_MANAGE("authManage");
		
		private final String page;
		
		private GameCPAPage(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * @param view
		 * @return
		 */
		public static GameCPAPage convertToPage(String view) {
			for (GameCPAPage t : GameCPAPage.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameCPAPage.values()[1];
        }
	}
	
	public enum GamePayerLevelPage{
		DAU_LEVEL("dauLevel"),INSTALL_LEVEL("installLevel"),
		INSTALL_PAYMENT_LEVEL("installPaymentLevel"),
		NEW_PAYMENT_LEVEL("newPaymentLevel"),
		KPI_PREDICT("kpiPredict");
		
		private final String page;
		
		private GamePayerLevelPage(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * @param view
		 * @return
		 */
		public static GamePayerLevelPage convertToPage(String view) {
			for (GamePayerLevelPage t : GamePayerLevelPage.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GamePayerLevelPage.values()[0];
        }
	}
	
	public enum GameEquipmentPage{
		EQUIP_DAU("equipDau"),
		VERSION_DAU("versionDau"),
		INSTALL_RETENTION("installRetention"),
		ALL_STEP("allStep"),
		INSTALL_STEP("installStep");
		
		private final String page;
		
		private GameEquipmentPage(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * @param view
		 * @return
		 */
		public static GameEquipmentPage convertToPage(String view) {
			for (GameEquipmentPage t : GameEquipmentPage.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameEquipmentPage.values()[0];
        }
	}
	
	public enum GameEquipmentType{
		ALL("equipDau"),
		SOURCE("source"),
		MODEL("model");
		
		private final String page;
		
		private GameEquipmentType(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * @param view
		 * @return
		 */
		public static GameEquipmentType convertToType(String view) {
			for (GameEquipmentType t : GameEquipmentType.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameEquipmentType.values()[0];
        }
	}
	
	public enum CurrencyConvertedToRMBRate{
		USD("美元",Constant.TO_RMB_RATE.get("USD")),
		TWD("台币",Constant.TO_RMB_RATE.get("TWD")),
		RMB("元",Constant.TO_RMB_RATE.get("RMB"));
		
		private final Double value;
		private final String currencyName;
		private CurrencyConvertedToRMBRate(String currencyName,Double rate){
			this.value = rate;
			this.currencyName = currencyName;
		}
		
		public static CurrencyConvertedToRMBRate convert(String name){
			for(CurrencyConvertedToRMBRate rate:values()){
				if(rate.getCurrencyName().equals(name)){
					return rate;
				}
			}
			return values()[2];
		}

		public Double getValue() {
			return value;
		}

		public String getCurrencyName() {
			return currencyName;
		}
	}
	
	
	public enum GameWhaleUserPage{
		WHALE_USER("whaleUser"),
		PAY_USER_LTV("payUserLtv"),
		SMALL_USER_LTV("smallUser");
		
		private final String page;
		
		private GameWhaleUserPage(String view){
			this.page = view;
		}
		
		public String value(){
			return page;
		}
		
		/**
		 * @param view
		 * @return
		 */
		public static GameWhaleUserPage convertToPage(String view) {
			for (GameWhaleUserPage t : GameWhaleUserPage.values()) {
                if (t.value().equals(view)) {
                        return t;
                }
            }
            return GameWhaleUserPage.values()[0];
        }
	}
}
