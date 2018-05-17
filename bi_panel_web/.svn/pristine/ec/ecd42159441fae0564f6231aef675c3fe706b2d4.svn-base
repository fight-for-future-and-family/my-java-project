$(function(){
	$(document).ready(function() {
	    $.games.init();
	});
	
	$.games={
		init:function(){
			$.games.initEvent();
			$.games.submit();
		},
		initEvent:function(){
			$("#query").click(function(){
				
				var beginDate = $("#beginTime").val();
				var endDate = $("#endTime").val();
				if($.games.dateCompare(beginDate, endDate)<0){
					alert("开始日期不能大于结束日期");
					return;
				}else if($.games.dateCompare(beginDate, endDate)>30){
					alert("开始日期和结束日期相差不能超过30天");
					return;
				}
				
				$("[css='tabs']").removeClass("ui-selected");
				$("#tabs ul li").first().addClass("ui-selected");
				
				$("[css='dateSelect']").removeClass("ui-selected");
				$("#today").addClass("ui-selected");
				$("#lst2day").addClass("last");
				
				$("#tabs-1").show();
				$("#tabs-2").hide();
				
				$("#dataType").val("");
				$("#allGame").val("");
				
				$.games.submit();
			});
			
//			$.games.dateSelectChange();
			$.games.dataTypeChange();
		},
		checkParam:function(){
			var ps={
					gameTypeId:$("#gameTypeId").val(),
					gamesTypeName:$("#gamesTypeName").val()
			};
		},
		dateSelectChange:function(){
			$($("[css='dateSelect']")).each(function(i,n){
				$(n).click(function(){
					
					var beginDate = $("#beginTime").val();
					var endDate = $("#endTime").val();
					if($.games.dateCompare(beginDate, endDate)>30){
						alert("开始日期不能大于结束日期");
						return;
					}else if($.games.dateCompare(beginDate, endDate)<0){
						alert("开始日期和结束日期相差不能超过30天");
						return;
					}
					
					$("[css='dateSelect']").removeClass("ui-selected");
					$(n).addClass("ui-selected");
					
					$("[css='tabs']").removeClass("ui-selected");
					$("#tabs ul li").first().addClass("ui-selected");
					
					$("#tabs-1").show();
					$("#tabs-2").hide();
					
					$("#dataType").val($(n).attr("val"));
					
					$.games.submit();
				});
			});
		},
		dataTypeChange:function(){
			$("#tabs-2").hide();
			
			$($("[css='tabs']")).each(function(i,n){
				$(n).click(function(){
					var beginDate = $("#beginTime").val();
					var endDate = $("#endTime").val();
					if($.games.dateCompare(beginDate, endDate)>30){
						alert("开始日期不能大于结束日期");
						return;
					}else if($.games.dateCompare(beginDate, endDate)<0){
						alert("开始日期和结束日期相差不能超过30天");
						return;
					}
					$("[css='tabs']").removeClass("ui-selected");
					$(n).addClass("ui-selected");

					$("#tabs-1").hide();
					$("#tabs-2").hide();
					if($(n).attr("val")=='tabs-1'){
						$("#tabs-1").show();
					}else{
						$.games.gamesSubmit();
						$("#tabs-2").show();
					}
				});
			});
		},
		dateCompare:function(beginDate, endDate){
			var time = (new Date(Date.parse(endDate.replace(/-/g, "/")))).getTime() - (new Date(Date.parse(beginDate.replace(/-/g, "/")))).getTime();
			var day = Math.floor(time/(24*60*60*1000));
			
			return day;
		},
		gamesSubmit:function(){
			var ps = {
					gameTypeId:$("#gameTypeId").val(),
					gamesTypeName:$("#gamesTypeName").val(),
					dataType:$("#dataType").val(),
					beginDate:$("#dataType").val()==""?$("#beginTime").val():"",
					endDate:$("#endTime").val()
			};
			var beginDate = $("#beginTime").val();
			var endDate = $("#endTime").val();
			if($.games.dateCompare(beginDate, endDate)>30){
				alert("开始日期不能大于结束日期");
				return;
			}else if($.games.dateCompare(beginDate, endDate)<0){
				alert("开始日期和结束日期相差不能超过30天");
				return;
			}
			var selected = [];
			$('#dt2_wrapper').remove();
			var ps = {
					gameTypeId:$("#gameTypeId").val(),
					gamesTypeName:$("#gamesTypeName").val(),
					dataType:$("#dataType").val(),
					beginDate:$("#beginTime").val(),
					endDate:$("#endTime").val()
			};
			$.ajax({
				type: "POST",
				url: "/panel_bi/gameRealTime/getGameSeries.ac",
				data: $.param(ps),
				dataType: "json",
				success: function(data){
					var table2 = $(".template_cache .table_2").clone(true);
					table2.attr("id","dt2");
					var reports = data.report;
					var reportAll = data.reportAll;
					
					$.each(reportAll,function(i,v){
						var trSum = $("tbody tr",table2).first().clone(true);
						$("td.endDate",trSum).text(v.gameName+"总览");
//						$("td.endDate",trSum).text(v.gameName);
						$("td.dnu",trSum).text(v.dnu);
						$("td.dau",trSum).text(v.dau);
						$("td.paymentAmount",trSum).text(v.paymentAmount);
						$("td.arpu",trSum).text(v.arpu);
						$("td.arppu",trSum).text(v.arppu);
						$("td.equipment",trSum).text(v.equipment);
						$("td.payer",trSum).text(v.payer);
						
						$("tfoot",table2).append(trSum);
					});
					
					$.each(reports,function(i,v){
						
						var trTemp = $("tbody tr",table2).first().clone(true);
						
						$("td.endDate",trTemp).text(v.beginDate);
						$("td.gameName",trTemp).text(v.gameName);
						$("td.dnu",trTemp).text(v.dnu);
						$("td.dau",trTemp).text(v.dau);
						$("td.paymentAmount",trTemp).text(v.paymentAmount);
						$("td.arpu",trTemp).text(v.arpu);
						$("td.arppu",trTemp).text(v.arppu);
						$("td.equipment",trTemp).text(v.equipment);
						$("td.payer",trTemp).text(v.payer);
						
						$('tbody',table2).append(trTemp); 
					});
					
					table2.removeClass("table_2").addClass("table table-striped");
					$("tbody tr",table2).first().remove();
					$('#data2').append(table2);
					
					$.games.dataTables($('#dt2'));
					$('#dt2 tbody tr').click(function (){
				        $(this).toggleClass('highlight');
				    });
				}
			});
			
		},
		submit:function(){
			$(".ajax_loading").show();
			$(".data").hide();
			var ps = {
					gameTypeId:$("#gameTypeId").val(),
					gamesTypeName:$("#gamesTypeName").val(),
					dataType:$("#dataType").val(),
					beginDate:$("#beginTime").val(),
					endDate:$("#endTime").val()
			};
			$("#query").attr("disabled", true);
			$.ajax({
				type: "POST",
				url: "/panel_bi/gameRealTime/getAllGameSeries.ac",
				data: $.param(ps),
				dataType: "json",
				success: function(data){
					if(data.msg == '0') {
						window.timmer=setInterval(function(){
							  window.clearInterval(window.timmer); 
							  $.games.submit();
							  },2*1000);
					}
					
					$("#query").attr("disabled", false);
					$.games.brokenTable(data.report, data.reportAll);
					
					$.games.brokenDataDiv('dnu',data.reportAll.dnu);
			    	$.games.brokenDataDiv('dau',data.reportAll.dau);
			    	$.games.brokenDataDiv('payment',data.reportAll.paymentAmount);
			    	$.games.brokenDataDiv('arpu',data.reportAll.arpu);
			    	$.games.brokenDataDiv('arppu',data.reportAll.arppu);
			    	$(".ajax_loading").hide();
			    	$(".data").show();
				}
			});
		},
		brokenDataDiv:function(name,count){
			$("#c_ul_"+name).text(count);
		},
		brokenTable:function(reports, reportAll){
			$('#dt1_wrapper').remove();
			
			var pay_count = 0,install_count = 0,dau_count=0,pu_count = 0,arpu = 0,arppu = 0;
			
			var table1 = $(".template_cache .table_1").clone(true);
			table1.attr("id","dt1");
			
			if(reportAll!=null){
				var trSum = $("tbody tr",table1).first().clone(true);
				$("td.endDate",trSum).text("总览");
				$("td.dnu",trSum).text(reportAll.dnu);
				$("td.dau",trSum).text(reportAll.dau);
				$("td.paymentAmount",trSum).text(reportAll.paymentAmount);
				$("td.arpu",trSum).text(reportAll.arpu);
				$("td.arppu",trSum).text(reportAll.arppu);
				$("td.equipment",trSum).text(reportAll.equipment);
				$("td.payer",trSum).text(reportAll.payer);
				$("tfoot",table1).append(trSum);
			}
			
			$.each(reports,function(i,v){
				
				var trTemp = $("tbody tr",table1).first().clone(true);
				
				$("td.endDate",trTemp).text(v.beginDate);
				$("td.dnu",trTemp).text(v.dnu);
				$("td.dau",trTemp).text(v.dau);
				$("td.paymentAmount",trTemp).text(v.paymentAmount);
				$("td.arpu",trTemp).text(v.arpu);
				$("td.arppu",trTemp).text(v.arppu);
				$("td.equipment",trTemp).text(v.equipment);
				$("td.payer",trTemp).text(v.payer);
				
				$('tbody',table1).append(trTemp); 
			});
			
			table1.removeClass("table_1").addClass("table table-striped");
			$("tbody tr",table1).first().remove();
			$('#data1').append(table1);
			
			$.games.dataTables($('#dt1'));
			$('#dt1 tbody tr').click(function (){
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
			    "order": [[ 0, "desc" ]],
			    "processing": false,
	            "lengthMenu": [ 10, 20, 50, 1000],
			    "pageLength": 10,
			    "language":{
			    	"url": "/dataTables/chinese.json"
			    },
			    tableTools: {
			    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
	                                        "aButtons":[ 
	                            ]
		        }
			});
			div.removeClass();
		    div.addClass('table table-striped');
		    return table;
		}
	};
});