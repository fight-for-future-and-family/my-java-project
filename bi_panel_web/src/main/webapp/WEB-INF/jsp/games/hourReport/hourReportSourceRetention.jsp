<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-实时</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/games/hourReportSourceRetention.js?v=${version}"></script>
<style type="text/css">
 .data_table {
	width: 100%;
	margin-top: 20px;
	overflow:hidden;
}

.zhibiao {
	width: 12.5%;
	height: 100px;
	float: left;
	margin-top: 20px;
	border-left: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-radius: 10px;
}

.zhibiao_left {
	width: 12.5%;
	height: 100px;
	float: left;
	margin-top: 20px;
	border-left: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-radius: 10px;
}

.zhibiao_right {
	width: 12.5%;
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

.highlight {
    background-color: #99f21b !important;
}

.detail-table td{
	min-width: 150px; 
}

.dataTables_wrapper .dataTables_filter {
	  margin-left:10px;
      z-index:1;
      position:absolute;
     }
     caption {
        font-style: normal;
        text-align: left;
        margin-left: 38%;
	}
  </style>
</head>

<body class="dt-example">
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<div class="lp-container">
        <jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_realTime" name="game_page"/>
		</jsp:include>
	<div class="data-container" style="min-height:800px;">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name}</li>
				<li>></li>
		    	<li>实时</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			      
			    <ul class="detail-nav">
			       <li id="realTime"><a href="#"></a>按分钟</li>
			       <li id="forecastHourReport"><a href="#"></a>按小时</li>
			       <li id="hourReport"><a href="#"></a>实时日报</li>
			        <li id="hourReportSourceLtv"><a href="#"></a>分渠道注收比</li>
			        <li class="active"  id="hourReportSourceRetention"><a href="#"></a>分渠道留存</li>
			    </ul>
			     <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/hourReportSourceRetention/downloadData.ac" enctype="multipart/form-data">
			     <input name="gamesId" type="hidden" value="${game.id }" />
			<%--       <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			       <input name="snid" type="hidden" value="${snid }" />
			      <input name="gameId" type="hidden" value="${gameId }" /> 
			      <input id="view" name="view" type="hidden" value="hourReport" />
			      <input id="gameRate" name="gameRate" type="hidden" value="${game.rate }" />
			      <input id="gameCurrency" type="hidden" value="${game.currency }" />
			      <input id="timeZone" name="timeZone" type="hidden" value="${game.timeZone }" />
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    		  <span style="padding-left:20px;">
			    		      <select id="channel" name="channel" style="width:100px;">
			    		         <option value="all" selected="selected">总览</option>
			    		         <option value="source">分渠道</option>
			    		      </select>
			    			</span>
			    			<span id="s_c_span" style="padding-left:20px;"></span>
			    			<span class="detail-left">
			    			<span style="font-size:12px;font-weight:bold;">计算时间：</span>
			    			 <span id="date_rt" style="width:88px;font-size:12px;font-weight:bold;color:red;">
			    			 </span>
			    			</span>
			    			<span class="detail-right" style="padding-top: 6px;">
			    				<button type="button" id="redone" class="btn btn-default btn-sm" style="width:100px;">重算</button>
			    			</span>
			    			<span class="detail-right" style="padding-top: 6px;">
			    				<button type="button" id="query" class="btn btn-default btn-sm" style="width:100px;">刷新</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
			   
					   <div id="data" class="detail-table" style="margin-top: 20px"> </div>
					   <div id="ajax_data" class="detail-table" style="position: relative;margin-top: 20px" hidden="hidden"> </div>
					   <div class='ajax_loading' hidden="hidden" style='height:60px;width:100%;text-align:center;line-height:60px;font-size:16px;display:none;'>
					   <!--  <span class="ajax_loading_span">数据实时计算中,请等待大约<span style="padding: 0px 5px;color: #F13D6D">2</span>分钟</span> -->
					    <span class="ajax_loading_span">数据实时计算中,请耐心等待......</span> 
					    <img class="ajax_loading_img" src="/images/loading.gif"></img>
					    </div>
					    </form:form>
</div>
</div>
<div hidden="hidden" class="template_cache">
			<table class="all_daily">
				<thead>
					<tr>
						<td>日期</td>
					<!-- 	<td>时间</td> -->
						<td>新注册</td>
						<td>D1</td>
						<td>D2</td>
						<td>D3</td>
						<td>D4</td>
						<td>D5</td>
						<td>D6</td>
						<td>D7</td>
					</tr>
				</thead>
				<tbody>
                    <tr>
						<td class="day"></td>
						<!-- <td class="hour"></td> -->
						<td class="reg"></td>
						<td class="d1"></td>
						<td class="d2"></td>
						<td class="d3"></td>
						<td class="d4"></td>
						<td class="d5"></td>
						<td class="d6"></td>
						<td class="d7"></td>
					</tr>
				</tbody>
			</table>
			<table class="source_daily">
				<thead>
					<tr>
						<td>日期</td>
					<!-- 	<td>时间</td> -->
						<td>渠道</td>
						<td>新注册</td>
						<td>D1</td>
						<td>D2</td>
						<td>D3</td>
						<td>D4</td>
						<td>D5</td>
						<td>D6</td>
						<td>D7</td>
					</tr>
				</thead>
				<tbody>
                    <tr>
						<td class="day"></td>
					<!-- 	<td class="hour"></td> -->
						<td class="source"></td>
						<td class="reg"></td>
						<td class="d1"></td>
						<td class="d2"></td>
						<td class="d3"></td>
						<td class="d4"></td>
						<td class="d5"></td>
						<td class="d6"></td>
						<td class="d7"></td>
					</tr>
				</tbody>
			</table>
			<table class="source_ajax_daily">
			<caption>
               <!--  <button type="button" class="btn btn-default btn-sm" style="width:100px;">下载数据</button> -->
              </caption>
				<thead>
					<tr>
						<td>日期</td>
						<!-- <td>时间</td> -->
						<td>渠道</td>
						<td>新注册</td>
						<td>D1</td>
						<td>D2</td>
						<td>D3</td>
						<td>D4</td>
						<td>D5</td>
						<td>D6</td>
						<td>D7</td>
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