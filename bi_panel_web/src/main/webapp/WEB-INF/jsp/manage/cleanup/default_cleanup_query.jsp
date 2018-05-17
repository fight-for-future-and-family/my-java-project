<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>ETL数据清理</title>
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
<script type="text/javascript" src="/manage/js/etl_engine_cleanup.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_auth.jsp" >
		  <jsp:param value="authli_etlengineCleanup" name="auth_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li><a href="/panel_manage/EtlengineCleanup/toEtlengineCleanupQuery.ac">ETL数据清理</a></li>
				<li>></li>
				<li><a  class="active green">清除ETL中间结果集</a></li>
			    </ol>
			    <ul class="detail-nav">
			     	<li id="metric" class="de-first " ><a href="/panel_manage/EtlengineCleanup/toEtlengineCleanupQuery.ac">ETL中间结果集</a></li>
			        <li id="concats" class=""><a href="/panel_manage/EtlengineCleanup/toEtlengineCleanupQuery.ac?type=facts">FACTS结果集</a></li>
			        <li id="concats" class="active"><a href="#">原始数据</a></li>
			        <li id="concats" class=""><a href="/panel_manage/EtlengineCleanup/toEtlengineCleanupQuery.ac?type=report">report数据</a></li>
			    </ul>
			    <div class="person-data">
			      <form:form id="submitForm" name="submitForm" method="post" modelAttribute="etlEngineCleanupVO" action="/panel_manage/EtlengineCleanup/toEtlengineCleanupQuery.ac" enctype="multipart/form-data">
					<p class="warn-btn">
					<span class="pt10 ml30" style="display:inline-block;"><span>选择游戏、时间</span>
					</span>
					<button id="add" class="btn btn-sm warn-set" style="margin-left:400px;">执行</button>
				    </p>
					<input type="hidden" name="type" value="default"/>
					<ul class="form_fields mt20">
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
						<p>
						<font color="red" id="submitTips">
						</font></p>
						</li>
						
						<li class="clear-fix" id="resultMsg">
						
						

	
							<c:if test="${!empty gameProcessMessMap}">
							
									<c:forEach var="map" items="${gameProcessMessMap}" varStatus="st1">
										<div>
										<p>
										<font color="green">
										<c:choose>
											<c:when test="${map.key==0}">
												<c:forEach var="cValue" items="${map.value}" varStatus="st2">
													&nbsp; &nbsp; <c:out value="${cValue.gameName}" />&nbsp; &nbsp; <c:out value="${cValue.ds}" /> 执行成功！
												</c:forEach>
											</c:when>
										</c:choose>
										</font>
									</p>
								</div>

									</c:forEach>
									
									
									<c:forEach var="map" items="${gameProcessMessMap}" varStatus="st1">
										<div>
										<p>
										<font color="red">
										<c:choose>
											<c:when test="${map.key==1}">
												<c:forEach var="cValue" items="${map.value}" varStatus="st2">
													<c:out value="${cValue.gameName}" />&nbsp; &nbsp; <c:out value="${cValue.ds}" /> 执行失败！</br>
													&nbsp; &nbsp; <c:out value="${cValue.errMessage}" /></br>
												</c:forEach>
											</c:when>
										</c:choose>
										</font>
									</p>
								</div>

									</c:forEach>
									
									<c:forEach var="map" items="${gameProcessMessMap}" varStatus="st1">
										<div>
										<p>
										<font color="red">
										<c:choose>
											<c:when test="${map.key==2}">
												清理数据时间段不能大于7天，请重新选择！
											</c:when>
										</c:choose>
										</font>
									</p>
								</div>

									</c:forEach>
									
									
									
								</c:if> 
								
								
						</li>
					</ul>
				    
			    	</form:form>	
			    </div>
			</div>
		</div>
	</div>
</body>
</html>