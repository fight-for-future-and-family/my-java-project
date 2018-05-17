<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>平台管理</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<style type="text/css">
#data {
  min-height: 560px;
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
<script type="text/javascript" src="/manage/js/adTracking/platforms.js?v=${version}"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_auth.jsp" >
		   <jsp:param value="authli_plat" name="auth_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a  href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
		    	<li class="green">平台管理</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			      <input name="id" type="hidden" value="${game.id }" />
			      <input name="gamesId" type="hidden" value="${game.id }" />
			      <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" type="hidden" value="platforms" />
			</div>
			
			<div id="caption" style="float:right;padding-right: 20px;padding-bottom: 10px;margin-top: -20px">
	          <button type="button" class="add-btn btn btn-default btn-sm" style="width:80px;">添加数据</button>
	          <button type="button" class="query-btn btn btn-default btn-sm" style="width:80px;">刷新</button>
    		</div>
			<div id="data" class="detail-table">
			</div>
		</div>
	</div>
	<div hidden="hidden" class="template_cache">
	  <table class="ajax_table">
          <caption style="height:30px"></caption>
     <thead>
         <tr>
         	<td>ID</td>
         	<td>平台名称</td>
         	<td>平台编码</td>
         	<td>是否有效</td>
         	<td>操作</td>
         </tr>
      </thead>
      <tbody>
      </tbody>
   </table>
   
   <select class="ajsx_sel">
      <option value="1">有效</option>
      <option value="0">无效</option>
   </select>
   
   <table class="addDataTable">
      <tr role="row" class="even">
	   <td><input type="text"></td>
	   <td><input type="text"></td>
	   <td>
		   <select>
		      <option value="1">有效</option>
		      <option value="0">无效</option>
		   </select>
      </td>
	   <td>
		   <button class="add-save-btn btn btn-default btn-sm" type="button">保存</button>
		   <button class="del-btn btn btn-default btn-sm" type="button">删除</button>
	   </td>
   </tr>
   </table>
   
	</div>
</body>
</html>