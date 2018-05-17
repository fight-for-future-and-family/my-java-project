<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-实时</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
 <link rel="stylesheet" href="/css/wap/realtimeHour.css?v=${version}"/>
<script type="text/javascript" src="/js/games/realTime/reportEachTime.js?v=${version}"></script>
<style>
#selectDate span.ui-selected,#tabs li.ui-selected {
	background: #56C887;
	color: white;
}

#selectDate span.last,#tabs li.last {
	border-right: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
}

#selectDate span,#tabs li {
	float: left;
	width: 85px;
	margin: -3px auto;
	text-align: center;
	font-size: 14px;
	line-height: 27px;
	border-left: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
}

.data_table{
   border-collapse: collapse !important
}

.data_table>thead>tr>th{
	background-color: rgba(236, 236, 236, 0.48);
	border-bottom: 0;
	font-weight: normal;
	text-align: center;
	font-size: 14px;
}

.data_table>tbody>tr>td{
	border-bottom: 1px solid #ddd;
	border-top:0;
	font-weight: normal;
	font-size: 14px;
}
.data_table>tbody>tr>td.bold{
font-weight: bold;
font-size: 16px;
color: #322998;
}

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

.zhibiao {
	width: 100%;
	height: 350px;
	float: left;
	margin-top: 20px;
}

.dataTables_wrapper .dataTables_filter {
	margin-left: 10px;
	z-index: 1;
	position: absolute;
}

.DTTT_container .DTTT_button_csv{
    margin-left: 5px;
    text-align: center;
    font-size: 14px;
    padding: 2px 10px;
    border: 1px solid #ebebeb;
    background-color: #fff;
    border-radius: 5px;
}

caption {
	font-style: normal;
	text-align: right;
	margin-right: 20px;
}

#data_radio{
	margin-top: 155px;
	height: 40px;
	background-color: rgba(236, 236, 236, 0.48);
	text-align: left;
	padding: 10px;
	font-size: 13px;
}

#data_radio input{
margin-left: 15px;
}

</style>
</head>

