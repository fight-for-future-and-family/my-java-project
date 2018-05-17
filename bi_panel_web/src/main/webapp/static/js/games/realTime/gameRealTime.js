$(function(){
	
	
	$(document).ready(function() {
	    $.gameRealTime.init();
	    $.timeZone.showTimeZone();
	});
	
	var isCanClientSubmit = true;
	var isCanSourceSubmint = true;
	
	$.gameRealTime={
		init:function(){
			$.gameRealTime.initEvent();
			$.gameRealTime.submit();
		},
		initEvent:function(){
			$("#query").click(function(){
				$("[css='dateSelect']").removeClass("ui-selected");
				$("#today").addClass("ui-selected");
				$("#lst2day").addClass("last");
				
				var dateArr = $.date.getBeforeTodayDate(Number($("#today").attr("val")));
				$("#beginTime_rt").val(dateArr[0]);
				$("#endTime_rt").val(dateArr[1]);
				
				$("[css='tabs']").removeClass("ui-selected");
				$("#tabs ul li").first().addClass("ui-selected");
				
				$("#tabs-1").hide();
				$("#tabs-3").hide();
				$("#tabs-2").show();
				
				$.gameRealTime.submit();
		    });
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.gameRealTime.redict($(n).attr("id"));
				});
			});
			
			$.gameRealTime.dateSelectChange();
			$.gameRealTime.dataTypeChange();
		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/gameRealTime/toGameRealTime.ac?id='+$("input[name='gamesId']").val()+'&view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		dateSelectChange:function(){
			var dateArr = $.date.getBeforeTodayDate(0);
			$("#beginTime_rt").val(dateArr[0]);
			$("#endTime_rt").val(dateArr[1]);
			
			$($("[css='dateSelect']")).each(function(i,n){
				$(n).click(function(){
					$("[css='dateSelect']").removeClass("ui-selected");
					$(n).addClass("ui-selected");
					
					var dateArr = $.date.getBeforeTodayDate(Number($(n).attr("val")));
					$("#beginTime_rt").val(dateArr[0]);
					$("#endTime_rt").val(dateArr[1]);
					
					$("[css='tabs']").removeClass("ui-selected");
					$("#tabs ul li").first().addClass("ui-selected");
					
					$("#tabs-1").hide();
					$("#tabs-3").hide();
					$("#tabs-2").show();
					
					$.gameRealTime.submit();
				});
			});
		},
		dataTypeChange:function(){
			$("#tabs-1").hide();
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
							$.gameRealTime.clientDataSubmit();
						}
						$("#tabs-1").show();
					}else if($(n).attr("val")=='tabs-3'){
						if(isCanSourceSubmit){
							$.gameRealTime.sourceDataSubmit();
						}
						$("#tabs-3").show();
					}else{
						$("#tabs-2").show();
					}
				});
			});
		},
		submit:function(){
			var ps = $.gameUtil.checkParam();
			if(ps == null){
				return;
		    }
			
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/gameRealTime/getGameRealTime.ac",
				  data: $.param(ps),
				  dataType: "json",
				  success: function(data){
				    	var textArr = $.gameRealTime.brokenTable_noClient(data.noClientResultList);
                        
                        $.gameRealTime.brokenDataDiv('installCount',textArr[0]);
				    	$.gameRealTime.brokenDataDiv('dauCount',textArr[1]);
				    	$.gameRealTime.brokenDataDiv('payCount',textArr[2]);
				    	$.gameRealTime.brokenDataDiv('avgARPU',textArr[3]);
				    	$.gameRealTime.brokenDataDiv('avgARPPU',textArr[4]);
				    	
				    	isCanClientSubmit = true;
				    	isCanSourceSubmit = true;
				  }
				});
		},
		brokenTable_noClient:function(reports){
			$('#dt2_wrapper').remove(); 
			
            var pay_count = 0,install_count = 0,dau_count=0,pu_count = 0,arpu = 0,arppu = 0;
			
			var table2 = $(".template_cache .table_2").clone(true);
			table2.attr("id","dt2");
			
			var rate = $("#gameRate").val();
			
			$.each(reports,function(i,v){
				var amount = v.amount / rate;
				pay_count += amount;
				install_count += v.install;
				dau_count += v.dau;
				pu_count += v.payment;
				
				var trTemp = $("tbody tr",table2).first().clone(true);
				
				$("td.date",trTemp).text(v.ds);
				$("td.install",trTemp).text(v.install);
				$("td.dau",trTemp).text(v.dau);
				$("td.pay",trTemp).text(amount);
				$("td.arpu",trTemp).text((v.dau == 0 ? 0 : Math.round(amount/v.dau*100)/100));
				$("td.arppu",trTemp).text((v.payment == 0 ? 0 : Math.round(amount/v.payment*100)/100));
				$("td.pu",trTemp).text(v.payment);
				$("td.pcnt",trTemp).text(v.paytimes);
				
				$('tbody',table2).append(trTemp); 
			});
			
			arpu = dau_count == 0 ? 0 : Math.round(pay_count/dau_count*100)/100;
			arppu = pu_count == 0 ? 0 : Math.round(pay_count/pu_count*100)/100;
			pay_count = Math.round(pay_count*100)/100;
			
			table2.removeClass("table_2").addClass("table table-striped");
			$("tbody tr",table2).first().remove();
			$('#data2').append(table2); 
			
			$.biDataTables.dataTables_realTime($('#dt2'));
			$('#dt2 tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
			
//			$('#dt2 button').click(function (){
//		        $("#downType").val("no_client");
//		        $(this).attr('disabled',true);
//		        $('#dt2 button').val(2);
//		        window.btnTimeout2=setInterval(function(){
//					$.gameUtil.btnTimeout($('#dt2 button'),window.btnTimeout2);
//				  },1000);
//		        $("#downLoadForm").submit();
//		    });
			
			return [install_count,dau_count,pay_count,arpu,arppu];
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
		                             return Math.round(v.amount/rate*100)/100;
		                          }
		                       },
		                       {
			                        targets: 5,
			                        render: function (a, b, v, d) {
			                             return (v.dau == 0 ? 0 : Math.round(v.amount/v.dau/rate*100)/100);
			                          }
			                    },
			                    {
			                        targets: 6,
			                        render: function (a, b, v, d) {
			                             return (v.payment == null || v.payment == 0 ? 0 : Math.round(v.amount/v.payment/rate*100)/100);
			                          }
			                     }
		                     ],
		         rowCallback:function(row, data) {
		                         if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
		                             $(row).addClass('highlight');
		                         }
		                     }
		    });

			$('#dt1').removeClass().addClass('table table-striped');
			
			$('#dt1 button').click(function (){
		        $("#downType").val("client");
		        $(this).attr('disabled',true);
		        $('#dt1 button').val(2);
		        window.btnTimeout1=setInterval(function(){
					$.gameUtil.btnTimeout($('#dt1 button'),window.btnTimeout1);
				  },1000);
		        $("#downLoadForm").submit();
		    });
			
			isCanClientSubmit = false;
			$.gameUtil.trHighLight($('#dt1 tbody'),selected);
		},
		sourceDataSubmit:function(){
			var ps = $.gameUtil.checkParam();
			if(ps == null){
				return;
		    }
			var selected = [];
			$('#dt3_wrapper').remove();
			
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
		        columns: [
		                      {"data": "ds"},
		                      {"data": "createive"},
		                      {"data": "adPlace"},
		                      {"data": "idfa"},
		                      {"data": "distinctIdfa"},
		                      {"data": "distinctIp"},
		                      {"data": "install"},
		                      {"data": "newEquip"},
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
		                    	orderable:false,
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
		                     ],
		                     rowCallback:function(row, data) {
		                         if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
		                             $(row).addClass('highlight');
		                         }
		                     }
		    });

			$('#dt3').removeClass().addClass('table table-striped');
			
			$('#dt3 button').click(function (){
		        $("#downType").val("source");
		        $(this).attr('disabled',true);
		        $(this).val(2);
		        window.btnTimeout3=setInterval(function(){
					$.gameUtil.btnTimeout($('#dt3 button'),window.btnTimeout3);
				  },1000);
		        $("#downLoadForm").submit();
		    });
			
			isCanSourceSubmit = false;
			
			$.gameUtil.trHighLight($('#dt3 tbody'),selected);
		},
		brokenDataDiv:function(name,count){
			$("#c_ul_"+name).text(count);
		}
	};
});
	                    