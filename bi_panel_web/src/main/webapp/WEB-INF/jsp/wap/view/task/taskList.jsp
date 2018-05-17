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
<title>自定义报表</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<link rel="stylesheet" href="/css/main/main.css?v=${version}"  />
<link rel="stylesheet" href="/css/wap/wap.css?v=${version}"/>
<script type="text/javascript" src="/wap/js/dataTables.js?v=${version}"></script>
<style type="text/css">
.float-left {
	float: left;
}

.dataTables_wrapper .dataTables_filter {
	margin-left: 10px;
	z-index: 1;
	position: absolute;
}

.caption {
	font-style: normal;
	text-align: right;
	margin-right: 30px;
}

.downBtn, .add-btn, .query-btn, .reflush-btn {
	margin-left: 10px;
	text-align: center;
	font-size: 14px;
	padding: 2px 10px;
	border: 1px solid #ebebeb;
	border-radius: 5px;
	background-color: #fff;
}

#data tbody tr{
	background-color: #99f21b !important;
}
</style>
<script type="text/javascript" src="/wap/js/task/customTask.js?v=${version}"></script>
</head>
<body>
<div class="lp-container">
<jsp:include page="/WEB-INF/jsp/wap/common/header.jsp" />
    <form:form id="adForm" name="adForm" method="post" modelAttribute="taskVO" action="/panel_bi/wap/gameView/toWapGameDatasView.ac?view='+view" enctype="multipart/form-data">
		<div class="data-container-main" style="min-height:100%;">
			<div class="detail" >
			    <ul class="detail-nav">
			       <li id="realTime"><a></a>实时</li>
			       <li id="daily" ><a ></a>基本运营数据</li>
		       		<c:if test="${game != null && game.terminalType == 1}">
			       		<li id="equipDau"><a></a>设备数据</li>
			       </c:if>
		       		<li id="taskList" class="active" style="margin-right:-10px"><a ></a>自定义报表</li>
		       	
			    </ul>
			      <input name="id" type="hidden" value="${game.id }" />
			      <%-- <input id="snid" name="snid" type="hidden" value="${game.snid }" />
			      <input id="gameid" name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input id="snid" name="snid" type="hidden" value="${snid }" />
			      <input id="gameid" name="gameId" type="hidden" value="${gameId }" />
			      <input name="gamesId" type="hidden" value="${game.id }" />
			      <input id="view" type="hidden" value="taskList" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" type="hidden" value="task" />
			</div>
			<div class="queryTask">
				<table id="taskSelect" style="width: 100%;text-align:left">
				  <tr class="model">
				     <th style="width:100px">选择模板</th>
				     <td colspan="3" >
				         <select id="model" style="float: left;width: 50%">
				            <option value="-1">请选择...</option>
				         </select>
				         <button id="queryTask" class="btn btn-default btn-sm" type="button" style="margin-left: 1%;float:left;height:24px" >查询</button>
				         <span id="str1" style="color: red;width:100px;">内容不能为空！</span>
				     </td>
				  </tr>
			   </table>
			</div>
			<div id="tempDesc" style="text-align: left;" hidden="hidden">
	       <table >
	         <tr>
	            <td style="width:150px"><label>模板执行间隔时间：</label></td>
	            <td><span id="interveldesc"></span>分</td>
	         </tr>
	       </table>
	       </div>
	       <div style="text-align: left;">
	       		<button type="button" class="reflush-btn" style="width:80px;" title="点我可刷新任务列表，获取任务执行的最新状态">列表刷新</button>
	       </div>
			<!-- <div class="caption">
               <button type="button" class="reflush-btn" title="点我可刷新任务列表，获取任务执行的最新状态">列表刷新</button>
               <button type="button" class="add-btn" title="点我可新建一个报表任务">新建任务</button>
            </div> -->
			<div id="data" class="detail-table" >
			</div>
			<div class="result" hidden="hidden">请耐心等待查询结果...</div>
			<div id="value_data" class="detail-table" hidden="hidden">
			</div>
			<div>
				<table style="table-layout:fixed;text-align:left">
					<thead>
						<tr>
							<th style="line-height:2em">自定义报表注意事项：</th>
						</tr>
					</thead>
					<tbody>
						<tr><td class="float-left" >1.如果任务正在执行，请耐心等待执行结果。</td></tr>
						<tr><td class="float-left" >2.如果任务执行完毕且成功，点击查看按钮直接查看数据。</td></tr>
						<tr><td class="float-left" >3.如果查询结果不是最新数据，点击刷新按钮，重新执行。</td></tr>
					</tbody>
				</table>
			</div>
		</div>
    </form:form>
	</div>
	
<div class="theme-popover-mask"></div>
	
	<div hidden="hidden" class="template_cache">
	<table class="ajax_table">
		<thead>
			<tr>
				<td style="min-width:20%">任务名称</td>
				<td style="min-width:20%">执行状态</td>
         		<td style="min-width:20%">执行时间</td>
         		<td style="min-width:20%">执行时长</td>
         		<td style="min-width:20%">操作</td>
			</tr>
		</thead>
     	<tbody>
		</tbody>
	</table>
	<table class="ajax_cache_table">
		<thead>
			<tr>
         		<td>ID</td>
         	</tr>
    	</thead>
		<tbody>
			<tr>
         		<td>2</td>
			</tr>
		</tbody>
	</table>
   <table class="addDataTable">
	  <tr class="model">
	     <th>选择模板：</th>
	     <td colspan="3" style="align:left;">
	         <select id="model" style="min-width:250px;">
	            <option value="-1">请选择...</option>
	         </select>
	     </td>
	  </tr>
	  <tr class="parm">
	     <th class="name"></th>
	     <td class="value"></td>
	  </tr>
   </table>
	</div>
	
</body>
</html>