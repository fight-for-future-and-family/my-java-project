<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-测试报告</title>
<link rel="stylesheet" href="/css/game/gameTestReport.css?v=${version}"/>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/games/gameEquipmentDimension.js?v=${version}"></script>
<style type="text/css">
.noneInput{
    outline:medium;
    border:0;
    background:transparent;
    color: gray;
    min-width:50px;
}
.zhibiao{
		height:350px;
		margin-top: 20px;
		float: right;
		width: 40%;
	}
.div_count_normal_li{
   text-align: left;font-size: 14px;padding-left: 30%;
}

.div_count_bold_li{
   text-align: left;
   font-size: 16px;
   font-weight:bold;
   padding-left: 30%;
   padding-bottom: 10px;
}


.div_text_normal_li{
   text-align: left;font-size: 14px;padding-left:15%;
}
.div_text_bold_li{
   text-align: left;
   font-size: 16px;
   font-weight:bold;
   padding-left: 15%;
   padding-bottom: 15px;
}

.td_left{
 text-align: left;
}

.zhibiao_pie{
		height:350px;
		margin-bottom: 30px;
		margin-top: -10px;
	}
	.zhibiao_bar{
		width:100%;
		height:350px;
		float: left;
		margin-top: 10px;
		margin-bottom: 20px;
	}
		.zhibiao_bar2{
		width:100%;
		height:350px;
		float: left;
		margin-top: 10px;
		margin-bottom: 20px;
	}
</style>
</head>
<%
   Calendar calendar = Calendar.getInstance();
   int year = calendar.get(Calendar.YEAR);
%>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_TestReport" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
				<li>测试报告</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			      <input name="gamesId" type="hidden" value="${game.id }" />
			     <%--  <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" />
			       --%>
			       <input name="snid" type="hidden" value="${snid }" />
			      <input name="gameId" type="hidden" value="${gameId }" />
			      <input id="gameRate" type="hidden" value="${game.rate }" />
			      <input id="gameCurrency" type="hidden" value="${game.currency }" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" type="hidden" value="sbwdview" />
			    <ul class="detail-nav">
			    	<li  id="all"><a href="#"></a>总体</li>
			       <!-- <li id="yhwd" ><a href="#"></a>用户维度</li> -->
			       <li id="sbwd" class="active"><a></a>设备维度</li>
			    </ul>
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    		   <span style="padding-left:20px;">
			    		      <select id="indicators" name="indicators" style="width:100px;">
			    		      	<!-- 
			    		         <option value="all" selected="selected">数据概览 </option>
			    		         <option value="zsb">注收比</option>
			    		         -->
			    		         <option value="leave">留存</option>
			    		      </select>
			    			</span>
			    			<span id="s_c_span" style="padding-left:20px;">
			    			</span>
			    			<span id="day_span">
			    			   <span class="detail-left">
				    			   <input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="${beginTime }" id="beginTime" name="beginTime"/>
				    			   <span>至</span>
				    			   <input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${endTime }" id="endTime" name="endTime" />
			    			   </span>
			    			</span>
			    		
			    			<span class="detail-right">
			    				<button id="query" class="btn btn-default btn-sm" style="width:100px;">查询</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
			 
		
			
			   
			       <div id="dau_cu_chart" class="clear-fix" style="width: 100%;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:6px;right:180px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			        <div id="dau_cu" class="zhibiao_pie"></div>
			        <div id="dau_cu_data" class="zhibiao_pie" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;"> </div>
			    </div>
			    
			   <div id="life_bar_chart" class="clear-fix" style="width: 100%;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:18px;right:80px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			       <div id="life_bar" class="zhibiao_bar"></div>
			       <div id="life_bar_data" class="zhibiao_bar" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;"> </div>
			    </div>
			    
			    
			     <div id="life_bar_chart2" class="clear-fix" style="width: 100%;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:18px;right:80px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			       <div id="life_bar2" class="zhibiao_bar2"></div>
			       <div id="life_bar_data2" class="zhibiao_bar2" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;"> </div>
			    </div>
			    
			  
			   
			    <div id="data" class="detail-table" style="margin-top: 20px"></div>
			    
			    <div id="data2" class="detail-table" style="margin-top: 20px"></div>
			    
			    <div id="data3" class="detail-table" style="margin-top: 20px"></div>
			    
			     <div id="data4" class="detail-table" style="margin-top: 20px"></div>
			</div>
		</div>
	</div>



