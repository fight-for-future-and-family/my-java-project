<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-设备分析</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/equip/equipDau.js?v=${version}"></script>
<link rel="stylesheet" href="/css/game/gameView.css?v=${version}"/>
<link href="/css/chart_to_data.css?v=${version }" rel="stylesheet" type="text/css" >
<style type="text/css">
.dataTables_wrapper .dataTables_filter {
	margin-left: 10px;
	z-index: 1;
	top: -40px;
	position: absolute;
}

.data_table {
	width: 100%;
	margin-top: 10px;
	margin-left: 0%;
}

#caption {
	font-style: normal;
	text-align: left;
	top: 5px;
	margin-right: 20px;
	float: right;
}

#caption span.ui-selected {
	background: #56C887;
	color: white;
}

#caption span.last {
	border-right: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
}

#caption span {
	float: left;
	margin: 5px auto;
	width: 85px;
	text-align: center;
	font-size: 14px;
	line-height: 27px;
	border-left: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
}

#equipDesc {
  min-height: 90px;
  padding-top: 5px;
}
#equipDesc li{
 text-align: left;
 font-size: 12px;
 margin-top: 10px;
}
#equipDesc li div{
 width: 50%;
 float: left;
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
.zhibiao_pie{
	    width:100%;
		height:360px;
		float: left;
		margin-top: 20px;
		margin-bottom: 20px;
	}
	.zhibiao_pie_data{
	    width:50%;
		height:360px;
		float: left;
		margin-top: 20px;
		margin-bottom: 20px;
	}
	.zhibiao_pie_tab{
	    width:32%;
		height:320px;
		float: left;
		margin-top: 20px;
		margin-bottom: 20px;
	}

