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

<title>${game.name }-设备数据</title>

 <link rel="stylesheet" href="/css/wap/wap.css?v=${version}"/>
 <link rel="stylesheet" href="/css/wap/realTime.css?v=${version}"/>
  <link rel="stylesheet" href="/css/wap/realtimeHour.css?v=${version}"/>
<script type="text/javascript" src="/wap/js/dataTables.js?v=${version}"></script>
<script type="text/javascript" src="/wap/js/equip/newEquipRetention.js?v=${version}"></script>
<style type="text/css">
.dataTables_wrapper .dataTables_filter {
	margin-left: 10px;
	z-index: 1;
	top: -35px;
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
	<jsp:include page="/WEB-INF/jsp/wap/common/header.jsp" />
	<div class="data-container-main">
			<div class="detail" style="width:100%;">
			   <ul class="detail-nav">
			       <li id="realTime"><a></a>实时</li>
			       <li id="daily" ><a ></a>基本运营数据</li>
			       <c:if test="${game != null && game.terminalType == 1}">
			       		<li id="equipDau" class="active"><a></a>设备数据</li>
			       </c:if>
		       		<li id="taskList" ><a ></a>自定义报表</li>
		       		
			    </ul>
			    <div class="area-sub">
					<div id="layout-t" class="tab-product tab-sub-3 ui-style-gradient">
						<h2 class="tab-hd">
							<span class="tab-hd-con" view="equipDau"><a></a>设备活跃</span> 
							<span class="tab-hd-con" view="versionDau"><a></a>版本活跃分布</span> 
							<span class="tab-hd-con current" view="installRetention"><a></a>新增设备留存</span>
						</h2>
					<div class="tab-bd dom-display">
					<div class="tab-bd-con current">
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/equip/downloadData.ac" enctype="multipart/form-data">
					<input name="gamesId" type="hidden" value="${game.id }" />
					<%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input name="snid" type="hidden" value="${snid }" />
				  <input name="gameId" type="hidden" value="${gameId }" />
					<input id="gameRate" type="hidden" value="${game.rate }" />
					<input id="gameCurrency" type="hidden" value="${game.currency }" />
					<input id="timeZone" type="hidden" value="${game.timeZone }" />
					<input id="view" name="view" type="hidden" value="installRetention" />
					<input id="queryType" name="queryType" type="hidden" value="amount" />
					<div class="detail-info" style="overflow: hidden;margin-top:10px;min-height:70px;">
				    	<ul class="detail-ul">
				    		<li class="detail-li">
				    		<span class="detail-left" style="width: 100%">
				    		   <span>
				    		      <select id="indicators" name="indicators" style="width:100px;">
				    		         <option value="all" selected="selected">总览</option>
				    		         <option value="source">分渠道</option>
				    		         <option value="model">分机型</option>
				    		      </select>
				    			</span>
				    			<span id="s_c_span" style="padding-left:10px;"></span>
				    		</span>
				    		</li>
				    		<li  class="detail-li" style="overflow: hidden;height:35px">
				    		<span class="detail-left" style="width: 75%">
				    		<span>
				    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="${beginTime }" id="beginTime" name="beginTime"/>
				    			<span>至</span>
				    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${endTime }" id="endTime" name="endTime" />
				    		</span>
				    		</span>
				    		<span class="detail-right" style="width:25%;margin-right: 0px">
				    			<button id="query" type="button" class="btn btn-default btn-sm" style="width:80px;padding-top: 3px;margin-right: 5px;">查询</button>
				    		</span>
				    		</li>
				    	</ul>
				    </div>
			   		 <div id="data" class="detail-table">
			   		   <div id="caption">
		   					<button type="button"  class="btn btn-default btn-sm" style="width:80px;margin-left: 10px;margin-top: 5px;">下载数据</button>
                       </div>
                       
			   		 </div>
			    </form:form>
			</div>
			</div>
			</div>
			</div>
		</div>
	</div>
	<div hidden="hidden" class="template_cache">
		 <table class="view_table">
	      <thead>
	      <tr>
	      	<td>日期</td>
	      	<td>新增设备</td>
	      	<td class="d1">D1</td>
	      	<td class="d2">D2</td>
	      	<td class="d3">D3</td>
	      	<td class="d4">D4</td>
	      	<td class="d5">D5</td>
	      	<td class="d6">D6</td>
	      	<td class="d7">D7</td>
	      </tr>
	      </thead>
          <tbody></tbody>
	   </table>
		 <table class="view_table_1">
	      <thead>
	      <tr>
	      	<td>日期</td>
	      	<td>渠道</td>
	      	<td>新增设备</td>
	      	<td class="d1">D1</td>
	      	<td class="d2">D2</td>
	      	<td class="d3">D3</td>
	      	<td class="d4">D4</td>
	      	<td class="d5">D5</td>
	      	<td class="d6">D6</td>
	      	<td class="d7">D7</td>
	      </tr>
	      </thead>
          <tbody></tbody>
	   </table>
	</div>
</body>
</html>