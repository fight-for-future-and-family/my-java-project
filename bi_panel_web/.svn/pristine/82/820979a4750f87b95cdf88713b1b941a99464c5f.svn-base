<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>维表管理</title>
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
	
.detail-table td {
    min-width: 90px;
}

.detail-table .short-td {
    max-width: 100px;
}

.table{
    margin-top: 30px;
}

.addDataTable_a,.updateDataTable_a{
    border-collapse: collapse !important;
	width:100%;
	margin-left: 0%;
	text-align: left;
}

.addDataTable_a>tbody>tr>td,th{
    padding:5px 5px;
	border-top: 0;
	font-weight: normal;
	font-size: 14px;
	height: 50px;
}
.addDataTable_a>tbody>tr>th{
	font-size:14px;
	font-weight: bold;
}

.updateDataTable_a>tbody>tr>td,th{
    padding:5px 5px;
	border-top: 0;
	font-weight: normal;
	font-size: 14px;
	height: 50px;
}
.updateDataTable_a>tbody>tr>th{
	font-size:14px;
	font-weight: bold;
}

.dform {
    padding: 10px 5px;
}

.warn-btn{
    height: 35px;
    text-align: left;
    padding-top: 10px; 
    border-bottom: 1px solid #ebebeb;
    overflow: hidden;
}
</style>
<script type="text/javascript" src="/js/adTracking/addShortUrl.js?v=${version}"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_ad" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
		    	<!-- <li>广告投放</li> -->
		    	<li><a class="green" href="/panel_bi/adTracking/toAdTracking.ac?gameId=${gameId}&snid=${snid}">广告投放</a></li>
		    	<li>></li>
		    	<li>批量添加</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			    <ul class="detail-nav">
			       <li id="shortUrl" class="de-first active"><a ></a>批量添加</li>
			    </ul>
			      <input name="id" type="hidden" value="${game.id }" />
			      <input name="gamesId" type="hidden" value="${game.id }" />
			      <input name="snid" type="hidden" value="${snid }" />
			      <input name="gameId" type="hidden" value="${gameId }" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" type="hidden" value="shortUrl" />
			</div>
			<div class="person-data">
				<table class="addDataTable_a">
			<c:set var="ps" value="null" />
			<tr>
				<th>投放平台:</th>
				<td><select id="platformName" class="pname">
						<option value="">请选择</option>
						<c:forEach items="${platforms }" var="p">
							<%-- <c:if test="${ps == 'null' }">
								<c:set var="ps" value="${p.code }" />
							</c:if> --%>
							<option value="${p.id}_${p.code}_${p.name }">${p.name }</option>
						</c:forEach>
				</select></td>
				<th>广告位:</th>
				<td>
					<span id="pcode" class="pcode"></span>
					<input id="adPlace" type="text" style="width: 50px" required="required">
				</td>
			</tr>
			<tr>
				<th>投放地址:</th>
				<td>
				    <input type="text" id="appUrl" required="required">
				 </td>
				<th>签名:</th>
				<td><input type="text" id="sign" required="required"></td>
			</tr>
			<tr>
				<th>签名秘钥:</th>
				<td><input type="text" id="encryptKey" required="required"></td>
				 <th>匹配规则:</th>
				<td>
				   <select id="matchRule">
						<option value="0">idfa</option>
						<option value="1">ip</option>
				   </select>
				</td>
			</tr>
			<tr>
				<th>是否有效:</th>
				<td><select id="status">
						<option value="1">有效</option>
						<!-- <option value="0">无效</option> -->
				</select></td>
				<th>是否跳转:</th>
				<td><select id="redirect">
						<option value="1">是</option>
						<option value="0">否</option>
				</select></td>
			</tr>
			<tr>
				<th>设备类型:</th>
				<td><select id="terminalType">
						<option value="1">手机</option>
						<!-- <option value="0">PC</option> -->
				</select></td>
				<th>系统类型:</th>
				<td><select id="systemType">
						<option value="0">IOS</option>
						<!-- <option value="1">ANROID</option> -->
				</select></td>
			</tr>
			<tr>
				<th>生成条数:</th>
				<td><input type="text" id="size" required="required"></td>
				<td><button type="button" id="save" class="btn btn-default btn-sm" >保存</button></td>
			</tr>
		</table>
			</div>
		</div>
	</div>
</body>
</html>