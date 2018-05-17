<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-维表管理</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<style type="text/css">
#data {
  min-height: 560px;
}
#data li{
 text-align: left;
 font-size: 14px;
 margin-top: 10px;
}
.ajax_loading{
    background:#fff;
    height:60px;
    width:100%;
    text-align:center;
    line-height:60px;
    font-size:16px;
    display:none;
    bottom:0px
  }
  
#file { 
	position:relative; 
	height:27px; 
	border: 1px #ccc solid;
	line-height: 1.42857143;
	border-radius: 4px;
	margin: 0 auto;
} 

#file input { 
	font-size:14px; 
	margin:0; 
	padding:0; 
	position:relative; 
	vertical-align:middle; 
	outline:none; 
} 

#file input[type="text"] { 
	border:3px none; 
	z-index:4; 
} 

#file input[type="button"] { 
	width:80px; 
	height:25px; 
	z-index:2; 
	cursor:pointer
} 

#file input[type="file"] { 
position:absolute; 
height:25px; 
opacity:0; 
z-index:3;
top:0;
right:0;
} 

.dataTables_wrapper .dataTables_filter {
	  margin-left:10px;
      z-index:1;
      position:absolute;
     }
     caption {
        font-style: normal;
        text-align: right;
        margin-right: 30px;
	}

</style>
<script type="text/javascript" src="/js/cpaCreative/dimensionCreative.js?v=${version}"></script>
<script type="text/javascript" src="/js/ajaxfileupload.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_CPACreative" name="game_page"/>
		</jsp:include>
		<div class="data-container">
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
			       <li id="cpa"><a></a>CPA</li>
			       <li id="dimension" class="active"><a></a>维表管理</li>
			       <li id="authManage"><a></a>权限管理</li>
			    </ul>
			    <form:form id="costPerForm" name="costPerForm" method="post" modelAttribute="gamesVO" action="/panel_bi/costPer/download_data_templete.ac" enctype="multipart/form-data">
			      <input name="id" type="hidden" value="${game.id }" />
			      <input name="gamesId" type="hidden" value="${game.id }" />
			      <%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input name="snid" type="hidden" value="${snid }" />
			      <input name="gameId" type="hidden" value="${gameId }" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" type="hidden" value="dimension" />
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li" >
			    			<div class="detail-left" style="width:70%">
			    			</div>
			    			<div class="detail-right" style="width:28%">
			    				<button type="button" id="query" class="btn btn-default btn-sm" style="margin-right:10px;width:80px;float: right">数据查询</button>
			    			</div>
			    		</li>
			    	</ul>
			    </div>
			    </form:form>
			</div>
			
			<div id="data" class="detail-table">
			</div>
		</div>
	</div>
	
	<div hidden="hidden" class="template_cache">
	  <table class="ajax_table">
   <caption>
          <button type="button" class="add-btn btn btn-default btn-sm" style="width:80px;">添加数据</button>
     </caption>
     <thead>
         <tr>
         	<td>ID</td>
         	<td>渠道代码</td>
         	<td>渠道名称</td>
         	<td>操作</td>
         </tr>
      </thead>
      <tbody>
      </tbody>
   </table>
   
   <table id="add-table">
   <tr>
	   <td><input type="text"></td>
	   <td><input type="text"></td>
	   <td><button class="add-save-btn btn btn-default btn-sm" type="button">保存</button>
	   <button class="del-btn btn btn-default btn-sm" type="button">删除</button></td>
   </tr>
   </table>
	
	</div>
</body>
</html>