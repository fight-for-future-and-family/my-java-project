<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>游戏流水录入</title>
<link rel="stylesheet" href="/css/laydate.css">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/skin_laydate.css">
<link rel="stylesheet" href="/css/page.css">
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/laydate.dev.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<script type="text/javascript" src="/js/gamesCreativeRateSave.js?v=${version}"></script>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_auth.jsp" >
		  <jsp:param value="gameli_monitor_metric" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
		    	<li>游戏分成录入</li>
			    </ol>
				<div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li" style="height:45px;overflow: hidden;">
			    			<span style="width:30%;float: left;padding-left:5px;padding-top:10px;">
			    			    游戏名称：<input id="seriesName" class="b-radius5 p-input" style="margin-left:5px;width:180px"/>
			    			</span>
			    			    
		    				<button id="query" class="btn btn-default btn-sm" style="width:100px;" >查询</button>
		    				<button id="add" class="btn btn-default btn-sm" style="width:100px;" onclick="add()">新增</button>
		    				<input id="editType" type="text" hidden="hidden" value="" />
			    		</li>
			    	</ul>
			    </div>
			    <div id="data" class="detail-table">
			    </div>
			</div>
			</div>
		</div>
		
<div hidden="hidden" class="template_cache">
	<table class="dataTable_query" >
		<thead>
   			<tr>
   				<td>游戏名称</td>
   				<td>渠道</td>
   				<td>分成比例(%)</td>
   				<td>月份</td>
   				<td>操作</td>
   			</tr>
   		</thead>
		<tbody>
			<tr>
				<td class="seriesName"></td>
				<td class="creative"></td>
				<td class="rate"></td>
				<td class="ds"></td>
				<td class="eidt"></td>
			</tr>
		</tbody>
	</table>
	<table class="dataTable_edit" style="width: 100%;">
		<thead>
   			<tr>
   				<td>游戏名称</td>
   				<td>渠道</td>
   				<td>分成比例(%)</td>
   				<td>月份</td>
   			</tr>
   		</thead>
		<tbody>
			<tr>
				<td><input id="seriesNameQ" style="width: 150px;" type="text" class="b-radius5 p-input" /></td>
	    		<td><input id="creative" type="text" class="b-radius5 p-input" /></td>
	    		<td><input id="rate" type="text" class="b-radius5 p-input" /></td>
	    		<td><input id="ds" type="text" class="b-radius5 p-input" /></td>
	    		<td ><button id="addBu"  class="btn btn-default btn-sm" style="width:100px;" onclick="addAndEdit()" >保存</button></td>
			</tr>
		</tbody>
	</table>
</div>

</body>
</html>