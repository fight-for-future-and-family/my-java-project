<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>操作文档上传</title>
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

.detail-table td {
    min-width: 90px;
}	
</style>
<%
   Calendar calendar = Calendar.getInstance();
   int year = calendar.get(Calendar.YEAR);
%>
<script type="text/javascript" src="/js/adminOpMonitor/adminOpMonitorUp.js?v=${version}"></script>
<script type="text/javascript" src="/js/ajaxfileupload.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_opMonitor" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
		    	<li>福利清单</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			    <ul class="detail-nav">
			       <li id="opMonitorUp" class="active"><a></a>操作文档上传</li>
			    </ul>
			    <form:form id="dataForm" name="dataForm" method="post" modelAttribute="gamesVO" action="/panel_bi/opMonitor/download_data_templete.ac" enctype="multipart/form-data">
			      <input name="id" type="hidden" value="${game.id }" />
			      <input name="gamesId" type="hidden" value="${game.id }" />
			      <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" type="hidden" value="opMonitorUp" />
			      <input id="downType" name="downType" type="hidden" value="template" />
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li" >
			    			<div class="detail-left" style="width:70%">
			    			   <div style="float: left">
			    			        <span id="m_y">
			    		          <select class="dateSelect" id="year" name="year" style="width:62px">
			    		            <c:forEach var="y" begin="<%=year %>" end="<%=year+5 %>" step="1"> 
                                        <option value="${y }"><c:out value="${y }" /></option>
			    		            </c:forEach>
			    		         </select>
			    		       </span>
			    			   <span id="m_m">
			    			      <select class="dateSelect" id="month" name="month" style="width:62px">
			    		            <c:forEach var="m" begin="1" end="12" step="1"> 
			    		                <c:if test="${m<10 }">
			    		                	<option value="0${m }"><c:out value="${m }月" /></option>
			    		                </c:if>
			    		                <c:if test="${m>10 }">
			    		                	<option value="${m }"><c:out value="${m }月" /></option>
			    		                </c:if>
			    		            </c:forEach>
			    		         </select>
			    			   </span>
			    			   </div>
				    			<div id="file" style="float: left;margin-left: 10px"> 
                                     <input type="text" value="未选择文件" style="width:260px;font-size: 12px;" />
                                     <input id="uploadFile" name="uploadFile" type="file"/> 
                                     <input type="button" class="btn" value="选择文件" />
                                 </div> 
			    			</div>
			    			<div class="detail-right" style="width:28%">
			    				<button type="button" id="query" class="btn btn-default btn-sm" style="margin-right:10px;width:80px;float: right">数据查询</button>
			    				<button type="submit" id="muban" class="btn btn-default btn-sm" style="margin-right:10px;width:80px;float: right">下载模板</button>
			    				<button type="button" id="upload" class="btn btn-default btn-sm" style="margin-right:10px;width:80px;float: right">上传</button>
			    			</div>
			    		</li>
			    	</ul>
			    </div>
			    </form:form>
			</div>
			
			<div id="data" class="detail-table">
		       <ul style="margin-bottom: 20px;">
		         <li>上传注意事项：</li>
		         <li style="padding-left: 25px;font-size: 12px;font-weight: bold;">  1.上传文件为office2003或者office2007版Excel。最好在系统中下载模板，编辑数据后上传，保证各字段为文本类型。</li>
		         <li style="padding-left: 25px;font-size: 12px;font-weight: bold;">  2.请耐心等待上传，不要频繁点击上传按钮。</li>
		         <li style="padding-left: 25px;font-size: 12px;font-weight: bold;">  3.保证文件格式正确、不要有空行、空白区域不要有其他数据或者设置。</li>
		       </ul>
		       <div class='ajax_loading'>上传中...<img src="/images/loading.gif"></img></div>
			</div>
		</div>
	</div>
	<div hidden="hidden" class="template_cache">
	  <table class="ajax_table">
   <caption>
          <button type="button" class="downBtn btn btn-default btn-sm" style="width:80px;">下载数据</button>
     </caption>
     <thead>
         <tr>
         	<td>日期</td>
         	<td>服务器ID</td>
         	<td>操作类型代码</td>
         	<td>操作人名称</td>
         	<td>道具ID</td>
         	<td>道具名称</td>
         	<td>道具数量</td>
         	<td>被操作人名称</td>
         	<td>被操作人角色</td>
         	<td>操作日期</td>
         	<td>申请人名称</td>
         	<td>用途</td>
         </tr>
      </thead>
      <tbody>
      </tbody>
   </table>
	</div>
</body>
</html>