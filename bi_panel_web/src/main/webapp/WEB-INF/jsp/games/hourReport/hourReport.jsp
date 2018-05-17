<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-实时</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/games/hourReport.js?v=${version}"></script>
<style type="text/css">
 .data_table {
	width: 100%;
	margin-top: 20px;
	overflow:hidden;
}

.zhibiao {
	width: 12.5%;
	height: 100px;
	float: left;
	margin-top: 20px;
	border-left: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-radius: 10px;
}

.zhibiao_left {
	width: 12.5%;
	height: 100px;
	float: left;
	margin-top: 20px;
	border-left: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-radius: 10px;
}

.zhibiao_right {
	width: 12.5%;
	height: 100px;
	float: left;
	margin-top: 20px;
	border-radius: 10px;
	border: 1px solid #ebebeb;
}

.data_ul li {
	padding-top: 10px;
}

.data_ul title {
	font-size: 20px;
	text-align: center;
}

.highlight {
    background-color: #99f21b !important;
}

.detail-table td{
	min-width: 150px; 
}

.dataTables_wrapper .dataTables_filter {
	  margin-left:10px;
      z-index:1;
      position:absolute;
     }
     caption {
        font-style: normal;
        text-align: left;
        margin-left: 38%;
	}
	
	.detail-info-zbsm{
	margin-top: 40px;
	border:1px solid #cacaca;
}
dl {
	display: none;
}

.dd{
   margin-top: 8px;
	width: 1000px;
}
.a{
	background:#5cb95e;
	border-radius:2px;
	color: white;
	display:inlie-block; 
	width:2000px;
}
.Thefont{
	font-weight:bold;
}
.detail-right-up-down{
	width:40px;
    height: 120px;
    float: right;
    
}

.detail-right2{
	display: inline-block;
	height: 30px;
	width: 80px;
	float: right;
	text-align: right;
	margin-top:6px;
}
  </style>
</head>

