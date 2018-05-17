<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="robots" content="noodp, noydir" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<title>游戏管理-游戏列表</title>
<link rel="stylesheet" href="/css/bootstrap.min.css"/>
<link rel="stylesheet" href="/css/page.css?v=${version}">

<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<style type="text/css">
.detail-table td{
	min-width: 150px; 
}
.kpi{
   min-width: 270px; 
}
.op_bar {
  margin-left: 70%;
  margin-bottom: 10px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_auth.jsp" >
		  <jsp:param value="authli_etlengineManage" name="auth_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a href="/panel_manage/toMain.ac">ETL模板管理</a></li>
				<li>></li>
		    	<li class="active green">模板列表</li>
			    </ol>
			    <div class="msgcss">${msg }</div>
			    
			<div class="msgcss">${msg }</div>
			<div class="op_bar" style="overflow: hidden;">
			<form:form method="post" modelAttribute="etlengineManageVO" action="/panel_manage/EtlengineManage/toAddEtlengineManage.ac" enctype="multipart/form-data">
			<form:button type="submit" class="btn customReportModelVO-sm warn-set" >添加模板</form:button>
			<input type="hidden" name="snid" value=""/>
			</form:form>
 			</div>
			    
			    <form:form id="submitForm" name="submitForm" method="post" modelAttribute="etlengineManageVO" action="/panel_manage/EtlengineManage/toEtlengineManage.ac" enctype="multipart/form-data">
			    <input type="hidden" id="toFirst" value="/panel_manage/EtlengineManage/toEtlengineManage">
			    <input type="hidden" id="toPage" value="/panel_manage/EtlengineManage/toEtlengineManageList_">
			    <input type="hidden" id="toDel" value="/panel_manage/EtlengineManage/toEtlengineManageDel_">
			    <input type="hidden" id="toEdit" value="/panel_manage/EtlengineManage/toEtlengineManageEdit_">
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li" style="height:45px;overflow: hidden;">
			    			<span style="width:25%;float: left;padding-left:5px;padding-top:10px;">
			    			    ID：<form:input path="entity.id" class="b-radius5 p-input" style="margin-left:5px;width:180px"/>
			    			</span>
			    			<span style="width:25%;float: left;padding-left:5px;padding-top:10px;">
			    			    层级：<form:input path="entity.level" class="b-radius5 p-input" style="margin-left:5px;width:180px"/>
			    			</span>
			    			
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
			    				<td>title</td>
			    			
			    				<td>层级</td>
			    				<td>创建时间</td>
			    				<td width="8%">操作</td>
			    			</tr>
			    		</thead>
			    		<tbody>
			    		   <c:forEach items="${displayResultList }" var="test">
			    		      <tr>
			    				 <td>${test.entity.id }</td>
			    				 <td>${test.entity.title }</td>
			    				
			    				 <td>${test.entity.level }</td>
			    				 <td><fmt:formatDate value="${test.entity.addedAt }" type="date"/></td>
			    				 <td>
			    				    <a id="opEdit" value="${test.entity.id }" page="${pagination.pageNo }">编辑</a>
			    				    <a id="opdel" value="${test.entity.id }" page="${pagination.pageNo }">删除</a>
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
							          <li><a id="first">首页</a></li>
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