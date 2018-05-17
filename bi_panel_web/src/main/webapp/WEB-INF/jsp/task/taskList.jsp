<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>自定义报表</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<link rel="stylesheet" href="/css/main/main.css?v=${version}"  />
<style type="text/css">
#data {
  min-height: 460px;
}

.dataTables_wrapper .dataTables_filter {
	  margin-left:10px;
      z-index:1;
      position:absolute;
     }
     .caption {
        font-style: normal;
        text-align: right;
        margin-right: 30px;
	}
	
	.downBtn,.add-btn,.query-btn,.reflush-btn,#downloadCsv{
	  margin-left: 10px;
	  text-align: center;
	  font-size: 14px;
	  padding: 2px 10px;
	  border: 1px solid #ebebeb;
	  border-radius:5px;
	  background-color: #fff;
	}
	
.detail-table {
    width: 98%;
}
	
.detail-table td {
    min-width: 90px;
}

.detail-table .short-td {
    max-width: 100px;
}

.table{
    margin-top: 30px;
}

.addDataTable{
    border-collapse: collapse !important;
	width:100%;
	margin-left: 0%;
	text-align: left;
}

.addDataTable>tbody>tr>td,th{
    padding:5px 5px;
	border-top: 0;
	font-weight: normal;
	font-size: 14px;
	height: 40px;
}
.addDataTable>tbody>tr>th{
	font-size:14px;
	font-weight: bold;
}

.warn-btn{
	height: 30px;
    text-align: left;
    padding-top: 10px; 
    border-bottom: 1px solid #ebebeb;
    overflow: hidden;
}

.result{
	border: 1px solid #ebebeb;
	border-radius: 10px;
	margin: 15px 0px;
    text-align: left;
    background-color: rgb(247, 247, 247);
    min-height: 35px;
    width: 98%;
    padding: 7px 15px;
}

.theme-popover {
	z-index:9999;
	position:fixed;
	top:50%;
	left:50%;
	width:650px;
	min-height:450px;
	margin:-180px 0 0 -300px;
	border-radius:5px;
	border:solid 2px #666;
	background-color:#fff;
	display:none;
	box-shadow: 0 0 10px #666;
}

</style>
<script type="text/javascript" src="/js/task/customTask.js?v=${version}"></script>



</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_customreport" name="game_page"/>
		</jsp:include>
		<div class="data-container" style="min-height:750px;">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
		    	<li>自定义报表</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			    <ul class="detail-nav">
			       <li id="shortUrl" class="de-first active"><a ></a>任务</li>
			    </ul>
			    <form:form id="adForm" name="adForm" method="post" modelAttribute="taskVO" action="/panel_bi/task/toTask.ac" enctype="multipart/form-data">
			      <input name="id" type="hidden" value="${game.id }" />
			      <input name="gamesId" type="hidden" value="${game.id }" />
			      <%-- <input id="snid" name="snid" type="hidden" value="${game.snid }" />
			      <input id="gameid" name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input id="snid" name="snid" type="hidden" value="${snid }" />
			      <input id="gameid" name="gameId" type="hidden" value="${gameId }" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" type="hidden" value="task" />
			      </form:form>
			    <form:form id="downForm" method="post" enctype="multipart/form-data">
			    </form:form>
			</div>
			
			<div class="caption">
               <button type="button" class="reflush-btn" style="width:80px;" title="点我可刷新任务列表，获取任务执行的最新状态">列表刷新</button>
               <button type="button" class="add-btn" style="width:80px;" title="点我可新建一个报表任务">新建任务</button>
            </div>
			<div id="data" class="detail-table" style="min-height:120px;">
			</div>
			<div class="result" hidden="hidden">请耐心等待查询结果...</div>
			<div id="value_data" class="detail-table" hidden="hidden">
			</div>
		</div>
	</div>
	
<div class="theme-popover">
     <div class="theme-poptit">
          <a href="javascript:;" title="关闭" class="close"><font size="5" >×</font></a>
          <h5 id="gameTypeName">新建任务</h5>
     </div>
     <div class="theme-popbod dform">
          <p class="warn-btn">
			  <button type="button" class="add-btn" style="width:80px;margin-right:10px;float: right;margin-top: -10px;">提交</button>
	       </p>
	       <div id="addTable" style="min-height:170px;">
	       </div>
	       <div id="tempDesc" class="detail-table" style="text-align: left;min-height:150px" hidden="hidden">
	       <table class="table" style="margin-top: -10px;">
	         <tr>
	            <td width="25%"><label>模板执行间隔时间：</label></td>
	            <td><span id="interveldesc"></span>分</td>
	         </tr>
	         <tr>
	            <td><label>模板说明：</label></td>
	            <td><span id="desc"></span></td>
	         </tr>
	       </table>
	       </div>
     </div>
</div>
<div class="theme-popover-mask"></div>
	
	<div hidden="hidden" class="template_cache">
	  <table class="ajax_table">
     <thead>
         <tr>
         	<td>ID</td>
         	<td>任务名称</td>
         	<!--<td>任务代码</td>  -->
         	<td style="min-width:16%">执行时间</td>
         	<td style="min-width:16%">结束时间</td>
         	<td>执行人</td>
         	<td>执行状态</td>
         	<td>记录数</td>
         	<td style="min-width:13%">操作</td>
         </tr>
      </thead>
      <tbody>
      </tbody>
   </table>
	  <table class="ajax_cache_table">
     <thead>
         <tr>
         	<td>ID</td>
         </tr>
      </thead>
      <tbody>
         <tr>
         	<td>2</td>
         </tr>
      </tbody>
   </table>
   <table class="addDataTable">
	  <tr class="model">
	     <th>选择模板：</th>
	     <td colspan="3" style="align:left;">
	         <select id="model" style="min-width:250px;">
	            <option value="-1">请选择...</option>
	         </select>
	     </td>
	  </tr>
	  <tr class="parm">
	     <th class="name"></th>
	     <td class="value"></td>
	  </tr>
   </table>
	</div>
	
</body>
</html>