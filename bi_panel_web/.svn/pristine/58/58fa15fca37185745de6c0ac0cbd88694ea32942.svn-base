<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>数据录入</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<style type="text/css">
#data {
  min-height: 560px;
}
#data li{
 text-align: left;
 font-size: 14px;
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
  
  #tabs{
min-height: 450px;
}   
   #tabs li{
    float: left;
    margin:15px auto; 
    width:112px; 
    border:1px solid #CCC;
    font-size:1.0em;
    line-height:30px;
    text-align: center;
   }
   #tabs a{
     padding:0 4px; color:#000;cursor: pointer;text-decoration: none;
   }
   #tabs .a-selected{
      padding:0 4px; color:#fff;cursor: pointer;text-decoration: none;
   }
   
   .ui-selected { background: #F39814; color: white; }
</style>
<script type="text/javascript" src="/js/download/gdtQuery.js?v=${version}"></script>
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
			       <li id="dataquery" class="de-first active"><a href="#"></a>数据查询</li>
			       <li id="upload"><a href="#"></a>数据上传</li>
			    </ul>
			    <form:form method="post" modelAttribute="gamesVO" action="/panel_bi/tool/download_data_templete.ac" enctype="multipart/form-data">
			      <input name="id" type="hidden" value="${game.id }" />
			      <input name="gamesId" type="hidden" value="${game.id }" />
			      <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" />
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
				    			
				    			  <span style="margin-left:20px;">
				    			     <input type="text" class="b-radius5 p-input" style="padding-left:10px;width:105px;"  value="${beginTime }" id="beginTime" name="beginTime"/>
				    			     <span>至</span>
				    			     <input type="text" class="b-radius5 p-input" style="padding-left:10px;width:105px;" value="${endTime }" id="endTime" name="endTime" />
			    			      </span>
			    			    
			    			   </div>
			    			</div>
			    			<div class="detail-right" style="width:28%">
			    				<button type="button" id="query" class="btn btn-default btn-sm" style="margin-right:10px;width:80px;float: right">查询</button>
			    			</div>
			    		</li>
			    	</ul>
			    </div>
			    </form:form>
			</div>
			
			<div id="data">
			  <div id="tabs" hidden="hidden">
				<ul>
					<li css="tabs" val="summary"  class="ui-selected">广点通-汇总</li>
					<li css="tabs" val="analysis">广点通-分析</li>
				</ul>
				<div id="summary">
					<div id="data_summary" class="detail-table" style="margin-top: 20px"></div>
				</div>
				<div id="analysis">
					<div id="data_analysis" class="detail-table" style="margin-top: 20px"></div>
				</div>
				<div class='ajax_loading' style='background:#F0F0F0;height:60px;width:100%;text-align:center;line-height:60px;font-size:18px;display:none;position:fixed;bottom:0px'>
					 数据加载中
				</div>
		    </div>
		    <div id="marketData" hidden="hidden">
		         
		    </div>
			</div>
		</div>

		<div hidden="hidden" class="template_cache">
			<table class="gdt_summary_tab">
				<thead>
					<tr>								
						<td>投放时间</td>
						<td>曝光量</td>
						<td>点击量</td>
						<td>点击率</td>
						<td>安装量</td>
						<td>转化率</td>
						<td>花费(元)</td>
						<td>当日成本(元)</td>
						<td>CPC(元)</td>
						<td>CPA(元)</td>
					</tr>
				</thead>
                <tbody>
                    <tr>
                        <td class="date"></td>
                        <td class="exposure"></td>
                        <td class="clickCount"></td>
                        <td class="clickRate"></td>
                        <td class="install"></td>
                        <td class="conversionRate"></td>
                        <td class="allCost"></td>
                        <td class="d0Cost"></td>
                        <td class="cpc"></td>
                        <td class="cpa"></td>
                    </tr>
                </tbody>
			</table>
			<table class="gdt_analysis_tab">
				<thead>
					<tr>								
						<td rowspan="2">日期</td>
						<td rowspan="2">广告安装量</td>
						<td rowspan="2">广告费（元）</td>
						<td rowspan="2">付费人数（当日）</td>
						<td rowspan="2">安装付费率</td>
						<td colspan="5">广告用户引入收入(元)-未考虑分成</td>
						<td colspan="5">广告ROI（未考虑分成）</td>
					</tr>
					<tr>								
						<td>当日</td>
						<td>1日</td>
						<td>3日</td>
						<td>7日</td>
						<td>30日</td>
						<td>当日</td>
						<td>1日</td>
						<td>3日</td>
						<td>7日</td>
						<td>30日</td>
					</tr>
				</thead>
                <tbody>
                    <tr>
                        <td class="date"></td>
                        <td class="advInstall"></td>
                        <td class="advCost"></td>
                        <td class="d0Payuser"></td>
                        <td class="payRate"></td>
                        <td class="d0Payment"></td>
                        <td class="d1Payment"></td>
                        <td class="d3Payment"></td>
                        <td class="d7Payment"></td>
                        <td class="d30Payment"></td>
                        <td class="d0Roi"></td>
                        <td class="d1Roi"></td>
                        <td class="d3Roi"></td>
                        <td class="d7Roi"></td>
                        <td class="d30Roi"></td>
                    </tr>
                </tbody>
			</table>
			<table class="market_tab">
				<thead>
					<tr>								
						<td>投放时间</td>
						<td>曝光量</td>
						<td>点击量</td>
						<td>点击率</td>
						<td>安装量</td>
						<td>转化率</td>
						<td>花费(元)</td>
						<td>当日成本(元)</td>
						<td>CPC(元)</td>
						<td>CPA(元)</td>
					</tr>
				</thead>
                <tbody>
                    <tr>
                        <td class="date"></td>
                        <td class="exposure"></td>
                        <td class="clickCount"></td>
                        <td class="clickRate"></td>
                        <td class="install"></td>
                        <td class="conversionRate"></td>
                        <td class="allCost"></td>
                        <td class="d0Cost"></td>
                        <td class="cpc"></td>
                        <td class="cpa"></td>
                    </tr>
                </tbody>
			</table>
		</div>

	</div>
</body>
</html>