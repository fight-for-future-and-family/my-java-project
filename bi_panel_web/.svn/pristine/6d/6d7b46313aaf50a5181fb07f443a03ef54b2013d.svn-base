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
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/laydate.dev.js"></script>

<link rel="stylesheet" href="/css/page.css?v=${version }">
<script type="text/javascript" src="/manage/js/user/userUtil.js?v=${version }"></script>
<script type="text/javascript" src="/manage/js/user/updateUser.js?v=${version }"></script>
<style type="text/css">
.p-ul{
 min-height:80px;
 height:100px;
 overflow: hidden;
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

.p-ul input{
 width:200px;
 text-align: left;
}
</style>

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_auth.jsp" >
		  <jsp:param value="authli_user" name="auth_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
		    	<li><a href="/panel_manage/user/toUserList.ac">用户管理</a></li>
				<li>></li>
		    	<li class="active green">编辑用户</li>
			    </ol>
			    <div class="person-data">
			      <form:form id="submitForm" name="submitForm" method="post" modelAttribute="usersVO" action="/panel_manage/user/userEdit_${prePageNo }.ac" enctype="multipart/form-data">
					<p class="warn-btn">
					  <span class="pt10 ml30" style="display:inline-block;">
					  <span>基本信息</span>
					  <span id="loginNameMes" hidden="hidden" style="margin-left:25px;color:red;"></span>
					  </span>
					  <button class="btn btn-sm warn-set" style="margin-left:400px;">保存</button>
				    </p>
				    
				    <form:input path="entity.id" type="hidden"/>
				    <input id="nameSuc" type="hidden" value="2"/>
				    <input id="loginName" type="hidden" value="${usersVO.entity.loginName }"/>
				    <input id="userGameList" value="${usersVO.userGames }" type="hidden"/>
				    <input id="userGroupList" value="${usersVO.userGroups }" type="hidden"/>
					<ul class="p-ul mt20">
						<li>
						    <span>
							<label for="entity.loginName">登录账号</label>
							<form:input path="entity.loginName" cssClass="b-radius5 p-input" /> <font color="red">*</font>
							</span>
							
							<span>
							<label for="entity.email">邮箱</label>
							<input class="b-radius5 p-input" style="border:0;color: gray" name="entity.email" readonly="readonly" value="${usersVO.entity.email }" /> <font color="red">*</font>
							</span>
						</li>
						<li>
						   <span>
						    <label for="entity.telepone">手机</label>
							<form:input cssClass="b-radius5 p-input" path="entity.telepone" /> <font color="red">*</font>
							</span>
						   <span>
						    <label for="entity.telepone">真实姓名</label>
							<form:input cssClass="b-radius5 p-input" path="entity.realName" /> <font color="red">*</font>
							</span>
							
							<!--  <span>
							<label for="entity.sex">性别</label>
							<form:select cssClass="b-radius5 p-input" path="entity.sex">
							  <form:option value="1">男</form:option>
							  <form:option value="2">女</form:option>
							  <form:option value="-1">未知</form:option>
							</form:select>
							</span>
							
							<span>
							<label for="entity.department">部门</label>
							<form:select cssClass="b-radius5 p-input" path="entity.department">
							  <form:option value="1">运营</form:option>
							  <form:option value="2">管理层</form:option>
							  <form:option value="3">BI</form:option>
							</form:select>
							</span>-->
						</li>
					</ul>
					
					<p class="warn-btn">
					   <span class="pt10 ml30" style="display:inline-block;"><span>管理游戏</span></span>
					<!--  <button type="button"  class="btn btn-sm warn-set" style="margin-left:400px;">添加</button>-->
				    </p>
				    <ul>
				     <li style="overflow: hidden;">
				      <c:set var="index" value="0"/>
				      <c:forEach items="${gamesList}" var="game">
				        <c:if test="${index % 5 == 0 }"><p  style="overflow: hidden;"></c:if>
				        <c:set var="index" value="${index+1 }"/>
				         <span style="float: left;width:20%;text-align: left;padding-left: 3%;">
				            <form:checkbox path="userGames" value="${game.id }" />
				            <span>${game.name }</span>
				        </span>
				      </c:forEach>
				      </li>
				      
				    </ul>
				    
				    <p class="warn-btn">
					<span class="pt10 ml30" style="display:inline-block;"><span>管理角色</span></span>
					<!-- <button type="button" class="btn btn-sm warn-set" style="margin-left:400px;">添加</button>-->
				    </p>
				    <ul>
				    <li style="overflow: hidden;">
				      <c:set var="index" value="0"/>
				      <c:forEach items="${groupsList}" var="group">
				        <c:if test="${index % 5 == 0 }"><p></c:if>
				        <c:set var="index" value="${index+1 }"/>
				        <span style="float: left;width:20%;text-align: left;padding-left: 3%">
				          <form:checkbox path="userGroups" value="${group.id }" />
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
</body>
</html>