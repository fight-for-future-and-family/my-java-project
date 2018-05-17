<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-注册</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<link href="/css/chart_to_data.css?v=${version }" rel="stylesheet" type="text/css" >
<style type="text/css">
	.kong{
	    width:100%;
		height:50px;
	}
	.zhibiao{
		width:100%;
		height:350px;
		float: left;
		margin-bottom: 20px;
	}
	.zhibiao_pie{
		height:350px;
		margin-bottom: 30px;
		margin-top: -10px;
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
}
</style>
<script type="text/javascript" src="/js/games/gameInstall.js?v=${version}"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container" style="overflow: hidden;">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_user" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
		    	<li>玩家分析</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			    <ul class="detail-nav">
			       <li id="install" class="de-first active"><a ></a>新增</li>
			       <li id="dau"><a href="#"></a>活跃</li>
			       <li id="retention"><a href="#"></a>留存</li>
			       <li id="life"><a href="#"></a>注收比</li>
			    </ul>
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/gamePlayer/downloadData.ac" enctype="multipart/form-data">
			     <input name="gamesId" type="hidden" value="${game.id }" />
			     <%-- <input name="snid" type="hidden" value="${game.snid }" />
			     <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			     <input name="snid" type="hidden" value="${snid }" />
			     <input name="gameId" type="hidden" value="${gameId }" />
			     <input id="gameRate" type="hidden" value="${game.rate }" />
			      <input id="gameCurrency" type="hidden" value="${game.currency }" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			     <input id="view" name="view" type="hidden" value="install" />
			     <input id="search" type="hidden" value="" />
			     
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    		   <span style="padding-left:20px;">
			    		      <select id="indicators" name="indicators" style="width:100px;">
			    		         <option value="all" selected="selected">总览</option>
			    		         <option value="source">分渠道</option>
			    		         <option value="client">分服</option>
			    		      </select>
			    			</span>
			    			<span id="s_c_span" style="padding-left:20px;">
			    			</span>
			    			<span class="detail-left">
				    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="${beginTime }" id="beginTime" name="beginTime"/>
				    			<span>至</span>
				    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${endTime }" id="endTime" name="endTime" />
			    			</span>
			    			<span class="detail-right">
			    				<button type="button" id="query" class="btn btn-default btn-sm" style="width:80px;">查询</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
			    
			    <div id="install_sc_chart" class="clear-fix" style="width: 49%;float: left;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:28px;right:80px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			        <div id="install_sc" class="zhibiao_pie"> </div>
			        <div id="install_sc_data" class="zhibiao_pie"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;"> </div>
			    </div>
			    
			     <div id="install_sb_chart" style="width: 49%;float: right;overflow: hidden;position: relative;">
			      <div class="dataShap">
			           <ul style="top:28px;right:80px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			     <div id="install_sb" class="zhibiao_pie"></div>
			     <div id="install_sb_data" class="zhibiao_pie"
			      style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;"> </div>
			     </div>
			     
			     <div id="install_count_chart" style="width: 100%;overflow: hidden;position: relative;">
			     <div class="dataShap">
			           <ul style="top:15px;right:180px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			      <div id="install_count" class="zhibiao"></div>
			      <div id="install_count_data" class="zhibiao" 
			      style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: -10px;padding:10px 0px;"> </div>
			    </div>
			    
			    <div id="data" class="detail-table"></div>
			    <div id="ajax_data" class="detail-table" style="position: relative;" hidden="hidden"></div>
			
			</form:form>
			</div>
		</div>
	</div>

    <div hidden="hidden" class="template_cache">
   <table class="view_table">
      <thead>
        <tr>
          <td>日期</td>
          <td>新注册</td>
          <td>创角数</td>
          <td>注册付费(${game.currency })</td>
          <td>注册付费率(%)</td>
          <td>ARPU(${game.currency })</td>
          <td>ARPPU(${game.currency })</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="date">-</td>
          <td class="install">-</td>
          <td class="roleChoice">-</td>
          <td class="installPay">-</td>
          <td class="installPayRate">-</td>
          <td class="arpu">-</td>
          <td class="arppu">-</td>
        </tr>
      </tbody>
   </table>
   <table class="install_sc_table">
      <thead>
        <tr>
          <td>服务器</td>
          <td>新增人数</td>
          <td>占比</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="clienid">-</td>
          <td class="install">-</td>
          <td class="rate">-</td>
        </tr>
      </tbody>
   </table>
   <table class="install_sb_table">
      <thead>
        <tr>
          <td>渠道</td>
          <td>新增人数</td>
          <td>占比</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="source">-</td>
          <td class="install">-</td>
          <td class="rate">-</td>
        </tr>
      </tbody>
   </table>
   <table class="install_count_table">
      <thead>
        <tr>
          <td>日期</td>
          <td>新增人数</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="date">-</td>
          <td class="install">-</td>
        </tr>
      </tbody>
   </table>
   <table class="source_ajax_table">
   <caption>
          <button type="button" id="sourceAllData" class="btn btn-default btn-sm" style="width:80px;">下载数据</button>
     </caption>
      <thead>
        <tr>
          <td>日期</td>
          <td>渠道</td>
          <td>新注册</td>
          <td>创角数</td>
          <td>注册付费(${game.currency })</td>
          <td>注册付费率(%)</td>
          <td>ARPU(${game.currency })</td>
          <td>ARPPU(${game.currency })</td>
        </tr>
      </thead>
      <tbody>
      </tbody>
   </table>
   <table class="client_ajax_table">
      <caption>
           <button type="button"  id="clientAllData" class="btn btn-default btn-sm" style="width:80px;">下载数据</button>
      </caption>
      <thead>
        <tr>
          <td>日期</td>
          <td>服务器</td>
          <td>新注册</td>
          <td>创角数</td>
          <td>注册付费(${game.currency })</td>
          <td>注册付费率(%)</td>
          <td>ARPU(${game.currency })</td>
          <td>ARPPU(${game.currency })</td>
        </tr>
      </thead>
      <tbody>
      </tbody>
   </table>
</div>
  
</body>
</html>