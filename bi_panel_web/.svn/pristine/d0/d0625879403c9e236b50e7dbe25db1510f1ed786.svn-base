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
		    	<li class="active green">编辑报表模板</li>
		    	
			    </ol>
			    <div class="person-data">
			      <form:form id="submitForm" name="submitForm" method="post" modelAttribute="customReportModelVO" action="/panel_manage/customReportModel/customReportModelEdit_${prePageNo }.ac" enctype="multipart/form-data">
					<p class="warn-btn">
					<span class="pt10 ml30" style="display:inline-block;">
					<span>模板基本信息
						<c:if test="${msg!=null && msg!='' }">
							<span style="color: #49C37C;font-size: 12px;font-weight: bold;">(${msg })</span>
						</c:if>
					</span>
					</span>
					<button id="add" type="button" class="btn btn-sm warn-set" style="margin-left:400px;">保存</button>
				    </p>
					<form:input path="entity.id" hidden="hidden" />
					<form:input path="isSqlChange" value="0" hidden="hidden" />
					<form:input path="isColumnChange" value="0" hidden="hidden" />
					<form:input path="isParamChange" value="0" hidden="hidden" />
					<input id="columns" name="columns" hidden="hidden">
					<input id="params" name="params" hidden="hidden">
					<ul id="baseUl" class="p-ul mt20">
					  <li>
						<span>
							 <label for="entity.type">模板类型:</label>
							 	<c:if test="${customReportModelVO.entity.type == 0 }">标准化模板</c:if>
							 	<c:if test="${customReportModelVO.entity.type == 1 }">个性化模板</c:if>
					   </span>
         			   <span id="gameIdSpan" <c:if test="${customReportModelVO.entity.type == 0}">hidden="hidden"</c:if>>
          			    	<c:if test="${customReportModelVO.entity.type == 1}">
          			    		<label for="gameId">游戏:</label>
          			    	    ${games.name }
          			    	</c:if>
        		 		 </span>
        		 		 <span>
							    <label for="entity.status">模板状态</label>
								<form:select path="entity.status">
							       <form:option value="1">有效</form:option>
							       <form:option value="0">无效</form:option>
							  
							    </form:select>
								<font color="red">*</font>
						    </span>
						</li>
						<li>
						  <span>
							   <label>模板名称:</label>
								${customReportModelVO.entity.name }
						   </span>
						      <span>
							    <label for="entity.code">模板CODE:</label>
								${customReportModelVO.entity.code }
						     </span>
						       <span>
							    <label for="entity.intervalTime">间隔时间</label>
							    <form:select path="entity.intervalTime">
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
							    cssClass="b-radius5 p-input" style="height:150px;width:812px;float: left"
							    required="required" value="${entity.templateSql }"/>
								<font color="red" style="float: left">*</font>
						     </span>
						</li>
						<li>
						      <span style="width:100%;">
							    <label for="entity.description" style="float: left">模板描述</label>
							    <form:textarea id="description" path="entity.description" 
							    cssClass="b-radius5 p-input" style="height:150px;width:812px;float: left"
							    value="${entity.description }" />
						     </span>
						 </li>  
					</ul>
					<p class="warn-btn">
						<span class="pt10 ml30" style="display:inline-block;">模板参数信息
						(<span style="color: #49C37C;font-size: 12px;font-weight: bold;">参数为日期的，结构必须是：day,ds,或者以date结尾</span>)
						</span>
				    </p>
				    <ul id="paramUl" class="p-ul mt20" style="min-height:110px;height:0px;margin-top: 20px;">
				    <li>
				        <c:set var="indexp" value="0" />
				    	<c:forEach items="${params }" var="paraml">
					    	<c:if test="${indexp != 0 && indexp % 3 == 0 }">
					    	   </li><li>
					    	</c:if>
					     <c:set var="indexp" value="${indexp+1 }" />
				    	 <span>
					    	 <label for="${paraml.paramCode }" style="float: left;width:132px">${paraml.paramCode }</label>
					    	 <input name="${paraml.paramCode }" value="${paraml.paramName }" class="b-radius5 p-input tempParamSpan" style="width:130px" required="required" />
							 <select code="${paraml.paramCode }" style="width:60px;" class="tempParamSpan">
							 <c:forEach begin="1" end="${fn:length(params)}" step="1" var="s">
							 	<option value="${s }" <c:if test="${s == indexp }">selected="selected"</c:if>>${s }</option>
							 </c:forEach>
					       		
					    	 </select>
							 <font  color="red">*</font>
						 </span>
				    	</c:forEach>
				    </li>
				    </ul>
				    
					<p class="warn-btn">
						<span class="pt10 ml30" style="display:inline-block;">模板返回字段信息</span>
				    </p>
				    <ul id="columnUl" class="p-ul mt20" style="height:600px;">
				    <li>
				        <c:set var="index" value="0" />
				    	<c:forEach items="${columns }" var="column">
					    	<c:if test="${index != 0 && index % 3 == 0 }">
					    	   </li><li>
					    	</c:if>
					     <c:set var="index" value="${index+1 }" />
				    	 <span>
					    	 <label for="${column.columnCode }" style="float: left;width:132px">${column.columnCode }</label>
					    	 <input name="${column.columnCode }" value="${column.columnName }" class="b-radius5 p-input tempColSpan" required="required" />
							 <font  color="red">*</font>
						 </span>
						 
				    	</c:forEach>
				    </li>
				    </ul>
			    	</form:form>	
			    </div>
			</div>
		</div>
		
		<div hidden="hidden" class="template">
			<ul class="templateUl">
				<li>
		    		<span class="tempSpan">
					    <label for="entity.intervalTime" style="width:132px"></label>
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