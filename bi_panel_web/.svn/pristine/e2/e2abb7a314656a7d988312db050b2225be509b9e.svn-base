<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="robots" content="noodp, noydir" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>${game.name }-实时</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
 <link rel="stylesheet" href="/css/wap/wap.css?v=${version}"/>
 <link rel="stylesheet" href="/css/wap/realTime.css?v=${version}"/>
 <link rel="stylesheet" href="/css/wap/realtimeHour.css?v=${version}"/>
<script type="text/javascript" src="/wap/js/reportEachTime.js?v=${version}"></script>
<script type="text/javascript" src="/wap/js/dataTables.js?v=${version}"></script>
<style>
.hour_data_table {
	border-collapse: collapse !important;
	width:100%;
	margin-left: 0%;
}

.hour_data_table>thead>tr>th {
	background-color: rgba(236, 236, 236, 0.48);
	border-bottom: 0;
	font-weight: normal;
	text-align: center;
	min-width: 150px;
}

.hour_data_table>tbody>tr>td {
	border-bottom: 1px solid #ddd;
	border-top: 0;
	font-weight: normal;
	font-size: 14px;
}

.hour_data_table>tbody>tr>td.bold {
	font-weight: bold;
	font-size: 16px;
	color: #322998;
}

.zhibiao {
	width: 100%;
	height: 480px;
	float: left;
	margin-top: 20px;
}

.tab_data_table {
	border-collapse: collapse !important;
	width:100%;
	margin-left: 0%;
	text-align: left;
	background-color: rgba(236, 236, 236, 0.48);
}

.tab_data_table>tbody>tr>td {
    padding:5px 5px;
	border-top: 0;
	font-weight: normal;
	font-size: 14px;
	height: 10px;
}



.tab-sub .tab-bd-con{padding-right:9px;padding-left:3px;}
</style>
</head>