</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_equip" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
					<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
					<li>></li>
					<li>${game.name }</li>
					<li>></li>
			    	<li>设备分析</li>
			    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
				     
			    <ul class="detail-nav">
			       <li id="equipDau" class="de-first active">设备活跃</li>
			       <li id="versionDau"><a></a>版本活跃分布</li>
			       <li id="installRetention"><a></a>新增设备留存</li>
			       <!--  <li id="stepChange"><a></a>前期步骤转换</li>-->
			    </ul>
			    
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/equip/downloadData.ac" enctype="multipart/form-data">
					<input name="gamesId" type="hidden" value="${game.id }" />
					<%-- <input name="snid" type="hidden" value="${game.snid }" />
					<input name="gameId" type="hidden" value="${game.gameid }" /> --%>
					<input name="snid" type="hidden" value="${snid }" />
					<input name="gameId" type="hidden" value="${gameId }" />
					<input id="gameRate" type="hidden" value="${game.rate }" />
					<input id="gameCurrency" type="hidden" value="${game.currency }" />
					<input id="timeZone" type="hidden" value="${game.timeZone }" />
					<input id="view" name="view" type="hidden" value="economyItem" />
					<input id="queryType" name="queryType" type="hidden" value="amount" />
					<div class="detail-info">
				    	<ul class="detail-ul">
				    		<li class="detail-li">
				    		<span class="detail-left" style="width: 80%">
				    		   <span>
				    		      <select id="indicators" name="indicators" style="width:100px;">
				    		         <option value="all" selected="selected">总览</option>
				    		         <option value="source">分渠道</option>
				    		         <option value="model">分机型</option>
				    		      </select>
				    			</span>
				    			<span id="s_c_span" style="padding-left:20px;"></span>
				    			<span style="padding-left:20px;">
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="${beginTime }" id="beginTime" name="beginTime"/>
					    			<span>至</span>
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${endTime }" id="endTime" name="endTime" />
				    			</span>
				    			</span>
				    			<span class="detail-right" style="width: 15%">
				    				<button id="query" type="button" class="btn btn-default btn-sm" style="width:80px;">查询</button>
				    			</span>
				    		</li>
				    	</ul>
				    </div>
				    <div id="desc" style="width:100%;clear: both;text-align: left;padding: 10px 0px 0px 0px;">
				      <img id="descImg" alt="说明小箭头" src="/images/down.png" style="width:2%;height:2%">
				       <font style="font-size: 12px"><span id="descText">展开说明</span></font>
				    </div>
				    <div id="equipDesc" class="detail-table" hidden="hidden">
				    <ul>
		        	 <li>
		        	 	<div><label>活跃设备数</label>：当天在终端设备上启动该应用的设备总数。</div>
		        		<div><label>激活设备数</label>：当天在终端设备上第一次启动该应用的设备总数。</div></li>
					 <li>
					 	<div><label>活跃老设备</label>：当天在终端设备上启动但非第一次启动该应用的设备总数。</div>
					 	<div><label>激活转化率</label>：当天激活且注册设备数/当天激活设备数。</div>
					 </li>
					 <li>
					 	<div><label>卸载设备数</label>：当天在终端设备上卸载该应用的设备数。</div>
					 	<div><label>设备留存率</label>：回访设备数/首日注册设备数</div>
					 </li>
		            </ul>
				    </div>
				    
				     <div id="tu" style="overflow: hidden;"></div>
				     <div id="pirtu">
			         </div>
			         <div id="sourceTu">
			        </div>
			      
			   		 <div id="data" class="detail-table">
			   		   <div id="caption">
		   					<button type="button"  class="btn btn-default btn-sm" style="width:80px;margin-left: 10px;margin-top: 5px;">下载数据</button>
                       </div>
                       
			   		 </div>
			    </form:form>
			</div>
		</div>
	</div>
	<div hidden="hidden" class="template_cache">
		 <table class="view_table" style="overflow: hidden;">
	      <thead>
	      <tr>
	      	<td>日期</td>
	      	<td>活跃设备</td>
	      	<td>激活设备</td>
	      	<td>激活且注册设备</td>
	      	<td>活跃老设备</td>
	      	<td>激活率</td>
	      	<td>卸载设备</td>
	      </tr>
	      </thead>
          <tbody>
          <tr>
	      	<td class="ds"></td>
	      	<td class="dau"></td>
	      	<td class="newEquip"></td>
	      	<td class="install"></td>
	      	<td class="olddau"></td>
	      	<td class="installRate"></td>
	      	<td class="uninstall"></td>
	      </tr>
          </tbody>
	   </table>
		 <table class="view_table_1" style="overflow: hidden;">
	      <thead>
	      <tr>
	      	<td>日期</td>
	      	<td>渠道</td>
	      	<td>活跃设备</td>
	      	<td>激活设备</td>
	      	<td>激活且注册设备</td>
	      	<td>活跃老设备</td>
	      	<td>激活率</td>
	      	<td>卸载设备</td>
	      </tr>
	      </thead>
          <tbody></tbody>
	   </table>
	   
	   <div class="tongjitu">
          <ul class="data_ul">
            <li class="div_count_normal_li name">新注册用户数</li>
            <li class="div_count_bold_li count"></li>
            <li class="div_count_normal_li yestoday"></li>
            <li class="div_count_normal_li weekAvg"></li>
            <li class="div_count_normal_li monthAvg"></li>
         </ul>
         <div class="tu" tuname="新注册用户数"></div>
       </div>
       
       <table class="pie_table_view" style="overflow: hidden;">
	      <thead>
	      <tr>
	      	<td>渠道</td>
	      	<td>新增设备</td>
	      	<td>比率</td>
	      </tr>
	      </thead>
          <tbody>
          <tr>
	      	<td class="sm">渠道</td>
	      	<td class="value">新增设备</td>
	      	<td class="rate">比率</td>
	      </tr>
          </tbody>
	   </table>
	   
	   <table class="bar_line_table_view" style="overflow: hidden;">
	      <thead>
	      <tr>
	      	<td>日期</td>
	      	<td>渠道1</td>
	      	<td>渠道1</td>
	      	<td>渠道1</td>
	      	<td>渠道1</td>
	      	<td>渠道1</td>
	      	<td>渠道1</td>
	      </tr>
	      </thead>
          <tbody>
          <tr>
	      	<td class="ds">渠道</td>
	      	<td class="s1">新增设备</td>
	      	<td class="s2">比率</td>
	      	<td class="s3">比率</td>
	      	<td class="s4">比率</td>
	      	<td class="s5">比率</td>
	      	<td class="s6">比率</td>
	      </tr>
          </tbody>
	   </table>
	   
	   <div class="pirtu_view">
					     <div class="dataShap">
				           <ul style="right:80px;">
							 <li class="onChoose"><em class="columnar">图</em></li>
							 <li><em class="linear">表</em></li>
						   </ul>
				       </div>
				    
					    <div id="model_chart" class="clear-fix" style="width: 49%;float: left;overflow: hidden;position: relative;">
				        <div id="model" class="zhibiao_pie"> </div>
				        <div id="model_newEquip_data" class="zhibiao_pie_tab" hidden="hidden"
				         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top:10px;padding:10px 0px;float: left;"> </div>
				       
				       <div id="model_install_data" class="zhibiao_pie_tab" hidden="hidden"
				         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 10px;padding:10px 0px;float: left;margin-left: 2%;margin-right: 2%"> </div>
				      
				       <div id="model_dau_data" class="zhibiao_pie_tab" hidden="hidden"
				         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 10px;padding:10px 0px;float: right;"> </div>
				    </div>
				    
				     <div id="source_chart" style="width: 50%;float: right;overflow: hidden;position: relative;">
				      
				     <div id="source" class="zhibiao_pie"></div>
				     <div id="source_newEquip_data" class="zhibiao_pie_tab" hidden="hidden"
				         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: left;"> </div>
				       
				       <div id="source_install_data" class="zhibiao_pie_tab" hidden="hidden"
				         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: left;margin-left: 2%;margin-right: 2%"> </div>
				      
				       <div id="source_dau_data" class="zhibiao_pie_tab" hidden="hidden"
				         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: right;"> </div>
				     </div>
			      </div>
	   
	   <div class="sourceTu_view">
			        <div id="source_chart" class="clear-fix" style="width: 50%;float: left;overflow: hidden;position: relative;">
				      <div class="dataShap">
				           <ul style="top:19px;right:10px;">
							 <li class="onChoose"><em class="columnar">图</em></li>
							 <li><em class="linear">表</em></li>
						   </ul>
			          </div>
				     <div id="source" class="zhibiao_pie"></div>
				     <div id="source_newEquip_data" class="zhibiao_pie_tab" hidden="hidden"
				         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: left;"> </div>
				       
				       <div id="source_install_data" class="zhibiao_pie_tab" hidden="hidden"
				         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: left;margin-left: 2%;margin-right: 2%"> </div>
				      
				       <div id="source_dau_data" class="zhibiao_pie_tab" hidden="hidden"
				         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: right;"> </div>
				     </div>
				     
				     <div id="dau_zhu_chart" class="clear-fix" style="width: 50%;float: right;overflow: hidden;position: relative;">
					    <!--   <div class="dataShap">
				           <ul style="top:19px;right:10px;">
							 <li class="onChoose"><em class="columnar">图</em></li>
							 <li><em class="linear">表</em></li>
						   </ul>
				       </div> -->
				       <div id="dau_zhu" class="zhibiao_pie"></div>
				         <div id="dau_zhu_data" class="zhibiao_pie" hidden="hidden"
			           style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;"> </div>
				     </div>
				     
				     <div id="newEquipRate_line_chart" class="clear-fix" style="width: 50%;float: right;overflow: hidden;position: relative;">
					     <!-- <div class="dataShap">
				           <ul style="top:19px;right:10px;">
							 <li class="onChoose"><em class="columnar">图</em></li>
							 <li><em class="linear">表</em></li>
						   </ul>
				       </div>--> 
				       <div id="newEquipRate_line" class="zhibiao_pie"></div>
				     </div>
				     
				     <div id="newEquip_line_chart"  class="clear-fix" style="width: 50%;float: left;overflow: hidden;position: relative;">
					     <!-- <div class="dataShap">
				           <ul style="top:19px;right:10px;">
							 <li class="onChoose"><em class="columnar">图</em></li>
							 <li><em class="linear">表</em></li>
						   </ul>
				       </div>--> 
				       <div id="newEquip_line" class="zhibiao_pie"></div>
				     </div>
				     
				     
		 </div>
	</div>
</body>
</html>