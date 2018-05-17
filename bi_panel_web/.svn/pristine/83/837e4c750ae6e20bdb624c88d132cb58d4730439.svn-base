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
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>

<title>${game.name }-日报</title>

 <link rel="stylesheet" href="/css/wap/wap.css?v=${version}"/>
 <link rel="stylesheet" href="/css/wap/realTime.css?v=${version}"/>
  <link rel="stylesheet" href="/css/wap/realtimeHour.css?v=${version}"/>
<script type="text/javascript" src="/wap/js/dataTables.js?v=${version}"></script>
<script type="text/javascript" src="/wap/js/gameDailyReport.js?v=${version}"></script>
<style type="text/css">
.dataTables_wrapper .dataTables_filter {
	margin-left: 10px;
	z-index: 1;
	top: -30px;
	position: absolute;
}

.dataTables_wrapper .dataTables_filter input{
	width: 176px;
}

#caption {
	font-style: normal;
	text-align: left;
	margin-top: -5px;
	margin-right: 5px;
	float: right;
}

</style>
</head>
<body>
	<div class="lp-container">
	<jsp:include page="/WEB-INF/jsp/wap/common/header.jsp" />
		<div class="data-container-main">
			<div class="detail" style="width:100%;">
			   <ul class="detail-nav" >
			       <li id="realTime"><a></a>实时</li>
			       <li class="active" id="daily" ><a ></a>基本运营数据</li>
			       <c:if test="${game != null && game.terminalType == 1}">  
			      		<li id="equipDau"><a></a>设备数据</li>
			       </c:if>
		       		<li id="taskList" ><a ></a>自定义报表</li>
		       		
			    </ul>
			    <div class="area-sub">
					<div id="layout-t" class="tab-product tab-sub-3 ui-style-gradient">
						<h2 class="tab-hd">
							<span class="tab-hd-con current" view="daily"><a></a>日报</span> 
							<span class="tab-hd-con" view="retention"><a></a>留存</span> 
							<span class="tab-hd-con" view="life"><a></a>注收比</span>
							<span class="tab-hd-con" view="money"><a></a>金额</span>
						</h2>
					<div class="tab-bd dom-display">
					<div class="tab-bd-con current">
			     <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/wap/gameView/downloadData.ac" enctype="multipart/form-data">
			    
			      <input name="gamesId" type="hidden" value="${game.id }" />
			     <%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input name="snid" type="hidden" value="${snid }" />
				  <input name="gameId" type="hidden" value="${gameId }" />
			     <input id="gameRate" type="hidden" value="${game.rate }" />
			      <input id="view" name="view" type="hidden" value="daily" />
			      
			    <div class="detail-info" style="overflow: hidden;margin-top:10px;min-height:70px;">
			    	<ul class="detail-ul">
			    		<li class="detail-li" style="overflow: hidden;min-height:32px;">
			    		   <div style="width:100px;float: left" class="detail-left">
			    		      <select id="channel" name="channel" style="width:100px;">
			    		         <option value="all" selected="selected">总览</option>
			    		         <option value="source">分渠道</option>
			    		         <option value="client">分服</option>
			    		      </select>
			    			</div>
			    			<div id="s_c_span" style="padding-left:20px;float: left;width:60%;margin-top:-1px;" class="detail-left-append"></div>
			    		</li>
			    		<li class="detail-li">
			    			<span class="detail-left"  style="width:75%">
				    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:105px;"  value="${beginTime }" id="beginTime" name="beginTime"/>
				    			<span>至</span>
				    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:105px;" value="${endTime }" id="endTime" name="endTime" />
			    			</span>
			    			<span class="detail-right" style="width:25%;margin-right: 0px">
			    				<button id="query" type="button" class="btn btn-default btn-sm" style="width:80px;padding-top: 3px;margin-right: 5px;">查询</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
		       <div id="data" class="detail-table">
		       </div>
		       <div id="ajax_data" class="detail-table" hidden="hidden">
		       <div id="caption" hidden="hidden">
		   			 <button type="button"  class="btn btn-default btn-sm" style="width:80px;">下载数据</button>
               </div>
		       </div>
			 </form:form>
			</div>
			</div>
			</div>
			</div>
			</div>
		</div>
		
		<div class="template_cache" >
		<table class="dataTable">
			<thead>
				<tr>
					<td>日期</td>
					<td>激活设备数</td>
					<td>新注册</td>
					<td>创角数</td>
					<td>dau</td>
					<td>老玩家活跃</td>
					<td>付费额(${game.currency })</td>
					<td>付费人数</td>
					<td>arpu(${game.currency })</td>
					<td>arppu(${game.currency })</td>
					<td>付费次数</td>
					<td>付费率(%)</td>
					<!-- 
					<td>新付费额</td>
					<td>新付费人数</td>
					 -->
					  <td>新注册用户付费率(%)</td>
					 <td>新注册用户arpu</td>
					<td>注册付费额(${game.currency })</td>
					<td>注册付费人数</td>
					<td>滚服付费额(${game.currency })</td>
					<td>滚服付费人数</td>
					<td>滚服人数</td>
					<td>最高在线人数</td>
					<td>平均在线人数</td>
					<td>平均在线时长</td>
				</tr>
				<tr>
					<td>日期</td>
					<td>激活设备数</td>
					<td>新注册</td>
					<td>创角数</td>
					<td>dau</td>
					<td>老玩家活跃</td>
					<td>付费额(${game.currency })</td>
					<td>付费人数</td>
					<td>arpu(${game.currency })</td>
					<td>arppu(${game.currency })</td>
					<td>付费次数</td>
					<td>付费率(%)</td>
					<!-- 
					<td>新付费额</td>
					<td>新付费人数</td>
					 -->
					 <td>新注册用户付费率(%)</td>
					 <td>新注册用户arpu</td>
					<td>注册付费额(${game.currency })</td>
					<td>注册付费人数</td>
					<td>滚服付费额(${game.currency })</td>
					<td>滚服付费人数</td>
					<td>滚服人数</td>
					<td>最高在线人数</td>
					<td>平均在线人数</td>
					<td>平均在线时长</td>
					<td>广告点击总数</td>
					<td>广告点击总数（去重）</td>
					<td>ip总数（去重）</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="day"></td>
					<td class="newEquip"></td>
					<td class="dnu"></td>
					<td class="roleChoice"></td>
					<td class="dau"></td>
					<td class="old_dau"></td>
					<td class="paymentAmount"></td>
					<td class="pu"></td>
					<td class="arpu"></td>
					<td class="arppu"></td>
					<td class="paymentCnt"></td>
					<td class="payRate"></td>
					<!-- 
					<td class="newPayAmount"></td>
					<td class="newPu"></td>
					 -->
					 <td class="newDnuUserPayRate"></td>
					 <td class="newDnuUserArpu"></td>
					<td class="installPayAmount"></td>
					<td class="installPu"></td>
					<td class="rollAmount"></td>
					<td class="rollPayUsers"></td>
					<td class="rollUsers"></td>
					<td class="pcu"></td>
					<td class="acu"></td>
					<td class="avgUserTime"></td>
				</tr>
				<tr>
					<td class="day"></td>
					<td class="newEquip"></td>
					<td class="dnu"></td>
					<td class="roleChoice"></td>
					<td class="dau"></td>
					<td class="old_dau"></td>
					<td class="paymentAmount"></td>
					<td class="pu"></td>
					<td class="arpu"></td>
					<td class="arppu"></td>
					<td class="paymentCnt"></td>
					<td class="payRate"></td>
					<!-- 
					<td class="newPayAmount"></td>
					<td class="newPu"></td>
					 -->
					 <td class="newDnuUserPayRate"></td>
					 <td class="newDnuUserArpu"></td>
					<td class="installPayAmount"></td>
					<td class="installPu"></td>
					<td class="rollAmount"></td>
					<td class="rollPayUsers"></td>
					<td class="rollUsers"></td>
					<td class="pcu"></td>
					<td class="acu"></td>
					<td class="avgUserTime"></td>
					<td class="idfa"></td>
					<td class="distinctIdfa"></td>
					<td class="distinctIp"></td>
				</tr>
			</tbody>
			<tfoot></tfoot>
		</table>
		<table class="ajax_table">
			<thead>
				<tr>
					<td>日期</td>
					<td class="head_type">渠道</td>
					
					<td>激活设备数</td>
					<td>新注册</td>
					<td>创角数</td>
					<td>dau</td>
					<td>老玩家活跃</td>
					<td>付费额(${game.currency })</td>
					<td>付费人数</td>
					<td>arpu(${game.currency })</td>
					<td>arppu(${game.currency })</td>
					<td>付费次数</td>
					<td>付费率(%)</td>
					<!-- 
					<td>新付费额</td>
					<td>新付费人数</td>
					 -->
					  <td>新注册用户付费率(%)</td>
					 <td>新注册用户arpu</td>
					<td>注册付费额(${game.currency })</td>
					<td>注册付费人数</td>
					<td>滚服付费额(${game.currency })</td>
					<td>滚服付费人数</td>
					<td>滚服人数</td>
					<td>最高在线人数</td>
					<td>平均在线人数</td>
					<td>平均在线时长</td>
				</tr>
				<tr>
					<td>日期</td>
					<td class="head_type">渠道</td>
					
					<td>激活设备数</td>
					<td>新注册</td>
					<td>创角数</td>
					<td>dau</td>
					<td>老玩家活跃</td>
					<td>付费额(${game.currency })</td>
					<td>付费人数</td>
					<td>arpu(${game.currency })</td>
					<td>arppu(${game.currency })</td>
					<td>付费次数</td>
					<td>付费率(%)</td>
					<!-- 
					<td>新付费额</td>
					<td>新付费人数</td>
					 -->
					 <td>新注册用户付费率(%)</td>
					 <td>新注册用户arpu</td>
					<td>注册付费额(${game.currency })</td>
					<td>注册付费人数</td>
					<td>滚服付费额(${game.currency })</td>
					<td>滚服付费人数</td>
					<td>滚服人数</td>
					<td>最高在线人数</td>
					<td>平均在线人数</td>
					<td>平均在线时长</td>
					<td>广告点击总数</td>
					<td>广告点击总数（去重）</td>
					<td>ip总数（去重）</td>
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