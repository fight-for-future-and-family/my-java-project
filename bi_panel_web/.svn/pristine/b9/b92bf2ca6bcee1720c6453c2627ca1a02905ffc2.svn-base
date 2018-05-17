$(function(){
	
	option_line = {
		    toolbox: {
		    	show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    }
		};
	
	option_bar = {
			title : {
		        text: '付费行为汇总',
		        subtext: '累计值'
		    },
		    tooltip : {trigger: 'axis',axisPointer : { type : 'shadow'}},
		    legend: {data:['付费额','付费人数']},
		    toolbox: {
		        show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true
		};
	
	var tab_avg_pay_user = null,tab_dateArr = null,tab_payArr = null,tab_userArr = null;
	$(document).ready(function() {
	    $.gamePayBehavior.init();
	    $.timeZone.showTimeZone();
	});
		
	$.gamePayBehavior={
			init:function(){
				$.gamePayBehavior.initEvent();
				$.gamePayBehavior.submit();
			},
			initEvent:function(){
				$("#query").click(function(){
					$.gamePayBehavior.submit();
			    });
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.gamePayBehavior.redict($(n).attr("id"));
					});
				});
				$("#indicators").change(function(){
					$.gameUtil.change($.gamePayBehavior);
				});
				
				$("#pay_user_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#pay_user_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						if($("em",n).attr("class") == 'columnar'){
							$("#pay_user").fadeIn();
							$("#pay_user_data").fadeOut();
							
							$("#pay_level_chart .dataShap ul").css('right','80px');
							$("#pay_level_chart .dataShap ul").css('top','19px');
						}else{
							$("#pay_user").fadeOut();
							$("#pay_user_chart .dataShap ul").css('right','85px');
							$("#pay_user_chart .dataShap ul").css('top','28px');
							
							if($("#dt_cnt_wrapper").length > 0){
								$("#pay_user_data").fadeIn();
							}else{
								$.gamePayBehavior.brokenLineTable();
								$("#pay_user_data").fadeIn();
							}
						}
					});
				});
				
				$("#pay_level_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#pay_level_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						if($("em",n).attr("class") == 'columnar'){
							$("#pay_level").fadeIn();
							$("#pay_level_data").fadeOut();
							
							$("#pay_level_chart .dataShap ul").css('right','80px');
							$("#pay_level_chart .dataShap ul").css('top','19px');
						}else{
							$("#pay_level").fadeOut();
							$("#pay_level_chart .dataShap ul").css('right','85px');
							$("#pay_level_chart .dataShap ul").css('top','28px');
							
							if($("#dt_lu_wrapper").length > 0){
								$("#pay_level_data").fadeIn();
							}else{
								$.gamePayBehavior.brokenBarTable();
								$("#pay_level_data").fadeIn();
							}
						}
					});
				});
			},
