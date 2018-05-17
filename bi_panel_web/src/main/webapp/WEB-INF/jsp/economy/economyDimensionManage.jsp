<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>维表管理</title>
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
	
	.edit-btn,.save-btn,.del-btn,.add-save-btn{
	  margin-left: 10px;
	  text-align: center;
	  font-size: 14px;
	  padding: 2px 15px;
	  border: 1px solid #ebebeb;
	  background-color: #C5D5D0;
	  color: rgb(86, 82, 82);
	  border-radius:5px;
    }
	
	.downBtn,.add-btn{
	  margin-left: 10px;
	  text-align: center;
	  font-size: 14px;
	  padding: 2px 10px;
	  border: 1px solid #ebebeb;
	  background-color: #DFE2E2;
	  color: rgb(86, 82, 82);
	  border-radius:5px;
	}
</style>
<script type="text/javascript" src="/js/economy/economyDimension.js?v=${version}"></script>
<script type="text/javascript" src="/js/ajaxfileupload.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_economy" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
		    	<li>经济系统</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			    <ul class="detail-nav">
			       <li id="economyItem"><a ></a>消费分析</li>
			       <li id="economyDimension" class="active"><a></a>维表管理</li>
			    </ul>
			    <form:form id="economyForm" name="economyForm" method="post" modelAttribute="gamesVO" action="/panel_bi/economy/download_data_templete.ac" enctype="multipart/form-data">
			      <input name="id" type="hidden" value="${game.id }" />
			      <input name="gamesId" type="hidden" value="${game.id }" />
			      <%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input name="snid" type="hidden" value="${snid }" />
			      <input name="gameId" type="hidden" value="${gameId }" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" type="hidden" value="economyDimension" />
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li" >
			    			<div class="detail-left" style="width:70%">
			    			   <div style="float: left" hidden="hidden">
			    			        <select id="templeteType" name="templeteType">
				    			   <option value="point">消费点</option>
				    			   <option value="item">道具</option>
				    			</select>
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
		       <div class='ajax_loading'>维表上传中...<img src="/images/loading.gif"></img></div>
			</div>
		</div>
	</div>
	
	<div hidden="hidden" class="template_cache">
	  <table class="ajax_table">
   <caption>
          <button type="button" class="downBtn" style="width:80px;">下载数据</button>
          <button type="button" class="add-btn" style="width:80px;">添加数据</button>
     </caption>
     <thead>
         <tr>
         	<td>消费代码</td>
         	<td>消费名称</td>
         	<td>操作</td>
         </tr>
      </thead>
      <tbody>
      </tbody>
   </table>
   
	
	</div>
</body>
</html>