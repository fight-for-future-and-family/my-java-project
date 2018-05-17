	<%@ page language="java" pageEncoding="UTF-8"%>
	<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
	<div class="sidebar">
		<ul>
		   <li id="main"><a href="/panel_manage/toMain.ac">主页</a></li>
		   <c:if test="${isOutSideUser}">
		      <!-- <li id="gameli_CPA" class="active"><a href="/panel_bi/costPer/toCPA.ac?type=other">CPS</a></li>
		      <li id="gameli_CPACreative"><a href="/panel_bi/cpaCreative/toCPACreative.ac?view=cpa">CPA</a></li> -->
		      <li id="gameli_CPA" class="active"><a class="aaa" onclick="editHref(this)" href="/panel_bi/costPer/toCPA.ac?type=other">CPS</a></li>
		      <li id="gameli_CPACreative"><a class="aaa" onclick="editHref(this)" href="/panel_bi/cpaCreative/toCPACreative.ac?view=cpa">CPA</a></li>
		   </c:if>
		   <c:if test="${!isOutSideUser}">
			   <li id="gameli_realTime" class="active"><a class="aaa" onclick="editHref(this)" href="/panel_bi/gameRealTime/toGameRealTime.ac">实时</a></li>	   
			   <li id="gameli_all"><a class="aaa" onclick="editHref(this)" href="/panel_bi/gameView/toGameOverview.ac">游戏总览</a></li>
			   <li id="gameli_user"><a class="aaa" onclick="editHref(this)" href="/panel_bi/gamePlayer/toGameplayerAnalysis.ac">游戏分析</a></li>
			   <li id="gameli_payment"><a class="aaa" onclick="editHref(this)" href="/panel_bi/gamePay/toGamePaymentAnalysis.ac">游戏收入</a></li>
			   <li id="gameli_economy"><a class="aaa" onclick="editHref(this)" href="/panel_bi/economy/toGameEconomy.ac">经济系统</a></li>
			   <li id="gameli_level"><a class="aaa" onclick="editHref(this)" href="/panel_bi/payerLevel/toGamePayerLevel.ac">等级分布分析</a></li>
			   <li id="gameli_wuser"><a class="aaa" onclick="editHref(this)" href="/panel_bi/whaleUser/toWhaleUser.ac">大R</a></li>
			    <c:if test="${game != null && game.terminalType == 1}">  
			      <li id="gameli_equip"><a class="aaa" onclick="editHref(this)" href="/panel_bi/equip/toEuipment.ac">设备分析</a></li>
			    </c:if>
			   <li id="gameli_download"><a class="aaa" onclick="editHref(this)" href="/panel_bi/tool/toGameTool.ac">日报下载</a></li>
			   <li id="gameli_market"><a class="aaa" onclick="editHref(this)" href="/panel_bi/market/toMarket.ac">市场&资源</a></li>
			   <li id="gameli_tool"><a class="aaa" onclick="editHref(this)" href="/panel_bi/analysisTool/toGameAnalysisTool.ac">分析工具</a></li>
			   <li id="gameli_ad"><a class="aaa" onclick="editHref(this)" href="/panel_bi/adTracking/toAdTracking.ac">广告投放</a></li>
			   <li id="gameli_customreport"><a class="aaa" onclick="editHref(this)" href="/panel_bi/customTask/toTask.ac">自定义报表</a></li>
			   <li id="gameli_CPA"><a class="aaa" onclick="editHref(this)" href="/panel_bi/costPer/toCPA.ac?view=cps">CPS</a></li>
			   <li id="gameli_CPACreative"><a class="aaa" onclick="editHref(this)" href="/panel_bi/cpaCreative/toCPACreative.ac?view=cpa">CPA</a></li>
			  <!--   <li id="gameli_opMonitor"><a href="/panel_bi/opMonitor/toOpMonitor.ac">福利清单</a></li>-->
		   </c:if>
		   
		   <c:choose>
		   	<c:when test="${isAdmin}">
		   	 <!-- <li id="gameli_monitor_metric"><a class="aaa" onclick="editHref(this)" href="/panel_bi/monitor_metric/toList.ac">报警监控</a></li> -->
		   	 <li id="gameli_monitor_metric"><a class="aaa" onclick="editHref(this)" href="/panel_bi/monitor_metric/toList.ac">报警监控</a></li>
		   	</c:when>
		   </c:choose>
		   <!-- csbg -->
	
	
			<!-- <li id="gameli_TestEtl"><a href="/panel_bi/gameTestEtl/toGameTestEtl.ac">测试ETL</a></li> -->
			
		</ul>
		<input type="hidden" id="game_page" name="game_page" value="${param.game_page }" >
	</div>
<script type="text/javascript" src="/manage/js/left_auth.js?v=${version}"></script>
<script type="text/javascript" src="/manage/js/left_nav.js?v=${version}"></script>
