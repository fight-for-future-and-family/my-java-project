<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>胡莱游戏报表系统</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/wanda.js?v=${version}"></script>
<style type="text/css">
	.kong{
	    width:100%;
		height:50px;
		float:left;
	}
	
	.game_con{
		margin:0 auto; 
		width:100%; 
		height:100%; 
		position: relative;
		text-align:center;
	}
	
	.game{
	    float:left;
		position:relative;
		width:165px;
		height:130px;
		margin-left:20px;
		margin-bottom:20px; 
		border: 2px solid #ebebeb;
		border-radius:15px;
		cursor:pointer;
	}
	
	.gameTypeCls{
	    float:left;
		position:relative;
		width:165px;
		height:130px;
		margin-left:20px;
		margin-bottom:15px; 
		border: 2px solid #ebebeb;
		border-radius:15px;
		cursor:pointer;
	}
	
	.name{
      height: 80px;
	  font-size: 14px;
	  display: block;
	  line-height: 20px;
	}
	
	.name i{
	    display: inline-block;
        height: 100%;
        vertical-align: middle;
	}
	.name span{
	display: inline-block;
	vertical-align: middle;
	}
	.name div{
	position: absolute;
	left: 0;
	width: 100%;
	height: 80px;
	padding: 20px 10px;
	}
	.install{
	  text-align: left;
	  padding-left: 12%;
	  font-size: 12px;
	  font-weight:bold;
	  padding-bottom: 5px;
	  color: rgba(51, 51, 51, 0.75);
	  display: block;
	}
	
	.payment{
	  text-align: left;
	  padding-left: 12%;
	  font-size: 12px;
	  font-weight:bold;
	  color: rgba(51, 51, 51, 0.75);
	  display: block;
	}
</style>
</head>

<body class="dt-example" style="background-color: white;overflow-x: hidden;">
<jsp:include page="/WEB-INF/jsp/common/header_wanda.jsp" />
<div >
<div class="detail-info" style="overflow: hidden;margin-top:10px;min-height:70px;">
	<span style="width:30%;float: left;padding-left:5px;padding-top:10px;">
	    开始日期：<input id="beginTime" name="beginTime" class="b-radius5 p-input" style="margin-left:5px;width:180px"/>
	  <input id="minTime" type="hidden"/>
	</span>
	<span style="width:30%;float: left;padding-left:5px;padding-top:10px;">
	    结束日期：<input id="endTime" name="endTime" class="b-radius5 p-input" style="margin-left:5px;width:180px"/>
	</span>
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
</body>
</html>