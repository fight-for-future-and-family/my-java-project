<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>下载</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<style type="text/css">
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
/** 第一行 样式*/
tr.odd { background-color: red; }
/** 第二行 样式*/
tr.even { background-color: red; }
/** 当前排序第一行样式 */
tr.odd td.sorting_1 {
    background-color: red;
}
/** 当前排序第二行样式 */
tr.even td.sorting_1 {
    background-color: red;
}
/** 选择第一行样式 */
table.dataTable tr.DTTT_selected.odd {
    background-color: red;
}
/** 选择第二行样式 */
table.dataTable tr.DTTT_selected.even {
    background-color: red;
}
/** 排序选择第一行样式 */
table.dataTable tr.DTTT_selected.odd td.sorting_1 {
    background-color: red;
}
/** 排序选择第二行样式 */
table.dataTable tr.DTTT_selected.even td.sorting_1 {
    background-color: red;
}

/** 分页按钮样式 */
.paging_full_numbers a.paginate_button {
    background-color: white;
}
/** 第一页分页按钮样式 */
.paginate_button.first.disabled{
	background: linear-gradient(to bottom, #FFF 0%, #DCDCDC 100%) repeat scroll 0% 0% transparent;
}
/** 上一页分页按钮样式 */
.paginate_button.previous.disabled{
	background: linear-gradient(to bottom, #FFF 0%, #DCDCDC 100%) repeat scroll 0% 0% transparent;
}
/** 下一页分页按钮样式 */
.paginate_button.next.disabled{
	background: linear-gradient(to bottom, #FFF 0%, #DCDCDC 100%) repeat scroll 0% 0% transparent;
}
/** 最后一页分页按钮样式 */
.paginate_button.last.disabled{
	background: linear-gradient(to bottom, #FFF 0%, #DCDCDC 100%) repeat scroll 0% 0% transparent;
}
/** 选中分页按钮样式 */
.dataTables_wrapper .dataTables_paginate .paginate_button.current, .dataTables_wrapper .dataTables_paginate .paginate_button.current:hover {
    color: #333 !important;
    border: 1px solid #CACACA;
    background: linear-gradient(to bottom, #FFF 0%, #DCDCDC 100%) repeat scroll 0% 0% transparent;
}

/** 工具按钮样式 */
.DTTT_button{
	background-color: blue;
}
</style>
<script type="text/javascript" src="/js/download/dailyReportDownLoad.js?v=${version}"></script>
<script type="text/javascript">
$(document).ready(function() {
	var index = 1;
    $('#example').dataTable( {
        "processing": true,
        "serverSide": true,
        "ajax": "/panel_bi/tool/getTestData.ac?",
        "pageLength": 10,
	    "language":{
	    	"url": "/dataTables/chinese.json"
	    },
	    tableTools: {
	    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
                                        "aButtons":[ 
                        { 
                           "sExtends": "csv", 
                           "sButtonText": "下载数据" 
                         }]
        },
        "deferRender": true,
	    "sPaginationType" : "full_numbers",
        "dom" : 'T<"clear"><"top">rt<"bottom"ip><"clear">' ,
        "bDestroy" : true,
        "bPaginate" : true,
        "bInfo" : true,
		"bSort" : true,
		"bScrollCollapse" : true,
		"bScrollInfinite" : true,
	    "bFilter" : false,
	    "paging": true, //翻页功能
	    "lengthChange": false, //改变每页显示数据数量
	    "searching": false, //过滤功能
	    "ordering": true, //排序功能
	    "order": [[ 1, "desc" ]],
        columns: [
                      {"data": "name"},
                      {"data": null},
                      {"data": index == 1 ? "email" : "telepone"},
                      {"data": "telepone"}
                  ],
        columnDefs: [
               {
                 targets: 1,
                 render: function (a, b, c, d) {
                      return 100-c.age;
                   }
                }
         
              ]
    } );
} );
</script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_download" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
		    	<li>日报下载</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			    <ul class="detail-nav">
			       <li id="dailyDown"><a href="#"></a>日报下载</li>
			       <li class="active" id="testServer"><a href="#"></a>测试服务端推送</li>
			    </ul>
	</div>		
			<div id="data">
				<table id="example" class="display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th>名字</th>
							<th>年龄</th>
							<th>邮箱</th>
							<th>电话</th>
						</tr>
					</thead>
				</table>
			</div>
			
		</div>
	</div>
</body>
</html>