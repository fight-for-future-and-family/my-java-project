$(function(){
	
	$(document).ready(function() {
	    $.hourReportSourceLtv.init();
	    $.timeZone.showTimeZone();
	});
	
	 var spendTimes = 0;
	$.hourReportSourceLtv={
		init:function(){
			$.hourReportSourceLtv.initEvent();
			$.hourReportSourceLtv.submit();
		},
		initEvent:function(){
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.hourReportSourceLtv.redict($(n).attr("id"));
				});
			});
			$("#channel").change(function(){
				$.hourReportSourceLtv.change($.hourReportSourceLtv);
			});
			$("#query").click(function(){
				$.hourReportSourceLtv.submit();
			});
			$("#redone").click(function(){
				$.hourReportSourceLtv.redone();
			});
		},
//		redict:function(view){
//			window.location.href='/panel_bi/hourReportSourceLtv/toHourReportSourceLtvView.ac?id='+$("input[name='gamesId']").val()+'&view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/hourReportSourceLtv/toHourReportSourceLtvView.ac?id='+$("input[name='gamesId']").val()+'&view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		checkParam:function(){
			var ps={
				     id:$("input[name='gamesId']").val(),
				     gameId:$("input[name='gameId']").val(),
				     snid:$("input[name='snid']").val(),
					 beginTime:$("input[name='beginTime']").val(),
					 endTime:$("input[name='endTime']").val(),
					 beginHour:$("input[name='beginHour']").val(),
					 endHour:$("input[name='endHour']").val(),
					 source:$('#s_c').val(),
					 view:$('#view').val(),
					 channel:$('#channel').val()
				};
		      if(ps.id == null || ps.id == ''){ return null; }
		      if(ps.gameId == null || ps.gameId == ''){ return null; }
		      if(ps.snid == null || ps.snid == ''){ return null; }
		      
		      if(ps.channel == null || ps.channel == ''){
		    	  ps.channel = 'all'; 
		       }else if(ps.channel == 'source' && (ps.source != null && ps.source == '-1')){
		    	   return null;
		       }
		      return ps;
		},
		redone:function(){
			var ps = $.hourReportSourceLtv.checkParam();
			$.ajax({
				type: "POST",
				url: "/panel_bi/hourReportSourceLtv/redoneLtvHourReport.ac",
				data: $.param(ps),
				dataType: "json",
				success: function(data){
					if(data.status==0){
						alert("实时日报正在计算中,请稍后再试");
					}else if(data.status==3){
						var tmpHour = Number(data.hour);
						var tmpMinute = Number(data.minute);
						alert("实时日报在15分钟内重新计算过，请稍后再试(上次计算时间为"+(tmpHour>=10?tmpHour:"0"+tmpHour)+":"+(tmpMinute>=10?tmpMinute:"0"+tmpMinute))+")";
					}else if(data.status==4){
						alert(data.msg);
					}else{
						location.reload(true);
					}
				}
			});
		},
		submit:function(){
			var ps = $.hourReportSourceLtv.checkParam();
			if(ps == null){
				return;
		    }
			
			if(ps.source == '-99'){
				if(ps.channel == 'source'){
					$.hourReportSourceLtv.brokenTable_source(ps);
				}
				return;
			}
			
			$("#data").hide();
			$(".ajax_loading").show();
			$("#ajax_data").hide();
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/hourReportSourceLtv/getHourReportSourceLtv.ac",
				  data: $.param(ps),
				  dataType: "json",
				  success: function(data){
					    
					  var viewHour = null;
					  var viewMinute = null;
					  
					  if(ps.channel == 'source'){
						  	viewHour = Number(data.sourceReports[0].hour);
				    		viewMinute = Number(data.sourceReports[0].minute);
					  }else{
				    		viewHour = data.reports==null?data.hour:Number(data.reports[0].hour);
				    		viewMinute = data.reports==null?data.minute:Number(data.reports[0].minute);
					  }
					  
					  var str = "";
					  if(viewHour >= 24){
						  str = "全天";
						 // $("#date_rt").text("实时计算："+str);
						  $("#date_rt").text(str);
					  }else{
						  str = "0:00~"+viewHour+":"+ (viewMinute<10?"0"+viewMinute:viewMinute);
						 // $("#date_rt").text("实时计算：("+data.date+" "+str+")");
						  $("#date_rt").text("("+data.date+" "+str+")");
					  }
					  
					  if(data.status == 'error'){
						  $("#data").hide();
						  $(".ajax_loading").show();
						  $(".ajax_loading img").hide();
						  $(".ajax_loading .ajax_loading_span").text("实时分渠道留存计算出错，请联系BI马上抢修!");
					  }else if(data.status == 'running'){
						  var spendMinuts = 2 - data.spendMinuts;
						  spendMinuts = spendMinuts < 0 ? 1 : spendMinuts;
						  $("#data").hide();
						  $(".ajax_loading").show();
						  $(".ajax_loading .ajax_loading_span span").text(spendMinuts);
						  
						  window.timmer=setInterval(function(){
								  window.clearInterval(window.timmer); 
								  $.hourReportSourceLtv.submit();
							  },15*1000);
					  }else{
						  
						  $("#data").show();
						  $(".ajax_loading").hide();
						  $("#ajax_data").hide();
						  
						  var textArr = new Array(8);
					    	if(ps.channel == 'source'){
					    		$.hourReportSourceLtv.brokenTable_source1(data.sourceReports,str);
					    	}else{
					    		$.hourReportSourceLtv.brokenTable_daily(data.reports,str);
					    	}
					  }
				  }
				});
		},
		brokenTable_daily:function(reports,str){
			$('#dt_source_ajax_wrapper').remove();
			$('#dt_wrapper').remove(); 
			
			var table = $(".template_cache .all_daily").clone(true);
			table.attr("id","dt");
			
			var rate = $("#gameRate").val();
			var currency = $("#gameCurrency").val();
			
			$.each(reports,function(i,v){
				
				var trTemp = $("tbody tr",table).first().clone(true);
				
				$("td.day",trTemp).text(v.day);
				//$("td.hour",trTemp).text(str);
				$("td.reg",trTemp).text(v.reg);
				$("td.d0",trTemp).text(v.d0);
				$("td.d1",trTemp).text(v.d1);
				$("td.d2",trTemp).text(v.d2);
				$("td.d3",trTemp).text(v.d3);
				$("td.d4",trTemp).text(v.d4);
				$("td.d5",trTemp).text(v.d5);
				$("td.d6",trTemp).text(v.d6);
				$("td.d7",trTemp).text(v.d7);
				
				$('tbody',table).append(trTemp); 
			});
			
			table.removeClass("all_daily").addClass("table table-striped");
			$("tbody tr",table).first().remove();
			$('#data').append(table); 
			
			$.biDataTables.dataTables($('#dt'));
			$('#dt tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
		},
		brokenTable_source1:function(reports,str){
            $('#dt_wrapper').remove(); 
			
			var table = $(".template_cache .source_daily").clone(true);
			table.attr("id","dt");
			
			var rate = $("#gameRate").val();
			var currency = $("#gameCurrency").val();
			
			$.each(reports,function(i,v){
				var trTemp = $("tbody tr",table).first().clone(true);
				
				$("td.day",trTemp).text(v.day);
				//$("td.hour",trTemp).text(str);
				$("td.source",trTemp).text(v.source);
				$("td.reg",trTemp).text(v.reg);
				$("td.d0",trTemp).text(v.d0);
				$("td.d1",trTemp).text(v.d1);
				$("td.d2",trTemp).text(v.d2);
				$("td.d3",trTemp).text(v.d3);
				$("td.d4",trTemp).text(v.d4);
				$("td.d5",trTemp).text(v.d5);
				$("td.d6",trTemp).text(v.d6);
				$("td.d7",trTemp).text(v.d7);
				
				$('tbody',table).append(trTemp); 
			});
			
			table.removeClass("all_daily").addClass("table table-striped");
			$("tbody tr",table).first().remove();
			$('#data').append(table); 
			
			$.biDataTables.dataTables_source($('#dt'));
			$('#dt tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
		},
		brokenTable_source:function(ps,str){
			$('#dt_source_ajax_wrapper').remove();
			$("#data").hide();
			$("#ajax_data").show();
			
			var selected = [];
			var rate = $("#gameRate").val();
			
			var table = table= $(".template_cache .source_ajax_daily").clone(true);
			table.attr("id", "dt_source_ajax");
			$('#ajax_data').append(table);
			
			$('#dt_source_ajax').dataTable({
		        "processing": true,
		        "serverSide": true,
		        ajax: {
				          data: function(d){
				        	  d.id=ps.id;
				        	  d.gameId=ps.gameId;
				        	  d.snid=ps.snid,
				        	  d.channel=ps.channel;
				        	  d.beginTime=ps.beginTime;
				        	  d.endTime=ps.endTime;
				        	  d.beginHour=ps.beginHour;
				        	  d.endHour=ps.endHour;
				        	  d.source=ps.source;
				        	  d.view=ps.view;
				          },
						  type: "POST",
						  url:"/panel_bi/hourReportSourceLtv/getHourReportSourceLtv.ac"
		              },
		        "pageLength": 10,
			    "language":{
			    	"url": "/dataTables/chinese.json"
			    },
		        "deferRender": true,
			    "sPaginationType" : "full_numbers",
		        "dom" : '<"clear"><"top">frt<"bottom"ip><"clear">' ,
				"bSort" : false,
			    "bFilter" : true,
			    "paging": true, //翻页功能
			    "lengthChange": false, //改变每页显示数据数量
			    "searching": true, //过滤功能
			    "ordering": true, //排序功能
			    "order": [[ 0, "desc" ]],
			    rowCallback:function(row, data) {
                    if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                        $(row).addClass('highlight');
                    }
                },
		        columns: [
		                      {"data": "day"},
		                     // {"data": null},
		                      {"data": "source"},
		                      {"data": "reg"},
		                      {"data":"d0"},
		                      {"data":"d1"},
		                      {"data":"d2"},
		                      {"data":"d3"},
		                      {"data":"d4"},
		                      {"data":"d5"},
		                      {"data":"d6"},
		                      {"data":"d7"}
		                  ],
		        columnDefs: [
		                     {
		                    	 targets: 0,
		                    	 orderable: false
		                     }
		                    /* ,
		                     {
		                    	 targets: 1,
		                    	 render: function (a, b, v, d) {
		                    		 var viewHour = Number(v.hour);
		                    		 var viewMinute = Number(v.minute);
		                    		 return "0:00~"+viewHour+":"+ (viewMinute<10?"0"+viewMinute:viewMinute);
		                    	 }
		                     }*/
		                     ]
		    });

			$('#dt_source_ajax').removeClass().addClass('table table-striped');
			
			$('#dt_source_ajax button').click(function (){
		        $(this).attr('disabled',true);
		        $('#dt1 button').val(2);
		        window.btnTimeout1=setInterval(function(){
					$.gameUtil.btnTimeout($('#dt1 button'),window.btnTimeout1);
				  },1000);
		        $("#downLoadForm").submit();
		    });
			
			$.gameUtil.trHighLight($('#dt_source_ajax tbody'),selected);
		},
		brokenDataDiv:function(name,count){
			$("#c_ul_"+name).text(count);
		},
		change:function(exclass){
			var value = $("#channel").val();
			if(value == 'all'){
				$("#s_c").remove();
				exclass.submit();
			}else if(value == 'source'){
				$("#s_c").remove();
				var ps = $.hourReportSourceLtv.checkParam();
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/hourReportSourceLtv/getGameSource.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
					    var span = $("#s_c_span");
					    var str = '<select id="s_c" style="max-width:240px"><option value="-1">请选择...</option><option value="-99">所有渠道</option>';
					    $.each(data.gameSources,function(i,v){
					    	str += '<option value="' + v +'">' + v +'</option>';
					    });
					    str += '</select>';
					    span.append(str);
					    
					    $("#s_c").change(function(){
					    	if(spendTimes != 0){
					    		alert("正在实时计算，请耐心等待！");
					    		return;
					    	}
					    	exclass.submit();
						});
					  }
				});
			}
		}
	};
});
	                    