//			redict:function(view){
//				window.location.href='/panel_bi/gamePay/toGamePaymentAnalysis.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/gamePay/toGamePaymentAnalysis.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			submit:function(){
				var ps = $.gameUtil.checkParam();
				if(ps == null){
					return;
			    }
				
				if($("#s_c").val() == '-99'){
	    			$.gamePayBehavior.brokenAjaxTable(ps);
	    			return;
				}
				
				$('#buttonDiv button').disabled = false;
				$('#ajax_data_user').hide();
				$('#ajax_data_pay').hide();
				$('#buttonDiv').hide();
				$('#pay_level_chart').show();
				$('#pay_user_chart').show();
				$('#data_pay').show();
				$('#data_user').show();
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/gamePay/getGamePaymentAnalysis.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
						  
					    	var pay_level = document.getElementById('pay_level');
					    	var pay_user = document.getElementById('pay_user');
					    	var indicators = $('#indicators').val();
					    	
					    	var dateArr = $.gameUtil.getDay('day',$("input[name='beginTime']").val(),$("input[name='endTime']").val()); 
					    	
					    	tab_dateArr = dateArr;
					    	$.gamePayBehavior.pageCssControl();
					    	$.gamePayBehavior.brokenLine(pay_user,data.reports,dateArr);
					    	$.gamePayBehavior.brokenBar(pay_level,data.levPayView,data.levUserView);
						    $.gamePayBehavior.brokenTable(data.levPayUserReports,data.levPayPayReports);
					  }
				});
			},
            pageCssControl:function(){
				
				$("#pay_level").show();
				$("#pay_user").show();
	    		
	    		$("#pay_level_data").hide();
	    		$("#pay_user_data").hide();
				
				$('#dt_cnt_wrapper').remove();
				$('#dt_lu_wrapper').remove();
				
				$(".dataShap li").removeClass("onChoose");
				$(".dataShap li:eq(0)").addClass("onChoose");
				$(".dataShap li:eq(2)").addClass("onChoose");
			},
			brokenTable:function(levPayUserReports,levPayPayReports){
				
				$('#dt_pay_wrapper').remove();
				var table = $(".template_cache .pay_level_table").clone(true);
				table.attr("id", "dt_pay");
				$('#data_pay').append(table);
				
				$.each(levPayPayReports,function(i,p){
					var v = p.levelPay;
					var trTemp = $("tbody tr", table).first().clone(true);
					
					$("td.date", trTemp).text(v.statDay);
					$("td.lp10", trTemp).text(Math.round(v.lp10*100)/100);
					$("td.lp50", trTemp).text(Math.round(v.lp50*100)/100);
					$("td.lp100", trTemp).text(Math.round(v.lp100*100)/100);
					$("td.lp200", trTemp).text(Math.round(v.lp200*100)/100);
					$("td.lp500", trTemp).text(Math.round(v.lp500*100)/100);
					$("td.lp1000", trTemp).text(Math.round(v.lp1000*100)/100);
					$("td.lp2000", trTemp).text(Math.round(v.lp2000*100)/100);
					$("td.lp2000up", trTemp).text(Math.round(v.lp2000up*100)/100);

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("pay_level_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables($('#dt_pay'));
				$('#dt_pay tbody tr').click(function (){
				     $(this).toggleClass('highlight');
				 });
				
				
				$('#dt_user_wrapper').remove();
				var table = $(".template_cache .pay_user_table").clone(true);
				table.attr("id", "dt_user");
				$('#data_user').append(table);
				
				$.each(levPayUserReports,function(i,p){
					var v = p.levelPay;
					var trTemp = $("tbody tr", table).first().clone(true);
					
					$("td.date", trTemp).text(v.statDay);
					$("td.lp10", trTemp).text(Math.round(v.lp10*100)/100);
					$("td.lp50", trTemp).text(Math.round(v.lp50*100)/100);
					$("td.lp100", trTemp).text(Math.round(v.lp100*100)/100);
					$("td.lp200", trTemp).text(Math.round(v.lp200*100)/100);
					$("td.lp500", trTemp).text(Math.round(v.lp500*100)/100);
					$("td.lp1000", trTemp).text(Math.round(v.lp1000*100)/100);
					$("td.lp2000", trTemp).text(Math.round(v.lp2000*100)/100);
					$("td.lp2000up", trTemp).text(Math.round(v.lp2000up*100)/100);
					
					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("pay_user_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables($('#dt_user'));
				$('#dt_user tbody tr').click(function (){
					$(this).toggleClass('highlight');
				});
			},
			brokenBar:function(cc,levPayView,levUserView){
				require.config({
					paths: {
						echarts: '/js/echarts/dist/',
						theme:'/js/echarts/theme/'
					}
				});
				require(
					[
						'echarts',
						'echarts/chart/bar',  
						'echarts/chart/line',
					],
					DrawEChart
				);
				
				function DrawEChart(ec){
					require(['theme/macarons'], function(tarTheme){
						var myChart = ec.init(cc,tarTheme);
						
						var currency = $("#gameCurrency").val();
						
						var payArr = [Math.round(levPayView.levelPay.lp10*100)/100,Math.round(levPayView.levelPay.lp50*100)/100,Math.round(levPayView.levelPay.lp100*100)/100,
						              Math.round(levPayView.levelPay.lp200*100)/100,Math.round(levPayView.levelPay.lp500*100)/100,Math.round(levPayView.levelPay.lp1000*100)/100,
						              Math.round(levPayView.levelPay.lp2000*100)/100,Math.round(levPayView.levelPay.lp2000up*100)/100];
						
						var userArr = [levUserView.levelPay.lp10,levUserView.levelPay.lp50,levUserView.levelPay.lp100,
						               levUserView.levelPay.lp200,levUserView.levelPay.lp500,levUserView.levelPay.lp1000,
						               levUserView.levelPay.lp2000,levUserView.levelPay.lp2000up];
						var xValue = ['[0,10]','(10,50]','(50,100]','(100,200]','(200,500]','(500,1000]','(1000,2000]','2000以上'];
						
						option_bar.xAxis = [{data:xValue,type:'category'}];
						option_bar.yAxis = [
						                 {type:'value',name:'付费额',boundaryGap:[0, 0.01],axisLabel : {formatter: '{value} '+currency+''}},
				                         {type : 'value',name : '付费人数',boundaryGap:[0, 0.01], axisLabel : {formatter: '{value}个'}}
				                       ];
						option_bar.tooltip = {trigger: 'axis'};
					    option_bar.series = [{name:'付费额',type:'bar',barGap:0,data:payArr},
						                      {name:'付费人数',yAxisIndex: 1,type:'bar',barGap:0,data:userArr}];
					    
					    myChart.setOption(option_bar,true);
					    
					    tab_payArr = payArr;
					    tab_userArr = userArr;
					});
				}
			},
			brokenLine:function(pay_user,reports,dateArr){

				require.config({
					paths: {
						echarts: '/js/echarts/dist/',
						theme:'/js/echarts/theme/'
					}
				});
				require(
					[
						'echarts',
						'echarts/chart/bar',  
						'echarts/chart/line',
					],
					DrawEChart
				);
				
				
				function DrawEChart(ec){
					require(['theme/macarons'], function(tarTheme){
						var myChart = ec.init(pay_user,tarTheme);
						
						var avg_pay_user = new Array();
						for(var i = 0;i<dateArr[1].length;i++){
							avg_pay_user.push('-');
						}
						
						$.each(dateArr[1],function(i,d){
							$.each(reports,function(j,v){
								if(d == v.statDay){
									avg_pay_user[i] = Math.round(v.totalPaymentCnt / v.payer*100)/100;
								}
							});
						});
						
						option_line.xAxis = [{data:dateArr[0],type:'category'}];
						option_line.yAxis = [{type:'value',boundaryGap:[0, 0.01],axisLabel:{formatter: '{value}次'}}];
						option_line.title = { text:'人均付费次数',subtext:'单位（次）'};
						option_line.tooltip = {trigger: 'axis',formatter: '{b}<br>{a}: {c}次'};
						option_line.series = [{name:'人均付费次数',type:'line',data:avg_pay_user}];
						myChart.setOption(option_line,true);
						
						tab_avg_pay_user = avg_pay_user;
			        });
				}
			},
			brokenLineTable:function(){
				$('#dt_cnt_wrapper').remove();
				var table = $(".template_cache .pay_cnt_table").clone(true);
				table.attr("id", "dt_cnt");
				$('#pay_user_data').append(table);
				
				$.each(tab_avg_pay_user,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);
					
					$("td.date", trTemp).text(tab_dateArr[1][i]);
					$("td.avg_pay_cnt", trTemp).text(v);
					
					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("pay_cnt_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart($('#dt_cnt'));
				$('#dt_cnt tbody tr').click(function (){
					$(this).toggleClass('highlight');
				});
			},
			brokenBarTable:function(){
				$('#dt_lu_wrapper').remove();
				var table = $(".template_cache .pay_level_user_table").clone(true);
				table.attr("id", "dt_lu");
				$('#pay_level_data').append(table);
				
				var stepArr = ['[0,10]','(10,50]','(50,100]','(100,200]','(200,500]','(500,1000]','(1000,2000]','2000以上'];
				for(var i=0;i<stepArr.length;i++){
					var trTemp = $("tbody tr", table).first().clone(true);
					
					$("td.step", trTemp).text(stepArr[i]);
					$("td.payAmount", trTemp).text(tab_payArr[i]);
					$("td.payUser", trTemp).text(tab_userArr[i]);
					
					$('tbody', table).append(trTemp);
				}
				
				table.removeClass("pay_level_user_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart_defined_sort($('#dt_lu'),[0]);
				$('#dt_lu tbody tr').click(function (){
					$(this).toggleClass('highlight');
				});
			},
			brokenAjaxTable:function(ps){
				var selected = [];
				var selected1 = [];
				$('#dt_ajax_user_wrapper').remove();
				$('#dt_ajax_pay_wrapper').remove();
				
				var type = $("#indicators").val();
				var rate = $("#gameRate").val();
				$('#ajax_data_user').show();
				$('#ajax_data_pay').show();
				$('#buttonDiv').show();
				$('#pay_level_chart').hide();
				$('#pay_user_chart').hide();
				$('#data_pay').hide();
				$('#data_user').hide();
				
				var table_user = $(".template_cache .ajax_user_table").clone(true);
				var table_pay = $(".template_cache .ajax_pay_table").clone(true);
                
				var headTemp_user = $("thead tr",table_user).first();
				var headTemp_pay = $("thead tr",table_pay).first();
				
				if("source" == type){
					$("td.head_type",headTemp_user).text('渠道');
					$("td.head_type",headTemp_pay).text('渠道');
				}else{
					$("td.head_type",headTemp_user).text('服务器');
					$("td.head_type",headTemp_pay).text('服务器');
				}
				
				table_user.attr("id", "dt_ajax_user");
				table_pay.attr("id", "dt_ajax_pay");
				$('#ajax_data_pay').append(table_pay);
				$('#ajax_data_user').append(table_user);
				
				$('#dt_ajax_user').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
					          data: function(d){
					        	  d.id=ps.id;
					        	  d.snid=ps.snid;
					        	  d.gameId=ps.gameId;
					        	  d.indicators=ps.indicators;
					        	  d.queryType=ps.queryType;
					        	  d.beginTime=ps.beginTime;
					        	  d.endTime=ps.endTime;
					        	  d.source=ps.source;
					        	  d.clientid=ps.clientid;
					        	  d.view=ps.view;
					        	  d.initTableType="user";
					          },
							  type: "POST",
							  url:"/panel_bi/gamePay/getGamePaymentAnalysis.ac"
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
			                      {"data": "levelPay.statDay"},
			                      {"data": "levelPay.sourceOrClientName"},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null}
			                  ],
			        columnDefs: [
			                     {
			                    	 targets: 1,
			                    	 orderable:  type == "source" ? false : true
			                     },
			                     {
			                    	 targets: 2,
			                    	 render: function (a, b, c, d) {
			                    		 return Math.round(c.levelPay.lp10*100)/100;
			                    	 }
			                     },
			                     {
			                    	 targets: 3,
			                    	 render: function (a, b, c, d) {
			                    		 return Math.round(c.levelPay.lp50*100)/100;
			                    	 }
			                     },
			                      {
			                        targets: 4,
			                        render: function (a, b, c, d) {
			                             return Math.round(c.levelPay.lp100*100)/100;
			                          }
			                       },
			                       {
				                        targets: 5,
				                        render: function (a, b, c, d) {
				                             return Math.round(c.levelPay.lp200*100)/100;
				                          }
				                    },
				                    {
				                        targets: 6,
				                        render: function (a, b, c, d) {
				                             return Math.round(c.levelPay.lp500*100)/100;
				                          }
				                     },
				                     {
				                    	 targets: 7,
				                    	 render: function (a, b, c, d) {
				                    		 return Math.round(c.levelPay.lp1000*100)/100;
				                    	 }
				                     },
				                     {
				                    	 targets: 8,
				                    	 render: function (a, b, c, d) {
				                    		 return Math.round(c.levelPay.lp2000*100)/100;
				                    	 }
				                     },
				                     {
				                    	 targets: 9,
				                    	 render: function (a, b, c, d) {
				                    		 return Math.round(c.levelPay.lp2000up*100)/100;
				                    	 }
				                     }
			                     ]
			    });
				
				$('#dt_ajax_pay').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
					          data: function(d){
					        	  d.id=ps.id;
					        	  d.snid=ps.snid;
					        	  d.gameId=ps.gameId;
					        	  d.indicators=ps.indicators;
					        	  d.queryType=ps.queryType;
					        	  d.beginTime=ps.beginTime;
					        	  d.endTime=ps.endTime;
					        	  d.source=ps.source;
					        	  d.clientid=ps.clientid;
					        	  d.view=ps.view;
					        	  d.initTableType="pay";
					          },
							  type: "POST",
							  url:"/panel_bi/gamePay/getGamePaymentAnalysis.ac"
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
                        if ( $.inArray(data.DT_RowId, selected1) !== -1 ) {
                            $(row).addClass('highlight');
                        }
                    },
			        columns: [
			                      {"data": "levelPay.statDay"},
			                      {"data": "levelPay.sourceOrClientName"},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null}
			                  ],
			        columnDefs: [
			                     {
			                    	 targets: 1,
			                    	 orderable:  type == "source" ? false : true
			                     },
			                     {
			                    	 targets: 2,
			                    	 render: function (a, b, c, d) {
			                    		 return Math.round(c.levelPay.lp10*100)/100;
			                    	 }
			                     },
			                     {
			                    	 targets: 3,
			                    	 render: function (a, b, c, d) {
			                    		 return Math.round(c.levelPay.lp50*100)/100;
			                    	 }
			                     },
			                      {
			                        targets: 4,
			                        render: function (a, b, c, d) {
			                             return Math.round(c.levelPay.lp100*100)/100;
			                          }
			                       },
			                       {
				                        targets: 5,
				                        render: function (a, b, c, d) {
				                             return Math.round(c.levelPay.lp200*100)/100;
				                          }
				                    },
				                    {
				                        targets: 6,
				                        render: function (a, b, c, d) {
				                             return Math.round(c.levelPay.lp500*100)/100;
				                          }
				                     },
				                     {
				                    	 targets: 7,
				                    	 render: function (a, b, c, d) {
				                    		 return Math.round(c.levelPay.lp1000*100)/100;
				                    	 }
				                     },
				                     {
				                    	 targets: 8,
				                    	 render: function (a, b, c, d) {
				                    		 return Math.round(c.levelPay.lp2000*100)/100;
				                    	 }
				                     },
				                     {
				                    	 targets: 9,
				                    	 render: function (a, b, c, d) {
				                    		 return Math.round(c.levelPay.lp2000up*100)/100;
				                    	 }
				                     }
			                     ]
			    });

				$('#dt_ajax_user').removeClass().addClass('table table-striped');
				$('#dt_ajax_pay').removeClass().addClass('table table-striped');
				
				$('#buttonDiv button').click(function (){
			        $(this)[0].disabled = true;
			        $("#downLoadForm").submit();
			    });
				$('#buttonDiv button').click(function (){
			        $(this).attr('disabled',true);
			        $(this).val(2);
			        window.btnTimeout=setInterval(function(){
						$.gameUtil.btnTimeout($('#buttonDiv button'),window.btnTimeout);
					  },1000);
			        $("#downLoadForm").submit();
			    });
				
				$.gameUtil.trHighLight($('#dt_ajax_user tbody'),selected);
				$.gameUtil.trHighLight($('#dt_ajax_pay tbody'),selected1);
			}
	};	
});
	                    