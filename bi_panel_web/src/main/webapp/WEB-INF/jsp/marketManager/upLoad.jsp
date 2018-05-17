<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>上传</title>
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
} 

#file input[type="file"] { 
position:absolute; 
height:25px; 
opacity:0; 
z-index:3;
top:0;
right:0;
} 
</style>
<script type="text/javascript" src="/js/download/upLoad.js?v=${version}"></script>
<script type="text/javascript" src="/js/ajaxfileupload.js"></script>
<script type="text/javascript">
window.onload=function(){ 
var file=document.querySelector("#file input[type='file']"); 
var text=document.querySelector("#file input[type='text']"); 
file.addEventListener("change",assign,false); 
function assign(){ 
text.value=file.value; 
} 
} 
</script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_market" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
		    	<li>市场&资源</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			    <ul class="detail-nav">
			       <li id="dataquery"><a href="#"></a>数据查询</li>
			       <li class="active" id="upload"><a href="#"></a>数据上传</li>
			    </ul>
			    <form:form method="post" modelAttribute="gamesVO" action="/panel_bi/market/download_data_templete.ac" enctype="multipart/form-data">
			      <input name="id" type="hidden" value="${game.id }" />
			      <input name="gamesId" type="hidden" value="${game.id }" />
			      <%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input name="snid" type="hidden" value="${snid }" />
			      <input name="gameId" type="hidden" value="${gameId }" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" type="hidden" value="overview" />
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li" >
			    			<div class="detail-left" style="width:70%">
			    			   <div style="float: left">
			    			      <select id="templeteType" name="templeteType">
				    			   <option value="gdt">广点通</option>
				    			   <option value="scgl">市场管理</option>
				    			</select>
			    			   </div>
				    			<!--  <div style="float: left;padding-left: 20px">
				    			  <input id="uploadFile" type="file" style="width:200px"/>
				    			</div>-->
				    			<div id="file" style="float: left;margin-left: 20px"> 
                                     <input type="text" value="未选择文件" style="width:260px" />
                                     <input id="uploadFile" name="uploadFile" type="file"/> 
                                     <input type="button" class="btn" value="选择文件" />
                                     
                                 </div> 
				    			
			    			</div>
			    			<div class="detail-right" style="width:28%">
			    				<!--  <button type="button" id="luru" class="btn btn-default btn-sm" style="margin-right:10px;width:80px;float: right">数据录入</button>-->
			    				<button type="submit" id="muban" class="btn btn-default btn-sm" style="margin-right:10px;width:80px;float: right">下载模板</button>
			    				<button type="button" id="query" class="btn btn-default btn-sm" style="margin-right:10px;width:80px;float: right">上传</button>
			    			</div>
			    		</li>
			    	</ul>
			    </div>
			    </form:form>
			</div>
			
			<div id="data">
		       <ul>
		         <li>上传注意事项：</li>
		         <li style="padding-left: 25px;">  1.上传文件为office2003或者office2007版Excel。最好在系统中下载模板，编辑数据后上传。</li>
		         <li style="padding-left: 25px;">  2.请耐心等待上传，不要频繁点击上传按钮。</li>
		         <li style="padding-left: 25px;">  3.保证文件格式正确、不要有空行、空白区域不要有其他数据或者设置。</li>
		       </ul>
		       <div class='ajax_loading'>报表上传中...<img src="/images/loading.gif"></img></div>
			</div>
			
		</div>
	</div>
</body>
</html>