<body class="dt-example">
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<div class="lp-container">
        <jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_realTime" name="game_page"/>
		</jsp:include>
		<form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/gameRealTime/downloadData.ac" enctype="multipart/form-data">
	<div class="data-container">
			<div class="detail" style="overflow: hidden">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name}</li>
				<li>></li>
		    	<li>实时</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
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
			      <input id="downType" name="downType" type="hidden" value="hourRealTime" />
			    <ul class="detail-nav">
			       <li id="realTime"><a href="#"></a>实时</li>
			       <li id="forecastHourReport" class="active" ><a href="#"></a>按小时</li>
			       <c:if test="${isDisplayHourReport or isAdmin or isLeader}">
			       		<li id="hourReport"><a href="#"></a>实时日报</li>
			       	   <li id="hourReportSourceLtv"><a href="#"></a>分渠道注收比</li> 
			       		  <li id="hourReportSourceRetention"><a href="#"></a>分渠道留存</li>
			       </c:if>
			       
			    </ul>
			   
			      <div id="data" style="padding: 0 1%;margin-top: 20px;">
				
				   </div>
				
				<div id="data_radio">
				 <input  type="radio" value="install" text="新注册用户数（含滚服）" name="data_radio_in"> 新注册用户数（含滚服）
				 <input  type="radio" value="equips" text="激活设备" name="data_radio_in"> 激活设备
				 <input  type="radio" value="dau" text="活跃用户数" name="data_radio_in"> 活跃用户数
				 <input  type="radio" value="payment" text="付费(${game.currency })" name="data_radio_in"> 付费(${game.currency })
				 <input  type="radio" value="arpu" text="平均ARPU(${game.currency })" name="data_radio_in"> 平均ARPU(${game.currency })
				 <input  type="radio" value="arppu" text="平均ARPPU(${game.currency })" name="data_radio_in"> 平均ARPPU(${game.currency })
				</div>
				
				<div id="chartDiv" class="clear-fix" style="width: 100%;float: left;overflow: hidden;position: relative;">
				   <!-- <div class="dataShap">
			           <ul style="top:35px;right:10px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div> -->
			        <div id="chart" class="zhibiao"></div>
			        <div id="chart_data" class="zhibiao" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:5px 0px;"> </div>
				</div>
				
				
				
				<div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    		   <span>
			    		      <select id="indicators" name="indicators" style="width:100px;">
			    		        <option value="source" selected="selected">分渠道</option>
			    		    <!--  <option value="client">分服</option>  -->
			    		      </select>
			    			</span>
			    			<span id="s_c_span" style="padding-left:20px;"></span>
			    		</li>
			    	</ul>
			    </div>
			    <div id="data_source_radio" style="float: left;margin-top: 10px;">
					 <input  type="radio" value="install" text="新注册用户数（含滚服）" name="data_radio_source_in"> 新注册用户数（含滚服）
					 <input  type="radio" value="equips" text="激活设备" name="data_radio_source_in"> 激活设备
					 <input  type="radio" value="dau" text="活跃用户数" name="data_radio_source_in"> 活跃用户数
					 <input  type="radio" value="payment" text="付费(${game.currency })" name="data_radio_source_in"> 付费(${game.currency })
					 <input  type="radio" value="arpu" text="平均ARPU(${game.currency })" name="data_radio_source_in"> 平均ARPU(${game.currency })
					 <input  type="radio" value="arppu" text="平均ARPPU(${game.currency })" name="data_radio_source_in"> 平均ARPPU(${game.currency })
				</div>
				<div id="chartDiv2" class="clear-fix" style="width: 100%;float: left;overflow: hidden;position: relative;">
				   <!-- <div class="dataShap">
			           <ul style="top:35px;right:10px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div> -->
			        <div id="chart2" class="zhibiao"></div>
			        <div id="chart_data2" class="zhibiao" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:5px 0px;"> </div>
				</div>
				
				<div id="tabs">
					<div class="clear-fix" style="float: left;">
						<ul id="selectDate" class="clear-fix" style="padding-left:20px;margin-top:10px;float: left;">
							<li css="tabs" id="today" val="tabs-2" class="ui-selected">总览</li>
						</ul>
  							<div hidden="hidden">
  							   <span id="beginTime_rt" style="width:88px;border:0;color: gray;text-align:left"></span>
			    			<span style="margin: 0px">~</span>
			    			<span id="endTime_rt" style="width:88px;border:0;color: gray;text-align:left"></span>
			    			<input type="hidden" name="beginTime" readonly="readonly"/>
			    			<input type="hidden" name="endTime" readonly="readonly"/>
  							</div>
					</div>
				
					
			
					 
					<ul class="clear-fix" style="float: right;margin-right: 2px;">
						 
						<li css="tabs" val="tabs-7">按小时</li>
						<li css="tabs" val="tabs-6">总览汇总</li>
						<li css="tabs" val="tabs-1">按服务器</li>
						<li css="tabs" val="tabs-3" class="last">按渠道</li>
					</ul>
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
    </form:form>
</div>
<div hidden="hidden" class="template_cache">
	<table class="dataTable_temp">
		<thead>
			<tr>
				<th width="8%">
				</th>
				<th>累计新注册用户数（含滚服）</th>
				<th>激活设备</th>
				<th>累计活跃用户数</th>
				<th>累计付费(${game.currency })</th>
				<th>平均ARPU(${game.currency })</th>
				<th>平均ARPPU(${game.currency })</th>
			</tr>
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
		</tbody>
	</table>
	<table class="chart_table_templete">
	<!--  <caption>
        <button type="button" class="btn btn-default btn-sm" style="width:80px;">下载数据</button>
     </caption>-->
		<thead>
			<tr>
				<td></td>
				<td>今日</td>
				<td>昨日</td>
			</tr>
		</thead>
		<tbody>
		    <tr>
				<td style="width:25%">1</td>
				<td>今日</td>
				<td>昨日</td>
			</tr>
		</tbody>
	</table>
	
	<table class="table_2">
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
			 <caption>
              <!--   <button type="button" class="btn btn-default btn-sm" style="width:80px;">下载数据</button> -->
              </caption>
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
					 <caption>
		         	 <!--  <button type="button" class="btn btn-default btn-sm" style="width:80px;">下载数据</button> -->
		      		</caption>
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
</div>
</body>
</html>