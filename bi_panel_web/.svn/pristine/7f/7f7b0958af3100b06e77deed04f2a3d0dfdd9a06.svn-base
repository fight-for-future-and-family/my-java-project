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
<script type="text/javascript" src="/manage/js/addGame.js?v=1.0.0"></script>
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
			      <form:form id="submitForm" name="submitForm" method="post" modelAttribute="monitorMetricVO" action="/panel_bi/monitor_metric/add.ac?gameId=${gameId}&snid=${snid}" enctype="multipart/form-data">
					<p class="warn-btn">
					<span class="pt10 ml30" style="display:inline-block;"><span>填写指标信息</span>
					</span>
					<input name="snid" type="hidden" value="${snid }" />
					<input name="gameId" type="hidden" value="${gameId }" />
					 <form:button type="submit" class="btn btn-sm warn-set"  style="margin-left:400px">保存</form:button>
				    </p>
					<ul class="p-ul mt20">
						<li style="width:100%;text-align:left;" >
							
							<p><label for="entity.name">指标名称</label>
							<form:select path="entity.name">
								<form:option value="payment">付费系统</form:option>
							</form:select>
							<font color="red">*</font><br><span id="for_entity_name" checkId="entity.name" class="errorMsg">指标名称不能为空</span></p>
							
							<p><label for="entity.intervalVal">采集数据频率</label>
							<form:radiobutton path="entity.intervalVal" value="900000"/>15分钟 &nbsp;
							<form:radiobutton path="entity.intervalVal" value="1800000"/>30分钟&nbsp;
							<form:radiobutton path="entity.intervalVal" value="3600000"/>1个小时
							<font color="red">*</font><br><span id="for_entity_intervalVal" checkId="entity.intervalVal" class="errorMsg">采集数据频率不能为空</span></p>
							
							<p><label for="entity.statisticsMethod">统计方法</label>
						   <form:select path="entity.statisticsMethod">
								<form:option value="avg">平均值</form:option>
							</form:select>
						   <font color="red">*</font><br><span id="for_entity_statisticsMethod" checkId="entity.statisticsMethod" class="errorMsg">统计方法不能为空</span></p>
						   
						</li>
					</ul>
			    	</form:form>	
			    </div>
			</div>
		</div>
	</div>
</body>
</html>