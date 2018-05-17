$(function(){
	
	$(document).ready(function() {
	    $.hourReport.init();
	    $.timeZone.showTimeZone();
	});
	 var dlPageSize = 3;
	 var spendTimes = 0;
	$.hourReport={
		init:function(){
			$.hourReport.initEvent();
			$.hourReport.showDiv();
			$.hourReport.submit();
		},
		initEvent:function(){
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.hourReport.redict($(n).attr("id"));
				});
			});
			$("#channel").change(function(){
				$.hourReport.change($.hourReport);
			});
			$("#query").click(function(){
				$.hourReport.submit();
			});
			$("#redone").click(function(){
				$.hourReport.redone();
			});
			$("#up").click(function(){
				var pageNo = $("#pageNo").val();
				var dlList = document.getElementsByTagName("dl");
				var pageLength = Math.ceil(parseInt(dlList.length)/parseInt(dlPageSize));
				
				var showBeginNo = ((pageNo)-2)*parseInt(dlPageSize);
				var showEndNo = parseInt(pageNo-1)*parseInt(dlPageSize)-1;
				for(var i=parseInt(showBeginNo);i>=0&&i<=parseInt(showEndNo);i++){
					dlList[i].style.display="inline";
				}
				
				var beginNo = ((pageNo)-1)*parseInt(dlPageSize);
				var endNo = ((pageNo))*parseInt(dlPageSize)-1
				for(var i=parseInt(beginNo);i<=parseInt(endNo)&&i<parseInt(dlList.length);i++){
					dlList[i].style.display="none";
				}
				
				if(parseInt(pageNo)>1){
					pageNo = parseInt(pageNo)-1;
					$("#pageNo").val(pageNo);
				}
				
				if(parseInt(pageNo)==1){
					$("#up").attr("disabled", true);
				}
				if(parseInt(pageNo)!=parseInt(pageLength)){
					$("#down").attr("disabled", false);
				}
			});
			
			$("#down").click(function(){
				var pageNo = $("#pageNo").val();
				var dlList = document.getElementsByTagName("dl");
				var pageLength = Math.ceil(parseInt(dlList.length)/parseInt(dlPageSize));
				
				if(parseInt(pageLength)==parseInt(pageNo)){
					return false;
				}
				
				var beginNo = ((pageNo)-1)*parseInt(dlPageSize);
				var endNo = parseInt(pageNo)*parseInt(dlPageSize)-1;
				for(var i=parseInt(beginNo);i<=parseInt(endNo);i++){
					dlList[i].style.display="none";
				}
				
				var showBeginNo = parseInt(pageNo)*parseInt(dlPageSize);
				var showEndNo = (parseInt(pageNo)+1)*parseInt(dlPageSize)-1;
				for(var i=showBeginNo;i<=parseInt(showEndNo)&&i<parseInt(dlList.length);i++){
					dlList[i].style.display="inline";
				}
				
				if(parseInt(pageNo)<parseInt(pageLength)){
					pageNo = parseInt(pageNo)+1;
					$("#pageNo").val(pageNo);
				}
				
				if(parseInt(pageNo)>1){
					$("#up").attr("disabled", false);
				}else{
					$("#up").attr("disabled", true);
				}
				if(parseInt(pageNo)==parseInt(pageLength)){
					$("#down").attr("disabled", true);
				}
			});
			
			$("#zbsm-info").hide();
			$("#zbsm").click(function(){
				$("#zbsm-info").show();
			});
			$("#close").click(function(){
				
				$("#zbsm-info").hide();
			});
				
			
		},
		showDiv:function(){
			var dlList = document.getElementsByTagName("dl");
			for(var i=0;i<parseInt(dlPageSize);i++){
				dlList[i].style.display="inline";
			}
			$("#up").attr("disabled","true");
		},
