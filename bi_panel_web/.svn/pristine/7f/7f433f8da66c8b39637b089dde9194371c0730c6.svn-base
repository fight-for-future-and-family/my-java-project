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
  <script type="text/javascript" src="/wap/js/dataTables.js?v=${version}"></script>
  <script type="text/javascript" src="/wap/js/gameRealTime.js?v=${version}"></script>
<style type="text/css">
.ajax_loading {
	background: #fff;
	height: 60px;
	width: 100%;
	text-align: center;
	line-height: 60px;
	font-size: 16px;
	display: none;
	position: fixed;
	bottom: 0px
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
</style>

</head>

<body>
<div class="lp-container">
<jsp:include page="/WEB-INF/jsp/wap/common/header.jsp" />

<form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/gameRealTime/downloadData.ac" enctype="multipart/form-data">
		<div class="data-container-main">
			<div class="detail" style="width:100%">
			      <input name="gamesId" type="hidden" value="${game.id }" />
			      <%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input name="snid" type="hidden" value="${snid }" />
				  <input name="gameId" type="hidden" value="${gameId }" />
			      <input id=gameRate type="hidden" value="${game.rate }" />
			      <input id="view" type="hidden" value="realTime" />
			      <input id="downType" name="downType" type="hidden" value="client" />
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
								<span class="tab-hd-con current" view="realTime"><a></a>按分钟</span> 
								<span class="tab-hd-con" view="forecastHourReport"><a></a>按小时</span> 
								<c:if test="${isDisplayHourReport or isAdmin or isLeader}">
								<span class="tab-hd-con" view="hourReport"><a></a>实时日报</span>
								 <span class="tab-hd-con" view="hourReportSourceLtv"><a></a>分渠道注收比</span> 
								<span class="tab-hd-con" view="hourReportSourceRetention"><a href="#"></a>分渠道留存</span>
							</c:if>
							</h2>
							<div class="tab-bd dom-display">
								<div class="tab-bd-con current">
									

									<div class="data_table">
										<div id="pay_count" style="width: 100%" class="zhibiao">
											<ul class="data_ul">
												<li style="text-align: center; font-size: 14px;">累计付费</li>
												<li id="c_ul_payCount" style="text-align: center; font-size: 16px; font-weight: bold;"></li>
											</ul>
										</div>
										<div id="install_count" style="width: 50%"
											class="zhibiao_left">
											<ul class="data_ul">
												<li style="text-align: center; font-size: 14px;">累计新注册用户数（含滚服）</li>
												<li id="c_ul_installCount" style="text-align: center; font-size: 16px; font-weight: bold;"></li>
											</ul>
										</div>
										<div id="dau_count" style="width: 50%" class="zhibiao_right">
											<ul class="data_ul">
												<li style="text-align: center; font-size: 14px;">累计活跃用户数</li>
												<li id="c_ul_dauCount" style="text-align: center; font-size: 16px; font-weight: bold;"></li>
											</ul>
										</div>
										<div id="arpu_count" style="width: 50%" class="zhibiao_left">
											<ul class="data_ul">
												<li style="text-align: center; font-size: 14px;">平均ARPU</li>
												<li id="c_ul_avgARPU" style="text-align: center; font-size: 16px; font-weight: bold;"></li>
											</ul>
										</div>
										<div id="arppu_count" style="width: 50%" class="zhibiao_right">
											<ul class="data_ul">
												<li style="text-align: center; font-size: 14px;">平均ARPPU</li>
												<li id="c_ul_avgARPPU" style="text-align: center; font-size: 16px; font-weight: bold;"></li>
											</ul>
										</div>
									</div>
									<div id="tabs">
										<div class="clear-fix" style="float: left;">
											<ul id="selectDate" class="clear-fix" style="padding-left:20px;float: left;">
												<li css="dateSelect" id="today" val="0" class="ui-selected">今天</li>
				                                <li css="dateSelect" id="lst2day" val="1" class="last">近2天</li>
											</ul>
			    							<div hidden="hidden">
			    							   <span id="beginTime_rt" style="width:88px;border:0;color: gray;text-align:left"></span>
								    			<span style="margin: 0px">~</span>
								    			<span id="endTime_rt" style="width:88px;border:0;color: gray;text-align:left"></span>
								    			<input type="hidden" name="beginTime" readonly="readonly"/>
								    			<input type="hidden" name="endTime" readonly="readonly"/>
			    							</div>
										</div>
										<ul class="clear-fix" style="float: right;margin-right: 2px;margin-top:10px;margin-bottom: 10px;">
											<li css="tabs" val="tabs-2">按日期</li>
											<li css="tabs" val="tabs-1">按服务器</li>
											<!-- <li css="tabs" val="tabs-3" class="last">按渠道</li> -->
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
										<div class='ajax_loading'>数据加载中...</div>
									</div>

								</div>
								<div class="tab-bd-con">
								
								</div>
							</div>
						</div>
					</div>
</div>
</div>
</form:form>



<div  hidden="hidden" class="template_cache">
			<table class="table_2">
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

<!-- <div class="template_cache">
			<table class="table_2" style="width:1000px;border:1px solid #F00; ">
				<thead>
				 	<tr>
				 		<td>123qqqqqqqqqqqqqqqqq</td>
				 		<td>123aaaaaaaaaaaaaaaa</td>
				 		<td>123aaaaaaaaaaaaaaaaa</td>
				 	</tr>
				</thead>
			</table>
</div> -->
</body>
</html>