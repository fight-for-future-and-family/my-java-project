$(function(){
	$.extend( $.fn.dataTable.defaults, {
	    "searching": false
	} );
	
	$.biDataTables = {
		dataTables:function(div){
			
			$("#"+div.attr('id')+"_wrapper").remove();
			
			var table = div.DataTable({
			"sPaginationType" : "full_numbers",
//            "aoColumnDefs": [ { "bSortable": false, "aTargets": [1] }] ,
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
		    "order": [[ 0, "desc" ]],
		    "processing": true,
            "lengthMenu": [ 10, 20, 50, 100],
		    "pageLength": 10,
		    "language":{
		    	"url": "/dataTables/chinese.json"
		    },
		    tableTools: {
		    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
                                       "aButtons":[ 
                             { 
                                 "sExtends": "xls", 
                                 "sButtonText": "下载数据" 
                               }
                            ]
	        }
			}
		  );
			
		   div.removeClass();
		   div.addClass('table table-striped');
		   
		   return table;
		},
dataTables2:function(div){
			
			$('.top').remove();
			$('.bottom').remove();
			$('.clear').remove();
			$('.DTTT_container').remove();
			
			var table = div.DataTable({
			"sPaginationType" : "full_numbers",
            //"aoColumnDefs": [ { "bSortable": false, "aTargets": [1] }] ,
            "dom" : 'T<"top">rt<"bottom"ip><"clear">' ,
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
		    "order": [[ 0, "asc" ]],
		    "processing": false,
            "lengthMenu": [ 10, 20, 50, 100],
		    "pageLength": 10,
		    //"scrollY": 510,
	        //"scrollX": true,
		    "language":{
		    	"url": "/dataTables/chinese.json"
		    },
//		    tableTools: {
//	           "sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
//	            　　　            "aButtons":["csv"]
//	        }
		    tableTools: {
		    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
                                        "aButtons":[ 
                          ]
	        }
			}
		  );
			
		   div.removeClass();
		   div.addClass('table table-striped');
		   
		   return table;
		},
        dataTables_chart:function(div){
			
			//$('.top').remove();
			//$('.bottom').remove();
			//$('.clear').remove();
			//$('.DTTT_container').remove();
			
			div.DataTable({
			"sPaginationType" : "simple_numbers",
            //"aoColumnDefs": [ { "bSortable": false, "aTargets": [1] }] ,
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
		    "order": [[ 0, "desc" ]],
		    "processing": true,
            "lengthMenu": [ 10, 20, 50, 100],
		    "pageLength": 5,
		    "language":{
		    	"url": "/dataTables/chinese_chart.json"
		    },
		    tableTools: {
		    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
                                         "aButtons":[ 
                            { 
                                 "sExtends": "xls", 
                                 "sButtonText": "下载数据" 
                               }]
	        }
			}
		  );
			
		   div.removeClass();
		   div.addClass('table table-striped');
		},
        dataTables_chart_defined_sort:function(div,numArr){
			
			div.DataTable({
			"sPaginationType" : "simple_numbers",
            "aoColumnDefs": [ { "bSortable": false, "aTargets": numArr }] ,
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
		    "ordering": false, //排序功能
		    "order": [[ 0, "desc" ]],
		    "processing": true,
            "lengthMenu": [ 10, 20, 50, 100],
		    "pageLength": 5,
		    "language":{
		    	"url": "/dataTables/chinese_chart.json"
		    },
		    tableTools: {
		    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
                                         "aButtons":[ 
                            { 
                                 "sExtends": "xls", 
                                 "sButtonText": "下载数据" 
                               }]
	        }
			}
		  );
			
		   div.removeClass();
		   div.addClass('table table-striped');
		},
        dataTables_chart_no_page:function(div){
			
			div.DataTable({
			"sPaginationType" : "simple_numbers",
            "dom" : 'T<"clear"><"top">rt<"bottom"ip><"clear">' ,
            "bDestroy" : true,
            "bPaginate" : true,
            "bInfo" : true,
			"bSort" : true,
			"bScrollCollapse" : true,
			"bScrollInfinite" : true,
		    "bFilter" : false,
		    "paging": false, //翻页功能
		    "lengthChange": false, //改变每页显示数据数量
		    "searching": false, //过滤功能
		    "ordering": false, //排序功能
		    "order": [[ 0, "desc" ]],
		    "processing": true,
            "lengthMenu": [ 10, 20, 50, 100],
		    "pageLength": 10,
		    "language":{
		    	"url": "/dataTables/chinese_chart.json"
		    },
		    tableTools: {
		    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",                                        "aButtons":[ 
                            { 
                                 "sExtends": "xls", 
                                 "sButtonText": "下载数据" 
                               }]
	        }
			}
		  );
			
		   div.removeClass();
		   div.addClass('table table-striped');
		},
        dataTables_source:function(div){
			
			$('.top').remove();
			$('.bottom').remove();
			$('.clear').remove();
			$('.DTTT_container').remove();
			
			div.DataTable({
			"sPaginationType" : "full_numbers",
            //"aoColumnDefs": [ { "bSortable": false, "aTargets": [1] }] ,
            "dom" : 'T<"clear"><"top">frt<"bottom"ip><"clear">' ,
            "bDestroy" : true,
            "bPaginate" : true,
            "bInfo" : true,
			"bSort" : true,
			"bScrollCollapse" : true,
			"bScrollInfinite" : true,
		    "bFilter" : false,
		    "paging": true, //翻页功能
		    "lengthChange": false, //改变每页显示数据数量
		    "searching": true, //过滤功能
		    "ordering": true, //排序功能
		    "order": [[ 0, "desc" ]],
		    "processing": true,
            "lengthMenu": [ 10, 20, 50, 100],
		    "pageLength": 10,
		    "language":{
		    	"url": "/dataTables/chinese.json"
		    },
		    tableTools: {
		    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
                                         "aButtons":[ 
                            { 
                                 "sExtends": "xls", 
                                 "sButtonText": "下载数据" 
                               }]
	        }
			}
		  );
			
		   div.removeClass();
		   div.addClass('table table-striped');
		},
		dataTables_realTime:function(div){
			div.DataTable({
			"sPaginationType" : "full",
            "dom" : 'T<"clear"><"top">rt<"bottom"ip><"clear">' ,
            "bDestroy" : true,
            "bPaginate" : true,
            "bInfo" : true,
			"bSort" : false,
			"bSortable" : false,
			"bScrollCollapse" : true,
			"bScrollInfinite" : true,
		    "bFilter" : false,
		    "paging": true, //翻页功能
		    "lengthChange": false, //改变每页显示数据数量
		    "searching": false, //过滤功能
		    "ordering": true, //排序功能
		    "order": [[ 0, "desc" ]],
		    "processing": true,
            "lengthMenu": [ 10, 20, 50, 100],
		    "pageLength": 10,
		    "language":{
		    	"url": "/dataTables/chinese.json"
		    },
		    tableTools: {
		    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
                                         "aButtons":[ 
                            { 
                                 "sExtends": "xls", 
                                 "sButtonText": "下载数据" 
                               }]
	        }
			}
		  );
			
		   div.removeClass();
		   div.addClass('table table-striped');
		},
		dataTables_realTime2:function(div){
			$('.top').remove();
			$('.bottom').remove();
			$('.clear').remove();
			$('.DTTT_container').remove();
			
			var table2 = div.DataTable({
			"sPaginationType" : "full_numbers",
            "dom" : 'T<"clear"><"top">rt<"bottom"ip><"clear">' ,
            "bDestroy" : true,
            "bPaginate" : true,
            "bInfo" : true,
			"bSort" : false,
			"bSortable" : false,
			"bScrollCollapse" : true,
			"bScrollInfinite" : true,
		    "bFilter" : false,
		    "paging": true, //翻页功能
		    "lengthChange": false, //改变每页显示数据数量
		    "searching": false, //过滤功能
		    "ordering": true, //排序功能
		    "order": [[ 0, "desc" ]],
		    "processing": true,
            "lengthMenu": [ 10, 20, 50, 100],
		    "pageLength": 10,
		    "language":{
		    	"url": "/dataTables/chinese.json"
		    },
		    tableTools: {
		    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
                                         "aButtons":[ 
                           ]
	        }
			}
		  );
			
			 
		   return table2;
		},
        dataTables_tool:function(div){
			
			$('.top').remove();
			$('.bottom').remove();
			$('.clear').remove();
			$('.DTTT_container').remove();
			
			div.DataTable({
			"sPaginationType" : "full_numbers",
            //"aoColumnDefs": [ { "bSortable": false, "aTargets": [1] }] ,
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
		    "order": [[ 0, "desc" ]],
		    "processing": true,
            "lengthMenu": [ 10, 20, 50, 100],
		    "pageLength": 10,
		    "language":{
		    	"url": "/dataTables/chinese.json"
		    },
		    tableTools: {
		    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
                                         "aButtons":[ 
                            { 
                                 "sExtends": "xls", 
                                 "sButtonText": "下载数据" 
                               }]
	        }
			}
		  );
			
		   div.removeClass();
		   div.addClass('table table-striped');
		}
	}
});