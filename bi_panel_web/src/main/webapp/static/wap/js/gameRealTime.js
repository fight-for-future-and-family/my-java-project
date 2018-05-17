$(function(){
	
	
	$(document).ready(function() {
	    $.gameRealTimeWap.init();
	});
	
	var isCanClientSubmit = true;
	var isCanSourceSubmint = true;
	var game;
	
	$.gameRealTimeWap={
		init:function(){
			$.gameRealTimeWap.initEvent();
			$.gameRealTimeWap.queryData();
		},
		initEvent:function(){
			$("#query").click(function(){
				$("[css='dateSelect']").removeClass("ui-selected");
				$("#today").addClass("ui-selected");
				
				var dateArr = $.date.getBeforeTodayDate(Number($("#today").attr("val")));
				$("#beginTime_rt").text(dateArr[0]);
				$("#endTime_rt").text(dateArr[1]);
				$("input[name='beginTime']").val(dateArr[0]);
				$("input[name='endTime']").val(dateArr[1]);

				$("[css='tabs']").removeClass("ui-selected");
				$("[css='tabs']").first().addClass("ui-selected");
				
				$('#dt1_wrapper').remove(); 
				$('#dt2_wrapper').remove(); 
				$('#dt3_wrapper').remove(); 
				$("#tabs-1").hide();
				$("#tabs-3").hide();
				$("#tabs-2").show();
				$.gameRealTimeWap.queryData();
		    });
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.gameRealTimeWap.redict($(n).attr("id"));
				});
			});
			
			$("#layout-t span").click(function(){
				$.gameRealTimeWap.redict($(this).attr("view"));
			});
			
			$.gameRealTimeWap.dateSelectChange();
			$.gameRealTimeWap.dataTypeChange();
		},
