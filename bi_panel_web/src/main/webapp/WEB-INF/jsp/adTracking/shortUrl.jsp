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
<script type="text/javascript" src="/js/adTracking/adShortUrl.js?v=${version}"></script>
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
		    	<li>广告投放</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			    <ul class="detail-nav">
			       <li id="shortUrl" class="de-first active"><a ></a>投放管理</li>
			    </ul>
			    <form:form id="adForm" name="adForm" method="post" modelAttribute="gamesVO" action="/panel_bi/adTracking/download_data.ac" enctype="multipart/form-data">
			      <input name="id" type="hidden" value="${game.id }" />
			      <input name="gamesId" type="hidden" value="${game.id }" />
			      <%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input name="snid" type="hidden" value="${snid }" />
			      <input name="gameId" type="hidden" value="${gameId }" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" type="hidden" value="shortUrl" />
			      </form:form>
			</div>
			
			<div class="caption">
               <button type="button" class="add-btn btn btn-default btn-sm" style="width:80px;">添加数据</button>
               <button type="button" class="batch-add-btn btn btn-default btn-sm" style="width:80px;">批量添加</button>
               <button type="button" class="queryInstall-btn btn btn-default btn-sm" style="width:80px;">查询激活</button>
               <button type="button" class="query-btn btn btn-default btn-sm" style="width:80px;">刷新</button>
            </div>
			<div id="data" class="detail-table">
			</div>
		</div>
	</div>
	
	 <div class="theme-popover" style="height:380px;width:700px;">
     <div class="theme-poptit">
          <a href="javascript:;" title="关闭" class="close"><font size="5" >×</font></a>
          <h5 id="gameTypeName">添加平台投放</h5>
     </div>
     <div class="theme-popbod dform">
          <div class="warn-btn">
              <div id="qi" hidden="hidden" style="margin-left:10px;float: left;margin-top: -10px;width:85%" >
                 <label>短链ID:</label><span style="margin-left:10px;"><input id="suid" type="text" style="width:15%;"/></span>
                 <label style="margin-left:10px;">IDFA:</label><span style="margin-left:10px;">
                 <input id="qidfa" type="text" style="width:60%;" placeholder="最多填写10个idfa,格式：idfa,idfa1,idfa3" /></span>
              </div>
			  <button type="button" class="add-btn btn btn-default btn-sm" style="width:80px;margin-right:10px;float: right;margin-top: -10px;">添加数据</button>
	       </div>
	       <div id="addTable">
	       </div>
     </div>
</div>
<div class="theme-popover-mask"></div>
	
	<div hidden="hidden" class="template_cache">
	  <table class="ajax_table">
     <thead>
         <tr>
         	<td>ID</td>
         	<td>投放平台</td>
         	<td>广告位</td>
         	<td>短链接</td>
         	<td class="short-td">匹配规则</td>
         	<td class="short-td">是否跳转</td>
         	<td>投放地址</td>
            <td>签名</td>
         	<td class="short-td">是否有效</td>
            <td>签名秘钥</td>
         	<td class="short-td">设备类型</td>
         	<td class="short-td">系统类型</td>
         	<td style="min-width:180px;">操作</td>
         </tr>
      </thead>
      <tbody>
      </tbody>
   </table>
		<table class="addDataTable_a">
			<c:set var="ps" value="null" />
			<tr>
				<th>投放平台:</th>
				<td><select id="platformName" class="pname">
						<c:forEach items="${platforms }" var="p">
							<c:if test="${ps == 'null' }">
								<c:set var="ps" value="${p.code }" />
							</c:if>
							<option value="${p.id}_${p.code}_${p.name }">${p.name }</option>
						</c:forEach>
				</select></td>
				<th>广告位:</th>
				<td>
					<span class="pcode">${ps }_</span>
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
						<option value="0">无效</option>
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
						<option value="0">PC</option>
				</select></td>
				<th>系统类型:</th>
				<td><select id="systemType">
						<option value="0">IOS</option>
						<option value="1">ANROID</option>
				</select></td>
			</tr>
		</table>
		<table class="updateDataTable_a">
			<c:set var="ps" value="null" />
			<tr>
				<th>投放平台:</th>
				<td id="platformModify"></td>
				<th>广告位:</th>
				<td><span id="pcode"></span><input id="adPlace"
					type="text" style="width: 50px"></td>
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
						<!-- <option value="0">PC</option> -->
						<option value="1">手机</option>
				</select></td>
				<th>系统类型:</th>
				<td><select id="systemType">
						<option value="0">IOS</option>
						<!-- <option value="-1">默认</option>
						<option value="1">ANROID</option> -->
				</select></td>
			</tr>
		</table>
		
		<table class="queryInstallTemp">
			<thead>
				<tr>
					<td>idfa</td>
					<td>点击时间</td>
					<td>激活时间</td>
					<td>回调时间</td>
					<td>回调状态</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="idfa"></td>
					<td class="actTime"></td>
					<td class="installTime"></td>
					<td class="callBackTime"></td>
					<td class="callBackStatus"></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>