<body class="dt-example">
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<div class="lp-container">
        <jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_realTime" name="game_page"/>
		</jsp:include>
	<div class="data-container" style="min-height:800px;">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name}</li>
				<li>></li>
		    	<li>实时</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			      
			    <ul class="detail-nav">
			       <li id="realTime"><a href="#"></a>实时</li>
			       <li id="forecastHourReport"><a href="#"></a>按小时</li>
			       <li class="active" id="hourReport"><a href="#"></a>实时日报</li>
			       <li id="hourReportSourceLtv"><a href="#"></a>分渠道注收比</li>
			        <li id="hourReportSourceRetention"><a href="#"></a>分渠道留存</li>
			    </ul>
			    
			    			
			   
			     <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/hourReport/downloadData.ac" enctype="multipart/form-data">
			     <input name="gamesId" type="hidden" value="${game.id }" />
			      <%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input name="snid" type="hidden" value="${snid }" />
			      <input name="gameId" type="hidden" value="${gameId }" />
			      <input id="view" name="view" type="hidden" value="hourReport" />
			      <input id="gameRate" name="gameRate" type="hidden" value="${game.rate }" />
			      <input id="gameCurrency" type="hidden" value="${game.currency }" />
			      <input id="timeZone" name="timeZone" type="hidden" value="${game.timeZone }" />
			      
			      
	    		
			
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    		  <span style="padding-left:20px;">
			    		      <select id="channel" name="channel" style="width:100px;">
			    		         <option value="all" selected="selected">总览</option>
			    		         <option value="source">分渠道</option>
			    		         <option value="client">分服</option>
			    		      </select>
			    			</span>
			    			<span id="s_c_span" style="padding-left:20px;"></span>
			    			<span class="detail-left">
			    			<span style="font-size:12px;font-weight:bold;">计算时间：</span>
			    			 <span id="date_rt" style="width:88px;font-size:12px;font-weight:bold;color:red;">
			    			 </span>
			    			</span>
			    			 <span class="detail-right2" >
			    				 <button type="button" id="zbsm" class="btn btn-default btn-sm">指标说明</button>
			    			</span>
			    			<span class="detail-right2" >
			    				<button type="button" id="redone"  style="width:80px;" class="btn btn-default btn-sm" >重算</button>
			    			</span>
			    			<span class="detail-right2" style="margin-right:10px;">
			    				<button type="button" id="query"   style="width:80px;" class="btn btn-default btn-sm" >刷新</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
			   
			    <div class="detail-info-zbsm" id="zbsm-info" style="height: 120px;">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    			<span class="detail-left">
			    				<dl class="explain"><dd class="dd"><span class="a">功能概述</span> <span class="Thefont">各项常规指标的实时统计情况。</span></dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">累计新注册用户数(绝对新增):</span>实时统计当日新增玩家账号的数量，不含滚服注册的账号数目。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">最高在线人数:</span>实时统计当日同一时间玩家在线的最高人数。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">平均在线人数:</span>实时统计当日每一时间段的玩家平均在线人数。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">平均在线时长(分钟):</span> 实时统计当日玩家平均每次进行游戏的时间。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">累计活跃用户数:</span>实时统计当日所有有进行过登陆行为的玩家账号数量。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">累计付费(${game.currency }):</span>实时统计当日游戏总收入金额。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">平均ARPU(${game.currency }):</span> 每用户平均收入，指当天时间内，游戏能从玩家身上获取收益的能力，衡量盈利能力的指标，计算公式为：ARPU=累计付费/累计活跃用户数。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">平均ARPPU(${game.currency }):</span> 每付费用户平均收入，指当天时间内，游戏能从付费玩家身上获取收益的能力，ARPPU=累计付费/付费人数。</dd></dl>
						    	
						    	
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">激活设备数:</span>实时统计当日激活的设备数量，如果已安装的游戏激活标识被移除的话，则设备激活不会被去重。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">老用户:</span>实时统计当日有登陆过游戏的老玩家的账号数量。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">创角:</span> 实时统计当日新创建角色的玩家账号数量。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">创角率:</span> 实时统计当日新创建角色玩家数在新注册玩家数中的占比，创角率=创角/新注册。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">次日留存:</span> 实时统计当日成功登陆游戏的新增玩家中，第二日再次登陆游戏的玩家数量，占当日游戏新增玩家数量的比例，次日留存=昨日注册玩家中今日活跃的人数/昨日新注册数。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">付费人数:</span> 实时统计当日成功充值的玩家数量，去重。</dd></dl>
						    	
						    	
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">付费次数:</span>实时统计当日玩家成功充值总次数。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">新付费用户数:</span>实时统计当前进行第一次付费的玩家数量。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">新付费金额:</span> 实时统计当前进行第一次付费玩家的付费金额。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">注册付费人数:</span> 实时统计当日新注册玩家中付费的玩家数量。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">注册付费金额:</span>实时统计当日新注册玩家中付费玩家的付费金额。</dd></dl>
						    	<dl class="explain"><dd class="dd"><span class="a">实时指标</span> <span class="Thefont">新注册付费率:</span>实时统计当日新注册玩家中付费的玩家在当日新注册玩家中的比例，新注册付费率=注册付费人数/新注册。</dd></dl>
						    <!-- 	<dl class="explain"><dd>5</dd></dl>
						    	<dl class="explain"><dd>6</dd></dl>
						    	<dl class="explain"><dd>7</dd></dl>
						    	<dl class="explain"><dd>8</dd></dl>
						    	<dl class="explain"><dd>9</dd></dl> -->
					    	</span>
					    	<span class="detail-right-up-down" >
					    		<input id="pageNo" type="hidden" value="1">
					    		<img src="/images/close.png" id="close"/>
					    		<button id="up" type="button" class="btn btn-default btn-sm" style="width:30px; height:30px; margin-top: 30px;"><img src="/images/up2.png" style="width:10px; height:10px;"/></button>
					    		<button id="down" type="button" class="btn btn-default btn-sm" style="width:30px; height:30px;"><img src="/images/down2.png" style="width:10px; height:10px;"/></button>
					    	</span>
				    	</li>
			    	</ul>
			    </div>
			   
			   <div class="data_table">
			       <div id="install_count" class="zhibiao_left">
			          <ul class="data_ul">
			            <li style="text-align: center;font-size: 14px;">累计新注册用户数<br/>(绝对新增)</li>
			            <li id="c_ul_installCount" style="text-align: center;font-size: 16px;font-weight:bold;">0</li></ul>
			       </div>
			       <div id="pcu_count" class="zhibiao">
			          <ul class="data_ul">
			            <li style="text-align: center;font-size: 14px;">最高在线人数</li>
			            <li id="c_ul_pcu" style="text-align: center;font-size: 16px;font-weight:bold;">0</li>
			          </ul>
			       </div>
			       <div id="acu_count" class="zhibiao">
			          <ul class="data_ul">
			            <li style="text-align: center;font-size: 14px;">平均在线人数</li>
			            <li id="c_ul_acu" style="text-align: center;font-size: 16px;font-weight:bold;">0</li>
			          </ul>
			       </div>
			       <div id="avgUserTime_count" class="zhibiao">
			          <ul class="data_ul">
			            <li style="text-align: center;font-size: 14px;">平均在线时长(分钟)</li>
			            <li id="c_ul_avgUserTime" style="text-align: center;font-size: 16px;font-weight:bold;">0</li>
			          </ul>
			       </div>
			       <div id="dau_count" class="zhibiao">
			          <ul class="data_ul">
			            <li style="text-align: center;font-size: 14px;">累计活跃用户数</li>
			            <li id="c_ul_dauCount" style="text-align: center;font-size: 16px;font-weight:bold;">0</li>
			          </ul>
			       </div>
			       <div id="pay_count" class="zhibiao">
			          <ul class="data_ul">
			            <li style="text-align: center;font-size: 14px;">累计付费(${game.currency })</li>
			            <li id="c_ul_payCount" style="text-align: center;font-size: 16px;font-weight:bold;">0</li>
			          </ul>
			       </div>
			       <div id="arpu_count" class="zhibiao">
			          <ul class="data_ul">
			            <li style="text-align: center;font-size: 14px;">平均ARPU(${game.currency })</li>
			            <li id="c_ul_avgARPU" style="text-align: center;font-size: 16px;font-weight:bold;">0</li>
			          </ul>
			       </div>
			       <div id="arppu_count" class="zhibiao_right">
			          <ul class="data_ul">
			            <li style="text-align: center;font-size: 14px;">平均ARPPU(${game.currency })</li>
			            <li id="c_ul_avgARPPU" style="text-align: center;font-size: 16px;font-weight:bold;">0</li>
			          </ul>
			       </div>
			    </div>
					   <div id="data" class="detail-table" style="margin-top: 20px"> </div>
					   <div id="ajax_data" class="detail-table" style="position: relative;margin-top: 20px" hidden="hidden"> </div>
					   <div class='ajax_loading' hidden="hidden" style='height:60px;width:100%;text-align:center;line-height:60px;font-size:16px;display:none;'>
					    <span class="ajax_loading_span">数据实时计算中,请等待大约<span style="padding: 0px 5px;color: #F13D6D">10</span>分钟</span>
					    <img class="ajax_loading_img" src="/images/loading.gif"></img>
					    </div>
					    </form:form>
