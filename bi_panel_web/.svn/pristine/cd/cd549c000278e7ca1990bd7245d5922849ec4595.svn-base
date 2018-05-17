<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>ETL引擎-查询游戏ETL计算情况</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<style type="text/css">
.p-input{
 width: 200px;
 height: 25px;
 border: none;
 border: 1px solid #ebebeb;
}
.form_fields {
	height: 260px;
	width:100%;
}
.form_fields li{
	width:100%;
	float:left;
	text-align:left;
	margin:0px 0px 10px 0px;
}
.form_fields li span{
	float:left;
	width:160px;
	overflow:hidden;
}
.form_fields li span input{
	width:13px;
	height:21px;
	margin:0px 2px 0px 0px;
	overflow:hidden;
}
.form_fields li span label{
	width:135px;
	overflow:hidden;
	margin:0px;
	white-space:nowrap;
	
}
label{
	font-weight:normal;
}
.errorMsg{
  color: red;
  display: none;
}
</style>
<script type="text/javascript" src="/js/date.js"></script>
<script type="text/javascript" src="/manage/js/etl_engine_trigger.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_auth.jsp" >
		  <jsp:param value="authli_etlengine_trigger" name="auth_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li><a href="/panel_manage/etlengine_trigger_query.ac">ETL引擎</a></li>
				<li>></li>
		    	<li class="active green">查询游戏ETL计算情况</li>
			    </ol>
			    <ul class="detail-nav">
			     	<li id="metric" class="de-first active" ><a href="">查询</a></li>
			        <li id="concats" class=""><a href="/panel_manage/etlengine_trigger.ac">执行</a></li>
			    </ul>
			    <div class="person-data">
			      <form:form id="submitForm" name="submitForm" method="post" modelAttribute="etlEngineTriggerVO" action="/panel_manage/etlengine_trigger_query.ac" enctype="multipart/form-data">
					<p class="warn-btn">
					<span class="pt10 ml30" style="display:inline-block;"><span>选择游戏、时间</span>
					</span>
					<button id="add" class="btn btn-sm warn-set" style="margin-left:400px;">查询</button>
				    </p>
					
					<ul class="form_fields mt20">
						<li class="clear-fix">
							<div>
								<input type="checkbox" id="checkAll" />
								<label for="checkAll">全选/全不选</label>
							</div>
						</li>
						<li class="clear-fix">
							<div>
								<form:checkboxes items="${games}" path="gameids" itemLabel="name" itemValue="id"/>
							</div>
						</li>
						<li class="clear-fix">
							<div>
								<label for="beginDate">开始时间</label>
								<form:input path="beginDate" cssClass="b-radius5 p-input" required="required" id="beginDate"/>
							</div>
						</li>
						<li class="clear-fix">
							<div>
								<label for="endDate">结束时间</label>
								<form:input path="endDate" cssClass="b-radius5 p-input" required="required" id="endDate"/>
							</div>
						</li>
						
						<li class="clear-fix">
							<c:forEach items="${gameProcessMessList}" var="mess">
								<c:set var="sn_game_id" value="${mess.snid}_${mess.gameid}"></c:set>
								<c:set var="processGame" value="${biGamesMap[sn_game_id]}"></c:set>
								<div sn_game_id="${sn_game_id}">
									<p>
										${processGame.name} &nbsp;
										<font color="red">
											<c:if test="${mess.taskStatus!=null}">
												<c:choose>
													<c:when test="${mess.taskStatus==0}">
														正在执行
													</c:when>
													<c:when test="${mess.taskStatus==1}">
														已经结束
													</c:when>
													<c:otherwise>
														执行错误
													</c:otherwise>
												</c:choose>
											</c:if>
										</font>
										${mess.ds }
										&nbsp;
										${mess.msg}
										&nbsp;
										已经运行<font color="red">${mess.spendMinuts}</font>分钟
									</p>
								</div>
							</c:forEach>
						</li>
					</ul>
				    
			    	</form:form>	
			    </div>
			</div>
		</div>
	</div>
</body>
</html>