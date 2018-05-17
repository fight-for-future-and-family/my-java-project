<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>账号管理-子账户</title>
<meta name="viewport" content="width=90%">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/laydate.css">
<link rel="stylesheet" href="/css/skin_laydate.css">
<link rel="stylesheet" href="/css/page.css">
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/laydate.dev.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
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
			     <form:form method="post" modelAttribute="monitorMetricVO" action="/panel_bi/monitor_metric/toAdd.ac?gameId=${gameId}&snid=${snid}">
			        <form:button type="submit" class="btn btn-default btn-sm"  style="width:100px;">添加</form:button>
			      </form:form>
			    </div>
			   <form:form id="submitForm" name="submitForm" method="post" modelAttribute="monitorMetricVO" action="/panel_bi/monitor_metric/toList.ac?gameId=${gameId}&snid=${snid}" enctype="multipart/form-data">
				    <div class="detail-table">
				    	<input name="snid" type="hidden" value="${snid }" />
						<input name="gameId" type="hidden" value="${gameId }" />
				    	<table class="table table-striped">
				    		<thead>
				    			<tr>
				    				<td>NO</td>
				    				<td>指标名称</td>
				    				<td>采集数据频率</td>
				    				<td>统计方法</td>
				    				<td>操作</td>
				    			</tr>
				    		</thead>
				    		<tbody>
				    		   <c:forEach items="${monitorMetricList }" var="de" varStatus="vs">
				    		      <tr>
				    		         <td>${vs.count }</td>
				    				 <td>
				    				 	<c:choose>
				    				 		<c:when test="${de.name eq 'payment'}">
				    				 			付费系统
				    				 		</c:when>
				    				 		<c:otherwise>
				    				 			其他
				    				 		</c:otherwise>
				    				 	</c:choose>
				    				 </td>
				    				 <td>${de.intervalVal/(1000*60)} 分钟</td>
				    				 <td>
				    				 	<c:choose>
				    				 		<c:when test="${de.statisticsMethod eq 'avg'}">
				    				 			平均值
				    				 		</c:when>
				    				 		<c:otherwise>
				    				 			其他
				    				 		</c:otherwise>
				    				 	</c:choose>
				    				 </td>
				    				 <td>
				    				    <a href="/panel_bi/monitor_metric/del.ac?entity.id=${de.id}&gameId=${gameId}&snid=${snid}">删除</a>
				    				 </td>
				    		      </tr>
				    		   </c:forEach>
				    		</tbody>
				    	</table>
				    </div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>