//		redict:function(view){
//			window.location.href='/panel_bi/wap/gameView/toWapGameDatasView.ac?view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/wap/gameView/toWapGameDatasView.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		dateSelectChange:function(){
			var dateArr = $.date.getBeforeTodayDate(0);
			$("#beginTime_rt").text(dateArr[0]);
			$("#endTime_rt").text(dateArr[1]);
			$("input[name='beginTime']").val(dateArr[0]);
			$("input[name='endTime']").val(dateArr[1]);
			$(".template_cache").hide();
			
			$($("[css='dateSelect']")).each(function(i,n){
				$(n).click(function(){
					$("[css='dateSelect']").removeClass("ui-selected");
					$(n).addClass("ui-selected");
					
					var dateArr = $.date.getBeforeTodayDate(Number($(n).attr("val")));
					$("#beginTime_rt").text(dateArr[0]);
					$("#endTime_rt").text(dateArr[1]);
					$("input[name='beginTime']").val(dateArr[0]);
					$("input[name='endTime']").val(dateArr[1]);

					$("[css='tabs']").removeClass("ui-selected");
					$("[css='tabs']").first().addClass("ui-selected");
					
					$('#dt1_wrapper').remove(); 
					$('#dt2_wrapper').remove(); 
					$('#dt3_wrapper').remove(); 
					$("#tabs-1").hide();
					$("#tabs-3").hide();
					$("#tabs-2").show();
					$.gameRealTimeWap.queryData();
				});
			});
		},
		dataTypeChange:function(){
			$("#tabs-1").hide();
			$("#tabs-2").hide();
			$("#tabs-3").hide();
			$($("[css='tabs']")).each(function(i,n){
				$(n).click(function(){
					$("[css='tabs']").removeClass("ui-selected");
					$(n).addClass("ui-selected");
					
					$("#tabs-1").hide();
					$("#tabs-2").hide();
					$("#tabs-3").hide();
					if($(n).attr("val")=='tabs-1'){
						if(isCanClientSubmit){
							$.gameRealTimeWap.clientDataSubmit();
						}
						
						$("#tabs-1").show();
					}else if($(n).attr("val")=='tabs-3'){
						if(isCanSourceSubmit){
							$.gameRealTimeWap.sourceDataSubmit();
						}
						$("#tabs-3").show();
					}else{
						$("#tabs-2").show();
					}
				});
			});
		},
		queryData:function(){
			var ps = $.gameUtil.checkParam();
			if(ps == null){
				return;
		    }
			
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/wap/gameView/getWapGameDatasView.ac",
				  data: $.param(ps),
				  dataType: "json",
				  success: function(data){
					    game = data.game;
					    
				    	var textArr = $.gameRealTimeWap.brokenTable_noClient(data.noClientResultList);
                        
                        $.gameRealTimeWap.brokenDataDiv('installCount',textArr[0],false);
				    	$.gameRealTimeWap.brokenDataDiv('dauCount',textArr[1],false);
				    	$.gameRealTimeWap.brokenDataDiv('payCount',textArr[2],true);
				    	$.gameRealTimeWap.brokenDataDiv('avgARPU',textArr[3],true);
				    	$.gameRealTimeWap.brokenDataDiv('avgARPPU',textArr[4],true);
				    	
				    	isCanClientSubmit = true;
				    	isCanSourceSubmit = true;
				    	
				    	$("[css='tabs']").removeClass("ui-selected");
				    	$("[css='tabs']").first().addClass("ui-selected");
				    	$("#tabs-2").show();
				  }
				});
		},
		clientDataSubmit:function(){

			var ps = $.gameUtil.checkParam();
			if(ps == null){
				return;
		    }
			var selected = [];
			$('#dt1_wrapper').remove();
			
			var rate = $("#gameRate").val();
			
			var table= $(".template_cache .table_1").clone(true);
			table.attr("id", "dt1");
			$('#data1').append(table);
			
			$('#dt1').dataTable( {
		        "processing": true,
		        "serverSide": true,
		        ajax: {
				          data: function(d){
				        	  d.id=ps.id;
				        	  d.gameId=ps.gameId;
				        	  d.snid=ps.snid;
				        	  d.indicators=ps.indicators;
				        	  d.queryType=ps.queryType;
				        	  d.beginTime=ps.beginTime;
				        	  d.endTime=ps.endTime;
				        	  d.source=ps.source;
				        	  d.clientid=ps.clientid;
				        	  d.view=ps.view;
				          },
						  type: "POST",
						  url:"/panel_bi/gameRealTime/getGameRealTimeClientView.ac"
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
		                      {"data": "ds"},
		                      {"data": "clientid"},
		                      {"data": "install"},
		                      {"data": "dau"},
		                      {"data": null},
		                      {"data": null},
		                      {"data": null},
		                      {"data": "payment"},
		                      {"data": "paytimes"}
		                  ],
		        columnDefs: [
		                      {
		                        targets: 4,
		                        render: function (a, b, v, d) {
		                             return v.amount == null || v.amount == 0 ? 0 : Math.round((v.amount/rate)*100)/100;
		                          }
		                       },
		                       {
			                        targets: 5,
			                        render: function (a, b, v, d) {
			                             return (v.dau == null || v.dau == 0 ? 0 : Math.round((v.amount/v.dau/rate)*100)/100);
			                          }
			                    },
			                    {
			                        targets: 6,
			                        render: function (a, b, v, d) {
			                             return (v.payment == null || v.payment == 0 ? 0 : Math.round(v.amount/v.payment/rate*100)/100);
			                          }
			                     }
		                     ]
		    });

			$('#dt1').removeClass().addClass('table table-striped');
			
			isCanClientSubmit = false;
			
			$('#dt1 button').click(function (){
			    $("#downType").val("client");
		        $(this).attr('disabled',true);
		        $(this).val(2);
		        window.btnTimeout=setInterval(function(){
					$.gameUtil.btnTimeout($('#dt1 button'),window.btnTimeout);
				  },1000);
		        $("#downLoadForm").submit();
		    });
			
			$.gameUtil.trHighLight($('#dt1 tbody'),selected);
		},
		brokenTable_noClient:function(reports){
			$('#dt2_wrapper').remove();
			var pay_count = 0,install_count = 0,dau_count=0,pu_count = 0,arpu = 0,arppu = 0;
			
			var table2 = $(".template_cache .table_2").clone(true);
			table2.attr("id","dt2");
			
			$.each(reports,function(i,v){
				var amount = v.amount / game.rate;
				pay_count += amount;
				install_count += v.install;
				dau_count += v.dau;
				pu_count += v.payment;
				
				var trTemp = $("tbody tr",table2).first().clone(true);
				
				$("td.date",trTemp).text(v.ds);
				$("td.install",trTemp).text(v.install);
				$("td.dau",trTemp).text(v.dau);
				$("td.pay",trTemp).text(Math.round(amount*100)/100);
				$("td.arpu",trTemp).text((v.dau == 0 ? 0 : Math.round(amount/v.dau*100)/100));
				$("td.arppu",trTemp).text((v.payment == 0 ? 0 : Math.round(amount/v.payment*100)/100));
				$("td.pu",trTemp).text(v.payment);
				$("td.pcnt",trTemp).text(v.paytimes);
				
				$('tbody',table2).append(trTemp); 
			});
			
			arpu = dau_count == 0 ? 0 : Math.round(pay_count/dau_count*100)/100;
			arppu = pu_count == 0 ? 0 : Math.round(pay_count/pu_count*100)/100;
			
			table2.removeClass("table_2").addClass("display");
			$("tbody tr",table2).first().remove();
			
			$('#data2').append(table2); 
			
			$.biDataTables.dataTables($('#dt2'));
			
			$('#dt2 tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
			
			return [install_count,dau_count,pay_count,arpu,arppu];
		},
		sourceDataSubmit:function(){
			var ps = $.gameUtil.checkParam();
			if(ps == null){
				return;
		    }
			$('#dt3_wrapper').remove();
			var selected = [];
			var rate = $("#gameRate").val();
			
			var table= $(".template_cache .table_3").clone(true);
			table.attr("id", "dt3");
			$('#data3').append(table);
			
			$('#dt3').dataTable( {
		        "processing": true,
		        "serverSide": true,
		        ajax: {
				          data: function(d){
				        	  d.id=ps.id;
				        	  d.gameId=ps.gameId;
				        	  d.snid=ps.snid;
				        	  d.indicators=ps.indicators;
				        	  d.queryType=ps.queryType;
				        	  d.beginTime=ps.beginTime;
				        	  d.endTime=ps.endTime;
				        	  d.source=ps.source;
				        	  d.clientid=ps.clientid;
				        	  d.view=ps.view;
				          },
						  type: "POST",
						  url:"/panel_bi/gameRealTime/getGameRealTimeSourceView.ac"
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
		                      {"data": "ds"},
		                      {"data": "createive"},
		                      {"data": "adPlace"},
		                      {"data": "idfa"},
		                      {"data": "distinctIdfa"},
		                      {"data": "distinctIp"},
		                      {"data": "install"},
		                      {"data":"newEquip"},
		                      {"data": "installPayer"},
		                      {"data": null},
		                      {"data": null}
		                  ],
		        columnDefs: [
		                     {
		                    	 targets: [1,2],
		                    	 orderable:false
		                     },
		                      {
		                        targets: 10,
		                        render: function (a, b, v, d) {
		                             return Math.round((v.installPayer/v.install)*10000)/100+'%';
		                          }
		                       },
		                      {
		                    	  targets: 9,
		                    	  render: function (a, b, v, d) {
		                    		  return Math.round((v.installPayment/rate)*100)/100;
		                    	  }
		                      }
		                     ]
		    });

			$('#dt3').removeClass().addClass('table table-striped');
				    
		    $('#dt3 button').click(function (){
		        $("#downType").val("source");
		        $(this).attr('disabled',true);
		        $(this).val(2);
		        window.btnTimeout=setInterval(function(){
					$.gameUtil.btnTimeout($('#dt3 button'),window.btnTimeout);
				  },1000);
		        $("#downLoadForm").submit();
		    });
			
			$.gameUtil.trHighLight($('#dt3 tbody'),selected);
			
			isCanSourceSubmit = false;
		},
		brokenDataDiv:function(name,count,isPay){
			if(isPay){
				$("#c_ul_"+name).text(Math.round(count*100)/100+' '+game.currency);
			}else{
				$("#c_ul_"+name).text(Math.round(count*100)/100);
			}
		}
	};
});
	                    