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
				<li><a href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
		    	<li>${game.name }</li>
				<li>></li>
		    	<li class="active green">报警监控</li>
			    </ol>
			     <ul class="detail-nav">
			     	<li id="metric" class="de-first" ><a href="/panel_bi/monitor_metric/toList.ac?gameId=${gameId}&snid=${snid}">指标</a></li>
			        <li id="concats" class="active"><a href="#">联系人</a></li>
			    </ul>
			    <div class="msgcss">${msg }</div>
			    <div class="op_bar">
			     <form:form method="post" modelAttribute="gameAlarmUserVO" action="/panel_bi/alarm_user/toAdd.ac?gameId=${gameId}&snid=${snid}">
			        <form:button type="submit" class="btn btn-default btn-sm"  style="width:100px;">添加</form:button>
			      </form:form>
			    </div>
			   <form:form id="submitForm" name="submitForm" method="post" modelAttribute="gameAlarmUserVO" action="/panel_bi/alarm_user/toList.ac?gameId=${gameId}&snid=${snid}" enctype="multipart/form-data">
			    <input name="snid" type="hidden" value="${snid }" />
					<input name="gameId" type="hidden" value="${gameId }" />
				    <div class="detail-table">
				    	<table class="table table-striped">
				    		<thead>
				    			<tr>
				    				<td>NO</td>
				    				<td>联系名称</td>
				    				<td>邮箱</td>
				    				<td>电话</td>
				    				<td>操作</td>
				    			</tr>
				    		</thead>
				    		<tbody>
				    		   <c:forEach items="${gameAlarmUserVOList }" var="de" varStatus="vs">
				    		      <tr>
				    		         <td>${vs.count }</td>
				    				 <td>${de.username }</td>
				    				 <td>${de.email }</td>
				    				 <td>${de.phone }</td>
				    				 <td>
				    				    <a href="/panel_bi/alarm_user/del.ac?entity.id=${de.entity.id}&gameId=${gameId}&snid=${snid}">删除</a>
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