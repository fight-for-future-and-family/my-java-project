<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>账号管理-子账户</title>
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/laydate.css">
<link rel="stylesheet" href="/css/skin_laydate.css">
<link rel="stylesheet" href="/css/page.css">
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/laydate.dev.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<script type="text/javascript" src="/js/games/monitormetric/monitorList.js?v=${version}"></script>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		  <jsp:param value="gameli_monitor_metric" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
		    	<li>${game.name }</li>
				<li>></li>
		    	<li>报警监控</li>
			    </ol>
			     <ul class="detail-nav">
			     	<li id="metric" class="de-first active" ><a href="#">指标</a></li>
			        <li id="concats" class=""><a href="/panel_bi/alarm_user/toList.ac?gameId=${gameId}&snid=${snid}">联系人</a></li>
			    </ul>
			    <div class="msgcss">${msg }</div>
			    <div class="op_bar">
			     <form:form method="post" modelAttribute="monitorMetrics" action="/panel_bi/monitor_metric/toAdd.ac?gameId=${gameId}&snid=${snid}">
			        <form:button type="submit" class="btn btn-default btn-sm"  style="width:100px;">添加</form:button>
			      </form:form>
			    </div>
			   <form:form id="submitForm" name="submitForm" method="post" modelAttribute="monitorMetrics" action="/panel_bi/monitor_metric/toList.ac?gameId=${gameId}&snid=${snid}" enctype="multipart/form-data">
				    <div>
				    	<input name="snid" type="hidden" value="${snid }" />
						<input name="gameId" type="hidden" value="${gameId }" />
				    	<div id="data" class="detail-table">
				    		
				    	</div>
				    </div>
				</form:form>
			</div>
		</div>
	</div>
	
<div hidden="hidden" class="template_cache">
	<table class="dataTable_query" >
		<thead>
   			<tr>
   				<td>ID</td>
   				<td>指标名称</td>
   				<td>减少(%)</td>
   				<td>增加(%)</td>
   				<td>是否邮件</td>
   				<td>是否短信</td>
   				<td>操作</td>
   			</tr>
   		</thead>
		<tbody>
			<tr>
				<td class="id"></td>
				<td class="columnName"></td>
				<td class="lowerLimit"></td>
				<td class="topLimit"></td>
				<td class="isEmail"></td>
				<td class="isPhone"></td>
				<td class="eidt"></td>
			</tr>
		</tbody>
	</table>
	<table class="dataTable_edit" style="width: 100%;">
		<thead>
   			<tr>
   				<td>ID</td>
   				<td>指标名称</td>
   				<td>减少(%)</td>
   				<td>增加(%)</td>
   				<td>是否邮件</td>
   				<td>是否短信</td>
   			</tr>
   		</thead>
		<tbody style="border-top: 1px solid #bdaeae">
			<tr>
				<td><span id="id" ></span></td>
				<td><span id="columnName" ></span></td>
	    		<td><input id="lowerLimit" type="text" style="border: 1px solid #c3b6b6;text-align: right;" class="b-radius5 p-input" /></td>
	    		<td><input id="topLimit" type="text" class="b-radius5 p-input" style="text-align: right;border: 1px solid #c3b6b6" /></td>
	    		<!-- <td><input id="isEmail" type="text" style="border: 1px solid #c3b6b6;text-align: right;" class="b-radius5 p-input" /></td>
	    		<td><input id="isPhone" type="text" class="b-radius5 p-input" style="text-align: right;border: 1px solid #c3b6b6" /></td> -->
	    		<td> <input id="isEmail" type="checkbox" /> </td>
	    		<td> <input id="isPhone" type="checkbox" /> </td>
	    		<td>
	    			<input id="save" type="button" class="btn btn-default btn-sm" style="width:100px;border: 1px solid #c3b6b6" value="保存" />
	    			<input id="cancel" type="button" class="btn btn-default btn-sm" style="width:100px;border: 1px solid #c3b6b6" value="取消" />
	    		</td>
			</tr>
		</tbody>
	</table>
</div>
</body>
</html>