</div>
</div>
<div hidden="hidden" class="template_cache">
			<table class="all_daily">
				<thead>
					<tr>
						<td>日期</td>
						<td>时间</td>
						<td>新注册</td>
						<td>激活设备数</td>
						<td>活跃</td>
						<td>老用户</td>
						<td>最高在线人数</td>
						<td>平均在线人数</td>
						<td>平均在线时长(分钟)</td>
						<td>创角</td>
						<td>创角率</td>
						<td>次日留存</td>
						<td>付费人数</td>
						<td>付费额(${game.currency })</td>
						<td>付费次数</td>
						<td>付费率</td>
						<td>新注册arpu(${game.currency })</td>
						<td>arpu(${game.currency })</td>
						<td>arppu(${game.currency })</td>
						<td>新付费用户数</td>
						<td>新付费金额(${game.currency })</td>
						<td>注册付费人数</td>
						<td>注册付费金额(${game.currency })</td>
						<td>新注册付费率</td>
					</tr>
				</thead>
				<tbody>
                    <tr>
						<td class="day"></td>
						<td class="hour"></td>
						<td class="dnu"></td>
						<td class="newEquip"></td>
						<td class="dau"></td>
						<td class="loyalUser"></td>
						<td class="pcu"></td>
						<td class="acu"></td>
						<td class="avgUserTime"></td>
						<td class="roleChoice"></td>
						<td class="roleChoiceRate"></td>
						<td class="d1"></td>
						<td class="pu"></td>
						<td class="payment"></td>
						<td class="paymentTimes"></td>
						<td class="payRate"></td>
						<td class="dnuArpu"></td>
						<td class="arpu"></td>
						<td class="arppu"></td>
						<td class="newPu"></td>
						<td class="newPayment"></td>
						<td class="installPu"></td>
						<td class="installPayment"></td>
						<td class="installPayRate"></td>
					</tr>
				</tbody>
			</table>
			<table class="source_daily">
				<thead>
					<tr>
						<td>日期</td>
						<td>时间</td>
						<td>渠道</td>
						<td>新注册</td>
						<td>激活设备数</td>
						<td>活跃</td>
						<td>老用户</td>
						<td>最高在线人数</td>
						<td>平均在线人数</td>
						<td>平均在线时长(分钟)</td>
						<td>次日留存</td>
						<td>付费人数</td>
						<td>付费额(${game.currency })</td>
						<td>付费次数</td>
						<td>付费率</td>
						<td>新注册arpu(${game.currency })</td>
						<td>arpu(${game.currency })</td>
						<td>arppu(${game.currency })</td>
						<td>新付费用户数</td>
						<td>新付费金额(${game.currency })</td>
						<td>注册付费人数</td>
						<td>注册付费金额(${game.currency })</td>
						<td>新注册付费率</td>
					</tr>
				</thead>
				<tbody>
                    <tr>
						<td class="day"></td>
						<td class="hour"></td>
						<td class="source"></td>
						<td class="dnu"></td>
						<td class="newEquip"></td>
						<td class="dau"></td>
						<td class="loyalUser"></td>
						<td class="pcu"></td>
						<td class="acu"></td>
						<td class="avgUserTime"></td>
						<td class="d1"></td>
						<td class="pu"></td>
						<td class="payment"></td>
						<td class="paymentTimes"></td>
						<td class="payRate"></td>
						<td class="dnuArpu"></td>
						<td class="arpu"></td>
						<td class="arppu"></td>
						<td class="newPu"></td>
						<td class="newPayment"></td>
						<td class="installPu"></td>
						<td class="installPayment"></td>
						<td class="installPayRate"></td>
					</tr>
				</tbody>
			</table>
			<table class="client_daily">
				<thead>
					<tr>
						<td>日期</td>
						<td>时间</td>
						<td>服务器ID</td>
						<td>新注册</td>
						<td>活跃</td>
						<td>老用户</td>
						<td>最高在线人数</td>
						<td>平均在线人数</td>
						<td>平均在线时长(分钟)</td>
						<!-- <td>次日留存</td> -->
						<td>付费人数</td>
						<td>付费额(${game.currency })</td>
						<td>交易笔数</td>
						<td>付费率</td>
						<td>arpu(${game.currency })</td>
						<td>arppu(${game.currency })</td>
						<%-- <td>注册付费人数</td>
						<td>注册付费金额(${game.currency })</td> --%>
						<%-- <td>滚服付费金额(${game.currency })</td> --%>
					</tr>
				</thead>
				<tbody>
                    <tr>
						<td class="day"></td>
						<td class="hour"></td>
						<td class="clientid"></td>
						<td class="dnu"></td>
						<td class="dau"></td>
						<td class="loyalUser"></td>
						<td class="pcu"></td>
						<td class="acu"></td>
						<td class="avgUserTime"></td>
						<!-- <td class="d1"></td> -->
						<td class="pu"></td>
						<td class="payment"></td>
						<td class="paymentTimes"></td>
						<td class="payRate"></td>
						<td class="arpu"></td>
						<td class="arppu"></td>
						<!-- <td class="installPu"></td>
						<td class="installPayment"></td> -->
					</tr>
				</tbody>
			</table>
			<table class="source_ajax_daily">
			<caption>
                <button type="button" class="btn btn-default btn-sm" style="width:100px;">下载数据</button>
              </caption>
				<thead>
					<tr>
						<td>日期</td>
						<td>时间</td>
						<td>渠道</td>
						<td>新注册</td>
						<td>激活设备数</td>
						<td>活跃</td>
						<td>老用户</td>
						<td>最高在线人数</td>
						<td>平均在线人数</td>
						<td>平均在线时长(分钟)</td>
						<td>次日留存</td>
						<td>付费人数</td>
						<td>付费额(${game.currency })</td>
						<td>付费次数</td>
						<td>付费率</td>
						<td>新注册arpu(${game.currency })</td>
						<td>arpu(${game.currency })</td>
						<td>arppu(${game.currency })</td>
						<td>新付费用户数</td>
						<td>新付费金额(${game.currency })</td>
						<td>注册付费人数</td>
						<td>注册付费金额(${game.currency })</td>
						<td>新注册付费率</td>
					</tr>
				</thead>
				<tbody>
                    <tr>
					</tr>
				</tbody>
			</table>
			<table class="client_ajax_daily">
			<caption>
                <button type="button" class="btn btn-default btn-sm" style="width:100px;">下载数据</button>
              </caption>
				<thead>
					<tr>
						<td>日期</td>
						<td>时间</td>
						<td>服务器ID</td>
						<td>新注册</td>
						<td>活跃</td>
						<td>老用户</td>
						<td>最高在线人数</td>
						<td>平均在线人数</td>
						<td>平均在线时长(分钟)</td>
						<!-- <td>次日留存</td> -->
						<td>付费人数</td>
						<td>付费额(${game.currency })</td>
						<td>交易笔数</td>
						<td>付费率</td>
						<td>arpu(${game.currency })</td>
						<td>arppu(${game.currency })</td>
						<%-- <td>注册付费人数</td>
						<td>注册付费金额(${game.currency })</td> --%>
						<%-- <td>滚服付费金额(${game.currency })</td> --%>
					</tr>
				</thead>
				<tbody>
                    <tr>
					</tr>
				</tbody>
			</table>
		</div>
</div>


</body>
</html>