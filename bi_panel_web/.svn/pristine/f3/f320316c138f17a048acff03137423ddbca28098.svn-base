<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-CPA&CPS</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/cpa/authManage.js?v=${version}"></script>
<link rel="stylesheet" href="/css/main/main.css?v=${version}"  />
<style type="text/css">
.dataTables_wrapper .dataTables_filter {
	margin-top: 10px;
}

#caption {
	font-style: normal;
	text-align: left;
	top: 5px;
	margin-right: 20px;
	float: right;
}

.zhibiao {
	width: 100%;
	height: 350px;
	margin-top: 20px;
	margin-bottom: 20px;
	float: left;
}

.auth-btn,.del-btn,.add-btn,.save-btn{
	  margin-left: 10px;
	  text-align: center;
	  font-size: 14px;
	  padding: 2px 10px;
	  border: 1px solid #ebebeb;
	  border-radius:5px;
	  background-color: #fff;
	}
.dform {
	padding:8px 5px;
	text-align: center;
}

.warn-btn{
    height: 30px;
    text-align: left;
    padding-top: 10px; 
    overflow: hidden;
    border-bottom: 1px solid #ebebeb;
}

.theme-popover {
	z-index:9999;
	position:fixed;
	top:45%;
	left:45%;
	width:900px;
	min-height:550px;
	margin:-180px 0 0 -300px;
	border-radius:5px;
	border:solid 2px #666;
	background-color:#fff;
	display:none;
	box-shadow: 0 0 10px #666;
}
.table-div{
    border-right: 1px solid #ebebeb;
    border-bottom: 1px solid #ebebeb;
    float: left;
    width:50%;
    min-height:430px;
    height:430px;
    overflow: auto;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_CPA" name="game_page"/>
		</jsp:include>
		<div class="data-container" style="min-height:700px">
			<div class="detail">
				<ol class="lp-breadcrumb">
					<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
					<li>></li>
					<li>${game.name }</li>
					<li>></li>
			    	<li>CPS</li>
			    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
				     
			    <ul class="detail-nav">
			       <!--  <li id="cpa"><a ></a>CPA</li>-->
			       <li id="cps"><a></a>CPS</li>
			       <c:if test="${!isOutSideUser }">
				       <li id="dimension"><a></a>维表管理</li>
				       <li id="authManage" class="active"><a></a>权限管理</li>
			       </c:if>
			    </ul>
			    
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/costPer/downloadData.ac" enctype="multipart/form-data">
					<input name="gamesId" type="hidden" value="${game.id }" />
					<%-- <input name="snid" type="hidden" value="${game.snid }" />
					<input name="gameId" type="hidden" value="${game.gameid }" /> --%>
					<input name="snid" type="hidden" value="${snid }" />
					<input name="gameId" type="hidden" value="${gameId }" />
					<input id="gameRate" type="hidden" value="${game.rate }" />
					<input id="gameCurrency" type="hidden" value="${game.currency }" />
					<input id="timeZone" type="hidden" value="${game.timeZone }" />
					<input id="view" name="view" type="hidden" value="authManage" />
					<div class="detail-info" style="margin-top: 10px;height: 50px;">
				    	<ul class="detail-ul">
				    		<li class="detail-li">
				    		<div class="detail-left" style="width:87%">
				    		<span style="padding-top:10px">
				    		      <label>登录账号：</label>
				    		      <input id="loginName" class="b-radius5 p-input" style="width:20%">
				    			</span>
				    		   <span style="padding-left:40px;padding-top:10px">
				    		     <label>用户邮箱：</label>
				    		     <input id="loginName" class="b-radius5 p-input" style="width:20%">
				    			</span>
				    		</div>
				    		   
				    			<div class="detail-right" style="width:10%">
				    				<button id="query" type="button" class="btn btn-default btn-sm" style="width:100px;">查询</button>
				    			</div>
				    		</li>
				    	</ul>
				    </div>
				    
			   
			   		 <div id="data" class="detail-table">
                       
			   		 </div>
			    </form:form>
			</div>
		</div>
	</div>
	
	<div class="theme-popover" style="height:480px;width:900px">
     <div class="theme-poptit">
          <a href="javascript:;" title="关闭" class="close">
          <font size="2">无操作或放弃更改请点关闭</font>
          <font size="5" >×</font>
          </a>
          <h5 id="gameTypeName">渠道权限分配</h5>
     </div>
     <div class="theme-popbod dform">
          <p class="warn-btn">
              <span  style="margin-left:10px;float: left;margin-top: -10px;color:red"> 
              注意：如果没有查询到要添加的渠道，在《CPA&CPS>维表管理》页中上传或添加；渠道分配完成后一定要点  保存按钮  --->
              </span>
			  <button type="button" class="save-btn" style="width:80px;margin-right:10px;float: right;margin-top: -10px;">保存</button>
	       </p>
	       <div class="table-div">
	       <p class="warn-btn" style="background-color:#F6F6F6;padding-bottom:8px;">
			  <span style="margin-left:10px;float: left;font-weight: bold">已分配的渠道</span>
	       </p>
	       <div id="hadTable">
	       </div>
	       </div>
	       <div class="table-div" style="border-right: none;">
	       <p class="warn-btn" style="background-color:#F6F6F6;padding-bottom:8px;">
			  <span style="margin-left:10px;float: left;font-weight: bold">未分配的渠道</span>
	       </p>
	       <div id="needTable">
	       </div>
	       </div>
     </div>
</div>
<div class="theme-popover-mask"></div>

	<div hidden="hidden" class="template_cache">
		 <table class="view_table" style="overflow: hidden;">
	      <thead>
	      	<tr>
	      		<td>登录账号</td>
	      		<td>邮箱</td>
	      		<td>真实名称</td>
	      		<td>电话</td>
	      		<td>操作</td>
	      	</tr>
	      </thead>
          <tbody>
          <tr>
	      		<td class="loginName">登录账号</td>
	      		<td class="email">邮箱</td>
	      		<td class="realName">真实名称</td>
	      		<td class="telepone">电话</td>
	      		<td class="op"><button type="button" class="auth-btn" style="width:80px;" oid="">渠道权限</button></td>
	      	</tr>
          </tbody>
	   </table>
	   
	   <table class="hadTable" style="width:100%">
	      <thead>
	      	<tr>
	      		<td>ID</td>
	      		<td>渠道代码</td>
	      		<td>渠道名称</td>
	      		<td>操作</td>
	      	</tr>
	      </thead>
          <tbody>
          <tr>
	      		<td class="id"></td>
	      		<td class="sourceCode"></td>
	      		<td class="sourceName"></td>
	      		<td class="op"><button type="button" class="del-btn" style="width:80px;" sid="">移除</button></td>
	      	</tr>
          </tbody>
		</table>
	   <table class="needTable" style="width:100%">
	      <thead>
	      	<tr>
	      		<td>ID</td>
	      		<td>渠道代码</td>
	      		<td>渠道名称</td>
	      		<td>操作</td>
	      	</tr>
	      </thead>
          <tbody>
          <tr>
	      		<td class="id"></td>
	      		<td class="sourceCode"></td>
	      		<td class="sourceName"></td>
	      		<td class="op"><button type="button" class="add-btn" style="width:80px;" sid="">增加</button></td>
	      	</tr>
          </tbody>
		</table>
	</div>
</body>
</html>