$(function() {
	$(document).ready(function() {
		$.gamesDailySave.init();
	});
	$.gamesDailySave = {
		init : function() {
			$.gamesDailySave.addTable();
			$.gamesDailySave.initEvent();
		},
		addTable:function(){
			var table = $(".template_cache .dataTable_edit").clone(true);
			$('#data').append(table);
		},
		initEvent:function(){
			$("#query").click(function(){
				var seriesName = $('#seriesName').val();
				if(seriesName==""){
					alert("游戏名称不能为空");
					return;
				}
				var data = {
					seriesName:seriesName,
					type:"query"
				}
				$.ajax({
					type : "POST",
					url : "/panel_bi/wanda/getCreativeRate.ac",
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
				$("td.creative",trTemp).text(v.creative);
				$("td.rate",trTemp).text(v.rate);
				$("td.ds",trTemp).text(v.ds);
				
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
		                  {"data": "creative"},
		                  {"data": "rate"},
		                  {"data": "ds"},
		                  {"data": null, "defaultContent": "<button class='edit-btn btn btn-default btn-sm' type='button' onclick='editTest(this)'>编辑</button>"}
		                  ],
		        columnDefs: [
								{
									orderable: false,
									targets: 3
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
	var creative = obj.parentNode.parentNode.cells[1].innerText;
	var rate = obj.parentNode.parentNode.cells[2].innerText;
	var ds = obj.parentNode.parentNode.cells[3].innerText;
	var data = {
		seriesName:seriesName,
		creative:creative,
		rate:rate,
		ds:ds,
		type:"query"
	}
	$.ajax({
		type : "POST",
		url : "/panel_bi/wanda/getCreativeRate.ac",
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
				$(".detail-table #creative").val(v[0].creative);
				$(".detail-table #rate").val(v[0].rate);
				$(".detail-table #ds").val(v[0].ds);
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
	var creative = $('#creative').val();
	var rate = $('#rate').val();
	if(isNaN(rate)||rate>100){
		alert("请输入正确的分成比例");
		return;
	}
	var ds = $('#ds').val();
	if(creative!="安卓"&&creative!="官网"&&creative!="海外"&&creative.toLocaleUpperCase()!="IOS"&&creative!="页游"){
		alert("请输入正确的渠道")
		return;
	}
	if(seriesName==""||creative==""||rate==""){
		alert("请检查游戏信息是否填写完整");
		return;
	}
	var data = {
		seriesName:seriesName,
		creative:creative,
		rate:rate,
		ds:ds,
		type:"add"
	}
	$.ajax({
		type : "POST",
		url : "/panel_bi/wanda/addCreativeRate.ac",
		data : data,
		dataType : "json",
		success : function(msg) {
			if(msg.num==1){
				alert("录入成功！");
				$('#seriesNameQ').val('');
				$('#creative').val('');
				$('#rate').val('');
				$('#ds').val('');
			}else{
				alert("录入失败，请确认该游戏当天没有录入过分成比例！");
			}
		}
	});
}

function edit(){
	var seriesName = $('#seriesNameQ').val();
	var creative = $('#creative').val();
	var rate = $('#rate').val();
	if(isNaN(rate)||rate>100){
		alert("请输入正确的分成比例");
		return;
	}
	var ds = $('#ds').val();
	if(seriesName==""||creative==""||rate==""){
		alert("请确认信息填写完整");
		return;
	}
	var data = {
		seriesName:seriesName,
		creative:creative,
		rate:rate,
		ds:ds,
		type:"edit"
	}
	$.ajax({
		type : "POST",
		url : "/panel_bi/wanda/editCreativeRate.ac",
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