//		redict:function(view){
//			window.location.href='/panel_bi/hourReport/toHourReportView.ac?id='+$("input[name='gamesId']").val()+'&view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/hourReport/toHourReportView.ac?id='+$("input[name='gamesId']").val()+'&view='+view+'&snid='+snid+'&gameId='+gameId;
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
					 clientid:$('#s_c').val(),
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
		       }else if(ps.channel == 'client' && (ps.clientid != null && ps.clientid == '-1')){
		    	   return null;
		       }
		      return ps;
		},
		redone:function(){
			var ps = $.hourReport.checkParam();
			$.ajax({
				type: "POST",
				url: "/panel_bi/hourReport/redoneHourReport.ac",
				data: $.param(ps),
				dataType: "json",
				success: function(data){
					if(data.status==0){
						alert("实时日报正在计算中,请稍后再试");
					}else if(data.status==3){
						var tmpHour = Number(data.hour);
						var tmpMinute = Number(data.minute);
						alert("实时日报在15分钟内重新计算过，请稍后再试(上次计算时间为"+(tmpHour>=10?tmpHour:"0"+tmpHour)+":"+(tmpMinute>=10?tmpMinute:"0"+tmpMinute))+")";
					}else{
						location.reload(true);
					}
				}
			});
		},
		submit:function(){
			var ps = $.hourReport.checkParam();
			if(ps == null){
				return;
		    }
			
			if(ps.source == '-99'){
				if(ps.channel == 'source'){
					$.hourReport.brokenTable_source(ps);
				}else if(ps.channel == 'client'){
					$.hourReport.brokenTable_client(ps);
				}
				return;
			}
			
			$(".data_table").hide();
			$("#data").hide();
			$(".ajax_loading").show();
			$("#ajax_data").hide();
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/hourReport/getHourReport.ac",
				  data: $.param(ps),
				  dataType: "json",
				  success: function(data){
					    
					  var viewHour = null;
					  var viewMinute = null;
					  
					  if(ps.channel == 'source'){
						  	viewHour = Number(data.sourceReports[0].hour);
				    		viewMinute = Number(data.sourceReports[0].minute);
					  }else if(ps.channel == 'client'){
				    		viewHour = Number(data.clientReports[0].hour);
				    		viewMinute = Number(data.clientReports[0].minute);
					  }else{
				    		viewHour = data.reports==null?data.hour:Number(data.reports[0].hour);
				    		viewMinute = data.reports==null?data.minute:Number(data.reports[0].minute);
					  }
					  
					  var str = "";
					  if(viewHour >= 24){
						  str = "全天";
						 // $("#date_rt").text("计算时间："+str);
						  $("#date_rt").text(str);
					  }else{
						  str = "0:00~"+viewHour+":"+ (viewMinute<10?"0"+viewMinute:viewMinute);
						 // $("#date_rt").text("计算时间：("+data.date+" "+str+")");
						  $("#date_rt").text("("+data.date+" "+str+")");
					  }
					  
					  if(data.status == 'error'){
						  $(".data_table").hide();
						  $("#data").hide();
						  $(".ajax_loading").show();
						  $(".ajax_loading img").hide();
						  $(".ajax_loading .ajax_loading_span").text("实时计算报表出错，请联系BI马上抢修!");
					  }else if(data.status == 'running'){
						  var spendMinuts = 2 - data.spendMinuts;
						  spendMinuts = spendMinuts < 0 ? 1 : spendMinuts;
						  $(".data_table").hide();
						  $("#data").hide();
						  $(".ajax_loading").show();
						  $(".ajax_loading .ajax_loading_span span").text(spendMinuts);
						  
						  window.timmer=setInterval(function(){
//							  if(spendTimes == spendMinuts){
//								  spendTimes = 0;
								  window.clearInterval(window.timmer); 
								  $.hourReport.submit();
//							  }else{
//								  var s = spendMinuts-spendTimes < 1 ? 1 : spendMinuts-spendTimes;
//								  $(".ajax_loading .ajax_loading_span span").text(s);
//								  spendTimes++;
//							  }
							  },30*1000);
					  }else{
						  
						  $(".data_table").show();
						  $("#data").show();
						  $(".ajax_loading").hide();
						  $("#ajax_data").hide();
						  
						  var textArr = new Array(8);
					    	if(ps.channel == 'source'){
					    		textArr = $.hourReport.brokenTable_source1(data.sourceReports,str);
					    	}else if(ps.channel == 'client'){
					    		textArr = $.hourReport.brokenTable_client1(data.clientReports,str);
					    	}else{
					    		textArr = $.hourReport.brokenTable_daily(data.reports,str);
					    	}
	                        
	                        $.hourReport.brokenDataDiv('installCount',Math.round(textArr[0]));
					    	$.hourReport.brokenDataDiv('dauCount',textArr[1]);
					    	$.hourReport.brokenDataDiv('payCount',textArr[2]);
					    	$.hourReport.brokenDataDiv('avgARPU',textArr[3]);
					    	$.hourReport.brokenDataDiv('avgARPPU',textArr[4]);
					    	$.hourReport.brokenDataDiv('pcu',textArr[5]);
					    	$.hourReport.brokenDataDiv('acu',textArr[6]);
					    	$.hourReport.brokenDataDiv('avgUserTime',textArr[7]);
					  }
				  }
				});
		},
		brokenTable_daily:function(reports,str){
			$('#dt_source_ajax_wrapper').remove();
			$('#dt_client_ajax_wrapper').remove();
			$('#dt_wrapper').remove(); 
			
            var pay_count = 0,install_count = 0,dau_count=0,pu_count = 0,arpu = 0,arppu = 0,acu=0,pcu=0,avgUserTime=0;
			
			var table = $(".template_cache .all_daily").clone(true);
			table.attr("id","dt");
			
			var rate = $("#gameRate").val();
			var currency = $("#gameCurrency").val();
			
			$.each(reports,function(i,v){
				var amount = v.payment / rate;
				pay_count += amount;
				install_count += v.dnu;
				dau_count += v.dau;
				pu_count += v.pu;
				acu += v.acu;
				pcu += v.pcu;
				avgUserTime += v.avgUserTime;
				
				var trTemp = $("tbody tr",table).first().clone(true);
				
				$("td.day",trTemp).text(v.day);
				$("td.hour",trTemp).text(str);
				$("td.dnu",trTemp).text(v.dnu);
				$("td.newEquip",trTemp).text(v.newEquip);
				$("td.dau",trTemp).text(v.dau);
				$("td.loyalUser",trTemp).text(v.loyalUser);
				$("td.pcu",trTemp).text(v.pcu);
				$("td.acu",trTemp).text(v.acu);
				$("td.avgUserTime",trTemp).text(v.avgUserTime);
				$("td.roleChoice",trTemp).text(v.roleChoice);
				$("td.roleChoiceRate",trTemp).text(Math.round(v.roleChoiceRate*100*100)/100 + '%');
				$("td.d1",trTemp).text(Math.round(v.d1*100*100)/100 + '%');
				$("td.pu",trTemp).text(v.pu);
				$("td.payment",trTemp).text(Math.round(v.payment/rate*100)/100);
				$("td.paymentTimes",trTemp).text(v.paymentTimes);
				$("td.payRate",trTemp).text(Math.round(v.payRate*100*100)/100 + '%');
				$("td.dnuArpu",trTemp).text(v.dnu==0 ? 0 : (Math.round(v.installPayAmount/v.dnu/rate*100)/100));
				$("td.arpu",trTemp).text(Math.round(v.arpu/rate*100)/100);
				$("td.arppu",trTemp).text(Math.round(v.arppu/rate*100)/100);
				$("td.newPu",trTemp).text(v.newPu);
				$("td.newPayment",trTemp).text(Math.round(v.newPayment/rate*100)/100);
				$("td.installPu",trTemp).text(v.installPu);
				$("td.installPayment",trTemp).text(Math.round(v.installPayAmount/rate*100)/100);
				$("td.installPayRate",trTemp).text(Math.round(v.installPayRate*100*100)/100 + '%');
				
				$('tbody',table).append(trTemp); 
			});
			
			arpu = dau_count == 0 ? 0 : Math.round(pay_count/dau_count*100)/100;
			arppu = pu_count == 0 ? 0 : Math.round(pay_count/pu_count*100)/100;
			pay_count = Math.round(pay_count*100)/100;
			
			table.removeClass("all_daily").addClass("table table-striped");
			$("tbody tr",table).first().remove();
			$('#data').append(table); 
			
			$.biDataTables.dataTables($('#dt'));
			$('#dt tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
			
			return [install_count,dau_count,pay_count,arpu,arppu,pcu,acu,avgUserTime];
		},
		brokenTable_source1:function(reports,str){
            $('#dt_wrapper').remove(); 
			
            var pay_count = 0,install_count = 0,dau_count=0,pu_count = 0,arpu = 0,arppu = 0,acu=0,pcu=0,avgUserTime=0;
			
			var table = $(".template_cache .source_daily").clone(true);
			table.attr("id","dt");
			
			var rate = $("#gameRate").val();
			var currency = $("#gameCurrency").val();
			
			$.each(reports,function(i,v){
				var amount = v.payment / rate;
				pay_count += amount;
				install_count += v.dnu;
				dau_count += v.dau;
				pu_count += v.pu;
				acu += v.acu;
				pcu += v.pcu;
				avgUserTime += v.avgUserTime;
				
				var trTemp = $("tbody tr",table).first().clone(true);
				
				$("td.day",trTemp).text(v.day);
				$("td.hour",trTemp).text(str);
				$("td.source",trTemp).text(v.source);
				$("td.dnu",trTemp).text(v.dnu);
				$("td.newEquip",trTemp).text(v.newEquip);
				$("td.dau",trTemp).text(v.dau);
				$("td.loyalUser",trTemp).text(v.loyalUser);
				$("td.pcu",trTemp).text(v.pcu);
				$("td.acu",trTemp).text(v.acu);
				$("td.avgUserTime",trTemp).text(v.avgUserTime);
				$("td.d1",trTemp).text(Math.round(v.d1*100*100)/100 + '%');
				$("td.pu",trTemp).text(v.pu);
				$("td.payment",trTemp).text(Math.round(v.payment/rate*100)/100);
				$("td.paymentTimes",trTemp).text(v.paymentTimes);
				$("td.payRate",trTemp).text(Math.round(v.payRate*100*100)/100 + '%');
				$("td.dnuArpu",trTemp).text(v.dnu==0 ? 0 : (Math.round(v.installPayAmount/v.dnu/rate*100)/100));
				$("td.arpu",trTemp).text(Math.round(v.arpu/rate*100)/100);
				$("td.arppu",trTemp).text(Math.round(v.arppu/rate*100)/100);
				$("td.newPu",trTemp).text(v.newPu);
				$("td.newPayment",trTemp).text(Math.round(v.newPayment/rate*100)/100);
				$("td.installPu",trTemp).text(v.installPu);
				$("td.installPayment",trTemp).text(Math.round(v.installPayAmount/rate*100)/100);
				$("td.installPayRate",trTemp).text(Math.round(v.installPayRate*100*100)/100 + '%');
				
				$('tbody',table).append(trTemp); 
			});
			
			arpu = dau_count == 0 ? 0 : Math.round(pay_count/dau_count*100)/100;
			arppu = pu_count == 0 ? 0 : Math.round(pay_count/pu_count*100)/100;
			pay_count = Math.round(pay_count*100)/100;
			
			table.removeClass("all_daily").addClass("table table-striped");
			$("tbody tr",table).first().remove();
			$('#data').append(table); 
			
			$.biDataTables.dataTables_source($('#dt'));
			$('#dt tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
			
			return [install_count,dau_count,pay_count,arpu,arppu,pcu,acu,avgUserTime];
		},
		brokenTable_source:function(ps,str){
			$('#dt_source_ajax_wrapper').remove();
			$('#dt_client_ajax_wrapper').remove();
			$("#data").hide();
			$(".data_table").hide();
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
				        	  d.clientid=ps.clientid;
				        	  d.view=ps.view;
				          },
						  type: "POST",
						  url:"/panel_bi/hourReport/getHourReport.ac"
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
		                      {"data": null},
		                      {"data": "source"},
		                      {"data": "dnu"},
		                      {"data":"newEquip"},
		                      {"data": "dau"},
		                      {"data": "loyalUser"},
		                      {"data": "pcu"},
		                      {"data": "acu"},
		                      {"data": "avgUserTime"},
		                      {"data": null},
		                      {"data": "pu"},
		                      {"data": null},
		                      {"data": "paymentTimes"},
		                      {"data": null},
		                      {"data": null},
		                      {"data": null},
		                      {"data": null},
		                      {"data": "newPu"},
		                      {"data": null},
		                      {"data": "installPu"},
		                      {"data": null},
		                      {"data": null}
		                  ],
		        columnDefs: [
		                     {
		                    	 targets: 0,
		                    	 orderable: false
		                     },
		                     {
		                    	 targets: 1,
		                    	 render: function (a, b, v, d) {
		                    		 var viewHour = Number(v.hour);
		                    		 var viewMinute = Number(v.minute);
		                    		 return "0:00~"+viewHour+":"+ (viewMinute<10?"0"+viewMinute:viewMinute);
		                    	 }
		                     },
		                     {
		                    	 targets: 10,
		                    	 render: function (a, b, v, d) {
		                    		 return Math.round(v.d1*100*100)/100 + '%';
		                    	 }
		                     },
		                      {
		                        targets: 12,
		                        render: function (a, b, v, d) {
		                             return Math.round((v.payment/rate)*100)/100;
		                          }
		                       },
		                       {
			                        targets: 14,
			                        render: function (a, b, v, d) {
			                             return Math.round(v.payRate*100*100)/100 + '%';
			                          }
			                    },
			                    {
			                        targets: 15,
			                        orderable: false,
			                        render: function (a, b, v, d) {
			                        	if(v.dnu==0||v.installPayAmount==0){
			                        		return "0";
			                        	}
			                             return Math.round(v.installPayAmount/v.dnu/rate*100)/100;
			                          }
			                    },
			                    {
			                        targets: 16,
			                        render: function (a, b, v, d) {
			                             return Math.round((v.arpu/rate)*100)/100;
			                          }
			                     },
			                     {
				                     targets: 17,
				                     render: function (a, b, v, d) {
				                         return Math.round((v.arppu/rate)*100)/100;
				                      }
				                  },
			                     {
			                    	 targets: 19,
			                    	 render: function (a, b, v, d) {
			                    		 return Math.round((v.newPayment/rate)*100)/100;
			                    	 }
			                     },
				                  {
				                	  targets: 21,
				                	  render: function (a, b, v, d) {
				                		  return Math.round((v.installPayAmount/rate)*100)/100;
				                	  }
				                  },
			                    	 {
			                    		 targets: 22,
			                    		 render: function (a, b, v, d) {
			                    			 return Math.round(v.installPayRate*100*100)/100 + '%';
			                    		 }
			                    	 }
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
		brokenTable_client:function(ps,str){
			$('#dt_source_ajax_wrapper').remove();
			$('#dt_client_ajax_wrapper').remove();
			$("#data").hide();
			$(".data_table").hide();
			$("#ajax_data").show();
			
			var selected = [];
			var rate = $("#gameRate").val();
			
			var table = table= $(".template_cache .client_ajax_daily").clone(true);
			table.attr("id", "dt_client_ajax");
			$('#ajax_data').append(table);
			
			$('#dt_client_ajax').dataTable({
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
				        	  d.clientid=ps.clientid;
				        	  d.view=ps.view;
				          },
						  type: "POST",
						  url:"/panel_bi/hourReport/getHourReport.ac"
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
		                      {"data": null},
		                      {"data": "clientid"},
		                      {"data": "dnu"},
		                      {"data": "dau"},
		                      {"data": "loyalUser"},
		                      {"data": "pcu"},
		                      {"data": "acu"},
		                      {"data": "avgUserTime"},
		                      /*{"data": null},*/
		                      {"data": "pu"},
		                      {"data": null},
		                      {"data": "paymentTimes"},
		                      {"data": null},
		                      {"data": null},
		                      {"data": null}
		                      /*{"data": "installPu"},
		                      {"data": null}*/
		                  ],
		        columnDefs: [
		                     {
		                    	 targets: 0,
		                    	 orderable: false
		                     },
		                     {
		                    	 targets: 1,
		                    	 orderable: false,
		                    	 render: function (a, b, v, d) {
		                    		 var viewHour = Number(v.hour);
		                    		 var viewMinute = Number(v.minute);
		                    		 return "0:00~"+viewHour+":"+ (viewMinute<10?"0"+viewMinute:viewMinute);
		                    	 }
		                     },
		                     {
		                    	 targets: 2,
		                    	 orderable: false
		                     },
		                     /*{
		                    	 targets: 6,
		                    	 render: function (a, b, v, d) {
		                    		 return Math.round(v.d1*100*100)/100 + '%';
		                    	 }
		                     },*/
		                      {
		                        targets: 10,
		                        render: function (a, b, v, d) {
		                             return Math.round((v.payment/rate)*100)/100;
		                          }
		                       },
		                       {
			                        targets: 12,
			                        render: function (a, b, v, d) {
			                             return Math.round(v.payRate*100*100)/100 + '%';
			                          }
			                    },
			                    {
			                        targets: 13,
			                        render: function (a, b, v, d) {
			                             return Math.round((v.arpu/rate)*100)/100;
			                          }
			                     },
			                     {
				                     targets: 14,
				                     render: function (a, b, v, d) {
				                         return Math.round((v.arppu/rate)*100)/100;
				                      }
				                  }
				                  /*{
				                	  targets: 15,
				                	  render: function (a, b, v, d) {
				                		  return Math.round((v.installPayAmount/rate)*100)/100;
				                	  }
				                  }*/
		                     ]
		    });

			$('#dt_client_ajax').removeClass().addClass('table table-striped');
			
			$('#dt_client_ajax button').click(function (){
		        $(this).attr('disabled',true);
		        $('#dt1 button').val(2);
		        window.btnTimeout1=setInterval(function(){
					$.gameUtil.btnTimeout($('#dt1 button'),window.btnTimeout1);
				  },1000);
		        $("#downLoadForm").submit();
		    });
			
			$.gameUtil.trHighLight($('#dt_client_ajax tbody'),selected);
		},
		brokenTable_client1:function(reports,str){
            $('#dt_wrapper').remove(); 
			
            var pay_count = 0,install_count = 0,dau_count=0,pu_count = 0,arpu = 0,arppu = 0,acu=0,pcu=0,avgUserTime=0;
			
			var table = $(".template_cache .client_daily").clone(true);
			table.attr("id","dt");
			
			var rate = $("#gameRate").val();
			var currency = $("#gameCurrency").val();
			
			$.each(reports,function(i,v){
				var amount = v.payment / rate;
				pay_count += amount;
				install_count += v.dnu;
				dau_count += v.dau;
				pu_count += v.pu;
				acu += v.acu;
				pcu += v.pcu;
				avgUserTime += v.avgUserTime;
				
				var trTemp = $("tbody tr",table).first().clone(true);
				
				$("td.day",trTemp).text(v.day);
				$("td.hour",trTemp).text(str);
				$("td.clientid",trTemp).text(v.clientid);
				$("td.dnu",trTemp).text(v.dnu);
				$("td.dau",trTemp).text(v.dau);
				$("td.loyalUser",trTemp).text(v.loyalUser);
				$("td.pcu",trTemp).text(v.pcu);
				$("td.acu",trTemp).text(v.acu);
				$("td.avgUserTime",trTemp).text(v.avgUserTime);
				/*$("td.d1",trTemp).text(Math.round(v.d1*100*100)/100 + '%');*/
				$("td.pu",trTemp).text(v.pu);
				$("td.payment",trTemp).text(Math.round(v.payment/rate*100)/100);
				$("td.paymentTimes",trTemp).text(v.paymentTimes);
				$("td.payRate",trTemp).text(Math.round(v.payRate*100*100)/100 + '%');
				$("td.arpu",trTemp).text(Math.round(v.arpu/rate*100)/100);
				$("td.arppu",trTemp).text(Math.round(v.arppu/rate*100)/100);
				/*$("td.installPu",trTemp).text(v.installPu);
				$("td.installPayment",trTemp).text(Math.round(v.installPayAmount/rate*100)/100);*/
				
				$('tbody',table).append(trTemp); 
			});
			
			arpu = dau_count == 0 ? 0 : Math.round(pay_count/dau_count*100)/100;
			arppu = pu_count == 0 ? 0 : Math.round(pay_count/pu_count*100)/100;
			pay_count = Math.round(pay_count*100)/100;
			
			table.removeClass("all_daily").addClass("table table-striped");
			$("tbody tr",table).first().remove();
			$('#data').append(table); 
			
			$.biDataTables.dataTables($('#dt'));
			$('#dt tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
			
			return [install_count,dau_count,pay_count,arpu,arppu,pcu,acu,avgUserTime];
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
				var ps = $.hourReport.checkParam();
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/hourReport/getGameSource.ac",
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
			}else if(value == 'client'){
				$("#s_c").remove();
				var ps = $.hourReport.checkParam();
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/hourReport/getGameClient.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
					    var span = $("#s_c_span");
					    var str = '<select id="s_c" style="max-width:240px"><option value="-1">请选择...</option><option value="-99">所有服务器</option>';
					    $.each(data.gameClients,function(i,v){
					    	str += '<option value="' + v +'">' + v +'</option>';
					    });
					    str += '</select>';
					    span.append(str);
					    
					    $("#s_c").change(function(){
					    	exclass.submit();
						});
					  }
				});
			}
		}
	};
});
	                    