<body>
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/wap/common/header.jsp" />

		<div class="data-container-main">
			<div class="detail" style="overflow: hidden">
				<input name="gamesId" type="hidden" value="${game.id }" /> 
				<%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input name="snid" type="hidden" value="${snid }" />
				  <input name="gameId" type="hidden" value="${gameId }" /> 
				<input id="view" type="hidden" value="realTime" /> 
				<input id="gameRate" type="hidden" value="${game.rate }" /> 
				<input id="gameCurrency" type="hidden" value="${game.currency }" /> 
				<input id="timeZone" type="hidden" value="${game.timeZone }" /> 
				<input id="view" type="hidden" value="forecastHourReport" />
				<ul class="detail-nav">
			       <li class="de-first active" id="realTime"><a></a>实时</li>
			       <li id="daily" ><a ></a>基本运营数据</li>
		       		<c:if test="${game != null && game.terminalType == 1}">
			       		<li id="equipDau"><a></a>设备数据</li>
			       </c:if>
		       		<li id="taskList" ><a ></a>自定义报表</li>
		       		
			    </ul>

				<div class="area-sub">
					<div id="layout-t" class="tab-product tab-sub-3 ui-style-gradient">
						<h2 class="tab-hd">
							<span class="tab-hd-con" view="realTime"><a></a>按分钟</span> 
							<span class="tab-hd-con current" view="forecastHourReport"><a></a>按小时</span>
							<c:if test="${isDisplayHourReport or isAdmin or isLeader}">
								<span class="tab-hd-con" view="hourReport"><a></a>实时日报</span>
								<span class="tab-hd-con" view="hourReportSourceLtv"><a></a>分渠道注收比</span> 
								<span class="tab-hd-con" view="hourReportSourceRetention"><a href="#"></a>分渠道留存</span>
							</c:if>
						</h2>
						<div class="tab-bd dom-display">
							<div class="tab-bd-con"></div>
							<div class="tab-bd-con current">
								<div id="data" style="width:100%;height:100%;margin-top: 10px;overflow:auto;"></div>
								<%-- 
								<div style="margin-top:-20px; width:100%;height:100%;overflow:auto;">
								     <table class="tab_data_table">
								        <tr>
								           <td colspan="2"><input  type="radio" value="install" text="累计新注册用户数（含滚服）" name="data_radio_in"> 累计新注册用户数（含滚服）</td>
								           <td><input  type="radio" value="dau" text="累计活跃用户数" name="data_radio_in"> 累计活跃用户数</td>
								        </tr>
								        <tr>
								           <td><input  type="radio" value="payment" text="累计付费(${game.currency })" name="data_radio_in"> 累计付费(${game.currency })</td>
								           <td style="text-align: center"><input  type="radio" value="arpu" text="平均ARPU(${game.currency })" name="data_radio_in"> 平均ARPU(${game.currency })</td>
								           <td><input  type="radio" value="arppu" text="平均ARPPU(${game.currency })" name="data_radio_in"> 平均ARPPU(${game.currency })</td>
								        </tr>
								     </table>
								</div>
				 --%>
				               <!--  <div id="chartDiv" class="clear-fix" style="width: 100%;float: left;overflow: auto;">
							        <div id="chart_data"> 
							        </div>
							   </div> -->
							
							
								<div id="tabs">
								
										<div class="clear-fix" style="float: left; margin-bottom:5px">
											<ul id="selectDate" class="clear-fix" style="padding-left:20px;float: left;width:400px">
												<li css="dateSelect" id="today" val="0" class="ui-selected" style="width:60px">总览</li>
				                               <!--   <li css="dateSelect" id="lst2day" val="1" class="last">近2天</li> -->
					                        	<li css="tabs" val="tabs-7" style="width:60px">按小时</li>
												<li css="tabs" val="tabs-6" style="width:80px">总览汇总</li>
												<!-- <li css="tabs" val="tabs-1" style="width:60px">服务器</li> -->
												<li css="tabs" val="tabs-3" class="last" style="width:100px">分服分渠道总览</li>
											</ul>
			    							<div hidden="hidden">
			    							   <span id="beginTime_rt" style="width:88px;border:0;color: gray;text-align:left"></span>
								    			<span style="margin: 0px">~</span>
								    			<span id="endTime_rt" style="width:88px;border:0;color: gray;text-align:left"></span>
								    			<input type="hidden" name="beginTime" readonly="readonly"/>
								    			<input type="hidden" name="endTime" readonly="readonly"/>
			    							</div>
			    						
										</div>
										
										   
										<!-- <ul class="clear-fix" style="float: left;margin-top:10px; margin-bottom: 10px;">
											
											<li css="tabs" val="tabs-7">按小时</li>
											<li css="tabs" val="tabs-6">总览汇总</li>
											<li css="tabs" val="tabs-1">按服务器</li>
											 <li css="tabs" val="tabs-3" class="last">按渠道</li>
										</ul> -->
										
										 <div id="select" style="width:300px;height:20px; ">
											    		   <div style="float: left;">
											    		      <select id="channel" name="channel" style="width:80px;">
											    		         <option value="source" selected="selected">分渠道</option>
											    		         <option value="client">分服</option>
											    		      </select>
											    			</div>
											    			<div id="s_c_span" style="height:30px;float:left;margin-left:10px;"></div>
										 </div>
										<div id="tabs-1">
											<div id="data1" class="detail-table" style="margin-top: 20px;clear:both;">
											</div>
										</div>
										<div id="tabs-2">
											<div id="data2" class="detail-table" style="margin-top: 20px;clear:both;">
											</div>
										</div>
										<div id="tabs-3">
					   						<div id="data3" class="detail-table" style="margin-top: 20px;clear:both;"></div>
										</div>
										
										<div id="tabs-6">
											<div id="data6" class="detail-table" style="margin-top: 20px;clear:both;">
											</div>
										</div>
										
										<div id="tabs-7">
											<div id="data7" class="detail-table" style="margin-top: 20px;clear:both;">
											</div>
										</div>
										<!-- <div class='ajax_loading'>数据加载中...</div> -->
									</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div hidden="hidden" class="template_cache">
	<table class="dataTable_temp">
		<thead>
			<%-- <tr>
				<th width="8%"></th>
				<th>累计新注册用户数（含滚服）</th>
				<th>累计活跃用户数</th>
				<th>累计付费(${game.currency })</th>
				<th>平均ARPU(${game.currency })</th>
				<th>平均ARPPU(${game.currency })</th>
			</tr> --%>
				<th width="8%"></th>
				<th>累计新注册用户数</th>
				<th>激活设备</th>
				<th>累计活跃用户数</th>
				<th>累计付费(${game.currency })</th>
				<th>平均ARPU(${game.currency })</th>
				<th>平均ARPPU(${game.currency })</th>
		</thead>
		<tbody>
			<tr class="first">
				<td>今日</td>
				<td class="bold">0</td>
			</tr>
			<tr class="second">
				<td>昨日</td>
				<td>0</td>
			</tr>
			<!-- <tr class="third">
				<td>前7日</td>
				<td>0</td>
			</tr>
			<tr class="fourth">
				<td>前30日</td>
				<td>0</td>
			</tr>
			<tr class="fifth">
				<td>预计今日</td>
				<td>0</td>
			</tr> -->
		</tbody>
	</table>
	
	<table class="table_2" id="font">
				<thead>
					<tr>
						<td style="width:1px;">时间</td>
						<td>今日新注册</td>
						<td>昨日新注册</td>
						<td>今日设备</td>
						<td>昨日设备</td>
						<td>今日活跃</td>
						<td>昨日活跃</td>
						<td>今日收入(${game.currency })</td>
						<td>昨日收入(${game.currency })</td>
						<td>今日ARPU(${game.currency })</td>
						<td>昨日ARPU(${game.currency })</td>
						<td>今日ARPPU(${game.currency })</td>
						<td>昨日ARPPU(${game.currency })</td>
						
						<%-- <td>付费用户数</td>
						<td>arpu(${game.currency })</td>
						<td>arppu(${game.currency })</td> --%>
						
					
					</tr>
				</thead>
				<tbody>
                    <tr>
						<td class="date"></td>
						<td class="install"></td>
						<td class="yesterday_install"></td>
						<td class="equips"></td>
						<td class="yesterday_equips"></td>
						<td class="dau"></td>
						<td class="yesterday_dau"></td>
						<td class="pay"></td>
						<td class="yesterday_pay"></td>
						<!-- <td class="pu"></td> --> 
						<td class="arpu"></td>
						<td class="yesterday_arpu"></td>
						<td class="arppu"></td>
						<td class="yesterday_arppu"></td>
						
					
					</tr>
				</tbody>
			</table>
			<table class="table_6">
		
				<thead>
					<tr>
						<td>时间</td>
						<td>今日新注册</td>
						<td>昨日新注册</td>
						<td>今日设备</td>
						<td>昨日设备</td>
						<td>今日活跃</td>
						<td>昨日活跃</td>
						<td>今日收入(${game.currency })</td>
						<td>昨日收入(${game.currency })</td>
						<td>今日ARPU(${game.currency })</td>
						<td>昨日ARPU(${game.currency })</td>
						<td>今日ARPPU(${game.currency })</td>
						<td>昨日ARPPU(${game.currency })</td>
					</tr>
				</thead>
				<tbody>
                    <tr>
						<td class="date"></td>
						<td class="install"></td>
						<td class="yesterday_install"></td>
						<td class="equips"></td>
						<td class="yesterday_equips"></td>
						<td class="dau"></td>
						<td class="yesterday_dau"></td>
						<td class="pay"></td>
						<td class="yesterday_pay"></td>
						<td class="arpu"></td>
						<td class="yesterday_arpu"></td>
						<td class="arppu"></td>
						<td class="yesterday_arppu"></td>
					</tr>
				</tbody>
			</table>
			
			<table class="table_7">
		
				<thead>
					<tr>
						<td>时间</td>
						<td>今日新注册</td>
						<td>昨日新注册</td>
						<td>今日设备</td>
						<td>昨日设备</td>
						<td>今日活跃</td>
						<td>昨日活跃</td>
						<td>今日收入(${game.currency })</td>
						<td>昨日收入(${game.currency })</td>
						<td>今日ARPU(${game.currency })</td>
						<td>昨日ARPU(${game.currency })</td>
						<td>今日ARPPU(${game.currency })</td>
						<td>昨日ARPPU(${game.currency })</td>
						
					</tr>
				</thead>
				<tbody>
                    <tr>
						<td class="date"></td>
						<td class="install"></td>
						<td class="yesterday_install"></td>
						<td class="equips"></td>
						<td class="yesterday_equips"></td>
						<td class="dau"></td>
						<td class="yesterday_dau"></td>
						<td class="pay"></td>
						<td class="yesterday_pay"></td>
						<td class="arpu"></td>
						<td class="yesterday_arpu"></td>
						<td class="arppu"></td>
						<td class="yesterday_arppu"></td>
					</tr>
				</tbody>
			</table>
			<table class="table_3">
			
				<thead>
					<tr>
						<td>时间</td>
						<td>渠道</td>
						<td>今日注册</td>
						<td>昨日注册</td>
						<td>今日设备</td>
						<td>昨日设备</td>
						<td>今日活跃</td>
						<td>昨日活跃</td>
						<td>今日收入(${game.currency })</td>
						<td>昨日收入(${game.currency })</td>
						<%-- <td>付费用户数</td> --%>
						<td>今日ARPU(${game.currency })</td>
						<td>昨日ARPU(${game.currency })</td>
						<td>今日ARPPU(${game.currency })</td>
						<td>昨日ARPPU(${game.currency })</td>
						
					</tr>
				</thead>
				<tbody>
					<tr>
					</tr>
				</tbody>
			</table>
			<table class="table_1">
					<!--  <caption>
		         	  <button type="button" class="btn btn-default btn-sm" style="width:80px;">下载数据</button>
		         	  <div style="height:20px;"></div>
		      		</caption>
		      	 -->
				<thead>
					<tr>
						<td>时间</td>
						<td>服务器</td>
						<td>今日注册</td>
						<td>昨日注册</td>
						<td>今日活跃</td>
						<td>昨日活跃</td>
						<td>今日收入(${game.currency })</td>
						<td>今日收入(${game.currency })</td>
						<%-- <td>付费用户数</td>
						<td>arpu(${game.currency })</td>
						<td>arppu(${game.currency })</td> --%>
						<td>今日ARPU(${game.currency })</td>
						<td>昨日ARPU(${game.currency })</td>
						<td>今日ARPPU(${game.currency })</td>
						<td>昨日ARPPU(${game.currency })</td>
						
					</tr>
				</thead>
				<tbody>
					<tr>
					</tr>
				</tbody>
			</table>
	<table class="chart_table_templete">
		<thead>
			<tr>
				<td></td>
				<td>今日</td>
				<td>昨日</td>
				<td>前7日</td>
				<td>前30日</td>
			</tr>
		</thead>
		<tbody>
		    <tr>
				<td style="min-width: 80px;">1</td>
				<td style="min-width: 120px;">今日</td>
				<td style="min-width: 80px;">昨日</td>
				<td style="min-width: 80px;">前7日</td>
				<td style="min-width: 80px;">前30日</td>
			</tr>
		</tbody>
	</table>
</div>
</body>
</html>