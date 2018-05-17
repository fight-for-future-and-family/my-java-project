<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>游戏管理-添加游戏</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<style type="text/css">
.p-input{
 width: 200px;
 height: 25px;
 border: none;
 border: 1px solid #ebebeb;
}
.p-ul {
height: 260px;
width:100%
}
.errorMsg{
  color: red;
  display: none;
}
.concat_con{
	float:left;
}
.concat_con span{
	float:left;
}
.concat_con span label{
	margin-left:2px;
	text-align:left;
	font-weight: normal;
}
</style>
<script type="text/javascript" src="/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="/js/games/monitormetric/monitorAdd.js?v=${version}"></script>
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
		    	<li><a class="green" href="/panel_bi/monitor_metric/toList.ac?gameId=${gameId}&snid=${snid}">报警监控</a></li>
				<li>></li>
		    	<li>添加指标</li>
			    </ol>
			    <div class="person-data">
			      <form:form id="submitForm" name="submitForm" method="post" modelAttribute="monitorMetrics" action="/panel_bi/monitor_metric/addMonitorListData.ac?gameId=${gameId}&snid=${snid}" enctype="multipart/form-data">
					<p class="warn-btn">
					<span class="pt10 ml30" style="display:inline-block;"><span>填写指标信息</span>
					</span>
					<input name="snid" type="hidden" value="${snid }" />
					<input name="gameId" type="hidden" value="${gameId }" />
					 <input id="save" class="btn btn-sm warn-set"  style="margin-left:400px" value="保存" />
				    </p>
					<ul class="p-ul mt20">
						<li style="width:100%;text-align:left;" >
							
							<p><label for="columnName">指标名称</label>
							<form:select path="columnName" id="columnName">
								<form:option value="dnu">新增用户数</form:option>
								<form:option value="dau">活跃用户数</form:option>
								<form:option value="payer">付费用户数</form:option>
								<form:option value="totalAmount">付费金额</form:option>
							</form:select>
							<font color="red">*</font><br>
							
							<p><label for="lowerLimit">减少</label>
							<form:input path="lowerLimit" />%
							<font color="red"> &nbsp;*</font><br>
							
							<p><label for="topLimit">增加</label>
						   <form:input path="topLimit" />%
						   <font color="red"> &nbsp;*</font><br>
						   
						   <p><label >预警方式</label>
						   <form:checkbox path="isEmail" value="1" /> <span>是否发送邮件</span>
						   <form:checkbox path="isPhone" value="1" /> <span>是否发送短信</span>
						   
						   <!-- <p>说明：超过低危范围,预警方式为邮件通知;超过高危范围,预警方式为短信通知 -->
						</li>
					</ul>
			    	</form:form>	
			    </div>
			</div>
		</div>
	</div>
</body>
</html>