<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-实时</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/games/realTime/gameRealTime.js?v=${version}"></script>
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

#tabs {
	min-height: 450px;
	margin-top: 20px;
}

.data_table {
	width: 100%;
	overflow: hidden;
	margin-top: 20px;
}

.zhibiao_left {
	width: 20%;
	height: 100px;
	float: left;
	margin-top: 20px;
	border-left: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-radius: 10px;
}

.zhibiao_right {
	width: 20%;
	height: 100px;
	float: left;
	margin-top: 20px;
	border-radius: 10px;
	border: 1px solid #ebebeb;
}

.data_ul li {
	padding-top: 10px;
}

.data_ul title {
	font-size: 20px;
	text-align: center;
}

.dataTables_wrapper .dataTables_filter {
	margin-left: 10px;
	z-index: 1;
	position: absolute;
}

caption {
	font-style: normal;
	text-align: right;
	margin-right: 30px;
}

#data3 td {
    min-width: 150px;
}

tr{
	
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
			<div class="detail">
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
			      <input id="downType" name="downType" type="hidden" value="client" />
			    <ul class="detail-nav">
			       <li class="de-first active" id="realTime"><a href="#"></a>实时</li>
			       <li id="forecastHourReport"><a href="#"></a>按小时</li>
			      <c:if test="${isDisplayHourReport or isAdmin or isLeader}">
			       		<li id="hourReport"><a href="#"></a>实时日报</li>
				        <li id="hourReportSourceLtv"><a href="#"></a>分渠道注收比</li> 
				         <li id="hourReportSourceRetention"><a href="#"></a>分渠道留存</li>
			       </c:if>
			    </ul>
			    
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    		  <span class="detail-left">
			    		   <span id="selectDate" style="padding-left:20px;overflow: hidden;">
                                <span css="dateSelect" id="today" val="0" class="ui-selected">今天</span>
                                <span css="dateSelect" id="lst2day" val="1" class="last">近2天</span>
			    			</span>
			    			<input type="text" style="width:85px;border:0;color: gray" id="beginTime_rt" name="beginTime" readonly="readonly"/>
                                ~ <input type="text" style="width:85px;border:0;color: gray" id="endTime_rt" name="endTime" readonly="readonly" />
			    		 </span>
			    			  <span class="detail-right" style="margin-right: 2.5%;padding-top: 6px;text-align: right;">
			    				<button id="query" type="button" class="btn btn-default btn-sm" style="width:80px;padding-top:6px;">刷新</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
			    
			   
			   <div class="data_table">
			       <div id="install_count" class="zhibiao_left">
			          <ul class="data_ul">
			            <li style="text-align: center;font-size: 14px;">累计新注册用户数（含滚服）</li>
			            <li id="c_ul_installCount" style="text-align: center;font-size: 16px;font-weight:bold;">0</li></ul>
			       </div>
			       <div id="dau_count" class="zhibiao_left">
			          <ul class="data_ul">
			            <li style="text-align: center;font-size: 14px;">累计活跃用户数</li>
			            <li id="c_ul_dauCount" style="text-align: center;font-size: 16px;font-weight:bold;">0</li>
			          </ul>
			       </div>
			       <div id="pay_count" class="zhibiao_left">
			          <ul class="data_ul">
			            <li style="text-align: center;font-size: 14px;">累计付费(${game.currency })</li>
			            <li id="c_ul_payCount" style="text-align: center;font-size: 16px;font-weight:bold;">0</li>
			          </ul>
			       </div>
			       <div id="arpu_count" class="zhibiao_left">
			          <ul class="data_ul">
			            <li style="text-align: center;font-size: 14px;">平均ARPU(${game.currency })</li>
			            <li id="c_ul_avgARPU" style="text-align: center;font-size: 16px;font-weight:bold;">0</li>
			          </ul>
			       </div>
			       <div id="arppu_count" class="zhibiao_right">
			          <ul class="data_ul">
			            <li style="text-align: center;font-size: 14px;">平均ARPPU(${game.currency })</li>
			            <li id="c_ul_avgARPPU" style="text-align: center;font-size: 16px;font-weight:bold;">0</li>
			          </ul>
			       </div>
			    </div>
				<div id="tabs">
					<ul class="clear-fix">
						<li css="tabs" val="tabs-2" id="h_2" class="ui-selected">按日期</li>
						<li css="tabs" id="h_1" val="tabs-1">按服务器</li>
						<!-- <li css="tabs" id="h_2" val="tabs-3" class="last">按渠道</li> -->
					</ul>
					<div id="tabs-1">
					   <div id="data1" class="detail-table">
					     
					   </div>
					</div>
					<div id="tabs-2">
					   <div id="data2" class="detail-table"></div>
					</div>
					<div id="tabs-3">
					   <div id="data3" class="detail-table"></div>
					</div>
				</div>

</div>
</div>
</form:form>
<div hidden="hidden" class="template_cache">
			<table class="table_2">
			<!--   <caption>
                <button type="button" class="btn btn-default btn-sm" style="width:80px;">下载数据</button>
              </caption>-->
				<thead>
					<tr>
						<td>日期</td>
						<td>新注册(含滚服)</td>
						<td>活跃</td>
						<td>收入(${game.currency })</td>
						<td>arpu(${game.currency })</td>
						<td>arppu(${game.currency })</td>
						<td>付费用户数</td>
						<td>付费次数</td>
					</tr>
				</thead>
				<tbody>
                    <tr>
						<td class="date"></td>
						<td class="install"></td>
						<td class="dau"></td>
						<td class="pay"></td>
						<td class="arpu"></td>
						<td class="arppu"></td>
						<td class="pu"></td>
						<td class="pcnt"></td>
					</tr>
				</tbody>
			</table>
			<table class="table_1">
			 <caption>
                <button type="button" class="btn btn-default btn-sm" style="width:80px;">下载数据</button>
              </caption>
				<thead>
					<tr>
						<td>日期</td>
						<td>服务器</td>
						<td>新注册(含滚服)</td>
						<td>活跃</td>
						<td>收入(${game.currency })</td>
						<td>arpu(${game.currency })</td>
						<td>arppu(${game.currency })</td>
						<td>付费用户数</td>
						<td>付费次数</td>
					</tr>
				</thead>
				<tbody>
					<tr>
					</tr>
				</tbody>
			</table>
			
			<table class="table_3">
			 <caption>
                <button type="button" class="btn btn-default btn-sm" style="width:80px;">下载数据</button>
              </caption>
				<thead>
					<tr>
						<td>日期</td>
						<td>渠道</td>
						<td>广告位</td>
						<td>点击总数</td>
						<td>点击总数（去重）</td>
						<td>ip总数（去重）</td>
						<td>新注册</td>
						<td>激活设备数</td>
						<td>付费人数</td>
						<td>付费额(${game.currency })</td>
						<td>付费率</td>
					</tr>
				</thead>
				<tbody>
					<tr>
					</tr>
				</tbody>
			</table>
		</div>
</div>


</body>
</html>