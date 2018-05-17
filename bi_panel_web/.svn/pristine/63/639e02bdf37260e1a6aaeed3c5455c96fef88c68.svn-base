<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>游戏管理-添加报表模板</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<style type="text/css">
.p-input{
 width: 200px;
 height: 25px;
 border: none;
 border: 1px solid #ebebeb;
}

.p-ul {
height: 450px;
width:100%
}

.p-ul li{
 padding-bottom: 20px;
 width:100%;
 overflow: hidden;
}

.p-ul span{
 width:32%;
 text-align: left;
 float: left;
}

.errorMsg{
  color: red;
  display: none;
}

.notic{
  color: #999;
  font-size: 12px;
}
</style>
<script type="text/javascript" src="/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="/js/timezone.js"></script>
<script type="text/javascript" src="/manage/js/customModel/customReportModel.js?v=${version}"></script>

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_auth.jsp" >
		  <jsp:param value="authli_cus" name="auth_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a href="/panel_manage/toMain.ac">主页</a></li>
    			<li>></li>
    			<li><a href="/panel_manage/customReportModel/toCustomReportModel.ac">自定义报表</a></li>
				<li>></li>
		    	<li class="active green">添加报表模板</li>
		    	
			    </ol>
			    <div class="person-data">
			      <form:form id="submitForm" name="submitForm" method="post" modelAttribute="customReportModelVO" action="/panel_manage/customReportModel/addCustomReportModel.ac" enctype="multipart/form-data">
					<input id="columns" name="columns" hidden="hidden">
					<input id="params" name="params" hidden="hidden">
					<form:input path="isSqlChange" value="0" hidden="hidden" />
					<p class="warn-btn">
						<span class="pt10 ml30" style="display:inline-block;">模板基本信息  
							<c:if test="${msg!=null && msg!='' }">
								<span style="color: #49C37C;font-size: 12px;font-weight: bold;">(${msg })</span>
							</c:if>
						</span>
						<button id="add" type="button" class="btn btn-sm warn-set" style="margin-left:400px;">保存</button>
				    </p>
					
					<ul id="baseUl" class="p-ul mt20">
						<li>
		         			<span>
		         			<label for="modelType">模板类型</label>
		             		<select id="modelType" name="modelType" style="width:200px;">
		               		<option value="-1">请选择...</option>
		               		<option value="0" <c:if test="${modelType == 0}">selected="selected"</c:if>>标准化模板</option>
		               		<option value="1" <c:if test="${modelType == 1}">selected="selected"</c:if>>个性化模板</option>
		               		</select>
		          			</span>
		          			
		         			 <span id="gameIdSpan" <c:if test="${modelType != 1}">hidden="hidden"</c:if>>
		          				 <label for="gameId">游戏</label>
		             	    	 <select id="gameId" name="gameId" style="width:200px;" >
		              	 			 <option value="-1">请选择...</option>
		                			 <c:forEach items="${gameList }" var="selGame">
		                	  			 <option <c:if test="${selGame.id == gameId}">selected="selected"</c:if> value="${selGame.id }">${selGame.name }</option>
		                			 </c:forEach>
		              			 </select>
		        		 	 </span>
						      <span>
							    <label for="entity.status">模板状态</label>
								<form:select path="entity.status" style="width:80px;">
							       <form:option value="1">有效</form:option>
							       <form:option value="0">无效</form:option>
							  
							    </form:select>
								<font color="red">*</font>
						    </span>
						</li>
						<li>
							<span>
							    <label for="entity.name">模板名称</label>
								<form:input path="entity.name" 
								cssClass="b-radius5 p-input notic" required="required" value="对用户展示的中文名称（唯一）" />
								<font color="red">*</font>
						    </span>
						      <span>
							    <label for="entity.code">模板CODE</label>
								<form:input path="entity.code" 
								cssClass="b-radius5 p-input notic" required="required" value="程序使用的英文名称（唯一）" />
								<font color="red">*</font>
						      </span>
						      
						       <span>
							    <label for="entity.intervalTime">间隔时间</label>
							    <form:select path="entity.intervalTime" style="width:80px;">
							       <form:option value="5">5 分</form:option>
							       <form:option value="10">10 分</form:option>
							       <form:option value="15">15 分</form:option>
							      </form:select>
								  <font  color="red">*</font>
						     </span>
						</li>
						<li>
							<span>
							    <label for="entity.isPresto">运行方式</label>
								<form:select path="entity.isPresto" style="width:80px;">
							       <form:option value="0">hive</form:option>
							       <form:option value="1">presto</form:option>
							  
							    </form:select>
								<font color="red">*</font>
						    </span>
						</li>
					  	<li>
						      <span style="width:100%;">
							    <label for="entity.templateSql" style="float: left">模板语句</label>
							    <form:textarea id="templateSql" path="entity.templateSql" 
							    cssClass="b-radius5 p-input notic" style="height:150px;width:812px;float: left"
							    required="required" value="" />
								<font color="red" style="float: left">*</font>
						     </span>
						 </li>  
					  	<li>
						      <span style="width:100%;">
							    <label for="entity.description" style="float: left">模板描述</label>
							    <form:textarea id="description" path="entity.description" 
							    cssClass="b-radius5 p-input notic" style="height:150px;width:812px;float: left"
							    value="" />
						     </span>
						 </li>  
					</ul>
					
					<p class="warn-btn">
						<span class="pt10 ml30" style="display:inline-block;">模板参数信息
						(<span style="color: #49C37C;font-size: 12px;font-weight: bold;">参数为日期的，结构必须是：day,ds,或者以date结尾</span>)</span>
				    </p>
				    <ul id="paramUl" class="p-ul mt20" style="min-height:110px;height:0px;margin-top: 20px;">
				    	
				    </ul>
					<p class="warn-btn">
						<span class="pt10 ml30" style="display:inline-block;">模板返回字段信息</span>
				    </p>
				    <ul id="columnUl" class="p-ul mt20" style="height:600px;">
				    	
				    </ul>
			    	</form:form>	
			    </div>
			</div>
		</div>
		
		<div hidden="hidden" class="template">
			<ul class="templateUl">
				<li>
		    		<span>
					    <label for="entity.code" style="width:132px"></label>
					    <input name="entity.code" class="b-radius5 p-input" required="required" />
						<font  color="red">*</font>
				     </span>
				</li>
			</ul>
			<ul class="paramTemplateUl">
				<li>
		    		<span>
					    <label for="entity.code" style="width:132px"></label>
					    <input name="entity.code" class="b-radius5 p-input" required="required" style="width:130px"/>
					    <select code="code" style="width:60px;">
					       <option value="-1">排序</option>
					    </select>
						<font  color="red">*</font>
				     </span>
				</li>
			</ul>
		</div>
		
		
	</div>
	
	
</body>
</html>