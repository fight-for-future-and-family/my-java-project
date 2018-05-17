<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>游戏流水查询</title>
<meta name="viewport" content="width=90%">
<link rel="stylesheet" href="/css/laydate.css">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/skin_laydate.css">
<link rel="stylesheet" href="/css/page.css">
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/laydate.dev.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<script type="text/javascript" src="/js/gamesDailyQuery.js?v=${version}"></script>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
</head>
<body class="dt-example" style="background-color: white;overflow-x: hidden;">
<jsp:include page="/WEB-INF/jsp/common/header_wanda.jsp" />
<div >
<jsp:include page="/WEB-INF/jsp/common/left_auth.jsp" >
	<jsp:param value="gameli_monitor_metric" name="game_page"/>
</jsp:include>
<div class="data-container">
	<div class="detail">
		<ol class="lp-breadcrumb">
		<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
		<li>></li>
    	<li>游戏流水查询</li>
	    </ol>
		<div class="detail-info" style="overflow: hidden;margin-top:10px;min-height:70px;">
			<span style="width:30%;float: left;padding-left:5px;padding-top:10px;">
			    开始日期：<input id="beginTime" name="beginTime" class="b-radius5 p-input" style="margin-left:5px;width:180px"/>
			  <input id="minTime" type="hidden"/>
			</span>
			<span style="width:30%;float: left;padding-left:5px;padding-top:10px;">
			    结束日期：<input id="endTime" name="endTime" class="b-radius5 p-input" style="margin-left:5px;width:180px"/>
			</span>
			<input id="day" type="text" hidden="hidden" value="" />
			<span class="detail-right" style="width:25%;margin-right: 0px">
				<button id="query" type="button" class="btn btn-default btn-sm" style="width:80px;padding-top: 3px;margin-right: 5px;">查询</button>
				<button id="download" type="button" class="btn btn-default btn-sm" style="width:80px;padding-top: 3px;margin-right: 5px;">下载数据</button>
			</span>
		</div>
		<div style="margin: 20px 10px 0px 10px;">
		<span id="ds"></span>
		</div>
		<div id="data" class="detail-table">
		</div>
		<form:form id="downForm" method="post" enctype="multipart/form-data">
		</form:form>
		<div class='ajax_loading' hidden="hidden" style='height:60px;width:100%;text-align:center;line-height:60px;font-size:16px;'>
			<span class="ajax_loading_span">数据加载中，请稍候</span>
			<br><img class="ajax_loading_img" src="/images/loading.gif"></img>
		</div>
		<div hidden="hidden" class="template_cache">
			<table class="dataTable" >
				<thead>
					<tr class="size">
						<td>序号</td>
						<td>游戏名称</td>
						<td>上线时间</td>
						<td>渠道</td>
						<td>流水(元)</td>
						<td>暂估分成比例(%)</td>
						<td>互爱互动收入(元)</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="id"></td>
						<td class="seriesName"></td>
						<td class="onlineDate"></td>
						<td class="creative"></td>
						<td class="paymentAmount"></td>
						<td class="rate"></td>
						<td class="hulaiAmount"></td>
					</tr>
				</tbody>
				<tfoot></tfoot>
			</table>
		</div>
	</div>
</div>
</div>
</body>
</html>