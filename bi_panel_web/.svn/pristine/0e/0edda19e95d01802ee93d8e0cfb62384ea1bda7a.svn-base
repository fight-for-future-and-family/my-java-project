<%@page import="com.hoolai.bi.report.core.Constant"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>游戏管理-修改游戏</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<style type="text/css">
.p-input{
 width: 200px;
 height: 25px;
 border: none;
 border: 1px solid #ebebeb;
}

.p-ul {
height: 350px;
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
</style>
<script type="text/javascript" src="/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="/js/timezone.js"></script>
<script type="text/javascript" src="/manage/js/game/addGame.js?v=${version }"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_auth.jsp" >
		  <jsp:param value="authli_game" name="auth_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
		    	<li><a href="/panel_manage/gameManager/toGameList.ac">游戏管理</a></li>
				<li>></li>
		    	<li class="active green">修改游戏</li>
			    </ol>
			    <div class="person-data">
			      <form:form id="submitForm" name="submitForm" method="post" modelAttribute="gamesVO" action="/panel_manage/gameManager/gameEdit_${prePageNo }.ac" enctype="multipart/form-data">
					<p class="warn-btn">
					<span class="pt10 ml30" style="display:inline-block;"><span>基本信息</span>
					</span>
					<button id="add" class="btn btn-sm warn-set" style="margin-left:400px;">保存</button>
				    </p>
					<form:input path="entity.id" type="hidden"/>
					 <input id="gameGroupList" value="${gameGroupList }" type="hidden"/>
					 <input id="timeZoneVal" value="${gamesVO.entity.timeZone }" type="hidden"/>
					<ul class="p-ul mt20">
						<li>
						    <span>
							   <label for="entity.name">游戏名称</label>
							   <form:input cssClass="b-radius5 p-input" path="entity.name" required="required"  />
							   <font color="red">*</font>
						   </span>
						   <span>
								<label for="entity.snid">平台ID</label>
								<form:input cssClass="b-radius5 p-input" path="entity.snid" required="required" />
								<font color="red">*</font>
							</span>
						    <span>
							    <label for="entity.gameid">游戏ID</label>
								<form:input path="entity.gameid" cssClass="b-radius5 p-input" required="required" />
								<font color="red">*</font>
						    </span>
						</li>
						<li>
						   <span>
								<label for="entity.stats">是否运营</label>
								<form:select path="entity.stats" required="required" style="min-width:200px;max-width:200px;">
								   <form:option value="1">是</form:option>
								   <form:option value="0">否</form:option>
								</form:select>
								<font color="red">*</font>
							</span>
						   <span>
								<label for="entity.rate">货币汇率</label>
								<form:input cssClass="b-radius5 p-input" path="entity.rate" required="required" />
								<font color="red">*</font>
							</span>
							
							<span>
								<label for="entity.currency">货币类型</label>
								<form:select path="entity.currency" required="required" 
								style="min-width:200px;max-width:200px;">
								   <form:option value="元">元</form:option>
								   <form:option value="美元">美元</form:option>
								   <form:option value="台币">台币</form:option>
								   <form:option value="未可知">未可知</form:option>
								   <form:option value="其他">其他</form:option>
								</form:select>
								<input hidden="hidden" class="b-radius5 p-input"/>
								<font color="red">*</font>
							</span>
						</li>
						
						<li>
						    <span>
								<label for="entity.onlineDate">隶属游戏系列</label>
								<form:select path="entity.parentId" required="required" 
								style="min-width:200px;max-width:200px;">
								   <form:option value="0">请选择...</form:option>
								   <c:forEach items="${gamesTypes }" var="gameType">
								     <form:option value="${gameType.id }">${gameType.name }</form:option>
								   </c:forEach>
								</form:select>
								<input hidden="hidden" class="b-radius5 p-input"/>
								<font color="red">*</font>
						    </span>
						    <span>
								<label for="entity.terminalType">游戏运行终端</label>
								<form:select path="entity.terminalType" required="required" 
								style="min-width:200px;max-width:200px;">
								   <form:option value="0">PC</form:option>
								   <form:option value="1">手机</form:option>
								</form:select>
								<input hidden="hidden" class="b-radius5 p-input"/>
								<font color="red">*</font>
						    </span>
						    <span>
								<label for="entity.systemType">游戏运行系统</label>
								<form:select path="entity.systemType" required="required" 
								style="min-width:200px;max-width:200px;">
								   <form:option value="-1">默认</form:option>
								   <form:option value="0" >IOS</form:option>
								   <form:option value="1">ANDROID</form:option>
								</form:select>
								<input hidden="hidden" class="b-radius5 p-input"/>
								<font color="red">*</font>
						    </span>
						    
						</li>
						<li>
						  <span>
								<label for="entity.etlStatus">是否计算etl</label>
								<form:select path="entity.etlStatus" required="required" 
								style="min-width:200px;max-width:200px;">
								   <form:option value="1">是</form:option>
								   <form:option value="0">否</form:option>
								</form:select>
								<input hidden="hidden" class="b-radius5 p-input"/>
								<font color="red">*</font>
						    </span>
						   <span>
								<label for="entity.onlineDate">上线日期</label>
								<form:input id="onlineDate" cssClass="b-radius5 p-input" path="onlineDate" />
						    </span>
						    <span>
								<label for="entity.timeZone">运营时区</label>
								<form:select id="timeZone" path="entity.timeZone" style="max-width:200px">
								   <form:option value="-1">未知时区</form:option>
								</form:select>
						    </span>
						   
						</li>
						<li>
							<span>
						      <label for="entity.alias">游戏别名</label>
							  <form:input cssClass="b-radius5 p-input" path="entity.alias" />
						   </span>
						   <span>
							   <label for="entity.monthKpi">月目标KPI</label>
							   <input name="entity.monthKpi" class="b-radius5 p-input" 
							   value="<fmt:formatNumber value="${gamesVO.entity.monthKpi }" pattern="#,##0"/>" />
						   </span>
						   <span>
								<label for="entity.yearKpi">年目标KPI</label>
								<input class="b-radius5 p-input" name="entity.yearKpi" 
								value="<fmt:formatNumber value="${gamesVO.entity.yearKpi }" pattern="#,##0"/>"/>
							</span>
						</li>
						<li>
						  <span>
							    <label for="entity.remark" style="float: left">游戏描述</label>
								<form:textarea cssClass="b-radius5 p-input" path="entity.remark" style="height:100px;width:250px;"/>
							</span>
						   <span style="clear: right;height:120px;">							
							    <label for="entity.logo" style="float: left">游戏LOGO</label>
								<img alt="logo" id="logo_img" src="${resourceUrl }${gamesVO.entity.logo }" style="height:100px;width:100px;float:left;border: 1px solid #ebebeb;">
							    <input id="upImage" type="button" value="上传图片" class="btn warn-set"/>
								<input id="imageFile" name="imageFile" type="file" value="选择图片" class="btn" style="margin-top:5px;margin-left: 100px"/>
							</span>
							
							<form:input type="hidden"  id="logo" path="entity.logo" value="${gamesVO.entity.logo }"/>
							<span>
							    <label for="entity.etlOrder">etl计算优先级</label>
								<form:input path="entity.etlOrder" cssClass="b-radius5 p-input" required="required" />
								<font color="red">*</font>
						    </span>
						</li>
					</ul>
				    
				    <p class="warn-btn">
					  <span class="pt10 ml30" style="display:inline-block;"><span>管理用户</span>
					</span>
				    </p>
				    <ul>
				    <li>
				      <c:set var="index" value="0"/>
				      <c:forEach items="${groupsList}" var="group">
				        <c:if test="${index % 5 == 0 }"><p></c:if>
				        <c:set var="index" value="${index+1 }"/>
				        <span style="float: left;width:20%;text-align: left;padding-left: 3%">
					        <form:checkbox path="gameManageUsers" value="${group.id }" />
					        <span>${group.groupName }</span>
				        </span>
				      </c:forEach>
				      </li>
				    </ul>
			    	</form:form>	
			    </div>
			</div>
		</div>
	</div>
	
	<div hidden="hidden" class="check">
	  <span id="for_entity_gameid" checkId="entity.gameid" class="errorMsg">游戏ID不能为空</span>
	  <span id="for_entity_snid" checkId="entity.snid" class="errorMsg">渠道ID不能为空</span>
	  <span id="for_entity_name" checkId="entity.name" class="errorMsg">游戏名称不能为空</span>
	  <span id="for_entity.rate" checkId="entity.rate" class="errorMsg">货币汇率不能为空</span>
	  <span id="for_entity_currency" checkId="entity.currency" class="errorMsg">货币类型不能为空</span>
	  <span id="for_entity_stats" checkId="entity.stats" class="errorMsg">是否在线不能为空</span>
	</div>
</body>
</html>