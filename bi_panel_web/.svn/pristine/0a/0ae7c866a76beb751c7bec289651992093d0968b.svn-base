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
<title>${game.name }-实时分渠道注收比</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
  <link rel="stylesheet" href="/css/wap/wap.css?v=${version}"/>
  <link rel="stylesheet" href="/css/wap/realTime.css?v=${version}"/>
  <link rel="stylesheet" href="/css/wap/realtimeHour.css?v=${version}"/>
  <script type="text/javascript" src="/wap/js/dataTables.js?v=${version}"></script>
  <script type="text/javascript" src="/wap/js/hourLtvReport.js?v=${version}"></script>
  <style type="text/css">
  .ajax_loading{
    background:#fff;
    height:60px;
    width:100%;
    text-align:center;
    line-height:60px;
    font-size:16px;
    display:none;
    bottom:0px;
    min-height:500px;
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
        margin-left: 48%;
	}
  </style>
</head>

<body>
<div class="lp-container">
<jsp:include page="/WEB-INF/jsp/wap/common/header.jsp" />
		<div class="data-container-main">
			<div class="detail">
			    <ul class="detail-nav">
			       <li class="de-first active" id="realTime"><a></a>实时</li>
			       <li id="daily" ><a ></a>基本运营数据</li>
		       		<c:if test="${game != null && game.terminalType == 1}">
			       		<li id="equipDau"><a></a>设备数据</li>
			       </c:if>
		       		<li id="taskList" ><a ></a>自定义报表</li>
		       		
			    </ul>
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/hourReportSourceLtv/downloadData.ac" enctype="multipart/form-data">
			     <input name="gamesId" type="hidden" value="${game.id }" />
			      <%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input name="snid" type="hidden" value="${snid }" />
				  <input name="gameId" type="hidden" value="${gameId }" />
			      <input id="view" name="view" type="hidden" value="hourReport" />
			      <input id="gameRate" name="gameRate" type="hidden" value="${game.rate }" />
			      <input id="gameCurrency" type="hidden" value="${game.currency }" />
			      <input id="timeZone" name="timeZone" type="hidden" value="${game.timeZone }" />
			    
			   <div class="area-sub">
					<div id="layout-t" class="tab-product tab-sub-3 ui-style-gradient">
						<h2 class="tab-hd">
							<span class="tab-hd-con" view="realTime"><a></a>按分钟</span> 
							<span class="tab-hd-con" view="forecastHourReport"><a></a>按小时</span>
							<c:if test="${isDisplayHourReport or isAdmin or isLeader}">
								<span class="tab-hd-con" view="hourReport"><a></a>实时日报</span>
								<span class="tab-hd-con current" view="hourReportSourceLtv"><a></a>分渠道注收比</span>
								<span class="tab-hd-con" view="hourReportSourceRetention"><a href="#"></a>分渠道留存</span>
							</c:if>
						</h2>
						<div class="tab-bd dom-display">
							<div class="tab-bd-con"></div>
							<div class="tab-bd-con current"> 
			   <div class="detail-info" style="margin-top:10px;">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    		  <span style="padding-left:5px;margin-top: 5px">
			    		      <select id="channel" name="channel" style="width:100px;">
			    		         <option value="all" selected="selected">总览</option>
			    		         <option value="source">分渠道</option>
			    		      </select>
			    			</span>
			    			<span id="s_c_span" style="padding-left:5px;"></span>
			    		</li>
			    		<li class="detail-li">
			    			<span class="detail-left" style="width:60%">
			    			<span style="font-size:12px;font-weight:bold;">计算时间：</span>
			    			 <span id="date_rt" style="width:88px;font-size:12px;font-weight:bold;color:red;">
			    			 </span>
			    			</span>
			    			<span class="detail-right" style="width:5%;">
			    				<button type="button" id="redone" class="btn btn-default btn-sm" style="width:50px;margin-top: -20px;">重算</button>
			    			</span>
			    			<span class="detail-right">
			    				<button type="button" id="query" class="btn btn-default btn-sm" style="width:50px;margin-top: -20px;">刷新</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
			 <div id="data" class="detail-table" style="margin-top: 20px"></div>
			 <div id="ajax_data" class="detail-table" style="margin-top: 20px" hidden="hidden"> </div>
			 <div class='ajax_loading' hidden="hidden" style='height:60px;width:100%;text-align:center;line-height:60px;font-size:16px;'>
				<!-- <span class="ajax_loading_span">数据实时计算中,请等待大约<span style="padding: 0px 5px;color: #F13D6D">5</span>分钟</span> -->
				 <span class="ajax_loading_span">数据实时计算中,请耐心等待......</span> 
				<br><img class="ajax_loading_img" src="/images/loading.gif"></img>
			</div>
			
			</div>
						</div>
					</div>
				</div>
			
			 </form:form>
</div>
</div>


<div hidden="hidden" class="template_cache">
			<table class="all_daily">
				<thead>
					<tr>
						<td>日期</td>
						<!-- <td>时间</td> -->
						<td>新注册</td>
						<td>d0</td>
						<td>d1</td>
						<td>d2</td>
						<td>d3</td>
						<td>d4</td>
						<td>d5</td>
						<td>d6</td>
						<td>d7</td>
					</tr>
				</thead>
				<tbody>
                    <tr>
						<td class="day"></td>
						<!-- <td class="hour"></td> -->
						<td class="reg"></td>
						<td class="d0"></td>
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
						<!-- <td>时间</td> -->
						<td>渠道</td>
						<td>新注册</td>
						<td>d0</td>
						<td>d1</td>
						<td>d2</td>
						<td>d3</td>
						<td>d4</td>
						<td>d5</td>
						<td>d6</td>
						<td>d7</td>
					</tr>
				</thead>
				<tbody>
                    <tr>
						<td class="day"></td>
						<!-- <td class="hour"></td> -->
						<td class="source"></td>
						<td class="reg"></td>
						<td class="d0"></td>
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
                <button type="button" class="btn btn-default btn-sm" style="width:100px;">下载数据</button>
              </caption>
				<thead>
					<tr>
						<td>日期</td>
						<!-- <td>时间</td> -->
						<td>渠道</td>
						<td>新注册</td>
						<td>d0</td>
						<td>d1</td>
						<td>d2</td>
						<td>d3</td>
						<td>d4</td>
						<td>d5</td>
						<td>d6</td>
						<td>d7</td>
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