<div hidden="hidden" class="template_cache">
   <table class="view_table">
      <thead>
        <tr>
          <td>日期</td>
          <td>激活设备</td>
          <td>D1(${game.currency })</td>
          <td>D2(${game.currency })</td>
          <td>D3(${game.currency })</td>
          <td>D4(${game.currency })</td>
          <td>D5(${game.currency })</td>
          <td>D6(${game.currency })</td>
          <td>D7(${game.currency })</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="install_date"></td>
          <td class="new_equip"></td>
          <td class="d1"></td>
          <td class="d2"></td>
          <td class="d3"></td>
          <td class="d4"></td>
          <td class="d5"></td>
          <td class="d6"></td>
          <td class="d7"></td>
        </tr>
      </tbody>
   </table>
   
    <table class="acu_table">
      <thead>
        <tr>
          <td>日期</td>
          <td>新增玩家</td>
          <td>激活设备</td>
          <td>激活注册转换率</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="ds">-</td>
           <td class="dnu"></td>
          <td class="new_equip"></td>
          <td class="jihuo_xinzeng_rate"></td>
        </tr>
      </tbody>
   </table>
   
    <table class="life_bar_table">
      <thead>
        <tr>
          <td>日期</td>
          <td>D0(${game.currency })</td>
          <td>D1(${game.currency })</td>
          <td>D3(${game.currency })</td>
          <td>D7(${game.currency })</td>
         
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="install_date">-</td>
          <td class="D0">0</td>
          <td class="D1">0</td>
          <td class="D3">0</td>
          <td class="D7">0</td>
        </tr>
      </tbody>
   </table>
   
       <table class="life_bar_table2">
      <thead>
        <tr>
          <td>日期</td>
          <td>D1(${game.currency })</td>
          <td>D3(${game.currency })</td>
          <td>D7(${game.currency })</td>
         
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="install_date">-</td>
          <td class="D1">0</td>
          <td class="D3">0</td>
          <td class="D7">0</td>
        </tr>
      </tbody>
   </table>
</div>

  <div hidden="hidden" class="template_cache2">
   <table class="view_table2">
      <thead>
      
        <tr>
         <tr>
          <td>日期</td>
          <td>激活设备</td>
          <td>D1(${game.currency })</td>
          <td>D2(${game.currency })</td>
          <td>D3(${game.currency })</td>
          <td>D4(${game.currency })</td>
          <td>D5(${game.currency })</td>
          <td>D6(${game.currency })</td>
          <td>D7(${game.currency })</td>
        </tr>
         
         
        </tr>
      </thead>
      <tbody>
        <tr>
        <td class="install_date"></td>
          <td class="new_equip"></td>
          <td class="d1"></td>
          <td class="d2"></td>
          <td class="d3"></td>
          <td class="d4"></td>
          <td class="d5"></td>
          <td class="d6"></td>
          <td class="d7"></td>
        </tr>
      </tbody>
   </table>
</div>

 <div hidden="hidden" class="template_cache3">
   <table class="view_table3">
      <thead>
      	
        <tr>
          <td>日期</td>
          <td>激活设备</td>
          <td>D1(${game.currency })</td>
          <td>D2(${game.currency })</td>
          <td>D3(${game.currency })</td>
          <td>D4(${game.currency })</td>
          <td>D5(${game.currency })</td>
          <td>D6(${game.currency })</td>
          <td>D7(${game.currency })</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="install_date"></td>
          <td class="new_equip"></td>
          <td class="d1"></td>
          <td class="d2"></td>
          <td class="d3"></td>
          <td class="d4"></td>
          <td class="d5"></td>
          <td class="d6"></td>
          <td class="d7"></td>
        </tr>
      </tbody>
   </table>
</div>

<div hidden="hidden" class="template_cache4">
   <table class="view_table4">
      <thead>
      	
        <tr>
          <td>日期</td>
          <td>激活设备</td>
          <td>D1(${game.currency })</td>
          <td>D2(${game.currency })</td>
          <td>D3(${game.currency })</td>
          <td>D4(${game.currency })</td>
          <td>D5(${game.currency })</td>
          <td>D6(${game.currency })</td>
          <td>D7(${game.currency })</td>
        </tr>
      </thead>
      <tbody>
        <tr>
         <td class="install_date"></td>
          <td class="new_equip"></td>
          <td class="d1"></td>
          <td class="d2"></td>
          <td class="d3"></td>
          <td class="d4"></td>
          <td class="d5"></td>
          <td class="d6"></td>
          <td class="d7"></td>
        </tr>
      </tbody>
   </table>
</div>
</body>
</html>