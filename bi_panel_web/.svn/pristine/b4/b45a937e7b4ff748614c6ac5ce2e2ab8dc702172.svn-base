$(function() {
	$(document).ready(function() {
		$.gamesDailySave.init();
	});
	$.gamesDailySave = {
		init : function() {
			$.gamesDailySave.addTable();
			$.gamesDailySave.initEvent();
			$.gamesDailySave.textInit();
		},
		textInit:function(){
			var msg="";
			if($("#importMsg").val()!=null&&$("#importMsg").val()!=""){
				msg=$("#importMsg").val();
				alert(msg);
				$("#importMsg").val("");
			}
//			$("#uploadFile").val();
//			var uploadFile = $("#uploadFile").val();
//			var a = uploadFile;
		},
		addTable:function(){
			var table = $(".template_cache .dataTable_edit").clone(true);
			$('#data').append(table);
		},
		initEvent:function(){
			$("#upload").click(function(){
				var uploadFile = $("#uploadFile").val();
				var fileNameArr = uploadFile.split('.');
				if("csv"!=fileNameArr[fileNameArr.length-1]){
					alert("上传必须为csv文件");
					return;
				}
				if(uploadFile!=""){
					$("#fileForm").submit();
				}else{
					alert("文件不能为空");
				}
		    });
			$("#query").click(function(){
				var seriesName = $('#seriesName').val();
				var dateTime = $('#ec_beginTime').val();
//				if(seriesName==""){
//					alert("游戏名称不能为空");
//					return;
//				}
				var data = {
					seriesName:seriesName,
					dateTime:dateTime,
					type:"query"
				}
				$.ajax({
					type : "POST",
					url : "/panel_bi/wanda/getWanDaGameDatasView.ac",
					data : data,
					dataType : "json",
					success : function(msg) {
						if(msg.reports!=null){
							$("#data .dataTable_edit").first().remove();
							$("#data .dataTable_query").first().remove();
							$("#data .dataTables_wrapper").first().remove();
							$.gamesDailySave.brokenTable(msg.reports);
						}else{
							alert("该游戏没有流水，请检查游戏名称是否正确,或该游戏是否录入过流水");
						}
					}
				});
		    });
		},
		brokenTable:function(reports){
//			$(".detail-table .table").first().remove();
			var table = $(".template_cache .dataTable_query").clone(true);
			table.attr("id","dt");
			
			$.each(reports,function(i,v){
				var trTemp = $("tbody tr",table).first().clone(true);
				
				$("td.seriesName",trTemp).text(v.seriesName);
				$("td.ds",trTemp).text(v.ds);
				$("td.creative",trTemp).text(v.creative);
				$("td.paymentAmount",trTemp).text(v.paymentAmount);
				$("td.remark",trTemp).text(v.remark==null?'无':v.remark);
				
				$('tbody',table).append(trTemp);
			});
			
			table.removeClass("dataTable").addClass("display");
			$("tbody tr",table).first().remove();
			$('#data').append(table);
			
			$.gamesDailySave.dataTables($('#dt'));
			$('#dt tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
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
		                  {"data": "seriesName"},
		                  {"data": "ds"},
		                  {"data": "creative"},
		                  {"data": "paymentAmount"},
		                  {"data": "remark"},
		                  {"data": null, "defaultContent": "<button class='edit-btn btn btn-default btn-sm' type='button' onclick='editTest(this)'>编辑</button>"}
		                  ],
		        columnDefs: [
								{
									orderable: false,
									targets: 4
								},
		                     ]
			});
			div.removeClass();
		    div.addClass('table table-striped');
		    return table;
		}
		
	}
});

function add(){
	$("#data .dataTable_edit").first().remove();
	$("#data .dataTable_query").first().remove();
	$("#data .dataTables_wrapper").first().remove();
	var table = $(".template_cache .dataTable_edit").clone(true);
	$('#data').append(table);
	$('#editType').val("1");
}



function editTest(obj){
	var seriesName = obj.parentNode.parentNode.cells[0].innerText;
	var dateTime = obj.parentNode.parentNode.cells[1].innerText;
	var creative = obj.parentNode.parentNode.cells[2].innerText;
	var data = {
		seriesName:seriesName,
		dateTime:dateTime,
		creative:creative,
		type:"query"
	}
	$.ajax({
		type : "POST",
		url : "/panel_bi/wanda/getWanDaGameDatasView.ac",
		data : data,
		dataType : "json",
		success : function(msg) {
			$("#data .dataTable_edit").first().remove();
			$("#data .dataTable_query").first().remove();
			$("#data .dataTables_wrapper").first().remove();
			var table = $(".template_cache .dataTable_edit").clone(true);
			$('#data').append(table);
			$.each(msg,function(i,v){
				$(".detail-table #seriesNameQ").val(v[0].seriesName);
				$(".detail-table #ds").val(v[0].ds);
				$(".detail-table #creative").val(v[0].creative);
				$(".detail-table #paymentAmount").val(v[0].paymentAmount);
				$(".detail-table #remark").val(v[0].remark);
			});
		}
	});
	$('#editType').val("2");
}

function addAndEdit(){
	var editType = $('#editType').val();
	if(editType=="1"||editType==""){
		addTest();
	}else{
		edit();
	}
}

function addTest(){
	
	var seriesName = $('#seriesNameQ').val();
	var dateTime = $('#ds').val();
	var creative = $('#creative').val();
	var paymentAmount = $('#paymentAmount').val();
	var remark = $('#remark').val();
	if(seriesName==""||dateTime==""||creative==""||paymentAmount==""){
		alert("请检查游戏信息是否填写完整");
		return;
	}
	var data = {
		seriesName:seriesName,
		dateTime:dateTime,
		creative:creative,
		paymentAmount:paymentAmount,
		remark:remark,
		type:"add"
	}
	$.ajax({
		type : "POST",
		url : "/panel_bi/wanda/getWanDaGameDatasView.ac",
		data : data,
		dataType : "json",
		success : function(msg) {
			if(msg.num==1){
				alert("录入成功！");
				$('#seriesNameQ').val('');
				$('#ds').val('');
				$('#creative').val('');
				$('#paymentAmount').val('');
				$('#remark').val('');
			}else{
				alert("录入失败，请确认该游戏当天没有录入过流水！");
			}
		}
	});
}

function edit(){
	var seriesName = $('#seriesNameQ').val();
	var dateTime = $('#ds').val();
	var creative = $('#creative').val();
	var paymentAmount = $('#paymentAmount').val();
	var remark = $('#remark').val();
	if(seriesName==""||dateTime==""){
		alert("游戏名称或日期不能为空");
		return;
	}
	var data = {
		seriesName:seriesName,
		dateTime:dateTime,
		creative:creative,
		paymentAmount:paymentAmount,
		remark:remark,
		type:"edit"
	}
	$.ajax({
		type : "POST",
		url : "/panel_bi/wanda/getWanDaGameDatasView.ac",
		data : data,
		dataType : "json",
		success : function(msg) {
			if(msg.num==1){
				alert("修改成功！");
			}else if(msg.num!=1){
				alert("修改失败，请确定游戏名称或日期填写正确！");
			}
		}
	});
}

