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
<script type="text/javascript" src="/manage/js/addGame.js?v=${version}"></script>
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
		    	<li><a>${game.name }</a></li>
				<li>></li>
		    	<li><a href="/panel_bi/alarm_user/toList.ac?gameId=${gameId}&snid=${snid}">报警监控</a></li>
				<li>></li>
		    	<li class="active green">添加联系人</li>
			    </ol>
			    <div class="person-data">
			      <form:form id="submitForm" name="submitForm" method="post" modelAttribute="gameAlarmUserVO" action="/panel_bi/alarm_user/add.ac?gameId=${gameId}&snid=${snid}" enctype="multipart/form-data">
					<p class="warn-btn">
					<span class="pt10 ml30" style="display:inline-block;"><span>选择报警联系人</span>
					</span>
					 <form:button type="submit" class="btn btn-sm warn-set"  style="margin-left:400px">保存</form:button>
					 <form:hidden path="gameId"/>
				    </p>
					<ul class="p-ul mt20">
						<li style="width:100%">
							<div class="concat_con">
								<form:checkboxes items="${userGamesVOList}" path="userIds" itemLabel="username" itemValue="entity.userId"/>
							</div>
						</li>
					</ul>
					</form:form>	
			    </div>
			</div>
		</div>
	</div>
</body>
</html>