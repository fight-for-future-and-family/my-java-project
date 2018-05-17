<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="robots" content="noodp, noydir" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<title>自定义报表-模板列表</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<link rel="stylesheet" href="/css/bootstrap.min.css"/>
<link rel="stylesheet" href="/css/page.css?v=${version}">

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

.detail-li span{
 width:24%;
 float: left;
 padding-left:5px;
 padding-top:10px;
}

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

</style>
<script type="text/javascript" src="/manage/js/customModel/modelListManager.js?v=${version}"></script>

</head>
<body>
 <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
 <div class="lp-container">
  <jsp:include page="/WEB-INF/jsp/common/left_auth.jsp" >
    <jsp:param value="authli_cusManager" name="auth_page"/>
  </jsp:include>
  <div class="data-container">
   <div class="detail">
    <ol class="lp-breadcrumb">
    <li><a href="/panel_manage/toMain.ac">主页</a></li>
    <li>></li>
    <li><a href="/panel_manage/customreportModel/toCustomReportModel.ac">自定义报表任务管理</a></li>
       </ol>
        <div class="msgcss">${msg }</div>
        <div class="op_bar" style="overflow: hidden;">
       </div>
     
       <div id="data" class="detail-table">
          <button id='del-btn' class='del-btn' style='width:50px;float:right; margin-right: 50px;' type='button' title='删除此任务'>删除</button> 
       </div>
   </div>
  </div>
 </div>
 
<div hidden="hidden" class="template_cache">
	<table class="ajax_table">
		<thead>
			<tr>
			<td>ID</td>
			<td>任务名称</td>
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
</div>
</body>
</html>