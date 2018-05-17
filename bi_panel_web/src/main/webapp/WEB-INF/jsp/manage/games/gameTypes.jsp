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
<link rel="stylesheet" href="/dataTables/media/css/jquery.dataTables.min.css"/>
<link rel="stylesheet" href="/dataTables/media/css/jquery.dataTables_themeroller.css"/>
<link rel="stylesheet" href="/dataTables/media/css/jquery.dataTables.css"/>
<link rel="stylesheet" href="/dataTables/extensions/TableTools/css/dataTables.tableTools.css"/>
<link rel="stylesheet" href="/css/page.css?v=${version}">

<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/dataTables/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/dataTables/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/dataTables/extensions/TableTools/js/dataTables.tableTools.js"></script>
<script type="text/javascript" src="/dataTables/dataTables.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<script type="text/javascript" src="/manage/js/game/gameTypes.js"></script>
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
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_auth.jsp" >
		  <jsp:param value="authli_game" name="auth_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
		    	<li><a href="/panel_manage/gameManager/toGameList.ac">游戏管理</a></li>
		    	<li>></li>
		    	<li class="active green">游戏分类管理</li>
			    </ol>
			    <div class="msgcss">${msg }</div>
			</div>
			
			<div id="data" class="detail-table" style="width:99%">
			
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
         	<td>分类ID</td>
         	<td>分类名称</td>
         	<td>操作</td>
         </tr>
      </thead>
      <tbody>
      </tbody>
   </table>
	</div>
</body>
</html>