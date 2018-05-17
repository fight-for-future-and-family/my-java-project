$(function() {
	$(document).ready(function() {
		$.wandaGameDailyReport.init();
	});

	var game;
	$.wandaGameDailyReport = {
		init : function() {
			$.wandaGameDailyReport.initEvent();
			$.wandaGameDailyReport.submit();
		},
		initEvent:function(){
			document.getElementById("query").disabled=true;
			document.getElementById("download").disabled=true;
			$("#query").click(function(){
				$.wandaGameDailyReport.submit();
		    });
			$("#download").click(function(){
				var beginDate = $("#beginTime").val();
				var endDate = $("#endTime").val();
				
				var url = "/panel_bi/wanda/downloadData.ac?beginDate="+beginDate+"&endDate="+endDate;
				$("#downForm").prop("action", url);
				window.btnTimeout1=setInterval(function(){
					$.gameUtil.btnTimeout($('#downloadCsv'),window.btnTimeout1);
				  },1000);
		        $("#downForm").submit();
		    });
		},
		submit : function() {
			$('#data').show();
			var beginTime = $('#beginTime').val();
			var endDate = $('#endTime').val();
			var data = {
				beginDate:beginTime,
				endDate:endDate
			}
			var minTime = $("#minTime").val();
			if(minTime!=""){
				if($.wandaGameDailyReport.checkDate(beginTime,endDate)){
					alert("开始日期和结束日期必须为同一个月");
					return;
				}
				var bool = $.wandaGameDailyReport.dateCompare($('#beginTime').val(),$('#endTime').val());
				if(!bool){
					alert("开始日期不能晚于结束日期");
					$(".ajax_loading").hide();
					return;
				}
				bool = $.wandaGameDailyReport.dateCompare($('#endTime').val(),minTime);
				if(!bool){
					alert("只能查询7天以前的数据");
					$(".ajax_loading").hide();
					return;
				}
			}
			$("#data .dataTables_wrapper").first().remove();
			$(".ajax_loading").show();
			document.getElementById("query").disabled=true;
			document.getElementById("download").disabled=true;
			$.ajax({
				type : "POST",
				url : "/panel_bi/wanda/getWanDaGameDatasView.ac",
				data:data,
				dataType : "json",
				success : function(data) {
					$(".ajax_loading").hide();
					var dsStr = "收入期间："+data.beginDate+" 至  "+data.endDate;
					document.getElementById("ds").innerText = dsStr;
					$("input[name='beginTime']").val(data.beginDate);
					$("input[name='endTime']").val(data.endDate);
					var minTime = $("#minTime").val();
					if(minTime==""){
						$("#minTime").val(data.endDate);
					}
					$.wandaGameDailyReport.brokenTable(data.reports);
					document.getElementById("query").disabled=false;
					document.getElementById("download").disabled=false;
				}
			});
		},
		checkDate:function(beginTime,endTime){
			var dt1 = beginTime;
		    var dt2 = endTime;
		    dt1 = new Date(dt1.replace(/-/g,"/"));
		    dt2 = new Date(dt2.replace(/-/g,"/"));
		    if((dt1.getMonth()==dt2.getMonth())&&(dt1.getFullYear()==dt2.getFullYear())){
		    	return false;
		    }else{
		    	return true;
		    }
		},
		dateCompare:function(startdate,enddate){
			var arr=startdate.split("-");
			var starttime=new Date(arr[0],arr[1],arr[2]);
			var starttimes=starttime.getTime();
			
			var arrs=enddate.split("-");
			var lktime=new Date(arrs[0],arrs[1],arrs[2]);
			var lktimes=lktime.getTime();

			if (starttimes > lktimes) {
				return false;
			} else {
				return true;
			}
		},
		substr:function(num){
			var amount = num;
			var length = amount.toString().length;
			var temp = "";
			for(var i=length-3;;i=i-3){
				if(i<=0){
					temp = amount.toString().substring(i,i+3)+temp;
					break;
				}else{
					temp = ','+amount.toString().substring(i,i+3)+temp;
				}
			}
			return temp;
		},
		brokenTable:function(reports){
			var table = $(".template_cache .dataTable").clone(true);
			table.attr("id","dt");
			var sumAmount = 0;
			var huaiAmount = 0;
			$.each(reports,function(i,v){
				if(v.paymentAmount==0){
					return true;
				}
				
				var trTemp = $("tbody tr",table).first().clone(true);
				
				$("td.id",trTemp).text(v.id==null?'':v.id);
				$("td.seriesName",trTemp).text(v.seriesName);
				$("td.onlineDate",trTemp).text(v.onlineDate);
				$("td.creative",trTemp).text(v.creative);
				$("td.paymentAmount",trTemp).text($.wandaGameDailyReport.substr(Math.round(v.paymentAmount)));
				$("td.rate",trTemp).text(v.rate==0?'':v.rate);
				$("td.hulaiAmount",trTemp).text($.wandaGameDailyReport.substr(Math.round(v.hulaiAmount)));
				sumAmount = sumAmount + Math.round(v.paymentAmount);
				huaiAmount = huaiAmount + Math.round(v.hulaiAmount);
				$('tbody',table).append(trTemp); 
			});
			
			/*var trSum = $("tbody tr",table).first().clone(true);
			$("td.id",trSum).text();
			$("td.seriesName",trSum).text('合计');
			$("td.onlineDate",trSum).text();
			$("td.creative",trSum).text();
			$("td.paymentAmount",trSum).text($.wandaGameDailyReport.substr(sumAmount));
			$("td.rate",trSum).text();
			$("td.hulaiAmount",trSum).text($.wandaGameDailyReport.substr(huaiAmount));
			$('tbody',table).append(trSum); */
			
			table.removeClass("dataTable").addClass("display");
			$("tbody tr",table).first().remove();
			$('#data').append(table);
			
			$.wandaGameDailyReport.dataTables($('#dt'));
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
			    "ordering": false, //排序功能
			    "order": [[ 1, "desc" ]],
			    "processing": false,
	            "lengthMenu": [ 10, 20, 50, 1000],
			    "pageLength": 1000,
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