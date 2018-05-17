<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="robots" content="noodp, noydir" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta charset="utf-8">

<title>账号管理-子账户</title>

<link rel="stylesheet" href="/css/bootstrap.min.css"/>
<link rel="stylesheet" href="/css/page.css?v=${version}">

<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/laydate.dev.js"></script>
<script type="text/javascript" src="/js/page.js?v=${version}"></script>
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
		    	<li class="active green">用户管理</li>
			    </ol>
			    <div class="msgcss">${msg }</div>
			    <div class="op_bar" style="overflow: hidden;">
			     <form:form method="post" modelAttribute="usersVO" action="/panel_manage/user/toAddUser.ac" enctype="multipart/form-data">
			        <form:button type="submit" class="btn btn-sm warn-set">添加</form:button>
			      </form:form>
			    </div>
			    <form:form id="submitForm" name="submitForm" method="post" modelAttribute="usersVO" action="/panel_manage/user/toUserList.ac" enctype="multipart/form-data">
			    <input type="hidden" id="toFirst" value="/panel_manage/user/toUserList">
			    <input type="hidden" id="toPage" value="/panel_manage/user/userList_">
			    <input type="hidden" id="toDel" value="/panel_manage/user/userDel_">
			    <input type="hidden" id="toStatus" value="/panel_manage/user/userStatus_">
			    <input type="hidden" id="toEdit" value="/panel_manage/user/toUserEdit_">
			    <div class="detail-info">
			      <ul class="detail-ul">
			    		<li class="detail-li" style="overflow: hidden;">
				    	  <span style="width:45%;float: left;padding-left:5px;padding-top:10px;">
				    	   	 <span>用户ID:</span><form:input path="entity.id" class="b-radius5 p-input" style="margin-left:25px;width:200px"/>
				    	  </span>
				    	  <span style="width:45%;float: left;padding-left:5px;padding-top:10px;">
				    	  	<span>用户名称:</span><form:input path="entity.loginName" class="b-radius5 p-input" style="margin-left:25px;width:200px"/>
				    	  	</span>
			    		</li>
			    		<li class="detail-li">
			    		   <div style="width:70%;padding-left:5px;padding-top:10px;" class="detail-left">
			    		   <span>用户角色</span>
			    		   <span style="margin-left:10px;">
			    		      <form:select path="groups.id" style="min-width:200px">
			    	               <form:option value="0" label="请选择..."/>      
			    	               <form:option value="-1" label="未分组"/>      
			    	               <form:options items="${groupsList}" itemValue="id" itemLabel="groupName" delimiter="&nbsp;&nbsp;"/>         
			    	            </form:select>
			    		   </span>
			    		   </div>
			    		   <span class="detail-right">
			    				<button id="query" class="btn btn-default btn-sm" style="width:100px;">查询</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
			    <div class="detail-table">
			    	<table class="table table-striped">
			    		<thead>
			    			<tr>
			    				<td>ID</td>
			    				<td>登录名称</td>
			    				<td>真实名称</td>
			    				<td>邮箱</td>
			    				<td>电话</td>
			    				<td>操作</td>
			    			</tr>
			    		</thead>
			    		<tbody>
			    		   <c:forEach items="${displayResultList }" var="user">
			    		      <tr>
			    		         <td>${user.entity.id } 
			    		         <c:if test="${user.smiple == 'smiple' }">
			    				 (密码简单)
			    				 </c:if></td>
			    				 <td>${user.entity.loginName }</td>
			    				 <td>${user.entity.realName }</td>
			    				 <td>${user.entity.email }</td>
			    				 <td>${user.entity.telepone }</td>
			    				 <td>
			    				    <a id="opEdit" value="${user.entity.id }" page="${pagination.pageNo }">编辑</a>|
			    				    <a id="opdel" value="${user.entity.id }" page="${pagination.pageNo }">删除</a>|
			    				    <c:if test="${user.entity.status !=0}">
			    				    <a id="opStatus" value="${user.entity.id }" op="1" page="${pagination.pageNo }">解冻</a>
			    				    </c:if>
			    				    <c:if test="${user.entity.status ==0}">
			    				    <a id="opStatus" value="${user.entity.id }" op="3" page="${pagination.pageNo }">冻结24小时</a>|
			    				    <a id="opStatus" value="${user.entity.id }" op="2" page="${pagination.pageNo }">无期冻结</a>
			    				    </c:if>
			    				 </td>
			    		      </tr>
			    		   </c:forEach>
			    		</tbody>
			    	</table>
			    </div>
			    <nav>
				    <ul class="pagination">
				       <c:if test="${pagination.totalPage > 0}">
				          <c:choose>
				             <c:when test="${pagination.totalPage==1 || !pagination.pre}">
							    <li class="disabled"><a>首页</a></li>
							    <li class="disabled"><a>上一页</a></li>
							 </c:when>
							 <c:otherwise>
							   <c:if test="${pagination.pre }">
						         <c:choose>
							        <c:when test="${pagination.prePageNo==1 }">
							          <li><a id="first">首页</a></li>
							          <li><a id="first">上一页</a></li>
							       </c:when>
							       <c:otherwise>
							          <li><a id="first" href="/panel_manage/user/toUserList.ac">首页</a></li>
							          <li><a id="page" value="${pagination.prePageNo}">上一页</a></li>
							       </c:otherwise>
						        </c:choose>
						     </c:if>
						    </c:otherwise>
				          </c:choose>
				       </c:if>
				    
				        <c:forEach begin="1" end="${pagination.totalPage }" var="pn">
								<c:choose>
									<c:when test="${pn == 1 && pn != pagination.pageNo }">
									   <li><a id="first">${pn}</a></li>
									</c:when>
									<c:when test="${(pn>=pagination.pageNo-pagination.beforeDisplayNum) && (pn < pagination.pageNo) }">
									   <li><a id="page" value="${pn}">${pn}</a></li>
									</c:when>
									<c:when test="${pn == pagination.pageNo }">
									   <li class="active"><a data="${pn}" href="javascript:void(0);" >${pn} <span class="sr-only">(current)</span></a></li>
									</c:when>
									<c:when test="${(pagination.pageNo+pagination.endDisplayNum >= pn) && (pn > pagination.pageNo) }">
										<li><a id="page" value="${pn}">${pn}</a></li>
									</c:when>
								</c:choose>
							</c:forEach>
				    
				      <c:if test="${pagination.totalPage > 0}">
				          <c:choose>
				             <c:when test="${pagination.totalPage==1 or ((pagination.pageNo + 1) > pagination.totalPage)}">
							    <li class="disabled"><a>下一页</a></li>
							    <li class="disabled"><a>末页</a></li>
							 </c:when>
							<c:otherwise>
							   <li><a id="page" value="${pagination.pageNo + 1}">下一页</a></li>
							   <li><a id="page" value="${pagination.totalPage}">末页</a></li>
							</c:otherwise>
				          </c:choose>
				       </c:if>
				     </ul>
				</nav>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>