$(function() {
	$(document).ready(function() {
		$.monitorMetricsReport.init();
	});
	
	$.monitorMetricsReport = {
		init : function() {
			$.monitorMetricsReport.initEvent();
			$.monitorMetricsReport.submit();
		},
		initEvent:function() {
			$("#save").click(function(){
				var id = $("#id").html();

				var lowerLimit = $("#lowerLimit").val();
				var topLimit = $("#topLimit").val();
				var isEmail = $("#isEmail").prop('checked');
				var isPhone = $("#isPhone").prop('checked');
				if(!( ($.monitorMetricsReport.isNumber(lowerLimit)||lowerLimit=='')&&
						($.monitorMetricsReport.isNumber(topLimit)||topLimit=='')
						) ) {
					alert("参数不能为非数字");
					return ;
				}
				
				$.ajax({
					"type": "POST",
					"url": "/panel_bi/monitor_metric/editMonitorListData.ac",
					"data": {
						snid:$("input[name='snid']").val(),
						gameId:$("input[name='gameId']").val(),
						id:id,
						lowerLimit:lowerLimit,
						topLimit:topLimit,
						isEmail:isEmail,
						isPhone:isPhone
					},
					"success":function(data){
						if(data.msg=='success'){
							alert('修改成功！');
							$.monitorMetricsReport.submit();
						}
					}
				});
				
			});
			$("#cancel").click(function(){
				$("#data .dataTable_edit").first().remove();
				$("#data .dataTables_wrapper").first().show();
			});
		},
		submit:function() {
			var ps = {
				snid:$("input[name='snid']").val(),
				gameId:$("input[name='gameId']").val()
			};
			
			$('#data').show();
			
			$.ajax({
				type: "POST",
				url: "/panel_bi/monitor_metric/getMonitorListData.ac",
				data: {
					snid:$("input[name='snid']").val(),
					gameId:$("input[name='gameId']").val()
				},
				success:function(data){
					$("#data .dataTable_edit").first().remove();
					$("#data .dataTable_query").first().remove();
					$("#data .dataTables_wrapper").first().remove();
					$.monitorMetricsReport.brokenTable(data.data);
				}
			});
		},
		isNumber:function(str){
			var n = Number(str);
			if (!isNaN(n)){
			    return true;
			}else {
				return false;
			}
		},
		brokenTable:function(report){
			$("#dt_wrapper").remove();
			var table = $(".template_cache .dataTable_query").clone(true);
			table.attr("id","dt");
			
			$.each(report,function(i,v){
				var trTemp = $("tbody tr",table).first().clone(true);
				
				$("td.id",trTemp).text(v.id);
				$("td.columnName",trTemp).text(v.columnName);
				$("td.lowerLimit",trTemp).text(v.lowerLimit);
				$("td.topLimit",trTemp).text(v.topLimit);
				$("td.isEmail",trTemp).text(v.isEmail==true?"是":"否");
				$("td.isPhone",trTemp).text(v.isPhone==true?"是":"否");
				
				$('tbody',table).append(trTemp);
			});
			
			table.removeClass("dataTable").addClass("display");
			$("tbody tr",table).first().remove();
			$('#data').append(table);
			
			$.monitorMetricsReport.dataTables($('#dt'));
//			$('#dt tbody tr').click(function (){
//		        $(this).toggleClass('highlight');
//		    });
		},
		dataTables:function(div){
			$('.top').remove();
			$('.bottom').remove();
			$('.clear').remove();
			$('.DTTT_container').remove();
			
			var table = div.DataTable({
				"sPaginationType" : "full_numbers",
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
			    "order": [[ 1, "desc" ]],
			    "processing": false,
	            "lengthMenu": [ 10, 20, 50, 100],
			    "pageLength": 10,
			    "language":{
			    	"url": "/dataTables/chinese.json"
			    },
			    tableTools: {
			    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
	                                        "aButtons":[ 
	                            ]
		        },
		        columns: [
		                  {"data": "id"},
		                  {"data": "columnName"},
		                  {"data": "lowerLimit"},
		                  {"data": "topLimit"},
		                  {"data": "isEmail"},
		                  {"data": "isPhone"},
		                  {"data": null, "defaultContent": ""}
		                  ],
		        columnDefs: [
								{
									targets: 6,
									orderable:false,
									render: function (a, b, v, d) {
										var editBtn = "<button id='edit-btn' class='btn btn-default btn-sm' style='width:70px;' type='button' val='"+v.id+"'   >编辑</button>";
										var delBtn = "<button id='del-btn' class='btn btn-default btn-sm' style='width:70px;' type='button' val='"+v.id+"'   >删除</button>";
										return editBtn+"&nbsp;"+delBtn;
									}
								},
		                     ]
			});
			
			$("#data tbody").on("click","#edit-btn",function(){
				var id = $(this).attr("val");
				
				var data = {
					snid:$("input[name='snid']").val(),
					gameId:$("input[name='gameId']").val(),
					id:id
				}
				$.ajax({
					type : "POST",
					url : "/panel_bi/monitor_metric/getMonitorListData.ac",
					data : data,
					dataType : "json",
					success : function(msg) {
						$("#data .dataTable_edit").first().remove();
//						$("#data .dataTable_query").first().hide();
						$("#data .dataTables_wrapper").first().hide();
						var table = $(".template_cache .dataTable_edit").clone(true);
						$('#data').append(table);
						$.each(msg,function(i,v){
							document.getElementById("id").innerText = v[0].id;
							document.getElementById("columnName").innerText = v[0].columnName;
							$(".detail-table #lowerLimit").val(v[0].lowerLimit);
							$(".detail-table #topLimit").val(v[0].topLimit);
							$(".detail-table #isEmail").attr("checked",v[0].isEmail);
							$(".detail-table #isPhone").attr("checked",v[0].isPhone);
						});
					}
				});
			});
			
			$("#data tbody").on("click","#del-btn",function(){
				var id = $(this).attr("val");
				var data = {
					snid:$("input[name='snid']").val(),
					gameId:$("input[name='gameId']").val(),
					id:id
				}
				$.ajax({
					type : "POST",
					url : "/panel_bi/monitor_metric/delMonitorListData.ac",
					data : data,
					dataType : "json",
					success : function(data) {
						if(data.msg=='success'){
							alert('删除成功！');
							$.monitorMetricsReport.submit();
						}
					}
				});
			});
			
			div.removeClass();
			div.addClass('table table-striped');
		    return table;
		}
	};
});

